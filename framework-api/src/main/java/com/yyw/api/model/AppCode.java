package com.yyw.api.model;

import java.util.Objects;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2021/4/14 18:06
 */
public interface AppCode {
    String toString();

    String getMessage();

    String getCode();

    default boolean isSuccess() {
        return Objects.equals(this.getCode(), 0);
    }
}
