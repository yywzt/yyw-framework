package com.yyw.framework.datadict.dto;

import lombok.Data;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/9/9 10:21
 */
@Data
public class DataDictDetailDTO {

    private Long id;

    private Long dictId;

    /**
     * 字典标签
     */
    private String label;

    /**
     * 字典值
     */
    private String value;
}
