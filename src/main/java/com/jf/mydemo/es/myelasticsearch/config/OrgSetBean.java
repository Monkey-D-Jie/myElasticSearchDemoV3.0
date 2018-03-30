package com.jf.mydemo.es.myelasticsearch.config;

import java.io.Serializable;

/**
 * 学校设置信息
 * Created by js on 2017/9/5.
 */
public class OrgSetBean implements Serializable {
    /**
     * key值
     */
    private String key;
    /**
     * 数据值
     */
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
