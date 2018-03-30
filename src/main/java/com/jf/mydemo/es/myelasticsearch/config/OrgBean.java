package com.jf.mydemo.es.myelasticsearch.config;

import java.io.Serializable;
import java.util.List;

/**
 * 配置文件学校信息
 * Created by js on 2017/9/5.
 */
public class OrgBean implements Serializable {
    /**
     * 学校名称
     */
    private String orgName;
    /**
     * 学校编号
     */
    private String orgCode;
    /**
     * 学校简称
     */
    private String orgAlias;
    /**
     * 学校类型
     */
    private String orgType;
    /**
     * 学校设置列表
     */
    private List<OrgSetBean> orgSetList;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgAlias() {
        return orgAlias;
    }

    public void setOrgAlias(String orgAlias) {
        this.orgAlias = orgAlias;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public List<OrgSetBean> getOrgSetList() {
        return orgSetList;
    }

    public void setOrgSetList(List<OrgSetBean> orgSetList) {
        this.orgSetList = orgSetList;
    }
}
