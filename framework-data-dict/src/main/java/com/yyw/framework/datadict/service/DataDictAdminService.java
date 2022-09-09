package com.yyw.framework.datadict.service;

import com.yyw.framework.datadict.dto.DataDictDTO;
import com.yyw.framework.datadict.dto.DataDictDetailDTO;
import com.yyw.framework.datadict.model.DataDict;
import com.yyw.framework.datadict.model.DataDictDetail;
import lombok.NonNull;

import java.util.Collection;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/9/9 10:21
 */
public interface DataDictAdminService {

    /**
     * 添加字典数据
     *
     * @param dataDict 字典数据
     * @return 新增完成的字典数据
     */
    DataDict addDataDict(@NonNull DataDictDTO dataDict);

    /**
     * 批量添加字典数据
     *
     * @param dataDicts 字典数据集合
     * @return 新增完成的字典数据集合
     */
    Collection<DataDict> addDataDict(@NonNull Collection<DataDictDTO> dataDicts);

    /**
     * 修改字典数据
     *
     * @param dataDict 字典数据
     * @return 修改完成的字典数据
     */
    DataDict updateDataDict(@NonNull DataDictDTO dataDict);

    /**
     * 批量修改字典数据
     *
     * @param dataDicts 字典数据集合
     * @return 修改完成的字典数据集合
     */
    Collection<DataDict> updateDataDict(@NonNull Collection<DataDictDTO> dataDicts);

    /**
     * 上下架字典
     *
     * @param dictId ID
     */
    void toggleDataDictStatus(@NonNull Long dictId);

    /**
     * 批量上下架字典
     *
     * @param dictIds ID
     */
    void toggleDataDictStatus(@NonNull Collection<Long> dictIds);

    /**
     * 删除字典
     *
     * @param dictId ID
     */
    void removeDataDictStatus(@NonNull Long dictId);

    /**
     * 批量删除字典
     *
     * @param dictIds ID
     */
    void removeDataDictStatus(@NonNull Collection<Long> dictIds);

    /**
     * 添加字典数据详情
     *
     * @param dataDictDetail 字典数据详情
     * @return 新增完成的字典数据详情
     */
    DataDictDetail addDataDictDetail(@NonNull DataDictDetailDTO dataDictDetail);

    /**
     * 批量添加字典数据详情
     *
     * @param dataDictDetails 字典数据详情集合
     * @return 新增完成的字典数据详情集合
     */

    Collection<DataDictDetail> addDataDictDetail(@NonNull Collection<DataDictDetailDTO> dataDictDetails);

    /**
     * 修改字典数据详情
     *
     * @param dataDictDetail 字典数据详情
     * @return 修改完成的字典数据详情
     */
    DataDictDetail updateDataDictDetail(@NonNull DataDictDetailDTO dataDictDetail);

    /**
     * 批量修改字典数据详情
     *
     * @param dataDictDetails 字典数据详情集合
     * @return 修改完成的字典数据集合
     */
    Collection<DataDictDetail> updateDataDictDetail(@NonNull Collection<DataDictDetailDTO> dataDictDetails);

    /**
     * 上下架字典详情
     *
     * @param dictDetailId ID
     */
    void toggleDataDictDetailStatus(@NonNull Long dictDetailId);

    /**
     * 批量上下架字典详情
     *
     * @param dictDetailIds ID
     */
    void toggleDataDictDetailStatus(@NonNull Collection<Long> dictDetailIds);

    /**
     * 删除字典详情
     *
     * @param dictDetailId ID
     */
    void removeDataDictDetailStatus(@NonNull Long dictDetailId);

    /**
     * 批量删除字典详情
     *
     * @param dictDetailIds ID
     */
    void removeDataDictDetailStatus(@NonNull Collection<Long> dictDetailIds);
}
