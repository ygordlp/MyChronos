package br.com.atlantico.mychronos.model;

import java.util.Calendar;

/**
 * Created by pereira_ygor on 09/06/2015.
 */
public class Timestamp {

    private int hour;
    private int minute;
    private long time;

    public Timestamp(int hour, int minute){
        this.hour = hour;
        this.minute = minute;

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        this.time = c.getTimeInMillis();
    }

    public long getTime(){
        return time;
    }

    public String toString(){
        String h = ((hour > 9)? "" : "0") + hour;
        String m = ((minute > 9)? "" : "0") + minute;

        return h + ":" + m;
    }

}
