package com.yyw.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2021/3/16 17:03
 */
@Data
public class PageInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer page;
    private Integer size;
}