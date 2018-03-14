package com.jf.mydemo.es.myelasticsearch.commons;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wjie
 * @date: 2018/3/13 0013
 * @time: 12:51
 * To change this template use File | Settings | File and Templates.
 */

public class RequestDataBean {

    private String reqMsg;
    private Map reqData;
    private boolean reqResult;

    public String getReqMsg() {
        return reqMsg;
    }

    public void setReqMsg(String reqMsg) {
        this.reqMsg = reqMsg;
    }

    public Map getReqData() {
        return reqData;
    }

    public void setReqData(Map reqData) {
        this.reqData = reqData;
    }

    public boolean isReqResult() {
        return reqResult;
    }

    public void setReqResult(boolean reqResult) {
        this.reqResult = reqResult;
    }

    @Override
    public String toString() {
        return "RequestDataBean{" +
                "reqMsg='" + reqMsg + '\'' +
                ", reqData=" + reqData +
                ", reqResult=" + reqResult +
                '}';
    }
}
