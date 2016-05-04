package com.example.group2.pillpal;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jessica on 5/2/16.
 */
public class UserInstance {

    String id;
    Integer index;
    String name;
    String email;
    String phone;
    HashMap<String, String> prescription;
    String password;
    Integer card;
    HashMap<String, String> billAdd;
    HashMap<String, String> shipAdd;
    Boolean refillRequested;
    ArrayList<User.statHolder> stats;
    ArrayList<Alarm> alarms;
    ArrayList<HashMap<String, String>> refillHistory;
    HashMap<String, String> currentRefillRequest;

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
        card = u.card;
        billAdd = u.billAdd;
        shipAdd = u.shipAdd;
        refillRequested = u.refillRequested;
        stats = u.stats;
        alarms = u.alarms;
        refillHistory = u.refillHistory;
        currentRefillRequest = u.currentRefillRequest;
    }

    public ArrayList<User.statHolder> getStats() {
        return stats;
    }

    public ArrayList<Alarm> getAlarms() {
        return alarms;
    }

    public void deleteAlarm(Alarm alarm) {
        alarms.remove(alarm);
    }

    public void addAlarm(Alarm alarm) {
        alarms.add(alarm);
    }
}
