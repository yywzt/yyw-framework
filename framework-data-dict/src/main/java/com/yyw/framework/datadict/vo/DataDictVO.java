package com.yyw.framework.datadict.vo;

import lombok.Data;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/9/9 10:21
 */
@Data
public class DataDictVO {

    private Long id;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 编码
     * 唯一
     */
    private String code;

    /**
     * 字典描述
     */
    private String description;

}
