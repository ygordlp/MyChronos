package br.com.atlantico.mychronos.model;

import java.util.Calendar;

/**
 * Created by pereira_ygor on 09/06/2015.
 */
public class Timestamp {

    private long id;
    private long time;
    private int hour;
    private int minute;

    public Timestamp(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        this.time = c.getTimeInMillis();
    }

    public Timestamp(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);

        this.hour = c.get(Calendar.HOUR_OF_DAY);
        this.minute = c.get(Calendar.MINUTE);
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public int getHour() {
        return this.hour;
    }

    public int getMinute() {
        return this.minute;
    }

    public String toString() {
        String h = ((hour > 9) ? "" : "0") + hour;
        String m = ((minute > 9) ? "" : "0") + minute;

        return h + ":" + m;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
