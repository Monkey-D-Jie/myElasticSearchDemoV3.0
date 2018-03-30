package com.jf.mydemo.es.myelasticsearch.interfaces.testDbInterfaces;

import com.jf.mydemo.es.myelasticsearch.config.MyPage;
import com.jf.mydemo.es.myelasticsearch.entities.testDbEntity.EmployeerBean;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wjie
 * @date: 2018/3/13 0013
 * @time: 12:49
 * To change this template use File | Settings | File and Templates.
 */

public interface ITestDbEsHandleInterfaces {

    List<EmployeerBean> queryEmployeerByEmpNo(String empNo);

    void addOrUpdateEmployeer(List<EmployeerBean> employeerBeans);

    //暂时不管这个先
    String deleteEmployeerBeanById(String empNo);
    /**
     * 测试下看看，在方法的参数上加上注解说明和不加注解说明（在controller加）有什么区别没有？
     * 初步判断的话：该是在controller层加，毕竟得先在哪里解析请求过来的参数嘛
     * ---->是的，你嘞判断是正确的。确实是这样
     *
     * 就employyerBean这个类来说，下面这些方法的应用场景就是
     * name，hiredate(或者birthdate)这两个字段。
     * 暂时先考虑为用name来匹配(firstName or lastName)
     */
    /**
     * 单字符串模糊查询，默认排序。
     * 将从所有字段中查找包含传来的word分词后字符串的数据集
     *
     * @param name
     * @param pageable
     * @return 查询到的数据集合
     */
    List<EmployeerBean> querySingleTitle(String name, Pageable pageable,String limit);
    Map<String,Object> querySingleTitle2(String name, MyPage pageable);

    /**
     * 单字符串模糊查询，单字段排序。
     *
     * @param name
     * @param pageable
     * @return 查询到的数据集合
     */
    List<EmployeerBean> querySingleEmployeerBean(String name, Pageable pageable);
    Map<String,Object> querySingleEmployeerBean2(String name, Pageable pageable);

    /**
     * 单字段对某字符串模糊查询，使用matchQuery()
     *
     * @param name
     * @param empNo
     * @param pageable
     * @return 查询到的数据集合
     */
    List<EmployeerBean> querySingleMatch(String name, Integer empNo, Pageable pageable);

    /**
     * 单字段对某短语进行匹配查询，短语分词的顺序会影响结果
     *
     * @param name
     * @param pageable
     * @return
     */
    List<EmployeerBean> querySinglePhraseMatch(String name, Pageable pageable);

    /**
     * term匹配，即不分词匹配，你传来什么值就会拿你传的值去做完全匹配
     *这个是最严格的匹配，属于低级查询，不进行分词的，
     * 参考这篇文章http://www.cnblogs.com/muniaofeiyu/p/5616316.html
     *
     * @param empNo
     * @param pageable
     * @return
     */
    List<EmployeerBean> querySingleTerm(Integer empNo, Pageable pageable);

    /**
     *多字段匹配
     *
     * @param name
     * @param pageable
     * @return
     */
    List<EmployeerBean> queryMultiMatch(String name, Pageable pageable);

    /**
     * 单字段包含所有输入
     *
     * @param name
     * @return
     */
    List<EmployeerBean> queryContain(String name);

    /**
     * 单字段包含所有输入(按比例包含)
     *
     * @param name
     * @return
     */
    List<EmployeerBean> queryContain2(String name);

    /**
     * 多字段合并查询
     *
     * @param name
     * @param empNo
     * @param hireDate
     * @return
     */
    List<EmployeerBean>  queryBool(String name, Integer empNo, Date hireDate);
}
