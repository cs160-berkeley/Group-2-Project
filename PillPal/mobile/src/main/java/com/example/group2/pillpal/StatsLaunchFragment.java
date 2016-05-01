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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StatsLaunchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StatsLaunchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatsLaunchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public StatsLaunchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatsLaunchFragment newInstance(String param1, String param2) {
        StatsLaunchFragment fragment = new StatsLaunchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

        try {
            StringBuilder response = readJSON();
            JSONArray hormones = new JSONArray(response.toString());
            ArrayList<Entry> estrogen = new ArrayList<>();
            final ArrayList<Entry> progestin = new ArrayList<>();
            final ArrayList<Entry> testosterone = new ArrayList<>();
            ArrayList<String> labels = new ArrayList<>();

            int e1 = 0;
            int e2 = 0;
            int p1 = 0;
            int p2 = 0;
            int t1 = 0;
            int t2 = 0;

            for (int i = 0; i < hormones.length(); i++) {
                JSONObject object = hormones.getJSONObject(i);
                String day = object.getString("day");
                String month = object.getString("month");
                int estrogenValue = object.getInt("estrogen");
                int progestinValue = object.getInt("progestin");
                int testosteroneValue = object.getInt("testosterone");
                estrogen.add(new Entry((float) estrogenValue, i));
                progestin.add(new Entry((float) progestinValue, i));
                testosterone.add(new Entry((float) testosteroneValue, i));
                if (i == 1) {
                    estrogenDiffDaily = estrogenValue - e1;
                    progestinDiffDaily = progestinValue - p1;
                    testosteroneDiffDaily = testosteroneValue - t1;
                } else if (i == 6) {
                    estrogenDiffWeekly = estrogenValue - (e1/6);
                    progestinDiffWeekly = progestinValue - (p1/6);
                    testosteroneDiffWeekly = testosteroneValue - (t1/6);
                } else if (i == hormones.length() - 1) {
                    estrogenDiffMonthly = estrogenValue - (e1/(hormones.length() - 1));
                    progestinDiffMonthly = progestinValue - (p1/(hormones.length() - 1));
                    testosteroneDiffMonthly = testosteroneValue - (t1/(hormones.length() - 1)
                    );
                }
                e1 += estrogenValue;
                p1 += progestinValue;
                t1 += testosteroneValue;
                labels.add(month + " " + day);
            }
        } catch (JSONException e) {
            Log.d("Error", e.toString());
        }
        Intent sendIntent = new Intent(getActivity(), PhoneToWatchService.class);
        sendIntent.putExtra("StatsValues", estrogenDiffDaily + "|" + progestinDiffDaily + "|" + testosteroneDiffDaily + "|" + estrogenDiffWeekly + "|" + progestinDiffWeekly + "|" + testosteroneDiffWeekly + "|" + estrogenDiffMonthly + "|" + progestinDiffMonthly + "|" + testosteroneDiffMonthly);
        getActivity().startService(sendIntent);
        return v;
    }

    public StringBuilder readJSON() {
        StringBuilder response = null;
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(getResources().openRawResource(getResources().getIdentifier("hormones", "raw", getActivity().getPackageName()))));
            response = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                response.append(line);
            }
            return response;
        } catch (IOException e) {
            Log.d("Error", e.toString());
            return response;
        }
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
