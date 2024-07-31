package com.example.backend.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String convertTimestampToDate(long timestamp) {
        Date date = new Date(timestamp);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(date);

        return formattedDate;
    }
}
