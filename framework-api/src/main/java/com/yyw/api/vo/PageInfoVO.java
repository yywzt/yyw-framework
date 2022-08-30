package com.yyw.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2021/3/16 16:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public static <T> PageInfoVO<T> empty(int pageNum, int pageSize) {
        return new PageInfoVO<>(pageSize, 0, 0, pageNum, new ArrayList<>());
    }

    public <K> PageInfoVO<K> build(List<K> list) {
        return new PageInfoVO<>(this.pageSize, this.totals, this.totalPages, this.currentPage, list);
    }

}
