package com.roland.urlblocker.services;

import android.annotation.SuppressLint;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class FormatHttpTime {
    private static final String[] s_DaysOfWeek = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private static final String[] s_MonthsOfYear = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    public String FormatHttpTime(Date date) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.setTime(date);
        @SuppressLint("DefaultLocale") String formattedTime = String.format("%s, %d %s %d %02d:%02d:%02d GMT",
                s_DaysOfWeek[cal.get(Calendar.DAY_OF_WEEK) - 1]
                , cal.get(Calendar.DAY_OF_MONTH)
                , s_MonthsOfYear[(cal.get(Calendar.MONTH))]
                , cal.get(Calendar.YEAR), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
        return formattedTime;
    }
}
