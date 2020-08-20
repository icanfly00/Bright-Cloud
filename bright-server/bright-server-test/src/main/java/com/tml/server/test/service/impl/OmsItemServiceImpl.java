package com.tml.server.test.service.impl;

import com.tml.server.test.entity.OmsItem;
import com.tml.server.test.mapper.OmsItemMapper;
import com.tml.server.test.service.IOmsItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tml.common.core.entity.QueryRequest;

import java.util.Arrays;
import java.util.List;

/**
 * 商品表 Service实现
 *
 * @author JacksonTu
 * @date 2020-08-20 11:49:45
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class OmsItemServiceImpl extends ServiceImpl<OmsItemMapper, OmsItem> implements IOmsItemService {

    @Override
    public IPage<OmsItem> pageOmsItem(QueryRequest request, OmsItem omsItem) {
        LambdaQueryWrapper<OmsItem> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        queryWrapper.eq(StringUtils.isNoneBlank(omsItem.getItemName()),OmsItem::getItemName,omsItem.getItemName())
                .eq(StringUtils.isNoneBlank(omsItem.getItemCode()),OmsItem::getItemCode,omsItem.getItemCode())
                .orderByDesc(OmsItem::getCreateTime);
        Page<OmsItem> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public boolean check(String itemName, String itemCode) {
        LambdaQueryWrapper<OmsItem> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        queryWrapper.eq(StringUtils.isNoneBlank(itemName),OmsItem::getItemName,itemName)
                .eq(StringUtils.isNoneBlank(itemCode),OmsItem::getItemCode,itemCode);
       int count= this.baseMapper.selectCount(queryWrapper);
       if(count>0){
           return false;
       }
       return true;

    }

    @Override
    public List<OmsItem> listOmsItem(OmsItem omsItem) {
        LambdaQueryWrapper<OmsItem> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        queryWrapper.eq(StringUtils.isNoneBlank(omsItem.getItemName()),OmsItem::getItemName,omsItem.getItemName())
                .eq(StringUtils.isNoneBlank(omsItem.getItemCode()),OmsItem::getItemCode,omsItem.getItemCode())
                .orderByDesc(OmsItem::getCreateTime);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOmsItem(OmsItem omsItem) {
        this.save(omsItem);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOmsItem(OmsItem omsItem) {
        this.saveOrUpdate(omsItem);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOmsItem(String[] ids) {
        List<String> list = Arrays.asList(ids);
        this.removeByIds(list);
    }
}
