<%--
  Created by IntelliJ IDEA.
  User: kai
  Date: 2018/3/30
  Time: 下午3:19
  To change this template use File | Settings | File Templates.
--%>
<%@include file="../common/tag.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>任务添加列表</title>
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
        <fieldset class="layui-elem-field"  style="width: 95%;margin: 20px auto">
            <legend>任务添加页面</legend>
            <div class="layui-field-box">
                <form class="layui-form" action="">
                    <input type="hidden" value="${config.vst_qc_id}" id="vst_qc_id" name="vst_qc_id">
                    <div class="layui-form-item">
                        <div class="layui-input-inline">
                            <input type="hidden" name="vst_qc_updator" required  lay-verify="required"
                                   placeholder="编辑人" value="${config.vst_qc_updator}" autocomplete="off" class="layui-input">
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-input-inline">
                            <input type="hidden" name="vst_qc_creator" required  lay-verify="required"
                                   placeholder="添加人" value="${config.vst_qc_creator}" autocomplete="off" class="layui-input">
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-input-inline">
                            <input type="hidden" name="vst_qc_addtime" required  lay-verify="required"
                                   placeholder="添加时间" value="${config.vst_qc_addtime}" autocomplete="off" class="layui-input">
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">任务类型:</label>
                        <div class="layui-input-inline">
                            <select name="vst_qc_type" lay-filter="aihao">
                                <c:if test="${config.vst_qc_type == 1}">
                                    <option value="1" selected>临时任务</option>
                                    <option value="2">定时任务</option>
                                </c:if>

                                <c:if test="${config.vst_qc_type == 2}">
                                    <option value="1">临时任务</option>
                                    <option value="2" selected>定时任务</option>
                                </c:if>

                            </select>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">操作类型:</label>
                        <div class="layui-input-inline">
                            <select name="vst_qc_job_type" lay-filter="aihao">
                                <c:if test="${config.vst_qc_job_type == 1}">
                                    <option value="1" selected>清理</option>
                                    <option value="2">推送</option>
                                    <option value="3">拉取</option>
                                </c:if>

                                <c:if test="${config.vst_qc_job_type == 2}">
                                    <option value="1">清理</option>
                                    <option value="2" selected>推送</option>
                                    <option value="3">拉取</option>
                                </c:if>

                                <c:if test="${config.vst_qc_job_type == 3}">
                                    <option value="1">清理</option>
                                    <option value="2">推送</option>
                                    <option value="3" selected>拉取</option>
                                </c:if>

                            </select>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">执行时间:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="vst_qc_job_cron" required  lay-verify="required"
                                   placeholder="请输入执行时间" value="${config.vst_qc_job_cron}" autocomplete="off" class="layui-input">
                        </div>
                        <div class="layui-form-mid layui-word-aux">任务执行的时间cron表达式</div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">第一阶段:</label>
                        <div class="layui-input-inline">
                            <select name="vst_qc_deal_before">
                                <c:if test="${config.vst_qc_deal_before == 1}">
                                    <option value="1" selected>禁用原表数据</option>
                                    <option value="2">备份</option>
                                    <option value="0">不处理</option>
                                </c:if>

                                <c:if test="${config.vst_qc_deal_before == 2}">
                                    <option value="1">禁用原表数据</option>
                                    <option value="2" selected>备份</option>
                                    <option value="0">不处理</option>
                                </c:if>

                                <c:if test="${config.vst_qc_deal_before == 0}">
                                    <option value="1">禁用原表数据</option>
                                    <option value="2">备份</option>
                                    <option value="0" selected>不处理</option>
                                </c:if>

                            </select>
                        </div>
                        <div class="layui-form-mid layui-word-aux">任务执行第一阶段需要执行的事情</div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">第二阶段:</label>
                        <div class="layui-input-inline">
                            <select name="vst_qc_deal_after">
                                <c:if test="${config.vst_qc_deal_after == 1}">
                                    <option value="1" selected>删除原表数据</option>
                                    <option value="0">不处理</option>
                                </c:if>

                                <c:if test="${config.vst_qc_deal_after == 0}">
                                    <option value="1">删除原表数据</option>
                                    <option value="0" selected>不处理</option>
                                </c:if>

                            </select>
                        </div>
                        <div class="layui-form-mid layui-word-aux">任务执行第二阶段需要执行的事情</div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">备份表的源数据源:</label>
                        <div class="layui-input-inline">
                            <c:forEach items="${result}" var="source">
                                <c:if test="${config.vst_qc_source_name.contains(source)}">
                                    <input type="checkbox" name="source[${source}]" value="${source}" title="${source}"
                                           lay-filter="source" checked>
                                </c:if>
                                <c:if test="${!config.vst_qc_source_name.contains(source)}">
                                    <input type="checkbox" name="source[${source}]" value="${source}" title="${source}"
                                           lay-filter="source">
                                </c:if>

                            </c:forEach>
                        </div>

                        <input type="hidden" id="vst_qc_source_name" name="vst_qc_source_name" value="${config.vst_qc_source_name}">

                        <div class="layui-form-mid layui-word-aux">需要备份数据的数据源</div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">数据库表名:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="vst_qc_table" id="vst_qc_table" required  lay-verify="required"
                                   placeholder="请输入表名" value="${config.vst_qc_table}" autocomplete="off" class="layui-input">
                        </div>
                        <div class="layui-form-mid layui-word-aux">输入对应的数据库表名</div>
                    </div>

                    <input type="hidden" hidden name="vst_qc_entity_name" required
                           placeholder="请输入实体类名" value="${config.vst_qc_entity_name}" autocomplete="off" class="layui-input">


                    <div class="layui-form-item">
                        <label class="layui-form-label">任务状态:</label>
                        <div class="layui-input-inline">
                            <select name="vst_qc_state" disabled>
                                <c:if test="${config.vst_qc_state == 1}">
                                    <option value="1" selected>启动</option>
                                    <option value="2">暂停</option>
                                </c:if>

                                <c:if test="${config.vst_qc_state == 2}">
                                    <option value="1">启动</option>
                                    <option value="2" selected>暂停</option>
                                </c:if>

                            </select>
                        </div>
                        <div class="layui-form-mid layui-word-aux">任务添加完后启动还是暂停</div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">任务名称:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="vst_qc_name" required  lay-verify="required"
                                   placeholder="请输入任务名称" value="${config.vst_qc_name}" autocomplete="off" class="layui-input">
                        </div>
                        <div class="layui-form-mid layui-word-aux">请输入对应的任务名称</div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">触发器名称:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="vst_qc_trigger_name" required  lay-verify="required"
                                   placeholder="请输入触发器名称" value="${config.vst_qc_trigger_name}" autocomplete="off" class="layui-input">
                        </div>
                        <div class="layui-form-mid layui-word-aux">请输入对应的触发器名称</div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">处理条件:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="vst_qc_condition" required  lay-verify="required"
                                   placeholder="请输入处理条件"  autocomplete="off" class="layui-input"
                            value="${config.vst_qc_condition}">
                        </div>
                        <div class="layui-form-mid layui-word-aux">请输入任务操作时的处理条件,默认1=1</div>
                    </div>

                    <div class="layui-form-item" id="vst_qc_backup">
                        <label class="layui-form-label">备份选项:</label>
                        <div class="layui-input-inline">
                            <select name="vst_qc_backup" lay-filter="backUp">
                                <c:if test="${config.vst_qc_backup == 1}">
                                    <option value="1" selected>备份表</option>
                                    <option value="2">备份文件</option>
                                    <option value="3">两者都备份</option>
                                </c:if>

                                <c:if test="${config.vst_qc_backup == 2}">
                                    <option value="1">备份表</option>
                                    <option value="2" selected>备份文件</option>
                                    <option value="3">两者都备份</option>
                                </c:if>

                                <c:if test="${config.vst_qc_backup == 3}">
                                    <option value="1">备份表</option>
                                    <option value="2">备份文件</option>
                                    <option value="3" selected>两者都备份</option>
                                </c:if>



                            </select>
                        </div>
                        <div class="layui-form-mid layui-word-aux">数据清除时的备份类型</div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">表备份类型:</label>
                        <div class="layui-input-inline">
                            <select name="vst_qc_backup_type">
                                <c:if test="${config.vst_qc_backup_type == 1}">
                                    <option value="1" selected>根据表字段备份</option>
                                    <option value="2">全表备份</option>
                                    <option value="3">根据清除条件备份</option>
                                </c:if>

                                <c:if test="${config.vst_qc_backup_type == 2}">
                                    <option value="1">根据表字段备份</option>
                                    <option value="2" selected>全表备份</option>
                                    <option value="3">根据清除条件备份</option>
                                </c:if>

                                <c:if test="${config.vst_qc_backup_type == 3}">
                                    <option value="1">根据表字段备份</option>
                                    <option value="2">全表备份</option>
                                    <option value="3" selected>根据清除条件备份</option>
                                </c:if>

                            </select>
                        </div>
                        <div class="layui-form-mid layui-word-aux">数据清除时的备份类型</div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">备份条件说明:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="vst_qc_backup_condition" required
                                   placeholder="请输入备份条件" value="${config.vst_qc_backup_condition}" autocomplete="off" class="layui-input">
                        </div>
                        <div class="layui-form-mid layui-word-aux">如果根据表字段备份，请填写字段，如果其余两种备份，不需要填写</div>
                    </div>



                    <div class="layui-form-item">
                        <label class="layui-form-label">备份表的目标数据源:</label>
                        <div class="layui-input-inline">
                            <c:forEach items="${result}" var="source">
                                <c:if test="${config.vst_qc_target_source_name.contains(source)}">
                                    <input type="checkbox" name="source[${source}]" value="${source}" title="${source}"
                                           lay-filter="target" checked>
                                </c:if>

                                <c:if test="${!config.vst_qc_target_source_name.contains(source)}">
                                    <input type="checkbox" name="source[${source}]" value="${source}" title="${source}"
                                           lay-filter="target">
                                </c:if>
                            </c:forEach>
                        </div>
                        <input type="hidden" id="vst_qc_target_source_name" name="vst_qc_target_source_name"
                               value="${config.vst_qc_target_source_name}">

                        <div class="layui-form-mid layui-word-aux">需要将数据备份到那个指定的库(多个用逗号隔开:A,B,C)</div>
                    </div>

                    <div class="layui-form-item" id="tarbet_table_name" >
                        <label class="layui-form-label">选择备份表:</label>

                        <input type="hidden" name="vst_qc_tarbet_table_name" id="vst_qc_tarbet_table_name"
                               value="${config.vst_qc_tarbet_table_name}">

                        <input type="hidden" name="source_name" id="source_name" value="${source_name}">
                        <div class="layui-input-inline">
                            <a class="layui-btn" id="select">选择备份表</a>
                        </div>
                    </div>


                    <div class="layui-form-item" id="vst_qc_file_format">
                        <label class="layui-form-label">备份文件格式:</label>
                        <div class="layui-input-inline">
                            <select name="vst_qc_file_format">
                                <c:if test="${config.vst_qc_file_format == 1}">
                                    <option value="1" selected>json格式</option>
                                    <option value="2">excel格式</option>
                                    <option value="3">sql格式</option>
                                </c:if>

                                <c:if test="${config.vst_qc_file_format == 2}">
                                    <option value="1">json格式</option>
                                    <option value="2" selected>excel格式</option>
                                    <option value="3">sql格式</option>
                                </c:if>

                                <c:if test="${config.vst_qc_file_format == 3}">
                                    <option value="1">json格式</option>
                                    <option value="2">excel格式</option>
                                    <option value="3" selected>sql格式</option>
                                </c:if>

                            </select>
                        </div>
                        <div class="layui-form-mid layui-word-aux">选择你所需要备份的文件格式</div>
                    </div>


                    <div class="layui-form-item" id="vst_qc_file_address">
                        <label class="layui-form-label">文件备份地址:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="vst_qc_file_address" required value="${config.vst_qc_file_address}"
                                   placeholder="请输入文件备份地址" autocomplete="off" class="layui-input">
                        </div>
                        <div class="layui-form-mid layui-word-aux">如果没有填写:默认地址:/vst/cibnvst-bi-quartz/file/</div>
                    </div>


                    <div class="layui-form-item" id="vst_qc_is_compression">
                        <label class="layui-form-label">是否压缩</label>
                        <div class="layui-input-inline">
                            <c:if test="${config.vst_qc_is_compression == 1}">
                                <input type="checkbox" name="switch" checked lay-skin="switch" lay-text="是|否" lay-filter = "switch">
                            </c:if>

                            <c:if test="${config.vst_qc_is_compression == 2}">
                                <input type="checkbox" name="switch" lay-skin="switch" lay-text="是|否" lay-filter = "switch">
                            </c:if>
                            <input type="hidden" id="is_compression" name="vst_qc_is_compression" value="${config.vst_qc_compression_address}">
                        </div>
                    </div>

                    <div class="layui-form-item" id="vst_qc_compression_format" >
                        <label class="layui-form-label">压缩格式:</label>
                        <div class="layui-input-inline">
                            <select name="vst_qc_compression_format">
                                <c:if test="${config.vst_qc_compression_format == 1}">
                                    <option value="1" selected>ZIP格式</option>
                                    <option value="2">JAR格式</option>
                                    <option value="3">GZIP格式</option>
                                    <option value="4">TAR格式</option>
                                </c:if>

                                <c:if test="${config.vst_qc_compression_format == 2}">
                                    <option value="1">ZIP格式</option>
                                    <option value="2" selected>JAR格式</option>
                                    <option value="3">GZIP格式</option>
                                    <option value="4">TAR格式</option>
                                </c:if>

                                <c:if test="${config.vst_qc_compression_format == 3}">
                                    <option value="1">ZIP格式</option>
                                    <option value="2">JAR格式</option>
                                    <option value="3" selected>GZIP格式</option>
                                    <option value="4">TAR格式</option>
                                </c:if>

                                <c:if test="${config.vst_qc_compression_format == 4}">
                                    <option value="1">ZIP格式</option>
                                    <option value="2">JAR格式</option>
                                    <option value="3">GZIP格式</option>
                                    <option value="4" selected>TAR格式</option>
                                </c:if>

                            </select>
                        </div>
                        <div class="layui-form-mid layui-word-aux">选择你所需要压缩的文件格式,默认zip格式</div>
                    </div>


                    <div class="layui-form-item" id="vst_qc_compression_address" >
                        <label class="layui-form-label">文件压缩地址:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="vst_qc_compression_address" required value="${config.vst_qc_compression_address}"
                                   placeholder="请输入文件压缩地址" autocomplete="off" class="layui-input">
                        </div>
                        <div class="layui-form-mid layui-word-aux">如果没有填写:则自动压缩在文件备份地址</div>
                    </div>



                    <div class="layui-form-item">
                        <label class="layui-form-label">任务描述:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="vst_qc_summary" value="${config.vst_qc_summary}" required
                                   placeholder="请输入任务描述"  autocomplete="off" class="layui-input">
                        </div>
                        <div class="layui-form-mid layui-word-aux">对任务的描述</div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-input-block">
                            <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
                            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                        </div>
                    </div>
                </form>
            </div>

        </fieldset>


    </div>

<%@include file="../common/footerjs.jsp"%>
<script type="text/javascript">
    layui.use(['form', 'layedit', 'laydate'], function() {
        var form = layui.form;


        //监听提交
        form.on('submit(formDemo)', function(data){
//            layer.msg(JSON.stringify(data.field));

            $.ajax({
                type: "POST",
                dataType: "json",
                url: "${ctx}/config/update",
                data: data.field,
                success:function (res) {
                    if (res.code === 200){
                        alert(res.msg);
                        window.location.href="${ctx}/config/index";
                    }else {
                        alert(res.msg);
                    }
                }


            });

            return false;
        });


        //监听源数据源选择框
        form.on('checkbox(source)', function(data){
            console.log(data)
            console.log(this.checked)

            source("#vst_qc_source_name",data.value,this.checked)
        });


        //监听目标数据源选择框
        form.on('checkbox(target)', function(data){

            source("#vst_qc_target_source_name",data.value,this.checked)

            //点击源数据源之后如果this.checked为false则需要清除弹框中选中的该数据源的表明，加入存在这条数据的话
            if (!this.checked){
                //获取弹框中选中的备份表数据
                let source_name = $("#source_name").val();

                console.log("source:"+source_name);

                let source = [];
                if (source_name.includes(",")){
                    source = source_name.split(",");
                }else {
                    source[0] = source_name;
                }

                //清除值
                source.forEach(function (item,index) {
                    if (item.includes(data.value)){
                        source.splice(index,1);
                    }
                });

                //重新组合
                let name = '';
                source.forEach(function (item) {
                    name+=item+",";
                });


                name = name.substring(0,name.lastIndexOf(","));

                console.log("name: "+name)


                $("#source_name").val(name);

            }
        });


        //点击压缩启动或者暂停
        form.on('switch(switch)',function (data) {
            if (data.elem.checked){
                $("#is_compression").val(1);
            }else {
                $("#is_compression").val(2);
            }
        });



        //点击选择按钮选择数据源
        $("#select").on('click',function () {
            select();
        });

        /**
         * 选择数据源
         * @param obj 指定值的标签id
         * @param name 数据源名称
         * @param flag 添加数据源还是删除删除数据源
         */
        function source(obj,name,flag) {
            console.log(obj+"---"+name+"----"+flag)
            //获取标签id的值
            let source = $(obj).val()+"";
            let source_name;
            console.log(source);
            //判断source中是否包含name
            if (source.includes(name)){
                //如果flag为true，表示需要将name添加到source中,但是source发现存在，则无需添加，否则，删除
                if (!flag){
                    //判断是否包含,
                    if (source.includes(",")){
                        console.log("ddd");
                        source_name = source.split(",");
                        console.log(source_name)

                    }else {
                        source_name = new Array(source);
                    }


                    let s = "";
                    //删除数据源为name的数据
                    source_name.forEach(function (value,index) {
                        console.log(value)
                        //如果相等则删除
                        if (value === name){
                            console.log(index);
                            console.log(value+"---"+name);
                            source_name.splice(index,1);
                        }
                    });

                    console.log("111");

                    console.log("剩余："+source_name)
                    //遍历剩余的数据
                    source_name.forEach(function (value) {
                        s+=value+",";
                    });
                    source=s.substring(0,s.lastIndexOf(","));
                    $(obj).val(source)

                }
            }else {
                if (flag){
                    if (source === null || source === ''){
                        source=name;
                    }else {
                        source = source+","+name;
                    }
                    $(obj).val(source)
                }
            }
        }


        /**
         * 选择备份表表名
         */
        function select() {
            //获取目标数据源
            let value = $("#vst_qc_target_source_name").val();
            //获取input，id=source_name中的值，这个值主要用于保存table.jsp返回的值
            let source_name = $("#source_name").val();
            if (value === null || value ==='' || value === undefined){
                alert("请选择目标数据源");
                return;
            }

            console.log(value);
            //获取要备份的表名
            let table = $("#vst_qc_table").val();

            if (table === null || table === "" || table === undefined){
                alert("请输入源数据表名");
                return;
            }

            console.log("sourceName:"+source_name);
            console.log("s:"+JSON.stringify(source_name));

            layer.open({
                type: 2,
                title: 'layer mobile页',
                shadeClose: true,
                shade: 0.8,
                area: ['800px', '90%'],
                content: '${ctx}/config/table?db='+value+"&table="+table+"&sourceName="+encodeURI(source_name), //iframe的url
                btn: ['确定','关闭'],
                yes: function(index,layero){
                    //当点击‘确定’按钮的时候，获取弹出层返回的值
                    let r = $(layero).find("iframe")[0].contentWindow.queryValue();

                    $("#source_name").val(r);

                    //打印返回的值，看是否有我们想返回的值。
                    console.log(r);

                    let source_table = ("{"+r+"}");
                    $("#vst_qc_tarbet_table_name").val(source_table);

                    console.log(source_table);

                    //最后关闭弹出层
                    layer.close(index);
                },
                cancel: function(){
                    //右上角关闭回调
                }
            });

        }

    });
</script>
</body>
</html>
