package com.abcxo.android.ifootball.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具类
 *
 * @author Vincent
 */
public class DateUtils {
    public static String getYearMonthDayWithChinese(long date) {
        SimpleDateFormat simpleDataFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        return simpleDataFormat.format(new Date(date));
    }

    public static String getHourMinuteWithColon(long date) {
        SimpleDateFormat simpleDataFormat = new SimpleDateFormat("HH:mm", Locale.CHINA);
        return simpleDataFormat.format(new Date(date));
    }
}
