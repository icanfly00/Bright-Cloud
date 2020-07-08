package com.tml.common.datasource.configuration;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import com.google.common.collect.Lists;
import com.tml.common.datasource.interceptor.DataPermissionInterceptor;
import com.tml.common.datasource.tenant.RestTenantHandler;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description Mybatis-Plus配置
 * @Author TuMingLong
 * @Date 2020/3/28 11:15
 */
@Slf4j
@Configuration
@EnableTransactionManagement
@MapperScan(value = {"com.tml.*.mapper", "com.tml.common.log.mapper"})
public class MybatisPlusConfiguration {

    @Resource
    private RestTenantHandler restTenantHandler;

    /**
     * 注册数据权限
     */
    @Bean
    public DataPermissionInterceptor dataPermissionInterceptor() {
        log.info("-----注册数据权限-----");
        return new DataPermissionInterceptor();
    }


    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

        List<ISqlParser> sqlParserList = Lists.newArrayList();
        // 攻击 SQL 阻断解析器、加入解析链
        sqlParserList.add(new BlockAttackSqlParser());
        // 多租户拦截
        TenantSqlParser tenantSqlParser = new TenantSqlParser();
        tenantSqlParser.setTenantHandler(restTenantHandler);
        sqlParserList.add(tenantSqlParser);
        paginationInterceptor.setSqlParserList(sqlParserList);
        return paginationInterceptor;
    }
}
