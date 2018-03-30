package com.jf.mydemo.es.myelasticsearch.dao.testDbDao;

import com.jf.mydemo.es.myelasticsearch.config.MyPage;
import com.jf.mydemo.es.myelasticsearch.entities.testDbEntity.EmployeerBean;
import com.jf.mydemo.es.myelasticsearch.interfaces.testDbInterfaces.ITestDbEsHandleInterfaces;
import org.elasticsearch.index.query.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wjie
 * @date: 2018/3/13 0013
 * @time: 13:03
 * To change this template use File | Settings | File and Templates.
 */
@Repository(value = "testDbEsHandleDao")
public class TestDbEsHandleDao implements ITestDbEsHandleInterfaces {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @PostConstruct
    private void init(){
        if (!elasticsearchTemplate.indexExists(EmployeerBean.indexName)) {
            // /注入时调用,当这个索引不存在,则创建索引
            elasticsearchTemplate.createIndex(EmployeerBean.indexName);
            System.out.println("init成功执行了！");
        }
    }

    @Override
    public List<EmployeerBean> queryEmployeerByEmpNo(String EmployeerBeanId) {
        Criteria c = new Criteria();
        c.and(Criteria.where("empNo").is(EmployeerBeanId));
        CriteriaQuery cq = new CriteriaQuery(c);
        List<EmployeerBean> ret = this.elasticsearchTemplate.queryForList(cq,EmployeerBean.class);
        return ret;
    }

    @Override
    public void addOrUpdateEmployeer(List<EmployeerBean> employeerBeans) {
        ArrayList<IndexQuery> inserts = null;
        if(!employeerBeans.isEmpty()){
            inserts = new ArrayList<IndexQuery>();
            for (EmployeerBean employeer: employeerBeans
                 ) {
                IndexQuery iq = new IndexQueryBuilder().withObject(employeer).build();
                inserts.add(iq);
            }
        }
        elasticsearchTemplate.bulkIndex(inserts);
        System.out.println("初始化数据执行成功----测试数据已加入！");
    }

    @Override
    public String deleteEmployeerBeanById(String EmployeerBeanId) {
        String result = elasticsearchTemplate.delete(EmployeerBean.class,EmployeerBeanId);
        /*QueryBuilder qb = spanFirstQuery(
                spanTermQuery("user", "kimchy"),
                3
        );
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(qb).build();
        List<EmployeerBean> list = elasticsearchTemplate.queryForList(searchQuery, EmployeerBean.class);*/
        return result;
    }

    @Override
    public List<EmployeerBean> querySingleTitle(String word, Pageable pageable,String limit) {
        /*使用queryStringQuery完成单字符串查询*/
        //MyPage page = new MyPage(100);
        /******
         *分页查询测试
         */
        //TransportClient client = (TransportClient) elasticsearchTemplate.getClient();
//        Date begin = new Date();
        //long count = client.prepare(INDEX).setTypes(TYPE).execute().actionGet().getCount();
//        int count = 10;
//        SearchRequestBuilder requestBuilder = client.prepareSearch(EmployeerBean.indexName).setTypes("EmployeerBean").setQuery(QueryBuilders.matchAllQuery());
//        for(int i=0,sum=0; sum<count; i++){
//            SearchResponse response = requestBuilder.setFrom(i).setSize(50).execute().actionGet();
//            sum += response.getHits().hits().length;
//            System.out.println("总量"+count+" 已经查到"+sum);
//        }
        /*SearchResponse response = client.prepareSearch(EmployeerBean.indexName)
                .setQuery(queryStringQuery(word))
                .setFrom((pageable.getPageNumber()-1)*Integer.parseInt(limit))
                .setSize(Integer.parseInt(limit))
                .execute().actionGet();*/
//        for (SearchHit searchHit : response.getHits()) {
//            System.out.println("------------------------"+searchHit);
//        }
        /******
         *分页查询测试
         */
        System.out.println("-------------------------------------------分页查询开始！");
        MyPage page = new MyPage();
        page.setNumber(pageable.getPageNumber());
        page.setSize(Integer.parseInt(limit));
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryStringQuery(word)).withPageable(page).build();
        Date begin = new Date();
//        List<EmployeerBean> list = elasticsearchTemplate.queryForList(searchQuery, EmployeerBean.class);
        Date end = new Date();
//        System.out.println("耗时: "+(end.getTime()-begin.getTime())+"总计找到："+response.getHits().hits().length+"条记录,当前显示");
        return elasticsearchTemplate.queryForList(searchQuery, EmployeerBean.class);
    }

    @Override
    public Map<String,Object> querySingleTitle2(String word, MyPage pageable) {
        System.out.println("-------------------------------分页查询开始！");
        Map<String,Object> map = null;
        //MyPage page = new MyPage();
        //page.setNumber(pageable.getPageNumber());
        //page.setSize(Integer.parseInt(limit));
        /**
         * 这里暂时想到一个匹配：
         * 对传进来的word参数作 分析
         * 全数字 ---》empNo
         * 非全数字--》对应到 firstName和lastName上去。
         * 对于firstName和lastName，这两个又可能和在一起。即JackyTopson的，
         *
         * empNo--》term
         * name---》对应到contain上
         * 现在的处理法子是就着查询直接查---》
         * 分析的那个，暂时不去做
         */
        //临时的模糊匹配
        //page对象实现Pageable接口，直接拿给ES，它会自动的去执行到 【(page-1)*pageSize】的计算操作
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryStringQuery(word)).withPageable(pageable).build();
        //全匹配查询
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("_id", word)).withPageable(pageable).build();
        //比较笨的方法获取查询到的条目数(这里可能还是有默认10000的限制)
        int resultNum = (int) elasticsearchTemplate.count(searchQuery,EmployeerBean.class);
        if(resultNum != 0){
            map = new HashMap<String,Object>(10);
            Date begin = new Date();
            List<EmployeerBean> list = elasticsearchTemplate.queryForList(searchQuery, EmployeerBean.class);
            if(list.isEmpty()){
                list = this.queryEmployeerByEmpNo(word);
            }
            Date end = new Date();
            map.put("list",list);
            map.put("count",resultNum);
            //返回都前端显示的搜索结果语句
            String msg = "耗时: "+((end.getTime()-begin.getTime())/1000.0)+"S,总计找到: "+resultNum+"条记录,当前显示"+list.size()+"条。";
            map.put("msg",msg);
        }
        return map;
    }

    @Override
    public List<EmployeerBean> querySingleEmployeerBean(String word, Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryStringQuery(word
        )).withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, EmployeerBean.class);
    }

    @Override
    public Map<String,Object> querySingleEmployeerBean2(String word, Pageable pageable) {
        Map<String,Object> map = new HashMap<String,Object>(8);
        List<EmployeerBean> list = null;
        int resultNum = 0;
        boolean result=word.matches("[0-9]+");
        Date begin = new Date();
        if(result){
            list = this.queryEmployeerByEmpNo(word);
        }else{
            //匹配查询(从姓名和编号来看)
            SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(multiMatchQuery(word, "firstName", "lastName")).withPageable(pageable).build();
            resultNum = (int) elasticsearchTemplate.count(searchQuery,EmployeerBean.class);
            list = elasticsearchTemplate.queryForList(searchQuery, EmployeerBean.class);
        }
       /* SearchQuery searchQuery2 = new NativeSearchQueryBuilder().withQuery(matchQuery("firstName",word)).withQuery(matchQuery("lastName",word)).withQuery(matchQuery("empNo",word)).withPageable(pageable).build();*/
        //比较笨的方法获取查询到的条目数(这里可能还是有默认10000的限制)
        Date end = new Date();
        if(!list.isEmpty()){
            map.put("list",list);
            map.put("count",resultNum);
            String msg = "耗时: "+((end.getTime()-begin.getTime())/1000.0)+"S,总计找到: "+resultNum+"条记录,当前显示"+list.size()+"条。";
            map.put("msg",msg);
        }
        return map;
    }

    @Override
    public List<EmployeerBean> querySingleMatch(String content, Integer userId, Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("firstName",content)).withQuery(matchQuery("lastName",content)).withPageable(pageable).build();
        /*将userId作为查询条件*/
        //        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("userId",userId)).withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, EmployeerBean.class);
    }

    @Override
    public List<EmployeerBean> querySinglePhraseMatch(String content, Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchPhraseQuery("firstName", content)).withQuery(matchPhraseQuery("lastName", content)).withPageable(pageable).build();
        //少匹配一个分词也OK、测试slop()方法
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchPhraseQuery("content", content).slop(2)).withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, EmployeerBean.class);
    }

    @Override
    public List<EmployeerBean> querySingleTerm(Integer userId, Pageable pageable) {
        //不对传来的值分词，去找完全匹配的
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("empNo", userId)).withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, EmployeerBean.class);
    }

    @Override
    public List<EmployeerBean> queryMultiMatch(String title, @PageableDefault(sort = "weight", direction = Sort.Direction.DESC) Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(multiMatchQuery(title, "firstName", "lastName")).withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(searchQuery, EmployeerBean.class);
    }

    @Override
    public List<EmployeerBean> queryContain(String title) {
        /*对应的是 es2.4.0*/
        //SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("title", title).operator(MatchQueryBuilder.Operator.AND)).build();
        /*对应到 es5.5.0*/
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("firstName", title).operator(Operator.AND)).withQuery(matchQuery("lastName", title).operator(Operator.AND)).build();
        return elasticsearchTemplate.queryForList(searchQuery, EmployeerBean.class);
    }

    @Override
    public List<EmployeerBean> queryContain2(String title) {
        /*minimumShouldMatch可以用在match查询中，设置最少匹配了多少百分比的能查询出来*/
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("title", title).operator(MatchQueryBuilder.Operator.AND).minimumShouldMatch("50%")).build();
        /*变更同上*/
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("firstName", title).operator(Operator.AND).minimumShouldMatch("50%")).build();
        return elasticsearchTemplate.queryForList(searchQuery, EmployeerBean.class);
    }

    @Override
    public List<EmployeerBean> queryBool(String title, Integer userId, Date hireDate) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQuery().must(termQuery("empNo", userId))
                .should(rangeQuery("hireDate").lt(hireDate)).must(matchQuery("firstName", title))).build();
        return elasticsearchTemplate.queryForList(searchQuery, EmployeerBean.class);
    }
}
