package com.example.group2.pillpal;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by Jessica on 5/2/16.
 */
public class UserInstance {

    String id;
    Integer index;
    String name;
    String email;
    String phone;
    String prescription;
    String password;
    String lastRefill;
    Integer card;
    String billAdd;
    String shipAdd;
    Boolean refRequested;
    ArrayList<User.statHolder> stats;
    ArrayList<Alarm> alarms;
    ArrayList<User.historyHolder> refHistory;

    private static UserInstance instance = null;


    protected UserInstance () {
        //protect against outside instantiation

    }

    public static UserInstance getInstance() {
        if(instance == null) {
            instance = new UserInstance();
        }
        return instance;
    }

    public void setAll(User u) {
        id = u.id;
        index = u.index;
        name = u.name;
        email = u.email;
        phone = u.phone;
        prescription = u.prescription;
        password = u.password;
        lastRefill = u.lastRefill;
        card = u.card;
        billAdd = u.billAdd;
        shipAdd = u.shipAdd;
        refRequested = u.refRequested;
        stats = u.stats;
        alarms = u.alarms;
        refHistory = u.refHistory;

    }

    public ArrayList<User.statHolder> getStats() {
        return stats;
    }

    public ArrayList<Alarm> getAlarms() {
        return alarms;
    }

    public ArrayList<User.historyHolder> getHistory() {
        return refHistory;
    }



    public void deleteAlarm(Alarm alarm) {
        alarms.remove(alarm);
    }

    public void addAlarm(Alarm alarm) {
        alarms.add(alarm);
    }
}
