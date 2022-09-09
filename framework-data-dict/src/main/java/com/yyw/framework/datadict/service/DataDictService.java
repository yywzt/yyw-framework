package com.yyw.framework.datadict.service;

import com.yyw.framework.datadict.vo.DataDictDetailVO;
import com.yyw.framework.datadict.vo.DataDictInfoVO;

import java.util.List;
import java.util.Map;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/9/9 10:21
 */
public interface DataDictService {

    /**
     * 获取字典数据
     *
     * @param code 字典编码
     * @return 字典
     */
    DataDictInfoVO getDataDict(String code);

    /**
     * 批量获取字典数据
     *
     * @param code 字典编码
     * @return 字典
     */
    List<DataDictInfoVO> getDataDict(String... code);

    /**
     * 获取字典详情数据
     *
     * @param code 字典编码
     * @return 字典详情列表
     */
    List<DataDictDetailVO> getDataDictDetail(String code);

    /**
     * 获取字典详情数据
     *
     * @param code 字典编码
     * @return Map<字典编码, 字典详情列表>
     */
    Map<String/*字典编码*/, List<DataDictDetailVO>/*对应字典详情列表*/> getDataDictDetail(String... code);

    /**
     * 获取字典详情数据
     *
     * @param code  字典编码
     * @param label 字典标签
     * @return 字典
     */
    DataDictDetailVO getDataDictDetail(String code, String label);

    /**
     * 获取字典值
     *
     * @param code  字典编码
     * @param label 字典标签
     * @return 字典值
     */
    String getDataDictValue(String code, String label);
}
