package com.tml.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tml.common.web.vo.TreeVo;
import com.tml.common.web.service.impl.BaseServiceImpl;
import com.tml.system.dto.DeptDto;
import com.tml.system.entity.SysDept;
import com.tml.system.mapper.SysDeptMapper;
import com.tml.system.service.ISysDeptService;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description 部门管理 服务实现类
 * @Author TuMingLong
 * @Date 2020/4/5 17:27
 */
@Service
public class SysDeptServiceImpl extends BaseServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {
    @Override
    public List<SysDept> selectDeptList() {
        List<SysDept> deptList = this.baseMapper.selectList(Wrappers.<SysDept>lambdaQuery()
                .select(SysDept::getDeptId, SysDept::getDeptName, SysDept::getParentId, SysDept::getSort, SysDept::getCreateTime, SysDept::getUpdateTime));
        List<SysDept> sysDeptList = deptList.stream()
                .filter(sysDept -> sysDept.getParentId() == 0 || ObjectUtils.isEmpty(sysDept.getParentId()))
                .peek(sysDept -> sysDept.setLevel(0))
                .collect(Collectors.toList());

        return sysDeptList;
    }

    @Override
    public boolean updateDeptById(DeptDto deptDto) {
        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(deptDto, sysDept);
        sysDept.setUpdateTime(LocalDateTime.now());
        return this.updateById(sysDept);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeDeptById(Serializable id) {
        //TODO:部门层级删除
        List<Integer> idList = this.list(Wrappers.<SysDept>lambdaQuery()
                .eq(SysDept::getParentId, id))
                .stream()
                .map(SysDept::getDeptId)
                .collect(Collectors.toList());
        //TODO:删除自己
        idList.add((Integer) id);
        return super.removeByIds(idList);
    }

    @Override
    public String selectDeptNameByDeptId(int deptId) {
        SysDept sysDept = this.baseMapper.selectOne(Wrappers.<SysDept>lambdaQuery()
                .select(SysDept::getDeptName)
                .eq(SysDept::getDeptId, deptId));
        if (!ObjectUtils.isEmpty(sysDept)) {
            return sysDept.getDeptName();
        } else {
            return null;
        }
    }

    @Override
    public List<SysDept> selectDeptListByDeptName(String deptName) {
        return this.list(Wrappers.<SysDept>lambdaQuery()
                .select(SysDept::getDeptId, SysDept::getDeptName, SysDept::getParentId, SysDept::getSort, SysDept::getCreateTime, SysDept::getUpdateTime)
                .eq(SysDept::getDeptName, deptName));
    }

    @Override
    public List<Integer> selectDeptIds(int deptId) {
        SysDept department = this.getDepartment(deptId);
        List<Integer> deptIdList = new ArrayList<>();
        if (department != null) {
            deptIdList.add(department.getDeptId());
            addDeptIdList(deptIdList, department);
        }
        return deptIdList;
    }

    @Override
    public List<TreeVo> getDeptTree() {
        List<SysDept> deptList = this.baseMapper.selectList(Wrappers.<SysDept>query()
                .select("dept_id", "name", "parent_id", "sort", "create_time", "update_time"));
        List<TreeVo> treeVoList = deptList.stream()
                .filter(sysDept -> sysDept.getParentId() == 0 || ObjectUtils.isEmpty(sysDept.getParentId()))
                .map(sysDept -> {
                    TreeVo treeVo = new TreeVo();
                    treeVo.setId(sysDept.getDeptId());
                    treeVo.setLabel(sysDept.getDeptName());
                    return treeVo;
                }).collect(Collectors.toList());

        findChildren(treeVoList, deptList);

        return treeVoList;
    }

    /**
     * 根据部门ID获取该部门及其下属部门树
     */
    private SysDept getDepartment(Integer deptId) {
        List<SysDept> departments = this.baseMapper.selectList(Wrappers.<SysDept>query().select("dept_id", "name", "parent_id", "sort", "create_time"));
        Map<Integer, SysDept> map = departments.stream().collect(
                Collectors.toMap(SysDept::getDeptId, department -> department));

        for (SysDept dept : map.values()) {
            SysDept parent = map.get(dept.getParentId());
            if (parent != null) {
                List<SysDept> children = parent.getChildren() == null ? new ArrayList<>() : parent.getChildren();
                children.add(dept);
                parent.setChildren(children);
            }
        }
        return map.get(deptId);
    }

    private void addDeptIdList(List<Integer> deptIdList, SysDept department) {
        List<SysDept> children = department.getChildren();
        if (children != null) {
            for (SysDept d : children) {
                deptIdList.add(d.getDeptId());
                addDeptIdList(deptIdList, d);
            }
        }
    }

    /**
     * 构建树
     *
     * @param treeVoList
     * @param deptList
     */
    private void findChildren(List<TreeVo> treeVoList, List<SysDept> deptList) {
        treeVoList.forEach(treeVo -> {
            List<TreeVo> children = Lists.newArrayList();
            deptList.forEach(sysDept -> {
                if (treeVo.getId() == sysDept.getParentId()) {
                    TreeVo treeVo2 = new TreeVo();
                    treeVo2.setId(sysDept.getDeptId());
                    treeVo2.setLabel(sysDept.getDeptName());
                    children.add(treeVo2);
                }
            });
            treeVo.setChildren(children);
            findChildren(children, deptList);
        });
    }
}
