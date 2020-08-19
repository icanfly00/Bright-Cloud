package com.tml.server.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.tml.api.system.entity.SysDept;
import com.tml.common.core.entity.QueryRequest;

import java.util.List;
import java.util.Map;

/**
 * @author JacksonTu
 * @version 1.0
 * @description 部门业务层
 * @since 2020-08-10 20:30
 */
public interface ISysDeptService extends IService<SysDept> {

    /**
     * 获取部门信息
     *
     * @param request request
     * @param dept    dept
     * @return 部门信息
     */
    Map<String, Object> findDepts(QueryRequest request, SysDept dept);

    /**
     * 获取部门列表
     *
     * @param dept    dept
     * @param request request
     * @return 部门列表
     */
    List<SysDept> findDepts(SysDept dept, QueryRequest request);

    /**
     * 创建部门
     *
     * @param dept dept
     */
    void createDept(SysDept dept);

    /**
     * 更新部门
     *
     * @param dept dept
     */
    void updateDept(SysDept dept);

    /**
     * 删除部门
     *
     * @param deptIds 部门id数组
     */
    void deleteDepts(String[] deptIds);
}
