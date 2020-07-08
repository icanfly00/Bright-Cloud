package com.tml.system.service;


import com.tml.common.web.service.IBaseService;
import com.tml.system.entity.OauthClientDetails;

/**
 * @Description OAuth2.0客户端信息服务接口
 * @Author TuMingLong
 * @Date 2020/5/10 16:48
 */
public interface IOauthClientDetailsService extends IBaseService<OauthClientDetails> {


    /**
     * 根据主键查询
     *
     * @param clientId clientId
     * @return OauthClientDetails
     */
    OauthClientDetails findById(String clientId);

    /**
     * 新增
     *
     * @param oauthClientDetails oauthClientDetails
     */
    void createOauthClientDetails(OauthClientDetails oauthClientDetails);

    /**
     * 修改
     *
     * @param oauthClientDetails oauthClientDetails
     */
    void updateOauthClientDetails(OauthClientDetails oauthClientDetails);

    /**
     * 删除
     *
     * @param clientIds clientIds
     */
    void deleteOauthClientDetails(String clientIds);
}
