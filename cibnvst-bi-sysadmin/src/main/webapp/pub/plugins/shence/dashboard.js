$(document).ready(function () {
    var res = [
        {
            "thead": ["日期", "省份活跃人数"],
            "x": ["11-4", "11-5", "11-6", "11-7", "11-8", "11-9", "11-10"],
            "series": [
                {
                    "name": "邮件营销",
                    "data": [120, 132, 101, 134, 90, 230, 210]
                },
                {
                    "name": "联盟广告",
                    "data": [220, 182, 191, 234, 290, 330, 310]
                },
                {
                    "name": "视频广告",
                    "data": [150, 232, 201, 154, 190, 330, 410]
                }
            ]
        }, {
            "thead": ["日期", "新增用户人数"],
            "x": ["9-1", "9-2", "9-3", "9-4", "9-5", "9-6", "9-7", "9-8", "9-9", "9-10", "9-11", "9-12", "9-13", "9-14", "9-15", "9-16", "9-17", "9-18", "9-19", "9-20", "9-20", "9-22", "9-23", "9-24", "9-25", "9-26", "9-27", "9-28", "9-29", "9-30"],
            "series": [
                {
                    "name": "邮件营销",
                    "data": [120, 132, 101, 134, 90, 230, 210, 120, 132, 101, 134, 90, 230, 210, 120, 132, 101, 134, 90, 230, 210, 120, 132, 101, 134, 90, 230, 210, 230, 212]
                },
                {
                    "name": "联盟广告",
                    "data": [220, 182, 191, 234, 290, 330, 310, 220, 182, 191, 234, 290, 330, 310, 220, 182, 191, 234, 290, 330, 310, 220, 182, 191, 234, 290, 330, 310, 330, 311]
                }
            ]
        }, {
            "thead": ["日期", "视频播放占比"],
            "x": ["9-4", "9-5", "9-6", "9-7", "9-8", "9-9", "9-10"],
            "series": [
                {
                    "name": "邮件营销",
                    "data": [120, 132, 101, 134, 90, 230, 208]
                }
            ]
        }, {
            "thead": ["日期", "综艺排行"],
            "x": ["10-4", "10-5", "10-6", "10-7", "10-8", "10-9", "10-10"],
            "series": [
                {
                    "name": "极限挑战",
                    "data": [1200, 1302, 1010, 1340, 900, 2300, 2008]
                }, {
                    "name": "奔跑吧",
                    "data": [1300, 1402, 1110, 1440, 800, 2600, 2408]
                }, {
                    "name": "非常静距离",
                    "data": [1400, 1502, 1210, 1540, 700, 2500, 2608]
                }
            ]
        }, {
            "thead": ["日期", "电视剧排行"],
            "x": ["9-5", "9-6", "9-7", "9-8", "9-9", "9-10", "9-11"],
            "series": [
                {
                    "name": "猎场",
                    "data": [520, 182, 401, 634, 390, 830, 708]
                }, {
                    "name": "伪装者",
                    "data": [581, 113, 496, 668, 307, 890, 798]
                }, {
                    "name": "仙剑奇侠传",
                    "data": [620, 282, 501, 734, 490, 930, 808]
                }
            ]
        }
    ];

    var option = {
        color: ["#559FF0", "#AACF44", "#FF9945", "#3AD1C5", "#FACF2A", "#FC6772", "#788CF0", "#2DCA93", "#EF717A", "#98AAD4", "#A5C63A", "#79302A", "#34485E", "#BDC3C7", "#A5C63A", "#84C1E9", "#81E0A9", "#F0B17A", "#AB9EDC", "#F0938A", "#FFE166", "#E5DABF", "#75D6C3", "#F5A9AF", "#C1CCE5", "#C9DC88", "#AE827F", "#85919E", "#D7DBDD", "#C9DC88"],
        calculable: true,
        grid: {show: false, top: 25, bottom: 0, right: 4, left: 4, containLabel: true},
        xAxis: {
            type: "category",
            data: [],
            splitLine: {show: false},
            axisLine: {lineStyle: {color: "#ccc"}},
            axisTick: {show: true, lineStyle: {color: "#ccc"}, alignWithLabel: true},
            axisLabel: {
                textStyle: {
                    color: "#7F7F7F",
                    fontFamily: '"Lucida Grande", "Lucida Sans Unicode", Arial, Helvetica, sans-serif',
                    fontSize: 12,
                    fontWeight: 300
                }
            }
        },
        tooltip: {
            borderColor: '#85919E',
            trigger: "axis",//是否节点触发
            borderWidth: 1,
            formatter: function (params) {
                console.log(params)
                $(".dashboard-widget-toolbar-date-text").eq(index).html(params[0].name);
                var html = '';
                $(params).each(function (g, h) {
                    html += '<li><div class="dashboard-widget-key">' + params[g].value + '<span class="dashboard-widget-unit"></span></div>'
                        + '<p class="dashboard-widget-key-caption">' + params[g].seriesName + '</p>'
                        + '</li>';
                });
                $('.dashboard-widget-key-wrap').eq(index).append(html);
                return "";
            }
        },
        radar: {
            triggerEvent: true
        },
        yAxis: {
            axisLine: {show: false},
            axisTick: {show: false},
            splitNumber: 3.5,
            type: 'value',
            scale: true,
            minInterval: 1,
            axisLabel: {
                formatter: '{value}',
                textStyle: {
                    color: "#7F7F7F",
                    fontFamily: '"Lucida Grande", "Lucida Sans Unicode", Arial, Helvetica, sans-serif',
                    fontSize: 12,
                    fontWeight: 300
                }
            }
        },
        axisPointer: {
            color: '#555'
        },
        series: []

    };

    var listArticle = `<article class="dashboard-widget" data-bookmarkid="">
                        <header class="dashboard-widget-header">
                        <div class="dashboard-widget-header-in">
                        <h4 title="视频播放数量"
                    data-href="chartsType=line&amp;sampling_factor=64&amp;from_date=2017-08-31&amp;to_date=2017-09-06&amp;bookmarkid=9&amp;tType=y&amp;ratio=n&amp;axis_config%5BisNormalize%5D=false&amp;rollup_date=false">
                        视频播放数量</h4>
                        <div class="dashboard-widget-timerange" data-calendar-status-refresh-time="1504748643">
                        <span class="timerange">2017-8-31至2017-9-6</span>
                    <span class="icon-edit"></span>
                        <span class="icon-remove"></span>
                        </div>
                        </div>
                        <div class="dashboard-widget-config">
                        <div class="dashboard-widget-dropdown" data-email="no">
                        <div class="widget-config-top dropdown segmentation-unit">
                        <div class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                        <span id="widgetUnit" class="selected" data-value="day">
                        按天
                        </span>
                        <span class="icon-edit"></span><span class="icon-remove"></span>
                        </div>
                        <ul class="dropdown-menu pull-right" role="menu">
                        <li class="multiselect-item">
                        <a data-value="rollup">合计</a>
                        </li>
                        <li role="separator" class="divider"></li>
                        <li class="disabled">
                        <a data-value="minute">按分钟</a>
                        </li>
                        <li>
                        <a data-value="hour">按小时</a>
                        </li>
                        <li class="active">
                        <a data-value="day">按天</a>
                        </li>
                        <li>
                        <a data-value="week">按周</a>
                        </li>
                        <li>
                        <a data-value="month">按月</a>
                        </li>
                        </ul>
                        </div>
                        <div class="widget-config-bottom segmentation-chart-type" id="widgetType1">
                        <span data-method="widgetType" data-value="number" title="数字"
                class="icon-num btn-icon " style="display:none;"></span>
                        <span data-method="widgetType" data-value="line" title="线图"
                class="icon-line-chart btn-icon active"></span>
                        <span data-method="widgetType" data-value="column" title="柱图"
                class="icon-bar-chart btn-icon "></span>
                        <span data-method="widgetType" data-value="pie" title="饼图"
                class="icon-pie-chart btn-icon " style="display:none;"></span>
                        <span data-method="widgetType" data-value="table" title="表格"
                class="icon-table btn-icon "></span>
                        <span data-method="widgetType" data-value="stack" title="累积"
                class="icon-stack-chart btn-icon " style="display:none;"></span>
                        </div>
                        </div>
                        </div>
                        </header>

                        <div class="dashboard-widget-content">
                        <div id="toolbar-container" class="dashboard-widget-toolbar-container">
                        <span class="dashboard-widget-toolbar-date-text">9-4</span>

                        </div>
                        <div id="keyinfo-container" class="dashboard-widget-keyinfo-container">
                        <ul class="dashboard-widget-key-wrap with-chart">

                        </ul>
                        </div>
                        <div id="" class="dashboard-widget-chart"
                    style="-webkit-tap-highlight-color: transparent; user-select: none; position: relative; background: transparent;">
                        </div>
                        </div>
                        </article>`;
// 遍历添加数据概览项
    $(res).each(function (index, item) {
        $('#my_dashboard_main').append(listArticle);
    });
// 遍历添加盒子id
    $('.dashboard-widget-chart').each(function (index, item) {
        $(this).attr('id', 'widget-chart-container' + index)
    });
    $(res).each(function (index, item) {
        var widgetChart = document.getElementById('widget-chart-container' + index);
        var dashboardChart = echarts.init(widgetChart);
        //添加echarts基础配置
        for (var i = 0; i < item.series.length; i++) {
            item.series[i].symbol = 'circle';
            item.series[i].symbolSize = 6;
            item.series[i].type = 'line';
            item.series[i].maxbarWidth = 20;
            item.series[i].stack = '总量';
        }

        option.xAxis.data = item.x;
        option.series = item.series;
        //初始化渲染数据末尾项
        $(".dashboard-widget-toolbar-date-text").eq(index).html(option.xAxis.data[option.xAxis.data.length - 1]);
        var html = '';
        $(item.series).each(function (e, t) {
            var targetSeries = t.data;
            html += '<li><div class="dashboard-widget-key" style="color:' + option.color[e] + ' !important;-webkit-text-fill-color: ' + option.color[e] + ' !important;">' + t.data[t.data.length - 1] + '<span class="dashboard-widget-unit"></span></div>'
                + '<p class="dashboard-widget-key-caption">' + t.name + '</p>'
                + '</li>';
        });
        $('.dashboard-widget-key-wrap').eq(index).append(html);
        option.tooltip.formatter = function (params) {
            $('.dashboard-widget-key-wrap').eq(index).html('');
            $(".dashboard-widget-toolbar-date-text").eq(index).html(params[0].name);
            var html = '';
            $(params).each(function (g, h) {
                html += '<li><div class="dashboard-widget-key" style="color:' + option.color[g] + ' !important;-webkit-text-fill-color: ' + option.color[g] + ' !important;">' + params[g].value + '<span class="dashboard-widget-unit"></span></div>'
                    + '<p class="dashboard-widget-key-caption">' + params[g].seriesName + '</p>'
                    + '</li>';
            });
            $('.dashboard-widget-key-wrap').eq(index).append(html);
            return "";
        };

        function charts_type() {
            //遍历切换折线图，柱形图，表格等
            $('.segmentation-chart-type').each(function (index, elem) {
                $(elem).find('span').each(function (i, m) {
                    $(m).click(function () {
                        $(m).addClass('active').siblings().removeClass('active');
                        var _this = $('.dashboard-widget-chart').eq(index);
                        var thisChart = document.getElementById('widget-chart-container' + index);
                        var thisDashboardChart = echarts.init(thisChart);
                        if (_this.find('table').length != 0) {
                            _this.find('table').remove();
                        }
                        if ($(m).attr('data-value') == 'column') {
                            _this.children('div').show();
                            if (res[index].series.length > 1) {
                                $(res[index].series).each(function (h, l) {
                                    l.type = 'bar';
                                });
                            } else if (res[index].series.length == 1) {
                                res[index].series[0].type = 'bar';
                                res[index].series[0].barWidth = 23;
                            }
                            option.series = res[index].series;

                            _this.siblings('.dashboard-widget-toolbar-container').removeClass('display-none').siblings('.dashboard-widget-keyinfo-container').removeClass('display-none');
                            _this.find('table').hide();
                            option.tooltip.formatter = function (params) {
                                $('.dashboard-widget-key-wrap').eq(index).html('');
                                $(".dashboard-widget-toolbar-date-text").eq(index).html(params[0].name);
                                var html = '';
                                $(params).each(function (g, h) {
                                    html += '<li><div class="dashboard-widget-key" style="color:' + option.color[g] + ' !important;-webkit-text-fill-color: ' + option.color[g] + ' !important;">' + params[g].value + '<span class="dashboard-widget-unit"></span></div>'
                                        + '<p class="dashboard-widget-key-caption">' + params[g].seriesName + '</p>'
                                        + '</li>';
                                });
                                $('.dashboard-widget-key-wrap').eq(index).append(html);
                                return "";
                            };

                            option.xAxis.data = res[index].x;
                            thisDashboardChart.setOption(option);

                        } else if ($(m).attr('data-value') == 'line') {
                            _this.children('div').show();

                            if (res[index].series.length > 1) {
                                $(res[index].series).each(function (h, l) {
                                    l.type = 'line';
                                });
                            } else if (res[index].series.length == 1) {
                                res[index].series[0].type = 'line';
                            }
                            _this.siblings('.dashboard-widget-toolbar-container').removeClass('display-none')
                                .siblings('.dashboard-widget-keyinfo-container').removeClass('display-none');
                            _this.find('table').hide();
                            option.series = res[index].series;

                            option.tooltip.formatter = function (params) {
                                $('.dashboard-widget-key-wrap').eq(index).html('');
                                $(".dashboard-widget-toolbar-date-text").eq(index).html(params[0].name);
                                var html = '';
                                $(params).each(function (g, h) {
                                    html += '<li><div class="dashboard-widget-key" style="color:' + option.color[g] + ' !important;-webkit-text-fill-color: ' + option.color[g] + ' !important;">' + params[g].value + '<span class="dashboard-widget-unit"></span></div>'
                                        + '<p class="dashboard-widget-key-caption">' + params[g].seriesName + '</p>'
                                        + '</li>';
                                });
                                $('.dashboard-widget-key-wrap').eq(index).append(html);
                                return "";
                            };
                            option.xAxis.data = res[index].x;
                            thisDashboardChart.setOption(option);

                        } else if ($(m).attr('data-value') == 'table') {
                            _this.children('div').hide();
                            _this.siblings('.dashboard-widget-toolbar-container').addClass('display-none')
                                .siblings('.dashboard-widget-keyinfo-container').addClass('display-none');
                            var table = '';
                            table = '<table class="table table-striped no-compare-table" style="display: block"> ' +
                                '                                <thead> ' +
                                '                                    <tr> ' +
                                '                                        <th>' + res[index].thead[0] + '</th> ' +
                                '                                        <th title="粉丝数的均值">' + res[index].thead[1] + '</th> ' +
                                '                                    </tr> ' +
                                '                                </thead> ' +
                                '                                <tbody class="tbody"> ' +
                                '                                </tbody> ' +
                                '                            </table>';

                            var html = '';

                            $(res[index].series).each(function (u, m) {
                                $(res[index].x).each(function (o, p) {
                                    html += '<tr>' +
                                        '         <td><span class="td-container">' + res[index].x[o] + '--' + res[index].series[u].name + '</span></td>' +
                                        '         <td><span class="td-container">' + res[index].series[u].data[o] + '</span></td>' +
                                        '    </tr>'
                                });
                            });
                            _this.append(table);
                            $('.tbody').append(html);
                        }

                        window.addEventListener("resize", function () {
                            thisDashboardChart.resize();
                        });

                    })
                })
            });
        }

        charts_type();
        dashboardChart.setOption(option, true);

        window.addEventListener("resize", function () {
            dashboardChart.resize();
        });
    });

    $('.dashboard-widget-timerange .timerange').each(function () {
        init_daterangepicker($(this));
    });

    init_daterangepicker($('#dashboard-date-range'));

    function init_daterangepicker(ele) {
        //定义locale汉化插件
        var locale = {

            "separator": " 至 ",
            "applyLabel": "确定",
            "cancelLabel": "取消",
            "fromLabel": "起始时间",
            "toLabel": "结束时间'",
            'startOfWeek': "monday",
            "customRangeLabel": "自定义",
            "weekLabel": "W",
            "daysOfWeek": ["日", "一", "二", "三", "四", "五", "六"],
            "monthNames": ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
            "firstDay": 1
        };
        //日期控件初始化
        ele.daterangepicker(
            {
                'locale': locale,
                "separator": " 至 ",
                //汉化按钮部分
                ranges: {
                    '今日': [moment(), moment()],
                    '昨日': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                    '本周': [moment().startOf('week').add(1, 'days'), moment()],
                    '上周': [moment().subtract(1, 'week').startOf('week').add(1, 'days'), moment().subtract(1, 'week').endOf('week').add(1, 'days')],
                    '本月': [moment().startOf('month'), moment().endOf('month')],
                    '上月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')],
                    '本年': [moment().startOf('year'), moment()],
                    '去年': [moment().subtract(1, 'year').startOf('year'), moment().subtract(1, 'year').endOf('year')],
                    '过去7天': [moment().subtract(6, 'days'), moment()],
                    '过去30天': [moment().subtract(29, 'days'), moment()],
                    '上线至今': [moment().subtract(29, 'days'), moment()]
                },
                startDate: moment().subtract(2, 'hours'),
                endDate: moment(),
                maxDate: moment()
            },
            function (start, end) {

                if (!ele.html()) {
                    ele.val(start.format('YYYY-MM-DD') + ' 至 ' + end.format('YYYY-MM-DD'));
                    $('.dashboard-widget-timerange .timerange').daterangepicker({
                        ranges: {
                            '今日': [moment(), moment()],
                            '昨日': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                            '本周': [moment().startOf('week').add(1, 'days'), moment()],
                            '上周': [moment().subtract(1, 'week').startOf('week').add(1, 'days'), moment().subtract(1, 'week').endOf('week').add(1, 'days')],
                            '本月': [moment().startOf('month'), moment().endOf('month')],
                            '上月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')],
                            '本年': [moment().startOf('year'), moment()],
                            '去年': [moment().subtract(1, 'year').startOf('year'), moment().subtract(1, 'year').endOf('year')],
                            '过去7天': [moment().subtract(6, 'days'), moment()],
                            '过去30天': [moment().subtract(29, 'days'), moment()],
                            '上线至今': [moment().subtract(29, 'days'), moment()]
                        },
                        "separator": " 至 ",
                        startDate: start._d,
                        endDate: end._d
                    });
                    $('.dashboard-widget-timerange .timerange').html(start.format('YYYY-MM-DD') + ' 至 ' + end.format('YYYY-MM-DD'));
                } else {
                    ele.html(start.format('YYYY-MM-DD') + ' 至 ' + end.format('YYYY-MM-DD'));
                }

            },

            ele.val(moment().subtract(2, 'hours').format('YYYY-MM-DD') + ' 至 ' + moment().format('YYYY-MM-DD'))
        ).bind('datepicker-apply', function (event, obj) {
            console.log(obj);
        });

        //初始化显示当前时间
        if (!ele.html()) {
            ele.val(moment().subtract(2, 'hours').format('YYYY-MM-DD') + ' 至 ' + moment().format('YYYY-MM-DD'));
        } else {
            ele.html(moment().subtract(2, 'hours').format('YYYY-MM-DD') + ' 至 ' + moment().format('YYYY-MM-DD'));
        }

        $('.daterangepicker .ranges ul li:nth-child(1)').addClass('active').siblings().removeClass('active');

    }

});