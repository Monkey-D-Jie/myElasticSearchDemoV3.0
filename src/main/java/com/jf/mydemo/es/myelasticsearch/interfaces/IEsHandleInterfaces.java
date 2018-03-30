package com.jf.mydemo.es.myelasticsearch.interfaces;

import com.jf.mydemo.es.myelasticsearch.entities.Post;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wjie
 * @date: 2018/3/13 0013
 * @time: 12:49
 * To change this template use File | Settings | File and Templates.
 */

public interface IEsHandleInterfaces {

    List<Post> queryPostById(String postId);

    void addOrUpdatePost(List<Post> posts);

    String deletePostById(String postId);
    /**
     * 测试下看看，在方法的参数上加上注解说明和不加注解说明（在controller加）有什么区别没有？
     * 初步判断的话：该是在controller层加，毕竟得先在哪里解析请求过来的参数嘛
     */
    /**
     * 单字符串模糊查询，默认排序。
     * 将从所有字段中查找包含传来的word分词后字符串的数据集
     * @param word
     * @param pageable
     * @return 查询到的数据集合
     */
    List<Post> querySingleTitle(String word, Pageable pageable);

    /**
     * 单字符串模糊查询，单字段排序。
     *
     * @param word
     * @param pageable
     * @return 查询到的数据集合
     */
    List<Post> querySinglePost(String word, Pageable pageable);

    /**
     * 单字段对某字符串模糊查询，使用matchQuery()
     *
     * @param content
     * @param userId
     * @param pageable
     * @return 查询到的数据集合
     */
    List<Post> querySingleMatch(String content, Integer userId, Pageable pageable);

    /**
     * 单字段对某短语进行匹配查询，短语分词的顺序会影响结果
     *
     * @param content
     * @param pageable
     * @return
     */
    List<Post> querySinglePhraseMatch(String content, Pageable pageable);

    /**
     * term匹配，即不分词匹配，你传来什么值就会拿你传的值去做完全匹配
     *这个是最严格的匹配，属于低级查询，不进行分词的，
     * 参考这篇文章http://www.cnblogs.com/muniaofeiyu/p/5616316.html
     * @param userId
     * @param pageable
     * @return
     */
    List<Post> querySingleTerm(Integer userId, Pageable pageable);

    /**
     *多字段匹配
     *
     * @param title
     * @param pageable
     * @return
     */
    List<Post> queryMultiMatch(String title, Pageable pageable);

    /**
     * 单字段包含所有输入
     *
     * @param title
     * @return
     */
    List<Post> queryContain(String title);

    /**
     * 单字段包含所有输入(按比例包含)
     *
     * @param title
     * @return
     */
    List<Post> queryContain2(String title);

    /**
     * 多字段合并查询
     *
     * @param title
     * @param userId
     * @param weight
     * @return
     */
    List<Post> queryBool(String title, Integer userId, Integer weight);
}
