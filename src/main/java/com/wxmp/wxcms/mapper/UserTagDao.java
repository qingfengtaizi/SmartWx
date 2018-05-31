/**
 * Copyright &copy; 2017-2018 <a href="http://www.webcsn.com">webcsn</a> All rights reserved.
 *
 * @author hermit
 * @date 2018-04-17 10:54:58
 */
package com.wxmp.wxcms.mapper;

import java.util.List;
import com.wxmp.wxcms.domain.UserTag;

/**
 *
 * @author fuzi Kong
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
public interface UserTagDao {

	public UserTag getById(Integer id);

	public List<UserTag> getUserTagListByPage(UserTag searchEntity);

	public void add(UserTag entity);
	
	public void addList(List<UserTag> list);

	public void update(UserTag entity);

	public void delete(UserTag entity);
	/**
     * <p>
     * 删除（根据ID 批量删除）
     * </p>
     * @param ids 主键ID列表 
     * @return int
     */
    Integer deleteBatchIds(String [] ids);
    /**
     * 获取最大的ID和本地库存的比较决定是否同步
     * @return
     */
    public Integer getMaxId();
}