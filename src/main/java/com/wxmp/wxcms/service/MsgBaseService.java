package com.wxmp.wxcms.service;

import java.util.List;

import com.wxmp.wxcms.domain.MsgBase;

/**
 * @author : hermit
 */
public interface MsgBaseService {

	public MsgBase getById(String id);

	public List<MsgBase> listForPage(MsgBase searchEntity);

	public void add(MsgBase entity);

	public void update(MsgBase entity);

	public void delete(MsgBase entity);



}