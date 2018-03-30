package com.jf.mydemo.es.myelasticsearch.commons;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wjie
 * @date: 2018/3/13 0013
 * @time: 12:51
 * To change this template use File | Settings | File and Templates.
 */

public class ResponseDataBean implements Serializable{

    private String msg;
    private Object data;
    private boolean resResult;
    private Integer count;
    private Integer code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isResResult() {
        return resResult;
    }

    public void setResResult(boolean resResult) {
        this.resResult = resResult;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ResponseDataBean{" +
                "msg='" + msg + '\'' +
                ", data=" + data +
                ", resResult=" + resResult +
                ", count=" + count +
                ", code=" + code +
                '}';
    }
}
