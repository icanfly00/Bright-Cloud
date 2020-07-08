package com.tml.common.web.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @Description 前端分页对象
 * @Author TuMingLong
 * @Date 2020/4/1 18:08
 */
@ApiModel("分页")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVo<T> implements Serializable {

    @ApiModelProperty("总行数")
    @JSONField(name = "total")
    @JsonProperty("total")
    private Long total = 0l;

    @ApiModelProperty("数据列表")
    @JSONField(name = "records")
    @JsonProperty("records")
    private List<T> records = Collections.emptyList();

    public PageVo(IPage page) {
        this.total = page.getTotal();
        this.records = page.getRecords();
    }
}
