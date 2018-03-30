package com.jf.mydemo.es.myelasticsearch.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wjie
 * @date: 2018/3/22 0022
 * @time: 13:51
 * To change this template use File | Settings | File and Templates.
 *
 * 数据源线程上下文对象
 */
public class DataSourceContextHolder {
    /**
     * 记录日志信息
     */
    private static Logger logger = LogManager.getLogger(DataSourceContextHolder.class.getName());
    /**
     * 本地线程管理变量
     */
    private static ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    /**
     * 设置需要切换的数据源名称
     *
     * @param dataSourceName 数据源名称
     */
    public static void setDataSourceType(String dataSourceName) {
        /*反射的服务查询，并且服务实例化，加载服务方法*/
        Method method = ReflectionUtils.findMethod(SpringContextUtil.getBean("dynamicLoadBean").getClass(), "initailizeMutiDataSource", new Class[]{String.class});
        /*回调方法，同时返回方法的回调结果*/
        ReflectionUtils.invokeMethod(method, SpringContextUtil.getBean("dynamicLoadBean"), dataSourceName);
        contextHolder.set(dataSourceName);
    }

    /**
     * 获取需要切换的数据源
     *
     * @return 数据源
     */
    public static String getDataSourceName() {
        return contextHolder.get();
    }

    /**
     * 移除本地线程管理的数据源
     */
    public static void clearDataSourceType() {
        contextHolder.remove();
    }
}
