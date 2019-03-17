package com.example.movieapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AppUtils {

    public static Calendar getDate(String dateString) {
        Calendar calendar = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(dateString);
            calendar = Calendar.getInstance();
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }
}
