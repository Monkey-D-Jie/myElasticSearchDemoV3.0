package com.jf.mydemo.es.myelasticsearch.config;

import com.jf.mydemo.es.myelasticsearch.entity.Post;
import com.jf.mydemo.es.myelasticsearch.interfaces.IEsHandleInterfaces;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
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
public class InitTest {

    @Autowired
    private IEsHandleInterfaces esHandleDao;
    @Autowired
    private Init init;

    @Test
    public void initData(){
        /*初始化数据到es中*/
        List<Post> postList = new ArrayList<Post>(50);
       for (int i = 0; i < 40; i++) {
            Post post = new Post();
            post.setTitle(init.getTitle().get(i));
            post.setContent(init.getContent().get(i));
            post.setWeight(i);
            post.setUserId(i%10+1);
            postList.add(post);
        }
        /**
         * @author: wjie
         * @date: 2018/3/14 0014 14:36
         *
         *添加EsCotroller中singlePhraseMatch()中使用slop()方法的测试数据
         */
        Post post = new Post();
        post.setTitle("我是");
        post.setContent("我爱中华人民共和国");
        post.setWeight(1);
        post.setUserId(1);
        Post post1 = new Post();
        post1.setTitle("我是");
        post1.setContent("中华共和国");
        post1.setWeight(2);
        post1.setUserId(2);
        postList.add(post);
        postList.add(post1);
        this.esHandleDao.addOrUpdatePost(postList);
        System.out.println("初始化数据完成！！");
    }

    @Test
    public void queryPostAll(){
        List<Post> postList = this.esHandleDao.queryPostById("!=null");
        if(!postList.isEmpty()){
            for (int i = 0; i < postList.size(); i++) {
                System.out.println(postList.get(i).toString());
            }
        }
    }
}
