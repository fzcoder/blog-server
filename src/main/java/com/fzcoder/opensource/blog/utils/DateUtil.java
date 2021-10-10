package com.fzcoder.opensource.blog.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
    // TimeZone
    // UTC West
    public static final String TIMEZONE_UTC12W = "UTC-12:00";
    public static final String TIMEZONE_UTC11W = "UTC-11:00";
    public static final String TIMEZONE_UTC10W = "UTC-10:00";
    public static final String TIMEZONE_UTC9W = "UTC-9:00";
    public static final String TIMEZONE_UTC8W = "UTC-8:00";
    public static final String TIMEZONE_UTC7W = "UTC-7:00";
    public static final String TIMEZONE_UTC6W = "UTC-6:00";
    public static final String TIMEZONE_UTC5W = "UTC-5:00";
    public static final String TIMEZONE_UTC4W = "UTC-4:00";
    public static final String TIMEZONE_UTC3W = "UTC-3:00";
    public static final String TIMEZONE_UTC2W = "UTC-2:00";
    public static final String TIMEZONE_UTC1W = "UTC-1:00";
    // UTC East
    public static final String TIMEZONE_UTC0E = "UTC+0:00";
    public static final String TIMEZONE_UTC1E = "UTC+1:00";
    public static final String TIMEZONE_UTC2E = "UTC+2:00";
    public static final String TIMEZONE_UTC3E = "UTC+3:00";
    public static final String TIMEZONE_UTC4E = "UTC+4:00";
    public static final String TIMEZONE_UTC5E = "UTC+5:00";
    public static final String TIMEZONE_UTC6E = "UTC+6:00";
    public static final String TIMEZONE_UTC7E = "UTC+7:00";
    public static final String TIMEZONE_UTC8E = "UTC+8:00";
    public static final String TIMEZONE_UTC9E = "UTC+9:00";
    public static final String TIMEZONE_UTC10E = "UTC+10:00";
    public static final String TIMEZONE_UTC11E = "UTC+11:00";
    public static final String TIMEZONE_UTC12E = "UTC+12:00";
    public static final String TIMEZONE_UTC13E = "UTC+13:00";
    public static final String TIMEZONE_UTC14E = "UTC+14:00";

    // Format Pattern
    public static final String FORMAT_PATTERN_1 = "yyyy-MM-dd";
    public static final String FORMAT_PATTERN_2 = "yyyy-MM-dd HH:mm:ss";

    /**
     * Date Format
     * @param date
     * @param pattern
     * @param timezone
     * @return
     */
    public static String format(Date date, String pattern, String timezone) {
        return DateFormatFactory.build(timezone, pattern).format(date);
    }

    public static Date parse(String timezone, String pattern, String source) throws ParseException {
        return DateFormatFactory.build(timezone, pattern).parse(source);
    }

    /**
     * 将日期分割成字符串数组
     * @param date
     * @param timeZone
     * @param format
     * @param regex
     * @return
     */
    public static String[] toDateArray(Date date, String timezone, String pattern, String regex) {
        return format(date, pattern, timezone).split(regex);
    }

    private static DateFormat getDateFormat(String timezone, String pattern) {
        DateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getTimeZone(timezone));
        return sdf;
    }

    static final class DateFormatFactory {
        public static DateFormat build(String timezone, String pattern) {
            DateFormat sdf = new SimpleDateFormat(pattern);
            sdf.setTimeZone(TimeZone.getTimeZone(timezone));
            return sdf;
        }
    }
}
