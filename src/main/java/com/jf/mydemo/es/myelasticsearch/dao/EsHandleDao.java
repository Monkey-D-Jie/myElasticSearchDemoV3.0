package com.jf.mydemo.es.myelasticsearch.dao;

import com.jf.mydemo.es.myelasticsearch.entity.Post;
import com.jf.mydemo.es.myelasticsearch.interfaces.IEsHandleInterfaces;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wjie
 * @date: 2018/3/13 0013
 * @time: 13:03
 * To change this template use File | Settings | File and Templates.
 */
@Repository(value = "esHandleDao")
//@Component
public class EsHandleDao implements IEsHandleInterfaces{

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @PostConstruct
    private void init(){
        if (!elasticsearchTemplate.indexExists(Post.indexName)) {
            // /注入时调用,当这个索引不存在,则创建索引
            elasticsearchTemplate.createIndex(Post.indexName);
            System.out.println("init成功执行了！");
        }
    }

    @Override
    public List<Post> queryPostById(String postId) {
        Criteria c = new Criteria();
        c.and(Criteria.where("id").is(postId));
        CriteriaQuery cq = new CriteriaQuery(c);
        List<Post> ret = elasticsearchTemplate.queryForList(cq,Post.class);
        return ret;
    }

    @Override
    public void addOrUpdatePost(List<Post> posts) {
        ArrayList<IndexQuery> inserts = null;
        if(!posts.isEmpty()){
            inserts = new ArrayList<IndexQuery>();
            for (Post post: posts
                 ) {
                IndexQuery iq = new IndexQueryBuilder().withObject(post).build();
                inserts.add(iq);
            }
        }
        elasticsearchTemplate.bulkIndex(inserts);
    }

    @Override
    public String deletePostById(String postId) {
        String result = elasticsearchTemplate.delete(Post.class,postId);
        return result;
    }

    @Override
    public List<Post> querySingleTitle(String word, Pageable pageable) {
        /*使用queryStringQuery完成单字符串查询*/
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryStringQuery(word)).withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, Post.class);
    }

    @Override
    public List<Post> querySinglePost(String word, Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryStringQuery(word
        )).withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, Post.class);
    }

    @Override
    public List<Post> querySingleMatch(String content, Integer userId, Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("content",content)).withPageable(pageable).build();
        /*将userId作为查询条件*/
        //        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("userId",userId)).withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, Post.class);
    }

    @Override
    public List<Post> querySinglePhraseMatch(String content, Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchPhraseQuery("content", content)).withPageable(pageable).build();
        //少匹配一个分词也OK、测试slop()方法
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchPhraseQuery("content", content).slop(2)).withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, Post.class);
    }

    @Override
    public List<Post> querySingleTerm(Integer userId, Pageable pageable) {
        //不对传来的值分词，去找完全匹配的
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("userId", userId)).withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, Post.class);
    }

    @Override
    public List<Post> queryMultiMatch(String title, @PageableDefault(sort = "weight", direction = Sort.Direction.DESC) Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(multiMatchQuery(title, "title", "content")).withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, Post.class);
    }

    @Override
    public List<Post> queryContain(String title) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("title", title).operator(MatchQueryBuilder.Operator.AND)).build();
        return elasticsearchTemplate.queryForList(searchQuery, Post.class);
    }

    @Override
    public List<Post> queryContain2(String title) {
        /*minimumShouldMatch可以用在match查询中，设置最少匹配了多少百分比的能查询出来*/
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("title", title).operator(MatchQueryBuilder.Operator.AND).minimumShouldMatch("50%")).build();
        return elasticsearchTemplate.queryForList(searchQuery, Post.class);
    }

    @Override
    public List<Post> queryBool(String title, Integer userId, Integer weight) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQuery().must(termQuery("userId", userId))
                .should(rangeQuery("weight").lt(weight)).must(matchQuery("title", title))).build();
        return elasticsearchTemplate.queryForList(searchQuery, Post.class);
    }
}
