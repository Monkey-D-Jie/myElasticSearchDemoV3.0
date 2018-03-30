package com.jf.mydemo.es.myelasticsearch.dao;

import com.jf.mydemo.es.myelasticsearch.config.MyPage;
import com.jf.mydemo.es.myelasticsearch.entities.Post;
import com.jf.mydemo.es.myelasticsearch.interfaces.IEsHandleInterfaces;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wjie
 * @date: 2018/3/15 0015
 * @time: 14:07
 * To change this template use File | Settings | File and Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class EsHandleInterfacesTest {
    @Autowired
    private IEsHandleInterfaces esHandleDao;

    @Test
    public void querySingleMatchTest(){
        String content = "魂梦断";
        Integer userId = 666;
        MyPage page = new MyPage();
        List<Post> posts = this.esHandleDao.querySingleMatch(content,userId,page);
        List<Post> posts2 = this.esHandleDao.querySingleMatch(content,userId,page);
        System.out.println(posts.toString());
        System.out.println(posts2.toString());

    }

}
