package com.tml.uaa.service;

import com.tml.common.constant.CacheConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import javax.sql.DataSource;

/**
 * @Description com.tml.uaa.service
 * @Author TuMingLong
 * @Date 2020/7/3 16:00
 */
@Slf4j
public class RedisClientDetailsService extends JdbcClientDetailsService {

    public RedisClientDetailsService(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    @Cacheable(value = CacheConstant.CLIENT_DETAILS_KEY,key="#clientId",unless = "#result==null")
    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
        return super.loadClientByClientId(clientId);
    }
}
