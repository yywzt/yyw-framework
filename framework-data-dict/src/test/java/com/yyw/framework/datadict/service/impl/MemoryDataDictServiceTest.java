package com.yyw.framework.datadict.service.impl;


import com.yyw.framework.datadict.dto.DataDictDTO;
import com.yyw.framework.datadict.dto.DataDictDetailDTO;
import com.yyw.framework.datadict.model.DataDict;
import com.yyw.framework.datadict.vo.DataDictDetailVO;
import com.yyw.framework.datadict.vo.DataDictInfoVO;
import com.yyw.framework.datadict.vo.DataDictVO;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/9/9 14:55
 */
public class MemoryDataDictServiceTest {

    private void addDataDictDetail(MemoryDataDictService dataDictService, DataDict dataDict) {
        List<DataDictDetailDTO> dictDetailDTOs = new ArrayList<>();
        DataDictDetailDTO dataDictDetailDTO = new DataDictDetailDTO();
        dataDictDetailDTO.setDictId(dataDict.getId());
        dataDictDetailDTO.setLabel("AA1000");
        dataDictDetailDTO.setValue("header_path_AA1000");
        dictDetailDTOs.add(dataDictDetailDTO);

        dataDictDetailDTO = new DataDictDetailDTO();
        dataDictDetailDTO.setDictId(dataDict.getId());
        dataDictDetailDTO.setLabel("AA2000");
        dataDictDetailDTO.setValue("header_path_AA2000");
        dictDetailDTOs.add(dataDictDetailDTO);

        dataDictDetailDTO = new DataDictDetailDTO();
        dataDictDetailDTO.setDictId(dataDict.getId());
        dataDictDetailDTO.setLabel("AA3000");
        dataDictDetailDTO.setValue("header_path_AA3000");
        dictDetailDTOs.add(dataDictDetailDTO);
        dataDictService.addDataDictDetail(dictDetailDTOs);
    }

    private DataDict addDataDict(MemoryDataDictService dataDictService) {
        DataDictDTO dataDictDTO = new DataDictDTO();
        dataDictDTO.setName("默认头像");
        dataDictDTO.setCode("DEFAULT_HEADER_PATH");
        dataDictDTO.setDescription("各渠道的用户默认头像");
        return dataDictService.addDataDict(dataDictDTO);
    }

    @Test
    public void addDataDict() {
    }

    @Test
    public void testAddDataDict() {
    }

    @Test
    public void updateDataDict() {
    }

    @Test
    public void testUpdateDataDict() {
    }

    @Test
    public void toggleDictStatus() {
    }

    @Test
    public void testToggleDictStatus() {
    }

    @Test
    public void removeDictStatus() {
    }

    @Test
    public void testRemoveDictStatus() {
    }

    @Test
    public void addDataDictDetail() {
    }

    @Test
    public void testAddDataDictDetail() {
    }

    @Test
    public void updateDataDictDetail() {
    }

    @Test
    public void testUpdateDataDictDetail() {
    }

    @Test
    public void toggleDictDetailStatus() {
    }

    @Test
    public void testToggleDictDetailStatus() {
    }

    @Test
    public void removeDictDetailStatus() {
    }

    @Test
    public void testRemoveDictDetailStatus() {
    }

    @Test
    public void getDict() {
        MemoryDataDictService dataDictService = new MemoryDataDictService();
        DataDictInfoVO dataDictInfo = dataDictService.getDataDict("a");
        assertNull(dataDictInfo);

        DataDict dataDict = addDataDict(dataDictService);
        addDataDictDetail(dataDictService, dataDict);

        dataDictInfo = dataDictService.getDataDict("DEFAULT_HEADER_PATH");
        assertNotNull(dataDictInfo);
        DataDictVO dataDictVO = dataDictInfo.getDict();
        assertNotNull(dataDictVO);
        List<DataDictDetailVO> dataDictDetailVOs = dataDictInfo.getDetail();
        assertNotNull(dataDictDetailVOs);
        assertEquals(3, dataDictDetailVOs.size());
    }

    @Test
    public void testGetDict() {
    }

    @Test
    public void getDictDetail() {
    }

    @Test
    public void testGetDictDetail() {
    }

    @Test
    public void testGetDictDetail1() {
    }

    @Test
    public void getDictValue() {
    }
}