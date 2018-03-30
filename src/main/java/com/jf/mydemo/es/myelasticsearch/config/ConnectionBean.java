package com.jf.mydemo.es.myelasticsearch.config;

import java.io.Serializable;

/**
 * 配置文件数据库部分
 * Created by js on 2017/9/5.
 */
public class ConnectionBean implements Serializable{
    /**
     * 项目名称
     */
    private String name;
    /**
     * 驱动类型
     */
    private String providerName;
    /**
     * 连接字符串
     */
    private String connectionString;
    /**
     * 数据库名称
     */
    private String databaseName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
}
