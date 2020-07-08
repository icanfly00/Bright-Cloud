package com.tml.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Description 部门DTO
 * @Author TuMingLong
 * @Date 2020/4/5 17:28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DeptDto implements Serializable {

    private Integer deptId;

    /**
     * 部门名称
     */
    private String name;


    /**
     * 上级部门
     */
    private Integer parentId;

    /**
     * 排序
     */
    private Integer sort;
}
