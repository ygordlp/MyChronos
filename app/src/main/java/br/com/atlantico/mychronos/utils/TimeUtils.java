package br.com.atlantico.mychronos.utils;

/**
 * Created by pereira_ygor on 08/06/2015.
 */
public class TimeUtils {

    public static final long MINUTE = 60 * 1000;
    public static final long HOUR = 60 * MINUTE;
    public static final long DAY = 24 * HOUR;


    public static String TimeToString(long time){
        long days, hours, minutes, daysTotal, hoursTotal;
        String result = "";

        days = time / DAY;
        daysTotal = days * DAY;

        hours = (time - daysTotal) / HOUR;
        hoursTotal = hours * HOUR;

        minutes = (time - daysTotal - hoursTotal) / MINUTE;

        if(days > 0){
            result += days + "d";
        }

        if(hours > 0){
            result += ((days > 0)? " " : "") + hours + "h";
        }

        if(minutes > 0){
            result += ((hours > 0)? " " : (days > 0)? " " : "") + minutes + "m";
        }

        return result;
    }
}
