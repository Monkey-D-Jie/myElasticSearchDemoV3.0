package com.jf.mydemo.es.myelasticsearch.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 云平台配置文件工具类
 * Created by js on 2017/9/5.
 */
@Component("machineUtil")
public class MachineUtil {
    /**
     * 记录日志信息
     */
    private Logger logger = LogManager.getLogger(MachineUtil.class.getName());

    @Autowired
    private MachineBean machineBean;

    /**
     * 封装配置文件处理bean
     *
     * @param num     取值区域
     * @param orgCode 取值学校
     * @param key     取值数据key
     * @return 一组想要的数据
     */
    public Object[] getValue(final String num, final String orgCode, final String key) {
        Object[] o = new Object[10];
        if ("1".equals(num)) {
            List<ConnectionBean> connectionList = this.machineBean.getConnectionList();
            for (int i = 0; i < connectionList.size(); i++) {
                if (key.equals(connectionList.get(i).getName())) {
                    o[0] = connectionList.get(i).getConnectionString();
                }
            }
        } else if ("2".equals(num)) {
            List<AppSetBean> appSetList = this.machineBean.getAppSetList();
            for (int j = 0; j < appSetList.size(); j++) {
                if (key.equals(appSetList.get(j).getKey())) {
                    o[0] = appSetList.get(j).getValue();
                }
            }
        } else if ("3".equals(num)) {
            List<AppKeysBean> appKeysList = this.machineBean.getAppKeysList();
            for (int k = 0; k < appKeysList.size(); k++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("appid", appKeysList.get(k).getAppid());
                map.put("appkey", appKeysList.get(k).getAppkey());
                o[k] = map;
            }
        } else if ("4".equals(num)) {
            List<OrgBean> orgList = this.machineBean.getOrgList();
            for (int t = 0; t < orgList.size(); t++) {
                if (orgCode.equals(orgList.get(t).getOrgCode())) {
                    o[0] = orgList.get(t);
                }
            }
        } else if ("5".equals(num)) {
            List<OrgBean> orgList = this.machineBean.getOrgList();
            for (int t = 0; t < orgList.size(); t++) {
                if (orgCode.equals(orgList.get(t).getOrgCode())) {
                    OrgBean orgBean = orgList.get(t);
                    List<OrgSetBean> orgSetList = orgBean.getOrgSetList();
                    for (int m = 0; m < orgSetList.size(); m++) {
                        if (key.equals(orgSetList.get(m).getKey())) {
                            o[0] = orgSetList.get(m).getValue();
                        }
                    }
                }
            }
        } else if ("6".equals(num)) {
            List<OrgBean> orgList = this.machineBean.getOrgList();
            for (int i = 0; i < orgList.size(); i++) {
                OrgBean orgBean = orgList.get(i);
                o[i] = orgBean.getOrgCode();
            }
        } else {
            o[0] = "参数不正确";
        }
        return o;
    }
}