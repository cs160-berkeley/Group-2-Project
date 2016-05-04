package com.example.group2.pillpal;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jessica on 4/29/16.
 */
public class User implements java.io.Serializable {
    /** in order:
     * the id number, same as index basically (may be useful when things get out of order)
     * the current index in the JSONArray
     * name, email, phone ("xxx-xxx-xxxx"), prescription, password self explanatory
     * Last refill is pill - not the date - of last refill (same as prescp.)
     * card is the last 4 digits of their CC number
     * billAdd and shipAdd are the billing and shipping addresses, respectively.
     * refRequested is a boolean that tells you, well, if a refill was requested or not.
     * Stats is array of stats (per ith day), alarms array of alarms, you get the idea.
     */
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
    ArrayList<statHolder> stats;
    ArrayList<alarmHolder> alarms;
    ArrayList<historyHolder> refHistory;


    public User(int idNum, JSONArray data) {
        index = idNum;
        setupUser(data);

    }

    /** Get methods **/

    public User getUser() {
        return this;
    }

    public ArrayList<statHolder> getStats() {
        return stats;
    }

    public ArrayList<alarmHolder> getAlarms() {
        return alarms;
    }

    public ArrayList<historyHolder> getHistory() {
        return refHistory;
    }


    public void setupUser(JSONArray arr) {
        /** get the id-th user from the entire JSON array, then uses info from the id-th user in the methods below to set up
        * all the instance variables so that when the object is deserialized in any context of the application, it can be
        * easily accessed and editable (maybe?) as a User object; AKA abstract away the JSON! Maybe support re-putting back
        * edited user objects into the database?
         **/
        try {
            JSONObject targetUser = arr.getJSONObject(index);
            id = targetUser.getString("id");
            name = targetUser.getString("name");
            lastRefill = targetUser.getString("prescription"); //** Just same as prescription, for convenience sake
            prescription = targetUser.getString("prescription");
            email = targetUser.getString("email");
            password = targetUser.getString("password");
            billAdd = targetUser.getString("bill_address");
            shipAdd = targetUser.getString("ship_address");
            phone = targetUser.getString("phone");
            card = targetUser.getInt("card");
            refRequested = targetUser.getBoolean("refill_requested");

            JSONArray preStats = targetUser.getJSONArray("stats");
            JSONArray preAlarms = targetUser.getJSONArray("alarms");
            JSONArray prefills = targetUser.getJSONArray("refill_history");

            stats = setStats(preStats);
            alarms = setAlarms(preAlarms);
            refHistory = setRefHistory(prefills);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public ArrayList<statHolder> setStats(JSONArray statsArr) {
        /** if empty... do something. Or not. **/
        ArrayList<statHolder> myStats = new ArrayList<>();

        for(int i = 0; i < statsArr.length(); i++) {
            try {
                JSONObject oneDay = statsArr.getJSONObject(i);
                statHolder obj = new statHolder(oneDay.getInt("day"), oneDay.getString("month"),
                        oneDay.getInt("estrogen"),oneDay.getInt("progestin"), oneDay.getInt("testosterone") );
                myStats.add(obj);

            } catch (Exception e) {
                    e.printStackTrace();
                }

        } return myStats;

    }

    public ArrayList<alarmHolder> setAlarms(JSONArray alarmsArr) {
        /** if empty... do something. Or not. **/
        ArrayList<alarmHolder> myAlarms = new ArrayList<>();

        for (int i = 0; i < alarmsArr.length(); i++) {
            try {
                JSONObject oneAlarm = alarmsArr.getJSONObject(i);
                alarmHolder obj = new alarmHolder(oneAlarm.getString("time"), oneAlarm.getString("ind"));
                myAlarms.add(obj);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } return myAlarms;
    }

    public ArrayList<historyHolder> setRefHistory(JSONArray refHistoryArr) {

        /** if empty... do something. Or not.
         * also this could be implemented as an array of strings, since
         * the only data the refill history object currently holds is just
         * one string. However, if we want to add stuff later, we can just
         * easily add or delete from this + the class itself**/

        ArrayList<historyHolder> myHist = new ArrayList<>();

        for (int i = 0; i < refHistoryArr.length(); i++) {
            try {
                JSONObject oneRefill = refHistoryArr.getJSONObject(i);
                historyHolder obj = new historyHolder(oneRefill.getString("date"));
                myHist.add(obj);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } return myHist;

    }

    /** Edit methods **/

    /** update database?**/


    /**inner classes to hold numbers and shit **/
    public class statHolder implements Serializable{

        Integer day, est, pro, tes;
        String month;


        public statHolder(Integer d, String m,
                          Integer e, Integer p, Integer t) {
            day = d;
            month = m;
            est = e;
            pro = p;
            tes = t;

        }


    }

    public class alarmHolder implements Serializable{
        String time, indicator;


        public alarmHolder(String t, String i) {
            time = t;
            indicator = i;

        }
    }

    public class historyHolder implements Serializable{
        String date;

        public historyHolder(String d) {
            date = d;
        }


    }



}
