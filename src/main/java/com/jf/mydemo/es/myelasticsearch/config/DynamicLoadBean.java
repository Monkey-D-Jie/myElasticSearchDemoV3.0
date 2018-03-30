package com.jf.mydemo.es.myelasticsearch.config;


import org.apache.commons.dbcp.DriverConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.apache.commons.pool.impl.GenericKeyedObjectPoolFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wjie
 * @date: 2018/3/21 0021
 * @time: 15:21
 * To change this template use File | Settings | File and Templates.
 */

@Component("dynamicLoadBean")
public class DynamicLoadBean extends AbstractRoutingDataSource implements ApplicationContextAware {
    /**
     * 记录日志信息
     */
    private Logger logger = LogManager.getLogger(DynamicLoadBean.class.getName());

    /**
     * 动态容器
     */
    private ConfigurableApplicationContext applicationContext = null;

    /**
     * 数据源静态变量池
     */
    private static Map<Object, Object> map = new HashMap<Object, Object>();

    @Autowired
    private MachineUtil machineUtil;

    /**
     * 初始ID
     */
    private String dataId;

    public DynamicLoadBean() {
    }

    public DynamicLoadBean(String dataId) {
        this.dataId = dataId;
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }

    public ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 初始化数据源
     *
     * @param id 数据源ID
     */
    public void initailizeMutiDataSource(String id) {
        if (id != null) {
            //动态设置参数
            if (DynamicLoadBean.map.get(id) == null) {
                String[] str = id.split("\\.");
                if (str.length == 2) {
                    String orgCode = str[0];
                    String dbName = str[1];
                    Object[] o = this.machineUtil.getValue("1", orgCode, dbName);
                    String url = "";
                    if (o.length != 0) {
                        url = String.valueOf(o[0]);
                    }
                    Object[] o1 = this.machineUtil.getValue("4", orgCode, dbName);
                    OrgBean orgBean;
                    String db_ip = "";
                    String db_port = "";
                    String db_uid = "";
                    String db_pwd = "";
                    if (o1.length != 0) {
                        orgBean = (OrgBean) o1[0];
                        if (orgBean != null) {
                            List<OrgSetBean> orgSetList = orgBean.getOrgSetList();
                            for (int i = 0; i < orgSetList.size(); i++) {
                                if ("db-ip".equals(orgSetList.get(i).getKey())) {
                                    db_ip = orgSetList.get(i).getValue();
                                } else if ("db-port".equals(orgSetList.get(i).getKey())) {
                                    db_port = orgSetList.get(i).getValue();
                                } else if ("db-uid".equals(orgSetList.get(i).getKey())) {
                                    db_uid = orgSetList.get(i).getValue();
                                } else if ("db-pwd".equals(orgSetList.get(i).getKey())) {
                                    db_pwd = orgSetList.get(i).getValue();
                                }
                            }
                        }
                    }
                    url = url.replace("db-ip", db_ip).replace("db-port", db_port).replace("orgCode", orgCode);
                    DataSourceMysql mysql = new DataSourceMysql();
                    mysql.setUrl(url);
                    mysql.setUsername(db_uid);
                    mysql.setPassword(db_pwd);
                    DataSource dataSource = this.createDataSource(mysql);
                    if (dataSource != null) {
                        DynamicLoadBean.map.put(id, dataSource);
                        this.setTargetDataSources(DynamicLoadBean.map);
                    }
                }
            }
        } else {
            this.setTargetDataSources(DynamicLoadBean.map);
        }
    }

    /**
     * 动态加载数据源
     *
     * @return 数据源
     */
    private DataSource createDataSource(DataSourceMysql mysql) {
        DataSource dataSource = null;
        try {
            Class.forName(mysql.getDriverClassName());
            Driver driver = DriverManager.getDriver(mysql.getUrl());
            GenericObjectPool connectionPool = new GenericObjectPool();
            connectionPool.setMaxActive(mysql.getMaxActive());
            connectionPool.setMaxIdle(mysql.getMaxIdle());
            connectionPool.setMinIdle(mysql.getMinIdle());
            connectionPool.setMaxWait(mysql.getMaxWait());
            int maxOpenPreparedStatements = GenericKeyedObjectPool.DEFAULT_MAX_TOTAL;
            GenericKeyedObjectPoolFactory statementPoolFactory = new GenericKeyedObjectPoolFactory(null, -1, GenericKeyedObjectPool.WHEN_EXHAUSTED_FAIL, 0, 1, maxOpenPreparedStatements);
            Properties connectionProperties = new Properties();
            connectionProperties.put("user", mysql.getUsername());
            connectionProperties.put("password", mysql.getPassword());
            DriverConnectionFactory driverConnectionFactory = new DriverConnectionFactory(driver, mysql.getUrl(), connectionProperties);
            new PoolableConnectionFactory(driverConnectionFactory, connectionPool, statementPoolFactory, null, null, true, -1, null, null);
            dataSource = new PoolingDataSource(connectionPool);
        } catch (ClassNotFoundException e) {
            if (this.logger.isErrorEnabled()) {
                this.logger.error("加载数据库驱动类失败，失败信息---->>" + e.getMessage(), e);
            }
        } catch (SQLException e) {
            if (this.logger.isErrorEnabled()) {
                this.logger.error("创建sql运行环境失败，失败信息---->>" + e.getMessage(), e);
            }
        }
        return dataSource;
    }

    @Override
    public void afterPropertiesSet() {
        this.initailizeMutiDataSource(this.dataId);
        super.afterPropertiesSet();
    }

    /**
     * 查找当前用户上下文变量中设置的数据源.
     *
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSourceName();
    }

    /**
     * @param dataSourceLookup 数据指向
     */
    @Override
    public void setDataSourceLookup(DataSourceLookup dataSourceLookup) {
        super.setDataSourceLookup(dataSourceLookup);
    }

    /**
     * 设置默认数据源
     *
     * @param defaultTargetDataSource 默认数据源
     */
    @Override
    public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
    }

    /**
     * 设置数据源集合
     *
     * @param targetDataSources 数据源集合
     */
    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }
}
