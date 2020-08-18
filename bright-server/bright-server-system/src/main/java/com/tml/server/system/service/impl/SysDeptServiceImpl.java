package com.tml.server.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tml.api.system.entity.DeptTree;
import com.tml.api.system.entity.SysDept;
import com.tml.common.core.entity.QueryRequest;
import com.tml.common.core.entity.TreeNode;
import com.tml.common.core.entity.constant.BrightConstant;
import com.tml.common.core.entity.constant.PageConstant;
import com.tml.common.core.utils.SortUtil;
import com.tml.common.core.utils.TreeUtil;
import com.tml.server.system.mapper.SysDeptMapper;
import com.tml.server.system.service.ISysDeptService;
import com.tml.server.system.service.ISysUserDataPermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author JacksonTu
 * @version 1.0
 * @description 部门业务层实现
 * @since 2020-08-10 20:30
 */
@Slf4j
@Service("deptService")
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

    private final ISysUserDataPermissionService userDataPermissionService;

    @Override
    public Map<String, Object> findDepts(QueryRequest request, SysDept dept) {
        Map<String, Object> result = new HashMap<>(2);
        try {
            List<SysDept> depts = findDepts(dept, request);
            List<DeptTree> trees = new ArrayList<>();
            buildTrees(trees, depts);
            List<? extends TreeNode<?>> deptTree = TreeUtil.build(trees);

            result.put(PageConstant.ROWS, deptTree);
            result.put(PageConstant.TOTAL, depts.size());
        } catch (Exception e) {
            log.error("获取部门列表失败", e);
            result.put(PageConstant.ROWS, null);
            result.put(PageConstant.TOTAL, 0);
        }
        return result;
    }

    @Override
    public List<SysDept> findDepts(SysDept dept, QueryRequest request) {
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(dept.getDeptName())) {
            queryWrapper.lambda().like(SysDept::getDeptName, dept.getDeptName());
        }
        if (StringUtils.isNotBlank(dept.getCreateTimeFrom()) && StringUtils.isNotBlank(dept.getCreateTimeTo())) {
            queryWrapper.lambda()
                    .ge(SysDept::getCreateTime, dept.getCreateTimeFrom())
                    .le(SysDept::getCreateTime, dept.getCreateTimeTo());
        }
        SortUtil.handleWrapperSort(request, queryWrapper, "orderNum", BrightConstant.ORDER_ASC, true);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createDept(SysDept dept) {
        if (dept.getParentId() == null) {
            dept.setParentId(SysDept.TOP_DEPT_ID);
        }
        dept.setCreateTime(new Date());
        this.save(dept);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDept(SysDept dept) {
        if (dept.getParentId() == null) {
            dept.setParentId(SysDept.TOP_DEPT_ID);
        }
        dept.setModifyTime(new Date());
        this.baseMapper.updateById(dept);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDepts(String[] deptIds) {
        this.delete(Arrays.asList(deptIds));
    }

    private void buildTrees(List<DeptTree> trees, List<SysDept> depts) {
        depts.forEach(dept -> {
            DeptTree tree = new DeptTree();
            tree.setId(dept.getDeptId().toString());
            tree.setParentId(dept.getParentId().toString());
            tree.setLabel(dept.getDeptName());
            tree.setOrderNum(dept.getOrderNum());
            trees.add(tree);
        });
    }

    private void delete(List<String> deptIds) {
        removeByIds(deptIds);
        userDataPermissionService.deleteByDeptIds(deptIds);

        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysDept::getParentId, deptIds);
        List<SysDept> depts = baseMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(depts)) {
            List<String> deptIdList = new ArrayList<>();
            depts.forEach(d -> deptIdList.add(String.valueOf(d.getDeptId())));
            this.delete(deptIdList);
        }
    }

}
