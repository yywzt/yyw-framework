package com.yyw.framework.datadict.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/9/9 11:18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataDictInfoVO {

    private DataDictVO dict;

    private List<DataDictDetailVO> detail;

}
