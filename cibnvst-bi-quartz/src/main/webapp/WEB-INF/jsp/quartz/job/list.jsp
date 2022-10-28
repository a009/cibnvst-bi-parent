<%--
  Created by IntelliJ IDEA.
  User: kai
  Date: 2018/3/29
  Time: 下午6:43
  To change this template use File | Settings | File Templates.
--%>
<%@include file="../common/tag.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>任务列表</title>
    <%@include file="../common/header.jsp"%>

</head>
<body>
    <fieldset class="layui-elem-field" style="width: 95%;margin: 10px auto">
        <legend>任务列表页面</legend>

        <div class="layui-field-box">
            <div class="demoTable">
                搜索任务名：
                <div class="layui-inline">
                    <input class="layui-input" name="id" id="jobName" autocomplete="off">
                </div>
                <button class="layui-btn" data-type="reload">搜索</button>

                <a href="${ctx}/config/add" class="layui-btn">添加任务</a>
            </div>

            <table class="layui-table" lay-data="{ height: 332, url:'${ctx}/config/list/', page:true, id:'idTest'}"
                   lay-filter="demo">
                <thead>
                <tr>
                    <th lay-data="{field:'vst_qc_type', sort: true, fixed: true,templet: '#vst_qc_type'}">任务类型</th>
                    <th lay-data="{field:'vst_qc_state', templet: '#vst_qc_state', unresize: true}">任务状态</th>
                    <th lay-data="{field:'vst_qc_table', sort: true}">数据库表名称</th>
                    <th lay-data="{field:'vst_qc_name'}">任务名</th>

                    <th lay-data="{field:'vst_qc_job_cron'}">任务执行时间</th>
                    <th lay-data="{field:'vst_qc_deal_before', width:135, sort: true,templet: '#before'}">第一阶段</th>
                    <th lay-data="{field:'vst_qc_deal_after',  sort: true, fixed: 'right',templet: '#after'}">第二阶段</th>
                    <th lay-data="{field:'vst_qc_condition',  sort: true, fixed: 'right'}">清理条件</th>
                    <th lay-data="{field:'vst_qc_backup', templet:'#backup'}">备份选项</th>
                    <th lay-data="{field:'vst_qc_backup_type',  sort: true, fixed: 'right',templet: '#back'}">备份类型</th>

                    <th lay-data="{field:'vst_qc_backup_condition',  sort: true, fixed: 'right'}">备份条件</th>
                    <th lay-data="{field:'vst_qc_summary',  sort: true}">任务描述</th>
                    <th lay-data="{fixed: 'right', width:200, align:'center', toolbar: '#barDemo'}">操作</th>
                </tr>
                </thead>
            </table>

        </div>
    </fieldset>



   <%@include file="../common/footerjs.jsp"%>

   <script type="text/html" id="barDemo">
       <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
       <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
       <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
       <a class="layui-btn layui-btn-warm layui-btn-xs " lay-event="time">时间</a>
   </script>

   <script type="text/html" id="vst_qc_type">
       {{#  if(d.vst_qc_type === 1){ }}
       <span style="color: #F581B1;">{{ '临时任务' }}</span>
       {{#  } else { }}
       {{ '定时任务' }}
       {{#  } }}
   </script>

   <%--<script type="text/html" id="vst_status">--%>
       <%--{{#  if(d.vst_status === 1){ }}--%>
       <%--<span style="color: #F581B1;">{{ '启动' }}</span>--%>
       <%--{{#  } else { }}--%>
       <%--{{ '暂停' }}--%>
       <%--{{#  } }}--%>
   <%--</script>--%>

   <script type="text/html" id="before">
       {{#  if(d.vst_qc_deal_before === 1){ }}
       {{ '禁用原表数据' }}
       {{#  } else if(d.vst_qc_deal_before == 2) { }}
       <span style="color: #F581B1;">{{ '备份原表数据' }}</span>
       {{#  } else{ }}
       {{ '不处理' }}
       {{# } }}
   </script>

   <script type="text/html" id="after">
       {{#  if(d.vst_qc_deal_after === 1){ }}
       <span style="color: #F581B1;">{{ '删除原表数据' }}</span>
       {{#  } else { }}
       {{ '不处理' }}
       {{#  } }}
   </script>

    <script type="text/html" id="backup">
        {{#  if(d.vst_qc_backup === 1){ }}
        <span style="color: #F581B1;">{{ '备份表' }}</span>
        {{#  } else if(d.vst_qc_backup === 2) { }}
        {{ '备份文件' }}
        {{#  } else{ }}
        {{ '两者都备份' }}
        {{#  } }}
    </script>

   <script type="text/html" id="back">
       {{#  if(d.vst_qc_backup_type === 1){ }}
       <span style="color: #F581B1;">{{ '根据字段备份' }}</span>
       {{#  } else if(d.vst_qc_backup_type === 2) { }}
       <span style="color: #1E9FFF;">{{ '全表备份' }}</span>
       {{#  }else { }}
       <span style="color: #00F7DE;">{{ '根据清除条件备份' }}</span>
       {{# } }}
   </script>

   <script type="text/html" id="vst_qc_state">
       <input type="checkbox" name="sex" value="{{d.vst_qc_id}}" lay-skin="switch" lay-text="启动|暂停" lay-filter="sexDemo"
              {{ d.vst_qc_state == 1 ? 'checked' : '' }}>
   </script>


   <script>
       layui.use(['table','layer'], function(){
           let table = layui.table;
           let form = layui.form;

           let $ = layui.$, active = {
               reload: function(){
                   let jobName = $('#jobName').val();
                   console.log(jobName);
                   //执行重载
                   table.reload('idTest', {
                       page: {
                           curr: 1 //重新从第 1 页开始
                       }
                       ,where: {
                           jobName: jobName

                       }
                   });
               }
           };

           //点击搜索
           $('.demoTable .layui-btn').on('click', function(){
               let type = $(this).data('type');
               active[type] ? active[type].call(this) : '';
           });

           //监听状态操作
           form.on('switch(sexDemo)', function(obj){
               let flag =obj.elem.checked;
               let title = "";
               if (flag){
                   title = "确定要恢复任务吗？"
               }else {
                   title = "确定要暂停任务吗？"
               }
               let value = this.value;

               layer.confirm(title, function(index){
                   console.log("进来了"+flag+"---"+value)
                   pauseAndResume(flag,value);

               });

           });


           //监听工具条执行查看，删除，编辑操作
           //监听工具条
           table.on('tool(demo)', function(obj){
               let data = obj.data;
               if(obj.event === 'detail'){
                   detail(data.vst_qc_id);

               } else if(obj.event === 'del'){
                   layer.confirm('真的删除行么', function(index){
                       del(data.vst_qc_id);
                   });
               } else if(obj.event === 'edit'){
                   window.location.href="${ctx}/config/edit?quartzId="+data.vst_qc_id;
               } else if(obj.event === 'time'){
                   time(data.vst_qc_id)
               }
           });



           
           
           //查看详情操作
           function detail(val) {
               layer.open({
                   type: 2,
                   title: '详细信息',
                   shadeClose: true,
                   shade: false,
                   maxmin: true, //开启最大化最小化按钮
                   area: ['800px', '600px'],
                   content: '${ctx}/config/detail?quartzId='+val //iframe的url
               });
           }


           //删除操作
           function del(val) {
               $.ajax({
                   type:"POST",
                   url:"${ctx}/config/delete",
                   dataType:"json",
                   data:{"quartzId":val},
                   success:function (res) {
                       if (res.code === 200){
                           alert(res.msg);
                       }else {
                           alert(res.msg);
                       }

                       window.location.reload();
                   }
               })
           }
           //修改任务触发时间
           function time(val) {
               layer.prompt({title: '请输入cron时间表达式', formType: 3}, function(cron, index){
                   $.ajax({
                       type:"GET",
                       url:"${ctx}/job/modify",
                       dataType:"json",
                       data:{"quartzId":val,time:cron},
                       success:function (res) {
                           if (res.code === 200){
                               layer.msg('触发时间修改成功,表达式为:'+cron);
                           }else {
                               alert(res.msg);
                           }

                           window.location.reload();
                       },
                       complete:function () {
                           layer.close(index);
                       }
                   });


               });
           }

           //恢复和暂停任务
           function pauseAndResume(flag,value) {
               //true表示恢复任务，false表示暂停任务
               if (flag){
                   $.ajax({
                       type:"POST",
                       url:"${ctx}/job/resume",
                       dataType:"json",
                       data:{"quartzId":value},
                       success:function (res) {
                           if (res.code === 200){
                               alert(res.msg);
                           }else {
                               alert(res.msg);
                           }
                           window.location.reload();
                       }
                   })
               }else {
                   $.ajax({
                       type:"POST",
                       url:"${ctx}/job/pause",
                       dataType:"json",
                       data:{"quartzId":value},
                       success:function (res) {
                           if (res.code === 200){
                               alert(res.msg);

                           }else {
                               alert(res.msg);
                           }
                           window.location.reload();
                       }
                   })
               }
           }
       });



   </script>
</body>
</html>
