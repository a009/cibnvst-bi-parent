<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@include file="../share/taglib.jsp"%>
<%@include file="../share/prefix.jsp"%>
<%@include file="../test/shence.jsp"%>
<c:set var="dataList" value="${cutPage._dataList}"></c:set>
<c:set var="buttonList" value="${cutPage._buttonList}"></c:set>
<head>
    <title>报表管理中心</title>

    <script type="text/javascript">
        $(function () {
            //下拉列表框，赋值
            $("#vst_button_state").val($("#hidden_button_state").val());

            //展开/隐藏 查询条件
            var hidden_search = '${hidden_search}';
            if (hidden_search == 1 || hidden_search == '1') {	//隐藏
                $("#hidden_search").val(1);
            } else {	//展开
                $("#hidden_search").val(2);
            }

            //展开/隐藏 tablelist
            var hidden_tablelist = '${hidden_tablelist}';
            if (hidden_tablelist == 2 || hidden_tablelist == '2') {	//展开
                $("#hidden_tablelist").val(2);
                $("#tableExample1 .table .table-tbody").removeClass("maxHeight").slideDown();
            } else {	//隐藏
                $("#hidden_tablelist").val(1);
                $("#tableExample1 .table .table-tbody").addClass("maxHeight");
            }
        });

        // 查询
        function validQuery() {
            $("#currPage").val(1);
            $("#queryBtn").attr("disabled", "disabled");
            $("#listForm").submit();
        }

        // 新增
        function button_add() {
            $("#listForm").attr("action", "${ctx}/sysButton/toAdd.action");
            $("#listForm").submit();
        }

        // 修改
        function button_edit() {
            var modifyChecked = "";
            $("input:checked[type=checkbox][name=recordIds]").each(function () {
                modifyChecked = modifyChecked + $(this).val() + ",";
            });
            modifyChecked = modifyChecked.substring(0, modifyChecked.length - 1);
            if (modifyChecked == "") {
                alert("您还没有选择记录!");
                return false;
            } else if (modifyChecked.indexOf(",") != -1) {
                alert("每次最多选择一条记录进行该操作!");
                return false;
            } else {
                $("#recordId").val(modifyChecked);
                $("#listForm").attr("action", "${ctx}/sysButton/toEdit.action");
                $("#listForm").submit();
            }
        }

        // 批量删除
        function button_delete() {
            var modifyChecked = "";
            $("input:checked[type=checkbox][name=recordIds]").each(function () {
                modifyChecked = modifyChecked + $(this).val() + ",";
            });
            modifyChecked = modifyChecked.substring(0, modifyChecked.length - 1);
            if (modifyChecked == "") {
                alert("您还没有选择记录!");
                return false;
            } else {
                if (window.confirm("您确定要删除所选的" + modifyChecked.split(',').length + "条记录吗?")) {
                    $("#recordId").val(modifyChecked);
                    $("#listForm").attr("action", "${ctx}/sysButton/deleteButton.action");
                    $("#listForm").submit();
                }
            }
        }

        // 批量禁用
        function button_stop() {
            var modifyChecked = "";
            var state = "";
            $("input:checked[type=checkbox][name=recordIds]").each(function () {
                modifyChecked = modifyChecked + $(this).val() + ",";
                state = state + $(this).attr("state") + ",";
            });
            modifyChecked = modifyChecked.substring(0, modifyChecked.length - 1);
            state = state.substring(0, state.length - 1);
            if (modifyChecked == "") {
                alert("您还没有选中记录！");
                return false;
            }
            var states = state.split(",");
            for (var i = 0; i < states.length; i++) {
                if (states[i] == 2) {
                    alert("所选记录中存在已禁用的记录不能再禁用，请检查！");
                    return false;
                }
            }
            if (confirm("您确定要禁用所选的" + modifyChecked.split(',').length + "条记录吗？")) {
                $("#recordId").val(modifyChecked);
                $("#recordState").val(2);
                $("#listForm").attr("action", "${ctx}/sysButton/modifyButtonState.action");
                $("#listForm").submit();
            }
        }

        // 批量启用
        function button_resume() {
            var modifyChecked = "";
            var state = "";
            $("input:checked[type=checkbox][name=recordIds]").each(function () {
                modifyChecked = modifyChecked + $(this).val() + ",";
                state = state + $(this).attr("state") + ",";
            });
            modifyChecked = modifyChecked.substring(0, modifyChecked.length - 1);
            state = state.substring(0, state.length - 1);
            if (modifyChecked == "") {
                alert("您还没有选中记录！");
                return false;
            }
            var states = state.split(",");
            for (var i = 0; i < states.length; i++) {
                if (states[i] == 1) {
                    alert("所选记录中存在已启用的记录不能再启用，请检查！");
                    return false;
                }
            }
            if (confirm("您确定要启用所选的" + modifyChecked.split(',').length + "条记录吗？")) {
                $("#recordId").val(modifyChecked);
                $("#recordState").val(1);
                $("#listForm").attr("action", "${ctx}/sysButton/modifyButtonState.action");
                $("#listForm").submit();
            }
        }

        // 初始化map实例
        var cahceMap = new HashMap();
        $(function () {
            $(".myClick").bind("click", function () {
                // 清除所有的.myClick样式下，所有的input文本域
                $(".myClick > input").each(function (i) {
                    $(this).parent("li").html($(this).val());
                });
                var value = "";
                // 首先判断有没有input子节点
                if ($(".myClick > input").length > 0) {
                    value = $(".myClick > input").val();
                } else {
                    value = $(this).text();
                }
                $(this).html("<input type='text' value='" + value + "'/>");
                $(".myClick > input").focusEnd();

            });

            $(document).on("blur", ".myClick > input", function () {
                // 清除所有的.myClick样式下，所有的input文本域
                $(".myClick > input").each(function (i) {
                    var $ul = $(this).parents("ul");
                    var newValue = $(this).val();
                    $(this).parent("li").html(newValue);
                    var firstTD = $ul.find("li:eq(0) :input[type=checkbox][name=recordIds]");
                    var oldValue = $(firstTD).attr("index");
                    if (newValue != oldValue) {
                        $("#doIndex").attr("disabled", false);
                        $("#doIndex").attr("class", "rankBtn");
                        cahceMap.put($(firstTD).attr("value"), newValue);
                    }
                });
            });
        });

        // 生效排序
        function makeIndex() {
            if ($("#doIndex").attr("disabled") == undefined || $("#doIndex").attr("disabled") == "") {
                if (!checkIndexs()) {
                    alert("非常抱歉！您修改的排序结果中有非数字的排序，请认真检查！");
                } else {
                    $.ajax({
                        url: "${ctx}/sysButton/modifyButtonIndexs.action",
                        type: "POST",
                        data: "ids=" + getKeys() + "&indexs=" + getValues() + "&moduleId=" + $("#moduleId").val(),
                        dataType: "text",
                        async: false,
                        cache: false,
                        success: function (data) {
                            if (data != null && data == "true") {
                                alert("修改成功！请点击查询后查看结果！");
                                $("#doIndex").attr("disabled", "disabled");
                                $("#doIndex").attr("class", "resetBtn");
                            } else {
                                alert("修改失败！");
                            }
                        },
                        error: function (XMLHttpRequest, textStatus, e) {
                            alert("修改失败！失败信息：" + e);
                        }
                    });
                }
            }
        }

        // 校验数据是否合法
        function checkIndexs() {
            var values = cahceMap.values();
            var reg = /^[0-9]{1,}$/;
            for (var i in values) {
                if (!reg.test(values[i])) {
                    return false;
                }
            }
            return true;
        }

        function getKeys() {
            var keys = cahceMap.keySet();
            var result = "";
            for (var i in keys) {
                result += keys[i] + ",";
            }
            return result;
        }

        function getValues() {
            var values = cahceMap.values();
            var result = "";
            for (var i in values) {
                result += values[i] + ",";
            }
            return result;
        }

        // 展开/隐藏 查询条件
        function search_open() {
            if ($(".searchBar").hasClass("active")) {	//展开
                $(".search-up-button").find("img").attr("src", "${ctx}/pub/listPages/images/search_close.png");
                $(".searchBar").removeClass("active").slideDown();
                $("#hidden_search").val(2);
            } else {	//隐藏
                $(".search-up-button").find("img").attr("src", "${ctx}/pub/listPages/images/search_open.png");
                $(".searchBar").addClass("active").slideUp(300);
                $("#hidden_search").val(1);
            }
        }

        // 展开/隐藏 tablelist
        function tablelist_open() {
            if ($("#tableExample1 .table .table-tbody").hasClass("maxHeight")) {	//展开
                $(".tablelist-up-button").find("img").attr("src", "${ctx}/pub/listPages/images/search_close.png");
                $("#tableExample1 .table .table-tbody").removeClass("maxHeight").slideDown();
                $("#hidden_tablelist").val(2);
            } else {	//隐藏
                $(".tablelist-up-button").find("img").attr("src", "${ctx}/pub/listPages/images/search_open.png");
                $("#tableExample1 .table .table-tbody").addClass("maxHeight");
                $("#hidden_tablelist").val(1);
            }
        }
    </script>
    <script type="text/javascript" src="${ctx}/pub/plugins/shence/funnel.js"></script>
</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <%@include file="../share/header.jsp"%>
    <%@include file="../share/leftMenu.jsp"%>
    <div class="content-wrapper">
        <div class="content-roll">
        </div>
    </div>
</div>
<!--主模块-->
<script type="text/html" id="tpl-funnel-index">
    <section style="display: none;" id="funnel_state_empty" class="sa-demo-wrap">
        <!--如果用没有任何数据，展示下面部分-->
        <div class="sa-demo-content" id="funnel_state_empty_event" style="display: none;">
            <h2>没有可展示的数据</h2>

            <p>请确认已经正确安装 SDK 或导入数据。</p>
        </div>
        <!--如果已经有数据了，但是没有创建漏斗，展示下面部分-->
        <div class="sa-demo-content" id="funnel_state_empty_funnel" style="display: none;">
            <h3>还没有创建漏斗</h3>

            <p>使用漏斗模型可以追踪产品流程中每一步的转化率。</p>
            <button type="button" class="btn btn-primary btn-create-fu" id="sa_fu_btn">创建漏斗</button>
        </div>
    </section>
    <div class="sa-report">
        <section class="report-ops funnel-ops" style="display: none;">
            <div class="ops-item">
                <div>显示漏斗</div>
                <div id="select-funnel" class="selector select-funnel">
                </div>
                <div id="funnel-group" class="group-control-item">
                </div>
                <button type="button" class="btn btn-primary btn-create-fu pull-right" id="sa_fu_btn_main">创建漏斗</button>
            </div>
            <div class="ops-item" id="funnel-filter-container"></div>
            <div class="ops-item">
                <button id="btnAddFilter" type="button" class="btn btn-link">
                    <span class="icon-add"></span><span>筛选条件</span>
                </button>
                <div class="query-button-wrap">
                    <button id="funnel_manual_query_btn" type="button" class="btn btn-primary" style="display:none;">
                        查询
                    </button>
                </div>
            </div>
        </section>
        <section class="report-chart segmentation-chart funnel-container" style="display: none;">
            <header>
                <div class="report-date-picker-wrap">
                    <span class="icon-date-picker"></span>
                    <input id="inputDate" type="text" class="form-control" readonly="readonly" data-placement="top" data-container="body">
                </div>
                <div class="report-date-picker-tip" id="funnel_datepick_convert_time_tooltip" data-original-title="" style="display: inline-block;" title="">
                    <span>窗口期:</span>
                    <div class="sa-content" id="funnel_datepick_convert_time_content"></div>
                </div>
                <div class="report-name-wrap" style="width: calc(100% - 510px) !important; word-break: break-all;" data-lang-style="<!--{en}width: calc(100% - 610px) !important; word-break: break-all;-->">
                    <h4 id="reportName" class="report-name"></h4>
                </div>
                <div class="btn-toolbar" id="btn-toolbar" role="toolbar" aria-label="..." style="">
                    <div class="btn-group" data-role="state" role="group" aria-label="toggle-chart">
                        <button type="button" class="btn btn-default active" data-method="trends">趋势</button>
                        <button type="button" class="btn btn-default" data-method="overview">对比</button>
                    </div>
                    <div class="btn-group" role="group" aria-label="toggle-chart">
                        <div class="dropdown chart-config">

                        </div>
                    </div>
                </div>
            </header>
            <div class="report-no-data" style="display: none;">
                <div class="report-no-data-msg">
                    <h5>没有查找到数据</h5>
                    <p>请尝试调整时间段或筛选条件</p>
                </div>
                <div class="report-no-data-msg" style="display:none;">
                    <h5>等待查询</h5>
                    <p>请确定查询条件，点击查询按钮</p>
                </div>
            </div>
            <div style="" class="container-fluid">
                <div id="trendFunnels" class="row trend-funnel">
                    <!--漏斗容器-->
                    <div id="funnelContainer" class="funnel-container" style="float: left;"></div>
                    <!--图表容器-->
                    <div id="chartContainer" class="chart-container" style="float: left; margin-left: 5px;"></div>
                </div>
                <div id="singleContainer" class="row" style="display: none;">

                </div>
            </div>
            <div class="page-part-h-split" style=""></div>
            <section class="report-chart" style="">
                <div id="table-container" class="table-wrap"></div>
                <div id="table-pagination"></div>
            </section>
        </section>
    </div>
    <div class="fu-cface-out" id="editFloatContainer"></div>
</script>

<!--漏斗选择-->
<script type="text/html" id="tpl-select-funnel">
    <select>
        {{each $data value index}}
        {{if (value.access)}}
        <option data-id="{{value.id}}" value="{{value.id}}" data-converttime="{{value.max_convert_time}}"
                title="{{value.name}}">
            {{value.name}}
            <span class="icon-edit"></span>
        </option>
        {{/if}}
        {{/each}}
    </select>
</script>

<!--按什么查询-->
<script type="text/html" id="tpl-filter-by-item">
    <div class="group-control-item">
        按
        <div class="selector property property-help">
            <select class="btn-selector">
                <option value="" data-type="" data-dict="">总体</option>
                {{each $data.newEvent value index}}
                <optgroup label="{{index}}" data-icon="icon-funnel-step,icon-event-property">

                    {{each value v i}}
                    <option value="{{v.id}}" data-type="{{v.data_type}}" data-dict="{{v.has_dict}}">
                        {{v.cname}}
                    </option>
                    {{/each}}

                </optgroup>
                {{/each}}

            </select>
            <span class="icon-help" data-toggle="tooltip" style="display: none;"></span>
        </div>
        查看
    </div>
</script>

<!--“显示设置”-->
<script type="text/x-handlebars-template" id="tpl-toolbar-config">
    <button class="btn btn-selector dropdown-toggle" type="button" data-method="chart-config" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
        <!--<span>对比设置</span>-->
        <span>显示设置</span>
    </button>
    <ul class="dropdown-menu config funnel-config" aria-labelledby=".." style="display: none">
        <div class="range-label-time sa-tab clearfix">
            <div data-for="#chart-measure-selector" class="selected" data-index="0">显示指标</div>
            <div data-for="#chart-by-selector" data-index="1" class="">显示分组</div>
        </div>
        <ul id="chart-measure-selector">

        </ul>
        <ul id="chart-by-selector">

        </ul>
    </ul>
</script>

<!--漏斗箭头-->
<script type="text/html" id="tpl-funnel-arrow">
    <div class="funnel-content">
        <div style="padding: 10px 0; text-align: center;width: 260px; display: inline-block;">
            <svg data-step="$ALL" class="{{if(!$data.overview)}}active{{/if}}" width="107" height="57" xmlns="http://www.w3.org/2000/svg">
                <g>
                    <title></title>
                    <g fill="none" fill-rule="evenodd">
                        <g class="active" fill="#EAEFF4">
                            <polygon points="0,0 107,0 107,44 53.5,57 0,44" style="stroke-width: 1; stroke: rgba(0,0,0,0.12);"/>
                        </g>
                    </g>
                    <text fill="#000000" stroke="#6666ff" stroke-width="0" stroke-dasharray="null" stroke-linejoin="null" stroke-linecap="null" x="50%" y="23" font-size="14" font-family="Monospace" text-anchor="middle" xml:space="preserve">总转化率</text>
                    <text xml:space="preserve" text-anchor="middle" font-size="14" y="39" x="48%" stroke-linecap="null" stroke-linejoin="null" stroke-dasharray="null" stroke-width="0" stroke="#6666ff" fill="#000000">
                        {{$data.funnel_detail[0].completion_rate}}
                    </text>
                </g>
            </svg>
        </div>
        {{if ($data.overview)}}
        <div style="display:inline-block;width: calc(100% - 270px); text-align: center; vertical-align: bottom; margin-bottom: 35px; font-weight: 500;">
            分组值: {{$data.by_values[0]}}
        </div>
        {{/if}}

        {{each $data.funnel_detail[0].steps value index}}
            <div style="width: 100%;">
                <div class="fu-arrow-container">
                    <div class="div-inline event-title">{{value.event_name}}</div>
                    <div class="div-inline sa-h-split"></div>
                    <div class="div-inline" style="width: 85px; text-align: right;">{{value.converted_user}} 人</div>
                </div>
                {{if ($data.overview)}}
                <div style="display: inline-block; width: calc(100% - 275px);">
                    <div class="rate-rectangle" style="width:{{if(index==0)}}100%{{else}}{{$data.funnel_detail[0].steps[index-1].conversion_rate}}%{{/if}}; ">
                        <div style="width: {{value.conversion_rate}}%;">
                            &nbsp;
                        </div>
                    </div>
                </div>
                {{/if}}
            </div>
            {{each value.rows v i}}
            <div style="padding: 10px 0; text-align: center;width: 260px;">
                <svg class="" data-step="{{value.event_name}}" width="100" height="44" xmlns="http://www.w3.org/2000/svg">
                    <g>
                        <g fill="none" fill-rule="evenodd">
                            <g fill="#EAEFF4" class="active">
                                <polygon points="18,0 82,0 82,20 100,20 50,44 0,20 18,20 " style="stroke-width: 1; stroke: rgba(0,0,0,0.12);"/>
                            </g>
                        </g>
                        <text xml:space="preserve" text-anchor="middle" font-size="14px" id="" y="26" x="48%" stroke-linecap="null" stroke-linejoin="null" stroke-dasharray="null" stroke-width="0" stroke="#6666ff" fill="#000000">
                            {{v.conversion_rate}}
                        </text>
                    </g>
                </svg>
            </div>
            {{/each}}
        {{/each}}
    </div>
</script>
<!--漏斗表格-->
<script type="text/html" id="tpl-funnel-table">
    <table class="table-normal table-segmentation">
        <thead class="header">
        <tr>
            <th class=" " data-original-title="" title="">

            </th>
            <th class="rollup-col " data-original-title="" title="">

            </th>
            <th class="rollup-col " data-original-title="" title="">
                总转化情况
            </th>
            <th class="rollup-col " data-original-title="" title="">
                第 1 步
            </th>
            <th class="rollup-col " data-original-title="" title="">
                第 2 步
            </th>
            <th class="rollup-col " data-original-title="" title="">
                第 3 步
            </th>
            <th class="rollup-col ">
                第 4 步
            </th>
        </tr>
        </thead>
        <tbody>
        {{each $data.funnel_detail value index}}
        <tr>
            {{if (index==0)}}
            <th>
                <span data-method="toggle-row" data-by-index="0" class="icon-collapse-right"
                      style="vertical-align: sub;"></span>
                <span class="median-time"></span>
            </th>
            <th>
                <span>总体</span>
                <span class="median-time"></span>
            </th>
            {{else}}
            <th>
                <span></span>
                <span class="median-time"></span>
            </th>
            <th>
                <span>{{$data.date_list[index]}}</span>
                <span class="median-time"></span>
            </th>
            {{/if}}
            {{each value.steps v i }}
            <td class="rollup-col">
            <span data-method="user-list" data-date="" data-rollup="rollup" data-row-index="0"
                  data-by-index="0" title="" data-toggle="tooltip" data-placement="top" data-container="body"
                  data-original-title="查看详细用户列表">
                {{if (i==0)}}
                    {{value.steps[4].converted_user}}
                {{else}}
                    {{v.converted_user}}
                {{/if}}
            </span>
                <span class="ratio">
                {{if (i==0)}}
                {{value.completion_rate}}
                {{else}}
                {{v.conversion_rate}}
                {{/if}}
                %
            </span>
            </td>
            {{/each}}
        </tr>
        {{/each}}
        </tbody>
    </table>
</script>
</body>
</html>