package com.jf.mydemo.es.myelasticsearch.config;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * 数据源实体类
 * Created by js on 2017/8/11.
 */
@Component("machineBean")
public class MachineBean implements Serializable {
    /**
     * 第一层
     */
    private List<ConnectionBean> connectionList;
    /**
     * 第二层
     */
    private List<AppSetBean> appSetList;
    /**
     * 第三层
     */
    private List<AppKeysBean> appKeysList;
    /**
     * 第四层
     */
    private List<OrgBean> orgList;

    public List<ConnectionBean> getConnectionList() {
        return connectionList;
    }

    public void setConnectionList(List<ConnectionBean> connectionList) {
        this.connectionList = connectionList;
    }

    public List<AppSetBean> getAppSetList() {
        return appSetList;
    }

    public void setAppSetList(List<AppSetBean> appSetList) {
        this.appSetList = appSetList;
    }

    public List<AppKeysBean> getAppKeysList() {
        return appKeysList;
    }

    public void setAppKeysList(List<AppKeysBean> appKeysList) {
        this.appKeysList = appKeysList;
    }

    public List<OrgBean> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<OrgBean> orgList) {
        this.orgList = orgList;
    }
}
