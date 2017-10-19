package coki.utils.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author ThinkGem
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		if(date==null)
			return "";
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到小时字符串 格式（dd）
	 */
	public static String getHour() {
		return formatDate(new Date(), "HH");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getMinute() {
		return formatDate(new Date(), "mm");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}


	/**
	 * 获得中文时间格式
	 * @return
     */
	public static String getCNTime(Date date) {
		return formatDate(date, "MM月dd日HH:mm");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*60*1000);
	}
	
	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*1000);
	}
	
	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }
	
	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}

	public static Date parseDate(String str){
		if(str==null||str.equalsIgnoreCase(""))
			return null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date rt = null;
		try {
			rt = simpleDateFormat.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return  rt;
	}

	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
//		System.out.println(formatDate(parseDate("2010/3/6")));
//		System.out.println(getDate("yyyy年MM月dd日 E"));
//		long time = new Date().getTime()-parseDate("2012-11-19").getTime();
//		System.out.println(time/(24*60*60*1000));
	}

	public static Date GMT2CSTDate(Date GMTDate){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(GMTDate);
		calendar.add(Calendar.HOUR,8);
		return calendar.getTime();
	}

	public static Long GMT2CSTLong(Date GMTDate){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(GMTDate);
		calendar.add(Calendar.HOUR,8);
		return calendar.getTime().getTime();
	}

	/**
	 * utc时间转换为Date时间
	 * @param utcTime
	 * @return
	 */
	public static Date convertToDate(String utcTime) {
		try {

			String timeZone;
			if (!utcTime.endsWith("Z")) {
				//末尾不包含Z,则不需要减去8小时
				utcTime += "Z";
				timeZone = "+0800";
			} else {
				timeZone = "+0000";
			}
			Date date = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")).parse(utcTime.replaceAll("Z$", timeZone));
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}



	/**
	 * Date时间转utc时间
	 *
	 * @param date
	 * @return
	 */
	public static String convertToUtcTime(Date date) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 根据开始时间和结束时间返回时间段内的时间集合
	 *
	 * @param beginDate
	 * @param endDate
	 * @return List
	 */
	public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {
		List<Date> lDate = new ArrayList<Date>();
		lDate.add(endDate);// 把终止加入集合
		Calendar cal = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		cal.setTime(endDate);
		boolean bContinue = true;
		while (bContinue) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			cal.add(Calendar.DAY_OF_MONTH, -1);
			// 测试此日期是否在指定日期之前
			if (beginDate.before(cal.getTime())) {
				lDate.add(cal.getTime());
			} else {
				break;
			}
		}
		lDate.add(beginDate);// 把开始时间加入集合
		return lDate;
	}

	/**
	 * 获取指定时间的 string 型年月日
	 */
	public static String getExactDate(int num){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, num);//0 今天; -1 昨天; +1 明天
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);
		String date = year + "-" + month + "-" + day;
		return date;
	}

	public static String getExactDate(int num, String pattern){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, num);//0 今天; -1 昨天; +1 明天
		Date date = calendar.getTime();
		if (StringUtils.isEmpty(pattern))
			return formatDate(date, "yyyy-MM-dd");
		return formatDate(date, pattern);
	}

    /**
     * 加天
     * @param day
     * @param base
     * @return
     */
    public final static Date addDay(int day, Date base){
        Calendar cal=Calendar.getInstance();
        if(base==null) base = cal.getTime();
        cal.setTime(base);
        cal.add(Calendar.DAY_OF_YEAR, day);
        return cal.getTime();
    }

    /**
     * 获取指本周第一天
     *
     * @return
     */
    public final static Date getFirstDayofWeek() {
        Calendar calendar = Calendar.getInstance();
        Calendar ca = (Calendar) calendar.clone();
        ca.add(Calendar.DATE, 1 - calendar.get(Calendar.DAY_OF_WEEK));
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        return ca.getTime();
    }

    /**
     * 本周最后一天
     *
     * @return
     */
    public final static Date getLastDayofWeek() {
        Calendar calendar = Calendar.getInstance();
        Calendar ca = (Calendar) calendar.clone();
        ca.add(Calendar.DATE, 8 - calendar.get(Calendar.DAY_OF_WEEK));
        ca.set(Calendar.HOUR_OF_DAY, 24);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        return ca.getTime();
    }

    /**
     * 获取本月第一天
     *
     * @return
     */
    public final static Date getFirstDayofMonth() {
        Calendar calendar = Calendar.getInstance();
        Calendar ca = (Calendar) calendar.clone();
        ca.set(Calendar.DAY_OF_MONTH, 1);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        return ca.getTime();
    }

    /**
     * 本月最后一天
     *
     * @return
     */
    public final static Date getLastDayofMonth() {
        Calendar calendar = Calendar.getInstance();
        Calendar ca = (Calendar) calendar.clone();
        ca.add(Calendar.MONTH, 1);
        ca.set(Calendar.DAY_OF_MONTH, 1);
        ca.add(Calendar.DAY_OF_MONTH, -1);
        ca.set(Calendar.HOUR_OF_DAY, 24);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        return ca.getTime();
    }

    /**
     * 下周
     *
     * @return
     */
    public final static Date getNextWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);
        return calendar.getTime();
    }

    /**
     * 一周第一天
     *
     * @param date
     * @return
     */
    public final static Date getFirstDayofWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar ca = (Calendar) calendar.clone();
        ca.add(Calendar.DATE, 1 - calendar.get(Calendar.DAY_OF_WEEK));
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        return ca.getTime();
    }

    /**
     * 一周最后一天
     *
     * @param date
     * @return
     */
    public final static Date getLastDayofWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar ca = (Calendar) calendar.clone();
        ca.add(Calendar.DATE, 8 - calendar.get(Calendar.DAY_OF_WEEK));
        ca.set(Calendar.HOUR_OF_DAY, 24);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        return ca.getTime();
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    public final static Date getFirstDayofYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return Date
     */
    public final static Date getLastDayofYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }


    /**
     * 获取月份的第一天
     *
     * @param month
     * @param year
     * @return
     */
    public final static Date getFirstDayofMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取最后一天
     *
     * @param month
     * @param year
     * @return
     */
    public final static Date getLastDayofMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * this year
     *
     * @return
     */
    public final static int getThisYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public final static int getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }


    /**
     * 获取当前年份
     *
     * @return
     */
    public final static int getCurrentYear() {
        return getThisYear();
    }

    /**
     * 当天凌晨
     *
     * @return
     */
    public final static Date getCurrentMor() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 当天24点
     *
     * @return
     */
    public final static Date getCurrentNig() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取日期的凌晨
     * @param date
     * @return
     */
    public final static Date getMor(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Calendar ca = (Calendar) cal.clone();
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.MILLISECOND, 0);
        return ca.getTime();
    }

    public final static Date getNig(Date date){
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        Calendar cal = (Calendar) ca.clone();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


    /**
     * 获取上半年或是下半年的第一天
     *
     * @return
     */
    public final static Date getSemiyearlyFirstDay(int month, int year) {
        if (month != -1) {
            return getFirstDayofMonth(month, year);
        } else {//当前月份
            int currentMonth = getCurrentMonth();
            int currentYear = getCurrentYear();
            if (currentMonth > 6) {
                return getFirstDayofMonth(7, currentYear);
            } else {
                return getFirstDayofMonth(1, currentYear);
            }
        }
    }

    /**
     *
     * @return
     */
    public  final  static Date getSemiyearlyLastDay(int month, int year){
        if (month != -1) {
            if(month>6){
                return getLastDayofMonth(12, year);
            }else {
                return getLastDayofMonth(6, year);
            }
        } else {//当前月份
            int currentMonth = getCurrentMonth();
            int currentYear = getCurrentYear();
            if (currentMonth > 6) {
                return getLastDayofMonth(12, currentYear);
            } else {
                return getLastDayofMonth(6, currentYear);
            }
        }
    }

    /**
     * 昨天
     * @return
     */
    public final static Date getYesterday(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR,-1);
        return cal.getTime();
    }


    /**
     *获取年份
     * @param date
     * @return
     */
    public final static int getYear(Date date){
        if (date!=null){
            Calendar cal=Calendar.getInstance();
            cal.setTime(date);
            return cal.get(Calendar.YEAR);
        }
        return 0;
    }

    /**
     * 获取月份
     * @param date
     * @return
     */
    public final  static int getMonth(Date date){
        if(date!=null){
            Calendar cal=Calendar.getInstance();
            cal.setTime(date);
            return cal.get(Calendar.MONTH);
        }
        return 1;
    }

    public final static int getWeek(Date date){
        if(date!=null){
            Calendar cal=Calendar.getInstance();
            cal.setTime(date);
            return cal.get(Calendar.DAY_OF_WEEK);
        }

        return 1;
    }


}
