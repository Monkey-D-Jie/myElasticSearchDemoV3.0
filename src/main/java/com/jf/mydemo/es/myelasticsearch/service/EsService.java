package com.jf.mydemo.es.myelasticsearch.service;

import com.jf.mydemo.es.myelasticsearch.commons.RequestDataBean;
import com.jf.mydemo.es.myelasticsearch.commons.ResponseDataBean;
import com.jf.mydemo.es.myelasticsearch.entities.Post;
import com.jf.mydemo.es.myelasticsearch.interfaces.IEsHandleInterfaces;
import com.jf.mydemo.es.myelasticsearch.interfaces.IEsServiceInterfaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wjie
 * @date: 2018/3/13 0013
 * @time: 10:57
 * To change this template use File | Settings | File and Templates.
 */
@Service("esService")
public class EsService implements IEsServiceInterfaces{

    @Autowired
    private IEsHandleInterfaces esHandleDao;

    private Boolean success = true;

    @Override
    public ResponseDataBean fetchPostById(RequestDataBean requestDataBean) {
        ResponseDataBean responseDataBean = null;
        if(requestDataBean != null){
            responseDataBean = new ResponseDataBean();
            String postId = String.valueOf(requestDataBean.getReqData().get("postId"));
            List<Post> postList = this.esHandleDao.queryPostById(postId);
            Map<String,Object> resultMap = new HashMap<String, Object>(8);
            resultMap.put("postList",postList);
            responseDataBean.setData(resultMap);
            responseDataBean.setMsg("查询操作执行成功");
            responseDataBean.setResResult(success);
        }
        return responseDataBean;
    }

    @Override
    public ResponseDataBean insertOrModifyPost(RequestDataBean requestDataBean) {
        ResponseDataBean responseDataBean = null;
        if(requestDataBean != null){
            responseDataBean = new ResponseDataBean();
            Post post = (Post)(requestDataBean.getReqData().get("post"));
            List<Post> postList = new ArrayList<Post>(8);
            postList.add(post);
            this.esHandleDao.addOrUpdatePost(postList);
            Map<String,Object> map = new HashMap<String,Object>(5);
            map.put("post",post);
            responseDataBean.setData(map);
            responseDataBean.setMsg("更新或插入操作执行成功");
            responseDataBean.setResResult(success);
        }
        return responseDataBean;
    }

    @Override
    public ResponseDataBean removePostById(RequestDataBean requestDataBean) {
        ResponseDataBean responseDataBean = null;
        if(requestDataBean != null){
            responseDataBean = new ResponseDataBean();
            String postId = String.valueOf(requestDataBean.getReqData().get("postId"));
            String result = this.esHandleDao.deletePostById(postId);
            Map<String,Object> map = new HashMap<String,Object>(5);
            map.put("result",result);
            responseDataBean.setData(map);
            responseDataBean.setMsg("删除操作执行成功");
            responseDataBean.setResResult(success);
        }
        return responseDataBean;
    }

    @Override
    public ResponseDataBean fetchSingleTitle(String word,Pageable pageable) {
        ResponseDataBean responseDataBean = null;
        if(word != null && pageable != null){
                List<Post> result = this.esHandleDao.querySingleTitle(word,pageable);
                Map<String,Object> map = new HashMap<String,Object>(5);
                map.put("result",result);
                responseDataBean = new ResponseDataBean();
                responseDataBean.setData(map);
                responseDataBean.setMsg("---->>单字符串模糊查询，默认排序.执行成功");
                responseDataBean.setResResult(success);
        }
        return responseDataBean;
    }

    @Override
    public ResponseDataBean fetchSinglePost(String word, Pageable pageable) {
        ResponseDataBean responseDataBean = null;
        if(word != null && pageable != null){
            List<Post> result = this.esHandleDao.querySinglePost(word,pageable);
            Map<String,Object> map = new HashMap<String,Object>(5);
            map.put("result",result);
            responseDataBean = new ResponseDataBean();
            responseDataBean.setData(map);
            responseDataBean.setMsg("---->>单字符串模糊查询，单字段（weight权重）排序.执行成功");
            responseDataBean.setResResult(success);
        }
        return responseDataBean;
    }

    @Override
    public ResponseDataBean fetchSingleMatch(String content, Integer userId, Pageable pageable) {
        ResponseDataBean responseDataBean = null;
        if(content != null && userId != null && pageable != null){
            List<Post> result = this.esHandleDao.querySingleMatch(content,userId,pageable);
            Map<String,Object> map = new HashMap<String,Object>(5);
            map.put("result",result);
            responseDataBean = new ResponseDataBean();
            responseDataBean.setData(map);
            responseDataBean.setMsg("---->>单字段对某字符串作模糊查询.执行成功");
            responseDataBean.setResResult(success);
        }
        return responseDataBean;
    }

    @Override
    public ResponseDataBean fetchSinglePhraseMatch(String content, Pageable pageable) {
        ResponseDataBean responseDataBean = null;
        if(content != null && pageable != null){
            List<Post> result = this.esHandleDao.querySinglePhraseMatch(content,pageable);
            Map<String,Object> map = new HashMap<String,Object>(5);
            map.put("result",result);
            responseDataBean = new ResponseDataBean();
            responseDataBean.setData(map);
            responseDataBean.setMsg("---->>单字段对某短语进行匹配查询.执行成功");
            responseDataBean.setResResult(success);
        }
        return responseDataBean;
    }

    @Override
    public ResponseDataBean fetchSingleTerm(Integer userId, Pageable pageable) {
        ResponseDataBean responseDataBean = null;
        if(userId != null && pageable != null){
            List<Post> result = this.esHandleDao.querySingleTerm(userId,pageable);
            Map<String,Object> map = new HashMap<String,Object>(5);
            map.put("result",result);
            responseDataBean = new ResponseDataBean();
            responseDataBean.setData(map);
            responseDataBean.setMsg("---->>term匹配，即不分词匹配查询.执行成功");
            responseDataBean.setResResult(success);
        }
        return responseDataBean;
    }

    @Override
    public ResponseDataBean fetchMultiMatch(String title, Pageable pageable) {
        ResponseDataBean responseDataBean = null;
        if(title != null && pageable != null){
            List<Post> result = this.esHandleDao.queryMultiMatch(title,pageable);
            Map<String,Object> map = new HashMap<String,Object>(5);
            map.put("result",result);
            responseDataBean = new ResponseDataBean();
            responseDataBean.setData(map);
            responseDataBean.setMsg("---->>多字段匹配 查询.执行成功");
            responseDataBean.setResResult(success);
        }
        return responseDataBean;
    }

    @Override
    public ResponseDataBean fetchContain(String title) {
        ResponseDataBean responseDataBean = null;
        if(title != null ){
            List<Post> result = this.esHandleDao.queryContain(title);
            Map<String,Object> map = new HashMap<String,Object>(5);
            map.put("result",result);
            responseDataBean = new ResponseDataBean();
            responseDataBean.setData(map);
            responseDataBean.setMsg("---->>单字段包含所有输入 查询.执行成功");
            responseDataBean.setResResult(success);
        }
        return responseDataBean;
    }

    @Override
    public ResponseDataBean fetchContain2(String title) {
        ResponseDataBean responseDataBean = null;
        if(title != null ){
            List<Post> result = this.esHandleDao.queryContain2(title);
            Map<String,Object> map = new HashMap<String,Object>(5);
            map.put("result",result);
            responseDataBean = new ResponseDataBean();
            responseDataBean.setData(map);
            responseDataBean.setMsg("---->>单字段包含所有输入(按比例包含)查询.执行成功");
            responseDataBean.setResResult(success);
        }
        return responseDataBean;
    }

    @Override
    public ResponseDataBean fetchBool(String title, Integer userId, Integer weight) {
        ResponseDataBean responseDataBean = null;
        if(title != null ){
            List<Post> result = this.esHandleDao.queryBool(title,userId,weight);
            Map<String,Object> map = new HashMap<String,Object>(5);
            map.put("result",result);
            responseDataBean = new ResponseDataBean();
            responseDataBean.setData(map);
            responseDataBean.setMsg("---->>合并查询.执行成功");
            responseDataBean.setResResult(success);
        }
        return responseDataBean;
    }

}
