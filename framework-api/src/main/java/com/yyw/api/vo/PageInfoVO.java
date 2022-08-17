package com.yyw.api.vo;

import lombok.Data;

import java.util.List;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2021/3/16 16:55
 */
@Data
public class PageInfoVO<T> {

    /**
     * 默认展示10条记录
     */
    private Integer pageSize;
    /**
     * 总记录数
     */
    private Integer totals;
    /**
     * 总页数
     */
    private Integer totalPages;
    /**
     * 当前页数
     */
    private Integer currentPage;
    /**
     * 当前页的数据
     */
    private List<T> list;

}
