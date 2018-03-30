package com.jf.mydemo.es.myelasticsearch.interfaces.testDbInterfaces;

import com.jf.mydemo.es.myelasticsearch.commons.RequestDataBean;
import com.jf.mydemo.es.myelasticsearch.commons.ResponseDataBean;
import com.jf.mydemo.es.myelasticsearch.commons.ResponseDataBean2;
import com.jf.mydemo.es.myelasticsearch.config.MyPage;
import org.springframework.data.domain.Pageable;

import java.util.Date;


/**
 * Created with IntelliJ IDEA.
 *
 * @author: wjie
 * @date: 2018/3/13 0013
 * @time: 12:50
 * To change this template use File | Settings | File and Templates.
 */

public interface ITestDbEsServiceInterfaces {

    ResponseDataBean fetchEmployeerById(RequestDataBean requestDataBean);

    ResponseDataBean insertOrModifyEmployeer(RequestDataBean requestDataBean);

    ResponseDataBean removeEmployeerById(RequestDataBean requestDataBean);

    ResponseDataBean fetchSingleTitle(String name, Pageable pageable);
    ResponseDataBean2 fetchSingleTitle2( String name, MyPage pageable);


    /**
     * 单字符串模糊查询，单字段（weight权重）排序
     *
     * @param name 查询关键
     * @param pageable 页数
     * @return 标准出参对象
     */
    ResponseDataBean fetchSingleEmployeer(String name, Pageable pageable);
    ResponseDataBean2 fetchSingleEmployeer2(String name, Pageable pageable);

    /**
     *单字段对某字符串作模糊查询
     *
     * @param name
     * @param empNo
     * @param pageable
     * @return 标准出参对象
     */
    ResponseDataBean fetchSingleMatch(String name, Integer empNo, Pageable pageable);

    /**
     * 单字段对某短语进行匹配查询，短语分词的顺序会影响结果
     *
     * @param name
     * @param pageable
     * @return 标准出参对象
     */
    ResponseDataBean fetchSinglePhraseMatch(String name, Pageable pageable);

    /**
     * term匹配，即不分词匹配，你传来什么值就会拿你传的值去做完全匹配
     *
     * @param empNo
     * @param pageable
     * @return 标准出参对象
     */
    ResponseDataBean fetchSingleTerm(Integer empNo, Pageable pageable);

    /**
     * 多字段匹配
     *
     * @param name
     * @param pageable
     * @return
     */
    ResponseDataBean fetchMultiMatch(String name, Pageable pageable);

    /**
     * 单字段包含所有输入
     *
     * @param name
     * @return
     */
    ResponseDataBean fetchContain(String name);

    /**
     * 单字段包含所有输入(按比例包含)
     *
     * @param name
     * @return
     */
    ResponseDataBean fetchContain2(String name);

    /**
     * 合并查询
     *
     * @param name
     * @param empNo
     * @param hireDate
     * @return
     */
    ResponseDataBean fetchBool(String name, Integer empNo, Date hireDate);


}
