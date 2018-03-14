package com.jf.mydemo.es.myelasticsearch.commons;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wjie
 * @date: 2018/3/13 0013
 * @time: 12:51
 * To change this template use File | Settings | File and Templates.
 */

public class ResponseDataBean {

    private String resMsg;
    private Object resData;
    private boolean resResult;

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public Object getResData() {
        return resData;
    }

    public void setResData(Object resData) {
        this.resData = resData;
    }

    public boolean isResResult() {
        return resResult;
    }

    public void setResResult(boolean resResult) {
        this.resResult = resResult;
    }

    @Override
    public String toString() {
        return "ResponseDataBean{" +
                "resMsg='" + resMsg + '\'' +
                ", resData=" + resData +
                ", resResult=" + resResult +
                '}';
    }
}
