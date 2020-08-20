package com.tml.server.test.service;

import com.tml.server.test.entity.OmsItem;

import com.tml.common.core.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 商品表 Service接口
 *
 * @author JacksonTu
 * @date 2020-08-20 11:49:45
 */
public interface IOmsItemService extends IService<OmsItem> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param omsItem omsItem
     * @return IPage<OmsItem>
     */
    IPage<OmsItem> pageOmsItem(QueryRequest request, OmsItem omsItem);

    /**
     * 检查
     * @param itemName
     * @param itemCode
     * @return
     */
    boolean check(String itemName,String itemCode);

    /**
     * 查询（所有）
     *
     * @param omsItem omsItem
     * @return List<OmsItem>
     */
    List<OmsItem> listOmsItem(OmsItem omsItem);

    /**
     * 新增
     *
     * @param omsItem omsItem
     */
    void saveOmsItem(OmsItem omsItem);

    /**
     * 修改
     *
     * @param omsItem omsItem
     */
    void updateOmsItem(OmsItem omsItem);

    /**
     * 删除
     *
     * @param ids
     */
    void deleteOmsItem(String[] ids);
}
