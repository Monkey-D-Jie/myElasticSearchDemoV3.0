package com.jf.mydemo.es.myelasticsearch.mapper;

import com.jf.mydemo.es.myelasticsearch.entities.testDbEntity.EmployeerBean;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wjie
 * @date: 2018/3/22 0022
 * @time: 14:13
 * To change this template use File | Settings | File and Templates.
 */

public interface IEmployeeMapper {
    List<EmployeerBean> queryAll();
}
