package br.com.atlantico.mychronos.test;

import org.junit.Test;

import java.util.ArrayList;

import br.com.atlantico.mychronos.model.Timestamp;
import br.com.atlantico.mychronos.utils.TimeUtils;

import static org.junit.Assert.assertEquals;

/**
 * Created by pereira_ygor on 08/06/2015.
 */
public class TimeUtilsTest {

    @Test
    public void testTimeDHMtoString_day() {
        long time = TimeUtils.DAY * 3;
        String expected = "3d";
        String actual = TimeUtils.TimeDHMtoString(time);

        assertEquals(expected, actual);
    }

    @Test
    public void testTimeDHMtoString_hour() {
        long time = TimeUtils.HOUR * 5;
        String expected = "5h";
        String actual = TimeUtils.TimeDHMtoString(time);

        assertEquals(expected, actual);
    }

    @Test
    public void testTimeDHMtoString_minute() {
        long time = TimeUtils.MINUTE * 45;
        String expected = "45m";
        String actual = TimeUtils.TimeDHMtoString(time);

        assertEquals(expected, actual);
    }

    @Test
    public void testTimeDHMtoString_day_hour() {
        long time = TimeUtils.DAY * 5 + TimeUtils.HOUR * 8;
        String expected = "5d 8h";
        String actual = TimeUtils.TimeDHMtoString(time);

        assertEquals(expected, actual);
    }

    @Test
    public void testTimeDHMtoString_hour_minute() {
        long time = TimeUtils.HOUR * 8 + TimeUtils.MINUTE * 30;
        String expected = "8h 30m";
        String actual = TimeUtils.TimeDHMtoString(time);

        assertEquals(expected, actual);
    }

    @Test
    public void testTimeDHMtoString_day_minute() {
        long time = TimeUtils.DAY * 1 + TimeUtils.MINUTE * 20;
        String expected = "1d 20m";
        String actual = TimeUtils.TimeDHMtoString(time);

        assertEquals(expected, actual);
    }

    @Test
    public void testTimeDHMtoString_day_hour_minute() {
        long time = TimeUtils.DAY * 1 + TimeUtils.HOUR * 8 + TimeUtils.MINUTE * 45;
        String expected = "1d 8h 45m";
        String actual = TimeUtils.TimeDHMtoString(time);

        assertEquals(expected, actual);
    }

    @Test
    public void testCalcWorkedTime_1_timestamp(){
        ArrayList<Timestamp> timestamps = new ArrayList<>();
        timestamps.add(new Timestamp(8, 0));

        long expected = 0;
        long actual = TimeUtils.calcWorkedTime(timestamps);

        assertEquals(expected, actual, 0);
    }

    @Test
    public void testCalcWorkedTime_2_timestamps(){
        ArrayList<Timestamp> timestamps = new ArrayList<>();
        timestamps.add(new Timestamp(8, 0));
        timestamps.add(new Timestamp(12, 0));

        long expected = 4 * TimeUtils.HOUR;
        long actual = TimeUtils.calcWorkedTime(timestamps);

        assertEquals(expected, actual);
    }

    @Test
    public void testCalcWorkedTime_3_timestamps(){
        ArrayList<Timestamp> timestamps = new ArrayList<>();
        timestamps.add(new Timestamp(8, 0));
        timestamps.add(new Timestamp(12, 0));
        timestamps.add(new Timestamp(13, 0));

        long expected = 4 * TimeUtils.HOUR;
        long actual = TimeUtils.calcWorkedTime(timestamps);

        assertEquals(expected, actual);
    }

    @Test
    public void testCalcWorkedTime_4_timestamps(){
        ArrayList<Timestamp> timestamps = new ArrayList<>();
        timestamps.add(new Timestamp(8, 0));
        timestamps.add(new Timestamp(12, 0));
        timestamps.add(new Timestamp(13, 0));
        timestamps.add(new Timestamp(17, 0));

        long expected = 8 * TimeUtils.HOUR;
        long actual = TimeUtils.calcWorkedTime(timestamps);

        assertEquals(expected, actual);
    }
}
