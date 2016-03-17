package com.cqgas.gasmeter.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 国耀 on 2015/12/5.
 */
public class DateUtils {
    public static final String DATE_FORMAT="yyyyMMdd";
    public static final String FULL_DATE_FORMAT="yyyyMMddHHmmss";
    public static SimpleDateFormat simpleDateFormat;
    public static SimpleDateFormat fullDateFormat;

    public static long getTimeInMillis(String date) {
        long result = 0;
        if (!TextUtils.isEmpty(date) && date.length() > 8) {
            int year = Integer.valueOf(date.substring(0, 4));
            int month = Integer.valueOf(date.substring(4, 6));
            int day = Integer.valueOf(date.substring(6, 8));
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day, 0, 0, 0);
            result = calendar.getTimeInMillis();
        }
        return result;
    }

    public static String currentTime(){
        if(fullDateFormat == null){
            fullDateFormat = new SimpleDateFormat(FULL_DATE_FORMAT);
        }
        Date date = new Date(System.currentTimeMillis());
        return fullDateFormat.format(date);
    }

    public static String getFormatDate(long min){
        Date date = new Date(min);
        if(simpleDateFormat == null){
            simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        }
        return simpleDateFormat.format(date);
    }

    public static String getFullFormatDate(long min){
        Date date = new Date(min);
        if(fullDateFormat == null){
            fullDateFormat = new SimpleDateFormat(FULL_DATE_FORMAT);
        }
        return fullDateFormat.format(date);
    }
}
