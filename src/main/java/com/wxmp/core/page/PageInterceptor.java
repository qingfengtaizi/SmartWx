/*
 * FileName：PageInterceptor.java 
 * <p>
 * Copyright (c) 2017-2020, <a href="http://www.webcsn.com">hermit (794890569@qq.com)</a>.
 * <p>
 * Licensed under the GNU General Public License, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/gpl-3.0.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.wxmp.core.page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.PropertyException;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSession.StrictMap;

import lombok.extern.slf4j.Slf4j;

/**
 * 通过拦截StatementHandler的prepare方法，重写sql语句实现物理分页
 *
 * @author hermit
 * @date 2017 -06-28 14:32:28
 */
@Slf4j
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class PageInterceptor implements Interceptor {
    
    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    
    private static String dialect = ""; // 数据库类型(默认为mysql)
    
    private static String pageSqlId = ""; // 需要拦截的ID(正则匹配)
    
    @Override
    public Object intercept(Invocation invocation)
        throws Throwable {
        StatementHandler statementHandler = (StatementHandler)invocation.getTarget();
        MetaObject metaObject = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
        
        // 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环可以分离出最原始的的目标类)
        while (metaObject.hasGetter("h")) {
            Object object = metaObject.getValue("h");
            metaObject = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
        }
        // 分离最后一个代理对象的目标类
        while (metaObject.hasGetter("target")) {
            Object object = metaObject.getValue("target");
            metaObject = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
        }
        
        MappedStatement mappedStatement = (MappedStatement)metaObject.getValue("delegate.mappedStatement");
        // 只重写需要分页的sql语句。通过MappedStatement的ID匹配，默认重写以Page结尾的MappedStatement的sql
        if (mappedStatement.getId().matches(pageSqlId)) {
            BoundSql boundSql = (BoundSql)metaObject.getValue("delegate.boundSql");
            Object parameterObject = boundSql.getParameterObject();
            if (parameterObject == null) {
                log.error("parameterObject为空");
            } else {
                Page page = null;
                try {
                    // 获取实体类的分页属性
                    page = getParameterPage(metaObject);
                } catch (Exception e) {
                    log.error("实体没有page属性：", e);
                }
                
                if (page != null) {
                    Connection connection = (Connection)invocation.getArgs()[0];
                    // 获取sql语句
                    String sql = boundSql.getSql();
                    // 重设分页参数里的总页数等
                    setPageParameter(sql, connection, mappedStatement, boundSql, page, metaObject);
                    
                    // 采用物理分页后，就不需要mybatis的内存分页了，所以重置下面的两个参数
                    metaObject.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
                    metaObject.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
                    
                    // 重写sql
                    String pageSql = buildPageSql(sql, page);
                    metaObject.setValue("delegate.boundSql.sql", pageSql);
                }
            }
        }
        // 将执行权交给下一个拦截器
        return invocation.proceed();
    }
    
    /**
     * 获取分页属性对象
     *
     * @author hermit
     * @date 2016年9月1日
     * @param obj
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private Page getParameterPage(MetaObject obj) {
    	Page page = new Page();
        //解决异常:  实体没有page属性：,在进行分页查询条件是List<String>集合时
        RoutingStatementHandler originalObj =  (RoutingStatementHandler) obj.getOriginalObject();
        BoundSql boundSql = originalObj.getBoundSql();
        if(boundSql.getParameterObject().getClass() == DefaultSqlSession.StrictMap.class) {
        	DefaultSqlSession.StrictMap parameterObject = (StrictMap<?>) boundSql.getParameterObject();
            Set<Map.Entry> entrySet = parameterObject.entrySet();
            Iterator<Entry> iterator = entrySet.iterator();
            Page p = null;
            while(iterator.hasNext()) {
            	Entry entry = iterator.next();
            	if("list".equals(entry.getKey().toString())) {
            		List objList =(List) entry.getValue();
            		p = (Page) objList.get(0);
            		break;
            	}
            }
            page.setPage(p.getPage());
            page.setPageSize(p.getPageSize());
        }else {
        	page.setPage(PageUtil.obj2Int(obj.getValue("delegate.boundSql.parameterObject.page")));
            page.setPageSize(PageUtil.obj2Int(obj.getValue("delegate.boundSql.parameterObject.pageSize")));
        }
        
        // page.setTotal(PageUtil.obj2Int(obj.getValue("delegate.boundSql.parameterObject.total")));
        // page.setTotalPage(PageUtil.obj2Int(obj.getValue("delegate.boundSql.parameterObject.totalPage")));
        // page.setPaging(obj.getValue("delegate.boundSql.parameterObject.paging").toString().equals("true") ? true : false);
        return page;
    }
    
    /**
     * 给分页属性赋值
     *
     * @author hermit
     * @date 2014年11月10日
     * @param obj
     * @param page
     */
    private void setPageValue(MetaObject obj, Page page) {
        obj.setValue("delegate.boundSql.parameterObject.page", page.getPage());
        obj.setValue("delegate.boundSql.parameterObject.pageSize", page.getPageSize());
        obj.setValue("delegate.boundSql.parameterObject.total", page.getTotal());
        obj.setValue("delegate.boundSql.parameterObject.totalPage", page.getTotalPage());
    }
    
    /**
     * 获取记录总数，并给分页属性赋值
     * 
     * @author hermit
     * @date 2017年6月27日 下午4:38:58
     * @param sql
     * @param connection
     * @param mappedStatement
     * @param boundSql
     * @param page
     */
    private void setPageParameter(String sql, Connection connection, MappedStatement mappedStatement, BoundSql boundSql, Page page, MetaObject metaObject) {
        // 获取总记录数
        // TODO: 2017/6/28 0028 hermit 修改sql获取总数：去掉 order by
        String countSql;
        Pattern orderPattern = Pattern.compile("\\sorder\\s+by\\s");
        Pattern groupbyPattern = Pattern.compile("\\sgroup\\s+by\\s");
        Pattern fromPattern = Pattern.compile("\\sfrom\\s");
        String lowerSql = sql.toLowerCase();
        Matcher orderMatch = orderPattern.matcher(lowerSql);
        Matcher groupbyMatch = groupbyPattern.matcher(lowerSql);
        Matcher fromMatch = fromPattern.matcher(lowerSql);
        int fromIndex = -1;
        int orderIndex = -1;
        int groupIndex = -1;
        if (fromMatch.find()) {
            fromIndex = fromMatch.start();
        }
        
        if (orderMatch.find()) {
            orderIndex = orderMatch.start();
        }
        
        if (groupbyMatch.find()) {
            groupIndex = groupbyMatch.start();
        }
        
        // int index = sql.lastIndexOf("order by");
        // int fromindex=sql.toLowerCase().indexOf(" from ");
        
        if (orderIndex >= 0 && groupIndex >= 0) {
            countSql = "select count(1) from (select 1 " + sql.substring(fromIndex, orderIndex) + ") as total";
        } else if (groupIndex >= 0) {
            countSql = "select count(1) from (select 1 " + sql.substring(fromIndex) + ") as total";
        } else if (orderIndex >= 0) {
            countSql = "select count(1) as total " + sql.substring(fromIndex, orderIndex);
        } else {
            countSql = "select count(1) as total " + sql.substring(fromIndex);
        }
        
        log.debug("-countSql-{}", countSql);
        
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        
        try {
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
            
            // 解决foreach参数失效问题 add by hermit 20170315
            MetaObject mo = (MetaObject)PageUtil.getValueByFieldName(boundSql, "metaParameters");
            if (mo != null) {
                PageUtil.setValueByFieldName(countBS, "metaParameters", mo);
            }
            
            // 设置参数
            setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());
            
            rs = countStmt.executeQuery();
            int totalCount = 0;
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
            
            // 设置分页属性
            page.setTotal(totalCount);
            
            // 计算总页数
            if (totalCount != 0) {
                int totalPage = totalCount / page.getPageSize();
                
                if (totalPage == 0 || totalCount % page.getPageSize() != 0) {
                    totalPage++;
                }
                
                page.setTotalPage(totalPage);
            }
            
            setPageValue(metaObject, page);
        } catch (Exception e) {
            log.error("分页参数设置异常：", e);
        } finally {
            try {
                rs.close();
                countStmt.close();
            } catch (SQLException e) {
                log.error("分页参数设置异常：", e);
            }
        }
        
    }
    
    /**
     * 对SQL参数设值
     * 
     * @author hermit
     * @date 2017年6月27日 下午4:38:51
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject)
        throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(ps);
    }
    
    /**
     * 根据数据库方言，生成特定的分页sql
     * 
     * @author hermit
     * @date 2017年6月27日 下午4:38:46
     * @param sql
     * @param page
     * @return
     */
    private String buildPageSql(String sql, Page page) {
        String newSql = "";
        if (page != null) {
            if ("mysql".equals(dialect)) {
                newSql = buildPageSqlForMysql(sql, page);
                log.debug("-newSql-{}", newSql);
            } else if ("oracle".equals(dialect)) {
                newSql = buildPageSqlForOracle(sql, page);
            } else {
                newSql = sql;
            }
        } else {
            newSql = sql;
        }
        
        return newSql;
    }
    
    /**
     * mysql分页
     * 
     * @author hermit
     * @date 2017年6月27日 下午4:37:49
     * @param sql
     * @param page
     * @return
     */
    public String buildPageSqlForMysql(String sql, Page page) {
        StringBuilder pageSql = new StringBuilder(100);
        String beginRow = String.valueOf((page.getPage() - 1) * page.getPageSize());
        pageSql.append(sql);
        pageSql.append(" limit ").append(beginRow).append(",").append(page.getPageSize());
        return pageSql.toString();
    }
    
    /**
     * oracle分页
     * 
     * @author hermit
     * @date 2017年6月27日 下午4:37:53
     * @param sql
     * @param page
     * @return
     */
    public String buildPageSqlForOracle(String sql, Page page) {
        StringBuilder pageSql = new StringBuilder();
        String beginRow = String.valueOf((page.getPage() - 1) * page.getPageSize());
        String endRow = String.valueOf(page.getPage() * page.getPageSize());
        
        pageSql.append("select * from ( select temp.*, rownum row_id from ( ");
        pageSql.append(sql);
        pageSql.append(" ) temp where rownum <= ").append(endRow);
        pageSql.append(") where row_id > ").append(beginRow);
        
        return pageSql.toString();
    }
    
    @Override
    public Object plugin(Object target) {
        // 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }
    
    /**
     * 设置属性值
     * 
     * @param p
     * @see
     */
    @Override
    public void setProperties(Properties p) {
        // 配置文件中的property属性
        dialect = p.getProperty("dialect");
        
        if (dialect == null || "".equals(dialect)) {
            try {
                throw new PropertyException("没有设置dialect属性");
            } catch (PropertyException e) {
                e.printStackTrace();
            }
        }
        pageSqlId = p.getProperty("pageSqlId");
        if (pageSqlId == null || "".equals(pageSqlId)) {
            try {
                throw new PropertyException("没有设置pageSqlId属性");
            } catch (PropertyException e) {
                e.printStackTrace();
            }
        }
    }
    
}
