/*
 * FileName：IBase.java 
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
package com.wxmp.core.common;

import java.util.List;

/**
 * @param <T>
 * @author hermit
 * @date 2018 -02-23 09:44:25
 */
public interface IBase<T> {
    /**
     * 查询单条数据
     *
     * @param entity
     * @return 单个实体
     * @throws Exception
     * @author hermit
     * @date 2018 -02-23 09:44:25
     */
    T queryObject(T entity)
        throws Exception;
    
    /**
     * 查询数据集合
     *
     * @param entity
     * @return List集合
     * @throws Exception
     * @author hermit
     * @date 2018 -02-23 09:44:49
     */
    List<T> queryList(T entity)
        throws Exception;
    
    /**
     * 根据分页参数查询数据集合
     *
     * @param entity
     * @return List集合
     * @throws Exception
     * @author hermit
     * @date 2018 -02-23 09:44:49
     */
    List<T> queryListByPage(T entity)
        throws Exception;
    
    /**
     * 查询数量，无数据返回 0
     *
     * @param entity
     * @return 数量
     * @throws Exception
     * @author hermit
     * @date 2018 -02-23 09:44:49
     */
    Integer queryCount(T entity)
        throws Exception;
    
    /**
     * 新增数据
     *
     * @param entity
     * @return 新增数量
     * @throws Exception
     * @author hermit
     * @date 2018 -02-23 09:45:04
     */
    Integer insert(T entity)
        throws Exception;
    
    /**
     * 修改数据（值为NULL的不修改）
     *
     * @param entity
     * @return 修改数量
     * @throws Exception
     * @author hermit
     * @date 2018 -02-23 09:45:04
     */
    Integer update(T entity)
        throws Exception;
    
    /**
     * 删除数据
     *
     * @param id
     * @return 删除数量
     * @throws Exception
     * @author hermit
     * @date 2018 -02-23 09:45:05
     */
    Integer delete(String id)
        throws Exception;

}
