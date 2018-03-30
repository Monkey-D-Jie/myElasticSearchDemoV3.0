package com.jf.mydemo.es.myelasticsearch.config;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wjie
 * @date: 2018/3/21 0021
 * @time: 15:26
 * To change this template use File | Settings | File and Templates.
 */

public class DataSourceMysql implements Serializable {
    /**
     * 数据库驱动
     */
    private String driverClassName = "com.mysql.jdbc.Driver";
    /**
     * 数据库地址
     */
    private String url;
    /**
     * 数据用户名
     */
    private String username;
    /**
     * 数据库密码
     */
    private String password;
    /**
     * 数据库最大活跃数
     */
    private Integer maxActive = 500;
    /**
     * 数据库最大空闲数
     */
    private Integer maxIdle = 2;
    /**
     * 最小空闲数
     */
    private Integer minIdle = 1;
    /**
     * 数据库最大等待时间
     */
    private Integer maxWait = 6000;

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(Integer maxActive) {
        this.maxActive = maxActive;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public Integer getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(Integer maxWait) {
        this.maxWait = maxWait;
    }
}
