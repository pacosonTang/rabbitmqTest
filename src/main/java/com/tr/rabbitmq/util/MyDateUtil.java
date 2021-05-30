package com.tr.rabbitmq.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateUtil {

    public static void main(String[] args) {
    }


    public static String getNow() {
        return getNow("yyyy-MM-dd hh:mm:ss.SSS");
    }

    public static String getNow(String format) {
        SimpleDateFormat formater = new SimpleDateFormat(format);
        String dateFormated = formater.format(new Date());
        return dateFormated;
    }
}
