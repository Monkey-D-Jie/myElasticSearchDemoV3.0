package com.jf.mydemo.es.myelasticsearch.config;

import java.io.Serializable;

/**
 * 配置文件的APPid
 * Created by js on 2017/9/5.
 */
public class AppKeysBean implements Serializable {
    /**
     * 请求openAPI的ID
     */
    private String appid;
    /**
     * 请求openAPI的key
     */
    private String appkey;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }
}
