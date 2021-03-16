package com.yyw.api.model;


import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2020/2/18 17:07
 * @describe
 */
@Data
public class GenericModel<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
    private T id;
    private Date createDate;
    private Date modifyDate;
    /**
     * 状态 0 已失效 1生效
     */
    private Integer enableStatus;

}
