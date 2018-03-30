<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>我的简易搜索页面</title>
    <!-- 引入css样式 -->
    <link rel="stylesheet" href="static/layui/css/layui.css">
    <!-- 引入javasrcipt的支持 -->
    <script type="text/javascript" src="static/layui/layui.js"></script>
    <!-- 引入jquery 的支持 -->
    <script type="text/javascript" src="static/jquery/jquery-3.1.1.min.js"></script>
    <style type="text/css">
        /**图片的css---为了让其居中(至少搁谷歌浏览器里是这样)*/
        .imgBox {
            margin-top: 30px;
            width: 100%;
            height: 100%;
            border: 1px solid #000;
        }

        .imgStyle {
            width: 100%;
            height: 100%;
        }

        /*让放置轮播图的div容器居中*/
        body {
            margin: 0;
            padding: 0;
        }

        .myCarouselClass {
            /*width: 50%;*/
            /*height: 300px;*/
            margin: 0 auto;
        }

        .wj-layui-input {
            display: inline-block;
            width: 80%;
        }

        .wj-layui-btn {
            width: 18%;
        }

        .wj-search {
            position: inherit;
            /*这里面几个参数值的意思：依次是‘上右下左’，只是这三个参数的意思：上外面局30，右给到的是自动(自动调齐两边)，下边距15px*/
            margin: 15px auto 5px;
            width: 905px;
        }

        /*单选框的样式布局说明*/
        .radioClass {
            margin: auto;
        }
        /*高亮样式*/
        .highlight {
            background: #ff421f;
        }

    </style>
</head>
<body>
<!-- 条目中可以是任意内容，如：<img src=""> -->
<div class="layui-carousel myCarouselClass" id="myCarousel">
    <div carousel-item>
        <div class="imgBox"><img class="imgStyle" src="static/resources/images/图片1.jpg"></div>
        <div class="imgBox"><img class="imgStyle" src="static/resources/images/图片2.jpg"></div>
        <div class="imgBox"><img class="imgStyle" src="static/resources/images/图片3.jpg"></div>
        <div class="imgBox"><img class="imgStyle" src="static/resources/images/图片4.jpg"></div>
        <div class="imgBox"><img class="imgStyle" src="static/resources/images/图片5.jpg"></div>
    </div>
</div>
<!-- 搜索框 -->
<div class="layui-form-item">
    <div class="layui-input-block wj-search">
        <input type="text" name="name"  style="margin-right: 14px" id="searchText" required
               lay-verify="required" placeholder="请输入查询信息"
               autocomplete="off" class="layui-input wj-layui-input">
        <button class="layui-btn layui-btn-warm wj-layui-btn" id="searchButton">点我搜索</button>
        <div class="radioClass">
            <input id="fullText" style="margin-right: 5px;margin-bottom: 5px" type="radio" name="search"
                   value="1" title="全匹配">全匹配
            <input type="radio" id="fuzzyText" style="margin-right: 5px;margin-bottom: 5px" name="search" value="2"
                   title="模糊搜索">模糊搜索
            <select name="sortType" id="sortType" style="margin: 10px" lay-verify="">
                <option value="">排序方式</option>
                <option value="1">按相关度</option>
                <option value="2">按匹配度</option>
            </select>
        </div>
    </div>
</div>


<!-- 搜索结果展示 +分页组件-->
<div style="position: inherit;margin: auto;width:max-content">
    <div>
        <%--<textarea name="searchTip" id="searchResult" style="width: 905px;margin: auto;" required lay-verify="required"
                  readonly="true"
                  placeholder="显示搜索的结果" class="layui-textarea">
            <span><font color="red">${sessionScope.msg} </font></span>
        </textarea>--%>
        <blockquote hidden="true" id="resultBox" style="width: 875px;margin: auto;" class="layui-elem-quote">
            <span id="searchResult2"><font size="10px"></font></span>
        </blockquote>
    </div>
    <table class="layui-hide" id="infoTable" lay-filter="demo" lay-size="lg"></table>

    <script type="text/html" id="modifyBar">
        <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
        <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </script>
    <%--<div class="pageClass" id="myPage">分页展示</div>--%>
</div>

<!-- 单独的分页组件(可选) -->

<script>

    //    获取到访问路径
    var ctx = '${ctx}';
    layui.use(['laypage', 'table', 'carousel'], function () {
        var laypage = layui.laypage //分页
            , carousel = layui.carousel
            , table = layui.table //表格

        //向世界问个好
//        layer.msg('Hello World');

//        高亮搜索的关键词
        function highlight(text, words, tag) {
            // 默认的标签，如果没有指定，使用span
            tag = tag || 'span';
            var i, len = words.length, re;
            for (i = 0; i < len; i++) {
                // 正则匹配所有的文本
                re = new RegExp(words[i], 'g');
                if (re.test(text)) {
                    text = text.replace(re, '<'+ tag +' class="highlight">$&</'+ tag +'>');
                }
            }
            return text;
        }

        /*保证刷新之后，当前页面的搜索框中还有数据*/
        //页面一旦加载即触发；


        $(function () {
            var boxText = sessionStorage.getItem("searchText");
            var radioTemp =  sessionStorage.getItem("radioValue");
            if (radioTemp == null || radioTemp == ""){
                $("input[type='radio'][value='1']").attr("checked",true);
            }else{
                //下面的两个方法都能实现给radio赋值
                $("input[type='radio'][value="+ radioTemp+"]").attr("checked",true);
//                $("input[type='radio'][value=" + radioTemp + "]").prop("checked", true);
            }
            if(boxText != "" && boxText != null){
                $("#searchText").attr("value",boxText);
                initTable(boxText,radioTemp);
            }
        });
//        点击搜索后才初始化下方表格的方法测试
        $("#searchButton").click(function () {
            var searchText = $("#searchText").val();
            if (searchText != null && searchText != "") {
                //获取到单选框的值
                var radioValue = $('input[name="search"]:checked').val();
                initTable(searchText,radioValue);
                sessionStorage.setItem("radioValue",radioValue);
            }else{
//                layer.msg("查询信息不能为空");
                layer.tips('搜索条件不能为空哦', '#searchText', {
                    tips: [1, '#313131'],
                    time:1000
                });
            }
        });

        function initTable(searchText,radioValue) {
            var load = layer.load(1, {
                shade: [0.3,'#c4caff'] //0.1透明度的白色背景
            });
            /*确定查询方式*/
            //获取用session来存searchText的值
            sessionStorage.setItem("searchText",searchText);
            var urlValue = "";
            //  1 全匹配；2 模糊搜索
            if(radioValue == "1"){
                urlValue =  '/myEsTestDbDemo/singleWord?name='+searchText;
            }else if(radioValue == "2"){
                urlValue =  '/myEsTestDbDemo/singleEmployeer?name='+searchText;
            }
//                然后才加载表格
            table.render({
                elem: '#infoTable'
                , height: 400
                , unresize: true
                , skin: 'row' //行边框风格
                , even: true //开启隔行背景
                , url: ctx+urlValue //数据接口
                , page: true //开启分页
                , cols: [[ //表头
                    {field: 'empNo', title: '员工编号', width: 100, align: 'center', sort: true, fixed: 'left'}
                    , {field: 'formatBirthDate', title: '出生日期', width: 160, align: 'center', sort: true}
                    , {field: 'firstName', title: '姓', align: 'center', width: 100}
                    , {field: 'lastName', title: '名', align: 'center', width: 100}
                    , {field: 'gender', title: '性别', align: 'center', width: 60}
                    , {field: 'formatHireDate', title: '入职时间', align: 'center', width: 160, sort: true}
                    , {fixed: 'right', width: 200, align: 'center', toolbar: '#modifyBar'}
                ]]
                , done: function (res, curr, count) {
                    //如果是异步请求数据方式，res即为你接口返回的信息。
                    //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
//                console.log(res);
//                layer.msg(res.msg);
                    //得到当前页码
                    layer.close(load);
                    console.log(curr);
                    //得到数据总量
                    console.log(count);
//                        $("#searchResult").text(res.msg);
                    if(res.msg != null && res.code == 0){
                        var words = [searchText];
                        var tag = 'span';
                        $("#resultBox").show();
                        $("#searchResult2").text(res.msg+"搜索词:"+searchText).css("background-color","yellow");
                        /*
                         * @author: wjie
                         * @date: 2018/3/28 0028 17:01
                         *$("#searchResult2").css
                         * 高亮参考链接:http://blog.jdk5.com/zh/javascript-highlighting-text/
                         *  我用的话，没弄上效果--》预期的效果是：【搜索词:xxx】中的xxx为高亮显示
                         */
//                           暂时这样处理
//                            $("#searchResult2").css("background-color","yellow");
                        /*高亮测试--->高亮没起到作用*/
//                            var $p = $('p');
//                            $('#highlight').click(function(){
//                            $p.html( unhighlight($p.html()) );
//                            $p.html( highlight($p.html(), words) );
//                            });
                    }
                }
            });

            table.on('tool(demo)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
                var data = obj.data //获得当前行数据
                    , layEvent = obj.event; //获得 lay-event 对应的值
                if (layEvent === 'detail') {
                    layer.msg('查看操作');
                } else if (layEvent === 'del') {
                    layer.confirm('真的删除行么', function (index) {
                        obj.del(); //删除对应行（tr）的DOM结构
                        layer.close(index);
                        //向服务端发送删除指令
                    });
                } else if (layEvent === 'edit') {
                    layer.msg('编辑操作');
                }
            });
        }
        //执行一个 table 实例
        /*table.render({
            elem: '#infoTable'
            , height: 400
            , unresize: true
            , skin: 'row' //行边框风格
            , even: true //开启隔行背景
            , url: ctx + '/myEsTestDbDemo/singleWord?name=Stein' //数据接口
            , page: true //开启分页
            , cols: [[ //表头
                {field: 'empNo', title: '员工编号', width: 100, align: 'center', sort: true, fixed: 'left'}
                , {field: 'formatBirthDate', title: '出生日期', width: 160, align: 'center', sort: true}
                , {field: 'firstName', title: '姓', align: 'center', width: 100}
                , {field: 'lastName', title: '名', align: 'center', width: 100}
                , {field: 'gender', title: '性别', align: 'center', width: 60}
                , {field: 'formatHireDate', title: '入职时间', align: 'center', width: 160, sort: true}
                , {fixed: 'right', width: 200, align: 'center', toolbar: '#modifyBar'}
            ]]
            , done: function (res, curr, count) {
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                console.log(res);
//                layer.msg(res);
                //得到当前页码
                console.log(curr);
                //得到数据总量
                console.log(count);
//                $("#searchResult").text(res.msg);
                $("#searchResult2").text(res.msg).css("background-color","yellow");


            }
            ,text: {
                none: '暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
            }
        });*/

        //监听工具条
       /* table.on('tool(demo)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data //获得当前行数据
                , layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent === 'detail') {
                layer.msg('查看操作');
            } else if (layEvent === 'del') {
                layer.confirm('真的删除行么', function (index) {
                    obj.del(); //删除对应行（tr）的DOM结构
                    layer.close(index);
                    //向服务端发送删除指令
                });
            } else if (layEvent === 'edit') {
                layer.msg('编辑操作');
            }
        });*/
        //执行一个轮播实例
        carousel.render({
            elem: '#myCarousel'
            , width: 905 //设置容器宽度
            , height: 350
            , autoplay: true
            , arrow: 'always' //显示箭头
            , anim: 'fade' //切换动画方式
        });

        //执行一个laypage实例
        //分页
        laypage.render({
            elem: 'myPage' //分页容器的id
            , count: 100 //总页数
            , skin: '#1E9FFF' //自定义选中色值
            //,skip: true //开启跳页
            , jump: function (obj, first) {
                if (!first) {
                    layer.msg('第' + obj.curr + '页');
                }
            }
        });

    });
</script>
</body>
</html>
<!-- -->