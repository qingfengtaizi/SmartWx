package com.wxmp.core.page;

import com.wxmp.wxapi.process.WxMemoryCacheClient;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页参数实体类
 *
 * @author hermit
 * @date 2017 -06-28 19:51:23
 */
@Data
public class Page implements Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    
    private int page = 1;// 当前页索引
    
    private int pageSize = 20;// 每页显示的数据条数
    
    private int total = 0;// 总条数
    
    private int totalPage = 1;// 总页数
    
    // private String sort;// 排序字段
    //
    // private String order;// 排序方式 ASC DESC

    private String account = WxMemoryCacheClient.getAccount();
    
}
