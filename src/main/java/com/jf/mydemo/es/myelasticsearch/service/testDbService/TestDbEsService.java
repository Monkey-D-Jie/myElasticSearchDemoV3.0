package com.jf.mydemo.es.myelasticsearch.service.testDbService;

import com.jf.mydemo.es.myelasticsearch.commons.RequestDataBean;
import com.jf.mydemo.es.myelasticsearch.commons.ResponseDataBean;
import com.jf.mydemo.es.myelasticsearch.commons.ResponseDataBean2;
import com.jf.mydemo.es.myelasticsearch.config.MyPage;
import com.jf.mydemo.es.myelasticsearch.entities.testDbEntity.EmployeerBean;
import com.jf.mydemo.es.myelasticsearch.interfaces.testDbInterfaces.ITestDbEsHandleInterfaces;
import com.jf.mydemo.es.myelasticsearch.interfaces.testDbInterfaces.ITestDbEsServiceInterfaces;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wjie
 * @date: 2018/3/13 0013
 * @time: 10:57
 * To change this template use File | Settings | File and Templates.
 */
@Service("testDbEsService")
public class TestDbEsService implements ITestDbEsServiceInterfaces {


    private Logger logger = LogManager.getLogger(TestDbEsService.class.getName());
    //    @Autowired
//    private IEsHandleInterfaces esHandleDao;
    @Autowired
    private ITestDbEsHandleInterfaces testDbEsHadle;

    private Boolean success = true;

    @Override
    public ResponseDataBean fetchEmployeerById(RequestDataBean requestDataBean) {
        ResponseDataBean responseDataBean = null;
        if (requestDataBean != null) {
            responseDataBean = new ResponseDataBean();
            String postId = String.valueOf(requestDataBean.getReqData().get("empNo"));
            List<EmployeerBean> empList = this.testDbEsHadle.queryEmployeerByEmpNo(postId);
            Map<String, Object> resultMap = new HashMap<String, Object>(8);
            resultMap.put("empList", empList);
            responseDataBean.setData(resultMap);
            responseDataBean.setMsg("查询操作执行成功");
            responseDataBean.setResResult(success);
        }
        return responseDataBean;
    }

    @Override
    public ResponseDataBean insertOrModifyEmployeer(RequestDataBean requestDataBean) {
        ResponseDataBean responseDataBean = null;
        if (requestDataBean != null) {
            responseDataBean = new ResponseDataBean();
            EmployeerBean employeerBean = (EmployeerBean) (requestDataBean.getReqData().get("employeer"));
            List<EmployeerBean> employeerBeanList = new ArrayList<EmployeerBean>(8);
            employeerBeanList.add(employeerBean);
            this.testDbEsHadle.addOrUpdateEmployeer(employeerBeanList);
            Map<String, Object> map = new HashMap<String, Object>(5);
            map.put("employeerBean", employeerBean);
            responseDataBean.setData(map);
            responseDataBean.setMsg("更新或插入操作执行成功");
            responseDataBean.setResResult(success);
        }
        return responseDataBean;
    }

    @Override
    public ResponseDataBean removeEmployeerById(RequestDataBean requestDataBean) {
        ResponseDataBean responseDataBean = null;
        if (requestDataBean != null) {
            responseDataBean = new ResponseDataBean();
            String empNo = String.valueOf(requestDataBean.getReqData().get("empNo"));
            String result = this.testDbEsHadle.deleteEmployeerBeanById(empNo);
            Map<String, Object> map = new HashMap<String, Object>(5);
            map.put("result", result);
            responseDataBean.setData(map);
            responseDataBean.setMsg("删除操作执行成功");
            responseDataBean.setResResult(success);
        }
        return responseDataBean;
    }

    @Override
    public ResponseDataBean fetchSingleTitle(String word, Pageable pageable) {
        ResponseDataBean responseDataBean = null;
        if (word != null && pageable != null) {
            String limit = "20";
            List<EmployeerBean> result = this.testDbEsHadle.querySingleTitle(word, pageable,limit);
            Map<String, Object> map = new HashMap<String, Object>(5);
            map.put("data", result);
            responseDataBean = new ResponseDataBean();
            responseDataBean.setData(map);
            responseDataBean.setMsg("---->>单字符串模糊查询，默认排序.执行成功");
            responseDataBean.setResResult(success);
            responseDataBean.setCount(10);
            responseDataBean.setCode(0);
        }
        return responseDataBean;
    }

    @Override
    public ResponseDataBean2 fetchSingleTitle2(String word, MyPage pageable) {
        //,String limit
        ResponseDataBean2 responseDataBean  = new ResponseDataBean2();
        if (word != null && pageable != null) {
            Map<String,Object> map = this.testDbEsHadle.querySingleTitle2(word, pageable);
            if(map != null){
                List<EmployeerBean> result = (List<EmployeerBean>) map.get("list");
                String msg = (String) map.get("msg");
                Integer count = (Integer) map.get("count");
                responseDataBean.setData(result);
                responseDataBean.setResResult(success);
                responseDataBean.setCount(count);
                //必须把code设置为0，不然table不认你的数据！
                responseDataBean.setCode(0);
                responseDataBean.setMsg(msg);
                this.logger.info("-----------------------------------查询后获取的数据总条数为:"+result.size());
            }else{
                responseDataBean.setCode(1);
                responseDataBean.setMsg("未能查到相关信息");
            }
        }
        return responseDataBean;
    }

    @Override
    public ResponseDataBean fetchSingleEmployeer(String word, Pageable pageable) {
        ResponseDataBean responseDataBean = null;
        if (word != null && pageable != null) {
            List<EmployeerBean> result = this.testDbEsHadle.querySingleEmployeerBean(word, pageable);
            Map<String, Object> map = new HashMap<String, Object>(5);
            map.put("result", result);
            responseDataBean = new ResponseDataBean();
            responseDataBean.setData(map);
            responseDataBean.setMsg("---->>单字符串模糊查询，单字段（weight权重）排序.执行成功");
            responseDataBean.setResResult(success);
        }
        return responseDataBean;
    }

    @Override
    public ResponseDataBean2 fetchSingleEmployeer2(String word, Pageable pageable) {
        ResponseDataBean2 responseDataBean = null;
        if (word != null && pageable != null) {
            Map<String, Object> map = this.testDbEsHadle.querySingleEmployeerBean2(word, pageable);
            if(map != null){
                List<EmployeerBean> result = (List<EmployeerBean>) map.get("list");
                String msg = (String) map.get("msg");
                Integer count = (Integer) map.get("count");
                responseDataBean = new ResponseDataBean2();
                responseDataBean.setData(result);
                responseDataBean.setResResult(success);
                responseDataBean.setCount(count);
                responseDataBean.setCode(0);
                responseDataBean.setMsg(msg);
            }else{
                responseDataBean.setCode(1);
                responseDataBean.setMsg("未能查到相关信息");
            }
        }
        return responseDataBean;
    }

    @Override
    public ResponseDataBean fetchSingleMatch(String content, Integer userId, Pageable pageable) {
        ResponseDataBean responseDataBean = null;
        if (content != null && userId != null && pageable != null) {
            List<EmployeerBean> result = this.testDbEsHadle.querySingleMatch(content, userId, pageable);
            Map<String, Object> map = new HashMap<String, Object>(5);
            map.put("result", result);
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
        if (content != null && pageable != null) {
            List<EmployeerBean> result = this.testDbEsHadle.querySinglePhraseMatch(content, pageable);
            Map<String, Object> map = new HashMap<String, Object>(5);
            map.put("result", result);
            responseDataBean = new ResponseDataBean();
            responseDataBean.setData(map);
            responseDataBean.setMsg("---->>单字段对某短语进行匹配查询.执行成功");
            responseDataBean.setResResult(success);
        }
        return responseDataBean;
    }

    @Override
    public ResponseDataBean fetchSingleTerm(Integer empNo, Pageable pageable) {
        ResponseDataBean responseDataBean = null;
        if (empNo != null && pageable != null) {
            List<EmployeerBean> result = this.testDbEsHadle.querySingleTerm(empNo, pageable);
            Map<String, Object> map = new HashMap<String, Object>(5);
            map.put("result", result);
            responseDataBean = new ResponseDataBean();
            responseDataBean.setData(map);
            responseDataBean.setMsg("---->>term匹配，即不分词匹配查询.执行成功");
            responseDataBean.setResResult(success);
        }
        return responseDataBean;
    }

    @Override
    public ResponseDataBean fetchMultiMatch(String name, Pageable pageable) {
        ResponseDataBean responseDataBean = null;
        if (name != null && pageable != null) {
            List<EmployeerBean> result = this.testDbEsHadle.queryMultiMatch(name, pageable);
            Map<String, Object> map = new HashMap<String, Object>(5);
            map.put("result", result);
            responseDataBean = new ResponseDataBean();
            responseDataBean.setData(map);
            responseDataBean.setMsg("---->>多字段匹配 查询.执行成功");
            responseDataBean.setResResult(success);
        }
        return responseDataBean;
    }

    @Override
    public ResponseDataBean fetchContain(String name) {
        ResponseDataBean responseDataBean = null;
        if (name != null) {
            List<EmployeerBean> result = this.testDbEsHadle.queryContain(name);
            Map<String, Object> map = new HashMap<String, Object>(5);
            map.put("result", result);
            responseDataBean = new ResponseDataBean();
            responseDataBean.setData(map);
            responseDataBean.setMsg("---->>单字段包含所有输入 查询.执行成功");
            responseDataBean.setResResult(success);
        }
        return responseDataBean;
    }

    @Override
    public ResponseDataBean fetchContain2(String name) {
        ResponseDataBean responseDataBean = null;
        if (name != null) {
            List<EmployeerBean> result = this.testDbEsHadle.queryContain2(name);
            Map<String, Object> map = new HashMap<String, Object>(5);
            map.put("result", result);
            responseDataBean = new ResponseDataBean();
            responseDataBean.setData(map);
            responseDataBean.setMsg("---->>单字段包含所有输入(按比例包含)查询.执行成功");
            responseDataBean.setResResult(success);
        }
        return responseDataBean;
    }

    @Override
    public ResponseDataBean fetchBool(String name, Integer empNo, Date hireDate) {
        ResponseDataBean responseDataBean = null;
        if (name != null) {
            List<EmployeerBean> result = this.testDbEsHadle.queryBool(name, empNo, hireDate);
            Map<String, Object> map = new HashMap<String, Object>(5);
            map.put("result", result);
            responseDataBean = new ResponseDataBean();
            responseDataBean.setData(map);
            responseDataBean.setMsg("---->>合并查询.执行成功");
            responseDataBean.setResResult(success);
        }
        return responseDataBean;
    }

}
