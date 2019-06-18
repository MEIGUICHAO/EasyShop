package com.example.moguhaian.easyshop.Utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    /**
     * 日期格式字符串转换成时间戳
     *
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static long getStringToDate(String dateString, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }


    public static String getImageTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Calendar imageTime = Calendar.getInstance();
        imageTime.setTimeInMillis(time);
        if (sameDay(calendar, imageTime)) {
            return "今天";
        } else if (sameWeek(calendar, imageTime)) {
            return "本周";
        } else if (sameMonth(calendar, imageTime)) {
            return "本月";
        } else {
            Date date = new Date(time);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
            return sdf.format(date);
        }
    }

    public static String getHourTimeFormat(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Calendar imageTime = Calendar.getInstance();
        imageTime.setTimeInMillis(time);
        String format = "HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date(time));
    }


    /**
     * 根据是否当天的消息返回不同格式的时间格式，当天为HH:mm:ss，否则为yyyy/MM/dd
     *
     * @param time 时间戳
     * @return
     */
    public static String getDefaultDateFromat(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Calendar imageTime = Calendar.getInstance();
        imageTime.setTimeInMillis(time);
        String format;
        if (sameDay(calendar, imageTime)) {
            format = "HH:mm:ss";
        } else {
            format = "yyyy/MM/dd";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date(time));
    }


    public static String getFullDateFromat(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Calendar imageTime = Calendar.getInstance();
        imageTime.setTimeInMillis(time);
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date(time));
    }

    public static String getDayFromat(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Calendar imageTime = Calendar.getInstance();
        imageTime.setTimeInMillis(time);
        String format = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date(time));
    }

    public static String getCountDown(long date) {
        try {
            if (date >= 0) {
                date = date - 1;
                String dateStr = "";
                int hour = (int) (date / (60 * 60));
                dateStr = dateStr + (hour > 9 ? hour + ":" : "0" + hour + ":");
                int min = (int) ((date / 60) - hour * 60);
                dateStr = dateStr + (min > 9 ? min + ":" : "0" + min + ":");
                long s = (date - hour * 60 * 60 - min * 60);
                dateStr = dateStr + (s > 9 ? s + "" : "0" + s + "");
                return dateStr;
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public static String getCountDownDay(long date) {
        try {
            if (date >= 0) {
                date = date - 1;
                String dateStr = "";
                int day = (int) (date / (24 * 60 * 60));
                if (day == 0) {
                    dateStr = "";
                } else {
                    dateStr = dateStr + (day > 100 ? day + ":" : (day > 9 ? day : "0" + day));
                    dateStr = dateStr + "天";
                }
                int hour = (int) (date / (60 * 60) - day * (24));
                dateStr = dateStr + (hour > 9 ? hour + ":" : "0" + hour + ":");
                int min = (int) ((date / 60) - day * (24 * 60) - hour * 60);
                dateStr = dateStr + (min > 9 ? min + ":" : "0" + min + ":");
                long s = (date - day * (24 * 60 * 60) - hour * 60 * 60 - min * 60);
                dateStr = dateStr + (s > 9 ? s + "" : "0" + s + "");
                return dateStr;
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public static String getMonthDay(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Calendar imageTime = Calendar.getInstance();
        imageTime.setTimeInMillis(time);
        String format = "MM月dd日";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date(time));
    }

    public static String getSimpleDayFromat(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Calendar imageTime = Calendar.getInstance();
        imageTime.setTimeInMillis(time);
        String format = "yy.MM.dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date(time));
    }

    public static String getSimpleDayFromat2(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Calendar imageTime = Calendar.getInstance();
        imageTime.setTimeInMillis(time);
        String format = "yy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date(time));
    }

    //获取当前系统时间
    public static long getCurrentTimeStamp() {
        return System.currentTimeMillis();
    }


    public static boolean sameDay(Calendar calendar1, Calendar calendar2) {
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR);
    }

    public static boolean sameWeek(Calendar calendar1, Calendar calendar2) {
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.WEEK_OF_YEAR) == calendar2.get(Calendar.WEEK_OF_YEAR);
    }

    public static boolean sameMonth(Calendar calendar1, Calendar calendar2) {
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH);
    }

    /**
     * 获取指定日期的起始时间
     */
    public static String getCurrentDateStartTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(calendar.getTime());
        return str;
//        return calendar.getTime();
    }

    /**
     * 获取指定日期的结束时间
     */
    public static String getCurrentDateEndTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(calendar.getTime());
        return str;
//        return calendar.getTime();
    }


    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 通过指定的年份和月份获取当月有多少天.
     *
     * @param year  年.
     * @param month 月.
     * @return 天数.
     */
    public static int getMonthDays(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
                    return 29;
                } else {
                    return 28;
                }
            default:
                return -1;
        }
    }

    /**
     * 获取指定年月的 1 号位于周几.
     *
     * @param year  年.
     * @param month 月.
     * @return 周.
     */
    public static int getFirstDayWeek(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 0);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static String getHourTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Calendar imageTime = Calendar.getInstance();
        imageTime.setTimeInMillis(time);
        String format = "HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date(time));
    }

    /**
     * 判断是否为今天(效率比较高)
     *
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsToday(String day) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == 0) {
                return true;
            }
        }
        return false;

    }

    private static ThreadLocal<SimpleDateFormat> DateLocal = new ThreadLocal<SimpleDateFormat>();


    public static SimpleDateFormat getDateFormat() {
        if (null == DateLocal.get()) {
            DateLocal.set(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA));
        }
        return DateLocal.get();
    }

    public static String formatDisplayTime(long time) {
        return formatDisplayTime(getFullDateFromat(time), null);
    }

    /**
     * 格式化时间（输出类似于 刚刚, 4分钟前, 一小时前, 昨天这样的时间）
     *
     * @param time    需要格式化的时间 如"2014-07-14 19:01:45"
     * @param pattern 输入参数time的时间格式 如:"yyyy-MM-dd HH:mm:ss"
     *                <p/>
     *                如果为空则默认使用"yyyy-MM-dd HH:mm:ss"格式
     * @return time为null，或者时间格式不匹配，输出空字符""
     */
    public static String formatDisplayTime(String time, String pattern) {
        String display = "";
        int tMin = 60 * 1000;
        int tHour = 60 * tMin;
        int tDay = 24 * tHour;

        if (time != null) {
            if (pattern == null) {
                pattern = "yyyy-MM-dd HH:mm:ss";
            }
            try {
                Date tDate = new SimpleDateFormat(pattern).parse(time);
                Date today = new Date();
                SimpleDateFormat thisYearDf = new SimpleDateFormat("yyyy");
                SimpleDateFormat todayDf = new SimpleDateFormat("yyyy-MM-dd");
                Date thisYear = new Date(thisYearDf.parse(thisYearDf.format(today)).getTime());
                Date yesterday = new Date(todayDf.parse(todayDf.format(today)).getTime());
                Date beforeYes = new Date(yesterday.getTime() - tDay);
                if (tDate != null) {
                    @SuppressWarnings("unused")
                    SimpleDateFormat halfDf = new SimpleDateFormat("MM月dd日");
                    long dTime = today.getTime() - tDate.getTime();
                    if (tDate.before(thisYear)) {
                        display = new SimpleDateFormat("yyyy年MM月dd日").format(tDate);
                    } else {

                        if (dTime < tMin) {
                            display = "刚刚";
                        } else if (dTime < tHour) {
                            display = (int) Math.ceil(dTime / tMin) + "分钟前";
                        } else if (dTime < tDay && tDate.after(yesterday)) {
                            display = (int) Math.ceil(dTime / tHour) + "小时前";
                        } else if (tDate.after(beforeYes) && tDate.before(yesterday)) {
                            display = "昨天  " + new SimpleDateFormat("HH:mm").format(tDate);
                        } else {
                            display = multiSendTimeToStr(tDate.getTime() / 1000);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return display;
    }

    /**
     * 群发使用的时间转换
     */
    public static String multiSendTimeToStr(long timeStamp) {

        if (timeStamp == 0) {
            return "";
        }
        Calendar inputTime = Calendar.getInstance();
        String timeStr = timeStamp + "";
        if (timeStr.length() == 10) {
            timeStamp = timeStamp * 1000;
        }
        inputTime.setTimeInMillis(timeStamp);
        Date currenTimeZone = inputTime.getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (calendar.before(inputTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return sdf.format(currenTimeZone);
        }
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        if (calendar.before(inputTime)) {
            @SuppressWarnings("unused")
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return "昨天";
        } else {
            calendar.add(Calendar.DAY_OF_MONTH, -5);
            if (calendar.before(inputTime)) {
                return getWeekDayStr(inputTime.get(Calendar.DAY_OF_WEEK));
            } else {
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.MONTH, Calendar.JANUARY);
                if (calendar.before(inputTime)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("M" + "/" + "d" + " ");
                    String temp1 = sdf.format(currenTimeZone);
                    SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
                    String temp2 = sdf1.format(currenTimeZone);
                    return temp1 + temp2;
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + "/" + "M" + "/" + "d" + " ");
                    String temp1 = sdf.format(currenTimeZone);
                    SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
                    String temp2 = sdf1.format(currenTimeZone);
                    return temp1 + temp2;
                }
            }
        }
    }

    /**
     * 时间转化为星期
     *
     * @param indexOfWeek 星期的第几天
     */
    public static String getWeekDayStr(int indexOfWeek) {
        String weekDayStr = "";
        switch (indexOfWeek) {
            case 1:
                weekDayStr = "星期日";
                break;
            case 2:
                weekDayStr = "星期一";
                break;
            case 3:
                weekDayStr = "星期二";
                break;
            case 4:
                weekDayStr = "星期三";
                break;
            case 5:
                weekDayStr = "星期四";
                break;
            case 6:
                weekDayStr = "星期五";
                break;
            case 7:
                weekDayStr = "星期六";
                break;
        }
        return weekDayStr;
    }

}
