package br.com.atlantico.mychronos.utils;

import java.util.ArrayList;

import br.com.atlantico.mychronos.model.Timestamp;

/**
 * Created by pereira_ygor on 08/06/2015.
 */
public class TimeUtils {

    public static final long MINUTE = 60 * 1000;
    public static final long HOUR = 60 * MINUTE;
    public static final long DAY = 24 * HOUR;


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
}
