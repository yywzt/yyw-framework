package com.yyw.framework.datadict.model;

import com.yyw.framework.datadict.vo.DataDictVO;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/9/9 10:17
 */
@Data
public class DataDict {

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

    private Integer enableStatus;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    public DataDictVO to() {
        DataDictVO dataDictVO = new DataDictVO();
        dataDictVO.setId(this.id);
        dataDictVO.setName(this.name);
        dataDictVO.setCode(this.code);
        dataDictVO.setDescription(this.description);
        return dataDictVO;

    }
}
