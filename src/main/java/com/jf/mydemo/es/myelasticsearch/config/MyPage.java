package com.jf.mydemo.es.myelasticsearch.config;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wjie
 * @date: 2018/3/15 0015
 * @time: 14:05
 * To change this template use File | Settings | File and Templates.
 */

public class MyPage  implements  Pageable  {


    private int page;
    private Integer size;
    /**
     * 第几页
     */
    private Integer number;
    /**
     *每页显示的条目数，即size
     */
    private Integer limit;
    /**
     * 排序方式
     */

    private Sort sort;
//    public MyPage(int page, int size, Sort sort) {
//        super(page, size, sort);
//    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
/*public MyPage(Integer size){
        this.size = size;
    }*/

    @Override
    public int getPageNumber() {
        return this.number;
    }

    @Override
    public int getPageSize() {
        return this.size;
    }

    @Override
    public long getOffset() {
        return 0;
    }

    @Override
    public Sort getSort() {
        return this.sort;
    }

    @Override
    public Pageable next() {
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        return null;
    }

    @Override
    public Pageable first() {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }
}
