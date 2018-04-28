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
