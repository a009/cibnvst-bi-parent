function _formatFull(data){
   if(((String)(data)).length==1){
       return "0"+data;
   }else{
       return data;
   }
}

//获取n天前的时间并格式化
function getNDateTime(dateFormat,n){
	var date = new Date();
	lIntval = n;
	date.setDate(date.getDate() - lIntval);
	var year = date.getFullYear();
	var month = _formatFull(date.getMonth()+1);
	var day = _formatFull(date.getDate());
	var hour = _formatFull(date.getHours());
	var minute = _formatFull(date.getMinutes());
	var second = _formatFull(date.getSeconds());
	if(dateFormat=="yyyy-MM-dd"){
		return year+"-"+month+"-"+day;
	}
	else if(dateFormat=="yyyy-MM-dd HH:mm"){
    	return year+"-"+month+"-"+day+" "+hour+":"+minute;
    }
	else if(dateFormat=="yyyy-MM-dd HH:mm:ss" || dateFormat=="YYYY-MM-DD hh:mm:ss"){
    	return year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
    }
	else if(dateFormat=="yyyyMMdd"){
		return String(year)+String(month)+String(day);
	}else if(dateFormat=="yyyy-MM"){
		return year+"-"+month;
	}
}

//获取某一个指定时间n天前的时间
function getNDateOfSomeDay(baseDay,n){
	baseDay = baseDay.replace(/\-/g, "/");
	var date = new Date(baseDay);
	lIntval = n;
	date.setDate(date.getDate() - lIntval);
	var year = date.getFullYear();
	var month = _formatFull(date.getMonth()+1);
	var day = _formatFull(date.getDate());
	var hour = _formatFull(date.getHours());
	var minute = _formatFull(date.getMinutes());
	var second = _formatFull(date.getSeconds());
	return year+"-"+month+"-"+day;
}

//获取两个时间的时间差
function getDateDiff(startTime, endTime, diffType) {
    startTime = startTime.replace(/\-/g, "/");//将xxxx-xx-xx的时间格式，转换为 xxxx/xx/xx的格式 
    endTime = endTime.replace(/\-/g, "/");
    diffType = diffType.toLowerCase();   //将计算间隔类性字符转换为小写
    var sTime = new Date(startTime);    //开始时间
    var eTime = new Date(endTime);  //结束时间
    var divNum = 1; //作为除数的数字
    switch (diffType) {
        case "second":
            divNum = 1000;
            break;
        case "minute":
            divNum = 1000 * 60;
            break;
        case "hour":
            divNum = 1000 * 3600;
            break;
        case "day":
            divNum = 1000 * 3600 * 24;
            break;
        default:
            break;
    }
    return parseInt((eTime.getTime() - sTime.getTime()) / parseInt(divNum));
}

//将日期从yyyyMMdd变成yyyy-MM-dd
function parseDate(dateStr){
	var pattern = /(\d{4})(\d{2})(\d{2})/;
	return dateStr.replace(pattern, '$1-$2-$3');
}

function printErrorLog(XMLHttpRequest, textStatus, errorThrown){
	alert("获取数据出错！");
	console.log("status=["+XMLHttpRequest.status+"],readyState=["+XMLHttpRequest.readyState+"],textStatus=["+textStatus+"]");
}

function getVideoType(cid){
	var result = "";
	if(cid==1){
		result = "电影";
	}else if(cid==2){
		result = "电视剧";
	}else if(cid==3){
		result = "动漫";
	}else if(cid==4){
		result = "综艺";
	}else if(cid==5){
		result = "纪录片";
	}else if(cid==6){
		result = "V视点";
	}else if(cid==7){
		result = "体育";
	}else if(cid==8){
		result = "少儿";
	}else if(cid==121){
		result = "微电影";
	}else if(cid==122){
		result = "搞笑";
	}else if(cid==124){
		result = "公开课";
	}
	return result;
}

function isNotEmpty(str){
	if(null==str || ""==str){
		return false;
	}
	return true;
}

function getValue(str){
	var value = str;
	if(str == -1){
		value = "全部";
	}else if(str == -2){
		value = "未知/暂无";
	}
	return value;
}

function getProvinceChi(str){
	var provinceChi = "";
	switch(str){
		case "cn-jl": provinceChi = "吉林省";break;
		case "cn-tj": provinceChi = "天津市";break;
		case "cn-ah": provinceChi = "安徽省";break;
		case "cn-sd": provinceChi = "山东省";break;
		case "cn-sx": provinceChi = "山西省";break;
		case "cn-xj": provinceChi = "新疆维吾尔自治区";break;
		case "cn-hb": provinceChi = "河北省";break;
		case "cn-he": provinceChi = "河南省";break;
		case "cn-hn": provinceChi = "湖南省";break;
		case "cn-gs": provinceChi = "甘肃省";break;
		case "cn-fj": provinceChi = "福建省";break;
		case "cn-gz": provinceChi = "贵州省";break;
		case "cn-cq": provinceChi = "重庆市";break;
		case "cn-js": provinceChi = "江苏省";break;
		case "cn-hu": provinceChi = "湖北省";break;
		case "cn-nm": provinceChi = "内蒙古自治区";break;
		case "cn-gx": provinceChi = "广西壮族自治区";break;
		case "cn-hl": provinceChi = "黑龙江省";break;
		case "cn-yn": provinceChi = "云南省";break;
		case "cn-ln": provinceChi = "辽宁省";break;
		case "cn-6668": provinceChi = "香港特别行政区";break;
		case "cn-zj": provinceChi = "浙江省";break;
		case "cn-sh": provinceChi = "上海市";break;
		case "cn-bj": provinceChi = "北京市";break;
		case "cn-gd": provinceChi = "广东省";break;
		case "cn-3681": provinceChi = "澳门特别行政区";break;
		case "cn-xz": provinceChi = "西藏自治区";break;
		case "cn-sa": provinceChi = "陕西省";break;
		case "cn-sc": provinceChi = "四川省";break;
		case "cn-ha": provinceChi = "海南省";break;
		case "cn-nx": provinceChi = "宁夏回族自治区";break;
		default: provinceChi = "未知";
	}
	return provinceChi;
}

function formatNumber(num, precision, separator) {
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
    return NaN;
}

//格式化时间戳为日期格式
function formatDate(longTime, dateFormat){
	if(longTime > 0){
		var date = new Date(longTime);
		var year = date.getFullYear();
		var month = _formatFull(date.getMonth()+1);
		var day = _formatFull(date.getDate());
		var hour = _formatFull(date.getHours());
		var minute = _formatFull(date.getMinutes());
		var second = _formatFull(date.getSeconds());
		if(dateFormat=="yyyy-MM-dd"){
			return year+"-"+month+"-"+day;
		}
		else if(dateFormat=="yyyy-MM-dd HH:mm:ss"){
	    	return year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
	    }
		else if(dateFormat=="yyyyMMdd"){
			return year+month+day;
		}
	}else{
		return "";
	}
}

function toMinute(longTime){
	return Math.round((longTime*100) / 1000 / 60) / 100;
}

function toHour(longTime){
	return Math.round((longTime*100) / 1000 / 60 / 60) / 100;
}

/**
 * 转换成(分:秒)
 * @param longTime 毫秒数
 * @return
 */
function toMMss(longTime){
	var result = "";
	var secondTime = Math.round(longTime / 1000);	//毫秒转换成秒
	var minute = Math.round(secondTime/60)+"";
	if(minute.length == 1){
		result += "0" + minute;
	}else{
		result += minute;
	}
	result += ":";
	var second = secondTime % 60+"";
	if(second.length == 1){
		result += "0" + second;
	}else{
		result += second;
	}
	return result;
}

function toDateMessage(longTime){
	var result = "";
	if(longTime >= 31536000000){
		result += Math.round(longTime/31536000000) + "年";
		longTime = longTime % 31536000000;
	}
	if(longTime >= 86400000){
		result += Math.round(longTime/86400000) + "天";
		longTime = longTime % 86400000;
	}
	if(longTime >= 3600000){
		result += Math.round(longTime/3600000) + "小时";
		longTime = longTime % 3600000;
	}
	if(longTime >= 60000){
		result += Math.round(longTime/60000) + "分钟";
		longTime = longTime % 60000;
	}
	if(longTime >= 1000){
		result += Math.round(longTime/1000) + "秒";
		longTime = longTime % 1000;
	}
	result += longTime + "毫秒";
	return result;
}

/**
 * 获取日期，周末带上星期
 * @param date
 * @return
 */
function getDateWeek(dateStr, format){
	var date = new Date();
	if(date != null){
		var year;
		var month;
		var day;
		if(format == "yyyy-MM-dd"){
			var strArr = dateStr.split("-");
			year = strArr[0];
			month = strArr[1];
			day = strArr[2];
		}else if(format == "yyyyMMdd"){
			year = dateStr.substring(0,4);
			month = dateStr.substring(4,6);
			day = dateStr.substring(6,8);
		}
		date = new Date(year, month-1, day);
	}
	if(date.getDay() == 0){
		return dateStr + " <span style='color:green;'>周日</span>";
	}else if(date.getDay() == 6){
		return dateStr + " <span style='color:green;'>周六</span>";
	}else{
		return dateStr;
	}
}

/**
 * 根据包名key获取value
 * @param str
 * @return
 */
function getPkgName(str){
	var result = "";
	switch(str){
	case "net.myvst.v2":
		result = "CIBN微视听";
		break;
	case "com.vst.itv52.v1":
		result = "全聚合桌面版";
		break;
	case "com.vst.live":
		result = "小薇直播";
		break;
	case "com.love.tuidan":
		result = "推单";
		break;
	case "com.vst.box.launcher":
		result = "vst盒子launcher";
		break;
	default:
		result = str;
	}
	return result;
}

/**
 * 根据key获取value
 * @param map
 * @param key
 * @return
 */
function getMapValue(map, key, defaul){
	map = map.substring(1,map.length-1);
	var arr = map.split(", ");
	for(var i=0; i<arr.length; i++){
		var code = arr[i];
		var temp = code.split("=");
		if(temp[0] == key){
			return temp[1];
		}
	}
	if(defaul != null){
		return defaul;
	}
	return key;
}

/**
 * 格式化字符串
 * @param str 原字符串
 * @param subLength 需要展示的最大长度
 * @return
 */
function formatString(str, subLength){
	var result = toString(str);
	if(str != null){
		if(subLength == null){
			subLength = 20;
		}
		if(str.length > subLength){
			result = str.substring(0,subLength) + "...";
		}
	}
	return result;
}

function toString(str){
	if(typeof(str)=="undefined"){
		return "";
	}
	return str;
}

/**
 * 参数格式化
 * 功能：①=开头，做等于判断；②!=开头，做不等于判断；③否则，默认
 * @param key
 * @param value
 * @return
 */
function paramFormat(key, value){
	var result = "";
	if(value != ''){
		if(value.indexOf('=') == 0){
			result += key + "_eq=" + value.substr(1);
		}else if(value.indexOf('!=') == 0){
			result += key + "_ne=" + value.substr(2);
		}else{
			result += key + "=" + value;
		}
	}
	return result;
}