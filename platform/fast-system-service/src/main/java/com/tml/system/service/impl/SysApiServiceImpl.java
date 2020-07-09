package com.tml.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.tml.common.constant.CommonConstant;
import com.tml.common.exception.AlertException;
import com.tml.common.web.service.impl.BaseServiceImpl;
import com.tml.system.entity.SysApi;
import com.tml.system.mapper.SysApiMapper;
import com.tml.system.service.ISysApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * @Description 系统API服务类接口实现
 * @Author TuMingLong
 * @Date 2020/5/10 17:01
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysApiServiceImpl extends BaseServiceImpl<SysApiMapper, SysApi> implements ISysApiService {


    /**
     * 查询列表
     *
     * @return
     */
    @Override
    public List<SysApi> findList(String serviceId) {
        QueryWrapper<SysApi> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(ObjectUtils.isNotEmpty(serviceId), SysApi::getServiceId, serviceId);
        List<SysApi> list = this.list(queryWrapper);
        return list;
    }

    /**
     * 根据主键获取接口
     *
     * @param apiId
     * @return
     */
    @Override
    public SysApi getApi(Long apiId) {
        return this.getById(apiId);
    }


    /**
     * 检查接口编码是否存在
     *
     * @param apiCode
     * @return
     */
    @Override
    public Boolean isExist(String apiCode) {
        QueryWrapper<SysApi> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SysApi::getApiCode, apiCode);
        int count = this.baseMapper.selectCount(queryWrapper);
        return count > 0 ? true : false;
    }

    /**
     * 添加接口
     *
     * @param api
     * @return
     */
    @Override
    public void addApi(SysApi api) {
        if (isExist(api.getApiCode())) {
            throw new AlertException(String.format("%s编码已存在!", api.getApiCode()));
        }
        if (api.getPriority() == null) {
            api.setPriority(0);
        }
        if (api.getStatus() == null) {
            api.setStatus(CommonConstant.ENABLED);
        }
        if (api.getApiCategory() == null) {
            api.setApiCategory(CommonConstant.DEFAULT_API_CATEGORY);
        }
        if (api.getIsPersist() == null) {
            api.setIsPersist(0);
        }
        if (api.getIsAuth() == null) {
            api.setIsAuth(0);
        }
        api.setCreateTime(new Date());
        api.setUpdateTime(api.getCreateTime());
        this.save(api);
        // 同步权限表里的信息
    }

    /**
     * 修改接口
     *
     * @param api
     * @return
     */
    @Override
    public void updateApi(SysApi api) {
        SysApi saved = getApi(api.getApiId());
        if (saved == null) {
            throw new AlertException("信息不存在!");
        }
        if (!saved.getApiCode().equals(api.getApiCode())) {
            // 和原来不一致重新检查唯一性
            if (isExist(api.getApiCode())) {
                throw new AlertException(String.format("%s编码已存在!", api.getApiCode()));
            }
        }
        if (api.getPriority() == null) {
            api.setPriority(0);
        }
        if (api.getApiCategory() == null) {
            api.setApiCategory(CommonConstant.DEFAULT_API_CATEGORY);
        }
        api.setUpdateTime(new Date());
        this.updateById(api);
        // 同步权限表里的信息
    }

    /**
     * 查询接口
     *
     * @param apiCode
     * @return
     */
    @Override
    public SysApi getApi(String apiCode) {
        QueryWrapper<SysApi> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SysApi::getApiCode, apiCode);
        return this.baseMapper.selectOne(queryWrapper);
    }


    /**
     * 移除接口
     *
     * @param apiId
     * @return
     */
    @Override
    public void removeApi(Long apiId) {
        SysApi api = getApi(apiId);
        if (api != null && api.getIsPersist().equals(CommonConstant.ENABLED)) {
            throw new AlertException(String.format("保留数据,不允许删除"));
        }
        this.baseMapper.deleteById(apiId);
    }
}
