package com.example.group2.pillpal;

/**
 * Created by unzi on 5/3/16.
 */
public class Alarm {
    public int hours;
    public int minutes;
    public Boolean isOn;
    public String timeOfDay;
    public String timeStringFormat;

    public Alarm(int hours, int minutes, Boolean is_on) {
        this.hours = hours;
        this.minutes = minutes;
        this.isOn = is_on;
        this.timeOfDay = (hours >= 12) ? "PM" : "AM";
        int hours_format = (hours >= 12) ? hours - 12 : hours;
        String hours_string = (hours_format < 10) ? Integer.toString(hours_format) : Integer.toString(hours_format);
        String minutes_string = (minutes < 10) ? "0" + Integer.toString(minutes) : Integer.toString(minutes);
        timeStringFormat = hours_string + ":" + minutes_string + " ";
    }

}
