package com.tml.system.service;

import com.tml.common.web.vo.TreeVo;
import com.tml.common.web.service.IBaseService;
import com.tml.system.dto.DeptDto;
import com.tml.system.entity.SysDept;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 部门管理 服务类
 * @Author TuMingLong
 * @Date 2020/4/5 17:26
 */
public interface ISysDeptService extends IBaseService<SysDept> {
    /**
     * 查询部门信息
     *
     * @return
     */
    List<SysDept> selectDeptList();

    /**
     * 更新部门
     *
     * @param deptDto
     * @return
     */
    boolean updateDeptById(DeptDto deptDto);

    /**
     * 删除部门
     *
     * @param id
     * @return
     */
    boolean removeDeptById(Serializable id);

    /**
     * 根据部门id部门名称
     *
     * @param deptId
     * @return
     */
    String selectDeptNameByDeptId(int deptId);

    /**
     * 根据部门名称查询部门信息
     *
     * @param deptName
     * @return
     */
    List<SysDept> selectDeptListByDeptName(String deptName);

    /**
     * 通过此部门id查询于此相关的部门ids
     *
     * @param deptId
     * @return
     */
    List<Integer> selectDeptIds(int deptId);

    /**
     * 获取部门树
     *
     * @return
     */
    List<TreeVo> getDeptTree();
}
