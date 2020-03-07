package com.example.daoyou;




import java.text.SimpleDateFormat;

import java.util.Calendar;

import java.util.Date;








    public class GetCurrentTime {




    public static int getYear() {

            Calendar date = Calendar.getInstance();
            int year = date.get(Calendar.YEAR);
            return year;

    }


    public static int getMonth() {

        Calendar date = Calendar.getInstance();
        int month = date.get(Calendar.MONTH)+1;
        return month;

    }





    public static int getDay() {

        Calendar date = Calendar.getInstance();
        int day = date.get(Calendar.DATE);
        return day;

    }




    public static int getHour() {

        Calendar date = Calendar.getInstance();
        int hour = date.get(Calendar.HOUR_OF_DAY);
        return hour;

    }


    public static String getCurrentDate(){
        int year = getYear();
        int month = getMonth();
        int day = getDay();
        int hour = getHour();
        return String.valueOf(year) +  "|" + String.valueOf(month) +  "|" + String.valueOf(day) +  "|" + String.valueOf(hour) +  "|";
    }

}
