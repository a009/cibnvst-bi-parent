$(function () {
    $.extend({
        /**
         * 日期控件初始化，定义locale汉化插件
         * @param option（参数）
         *
         *调用的两种方式：1、option = “今天”，2、option={ 键:值}
         *参数：name（快捷方式的名称），startTime、endTime（数据必须是一对的起止时间戳），oneDay（一天的时间戳），haveTime（支不支持HH:mm:ss部分，默认没有）
         *
         * 例如： $.initDaterangepicker('今天');
         *
         * $.initDaterangepicker({
            startTime:1514736000000, //2018-01-01
			endTime:1533052800000    //2018-01-08
		    });
         *
         * update:2018-06-20
         *
         * @returns {boolean}
         */
        "initDaterangepicker":function (option) {
            function isString(source) {
                return '[object String]' == Object.prototype.toString.call(source);
            };
            function isObject(source) {
                return 'function' == typeof source || !!(source && 'object' == typeof source);
            };
            function isNumber(source) {
                return '[object Number]' == Object.prototype.toString.call(source) && isFinite(source);
            };

            var isString = isString(option);
            var isObject = isObject(option);
            var $option = {
                name:'',//默认今天
                startTime:'',//开始时间的时间戳
                endTime:'',//结束时间的时间戳
                oneDay:new Date().getTime(),//只选一天时间
                haveTime:false,//有没有HH:mm:ss部分，默认没有
            }

            if(isString){
                $option.name = option;
            }else if(isObject){
                $option.name = option.name || '';
                $option.startTime = option.startTime || '';
                $option.endTime = option.endTime || '';
                $option.oneDay = option.oneDay || new Date().getTime();
                $option.haveTime = option.haveTime || false;
            }else{
                return false;
            }
            var ele=$("#daterangepickerInputDate");
            if(!(ele.length && ele.length>0)){
                return false;
            }
            var haveTimeParam={
                timePicker:false,
                timePicker12Hour:true,
                timePickerSeconds:false,
                format: "YYYY-MM-DD"
            }
            if($option.haveTime===true){
                haveTimeParam={
                    timePicker:true,
                    timePicker12Hour:false,
                    timePickerSeconds:true,
                    format: "YYYY-MM-DD HH:mm:ss"
                }
            }
            var config = {
                'locale': {
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
                },
                "separator": " 至 ",
                //汉化按钮部分
                ranges: {
                    '今日': [moment().hours(0).minutes(0).seconds(0), moment()],
                    '昨日': [moment().subtract(1, 'days').hours(0).minutes(0).seconds(0), moment().subtract(1, 'days').hours(23).minutes(59).seconds(59)],
                    '本周': [moment().startOf('isoWeek').hours(0).minutes(0).seconds(0), moment().endOf('isoWeek')],
                    '上周': [moment().subtract(1, 'week').startOf('isoWeek'), moment().subtract(1, 'week').endOf('isoWeek')],
                    '本月': [moment().startOf('month').hours(0).minutes(0).seconds(0), moment()],
                    '上月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')],
                    '本年': [moment().startOf('year').hours(0).minutes(0).seconds(0), moment()],
                    '去年': [moment().subtract(1, 'year').startOf('year'), moment().subtract(1, 'year').endOf('year')],
                    '过去7天': [moment().subtract(6, 'days').hours(0).minutes(0).seconds(0), moment()],
                    '过去30天': [moment().subtract(29, 'days').hours(0).minutes(0).seconds(0), moment()],
                    '上线至今': [moment('2017-11-01'), moment()]
                },
                startDate:  moment().subtract(1, 'hours').hours(0).minutes(0).seconds(0),
                endDate:  moment(),
                maxDate: moment(),
                alwaysShowCalendars: true,
                timePicker: haveTimeParam.timePicker,
                timePicker12Hour: haveTimeParam.timePicker12Hour,
                timePickerSeconds: haveTimeParam.timePickerSeconds,
                format: haveTimeParam.format
            };
            if($option.name.length>0 && config.ranges[$option.name]){
                //如果是config.ranges的快捷参数的名称，优先级1
                config.startDate = config.ranges[$option.name][0];
                config.endDate = config.ranges[$option.name][1];
            }else if(isNumber($option.startTime) && $option.startTime>0 && isNumber($option.endTime) && $option.endTime>0){
                //如果是起始、结束两个时间段的时间戳，优先级2
                config.startDate = moment($option.startTime).hours(0).minutes(0).seconds(0);
                config.endDate = moment($option.endTime).hours(23).minutes(59).seconds(59);
            }else if(isNumber($option.oneDay) && $option.oneDay>0){
                //一个时间的时间戳，优先级3，如果前面两项都没有参数传过来，则使用这个时间戳
                config.startDate = moment($option.oneDay).hours(0).minutes(0).seconds(0);
                config.endDate = moment($option.oneDay).hours(23).minutes(59).seconds(59);
            }

            ele.daterangepicker(
                config,
                function (start, end) {
                    if($option.haveTime===true){
                        if (!ele.html()) {
                            ele.val(start.format('YYYY-MM-DD HH:mm:ss') + ' 至 ' + end.format('YYYY-MM-DD HH:mm:ss'));
                        } else {
                            ele.html('<div style="float: left;width: 60px;line-height: 14px;font-size: 12px;margin-left: 6px;">'+start.format('YYYY-MM-DD HH : mm : ss') +'</div>'+ '<div style="float: left;width: 20px;">'+' 至 '+'</div>' + '<div style="float: left;width: 60px;line-height: 14px;font-size: 12px;">'+end.format('YYYY-MM-DD HH : mm : ss')+'</div>');
                        }
                        $('#startDay').val(start.format('YYYY-MM-DD HH:mm:ss'));
                        $('#endDay').val(end.format('YYYY-MM-DD HH:mm:ss'));
                    }else{
                        if (!ele.html()) {
                            ele.val(start.format('YYYY-MM-DD') + ' 至 ' + end.format('YYYY-MM-DD'));
                        } else {
                            ele.html(start.format('YYYY-MM-DD') + ' 至 ' + end.format('YYYY-MM-DD'));
                        }
                        $('#startDay').val(start.format('YYYY-MM-DD'));
                        $('#endDay').val(end.format('YYYY-MM-DD'));
                    }

                },
                ele.val(moment().subtract(2, 'hours').format('YYYY-MM-DD') + ' 至 ' + moment().format('YYYY-MM-DD'))
            ).bind('datepicker-apply', function (event, obj) {
                console.log(obj);
            });
            //初始化显示当前时间
            var dateStringStart = (config.startDate && config.startDate.format('YYYY-MM-DD')) || moment().subtract(2, 'hours').format('YYYY-MM-DD');
            var dateStringEnd = (config.endDate && config.endDate.format('YYYY-MM-DD')) || moment().format('YYYY-MM-DD');
            if($option.haveTime===true){
                dateStringStart = (config.startDate && config.startDate.format('YYYY-MM-DD HH : mm : ss')) || moment().subtract(2, 'hours').format('YYYY-MM-DD HH : mm : ss');
                dateStringEnd = (config.endDate && config.endDate.format('YYYY-MM-DD HH : mm : ss')) || moment().format('YYYY-MM-DD HH : mm : ss');
            }
            if($option.haveTime===true){
                ele.html('<div style="float: left;width: 60px;line-height: 14px;font-size: 12px;margin-left: 6px;">'+dateStringStart +'</div>'+ '<div style="float: left;width: 20px;">'+' 至 '+'</div>' + '<div style="float: left;width: 60px;line-height: 14px;font-size: 12px;">'+dateStringEnd+'</div>');
            }else{
                var dateString = dateStringStart + ' 至 ' + dateStringEnd;
                if (!ele.html()) {
                    ele.val(dateString);
                } else {
                    ele.html(dateString);
                }
            }
            $('#startDay').val(dateStringStart);
            $('#endDay').val(dateStringEnd);
            var activrFlag=false;
            $('.daterangepicker .ranges ul li').each(function () {
                var that = $(this);
                if(that.text()===$option.name){
                    that.addClass('active');
                    activrFlag = true;
                }
            })
            if(!activrFlag){
                $('.daterangepicker .ranges ul li:nth-child(1)').addClass('active');
            }
        }
    });
    $.initDaterangepicker('今天');
});