//package com.example.group2.pillpal;
//
//import android.content.Context;
//import android.content.res.AssetManager;
//import android.content.res.Resources;
//import android.os.StrictMode;
//import android.util.Log;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import android.content.res.Resources;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.HashMap;
//
///**
// * Created by Jessica on 4/29/16.
// */
//public class jsonParse {
//
//
//
//
//
//    public JSONObject jParse(String jString) {
//
//        try {
//
//            StringBuilder responseStrBuilder = new StringBuilder();
//
//            String inputStr;
//            while ((inputStr = streamReader.readLine()) != null) {
//                responseStrBuilder.append(inputStr);
//            }
//            streamReader.close();
//
//            String resp = responseStrBuilder.toString();
//            JSONObject mObject = new JSONObject(resp);
//
//            return mObject;
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//
//        return new JSONObject();
//    }
//
//    public JSONObject objParse(String mUrl) {
//
//        try {
//            URL url = new URL(mUrl);
//            HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();
//            httpsURLConnection.setReadTimeout(10000);
//            httpsURLConnection.setConnectTimeout(15000);
//            httpsURLConnection.setRequestMethod("GET");
//            httpsURLConnection.setDoInput(true);
//            if (android.os.Build.VERSION.SDK_INT > 9)
//            {
//                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//                StrictMode.setThreadPolicy(policy);
//            }
//
//
//            httpsURLConnection.connect();
//            int mStatus = httpsURLConnection.getResponseCode();
//            Log.d("DEBUG_TAG", "The response is: " + mStatus);
//
//            InputStream loc = httpsURLConnection.getInputStream();
//
//
//            BufferedReader streamReader = new BufferedReader(new InputStreamReader(loc, "UTF-8"));
//            StringBuilder responseStrBuilder = new StringBuilder();
//
//            String inputStr;
//            while ((inputStr = streamReader.readLine()) != null) {
//                responseStrBuilder.append(inputStr);
//            }
//            streamReader.close();
//
//            String resp = responseStrBuilder.toString();
//            JSONObject mObject = new JSONObject(resp);
//
//            return mObject;
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//
//        return new JSONObject();
//    }
//
//
//}
