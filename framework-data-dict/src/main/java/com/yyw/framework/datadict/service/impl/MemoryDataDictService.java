package com.yyw.framework.datadict.service.impl;

import com.yyw.api.enums.EnableStatusEnum;
import com.yyw.api.exception.BusinessException;
import com.yyw.api.model.ResponseCode;
import com.yyw.framework.datadict.dto.DataDictDTO;
import com.yyw.framework.datadict.dto.DataDictDetailDTO;
import com.yyw.framework.datadict.model.DataDict;
import com.yyw.framework.datadict.model.DataDictDetail;
import com.yyw.framework.datadict.service.DataDictAdminService;
import com.yyw.framework.datadict.service.DataDictService;
import com.yyw.framework.datadict.vo.DataDictDetailVO;
import com.yyw.framework.datadict.vo.DataDictInfoVO;
import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 内存数据字典
 *
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/9/9 10:40
 */
public class MemoryDataDictService implements DataDictService, DataDictAdminService {

    private static final List<DataDict> DATA_DICT = new CopyOnWriteArrayList<>();
    private static final List<DataDictDetail> DATA_DICT_DETAIL = new CopyOnWriteArrayList<>();

    private static final AtomicLong DATA_DICT_ID_ATOMIC = new AtomicLong();
    private static final AtomicLong DATA_DICT_DETAIL_ID_ATOMIC = new AtomicLong();
    public static final long DATA_DICT_ID_INCREMENT = 1L;
    public static final long DATA_DICT_DETAIL_ID_INCREMENT = 1L;

    public Optional<DataDict> getById(Long id) {
        return DATA_DICT.stream()
                .filter(dataDict -> Objects.equals(dataDict.getId(), id))
                .findFirst();
    }

    public DataDict checkDataDict(Long id) {
        return getById(id)
                .orElseThrow(() -> new BusinessException(ResponseCode.SELECT_ONE_EXCEPTION));
    }

    public Optional<DataDictDetail> getDataDictDetailById(Long id) {
        return DATA_DICT_DETAIL.stream()
                .filter(dataDictDetail -> Objects.equals(dataDictDetail.getId(), id))
                .findFirst();
    }

    public DataDictDetail checkDataDictDetail(Long id) {
        return getDataDictDetailById(id)
                .orElseThrow(() -> new BusinessException(ResponseCode.SELECT_ONE_EXCEPTION));
    }

    @Override
    public DataDict addDataDict(@NonNull DataDictDTO dataDict) {
        Optional<DataDict> dictOpt = DATA_DICT.stream()
                .filter(d -> Objects.equals(dataDict.getCode(), d.getCode()))
                .findFirst();
        if (dictOpt.isPresent()) {
            return dictOpt.get();
        }
        DataDict dict = new DataDict();
        dict.setId(DATA_DICT_ID_ATOMIC.addAndGet(DATA_DICT_ID_INCREMENT));
        dict.setName(dataDict.getName());
        dict.setCode(dataDict.getCode());
        dict.setDescription(dataDict.getDescription());
        dict.setEnableStatus(EnableStatusEnum.DIS_ENABLE_STATUS.getCode());
        dict.setCreateDate(LocalDateTime.now());
        dict.setModifyDate(LocalDateTime.now());
        DATA_DICT.add(dict);
        return dict;
    }

    @Override
    public Collection<DataDict> addDataDict(@NonNull Collection<DataDictDTO> dataDicts) {
        if (CollectionUtils.isEmpty(dataDicts)) {
            return Collections.emptyList();
        }
        return dataDicts.stream()
                .map(this::addDataDict)
                .collect(Collectors.toList());
    }

    @Override
    public DataDict updateDataDict(@NonNull DataDictDTO dataDict) {
        if (Objects.isNull(dataDict.getId())) {
            return null;
        }
        DataDict dict = checkDataDict(dataDict.getId());
        dict.setName(dataDict.getName());
        dict.setDescription(dataDict.getDescription());
        dict.setModifyDate(LocalDateTime.now());
        return dict;
    }

    @Override
    public Collection<DataDict> updateDataDict(@NonNull Collection<DataDictDTO> dataDicts) {
        if (CollectionUtils.isEmpty(dataDicts)) {
            return Collections.emptyList();
        }
        return dataDicts.stream()
                .map(this::updateDataDict)
                .collect(Collectors.toList());
    }

    @Override
    public void toggleDataDictStatus(@NonNull Long dictId) {
        DataDict dataDict = checkDataDict(dictId);
        if (Objects.equals(EnableStatusEnum.ENABLE_STATUS.getCode(), dataDict.getEnableStatus())) {
            dataDict.setEnableStatus(EnableStatusEnum.DIS_ENABLE_STATUS.getCode());
            return;
        }
        if (Objects.equals(EnableStatusEnum.DIS_ENABLE_STATUS.getCode(), dataDict.getEnableStatus())) {
            dataDict.setEnableStatus(EnableStatusEnum.ENABLE_STATUS.getCode());
        }
    }

    @Override
    public void toggleDataDictStatus(@NonNull Collection<Long> dictIds) {
        dictIds.forEach(this::toggleDataDictDetailStatus);
    }

    @Override
    public void removeDataDictStatus(@NonNull Long dictId) {
        DATA_DICT.removeIf(dataDict -> Objects.equals(dictId, dataDict.getId()));
    }

    @Override
    public void removeDataDictStatus(@NonNull Collection<Long> dictIds) {
        DATA_DICT.removeIf(dataDict -> dictIds.contains(dataDict.getId()));
    }

    @Override
    public DataDictDetail addDataDictDetail(@NonNull DataDictDetailDTO dataDictDetail) {
        Optional<DataDictDetail> dictDetailOpt = DATA_DICT_DETAIL.stream()
                .filter(d -> Objects.equals(d.getLabel(), dataDictDetail.getLabel()))
                .filter(d -> Objects.equals(d.getDictId(), dataDictDetail.getDictId()))
                .findFirst();
        if (dictDetailOpt.isPresent()) {
            return dictDetailOpt.get();
        }
        DataDictDetail dictDetail = new DataDictDetail();
        dictDetail.setId(DATA_DICT_DETAIL_ID_ATOMIC.addAndGet(DATA_DICT_DETAIL_ID_INCREMENT));
        dictDetail.setDictId(dataDictDetail.getDictId());
        dictDetail.setLabel(dataDictDetail.getLabel());
        dictDetail.setValue(dataDictDetail.getValue());
        dictDetail.setEnableStatus(EnableStatusEnum.DIS_ENABLE_STATUS.getCode());
        dictDetail.setCreateDate(LocalDateTime.now());
        dictDetail.setModifyDate(LocalDateTime.now());
        DATA_DICT_DETAIL.add(dictDetail);
        return dictDetail;
    }

    @Override
    public Collection<DataDictDetail> addDataDictDetail(@NonNull Collection<DataDictDetailDTO> dataDictDetails) {
        if (CollectionUtils.isEmpty(dataDictDetails)) {
            return Collections.emptyList();
        }
        return dataDictDetails.stream()
                .map(this::addDataDictDetail)
                .collect(Collectors.toList());
    }

    @Override
    public DataDictDetail updateDataDictDetail(@NonNull DataDictDetailDTO dataDictDetail) {
        if (Objects.isNull(dataDictDetail.getId())) {
            return null;
        }
        Optional<DataDictDetail> dataDictDetailOptional = DATA_DICT_DETAIL.stream()
                .filter(d -> Objects.equals(d.getLabel(), dataDictDetail.getLabel()))
                .filter(d -> !Objects.equals(d.getId(), dataDictDetail.getId()))
                .findFirst();
        if (dataDictDetailOptional.isPresent()) {
            throw new BusinessException(ResponseCode.UPDATE_EXCEPTION);
        }
        DataDictDetail currentDataDictDetail = checkDataDictDetail(dataDictDetail.getId());
        currentDataDictDetail.setLabel(dataDictDetail.getLabel());
        currentDataDictDetail.setValue(dataDictDetail.getValue());
        currentDataDictDetail.setModifyDate(LocalDateTime.now());
        return currentDataDictDetail;

    }

    @Override
    public Collection<DataDictDetail> updateDataDictDetail(@NonNull Collection<DataDictDetailDTO> dataDictDetails) {
        if (CollectionUtils.isEmpty(dataDictDetails)) {
            return Collections.emptyList();
        }
        return dataDictDetails.stream()
                .map(this::updateDataDictDetail)
                .collect(Collectors.toList());
    }

    @Override
    public void toggleDataDictDetailStatus(@NonNull Long dictDetailId) {
        DataDictDetail dataDictDetail = checkDataDictDetail(dictDetailId);
        if (Objects.equals(EnableStatusEnum.ENABLE_STATUS.getCode(), dataDictDetail.getEnableStatus())) {
            dataDictDetail.setEnableStatus(EnableStatusEnum.DIS_ENABLE_STATUS.getCode());
            return;
        }
        if (Objects.equals(EnableStatusEnum.DIS_ENABLE_STATUS.getCode(), dataDictDetail.getEnableStatus())) {
            dataDictDetail.setEnableStatus(EnableStatusEnum.ENABLE_STATUS.getCode());
        }
    }

    @Override
    public void toggleDataDictDetailStatus(@NonNull Collection<Long> dictDetailIds) {
        dictDetailIds.forEach(this::toggleDataDictDetailStatus);
    }

    @Override
    public void removeDataDictDetailStatus(@NonNull Long dictDetailId) {
        DATA_DICT_DETAIL.removeIf(dataDictDetail -> Objects.equals(dictDetailId, dataDictDetail.getId()));
    }

    @Override
    public void removeDataDictDetailStatus(@NonNull Collection<Long> dictDetailIds) {
        DATA_DICT_DETAIL.removeIf(dataDictDetail -> dictDetailIds.contains(dataDictDetail.getId()));
    }

    private Optional<DataDict> getDictByCode(String code) {
        return DATA_DICT.stream()
                .filter(dataDict -> Objects.equals(dataDict.getCode(), code))
                .findFirst();
    }

    private List<DataDictDetail> getDictDetailByDict(@NonNull DataDict dict) {
        if (Objects.isNull(dict.getId())) {
            return Collections.emptyList();
        }
        return DATA_DICT_DETAIL.stream()
                .filter(dataDictDetail -> Objects.equals(dataDictDetail.getDictId(), dict.getId()))
                .collect(Collectors.toList());
    }

    private Optional<DataDictDetail> getDictDetailByDict(@NonNull String code, @NonNull String label) {
        Optional<DataDict> dictOptional = getDictByCode(code);
        if (!dictOptional.isPresent()) {
            return Optional.empty();
        }
        return getDictDetailByDict(dictOptional.get(), label);
    }

    private Optional<DataDictDetail> getDictDetailByDict(@NonNull DataDict dict, @NonNull String label) {
        if (Objects.isNull(dict.getId())) {
            return Optional.empty();
        }
        return DATA_DICT_DETAIL.stream()
                .filter(dataDictDetail -> Objects.equals(dataDictDetail.getDictId(), dict.getId()))
                .filter(dataDictDetail -> Objects.equals(dataDictDetail.getLabel(), label))
                .findFirst();
    }

    @Override
    public DataDictInfoVO getDataDict(String code) {
        Optional<DataDict> dictOptional = getDictByCode(code);
        if (!dictOptional.isPresent()) {
            return null;
        }
        DataDict dict = dictOptional.get();
        List<DataDictDetailVO> dictDetails = getDictDetailByDict(dict).stream()
                .map(DataDictDetail::to)
                .collect(Collectors.toList());
        return DataDictInfoVO.builder()
                .dict(dict.to())
                .detail(dictDetails)
                .build();
    }

    @Override
    public List<DataDictInfoVO> getDataDict(String... code) {
        return Stream.of(code)
                .map(this::getDataDict)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<DataDictDetailVO> getDataDictDetail(String code) {
        Optional<DataDict> dictOptional = getDictByCode(code);
        if (!dictOptional.isPresent()) {
            return Collections.emptyList();
        }
        List<DataDictDetail> dictDetails = getDictDetailByDict(dictOptional.get());
        return dictDetails.stream()
                .map(DataDictDetail::to)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, List<DataDictDetailVO>> getDataDictDetail(String... code) {
        return Stream.of(code)
                .collect(Collectors.toMap(c -> c, this::getDataDictDetail));
    }

    @Override
    public DataDictDetailVO getDataDictDetail(String code, String label) {
        return getDictDetailByDict(code, label)
                .map(DataDictDetail::to)
                .orElse(null);
    }

    @Override
    public String getDataDictValue(String code, String label) {
        return getDictDetailByDict(code, label)
                .map(DataDictDetail::getValue)
                .orElse(null);
    }
}
