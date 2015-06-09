package br.com.atlantico.mychronos.test;

import org.junit.Test;

import br.com.atlantico.mychronos.utils.TimeUtils;

import static org.junit.Assert.assertEquals;

/**
 * Created by pereira_ygor on 08/06/2015.
 */
public class TimeUtilsTest {

    @Test
    public void testTimeToString() {
        System.out.println("Testing TimeUtils.TimeToString");
        long time = TimeUtils.DAY * 3;
        String expected = "3d";
        String actual = TimeUtils.TimeDHMtoString(time);

        System.out.println("Days test: " + actual);
        assertEquals(expected, actual);
        System.out.println("PASSED");

        time = TimeUtils.HOUR * 5;
        expected = "5h";
        actual = TimeUtils.TimeDHMtoString(time);

        System.out.println("Hours test: " + actual);
        assertEquals(expected, actual);
        System.out.println("PASSED");

        time = TimeUtils.MINUTE * 45;
        expected = "45m";
        actual = TimeUtils.TimeDHMtoString(time);

        System.out.println("Minutes test: " + actual);
        assertEquals(expected, actual);
        System.out.println("PASSED");

        time = TimeUtils.DAY * 5 + TimeUtils.HOUR * 8;
        expected = "5d 8h";
        actual = TimeUtils.TimeDHMtoString(time);

        System.out.println("Days and Hours test: " + actual);
        assertEquals(expected, actual);
        System.out.println("PASSED");

        time = TimeUtils.HOUR * 8 + TimeUtils.MINUTE * 30;
        expected = "8h 30m";
        actual = TimeUtils.TimeDHMtoString(time);

        System.out.println("Hours and minutes test: " + actual);
        assertEquals(expected, actual);
        System.out.println("PASSED");

        time = TimeUtils.DAY * 1 + TimeUtils.MINUTE * 20;
        expected = "1d 20m";
        actual = TimeUtils.TimeDHMtoString(time);

        System.out.println("Days and minutes test: " + actual);
        assertEquals(expected, actual);
        System.out.println("PASSED");

        time = TimeUtils.DAY * 1 + TimeUtils.HOUR * 8 + TimeUtils.MINUTE * 45;
        expected = "1d 8h 45m";
        actual = TimeUtils.TimeDHMtoString(time);

        System.out.println("All test: " + actual);
        assertEquals(expected, actual);
        System.out.println("PASSED");
    }
}
