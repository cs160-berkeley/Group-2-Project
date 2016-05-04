package com.example.group2.pillpal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.github.mikephil.charting.data.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class StatsLaunchFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public StatsLaunchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_stats_temp, container, false);
        ViewPager pager = (ViewPager) v.findViewById(R.id.pager);
        pager.setAdapter(new TestAdapter(getChildFragmentManager()));

        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) v.findViewById(R.id.tabs);
        tabs.setBackgroundColor(Color.WHITE);
        pager.setCurrentItem(0);
        tabs.setViewPager(pager);

        int estrogenDiffDaily = 0;
        int progestinDiffDaily = 0;
        int testosteroneDiffDaily = 0;

        int estrogenDiffWeekly = 0;
        int progestinDiffWeekly = 0;
        int testosteroneDiffWeekly = 0;

        int estrogenDiffMonthly = 0;
        int progestinDiffMonthly = 0;
        int testosteroneDiffMonthly = 0;

        UserInstance u = UserInstance.getInstance();
        ArrayList<User.statHolder> userStats = u.getStats();

        ArrayList<Entry> estrogen = new ArrayList<>();
        final ArrayList<Entry> progestin = new ArrayList<>();
        final ArrayList<Entry> testosterone = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        int e1, p1, t1, e2, p2, t2;
        int estFluctuationWeekly, estFluctuationMonthly;
        int proFluctuationWeekly, proFluctuationMonthly;
        int tesFluctuationWeekly, tesFluctuationMonthly;
        e1 = p1 = t1 = e2 = p2 = t2 = 0;
        estFluctuationWeekly = estFluctuationMonthly = 0;
        proFluctuationWeekly = proFluctuationMonthly = 0;
        tesFluctuationWeekly = tesFluctuationMonthly = 0;

        int num_stats = userStats.size();
        for (int i = 0; i < num_stats; i++) {
            User.statHolder stat = userStats.get(i);
            int day = stat.day;
            String month = stat.month;
            int estrogenValue = stat.est;
            int progestinValue = stat.pro;
            int testosteroneValue = stat.tes;
            estrogen.add(new Entry((float) estrogenValue, i));
            progestin.add(new Entry((float) progestinValue, i));
            testosterone.add(new Entry((float) testosteroneValue, i));

            if (i > 0 && i < 7) {
                estFluctuationWeekly += Math.abs(estrogenValue - e2);
                proFluctuationWeekly += Math.abs(progestinValue - p2);
                tesFluctuationWeekly += Math.abs(testosteroneValue - t2);
                estFluctuationMonthly += Math.abs(estrogenValue - e2);
                proFluctuationMonthly += Math.abs(progestinValue - p2);
                tesFluctuationMonthly += Math.abs(testosteroneValue - t2);
            } else if (i > 0) {
                estFluctuationMonthly += Math.abs(estrogenValue - e2);
                proFluctuationMonthly += Math.abs(progestinValue - p2);
                tesFluctuationMonthly += Math.abs(testosteroneValue - t2);
            }

            if (i == 1) {
                estrogenDiffDaily = estrogenValue - e1;
                progestinDiffDaily = progestinValue - p1;
                testosteroneDiffDaily = testosteroneValue - t1;
            } else if (i == 6) {
                estrogenDiffWeekly = estrogenValue - (e1/6);
                progestinDiffWeekly = progestinValue - (p1/6);
                testosteroneDiffWeekly = testosteroneValue - (t1/6);
            } else if (i == num_stats - 1) {
                estrogenDiffMonthly = estrogenValue - (e1/(num_stats - 1));
                progestinDiffMonthly = progestinValue - (p1/(num_stats - 1));
                testosteroneDiffMonthly = testosteroneValue - (t1/(num_stats - 1)
                );
            }
            e1 += estrogenValue;
            p1 += progestinValue;
            t1 += testosteroneValue;
            e2 = estrogenValue;
            p2 = progestinValue;
            t2 = testosteroneValue;
            labels.add(month + " " + day);
        }
        estFluctuationWeekly = estFluctuationWeekly/(6);
        proFluctuationWeekly = proFluctuationWeekly/(6);
        tesFluctuationWeekly = tesFluctuationWeekly/(6);
        estFluctuationMonthly = estFluctuationMonthly/(num_stats - 1);
        proFluctuationMonthly = proFluctuationMonthly/(num_stats - 1);
        tesFluctuationMonthly = tesFluctuationMonthly/(num_stats - 1);

        Intent sendIntent = new Intent(getActivity(), PhoneToWatchService.class);
        sendIntent.putExtra("StatsValues", estrogenDiffDaily + "|" + progestinDiffDaily + "|" + testosteroneDiffDaily + "|" + estrogenDiffWeekly + "|" + progestinDiffWeekly + "|" + testosteroneDiffWeekly + "|" + estrogenDiffMonthly + "|" + progestinDiffMonthly + "|" + testosteroneDiffMonthly);
        sendIntent.putExtra("fluctuations", estFluctuationWeekly + "|" + proFluctuationWeekly + "|" + tesFluctuationWeekly + "|" + estFluctuationMonthly + "|" + proFluctuationMonthly + "|" + tesFluctuationMonthly);
        getActivity().startService(sendIntent);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
