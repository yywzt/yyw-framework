package com.yyw.framework.datadict.model;

import com.yyw.framework.datadict.vo.DataDictDetailVO;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2022/9/9 10:17
 */
@Data
public class DataDictDetail {

    private Long id;

    private Long dictId;

    /**
     * 字典标签
     */
    private String label;

    /**
     * 字典值
     */
    private String value;

    private Integer enableStatus;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    public DataDictDetailVO to() {
        DataDictDetailVO dataDictDetailVO = new DataDictDetailVO();
        dataDictDetailVO.setId(this.id);
        dataDictDetailVO.setLabel(this.label);
        dataDictDetailVO.setValue(this.value);
        return dataDictDetailVO;
    }
}
