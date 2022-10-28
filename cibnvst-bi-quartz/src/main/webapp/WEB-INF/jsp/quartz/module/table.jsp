<%--
  Created by IntelliJ IDEA.
  User: kai
  Date: 2018/4/25
  Time: 下午2:42
  To change this template use File | Settings | File Templates.
--%>
<%@include file="../common/tag.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <%@include file="../common/header.jsp"%>

    <style type="text/css">

        .layui-field-box{
            margin-left: 500px;

        }

        .layui-form-item .layui-input-inline {
            float: left;
            width: 400px;
            margin-right: 10px;
        }
    </style>
</head>
<body>
   <div class="admin">
           <div class="layui-tab layui-tab-card" lay-filter="tableTab"  >
               <input type="hidden" id="vst_qc_table" name="vst_qc_table" value="${table}">
               <ul class="layui-tab-title">
                   <c:forEach items="${db}" var="source" varStatus="status">
                       <c:if test="${status.index == 0}">
                           <li class="layui-this">${source}</li>
                       </c:if>
                       <c:if test="${status.index != 0}">
                           <li>${source}</li>
                       </c:if>
                   </c:forEach>
               </ul>
               <!--用于保存选中的备份表信息-->
               <input type="hidden" id="source_table" value="${sourceName}">
               <div class="layui-tab-content">
                    <c:forEach items="${db}" var="source" varStatus="status">
                        <c:if test="${status.index == 0}">
                            <div class="layui-tab-item layui-show">
                                <form class="layui-form" action="">
                                    <div class="layui-form-item">
                                        <div class="layui-input-block" style="margin: 0">
                                            <c:if test="${data != null}">
                                                <c:forEach items="${data}" var="data" varStatus="status">
                                                    <c:if test="${sourceName != null && sourceName != ''}">
                                                        <c:if test="${sourceName.contains(source)}">
                                                            <c:if test="${sourceName.contains(data.TABLE_NAME)}">
                                                                <input type="checkbox" data-sid="${source}" checked  value="${data.TABLE_NAME}" name="like[${data.TABLE_NAME}]"
                                                                       title="${data.TABLE_NAME}" lay-filter="tabSource">
                                                            </c:if>

                                                            <c:if test="${!sourceName.contains(data.TABLE_NAME)}">
                                                                <input type="checkbox" data-sid="${source}"  value="${data.TABLE_NAME}" name="like[${data.TABLE_NAME}]"
                                                                       title="${data.TABLE_NAME}" lay-filter="tabSource">
                                                            </c:if>
                                                        </c:if>

                                                        <c:if test="${!sourceName.contains(source)}">
                                                            <input type="checkbox" data-sid="${source}"  value="${data.TABLE_NAME}" name="like[${data.TABLE_NAME}]"
                                                                   title="${data.TABLE_NAME}" lay-filter="tabSource">
                                                        </c:if>
                                                    </c:if>

                                                    <c:if test="${sourceName == null || sourceName == ''}">
                                                        <input type="checkbox" data-sid="${source}"  value="${data.TABLE_NAME}" name="like[${data.TABLE_NAME}]"
                                                               title="${data.TABLE_NAME}" lay-filter="tabSource">
                                                    </c:if>

                                                </c:forEach>
                                            </c:if>
                                        </div>

                                    </div>
                                </form>
                            </div>
                        </c:if>

                        <c:if test="${status.index != 0}">
                            <div class="layui-tab-item">
                                <form class="layui-form" action="">
                                    <div class="layui-form-item">
                                        <div class="layui-input-block" style="margin: 0">
                                            <%--<input type="checkbox" name="like[write]" title="写作">--%>
                                        </div>

                                    </div>
                                </form>
                            </div>
                        </c:if>
                    </c:forEach>
               </div>
           </div>
   </div>
   <%@include file="../common/footerjs.jsp"%>
<script type="text/javascript">
    layui.use(['form', 'layer','element'],function () {
        let element = layui.element;

        let form = layui.form;

        let layer = layui.layer;

        //选择项tab触发
        element.on('tab(tableTab)',function (data) {
            console.log(data);
            search(data.elem.context.innerText)
        });

        //监听目标数据源选择框
        form.on('checkbox(tabSource)', function(data){
            console.log(data);
            console.log(data.elem.dataset.sid);
            console.log(this.checked);

            source("#source_table",data.value,this.checked,data.elem.dataset.sid)

        });



        //根据数据源，和表名模糊匹配数据源中对应的备份表
        function search(db) {

            console.log(db);
            //获取表名
            let table = $("#vst_qc_table").val();


            if (table === null || table === "" || table === undefined){
                alert("请选择源数据表名");
                return;
            }

            let $layui = layui;

            $.ajax({
                type:'get',
                url:'${ctx}/config/query',
                data:{db:db,table:table},
                dataType:'json',
                success:function (res) {
                    if (res.code === 200){
                        let data = res.data;
                        console.table(data);

                        //获取input中存储的数据
                        let table = $("#source_table").val();


                        let source = [];
                        if (table  !== null || table !== '') {
                            if (table.includes(",")) {
                                source = table.split(",");
                            } else {
                                source[0] = table;
                            }
                        }

                        let db_name = '';
                        source.forEach(function (item) {
                            if (item.includes(db)){
                                db_name  = item;
                            }
                        });



                        $(".layui-input-block").html("");

                        data.forEach(function (item) {
                            if (db_name.includes(item.TABLE_NAME)){
                                console.log(111);
                                let html='<input type="checkbox"  lay-filter="tabSource" checked data-sid="'+db+'"  value="'+item.TABLE_NAME+'" name="like['+item.TABLE_NAME+']" title="'+item.TABLE_NAME+'">';
                                $(".layui-input-block").append(html);
                            }else {
                                console.log(222);
                                let html='<input type="checkbox"  lay-filter="tabSource" data-sid="'+db+'"  value="'+item.TABLE_NAME+'" name="like['+item.TABLE_NAME+']" title="'+item.TABLE_NAME+'">';
                                $(".layui-input-block").append(html);
                            }
                        });

                        //从新渲染页面
                        $layui.use('form', function(){
                            let form = $layui.form;
                            //根据的type类型修改
                            form.render('checkbox');
                        });

                    }else {
                        $(".layui-input-block").html("");
                        alert(res.msg)
                    }
                }
            });
        }

        /**
         * kai
         * 保存选中的备份表信息
         * @param obj 保存备份表信息的标签id
         * @param name 选中的表名信息
         * @param flag 是选中还是删除
         * @param db 获取数据源名称
         */
        function source(obj,name,flag,db) {
            //获取标签id的值，格式 数据源/表1:表2:表3,数据源1/表1:表2:表3
            let table = $(obj).val();
            //判断flag是否true，如果是true表示需要添加,否则删除
            if (flag){
                //判断是否存在该数据源对应的数据,如果存在,则获取table中该数据源部分
                if (table.includes(db)){
                    let source = [];
                    if (table.includes(",")){
                        source = table.split(",");
                    }else {
                        source[0] = table;
                    }

                    //用于从新拼接source中的值
                    let t = "";

                    //遍历所有选中的数据
                    source.forEach(function (item) {

                        if (item.includes(db)){
                            //判断name在item中是否存在,如果存在则不添加
                            if (item.includes(name)){
                                return;
                            }

                            item = item.substring(0,item.lastIndexOf("'"))+name+"|'";

                            t+=item+",";
                        }else {
                            t+=item+",";
                        }
                    });

                    table = t.substring(0,t.lastIndexOf(","));

                   $(obj).val(table);
                }else {
                    if (table === null || table === ''){
                        table = "'"+db+"':'"+name+"|'";
                    }else {
                        table = table+",'"+db+"':'"+name+"|'";
                    }


                    $(obj).val(table);
                }
            }else {
                if (table.includes(db)){
                    let source = [];
                    if (table.includes(",")){
                        console.log(333);
                        source = table.split(",");
                    }else {
                        console.log(444);
                        source[0] = table;
                    }

                    console.log(source);


                    source.forEach(function (item,index) {
                        console.log(item);
                        //如果找到该数据源，进入
                        if (item.includes(db)){

                            //如果item中包含name则将name去掉
                            if (item.includes(name)){
                                item = item.replace(name+"|","");
                                if (item.replace("'"+db+"':''","") === '' || item.replace("'"+db+"':''","") === null){
                                    console.log("删除");
                                    source.splice(index,1);
                                }
                            }
                        }
                    });

                    console.log(source);

                    //用于从新获取数据
                    let t="";
                    //重新遍历source数据
                    source.forEach(function (item,index) {
                        t += item+",";
                    });


                    table = t.substring(0,t.lastIndexOf(","));

                    $(obj).val(table);
                }

            }

            console.log("最后结果:"+table);

        }

    });


    /**
     * 获取选中的数据返回给主页面
     */
    function queryValue() {
        let value = $("#source_table").val();

        return value;
    }


</script>
</body>
</html>
