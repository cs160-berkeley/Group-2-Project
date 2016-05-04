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
        String hours_string = (hours >= 12) ? Integer.toString(hours - 12) : Integer.toString(hours);
        timeStringFormat = hours_string + ":" + Integer.toString(minutes) + " ";
    }

}
