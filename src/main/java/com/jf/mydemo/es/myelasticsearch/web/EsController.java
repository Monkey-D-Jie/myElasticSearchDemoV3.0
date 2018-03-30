package com.jf.mydemo.es.myelasticsearch.web;

import com.jf.mydemo.es.myelasticsearch.commons.RequestDataBean;
import com.jf.mydemo.es.myelasticsearch.commons.ResponseDataBean;
import com.jf.mydemo.es.myelasticsearch.service.EsService;
import com.jf.mydemo.es.myelasticsearch.utils.JsonXmlObjectKit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wjie
 * @date: 2018/3/13 0013
 * @time: 11:11
 * To change this template use File | Settings | File and Templates.
 */
@Controller
//@EnableSpringDataWebSupport
@RequestMapping("/myEsDemo")
public class EsController {
    /**
     * 记录日志信息
     */
    private Logger logger = LogManager.getLogger(EsController.class.getName());

    @Autowired
    private EsService esService;

    @RequestMapping(value = {"/fetchPost"}, method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ResponseDataBean fetchPost(@RequestParam("data") String data){
        ResponseDataBean responseDataBean = null;
        if(data != null){
            Map<String,Object> map = new HashMap<String,Object>(5);
            map.put("postId",data);
            RequestDataBean requestDataBean = new RequestDataBean();
            requestDataBean.setReqData(map);
            responseDataBean  = this.esService.fetchPostById(requestDataBean);
        }
        return responseDataBean;
    }

    @RequestMapping(value = {"/insertOrModifyPost"}, method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ResponseDataBean insertOrModifyPost(@RequestParam("data") String data){
        ResponseDataBean responseDataBean = null;
        if(data != null){
            RequestDataBean requestDataBean = JsonXmlObjectKit.convertJsonObject(data,RequestDataBean.class);
            responseDataBean  = this.esService.insertOrModifyPost(requestDataBean);
        }
        return responseDataBean;
    }

    @RequestMapping(value = {"/removePost"}, method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ResponseDataBean removePost(@RequestParam("data") String data){
        ResponseDataBean responseDataBean = null;
        if(data != null){
            Map<String,Object> map = new HashMap<String,Object>(5);
            map.put("postId",data);
            RequestDataBean requestDataBean = new RequestDataBean();
            requestDataBean.setReqData(map);
            responseDataBean  = this.esService.removePostById(requestDataBean);
        }
        return responseDataBean;
    }

    /**
     * @author: wjie
     * @date: 2018/3/14 0014 13:51
     *
     *测试参照：http://blog.csdn.net/tianyaleixiaowu/article/details/77965257
     */
    /*, method = {RequestMethod.POST}, produces = {"application/json;charset=UTF-8"}*/
    /**
     * @author: wjie
     * @date: 2018/3/14 0014 13:35
     *
     * 在浏览器的url地址栏中访问的结果：
     * http://localhost:8080/myEsDemo/singlePost?word=浣溪沙&size=20
     * 按照默认的排序方式，即匹配相关度排序，
     * 前10个匹配度最高，都是完全带“浣溪沙”三个字的。
     * 第10个、11个都是题目和正文都包含“溪”字而且出现了2次“溪”，最后一个是正文带一个“溪”。
     */
    @RequestMapping(value = {"/singleWord"})
    @ResponseBody
    public ResponseDataBean singleWord(String word, @PageableDefault Pageable pageable){
        ResponseDataBean responseDataBean = null;
        if(word != null && pageable != null){
            responseDataBean  = this.esService.fetchSingleTitle(word,pageable);
        }
        if(this.logger.isInfoEnabled()){
            this.logger.info("在EsController中singleWord，最终得到的返回数据为:<<---"+JsonXmlObjectKit.convertObjectJson(responseDataBean));
        }
        return responseDataBean;
    }

    /**
     * @author: wjie
     * @date: 2018/3/14 0014 13:41
     *
     *单字符串模糊查询，单字段排序。(用weight权重，由高到低来排序)
     * 测试结果：
     * http://localhost:8080/myEsDemo/singlePost?word=浣溪沙&size=20
     *同样的结果，只是这回是按照权重【由高到低】来排序的
     */
    @RequestMapping(value = {"/singlePost"})
    @ResponseBody
    public ResponseDataBean singlePost(String word, @PageableDefault(sort = "weight", direction = Sort.Direction.DESC) Pageable pageable){
        ResponseDataBean responseDataBean = null;
        if(word != null && pageable != null){
            responseDataBean  = this.esService.fetchSinglePost(word,pageable);
        }
        if(this.logger.isInfoEnabled()){
            this.logger.info("在EsController中的singlePost，最终得到的返回数据为:<<---"+JsonXmlObjectKit.convertObjectJson(responseDataBean));
        }
        return responseDataBean;
    }
    /**
     * @author: wjie
     * @date: 2018/3/14 0014 13:59
     *
     *单字段对某字符串模糊查询,使用matchQuery()
     * 测试结果：
     * ①:无uesrId
     *http://localhost:8080/myEsDemo/singleMatch?content=落日熔金size=20
     *得到的是包含‘落日熔金’(全部或者部分)的结果
     * ②:有userId-->用userId来查询
     *http://localhost:8080/myEsDemo/singleMatch?userId=1&size=20
     * 得到的就是userId=1的查询结果（要把querySingleMatch()方法中根据content查询的条件改一下）
     */
    @RequestMapping(value = {"/singleMatch"})
    @ResponseBody
    public ResponseDataBean singleMatch(String content, Integer userId,@PageableDefault Pageable pageable){
        ResponseDataBean responseDataBean = null;
        //userId = 66;
        //content = "66";
        if(content != null && userId != null && pageable != null){
            responseDataBean  = this.esService.fetchSingleMatch(content,userId,pageable);
        }
        if(this.logger.isInfoEnabled()){
            this.logger.info("在EsController中的singleMatch，最终得到的返回数据为:<<---"+JsonXmlObjectKit.convertObjectJson(responseDataBean));
        }
        return responseDataBean;
    }

    /**
     * @author: wjie
     * @date: 2018/3/14 0014 14:25
     *
     *单字段对某短语进行匹配查询，用matchPhraseQuery()
     * http://localhost:8080/myEsDemo/singlePhraseMatch?落日熔金=&size=20
     * 测试结果：
     *就只有一条结果。
     * 说明：它与match()类似，都会先把符合条件的词条搜索出来。
     * 同其不同的是，这里要求短语匹配，即词条得相邻（像那种包含有落日，有熔金的就不行。只能是全权包含‘落日熔金’的才行
     * 。这也是为什么这里只能查出一条数据的原因）
     *有没什么办法能把隔几个词的内容也能查出来呢？
     * 有！
     * 这种完全匹配比较严格，类似于数据库里的“%落日熔金%”这种，使用场景比较狭窄。如果我们希望能不那么严格，
     * 譬如搜索“中华共和国”，希望带“我爱中华人民共和国”的也能出来，
     * 就是分词后，中间能间隔几个位置的也能查出来，可以使用slop参数。
     * 测试结果：
     * http://localhost:8080/myEsDemo/singlePhraseMatch?中华共和国=&size=20
     * 得到的结果：2条
     * content:我爱中华人民共和国,中华共和国
     * PS：slop这里表示跨越之意
     * 更多内容：http://blog.csdn.net/xifeijian/article/details/51090707
     * slop参数告诉match_phrase查询词条能够相隔多远时仍然将文档视为匹配。
     * 相隔多远的意思是，你需要移动一个词条多少次来让查询和文档匹配？
     * 尽管在使用了slop的短语匹配中，所有的单词都需要出现，但是单词的出现顺序可以不同。
     * 如果slop的值足够大，那么单词的顺序可以是任意的。
     * 什么意思喃-->就是说，你要查询的词条只要是【被查询词条】的子集，在slop足够大的情况下，它
     * 就都能算是‘匹配’。只是词条的间隔会不同而已。slop值越大，比对后正确的情况越多。自然就能匹配的更好
     */

    @RequestMapping(value = {"/singlePhraseMatch"})
    @ResponseBody
    public ResponseDataBean singlePhraseMatch(String content,@PageableDefault Pageable pageable){
        ResponseDataBean responseDataBean = null;
        if(content != null && pageable != null){
            responseDataBean  = this.esService.fetchSinglePhraseMatch(content,pageable);
        }
        if(this.logger.isInfoEnabled()){
            this.logger.info("在EsController中的singlePhraseMatch，最终得到的返回数据为:<<---"+JsonXmlObjectKit.convertObjectJson(responseDataBean));
        }
        return responseDataBean;
    }
    /**
     * @author: wjie
     * @date: 2018/3/14 0014 14:59
     *
     *term匹配，即不分词匹配，传哪个就把那个参数拿去做完全匹配，有点类似于 MySQL中的 ==
     *这里是以userId为例
     * 测试结果；
     * http://localhost:8080/myEsDemo/singleTerm?userId=9&size=20
     * PS:
     * 通常情况下，我们不会使用term查询，绝大部分情况我们使用ES的目的就是为了使用它的分词模糊查询功能。
     * 而term一般适用于做过滤器filter的情况，
     * 譬如我们去查询title中包含“浣溪沙”且userId=1时，那么就可以用termQuery("userId", 1)作为查询的filter。
     */
    @RequestMapping(value = {"/singleTerm"})
    @ResponseBody
    public ResponseDataBean singleTerm(Integer userId,@PageableDefault Pageable pageable){
        ResponseDataBean responseDataBean = null;
        if(userId != null && pageable != null){
            responseDataBean  = this.esService.fetchSingleTerm(userId,pageable);
        }
        if(this.logger.isInfoEnabled()){
            this.logger.info("在EsController中的singleTerm，最终得到的返回数据为:<<---"+JsonXmlObjectKit.convertObjectJson(responseDataBean));
        }
        return responseDataBean;
    }

    /**
     * @author: wjie
     * @date: 2018/3/14 0014 15:14
     *
     *多字段匹配，但凡在title或content中包含（全部或部分）有入参‘title’的，都能被查出来
     * 测试结果:
     *http://localhost:8080/myEsDemo/multiMatch?title=我是&size=20
     */
    @RequestMapping(value = {"/multiMatch"})
    @ResponseBody
    public ResponseDataBean multiMatch(String title, @PageableDefault(sort = "weight", direction = Sort.Direction.DESC) Pageable pageable){
        ResponseDataBean responseDataBean = null;
        if(title != null && pageable != null){
            responseDataBean  = this.esService.fetchMultiMatch(title,pageable);
        }
        if(this.logger.isInfoEnabled()){
            this.logger.info("在EsController中的multiMatch，最终得到的返回数据为:<<---"+JsonXmlObjectKit.convertObjectJson(responseDataBean));
        }
        return responseDataBean;
    }

    /**
     * @author: wjie
     * @date: 2018/3/14 0014 15:21
     *
     *单字段包含所有输入
     *
     * 当我们输入“我天”时，ES会把分词后所有包含“我”和“天”的都查询出来，
     * 如果我们希望必须是包含了两个字的才能被查询出来，
     * 那么我们就需要设置一下Operator(在dao层方法中)。
     * 说明:
     * 无论是matchQuery，multiMatchQuery，queryStringQuery等，
     * 都可以设置operator。默认为Or，设置为And后，
     * 就会把符合包含所有输入的才查出来。
     * 测试结果：
     * http://localhost:8080/myEsDemo/contain?title=我是size=20
     * 只有title完全匹配‘我是’的数据被查询出来
     */
    @RequestMapping(value = {"/contain"})
    @ResponseBody
    public ResponseDataBean contain(String title, @PageableDefault(sort = "weight", direction = Sort.Direction.DESC) Pageable pageable){
        ResponseDataBean responseDataBean = null;
        if(title != null && pageable != null){
            responseDataBean  = this.esService.fetchContain(title);
        }
        if(this.logger.isInfoEnabled()){
            this.logger.info("在EsController中的contain，最终得到的返回数据为:<<---"+JsonXmlObjectKit.convertObjectJson(responseDataBean));
        }
        return responseDataBean;
    }

    /**
     * @author: wjie
     * @date: 2018/3/14 0014 15:29
     *
     *单字段包含所有输入(按比例包含)
     * 测试结果:
     *http://localhost:8080/myEsDemo/contain2?title=蝶恋花&size=20
     *
     */
    @RequestMapping(value = {"/contain2"})
    @ResponseBody
    public ResponseDataBean contain2(String title, @PageableDefault(sort = "weight", direction = Sort.Direction.DESC) Pageable pageable){
        ResponseDataBean responseDataBean = null;
        if(title != null && pageable != null){
            responseDataBean  = this.esService.fetchContain2(title);
        }
        if(this.logger.isInfoEnabled()){
            this.logger.info("在EsController中的contain2，最终得到的返回数据为:<<---"+JsonXmlObjectKit.convertObjectJson(responseDataBean));
        }
        return responseDataBean;
    }
    /**
     * @author: wjie
     * @date: 2018/3/14 0014 15:44
     *
     *单字段包含所有输入
     * 即boolQuery，可以设置多个条件的查询方式。它的作用是用来组合多个Query，
     * 有四种方式来组合，must，mustnot，filter，should。
    must代表返回的文档必须满足must子句的条件，会参与计算分值；
    filter代表返回的文档必须满足filter子句的条件，但不会参与计算分值；
    should代表返回的文档可能满足should子句的条件，也可能不满足，
    有多个should时满足任何一个就可以，
    通过minimum_should_match设置至少满足几个。
    mustnot代表必须不满足子句的条件。
    譬如我想查询title包含“XXX”，且userId=“1”，且weight最好小于5的结果。那么就可以使用boolQuery来组合。

     测试结果:
        http://localhost:8080/myEsDemo/bool?title=蝶恋花&userId=8&weight=7&size=20
        根据自己的需求调整dao中方法的参数级别。
        这里是以userId为主，即主要是看userId的值，才决定是否为符合条件的记录
     */
    @RequestMapping(value = {"/bool"})
    @ResponseBody
    public ResponseDataBean bool(String title, Integer userId, Integer weight,@PageableDefault(sort = "weight", direction = Sort.Direction.DESC) Pageable pageable){
        ResponseDataBean responseDataBean = null;
        if(title != null && pageable != null){
            responseDataBean  = this.esService.fetchBool(title,userId,weight);
        }
        if(this.logger.isInfoEnabled()){
            this.logger.info("在EsController中的bool，最终得到的返回数据为:<<---"+JsonXmlObjectKit.convertObjectJson(responseDataBean));
        }
        return responseDataBean;
    }
}
