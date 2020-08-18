package com.tml.server.generator.service;

import com.tml.common.core.entity.QueryRequest;
import com.tml.server.generator.entity.Column;
import com.tml.server.generator.entity.Table;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @description 
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
public interface IGeneratorService {

    /**
     * 获取数据库列表
     *
     * @param databaseType databaseType
     * @return 数据库列表
     */
    List<String> getDatabases(String databaseType);

    /**
     * 获取数据表
     *
     * @param tableName    tableName
     * @param request      request
     * @param databaseType databaseType
     * @param schemaName   schemaName
     * @return 数据表分页数据
     */
    IPage<Table> getTables(String tableName, QueryRequest request, String databaseType, String schemaName);

    /**
     * 获取数据表列信息
     *
     * @param databaseType databaseType
     * @param schemaName   schemaName
     * @param tableName    tableName
     * @return 数据表列信息
     */
    List<Column> getColumns(String databaseType, String schemaName, String tableName);

}
