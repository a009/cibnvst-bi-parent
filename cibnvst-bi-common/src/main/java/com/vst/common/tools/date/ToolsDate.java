package com.vst.common.tools.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.vst.common.tools.number.ToolsNumber;
import com.vst.common.tools.string.ToolsString;

/**
 * @author weiwei
 * @date 2015-1-6 下午01:20:44
 * @description 日期工具类
 */
public class ToolsDate {

	/**
	 * 日期格式：年月日时分秒
	 */
	public static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	public static String yyyy_MM_dd_HH_mm_ss2 = "yyyy/MM/dd HH:mm:ss";
	public static String yyyy_MM_dd_HH_mm_ss3 = "yyyyMMddHHmmss";
	
	/**
	 * 日期格式：年月日十分
	 */
	public static String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
	public static String yyyy_MM_dd_HH_mm2 = "yyyy/MM/dd HH:mm";
	public static String yyyy_MM_dd_HH_mm3 = "yyyyMMdd/HH/mm";
	public static String yyyy_MM_dd_HH_mm4 = "yyyyMMdd HH:mm";
	
	/**
	 * 日期格式：年月日
	 */
	public static String yyyy_MM_dd = "yyyy-MM-dd";
	public static String yyyy_MM_dd2 = "yyyy/MM/dd"; 
	public static String yyyy_MM_dd3 = "yyyy.MM.dd"; 
	public static String yyyy_MM_dd4 = "yyyyMMdd"; 
	
	/**
	 * 日期格式：年月
	 */
	public static String yyyy_MM = "yyyy-MM";
	
	/**
	 * 日期格式：月日
	 */
	public static String MM_dd = "MM月dd日"; 
	
	/**
	 * 日期格式：月日
	 */
	public static String MM_dd2 = "MMdd"; 
	
	/**
	 * 日期格式：时分秒
	 */
	public static String HH_mm_ss = "HH:mm:ss";
	
	/**
	 * 日期格式：时分
	 */
	public static String HH_mm = "HH:mm"; 
	
	
	/**
	 * 日期格式：年
	 */
	public static String yyyy = "yyyy";
	
	/**
	 * 日期格式：时
	 */
	public static String HH = "HH";
	
	/**
	 * 日期格式：分
	 */
	public static String mm = "mm";
	
	/**
	 * 时间戳转换成对应的时间格式
	 * @param parrten
	 * @param time
	 * @return
	 */
	public static String formatDate(String parrten, Long time){
		// 返回结果
		String result = null;
		
		if(time != null && time != 0 && time != -1){
			// 如果小于13位
			if(time.toString().length() == 10) time = Long.valueOf(time.longValue() * 1000L);
			
			SimpleDateFormat sdf = new SimpleDateFormat(parrten);
			TimeZone zone = TimeZone.getTimeZone("Asia/Shanghai");
			sdf.setTimeZone(zone);
			
			result = sdf.format(new Date(time.longValue()));
		}
		
		return result;
	}
	
	/**
	 * 时间戳转换成对应的时间格式
	 * @param parrten
	 * @param time
	 * @return
	 */
	public static String formatDate(String parrten, String time){
		// 返回结果
		String result = "";
		
		if(!ToolsString.isEmpty(time)){
			long date = Long.parseLong(time);
			
			// 如果小于13位
			if(time.length() == 10) date = date * 1000;
			
			SimpleDateFormat sdf = new SimpleDateFormat(parrten);
			TimeZone zone = TimeZone.getTimeZone("Asia/Shanghai");
			sdf.setTimeZone(zone);
			
			result = sdf.format(new Date(date));
		}
		
		return result;
	}
	
	/**
	 * 获取距离当前时间distince个毫秒数的日期
	 * @param distince
	 * @return
	 */
	public static Date getCurrDate(long distince){
		return new Date(System.currentTimeMillis() - distince);
	}
	
	/**
	 * 获取当天开始时间
	 * @return
	 */
	public static String getCurrStartDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		TimeZone zone = TimeZone.getTimeZone("Asia/Shanghai");
		sdf.setTimeZone(zone);
		return sdf.format(new Date());
	}
	
	/**
	 * 获取当天结束时间
	 * @return
	 */
	public static String getCurrEndDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		TimeZone zone = TimeZone.getTimeZone("Asia/Shanghai");
		sdf.setTimeZone(zone);
		return sdf.format(new Date());
	}
	
	/**
	 * 获取从当天开始，days天后的时间，包括当天；格式默认：yyyy-MM-dd 23:59:59
	 * 例如：今天是2015-01-21，我想获取三天后的时间，days=3
	 * @param days
	 * @return
	 */
	public static String getDaysDate(int days){
		return getDaysDate(days, "yyyy-MM-dd 23:59:59");
	}
	
	/**
	 * 获取从当天开始，days天后的时间，包括当天；自定义格式
	 * 例如：今天是2015-01-21，我想获取三天后的时间，days=3
	 * @param days
	 * @return
	 */
	public static String getDaysDate(int days, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		TimeZone zone = TimeZone.getTimeZone("Asia/Shanghai");
		sdf.setTimeZone(zone);
		return sdf.format(Long.valueOf(System.currentTimeMillis() + (days - 1) * 24 * 60 * 60 * 1000L));
	}
	
	/**
	 * 根据日期获取星期（目前只支持yyyy-MM-dd格式）
	 * @param date
	 * @return
	 */
	public static String getWeekByDate(String date){
		return getWeekByDate(date, "yyyy-MM-dd");
	}
	
	/***
	 * 根据日期获取星期
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getWeekByDate(String date, String format){
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(date));
			
			int day = c.get(Calendar.DAY_OF_WEEK);
			switch (day) {
			case 1:
				result = "周日";
				break;
			case 2:
				result = "周一";
				break;
			case 3:
				result = "周二";
				break;
			case 4:
				result = "周三";
				break;
			case 5:
				result = "周四";
				break;
			case 6:
				result = "周五";
				break;
			case 7:
				result = "周六";
				break;
			default:
				break;
			}
			
		} catch (ParseException e) {
		}
		return result;
	}
	
	/**
	 * 根据日期获取星期（目前只支持yyyy-MM-dd格式）
	 * @param date
	 * @return
	 */
	public static int getDayOfWeek(){
		TimeZone zone = TimeZone.getTimeZone("Asia/Shanghai");
		Calendar c = Calendar.getInstance(zone);
		return c.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * 解析时间
	 * @param parrten
	 * @param time
	 * @return
	 */
	public static Date parseDate(String parrten, String time){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(parrten);
			TimeZone zone = TimeZone.getTimeZone("Asia/Shanghai");
			sdf.setTimeZone(zone);
			return sdf.parse(time);
		} catch (ParseException e) {
		}
		return null;
	}
	
	/**
	 * 获取当前小时
	 * @return
	 */
	public static int getCurrHour(){
		// 设定时区
		TimeZone zone = TimeZone.getTimeZone("Asia/Shanghai");
		return Calendar.getInstance(zone).get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 获取当天的开始时间，毫秒数
	 * @return
	 */
	public static long getCurrStartTime(){
		return getCurrStartTime(System.currentTimeMillis());
	}
	
	/**
	 * 获取当天的开始时间，毫秒数
	 * @return
	 */
	public static long getCurrStartTime(long time){
		// 设定时区
		TimeZone zone = TimeZone.getTimeZone("Asia/Shanghai");
		Calendar c = Calendar.getInstance(zone);
		c.setTimeInMillis(time);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTimeInMillis();
	}
	
	/**
	 * 获取当天的结束时间，毫秒数
	 * @return
	 */
	public static long getCurrEndTime(){
		return getCurrEndTime(System.currentTimeMillis());
	}
	
	/**
	 * 获取当天的结束时间，毫秒数
	 * @return
	 */
	public static long getCurrEndTime(long time){
		// 设定时区
		TimeZone zone = TimeZone.getTimeZone("Asia/Shanghai");
		Calendar c = Calendar.getInstance(zone);
		c.setTimeInMillis(time);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return c.getTimeInMillis();
	}
	
	/**
	 * 获取当天时间 yyyyMMdd
	 * @return
	 */
	public static String getCurrDate(){
		return getCurrDate("yyyy-MM-dd");
	}
	
	/**
	 * 获取当天时间 指定格式
	 * @param format
	 * @return
	 */
	public static String getCurrDate(String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		TimeZone zone = TimeZone.getTimeZone("Asia/Shanghai");
		sdf.setTimeZone(zone);
		return sdf.format(new Date());
	}
	
	/**
	 * 将时间格式转换成时间戳格式
	 * 
	 * @param parrten
	 * @param time
	 * @return
	 */
	public static long toUnixTimestamp(String parrten, String time) {
		SimpleDateFormat sdf = new SimpleDateFormat(parrten);
		TimeZone zone = TimeZone.getTimeZone("Asia/Shanghai");
		sdf.setTimeZone(zone);
		Date date = null;
		try {
			date = sdf.parse(time);
			return date.getTime();
		} catch (ParseException e) {
		}
		return 0L;
	}

    /**
     * 格式化字符串时间，返回Timestamp
     * @param sdf
     * @param time
     * @return
     */
    public static Timestamp formatDate(SimpleDateFormat sdf, String time) {
        Timestamp result = null;
        TimeZone zone = TimeZone.getTimeZone("Asia/Shanghai");
        sdf.setTimeZone(zone);
        try {
            result = new Timestamp(sdf.parse(time).getTime());
        } catch (ParseException e) {
        }
        return result;
    }

    /**
     * 格式化字符串时间，返回String
     *
     * @param sdf
     * @param time
     * @return
     */
    public static String formatDate(SimpleDateFormat sdf, long time) {
        String result = null;

        TimeZone zone = TimeZone.getTimeZone("Asia/Shanghai");
        sdf.setTimeZone(zone);

        try {
            result = sdf.format(new Date(time));
        } catch (Exception e) {
        }

        return result;
    }

    /**
     * 获取当前年份
     *
     * @return
     */
    public static int getCurrYear() {
        TimeZone zone = TimeZone.getTimeZone("Asia/Shanghai");
        Calendar c = Calendar.getInstance(zone);
        return c.get(Calendar.YEAR);
    }


    public static int getBeforeDaysDate() {
        return getBeforeDaysDate(1);
    }

    /**
     * 从昨天开始算，获取七天前的时间
     *
     * @return 返回int型日期yyyMMdd
     */
    public static int getBeforeDaysDate(int limit) {
        TimeZone zone = TimeZone.getTimeZone("Asia/Shanghai");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.add(Calendar.DAY_OF_MONTH, -(limit * 8));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        sdf.setTimeZone(zone);
        return ToolsNumber.parseInt(sdf.format(c.getTime()));
    }

    /**
     * 获取数个月前或月后
     *
     * @param limit 正数表示xx个月后，负数表示xx个月前
     * @return 返回int型日期yyyMMdd
     */
    public static int getMonthsAgoOrLater(int limit) {
        TimeZone zone = TimeZone.getTimeZone("Asia/Shanghai");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.add(Calendar.MONTH, limit);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        sdf.setTimeZone(zone);
        return ToolsNumber.parseInt(sdf.format(c.getTime()));
    }

    /**
     * 格式化秒
     *
     * @param seconds
     * @return
     */
    public static String formatTime(int seconds) {
        if (seconds <= 0) {
            return "";
        }
        int hours = seconds / 60 / 60;
        int minutes = (seconds - hours * 60 * 60) / 60;
        String hoursStr = "";
        String minutesStr = "";
        String secondsStr = "";
        if (hours > 0) {
            hoursStr = hours < 10 ? ("0" + hours) : String.valueOf(hours);
        } else {
            hoursStr = "00";
        }

        if (minutes > 0) {
            minutesStr = minutes < 10 ? ("0" + minutes) : String.valueOf(minutes);
        } else {
            minutesStr = "00";
        }

        seconds = seconds - hours * 60 * 60 - minutes * 60;
        if (seconds > 0) {
            secondsStr = seconds < 10 ? ("0" + seconds) : String.valueOf(seconds);
        } else {
            secondsStr = "00";
        }
        if ("00".equals(hoursStr)) {
            return minutesStr + ":" + secondsStr;
        }
        return hoursStr + ":" + minutesStr + ":" + secondsStr;
    }
}
