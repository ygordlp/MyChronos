package br.com.atlantico.mychronos.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import br.com.atlantico.mychronos.model.Timestamp;

/**
 * Created by pereira_ygor on 08/06/2015.
 */
public class TimeUtils {

    public static final long MINUTE = 60 * 1000;
    public static final long HOUR = 60 * MINUTE;
    public static final long DAY = 24 * HOUR;


    /**
     * Converts time to format 99d99h99m
     * Examples: 4d, 4d5h, 4d5h30m, 5h30m, 4h30m
     * @param time
     * @return
     */
    public static String TimeDHMtoString(long time) {
        long days, hours, minutes, daysTotal, hoursTotal;
        String result = "";

        days = time / DAY;
        daysTotal = days * DAY;

        hours = (time - daysTotal) / HOUR;
        hoursTotal = hours * HOUR;

        minutes = (time - daysTotal - hoursTotal) / MINUTE;

        if (days > 0) {
            result += days + "d";
        }

        if (hours > 0) {
            result += ((days > 0) ? " " : "") + hours + "h";
        }

        if (minutes > 0) {
            result += ((hours > 0) ? " " : (days > 0) ? " " : "") + minutes + "m";
        }

        return result;
    }

    public static long calcWorkedTime(ArrayList<Timestamp> timestamps) {
        long result = 0;

        int count = timestamps.size();

        for (int i = 0; i < count; i += 2) {
            if(i + 1 < count) {
                result += timestamps.get(i + 1).getTime() - timestamps.get(i).getTime();
            }
        }

        return result;
    }

    public static Timestamp calcTimeToLeave(ArrayList<Timestamp> timestamps){
        Timestamp result = null;

        if(timestamps != null && !timestamps.isEmpty()){
            long regular = timestamps.get(0).getTime() + (HOUR * 9);
            int count = timestamps.size();
            long over = 0;
            if(count > 2) {
                long lunchTime = timestamps.get(2).getTime() - timestamps.get(1).getTime();
                if(lunchTime > HOUR){
                    over = lunchTime - HOUR;
                }
            }
            result = new Timestamp(regular + over);

            if(result.getHour() < 17){
                result = new Timestamp(17, 0);
            }
        }

        return result;
    }

    public static String getSQLDate(Calendar cal){
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        String sy = year + "-";
        String sm = ((month < 10)? "0" : "") + month + "-";
        String sd = ((day < 10)? "0" : "") + day;

        String date = sy + sm + sd;
        return date;
    }

    public static String getShortDate(Calendar cal){
        int year = cal.get(Calendar.YEAR);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        String sm = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());

        String date = sm + " " + day + ", " + year;
        return date;
    }

    /**
     * Gets the information if the days are the same, ignoring time;
     *
     * @param cal1 A day to be compare
     * @param cal2 A day to be compare
     * @return True, if is the same day
     */
    public static boolean isSameDay(Calendar cal1, Calendar cal2){
        int y1 = cal1.get(Calendar.YEAR);
        int m1 = cal1.get(Calendar.MONTH);
        int d1 = cal1.get(Calendar.DAY_OF_MONTH);

        int y2 = cal2.get(Calendar.YEAR);
        int m2 = cal2.get(Calendar.MONTH);
        int d2 = cal2.get(Calendar.DAY_OF_MONTH);

        boolean res = (y1 == y2) && (m1 == m2) && (d1 == d2);

        return res;
    }

    /**
     * Get a day after the cal day or before;
     * Positive number of days get a day after cal.
     * Negative number of days get a day before cal.
     * @param cal
     * @param days
     * @return
     */
    public Calendar getAfterBeforeDays(Calendar cal, int days){
        Calendar day = Calendar.getInstance();
        day.setTimeInMillis(cal.getTimeInMillis());
        day.add(Calendar.DAY_OF_MONTH, 1);
        return day;
    }

    public Calendar getNextDay(Calendar cal){
        return getAfterBeforeDays(cal, 1);
    }

    public Calendar getPreviousDay(Calendar cal){
        return getAfterBeforeDays(cal, -1);
    }
}
