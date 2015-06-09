package br.com.atlantico.mychronos.model;

/**
 * Created by pereira_ygor on 09/06/2015.
 */
public class Timestamp {

    private int hour;
    private int minute;

    public Timestamp(int hour, int minute){
        this.hour = hour;
        this.minute = minute;
    }

    public String toString(){
        return hour + ":" + minute;
    }

}
