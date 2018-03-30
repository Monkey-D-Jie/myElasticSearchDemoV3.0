package com.jf.mydemo.es.myelasticsearch.config;


import org.springframework.data.domain.PageRequest;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wjie
 * @date: 2018/3/15 0015
 * @time: 14:05
 * To change this template use File | Settings | File and Templates.
 */

public class MyPage2 extends PageRequest {

    /**
     *每页显示的条目数，即size
     */
    private Integer limit;

    public Integer getSize() {
        return this.limit;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    private Integer size;
    /**
     * 第几页
     */
    private Integer number;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public MyPage2(int page, int size) {
        super(page, size);
    }

    /**
     * 排序方式
     */


}
