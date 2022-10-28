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
    <title>任务详细信息</title>
    <%@include file="../common/header.jsp"%>

    <style type="text/css">

        /*.layui-field-box{*/
            /*margin-left: 500px;*/

        /*}*/

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
            <legend>任务详情页面</legend>
            <div class="layui-field-box">
                <form class="layui-form" action="">
                    <input type="hidden" value="${config.vst_qc_id}" id="vst_qc_id" name="vst_qc_id">

                    <div class="layui-form-item">
                        <label class="layui-form-label">任务类型:</label>
                        <div class="layui-input-inline">
                            <select name="vst_qc_type" lay-filter="aihao" disabled>
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
                            <select name="vst_qc_job_type" lay-filter="aihao" disabled>
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
                                    <option value="clean">清理</option>
                                    <option value="push">推送</option>
                                    <option value="pull" selected>拉取</option>
                                </c:if>

                            </select>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">执行时间:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="vst_qc_job_cron" required  lay-verify="required" disabled
                                   placeholder="请输入执行时间" value="${config.vst_qc_job_cron}" autocomplete="off" class="layui-input">
                        </div>
                        <div class="layui-form-mid layui-word-aux">任务执行的时间cron表达式</div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">第一阶段:</label>
                        <div class="layui-input-inline">
                            <select name="vst_qc_deal_before" disabled>
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
                            <select name="vst_qc_deal_after" disabled>
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
                        <label class="layui-form-label">数据库表名:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="vst_qc_table" required  lay-verify="required"
                                   placeholder="请输入表名" disabled value="${config.vst_qc_table}" autocomplete="off" class="layui-input">
                        </div>
                        <div class="layui-form-mid layui-word-aux">输入对应的数据库表名</div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">实体类名:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="vst_qc_entity_name" required  lay-verify="required"
                                   placeholder="请输入实体类名" disabled value="${config.vst_qc_entity_name}"
                                   autocomplete="off" class="layui-input">
                        </div>
                        <div class="layui-form-mid layui-word-aux">数据库对应的实体类名称</div>
                    </div>


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
                            <input type="text" name="vst_qc_name" disabled required  lay-verify="required"
                                   placeholder="请输入任务名称" value="${config.vst_qc_name}" autocomplete="off" class="layui-input">
                        </div>
                        <div class="layui-form-mid layui-word-aux">请输入对应的任务名称</div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">触发器名称:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="vst_qc_trigger_name" disabled required  lay-verify="required"
                                   placeholder="请输入触发器名称" value="${config.vst_qc_trigger_name}" autocomplete="off" class="layui-input">
                        </div>
                        <div class="layui-form-mid layui-word-aux">请输入对应的触发器名称</div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">处理条件:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="vst_qc_condition" disabled required  lay-verify="required"
                                   placeholder="请输入处理条件"  autocomplete="off" class="layui-input"
                            value="${config.vst_qc_condition}">
                        </div>
                        <div class="layui-form-mid layui-word-aux">请输入任务操作时的处理条件,默认1=1</div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">备份类型:</label>
                        <div class="layui-input-inline">
                            <select name="vst_qc_backup_type"  disabled>
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
                            <input type="text" name="vst_qc_backup_condition" disabled required
                                   placeholder="请输入备份条件" value="${config.vst_qc_backup_condition}" autocomplete="off" class="layui-input">
                        </div>
                        <div class="layui-form-mid layui-word-aux">如果根据表字段备份，请填写字段，如果其余两种备份，不需要填写</div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">备份表的源数据源:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="vst_qc_source_name" disabled required  lay-verify="required"
                                   placeholder="请输入备份源数据源" value="${config.vst_qc_source_name}" autocomplete="off" class="layui-input">
                        </div>
                        <div class="layui-form-mid layui-word-aux">需要备份数据的数据源。(多个用逗号隔开:A,B,C)</div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">备份表的目标数据源:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="vst_qc_target_source_name" disabled required  lay-verify="required"
                                   placeholder="请输入备份目标数据源" value="${config.vst_qc_target_source_name}" autocomplete="off" class="layui-input">
                        </div>
                        <div class="layui-form-mid layui-word-aux">需要将数据备份到那个指定的库(多个用逗号隔开:A,B,C)</div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">任务描述:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="vst_qc_summary" value="${config.vst_qc_summary}" required  lay-verify="required"
                                   placeholder="请输入任务描述" disabled autocomplete="off" class="layui-input">
                        </div>
                        <div class="layui-form-mid layui-word-aux">对任务的描述</div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">修改人:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="vst_qc_updator" required  lay-verify="required"
                                   placeholder="" disabled value="${config.vst_qc_updator}" autocomplete="off" class="layui-input">
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">新增人:</label>
                        <div class="layui-input-inline">
                            <input type="text" name="vst_qc_creator" required  lay-verify="required"
                                    value="${config.vst_qc_creator}" disabled autocomplete="off" class="layui-input">
                        </div>
                    </div>

                </form>
            </div>

        </fieldset>


    </div>

<%@include file="../common/footerjs.jsp"%>
<script type="text/javascript">
    layui.use(['form', 'layedit', 'laydate'], function() {
        var form = layui.form

    });
</script>
</body>
</html>
