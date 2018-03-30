package com.jf.mydemo.es.myelasticsearch.config.tesbDb;

import com.jf.mydemo.es.myelasticsearch.config.MyPage;
import com.jf.mydemo.es.myelasticsearch.entities.testDbEntity.EmployeerBean;
import com.jf.mydemo.es.myelasticsearch.interfaces.testDbInterfaces.ITestDbEsHandleInterfaces;
import com.sun.istack.internal.logging.Logger;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wjie
 * @date: 2018/3/13 0013
 * @time: 14:19
 * To change this template use File | Settings | File and Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class TestDbInitTest {

    private static Logger logger = Logger.getLogger(TestDbInitTest.class);

    /**
     * 创建client用
     */
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ITestDbEsHandleInterfaces testDbEsHandleDao;

    private final static String indexName = "mytestdbdemo";
    private final static String typeName = "employee";

    /**
     *  创建索引(数据库)和mapping(表)
     *
     * @throws IOException
     */

    @Test
    public void CreateIndexAndMapping() throws IOException {
        this.logger.info("创建索引开始----start");
        /*创建索引*/
        Client client = this.elasticsearchTemplate.getClient();
        CreateIndexRequestBuilder cib=client.admin().indices().prepareCreate(indexName);
        /*添加映射*/
        XContentBuilder mapping = XContentFactory.jsonBuilder()
                .startObject()
                .startObject("properties") //设置之定义字段
                .startObject("empNo")
                .field("type","integer") //设置数据类型
                .endObject()
                .startObject("birthDate")
                .field("type","date")
                .field("format","yyyy-MM-dd HH:mm:ss")
                .endObject()
                .startObject("firstName")
                .field("type","string")
                .endObject()
                .startObject("lastName")
                .field("type","string")
                .endObject()
                .startObject("gender")
                .field("type","string")
                .endObject()
                .startObject("hireDate")
                //设置Date类型
                .field("type","date")
                //设置Date的格式
                .field("format","yyyy-MM-dd HH:mm:ss")
                .endObject()
                .endObject()
                .endObject();
        cib.addMapping(typeName, mapping);
        CreateIndexResponse res=cib.execute().actionGet();
        System.out.println("----------添加映射成功----------"+res.toString());
    }


    @Test
    public void queryPostAll(){
        List<EmployeerBean> postList = this.testDbEsHandleDao.queryEmployeerByEmpNo("52290");
        if(!postList.isEmpty()){
            for (int i = 0; i < postList.size(); i++) {
                System.out.println(postList.get(i).toString());
            }
        }
    }


    @Test
    public void fetchSingleTitleTest(){
        String name = "Stein";
        MyPage page = new MyPage();
//        List<EmployeerBean> list = this.testDbEsHandleDao.querySingleTitle(name,page);
        //long date = list.get(0).getBirthDate().getTime();
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //System.out.println("-------------------"+sdf.format(date));
//        this.logger.info(JsonXmlObjectKit.convertObjectJson(list.get(0)));
    }

}
