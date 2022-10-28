<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@include file="share/taglib.jsp" %>
<%@include file="share/prefix.jsp" %>
<head>
<title>报表后台中心</title>

<!-- Theme style -->
<link rel="stylesheet" href="${ctx}/pub/listPages/css/listCommon.css">
<script src="${ctx}/pub/plugins/highcharts/highcharts.js"></script>
<script src="${ctx}/pub/plugins/laydate/laydate.js"></script>
<style type="text/css">
	#refresh:hover{
		color: #00a7d0;
	}
	#export:hover{
		color: #00a7d0;
	}
	
	.export-table{
		width: 100%;
		max-width: 100%;
    	text-align: left;
	}
	.export-table>thead>tr>th {
    	vertical-align: bottom;
    	padding: 5px;
    	white-space: nowrap;
    	background: #e4eaec;
    	color: #58666e;
    	border-bottom: 1px solid #e4eaec;
    	border-left: 1px solid #e4eaec;
    	border-right: 1px solid #e4eaec;
	}
	.export-table>tbody>tr>td {
    	padding: 5px;
    	font-weight: 400;
    	font-size: 12px;
    	empty-cells: show;
    	border-top: 0;
    	border-right: 0;
    	border-bottom: 1px solid #e4eaec;
    	border-left: 1px solid #e4eaec;
    	border-right: 1px solid #e4eaec;
	}
</style>

<script>
    //影片排行榜整理
    function showTable(vv, uv, id) {
        $(".myLoad").attr("style", "display:none");
        //播放量
        var table = "<table id='example1' class='table table-bordered table-striped' style='margin-bottom:0;'><thead><tr><th width='10%'>排行</th>";
        if (id == "site") {
            table += "<th width='30%'><div style='width:180px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;'>平台名称</div></th>";
        } else {
            table += "<th width='30%'><div style='width:180px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;'>影片名称</div></th>";
        }
        table += "<th width='15%'>播放量</th><th width='15%'>观看人数</th><th width='15%'>人均播放量</th><th width='15%'>人均时长(m)</th></tr></thead><tbody>";
        $.each(vv, function (i, n) {
            var row = i % 2 + 1;
            var num = i + 1;
            
            var playColor = "black";
            var playText = "-";
            if (parseFloat(n.vst_mp_vv_dod) > 0) {
                playColor = "green";
                playText = "↑";
            } else if (parseFloat(n.vst_mp_vv_dod) < 0) {
                playColor = "red";
                playText = "↓";
            }
            
            var userColor = "black";
            var userText = "-";
            if (parseFloat(n.vst_mp_uv_dod) > 0) {
                userColor = "green";
               	userText = "↑";
            } else if (parseFloat(n.vst_mp_uv_dod) < 0) {
                userColor = "red";
                userText = "↓";
            }
            
            table += "<tr class='row" + row + "'>";
            table += "<td title='" + toString(n.vst_mp_uuid) + "'>" + num + "</td>";
            table += "<td title='" + toString(n.vst_mp_name) + "'><div style='width:180px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;'>" + formatString(n.vst_mp_name) + "</div></td>";
            table += "<td>" + myFormatNumber(n.vst_mp_vv) + "&nbsp;&nbsp;<span style='font-weight:bold;font-size:12px;font-weight:1200' class='" + playColor + "'><strong>" + playText + "</strong></span></td>";
            table += "<td>" + myFormatNumber(n.vst_mp_uv) + "&nbsp;&nbsp;<span style='font-weight:bold;font-size:12px;font-weight:1200' class='" + userColor + "'><strong>" + userText + "</strong></span></td>";
            table += "<td>" + myFormatNumber(n.vst_mp_avg) + "</td>";
            table += "<td>" + toMinute(n.vst_mp_playtime_avg*1000) + "</td>";
            table += "</tr>";
        });
        table += "</tbody></table>";
        $("#" + id).html(table);

        //观看人数
        var otherTable = "<table id='example1' class='table table-bordered table-striped' style='margin-bottom:0;'><thead><tr><th width='10%'>排行</th>";
        if (id == "site") {
            otherTable += "<th width='30%'><div style='width:180px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;'>平台名称</div></th>";
        } else {
            otherTable += "<th width='30%'><div style='width:180px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;'>影片名称</div></th>";
        }
        otherTable += "<th width='15%'>播放量</th><th width='15%'>观看人数</th><th width='15%'>人均播放量</th><th width='15%'>人均时长(m)</th></tr></thead><tbody>";
        $.each(uv, function (i, n) {
            var row = i % 2 + 1;
            var num = i + 1;

            var playColor = "black";
            var playText = "-";
            if (parseFloat(n.vst_mp_vv_dod) > 0) {
                playColor = "green";
                playText = "↑";
            } else if (parseFloat(n.vst_mp_vv_dod) < 0) {
                playColor = "red";
                playText = "↓";
            }
            
            var userColor = "black";
            var userText = "-";
            if (parseFloat(n.vst_mp_uv_dod) > 0) {
                userColor = "green";
               	userText = "↑";
            } else if (parseFloat(n.vst_mp_uv_dod) < 0) {
                userColor = "red";
                userText = "↓";
            }

            otherTable += "<tr class='row" + row + "'>";
            otherTable += "<td title='" + toString(n.vst_mp_uuid) + "'>" + num + "</td>";
            otherTable += "<td title='" + toString(n.vst_mp_name) + "'><div style='width:180px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;'>" + formatString(n.vst_mp_name) + "</div></td>";
            otherTable += "<td>" + myFormatNumber(n.vst_mp_vv) + "&nbsp;&nbsp;<span style='font-weight:bold;font-size:12px;font-weight:1200' class='" + playColor + "'><strong>" + playText + "</strong></span></td>";
            otherTable += "<td>" + myFormatNumber(n.vst_mp_uv) + "&nbsp;&nbsp;<span style='font-weight:bold;font-size:12px;font-weight:1200' class='" + userColor + "'><strong>" + userText + "</strong></span></td>";
            otherTable += "<td>" + myFormatNumber(n.vst_mp_avg) + "</td>";
            otherTable += "<td>" + toMinute(n.vst_mp_playtime_avg*1000) + "</td>";
            otherTable += "</tr>";
        });
        otherTable += "</tbody></table>";
        $("#other" + id).html(otherTable);
    }
    
    //平台排行榜整理
    function showSite(vv, uv, id) {
        $(".myLoad").attr("style", "display:none");
        //播放量
        var table = "<table id='example1' class='table table-bordered table-striped' style='margin-bottom:0;'><thead><tr><th width='10%'>排行</th>";
        if (id == "site") {
            table += "<th width='30%'><div style='width:180px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;'>平台名称</div></th>";
        } else {
            table += "<th width='30%'><div style='width:180px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;'>影片名称</div></th>";
        }
        table += "<th width='15%'>播放量</th><th width='15%'>观看人数</th><th width='15%'>人均播放量</th><th width='15%'>人均时长(m)</th></tr></thead><tbody>";
        $.each(vv, function (i, n) {
            var row = i % 2 + 1;
            var num = i + 1;

            var playColor = "black";
            var playText = "-";
            if (parseFloat(n.vst_ms_vv_dod) > 0) {
                playColor = "green";
                playText = "↑";
            } else if (parseFloat(n.vst_ms_vv_dod) < 0) {
                playColor = "red";
                playText = "↓";
            }

            var userColor = "black";
            var userText = "-";
            if (parseFloat(n.vst_ms_uv_dod) > 0) {
                userColor = "green";
               	userText = "↑";
            } else if (parseFloat(n.vst_ms_uv_dod) < 0) {
                userColor = "red";
                userText = "↓";
            }
            
            table += "<tr class='row" + row + "'>";
            table += "<td>" + num + "</td>";
            table += "<td title='" + toString(n.vst_ms_site) + "'><div style='width:180px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;'>" + formatString(n.vst_ms_site) + "</div></td>";
            table += "<td>" + myFormatNumber(n.vst_ms_vv) + "&nbsp;&nbsp;<span style='font-weight:bold;font-size:12px;font-weight:1200' class='" + playColor + "'><strong>" + playText + "</strong></span></td>";
            table += "<td>" + myFormatNumber(n.vst_ms_uv) + "&nbsp;&nbsp;<span style='font-weight:bold;font-size:12px;font-weight:1200' class='" + userColor + "'><strong>" + userText + "</strong></span></td>";
            table += "<td>" + myFormatNumber(n.vst_ms_avg) + "</td>";
            table += "<td>" + toMinute(n.vst_ms_playtime_avg*1000) + "</td>";
            table += "</tr>";
        });
        table += "</tbody></table>";
        $("#" + id).html(table);


        //观看人数
        var otherTable = "<table id='example1' class='table table-bordered table-striped' style='margin-bottom:0;'><thead><tr><th width='10%'>排行</th>";
        if (id == "site") {
            otherTable += "<th width='30%'><div style='width:180px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;'>平台名称</div></th>";
        } else {
            otherTable += "<th width='30%'><div style='width:180px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;'>影片名称</div></th>";
        }
        otherTable += "<th width='15%'>播放量</th><th width='15%'>观看人数</th><th width='15%'>人均播放量</th><th width='15%'>人均时长(m)</th></tr></thead><tbody>";
        $.each(uv, function (i, n) {
            var row = i % 2 + 1;
            var num = i + 1;

            var playColor = "black";
            var playText = "-";
            if (parseFloat(n.vst_ms_vv_dod) > 0) {
                playColor = "green";
                playText = "↑";
            } else if (parseFloat(n.vst_ms_vv_dod) < 0) {
                playColor = "red";
                playText = "↓";
            }

            var userColor = "black";
            var userText = "-";
            if (parseFloat(n.vst_ms_uv_dod) > 0) {
                userColor = "green";
               	userText = "↑";
            } else if (parseFloat(n.vst_ms_uv_dod) < 0) {
                userColor = "red";
                userText = "↓";
            }

            otherTable += "<tr class='row" + row + "'>";
            otherTable += "<td>" + num + "</td>";
            otherTable += "<td title='" + toString(n.vst_ms_site) + "'><div style='width:180px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;'>" + formatString(n.vst_ms_site) + "</div></td>";
            otherTable += "<td>" + myFormatNumber(n.vst_ms_vv) + "&nbsp;&nbsp;<span style='font-weight:bold;font-size:12px;font-weight:1200' class='" + playColor + "'><strong>" + playText + "</strong></span></td>";
            otherTable += "<td>" + myFormatNumber(n.vst_ms_uv) + "&nbsp;&nbsp;<span style='font-weight:bold;font-size:12px;font-weight:1200' class='" + userColor + "'><strong>" + userText + "</strong></span></td>";
            otherTable += "<td>" + myFormatNumber(n.vst_ms_avg) + "</td>";
            otherTable += "<td>" + toMinute(n.vst_ms_playtime_avg*1000) + "</td>";
            otherTable += "</tr>";
        });
        otherTable += "</tbody></table>";
        $("#other" + id).html(otherTable);
    }
	
    function pie(vv, uv) {
        var data = vv;
        var data2 = uv;

        //播放量屏占比
        var series = "[";
        $.each(data, function (i, n) {
            series += "{name:\"" + getValue(n["vst_mcp_cid"]) + "：" + n.vst_mcp_vv + "\",y:" + n.vst_mcp_vv + "},";
        });

        series += "]";
        series = eval(series);

        $("#cidPie").highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
            },
            legend: {
                align: 'right',
                verticalAlign: 'top',
                layout: 'vertical',
                x: -50,
                y: 0

            },
            title: {
                text: false
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        color: '#000000',
                        connectorColor: '#000000',
                        format: '<b>{point.name}</b>: {point.percentage:.2f} %'
                    },
                    showInLegend: false
                }
            },
            credits: {
            	enabled: false
            },
            series: [{
                type: 'pie',
                name: '比重',
                data: series
            }]
        });

        //观看人数屏占比
        var userseries = "[";
        $.each(data2, function (i, n) {
            userseries += "{name:\"" + getValue(n["vst_mcp_cid"]) + "：" + n.vst_mcp_uv + "\",y:" + n.vst_mcp_uv + "},";
        });

        userseries += "]";
        userseries = eval(userseries);

        $("#othercidPie").highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
            },
            legend: {
                align: 'right',
                verticalAlign: 'top',
                layout: 'vertical',
                x: -50,
                y: 0

            },
            title: {
                text: false
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        color: '#000000',
                        connectorColor: '#000000',
                        format: '<b>{point.name}</b>: {point.percentage:.2f} %'
                    },
                    showInLegend: false
                }
            },
            credits: {
            	enabled: false
            },
            series: [{
                type: 'pie',
                name: '比重',
                data: userseries
            }]
        });
    }

    function changeTab(showid, hideid, obj) {
        var des = "<img class='desMyImg' src='${ctx}/pub/images/desc.gif'/>";
        $("#" + showid).addClass("active");
        $("#" + hideid).removeClass("active");
        $("li a." + hideid).find("img.desMyImg").remove();
        if ($("li a." + showid).find("img.desMyImg").length == 0) {
            $("li a." + showid).append(des);
        }
    }

    function changeTab2(id, dom) {
        $("#" + id).find("li").find("a").find("img.desMyImg").remove();
        var des = "<img class='desMyImg' src='${ctx}/pub/images/desc.gif'/>";
        var thisDom = $("." + dom.className);
        var liList = $("#" + id).find("li").find("a");
        $.each(liList, function (i, value) {
            if (value.className == dom.className) {
                liList.eq(i).append(des);
            }
        });
    }

    function myFormatNumber(num, precision, separator) {
        var parts;
        if (!isNaN(parseFloat(num)) && isFinite(num)) {// 判断是否为数字
            num = Number(num);
            // 处理小数点位数
            num = (typeof precision !== 'undefined' ? num.toFixed(precision) : num).toString();
            // 分离数字的小数部分和整数部分
            parts = num.split('.');
            // 整数部分加[separator]分隔, 借用一个著名的正则表达式
            parts[0] = parts[0].toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1' + (separator || ', '));

            return parts.join('.');
        }
        return "暂无数据";
    }

    //获取昨天日期
    function GetDay() {
        var today = new Date();
        var yesterday_milliseconds = today.getTime() - 1000 * 60 * 60 * 24;

        var yesterday = new Date();
        yesterday.setTime(yesterday_milliseconds);

        var strYear = yesterday.getFullYear();
        var strDay = yesterday.getDate();
        var strMonth = yesterday.getMonth() + 1;
        if (strMonth < 10) {
            strMonth = "0" + strMonth;
        }
        if (strDay < 10) {
            strDay = "0" + strDay;
        }

        var strYesterday = strYear + "-" + strMonth + "-" + strDay;
        return strYesterday;
    }
    
    function getHomeData(date, pkgName, isCache) {
        $("#exportTbody").html("");
        $(".tab-pane").html('<div class="myLoad" style="width: 100%;height: 411px;"><img class="loadImg" src="${ctx}/pub/images/loading1.gif"/></div>');
        $.ajax({
            url: '${ctx}/index/ajaxGetIndexData.action',
            type: 'GET',
            data: {
                date: date,
                pkgName: pkgName,
                isCache: isCache
            },
            dataType : "json",
            success: function (res) {
                if(res.code==100){
                    showChart(res.data);
                    $('.myLoad').hide();
                    // 重置视频分类的高
                    var siteH=$('#site').height();
					$('#othercidPie').css({height:siteH});
					$('#cidPie').css({height:siteH});
                }else{
                    $(".tab-pane").html("<div style='width: 100%;height: 411px;line-height: 411px;text-align: center'>"+res.msg+"</div>")
                }
				// 导出表格赋值
				var tbody = "";
    			if(pkgName == 'net.myvst.v2' || pkgName == 'com.vst.itv52.v1'){
    				tbody += "<tr>";
    				tbody += "<td><input class='exportBoxForm_selectItem' type='checkbox' name='export_column' value='classify' checked='checked' />分类播放</td>";
    				tbody += "<td><input class='exportBoxForm_selectItem' type='checkbox' name='export_column' value='site' checked='checked' />平台播放</td>";
    				tbody += "<td><input class='exportBoxForm_selectItem' type='checkbox' name='export_column' value='class1' checked='checked' />电影</td>";
    				tbody += "<td><input class='exportBoxForm_selectItem' type='checkbox' name='export_column' value='class2' checked='checked' />电视剧</td>";
    				tbody += "</tr>";
    				tbody += "<tr>";
    				tbody += "<td><input class='exportBoxForm_selectItem' type='checkbox' name='export_column' value='class3' checked='checked' />动漫</td>";
    				tbody += "<td><input class='exportBoxForm_selectItem' type='checkbox' name='export_column' value='class4' checked='checked' />综艺</td>";
    				tbody += "<td><input class='exportBoxForm_selectItem' type='checkbox' name='export_column' value='class5' checked='checked' />纪录片</td>";
    				tbody += "<td><input class='exportBoxForm_selectItem' type='checkbox' name='export_column' value='class8' checked='checked' />少儿</td>";
    				tbody += "</tr>";
    				tbody += "<tr>";
    				tbody += "<td><input class='exportBoxForm_selectItem' type='checkbox' name='export_column' value='class525' checked='checked' />体育</td>";
    				tbody += "<td><input class='exportBoxForm_selectItem' type='checkbox' name='export_column' value='class529' checked='checked' />游戏</td>";
    				tbody += "<td><input class='exportBoxForm_selectItem' type='checkbox' name='export_column' value='class514' checked='checked' />新闻</td>";
    				tbody += "<td><input class='exportBoxForm_selectItem' type='checkbox' name='export_column' value='class515' checked='checked' />自媒体</td>";
    				tbody += "</tr>";
    				tbody += "<tr>";
    				tbody += "<td><input class='exportBoxForm_selectItem' type='checkbox' name='export_column' value='class526' checked='checked' />教育</td>";
    				tbody += "</tr>";
    			}else if(pkgName == 'com.xiaowei.media' || pkgName == 'com.xiaowei.media.ios'){
    				tbody += "<tr>";
    				tbody += "<td><input class='exportBoxForm_selectItem' type='checkbox' name='export_column' value='classify' checked='checked' />分类播放</td>";
    				tbody += "<td><input class='exportBoxForm_selectItem' type='checkbox' name='export_column' value='site' checked='checked' />平台播放</td>";
    				tbody += "<td><input class='exportBoxForm_selectItem' type='checkbox' name='export_column' value='class1' checked='checked' />电影</td>";
    				tbody += "<td><input class='exportBoxForm_selectItem' type='checkbox' name='export_column' value='class2' checked='checked' />电视剧</td>";
    				tbody += "</tr>";
    				tbody += "<tr>";
    				tbody += "<td><input class='exportBoxForm_selectItem' type='checkbox' name='export_column' value='class3' checked='checked' />动漫</td>";
    				tbody += "<td><input class='exportBoxForm_selectItem' type='checkbox' name='export_column' value='class4' checked='checked' />综艺</td>";
    				tbody += "<td><input class='exportBoxForm_selectItem' type='checkbox' name='export_column' value='class5' checked='checked' />纪录片</td>";
    				tbody += "<td><input class='exportBoxForm_selectItem' type='checkbox' name='export_column' value='class6' checked='checked' />少儿</td>";
    				tbody += "</tr>";
    				tbody += "<tr>";
    				tbody += "<td><input class='exportBoxForm_selectItem' type='checkbox' name='export_column' value='class1304' checked='checked' />直播</td>";
    				tbody += "</tr>";
    			}
    			$("#exportTbody").html(tbody);
            }
        })
    }
    
    function showChart(data) {
        //1:电影，2：电视剧，3：动漫，4：综艺，5：纪录片，7：体育，8：少儿，514：新闻，515：自媒体，525：体育，526：教育，529：游戏
        var tabList;
        var pkgName = $("#pkgName").val();
        if(pkgName == 'net.myvst.v2' || pkgName == 'com.vst.itv52.v1'){
        	tabList = [
	            {id: 1, name: 'movie'},
	            {id: 2, name: 'tv'},
	            {id: 3, name: 'dm'},
	            {id: 4, name: 'zy'},
	            {id: 5, name: 'jlp'},
	            {id: 7, name: 'sportOld'},
	            {id: 8, name: 'children'},
	            {id: 514, name: 'news'},
	            {id: 515, name: 'selfmedia'},
	            {id: 525, name: 'sport'},
	            {id: 526, name: 'study'},
	            {id: 529, name: 'game'}
	        ];
	        $(".pkgTable1").show();
	        $(".pkgTable2").hide();
        }else if(pkgName == 'com.xiaowei.media' || pkgName == 'com.xiaowei.media.ios'){
        	tabList = [
				{id: 1304, name: 'live'},
  	            {id: 1, name: 'movie'},
  	            {id: 2, name: 'tv'},
  	            {id: 3, name: 'dm'},
  	            {id: 4, name: 'zy'},
  	            {id: 5, name: 'jlp'},
  	            {id: 6, name: 'children'}
  	        ];
        	$(".pkgTable1").hide();
	        $(".pkgTable2").show();
        }else{
        	$(".pkgTable1").show();
	        $(".pkgTable2").hide();
        }
        
        $.each(tabList, function (index, value) {
            $("#" + this.name).html('');
            var vv = null;
            var uv = null;
            if (data.movieVV && data.movieUV) {
                vv = data.movieVV['vv_' + this.id];
                uv = data.movieUV['uv_' + this.id];
            }
            if (vv && uv) {
                showTable(vv, uv, this.name);
            }
        });
        $("#site").html('');
        if(data.siteVV && data.siteUV){
            showSite(data.siteVV, data.siteUV, 'site');
        }
        $("#cidPie").html('');
        $("#othercidPie").html('');
        if(data.classifyVV && data.classifyUV){
            pie(data.classifyVV, data.classifyUV);
        }
    }

	// 刷新缓存数据
    function refreshData(){
    	getHomeData($("#homeDate").val(), $("#pkgName").val(), false);
    }

    /****************************导出******************************/
	// 导出数据
	function exportData(){
		// 打开弹窗
		$("#export_myModal").attr("class","modal fade in");
		$("#export_myModal").attr("aria-hidden","false");
		$("#export_myModal").show();
		$("#exportBoxForm")[0].reset();//重置
		$("#exportSubmit").removeAttr("disabled");//解除提交按钮禁用状态
	}

	$(document).ready(function(){
  		$.formValidator.initConfig({
  			validatorgroup:"1",
	  		formid:"exportBoxForm",
	  		wideword: false,
	  		onerror : function(msg) {},
			onsuccess:doSubmit
		});
		
		// 关闭弹框事件
		$(".close").click(function(){
			$("#export_myModal").hide();
		});
		
		// 导出的全选功能
		$("#exportBoxForm #exportBoxForm_selectAll").change(function(){
			$("#exportBoxForm .exportBoxForm_selectItem").prop("checked",$(this).prop("checked"));
		});
		$("#exportSubmit").click(function(){
			var flag = $.formValidator.pageIsValid("1");// 触发全局校验
			if(flag){
				var export_column = "";
				$("input[name='export_column']").each(function(){
					if($(this).is(':checked')){
						export_column += $(this).val() + ",";
					}
				});
				if(export_column == null || export_column == ''){
					alert("您未勾选要导出的字段！");
					$("#exportSubmit").removeAttr("disabled");
					return false;
				}else{
					export_column = export_column.substring(0,export_column.length-1);
				}
				if(confirm("导出速度，由数据大小决定，请耐心等待。。")){
					var date = $("#homeDate").val();
					var pkgName = $("#pkgName").val();

					var params = "date=" + date + "&pkgName=" + pkgName;
					params += "&export_column=" + encodeURI(encodeURI(export_column));
					
					location.href = "${ctx}/index/exportData.action?" + params;
				}
			}else{
				return false;
			}
			$("#export_myModal .modal-header .close span").click();
		});
	});
	
	// 提交处理
	function doSubmit() {
		//提交后禁用提交按钮，避免多次提交
		$("#exportSubmit").attr("disabled", "disabled");
		return true;
	}
	/****************************导出******************************/
</script>
</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@include file="share/header.jsp" %>
    <%@include file="share/leftMenu.jsp"%>
    <%@ include file="share/sharForm.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper" style="overflow-y:auto;background-color: #ecf0f5;">
    
    	<!--导出弹窗-->
		<div class="modal fade" id="export_myModal" tabindex="-1" role="dialog">
			<div class="modal-dialog" role="document" style="margin: 100px auto;">
				<div class="modal-content">
					<div class="modal-header">
						<div class="close" data-dismiss="modal" aria-label="Close" style="margin-top: 0; overflow: hidden;">
							<span aria-hidden="true" style="display: block; line-height: 20px; font-size: 40px;">&times;</span>
						</div>
						<h4 class="modal-title">
							<i class="fa fa-home"></i><span style="font-size: 14px;">首页 > 数据概况 > 导出</span>
						</h4>
					</div>
					<div class="modal-body" style="padding: 0 15px; max-height: 450px; overflow: auto;">
						<form id="exportBoxForm" method="post" enctype="multipart/form-data">
							<table class="export-table">
								<thead>
									<tr>
										<th width="20%">
											<input type="checkbox" id="exportBoxForm_selectAll" checked="checked" />全选
										</th>
										<th width="20%"></th>
										<th width="20%"></th>
										<th width="20%"></th>
									</tr>
								</thead>
								<tbody id="exportTbody">
									
								</tbody>
							</table>
						</form>
					</div>
					<div class="modal-footer" style="position: relative; bottom: 0; right: 0;">
						<input type="submit" class="btnSubmit" id="exportSubmit" value="导出" />
					</div>
				</div>
			</div>
		</div>
		<!--导出弹窗-->
    
        <section class="content-header">
            <h1>
            	数据概况
            	<i id="refresh" class="fa fa-refresh" onclick="refreshData();"></i>&nbsp;
            	<i id="export" class="fa fa-file-excel-o" onclick="exportData();"></i>
            </h1>
            <ol class="breadcrumb">
                <li>
                	日期：<input type="text" class="laydate-icon" id="homeDate" size="16" readonly="readonly"
                              style="width:240px;line-height:30px;height: 30px;padding-left: 10px;">
                </li>
            </ol>
        </section>
        
        <section class="content" id="pkg_table1">
            <div class="row">
                <!-- Left col -->
                <section class="col-lg-6 connectedSortable connectedSortableLeft">
                    
                    <!-- 分类播放 -->
                    <div class="nav-tabs-custom">
                        <!--小屏幕-->
                        <div class="row-screen">
                            <div class="pull-left header">
                                <i class="fa fa-bar-chart-o table-left-icon"></i>&nbsp;&nbsp;
                                <small class="table-left-title">视频分类播放占比</small>
                            </div>
                            <div class="small-screen pull-right">
                                <div class="table-active act-div">
                                    <span class="table-active-class">播放量</span>
                                </div>
                                <ul class="small-screen-table">
                                    <li class="active bug">
                                        <a href="javascript:void(0)" class="tab_switch cidPie" data-toggle="tab"
                                           onclick="changeTab('cidPie','othercidPie')">播放量</a>
                                    </li>
                                    <li>
                                        <a href="javascript:void(0)" class="tab_switch othercidPie" data-toggle="tab"
                                           onclick="changeTab('othercidPie','cidPie')">观看人数</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <!-- 大屏幕 -->
                        <ul class="nav nav-tabs pull-right big-screen">
                            <li><a href="javascript:void(0)" data-toggle="tab" class="tab_switch othercidPie"
                                   onclick="changeTab('othercidPie','cidPie')">观看人数</a></li>
                            <li class="active"><a href="javascript:void(0)" data-toggle="tab" class="tab_switch cidPie"
                                                  onclick="changeTab('cidPie','othercidPie')">播放量</a></li>
                            <li class="pull-left header"><i class="fa fa-bar-chart-o"></i>
                                <small>视频分类播放占比</small>
                            </li>
                        </ul>
                        <div class="tab-content no-padding">
                            <div class="chart tab-pane active" id="cidPie"
                                 style="position: relative;width: 100%; min-height: 411px;">
                                <div class="myLoad"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
                            </div>
                            <div class="chart tab-pane" id="othercidPie"
                                 style="position: relative;width: 100%; min-height: 411px;"></div>
                        </div>
                    </div>
                    
                    <!-- 电视剧  -->
                    <div class="nav-tabs-custom">
                        <!--小屏幕-->
                        <div class="row-screen">
                            <div class="pull-left header">
                                <i class="fa fa-bar-chart-o table-left-icon"></i>&nbsp;&nbsp;
                                <small class="table-left-title">电视剧播放排行榜</small>
                            </div>
                            <div class="small-screen pull-right">
                                <div class="table-active act-div">
                                    <span class="table-active-class">播放量</span>
                                </div>
                                <ul class="small-screen-table">
                                    <li class="active bug"><a href="javascript:void(0)" class="tab_switch tv"
                                                              data-toggle="tab"
                                                              onclick="changeTab('tv','othertv')">播放量</a></li>
                                    <li><a href="javascript:void(0)" class="tab_switch othertv" data-toggle="tab"
                                           onclick="changeTab('othertv','tv')">观看人数</a></li>
                                </ul>
                            </div>
                        </div>
                        <!-- 大屏幕 -->
                        <ul class="nav nav-tabs pull-right big-screen">
                            <li><a href="javascript:void(0)" data-toggle="tab" class="othertv"
                                   onclick="changeTab('othertv','tv')">观看人数</a></li>
                            <li class="active"><a href="javascript:void(0)" data-toggle="tab" class="tv"
                                                  onclick="changeTab('tv','othertv')">播放量</a></li>
                            <li class="pull-left header"><i class="fa fa-bar-chart-o"></i>
                                <small>电视剧播放排行榜</small>
                            </li>
                        </ul>
                        <div class="tab-content no-padding">
                            <div class="chart tab-pane active" id="tv" style="position: relative; min-height: 411px;">
                                <div class="myLoad"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
                            </div>
                            <div class="chart tab-pane " id="othertv" style="position: relative; min-height: 411px;"></div>
                        </div>
                    </div>
                    
                    <!-- 综艺 -->
                    <div class="nav-tabs-custom">
                        <!--小屏幕-->
                        <div class="row-screen">
                            <div class="pull-left header">
                                <i class="fa fa-bar-chart-o table-left-icon"></i>&nbsp;&nbsp;
                                <small class="table-left-title">综艺播放排行榜</small>
                            </div>
                            <div class="small-screen pull-right">
                                <div class="table-active act-div">
                                    <span class="table-active-class">播放量</span>
                                </div>
                                <ul class="small-screen-table">
                                    <li class="active bug"><a href="javascript:void(0)" class="tab_switch zy"
                                                              data-toggle="tab"
                                                              onclick="changeTab('zy','otherzy')">播放量</a></li>
                                    <li><a href="javascript:void(0)" class="tab_switch" data-toggle="tab otherzy"
                                           onclick="changeTab('otherzy','zy')">观看人数</a></li>
                                </ul>
                            </div>
                        </div>
                        <!-- 大屏幕 -->
                        <ul class="nav nav-tabs pull-right big-screen">
                            <li><a href="javascript:void(0)" data-toggle="tab" class="otherzy"
                                   onclick="changeTab('otherzy','zy')">观看人数</a></li>
                            <li class="active"><a href="javascript:void(0)" data-toggle="tab" class="zy"
                                                  onclick="changeTab('zy','otherzy')">播放量</a></li>
                            <li class="pull-left header"><i class="fa fa-bar-chart-o"></i>
                                <small>综艺播放排行榜</small>
                            </li>
                        </ul>
                        <div class="tab-content no-padding">
                            <!-- Morris chart - Sales -->
                            <div class="chart tab-pane active" id="zy" style="position: relative; min-height: 411px;">
                                <div class="myLoad"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
                            </div>
                            <div class="chart tab-pane" id="otherzy" style="position: relative; min-height: 411px;"></div>
                        </div>
                    </div>

                    <!-- 动漫 -->
                    <div class="nav-tabs-custom">
                        <!--小屏幕-->
                        <div class="row-screen">
                            <div class="pull-left header">
                                <i class="fa fa-bar-chart-o table-left-icon"></i>&nbsp;&nbsp;
                                <small class="table-left-title">动漫播放排行榜</small>
                            </div>
                            <div class="small-screen pull-right">
                                <div class="table-active act-div">
                                    <span class="table-active-class">播放量</span>
                                </div>
                                <ul class="small-screen-table">
                                    <li class="active"><a href="javascript:void(0)" class="tab_switch dm"
                                                          data-toggle="tab" onclick="changeTab('dm','otherdm')">播放量</a>
                                    </li>
                                    <li><a href="javascript:void(0)" class="tab_switch otherdm" data-toggle="tab"
                                           onclick="changeTab('otherdm','dm')">观看人数</a></li>
                                </ul>
                            </div>
                        </div>
                        <!-- 大屏幕 -->
                        <ul class="nav nav-tabs pull-right big-screen">
                            <li><a href="javascript:void(0)" data-toggle="tab" class="otherdm"
                                   onclick="changeTab('otherdm','dm')">观看人数</a></li>
                            <li class="active"><a href="javascript:void(0)" data-toggle="tab" class="dm"
                                                  onclick="changeTab('dm','otherdm')">播放量</a></li>
                            <li class="pull-left header"><i class="fa fa-bar-chart-o"></i>
                                <small>动漫播放排行榜</small>
                            </li>
                        </ul>
                        <div class="tab-content no-padding">
                            <!-- Morris chart - Sales -->
                            <div class="chart tab-pane active" id="dm" style="position: relative; min-height: 411px;">
                                <div class="myLoad"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
                            </div>
                            <div class="chart tab-pane" id="otherdm" style="position: relative; min-height: 411px;"></div>
                        </div>
                    </div>

                    <!-- 游戏 -->
                    <div class="nav-tabs-custom pkgTable1">
                        <!--小屏幕-->
                        <div class="row-screen">
                            <div class="pull-left header">
                                <i class="fa fa-bar-chart-o table-left-icon"></i>&nbsp;&nbsp;
                                <small class="table-left-title">游戏播放排行榜</small>
                            </div>
                            <div class="small-screen pull-right">
                                <div class="table-active act-div">
                                    <span class="table-active-class">播放量</span>
                                </div>
                                <ul class="small-screen-table">
                                    <li class="active"><a href="javascript:void(0)" class="tab_switch game"
                                                          data-toggle="tab"
                                                          onclick="changeTab('game','othergame')">播放量</a></li>
                                    <li><a href="javascript:void(0)" class="tab_switch othergame" data-toggle="tab"
                                           onclick="changeTab('othergame','game')">观看人数</a></li>
                                </ul>
                            </div>
                        </div>
                        <!-- 大屏幕 -->
                        <ul class="nav nav-tabs pull-right big-screen">
                            <li><a href="javascript:void(0)" data-toggle="tab" class="othergame"
                                   onclick="changeTab('othergame','game')">观看人数</a></li>
                            <li class="active"><a href="javascript:void(0)" data-toggle="tab" class="game"
                                                  onclick="changeTab('game','othergame')">播放量</a></li>
                            <li class="pull-left header"><i class="fa fa-bar-chart-o"></i>
                                <small>游戏播放排行榜</small>
                            </li>
                        </ul>
                        <div class="tab-content no-padding">
                            <!-- Morris chart - Sales -->
                            <div class="chart tab-pane active" id="game" style="position: relative; min-height: 411px;">
                                <div class="myLoad"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
                            </div>
                            <div class="chart tab-pane" id="othergame" style="position: relative; min-height: 411px;"></div>
                        </div>
                    </div>

                    <!-- 纪录片 -->
                    <div class="nav-tabs-custom">
                        <!--小屏幕-->
                        <div class="row-screen">
                            <div class="pull-left header">
                                <i class="fa fa-bar-chart-o table-left-icon"></i>&nbsp;&nbsp;
                                <small class="table-left-title">纪录片播放排行榜</small>
                            </div>
                            <div class="small-screen pull-right">
                                <div class="table-active act-div">
                                    <span class="table-active-class">播放量</span>
                                </div>
                                <ul class="small-screen-table">
                                    <li class="active"><a href="javascript:void(0)" class="tab_switch jlp"
                                                          data-toggle="tab"
                                                          onclick="changeTab('jlp','otherjlp')">播放量</a></li>
                                    <li><a href="javascript:void(0)" class="tab_switch otherjlp" data-toggle="tab"
                                           onclick="changeTab('otherjlp','jlp')">观看人数</a></li>
                                </ul>
                            </div>
                        </div>
                        <!-- 大屏幕 -->
                        <ul class="nav nav-tabs pull-right big-screen">
                            <li><a href="javascript:void(0)" data-toggle="tab" class="otherjlp"
                                   onclick="changeTab('otherjlp','jlp')">观看人数</a></li>
                            <li class="active"><a href="javascript:void(0)" data-toggle="tab" class="jlp"
                                                  onclick="changeTab('jlp','otherjlp')">播放量</a></li>
                            <li class="pull-left header"><i class="fa fa-bar-chart-o"></i>
                                <small>纪录片播放排行榜</small>
                            </li>
                        </ul>
                        <div class="tab-content no-padding">
                            <!-- Morris chart - Sales -->
                            <div class="chart tab-pane active" id="jlp" style="position: relative; min-height: 411px;">
                                <div class="myLoad"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
                            </div>
                            <div class="chart tab-pane" id="otherjlp" style="position: relative; min-height: 411px;"></div>
                        </div>
                    </div>

                    <!-- 教育 -->
                    <div class="nav-tabs-custom pkgTable1">
                        <!--小屏幕-->
                        <div class="row-screen">
                            <div class="pull-left header">
                                <i class="fa fa-bar-chart-o table-left-icon"></i>&nbsp;&nbsp;
                                <small class="table-left-title">教育播放排行榜</small>
                            </div>
                            <div class="small-screen pull-right">
                                <div class="table-active act-div">
                                    <span class="table-active-class">播放量</span>
                                </div>
                                <ul class="small-screen-table">
                                    <li class="active"><a href="javascript:void(0)" class="tab_switch study"
                                                          data-toggle="tab" onclick="changeTab('study','otherstudy')">播放量</a>
                                    </li>
                                    <li><a href="javascript:void(0)" class="tab_switch otherstudy" data-toggle="tab"
                                           onclick="changeTab('otherstudy','study')">观看人数</a></li>
                                </ul>
                            </div>
                        </div>
                        <!-- 大屏幕 -->
                        <ul class="nav nav-tabs pull-right big-screen">
                            <li><a href="javascript:void(0)" data-toggle="tab" class="otherstudy"
                                   onclick="changeTab('otherstudy','study')">观看人数</a></li>
                            <li class="active"><a href="javascript:void(0)" data-toggle="tab" class="study"
                                                  onclick="changeTab('study','otherstudy')">播放量</a></li>
                            <li class="pull-left header"><i class="fa fa-bar-chart-o"></i>
                                <small>教育播放排行榜</small>
                            </li>
                        </ul>
                        <div class="tab-content no-padding">
                            <div class="chart tab-pane active" id="study" style="position: relative; min-height: 411px;">
                                <div class="myLoad"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
                            </div>
                            <div class="chart tab-pane" id="otherstudy"
                                 style="position: relative; min-height: 411px;"></div>
                        </div>
                    </div>
                    
                </section>
                
                <!-- Right col -->
                <section class="col-lg-6 connectedSortable connectedSortableRight">
                    
                    <!-- 平台播放 -->
                    <div class="nav-tabs-custom">
                        <!--小屏幕-->
                        <div class="row-screen">
                            <div class="pull-left header">
                                <i class="fa fa-bar-chart-o table-left-icon"></i>&nbsp;&nbsp;
                                <small class="table-left-title">平台播放排行榜</small>
                            </div>
                            <div class="small-screen pull-right">
                                <div class="table-active act-div">
                                    <span class="table-active-class">播放量</span>
                                </div>
                                <ul class="small-screen-table">
                                    <li class="active"><a href="javascript:void(0)" class="tab_switch site"
                                                          data-toggle="tab"
                                                          onclick="changeTab('site','othersite')">播放量</a></li>
                                    <li><a href="javascript:void(0)" class="tab_switch othersite" data-toggle="tab"
                                           onclick="changeTab('othersite','site')">观看人数</a></li>
                                </ul>
                            </div>
                        </div>
                        <!-- 大屏幕 -->
                        <ul class="nav nav-tabs pull-right big-screen">
                            <li><a href="javascript:void(0)" data-toggle="tab" class="othersite"
                                   onclick="changeTab('othersite','site')">观看人数</a></li>
                            <li class="active"><a href="javascript:void(0)" data-toggle="tab" class="site"
                                                  onclick="changeTab('site','othersite')">播放量</a></li>
                            <li class="pull-left header"><i class="fa fa-bar-chart-o"></i>
                                <small>平台播放排行榜</small>
                            </li>
                        </ul>
                        <div class="tab-content no-padding">
                            <!-- Morris chart - Sales -->
                            <div class="chart tab-pane active" id=site style="position: relative; min-height: 411px;">
                                <div class="myLoad"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
                            </div>
                            <div class="chart tab-pane" id="othersite" style="position: relative;min-height: 411px; "></div>
                        </div>
                    </div>

                    <!-- 少儿 -->
                    <div class="nav-tabs-custom">
                        <!--小屏幕-->
                        <div class="row-screen">
                            <div class="pull-left header">
                                <i class="fa fa-bar-chart-o table-left-icon"></i>&nbsp;&nbsp;
                                <small class="table-left-title">少儿播放排行榜</small>
                            </div>
                            <div class="small-screen pull-right">
                                <div class="table-active act-div">
                                    <span class="table-active-class">播放量</span>
                                </div>
                                <ul class="small-screen-table">
                                    <li class="active"><a href="javascript:void(0)" class="tab_switch children"
                                                          data-toggle="tab"
                                                          onclick="changeTab('children','otherchildren')">播放量</a></li>
                                    <li><a href="javascript:void(0)" class="tab_switch otherchildren" data-toggle="tab"
                                           onclick="changeTab('otherchildren','children')">观看人数</a></li>
                                </ul>
                            </div>
                        </div>
                        <!-- 大屏幕 -->
                        <ul class="nav nav-tabs pull-right big-screen">
                            <li><a href="javascript:void(0)" data-toggle="tab" class="otherchildren"
                                   onclick="changeTab('otherchildren','children')">观看人数</a></li>
                            <li class="active"><a href="javascript:void(0)" data-toggle="tab" class="children"
                                                  onclick="changeTab('children','otherchildren')">播放量</a></li>
                            <li class="pull-left header"><i class="fa fa-bar-chart-o"></i>
                                <small>少儿播放排行榜</small>
                            </li>
                        </ul>
                        <div class="tab-content no-padding">
                            <!-- Morris chart - Sales -->
                            <div class="chart tab-pane active" id="children" style="position: relative; min-height: 411px;">
                                <div class="myLoad"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
                            </div>
                            <div class="chart tab-pane" id="otherchildren"
                                 style="position: relative; min-height: 411px;"></div>
                        </div>
                    </div>

                    <!-- 电影 -->
                    <div class="nav-tabs-custom">
                        <!--小屏幕-->
                        <div class="row-screen">
                            <div class="pull-left header">
                                <i class="fa fa-bar-chart-o table-left-icon"></i>&nbsp;&nbsp;
                                <small class="table-left-title">电影播放排行榜</small>
                            </div>
                            <div class="small-screen pull-right">
                                <div class="table-active act-div">
                                    <span class="table-active-class">播放量</span>
                                </div>
                                <ul class="small-screen-table">
                                    <li class="active"><a href="javascript:void(0)" class="tab_switch movie"
                                                          data-toggle="tab"
                                                          onclick="changeTab('movie','othmovie')">播放量</a></li>
                                    <li><a href="javascript:void(0)" class="tab_switch othmovie" data-toggle="tab"
                                           onclick="changeTab('othermovie','movie')">观看人数</a></li>
                                </ul>
                            </div>
                        </div>
                        <!-- 大屏幕 -->
                        <ul class="nav nav-tabs pull-right big-screen">
                            <li><a href="javascript:void(0)" data-toggle="tab" class="othermovie"
                                   onclick="changeTab('othermovie','movie')">观看人数</a></li>
                            <li class="active"><a href="javascript:void(0)" data-toggle="tab" class="movie"
                                                  onclick="changeTab('movie','othermovie')">播放量</a></li>
                            <li class="pull-left header"><i class="fa fa-bar-chart-o"></i>
                                <small>电影播放排行榜</small>
                            </li>
                        </ul>
                        <div class="tab-content no-padding">
                            <!-- Morris chart - Sales -->
                            <div class="chart tab-pane active" id="movie" style="position: relative; min-height: 411px;">
                                <div class="myLoad"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
                            </div>
                            <div class="chart tab-pane" id="othermovie"
                                 style="position: relative; min-height: 411px;"></div>
                        </div>
                    </div>

                    <!-- 体育 -->
                    <div class="nav-tabs-custom pkgTable1">
                        <!--小屏幕-->
                        <div class="row-screen">
                            <div class="pull-left header">
                                <i class="fa fa-bar-chart-o table-left-icon"></i>&nbsp;&nbsp;
                                <small class="table-left-title">体育播放排行榜</small>
                            </div>
                            <div class="small-screen pull-right">
                                <div class="table-active act-div">
                                    <span class="table-active-class">播放量</span>
                                </div>
                                <ul class="small-screen-table">
                                    <li class="active bug"><a href="javascript:void(0)" class="tab_switch sport"
                                                              data-toggle="tab"
                                                              onclick="changeTab('sport','othersport')">播放量</a></li>
                                    <li><a href="javascript:void(0)" class="tab_switch othersport" data-toggle="tab"
                                           onclick="changeTab('othersport','sport')">观看人数</a></li>
                                </ul>
                            </div>
                        </div>
                        <!-- 大屏幕 -->
                        <ul class="nav nav-tabs pull-right big-screen">
                            <li><a href="javascript:void(0)" data-toggle="tab" class="othersport"
                                   onclick="changeTab('othersport','sport')">观看人数</a></li>
                            <li class="active"><a href="javascript:void(0)" data-toggle="tab" class="sport"
                                                  onclick="changeTab('sport','othersport')">播放量</a></li>
                            <li class="pull-left header"><i class="fa fa-bar-chart-o"></i>
                                <small>体育播放排行榜</small>
                            </li>
                        </ul>
                        <div class="tab-content no-padding">
                            <!-- Morris chart - Sales -->
                            <div class="chart tab-pane active" id="sport" style="position: relative; min-height: 411px;">
                                <div class="myLoad"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
                            </div>
                            <div class="chart tab-pane" id="othersport"
                                 style="position: relative;min-height: 411px; "></div>
                        </div>
                    </div>

                    <!-- 新闻 -->
                    <div class="nav-tabs-custom pkgTable1">
                        <!--小屏幕-->
                        <div class="row-screen">
                            <div class="pull-left header">
                                <i class="fa fa-bar-chart-o table-left-icon"></i>&nbsp;&nbsp;
                                <small class="table-left-title">新闻播放排行榜</small>
                            </div>
                            <div class="small-screen pull-right">
                                <div class="table-active act-div">
                                    <span class="table-active-class">播放量</span>
                                </div>
                                <ul class="small-screen-table">
                                    <li class="active bug"><a href="javascript:void(0)" class="tab_switch news"
                                                              data-toggle="tab" onclick="changeTab('news','othernews')">播放量</a>
                                    </li>
                                    <li><a href="javascript:void(0)" class="tab_switch othernews" data-toggle="tab"
                                           onclick="changeTab('othernews','news')">观看人数</a></li>
                                </ul>
                            </div>
                        </div>
                        <!-- 大屏幕 -->
                        <ul class="nav nav-tabs pull-right big-screen">
                            <li><a href="javascript:void(0)" data-toggle="tab" class="othernews"
                                   onclick="changeTab('othernews','news')">观看人数</a></li>
                            <li class="active"><a href="javascript:void(0)" data-toggle="tab" class="news"
                                                  onclick="changeTab('news','othernews')">播放量</a></li>
                            <li class="pull-left header"><i class="fa fa-bar-chart-o"></i>
                                <small>新闻播放排行榜</small>
                            </li>
                        </ul>
                        <div class="tab-content no-padding">
                            <!-- Morris chart - Sales -->
                            <div class="chart tab-pane active" id="news" style="position: relative;min-height: 411px; ">
                                <div class="myLoad"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
                            </div>
                            <div class="chart tab-pane" id="othernews" style="position: relative; min-height: 411px;"></div>
                        </div>
                    </div>

                    <!-- 自媒体 -->
                    <div class="nav-tabs-custom pkgTable1">
                        <!--小屏幕-->
                        <div class="row-screen">
                            <div class="pull-left header">
                                <i class="fa fa-bar-chart-o table-left-icon"></i>&nbsp;&nbsp;
                                <small class="table-left-title">自媒体播放排行榜</small>
                            </div>
                            <div class="small-screen pull-right">
                                <div class="table-active act-div">
                                    <span class="table-active-class">播放量</span>
                                </div>
                                <ul class="small-screen-table">
                                    <li class="active"><a href="javascript:void(0)" class="tab_switch selfmedia"
                                                          data-toggle="tab"
                                                          onclick="changeTab('selfmedia','otherselfmedia')">播放量</a></li>
                                    <li><a href="javascript:void(0)" class="tab_switch otherselfmedia" data-toggle="tab"
                                           onclick="changeTab('otherselfmedia','selfmedia')">观看人数</a></li>
                                </ul>
                            </div>
                        </div>
                        <!-- 大屏幕 -->
                        <ul class="nav nav-tabs pull-right big-screen">
                            <li><a href="javascript:void(0)" data-toggle="tab" class="otherselfmedia"
                                   onclick="changeTab('otherselfmedia','selfmedia')">观看人数</a></li>
                            <li class="active"><a href="javascript:void(0)" data-toggle="tab" class="selfmedia"
                                                  onclick="changeTab('selfmedia','otherselfmedia')">播放量</a></li>
                            <li class="pull-left header"><i class="fa fa-bar-chart-o"></i>
                                <small>自媒体播放排行榜</small>
                            </li>
                        </ul>
                        <div class="tab-content no-padding">
                            <!-- Morris chart - Sales -->
                            <div class="chart tab-pane active" id="selfmedia"
                                 style="position: relative; min-height: 411px;">
                                <div class="myLoad"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
                            </div>
                            <div class="chart tab-pane" id="otherselfmedia"
                                 style="position: relative; min-height: 411px;"></div>
                        </div>
                    </div>
                    
                    <!-- 直播 -->
                    <div class="nav-tabs-custom pkgTable2">
                        <!--小屏幕-->
                        <div class="row-screen">
                            <div class="pull-left header">
                                <i class="fa fa-bar-chart-o table-left-icon"></i>&nbsp;&nbsp;
                                <small class="table-left-title">直播播放排行榜</small>
                            </div>
                            <div class="small-screen pull-right">
                                <div class="table-active act-div">
                                    <span class="table-active-class">播放量</span>
                                </div>
                                <ul class="small-screen-table">
                                    <li class="active"><a href="javascript:void(0)" class="tab_switch live"
                                                          data-toggle="tab"
                                                          onclick="changeTab('live','otherlive')">播放量</a></li>
                                    <li><a href="javascript:void(0)" class="tab_switch otherlive" data-toggle="tab"
                                           onclick="changeTab('otherlive','live')">观看人数</a></li>
                                </ul>
                            </div>
                        </div>
                        <!-- 大屏幕 -->
                        <ul class="nav nav-tabs pull-right big-screen">
                            <li><a href="javascript:void(0)" data-toggle="tab" class="otherlive"
                                   onclick="changeTab('otherlive','live')">观看人数</a></li>
                            <li class="active"><a href="javascript:void(0)" data-toggle="tab" class="live"
                                                  onclick="changeTab('live','otherlive')">播放量</a></li>
                            <li class="pull-left header"><i class="fa fa-bar-chart-o"></i>
                                <small>直播播放排行榜</small>
                            </li>
                        </ul>
                        <div class="tab-content no-padding">
                            <!-- Morris chart - Sales -->
                            <div class="chart tab-pane active" id="live"
                                 style="position: relative; min-height: 411px;">
                                <div class="myLoad"><img class="loadImg" src='${ctx}/pub/images/loading1.gif'/></div>
                            </div>
                            <div class="chart tab-pane" id="otherlive"
                                 style="position: relative;min-height: 411px; "></div>
                        </div>
                    </div>
                    
                </section>
            </div>
        </section>
        
    </div>
    <%@include file="share/footer.jsp" %>
    <div class="control-sidebar-bg"></div>
</div>

<script>
	$(function () {
	    laydate.render({
	        elem : '#homeDate',
	        max : +new Date(),
	        done : function (value, date) {
	            getHomeData(value,$("#pkgName").val());
	        }
	    });
	
	    var yesterday = GetDay();
	    $("#homeDate").attr("value", yesterday);
	    $(".nav-tabs-custom ul.nav>li.active").find("a").append("<img class='desMyImg' src='${ctx}/pub/images/desc.gif'/>");
	
	    getHomeData(yesterday, $("#pkgName").val());
	    
	    $(window).resize(function(){
	    	// 重置视频分类的高
	        var siteH=$('#site').height();
			$('#othercidPie').css({height:siteH});
			$('#cidPie').css({height:siteH});
	    })
	    $('.tab_switch').click(function () {
	        $(window).resize();
	        var objctid = $(this).attr('href');
	        $(objctid).addClass('active');
	        $('#othercidPie').highcharts().reflow();
	        $('#cidPie').highcharts().reflow();
	    });
	
	    //切换包名
	    $(".pkgName-menu>li").click(function () {
	        var date = $("#homeDate").val();
	    	var pkgName = $(this).attr("data-value");
	    	getHomeData(date, pkgName);
	    });
	});
</script>

</body>
</html>
