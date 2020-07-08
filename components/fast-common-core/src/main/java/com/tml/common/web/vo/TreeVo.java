package com.tml.common.web.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 树VO
 * @Author TuMingLong
 * @Date 2020/4/5 17:38
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TreeVo implements Serializable {
    private int id;
    private String label;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeVo> children;
}
