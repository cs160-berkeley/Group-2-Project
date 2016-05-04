package com.example.group2.pillpal;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

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
    HashMap<String, String> prescription;
    String password;
    ArrayList<HashMap<String, String>> refillHistory;
    Integer cardNumber;
    String cardType;
    HashMap<String, String> billAdd;
    HashMap<String, String> shipAdd;
    HashMap<String, String> currentRefillRequest;
    Boolean refillRequested;
    ArrayList<statHolder> stats;
    ArrayList<Alarm> alarms;

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

    public ArrayList<Alarm> getAlarms() {
        return alarms;
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
            JSONArray refill_history_json = (JSONArray) targetUser.get("refill_history");
            refillHistory = new ArrayList<HashMap<String, String>>();
            if (refill_history_json != null) {
                for (int i = 0; i < refill_history_json.length(); i++){
                    JSONObject entry = (JSONObject) refill_history_json.get(i);
                    HashMap<String, String> first_refill = new HashMap<String, String>();
                    first_refill.put("name", entry.getString("name"));
                    first_refill.put("date", entry.getString("date"));
                    refillHistory.add(first_refill);
                }
            }
            JSONObject json_prescription = (JSONObject) targetUser.get("prescription");
            prescription = new HashMap<String, String>();
            prescription.put("name", json_prescription.getString("name"));
            prescription.put("dosage", json_prescription.getString("dosage"));
            email = targetUser.getString("email");
            password = targetUser.getString("password");

            JSONObject billing_address_json = (JSONObject) targetUser.get("bill_address");
            JSONObject shipping_address_json = (JSONObject) targetUser.get("ship_address");
            billAdd = new HashMap<String, String>();
            shipAdd = new HashMap<String, String>();
            billAdd.put("street_name", billing_address_json.getString("street_name"));
            billAdd.put("city", billing_address_json.getString("city"));
            billAdd.put("state", billing_address_json.getString("state"));
            billAdd.put("zip", billing_address_json.getString("zip"));

            shipAdd.put("street_name", shipping_address_json.getString("street_name"));
            shipAdd.put("city", shipping_address_json.getString("city"));
            shipAdd.put("state", shipping_address_json.getString("state"));
            shipAdd.put("zip", shipping_address_json.getString("zip"));

            phone = targetUser.getString("phone");
            cardNumber = targetUser.getInt("card_number");
            cardType = targetUser.getString("card_type");

            refillRequested = targetUser.getBoolean("refill_requested");
            currentRefillRequest = new HashMap<String, String>();
            JSONArray preStats = targetUser.getJSONArray("stats");
            JSONArray preAlarms = targetUser.getJSONArray("alarms");

            stats = setStats(preStats);
            alarms = setAlarms(preAlarms);

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
                        oneDay.getInt("estrogen"),oneDay.getInt("estrogen"), oneDay.getInt("estrogen") );
                myStats.add(obj);

            } catch (Exception e) {
                    e.printStackTrace();
                }

        } return myStats;

    }

    public ArrayList<Alarm> setAlarms(JSONArray alarmsArr) {
        /** if empty... do something. Or not. **/
        ArrayList<Alarm> myAlarms = new ArrayList<>();

        for (int i = 0; i < alarmsArr.length(); i++) {
            try {
                JSONObject alarm_json = alarmsArr.getJSONObject(i);
                Alarm alarm = new Alarm(alarm_json.getInt("hours"), alarm_json.getInt("minutes"), true);
                myAlarms.add(alarm);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return myAlarms;
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

    public class historyHolder implements Serializable{
        String date;

        public historyHolder(String d) {
            date = d;
        }


    }

}
