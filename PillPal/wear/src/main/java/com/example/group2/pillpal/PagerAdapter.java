package com.example.group2.pillpal;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentGridPagerAdapter {

    private final Context mContext;
    private ArrayList<Row> mPages;
    private ArrayList<StatsItem> StatsPages = new ArrayList<>();

    public PagerAdapter(Context context, FragmentManager fm, String StatsValues) throws JSONException {
        super(fm);
        mContext = context;
        initPages(StatsValues);
    }

    private void initPages(String StatsValues) {
        try {
            mPages = new ArrayList<>();
            parseResult(StatsValues);
            Row row1 = new Row();
            for (int i = 0; i < 3; i++) {
                row1.addPages(StatsPages.get(i));
            }
            Row row2 = new Row();
            for (int i = 3; i < 6; i++) {
                row2.addPages(StatsPages.get(i));
            }
            Row row3 = new Row();
            for (int i = 6; i < 9; i++) {
                row3.addPages(StatsPages.get(i));
            }
            mPages.add(row1);
            mPages.add(row2);
            mPages.add(row3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseResult(String StatsValues) {
        ArrayList<String> results = new ArrayList<>();
        for (String word: StatsValues.split("\\|")) {
            results.add(word);
        }

        String[] hormones = new String[3];
        hormones[0] = "Estrogen";
        hormones[1] = "Progestin";
        hormones[2] = "Testosterone";

        for (int i = 0; i < 9; i ++) {
            if (i < 3) {
                StatsPages.add(new StatsItem(hormones[i % 3], "Daily", results.get(i), Math.abs(Integer.parseInt(results.get(i))) > 20 ? "Abnormal" : "Normal"));
            } else if (i < 6) {
                StatsPages.add(new StatsItem(hormones[i % 3], "Weekly", results.get(i), Math.abs(Integer.parseInt(results.get(i))) > 20 ? "Abnormal" : "Normal"));
            } else if (i < 9) {
                StatsPages.add(new StatsItem(hormones[i % 3], "Monthly", results.get(i), Math.abs(Integer.parseInt(results.get(i))) > 20 ? "Abnormal" : "Normal"));
            }
        }
    }

    @Override
    public Fragment getFragment(int row, int col) {
        StatsItem page = (mPages.get(row)).getPages(col);
        StatFragment fragment = StatFragment.newInstance(page.getHormone(), page.getPeriod(), page.getDifference(), page.getStatus());
        return fragment;
    }

    @Override
    public int getRowCount() {
        return mPages.size();
    }

    @Override
    public int getColumnCount(int row) {
        return mPages.get(row).size();
    }
}