package com.yyw.api.enums;

import lombok.AllArgsConstructor;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/8/16 17:34
 */
@AllArgsConstructor
public enum EnableStatusEnum {

    /**
     * 有效
     */
    DELETE_ENABLE_STATUS(-1, "删除"),
    DIS_ENABLE_STATUS(0, "无效"),
    ENABLE_STATUS(1, "有效");

    private final Integer code;
    private final String name;

    public static EnableStatusEnum getByCode(Integer code) {
        if(code == null){
            return null;
        }
        for (EnableStatusEnum enableStatusEnum : EnableStatusEnum.values()) {
            if (enableStatusEnum.getCode().equals(code)) {
                return enableStatusEnum;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}