package com.chyjr.platform.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @ProjectName:  [PlatformCommon]
 * @Package:      [com.chyjr.platform.common.util.DateUtils.java] 
 * @ClassName:    [DateUtils]  
 * @Description:  [日期格式化类]  
 * @Author:       [wangxiaohua]  
 * @CreateDate:   [Oct 14, 2014 4:56:37 PM]  
 * @UpdateUser:   [wangxiaohua]  
 * @UpdateDate:   [Oct 14, 2014 4:56:37 PM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public class DateUtils {
    private static final String HH_PATTERN = "HH:mm";
    private static final String MM_PATTERN = "MM-dd HH:mm";
    private static final String MMDD_PATTERN = "MM-dd";
    private static final String MMDDCHINA_PATTERN = "MM月dd日";
    private static final String DEFAULT_PATTERN = "yyyy-MM-dd";
    private static final String ALL_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String ALL_PATTERN_S = "MM-dd HH:mm";

    private static final String WEEK_NAME = "星期";
    //
    private static final String[] weeks = {"日", "一", "二", "三", "四", "五", "六"};

    public DateUtils() {
        super();
    }

    public static Date newDate() {
        return newDate(System.currentTimeMillis());
    }

    public static Date newDate(Long time) {
        return new Date(time);
    }

    public static Date newDate(Date date) {
        return new Date(date.getTime());
    }

    public static Date newDate(int year, int month, int date) {
        return newDate(year, month, date, 0, 0, 0);
    }

    public static Date newDate(int year, int month, int date, int hour,
                               int minute, int second) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, date);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);
        return c.getTime();
    }

    public static String getWeekCh(Date d) {
        int week = getDay(d) - 1;
        if (week >= 0 && week <= 6) {
            return WEEK_NAME + weeks[week];
        } else return null;
    }

    public static String getWeek() {
        int week = getDay(newDate(System.currentTimeMillis())) - 1;
        if (week >= 0 && week <= 6) {
            return WEEK_NAME + weeks[week];
        } else
            return null;
    }

    public static int get(Date d, int field) {
        if (d == null) {
            return -1;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(field);
    }

    public static int getYear(Date d) {
        return get(d, Calendar.YEAR);
    }

    public static int getMonth(Date d) {
        return get(d, Calendar.MONTH);
    }

    public static int getDate(Date d) {
        return get(d, Calendar.DAY_OF_MONTH);
    }

    public static int getDay(Date d) {
        return get(d, Calendar.DAY_OF_WEEK);
    }

    public static int getHour(Date d) {
        return get(d, Calendar.HOUR_OF_DAY);
    }

    public static int getMinute(Date d) {
        return get(d, Calendar.MINUTE);
    }

    public static int getSecond(Date d) {
        return get(d, Calendar.SECOND);
    }

    public static void set(Date d, int field, int value) {
        if (d == null) {
            return;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(field, value);
        d.setTime(c.getTimeInMillis());
    }

    public static void setYear(Date d, int value) {
        set(d, Calendar.YEAR, value);
    }

    public static void setMonth(Date d, int value) {
        set(d, Calendar.MONTH, value);
    }

    public static void setDate(Date d, int value) {
        set(d, Calendar.DAY_OF_MONTH, value);
    }

    public static void setHour(Date d, int value) {
        set(d, Calendar.HOUR_OF_DAY, value);
    }

    public static void setMinute(Date d, int value) {
        set(d, Calendar.MINUTE, value);
    }

    public static void setSecond(Date d, int value) {
        set(d, Calendar.SECOND, value);
    }


    public static String format() {
        return format(DEFAULT_PATTERN);
    }

    public static String format(Date date) {
        return format(date, DEFAULT_PATTERN);
    }

    public static String format(String pattern) {
        return format(newDate(), pattern);
    }

    public static String format(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(pattern).format(date);
    }

    public static Date parse(String date) throws ParseException {
        return parse(date, DEFAULT_PATTERN);
    }

    public static Date parse(String date, String pattern) throws ParseException {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(pattern).parse(date);
    }

    public static Date parseNullIfException(String date) {
        return parseNullIfException(date, DEFAULT_PATTERN);
    }

    public static Date parseNullIfException(String date, String pattern) {
        if (date == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException ex) {
            return null;
        }
    }

    private static final int NOT_NULL = 1000;

    private static int nullCompare(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return (d1 == null) ? (d2 == null ? 0 : -1) : 1;
        }
        return NOT_NULL;
    }

    public static int compare(Date d1, Date d2) {
        int c = nullCompare(d1, d2);
        if (c != NOT_NULL) {
            return c;
        }
        long t1 = d1.getTime();
        long t2 = d2.getTime();
        return (t1 > t2) ? 1 : ((t1 == t2) ? 0 : -1);
    }


    public static boolean before(Date d1, Date d2) {
        return compare(d1, d2) < 0;
    }

    public static boolean beforeYear(Date d1, Date d2) {
        return getYear(d1) < getYear(d2);
    }

    public static boolean beforeMonth(Date d1, Date d2) {
        return beforeYear(d1, d2) ? true :
                (equalYear(d1, d2) ? getMonth(d1) < getMonth(d2) : false);
    }

    public static boolean beforeDate(Date d1, Date d2) {
        return beforeMonth(d1, d2) ? true :
                (equalMonth(d1, d2) ? getDate(d1) < getDate(d2) : false);
    }

    public static boolean beforeHour(Date d1, Date d2) {
        return beforeDate(d1, d2) ? true :
                (equalDate(d1, d2) ? getHour(d1) < getHour(d1) : false);
    }

    public static boolean beforeMinute(Date d1, Date d2) {
        return beforeHour(d1, d2) ? true :
                (equalHour(d1, d2) ? getMinute(d1) < getMinute(d1) : false);
    }

    public static boolean beforeSecond(Date d1, Date d2) {
        return beforeMinute(d1, d2) ? true :
                (equalHour(d1, d2) ? getSecond(d1) < getSecond(d2) : false);
    }

    public static boolean after(Date d1, Date d2) {
        return compare(d1, d2) > 0;
    }

    public static boolean afterYear(Date d1, Date d2) {
        return getYear(d1) > getYear(d2);
    }

    public static boolean afterMonth(Date d1, Date d2) {
        return afterYear(d1, d2) ? true :
                (equalYear(d1, d2) ? getMonth(d1) > getMonth(d2) : false);
    }

    public static boolean afterDate(Date d1, Date d2) {
        return afterMonth(d1, d2) ? true :
                (equalMonth(d1, d2) ? getDate(d1) > getDate(d2) : false);
    }

    public static boolean afterHour(Date d1, Date d2) {
        return afterDate(d1, d2) ? true :
                (equalDate(d1, d2) ? getHour(d1) > getHour(d2) : false);
    }

    public static boolean afterMinute(Date d1, Date d2) {
        return afterHour(d1, d2) ? true :
                (equalHour(d1, d2) ? getMinute(d1) > getMinute(d2) : false);
    }

    public static boolean afterSecond(Date d1, Date d2) {
        return afterMinute(d1, d2) ? true :
                (equalMinute(d1, d2) ? getSecond(d1) > getSecond(d2) : false);
    }


    public static Date firstOfMonth(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    public static Date lastOfMonth(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int d = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, d);
        return c.getTime();
    }

    public static Date move(Date date, int field, int inc) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int fieldValue = c.get(field);
        fieldValue += inc;
        c.set(field, fieldValue);
        date.setTime(c.getTimeInMillis());
        return date;
    }

    public static Date next(Date date, int field) {
        return move(date, field, 1);
    }

    public static Date previous(Date date, int field) {
        return move(date, field, -1);
    }

    public static Date nextDate(Date date) {
        return next(date, Calendar.DAY_OF_MONTH);
    }

    public static Date nextMonth(Date date) {
        return next(date, Calendar.MONTH);
    }

    public static Date nextYear(Date date) {
        return next(date, Calendar.YEAR);
    }

    public static Date prevDate(Date date) {
        return previous(date, Calendar.DAY_OF_MONTH);
    }

    public static Date prevMonth(Date date) {
        return previous(date, Calendar.MONTH);
    }

    public static Date prevYear(Date date) {
        return previous(date, Calendar.YEAR);
    }

    public static int marginYear(Date d1, Date d2) {
        int y1 = getYear(d1);
        int y2 = getYear(d2);
        return y1 - y2;
    }

    public static int marginMonth(Date d1, Date d2) {
        int y1 = getYear(d1);
        int y2 = getYear(d2);
        int m1 = getMonth(d1);
        int m2 = getMonth(d2);

        return (y1 - y2) * 12 + m1 - m2;
    }

    public static int marginDate(Date date1, Date date2) {
        long t1 = newDate(getYear(date1), getMonth(date1), getDate(date1),
                0, 0, 0).getTime();

        long t2 = newDate(getYear(date2), getMonth(date2), getDate(date2),
                0, 0, 0).getTime();

        Date min = new Date(Math.min(t1, t2));
        Date max = new Date(Math.max(t1, t2));
        int offset = min.getTime() == t1 ? -1 : 1;
        int dates = 0;
        while (min.getTime() < max.getTime()) {
            setDate(min, getDate(min) + 1);
            dates++;
        }
        return dates * offset;
    }

    public static int marginHour(Date d1, Date d2) {
        int days = marginDate(d1, d2);
        int hour1 = getHour(d1);
        int hour2 = getHour(d2);
        return days * 24 + (hour1 - hour2);
    }

    public static int marginMinute(Date d1, Date d2) {
        int hours = marginHour(d1, d2);
        int minute1 = getMinute(d1);
        int minute2 = getMinute(d2);
        return hours * 60 + (minute1 - minute2);
    }

    public static int marginSecond(Date d1, Date d2) {
        int minutes = marginMinute(d1, d2);
        int second1 = getSecond(d1);
        int second2 = getSecond(d2);

        return minutes * 60 + (second1 - second2);
    }

    public static boolean equalYear(Date d1, Date d2) {
        return getYear(d1) == getYear(d2);
    }

    public static boolean equalMonth(Date d1, Date d2) {
        return equalYear(d1, d2) && getMonth(d1) == getMonth(d2);
    }

    public static boolean equalDate(Date d1, Date d2) {
        return equalMonth(d1, d2) && getDate(d1) == getDate(d2);
    }

    public static boolean equalHour(Date d1, Date d2) {
        return equalDate(d1, d2) && getHour(d1) == getHour(d2);
    }

    public static boolean equalMinute(Date d1, Date d2) {
        return equalHour(d1, d2) && getMinute(d1) == getMinute(d2);
    }

    public static boolean equalSecond(Date d1, Date d2) {
        return equalMinute(d1, d2) && getSecond(d1) == getSecond(d2);
    }

    public static boolean equalTime(Date d1, Date d2) {
        int c = nullCompare(d1, d1);
        if (c != NOT_NULL) {
            return c == 0;
        }
        return d1.getTime() == d1.getTime() || d1.equals(d2);
    }

    public static String simpleDateFormat(String format, Date date) {
        return new SimpleDateFormat(format).format(date).toString();
    }

    /**
     * 返回昨天、前天
     *
     * @param dat String
     * @return String
     */
    public static String formatday(String dat) {
        Date date = null;
        try {
            date = new SimpleDateFormat(ALL_PATTERN).parse(dat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date sdate = date;
        Date data = parseNullIfException(simpleDateFormat(DEFAULT_PATTERN, date));
        Date today = parseNullIfException(simpleDateFormat(DEFAULT_PATTERN, new Date()));
        if (data.toString().equals(today.toString())) {
            return simpleDateFormat(HH_PATTERN, date);
        }
        if (afterYear(today, data)) {
            return simpleDateFormat(DEFAULT_PATTERN, date);
        }
        DateUtils.nextDate(data);
        if (data.toString().equals(today.toString())) {
            return "昨天: " + simpleDateFormat(HH_PATTERN, date);
        }
        DateUtils.nextDate(data);
        if (data.toString().equals(today.toString())) {
            return "前天: " + simpleDateFormat(HH_PATTERN, date);
        }
        return simpleDateFormat(MMDDCHINA_PATTERN, sdate);
    }

    /**
     * 今天返回xx分钟xx小时前 昨天前天
     *
     * @param dat String
     * @return String
     */
    public static String formatday4(String dat) {
        if (dat == null || dat.equals("")) {
            return "";
        }
        Date date = null;
        try {
            date = new SimpleDateFormat(ALL_PATTERN).parse(dat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date sdate = date;
        Date data = parseNullIfException(simpleDateFormat(DEFAULT_PATTERN, date));
        Date today = parseNullIfException(simpleDateFormat(DEFAULT_PATTERN, new Date()));
        if (data.toString().equals(today.toString())) {
            String str = "";
            long time = 1;
            long ms = new Date().getTime() - sdate.getTime();
            long m = ms / 1000;
            if (m < 3600) {
                if (m / 60 != 0) {
                    time = m / 60;
                }
                if (time < 0)
                    time = 0;
                str = time + "分钟前";
            } else {
                if (m / 3600 != 0) {
                    time = m / 3600;
                }
                str = time + "小时前";
            }
            return str;
        }
        if (afterYear(today, data)) {
            return simpleDateFormat(DEFAULT_PATTERN, date);
        }
        DateUtils.nextDate(data);
        if (data.toString().equals(today.toString())) {
            return "昨天:" + simpleDateFormat(HH_PATTERN, date);
        }
        DateUtils.nextDate(data);
        if (data.toString().equals(today.toString())) {
            return "前天:" + simpleDateFormat(HH_PATTERN, date);
        }
        return simpleDateFormat(MMDDCHINA_PATTERN, sdate);
    }

    /**
     * 返回完整日期+时间
     *
     * @param dat String
     * @return String
     */
    public static String formatday5(String dat) {
        if (dat == null || dat.equals("")) {
            return "";
        }
        Date date = null;
        try {
            date = new SimpleDateFormat(ALL_PATTERN).parse(dat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date sdate = date;
        Date data = parseNullIfException(simpleDateFormat(DEFAULT_PATTERN, date));
        Date today = parseNullIfException(simpleDateFormat(DEFAULT_PATTERN, new Date()));
        if (data.toString().equals(today.toString())) {
            String str = "";
            long time = 1;
            long ms = new Date().getTime() - sdate.getTime();
            long m = ms / 1000;
            if (m < 3600) {
                if (m / 60 != 0) {
                    time = m / 60;
                }
                if (time < 0)
                    time = 0;
                str = time + "分钟前";
            } else {
                if (m / 3600 != 0) {
                    time = m / 3600;
                }
                str = time + "小时前";
            }
            return str;
        }
        if (afterYear(today, data)) {
            return simpleDateFormat(DEFAULT_PATTERN, date);
        }
        DateUtils.nextDate(data);
        if (data.toString().equals(today.toString())) {
            return "昨天:" + simpleDateFormat(HH_PATTERN, date);
        }
        DateUtils.nextDate(data);
        if (data.toString().equals(today.toString())) {
            return "前天:" + simpleDateFormat(HH_PATTERN, date);
        }
        return simpleDateFormat(ALL_PATTERN, sdate);
    }

    public static String formatday6(String dat) {
        if (dat == null || dat.equals("")) {
            return "";
        }
        Date date = null;
        try {
            date = new SimpleDateFormat(ALL_PATTERN).parse(dat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date sdate = date;
        Date data = parseNullIfException(simpleDateFormat(DEFAULT_PATTERN, date));
        Date today = parseNullIfException(simpleDateFormat(DEFAULT_PATTERN, new Date()));
        if (data.toString().equals(today.toString())) {
            String str = "";
            long time = 1;
            long ms = new Date().getTime() - sdate.getTime();
            long m = ms / 1000;
            if (m < 3600) {
                if (m / 60 != 0) {
                    time = m / 60;
                }
                if (time < 0)
                    time = 0;
                str = time + "分钟前";
            } else {
                if (m / 3600 != 0) {
                    time = m / 3600;
                }
                str = time + "小时前";
            }
            return str;
        }
        if (afterYear(today, data)) {
            return simpleDateFormat(DEFAULT_PATTERN, date);
        }
        DateUtils.nextDate(data);
        if (data.toString().equals(today.toString())) {
            return "昨天:" + simpleDateFormat(HH_PATTERN, date);
        }
        DateUtils.nextDate(data);
        if (data.toString().equals(today.toString())) {
            return "前天:" + simpleDateFormat(HH_PATTERN, date);
        }
        return simpleDateFormat(ALL_PATTERN_S, sdate);
    }

    /**
     * 当年返回月-日 其他返回年-月-日
     *
     * @param dat String
     * @return String
     */
    public static String fromatday3(String dat) {
        Date date = null;
        try {
            date = new SimpleDateFormat(ALL_PATTERN).parse(dat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date data = parseNullIfException(simpleDateFormat(DEFAULT_PATTERN, date));
        Date today = parseNullIfException(simpleDateFormat(DEFAULT_PATTERN, new Date()));
        if (afterYear(today, data)) {
            return simpleDateFormat(DEFAULT_PATTERN, date);
        } else {
            return simpleDateFormat(MMDDCHINA_PATTERN, date);
        }
    }

    /**
     * 当天返回小时、分，其他返回月-日
     *
     * @param dat String
     * @return String
     */
    public static String formatday2(String dat) {
        Date date = null;
        try {
            date = new SimpleDateFormat(ALL_PATTERN).parse(dat);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        return formatday2(date);
    }

    public static String formatday2(Date date) {
        Date sdate = date;
        Date data = parseNullIfException(simpleDateFormat(DEFAULT_PATTERN, date));
        Date today = parseNullIfException(simpleDateFormat(DEFAULT_PATTERN, new Date()));
        if (data.toString().equals(today.toString())) {
            return simpleDateFormat(HH_PATTERN, date);
        }
        return simpleDateFormat(MMDD_PATTERN, sdate);
    }

    /**
     * 返回与当前日期相差的天数 如为负数返回1
     *
     * @param date String
     * @return int
     */
    public static int marginDate(String dat) {
        Date date = null;
        try {
            date = new SimpleDateFormat(DEFAULT_PATTERN).parse(dat);
        } catch (ParseException e) {
            e.printStackTrace();
            return 1;
        }
        return marginDate(new Date(), date);
    }

    //重载
    public static int marginDate(String dat1, String dat2) {
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = new SimpleDateFormat(DEFAULT_PATTERN).parse(dat1);
            date2 = new SimpleDateFormat(DEFAULT_PATTERN).parse(dat2);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
        return marginDate(date1, date2);
    }

    /**
     * //返回昨天、前天  方法已过时
     * public static String formatday(String date) {
     * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     * Date userdate = null;
     * try {
     * userdate = sdf.parse(date);
     * } catch (ParseException e) {
     * e.printStackTrace();
     * }
     * Date now = new Date();
     * Calendar time = Calendar.getInstance();
     * time.clear();
     * time.set(Calendar.YEAR, userdate.getYear());
     * time.set(Calendar.MONTH, userdate.getMonth()); //Calendar对象默认一月为0
     * int day = time.getActualMaximum(Calendar.DAY_OF_MONTH); //本月份的天数
     * if (now.getMonth() == userdate.getMonth()) {
     * if (now.getDate() - userdate.getDate() == 0) {
     * SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
     * return sdf1.format(userdate);
     * } else if (now.getDate() - userdate.getDate() == 1) {
     * SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
     * return ("昨天: " + sdf1.format(userdate));
     * } else if ((now.getDate() - userdate.getDate() == 2)) {
     * SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
     * return ("前天: " + sdf1.format(userdate));
     * } else {
     * SimpleDateFormat sdf1 = new SimpleDateFormat("MM:dd HH:mm");
     * return ("" + sdf1.format(userdate));
     * }
     * } else {
     * if (now.getDate() + day - userdate.getDate() == 1) {
     * SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
     * return ("昨天: " + sdf1.format(userdate));
     * } else if ((now.getDate() + day - userdate.getDate() == 2)) {
     * SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
     * return ("前天: " + sdf1.format(userdate));
     * } else {
     * SimpleDateFormat sdf1 = new SimpleDateFormat("MM:dd HH:mm");
     * return ("" + sdf1.format(userdate));
     * }
     * }
     * 1274870496271
     * }
     */

    public static void main(String[] args) {

    	System.out.println( format(new Date(), "yyyyMMdd"));
    }
}
