package com.jf.mydemo.es.myelasticsearch.interfaces;

import com.jf.mydemo.es.myelasticsearch.commons.RequestDataBean;
import com.jf.mydemo.es.myelasticsearch.commons.ResponseDataBean;
import org.springframework.data.domain.Pageable;


/**
 * Created with IntelliJ IDEA.
 *
 * @author: wjie
 * @date: 2018/3/13 0013
 * @time: 12:50
 * To change this template use File | Settings | File and Templates.
 */

public interface IEsServiceInterfaces {

    ResponseDataBean fetchPostById(RequestDataBean requestDataBean);

    ResponseDataBean insertOrModifyPost(RequestDataBean requestDataBean);

    ResponseDataBean removePostById(RequestDataBean requestDataBean);

    ResponseDataBean fetchSingleTitle(String word, Pageable pageable);

    /**
     * 单字符串模糊查询，单字段（weight权重）排序
     *
     * @param word 查询关键
     * @param pageable 页数
     * @return 标准出参对象
     */
    ResponseDataBean fetchSinglePost(String word, Pageable pageable);

    /**
     *单字段对某字符串作模糊查询
     *
     * @param content
     * @param userId
     * @param pageable
     * @return 标准出参对象
     */
    ResponseDataBean fetchSingleMatch(String content, Integer userId, Pageable pageable);

    /**
     * 单字段对某短语进行匹配查询，短语分词的顺序会影响结果
     *
     * @param content
     * @param pageable
     * @return 标准出参对象
     */
    ResponseDataBean fetchSinglePhraseMatch(String content, Pageable pageable);

    /**
     * term匹配，即不分词匹配，你传来什么值就会拿你传的值去做完全匹配
     *
     * @param userId
     * @param pageable
     * @return 标准出参对象
     */
    ResponseDataBean fetchSingleTerm(Integer userId, Pageable pageable);

    /**
     * 多字段匹配
     *
     * @param title
     * @param pageable
     * @return
     */
    ResponseDataBean fetchMultiMatch(String title, Pageable pageable);

    /**
     * 单字段包含所有输入
     *
     * @param title
     * @return
     */
    ResponseDataBean fetchContain(String title);

    /**
     * 单字段包含所有输入(按比例包含)
     *
     * @param title
     * @return
     */
    ResponseDataBean fetchContain2(String title);

    /**
     * 合并查询
     *
     * @param title
     * @param userId
     * @param weight
     * @return
     */
    ResponseDataBean fetchBool(String title, Integer userId, Integer weight);


}
