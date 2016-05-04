package com.example.group2.pillpal;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class WeeklyStatsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    int[] mColors = new int[] {
            Color.rgb(68,138,255), Color.rgb(255, 112, 67), Color.rgb(0, 230, 118)
    };

    public WeeklyStatsFragment() {
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
        View v = inflater.inflate(R.layout.stats_fragment, container, false);
        TextView changesTitle = (TextView) v.findViewById(R.id.changesTitle);
        TextView estrogenLabel = (TextView) v.findViewById(R.id.estrogenValue);
        TextView progestinLabel = (TextView) v.findViewById(R.id.progestinValue);
        TextView testosteroneLabel = (TextView) v.findViewById(R.id.testosteroneValue);
        final TextView status = (TextView) v.findViewById(R.id.status);
        changesTitle.setText("Weekly Changes");
        UserInstance u = UserInstance.getInstance();
        ArrayList<User.statHolder> userStats = u.getStats();

        ArrayList<Entry> estrogen = new ArrayList<>();
        ArrayList<Entry> progestin = new ArrayList<>();
        ArrayList<Entry> testosterone = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        int e1, p1, t1, e2, p2, t2, estFluctuation, proFluctuation, tesFluctuation;
        e1 = p1 = t1 = e2 = p2 = t2 = estFluctuation = proFluctuation = tesFluctuation = 0;

        int num_stats = 7;
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
            if (i > 0) {
                estFluctuation += Math.abs(estrogenValue - e2);
                proFluctuation += Math.abs(progestinValue - p2);
                tesFluctuation += Math.abs(testosteroneValue - t2);
            }
            e2 = estrogenValue;
            p2 = progestinValue;
            t2 = testosteroneValue;
            if (i != num_stats - 1) {
                e1 += estrogenValue;
                p1 += progestinValue;
                t1 += testosteroneValue;
            }
            labels.add(month + " " + day);
        }

        e1 = e1/(num_stats - 1);
        p1 = p1/(num_stats - 1);
        t1 = t1/(num_stats - 1);
        estFluctuation = estFluctuation/(num_stats - 1);
        proFluctuation = proFluctuation/(num_stats - 1);
        tesFluctuation = tesFluctuation/(num_stats - 1);

        final int estrogenDiff = e2 - e1;
        final int progestinDiff = p2 - p1;
        final int testosteroneDiff = t2 - t1;

        String estrogenText = hormoneText(estrogenDiff);
        String progestinText = hormoneText(progestinDiff);
        String testosteroneText = hormoneText(testosteroneDiff);
        estrogenLabel.setText(estrogenText);
        progestinLabel.setText(progestinText);
        testosteroneLabel.setText(testosteroneText);

        if (estFluctuation > 30 || proFluctuation > 30 || tesFluctuation > 30) {
            status.setText(u.prescription + " doesn't seem to be a good fit for you. Consider changing your pill or visiting a doctor.");
        } else {
            status.setText("You're on track with " + u.prescription + "! Hormone levels for the week are looking great!");
        }

        setupChart(estrogen, progestin, testosterone, labels, v);
        return v;
    }

    public String hormoneText(int hormone) {
        return hormone > 0 ? "+" + hormone + "%" : hormone + "%";
    }

    public void setupChart(ArrayList<Entry> estrogen, ArrayList<Entry> progestin, ArrayList<Entry> testosterone, ArrayList<String> labels, View v) {
        LineDataSet estrogenDataset = new LineDataSet(estrogen, "Estrogen");
        estrogenDataset.setLineWidth(1.5f);
        estrogenDataset.setDrawValues(false);
        estrogenDataset.setDrawCircles(false);
        estrogenDataset.setDrawCubic(true);
        estrogenDataset.setColor(mColors[0]);
        estrogenDataset.setFillAlpha(65);
        estrogenDataset.setDrawFilled(true);
        estrogenDataset.setFillColor(mColors[0]);

        LineDataSet progestinDataset = new LineDataSet(progestin, "Progestin");
        progestinDataset.setLineWidth(1.5f);
        progestinDataset.setDrawValues(false);
        progestinDataset.setDrawCircles(false);
        progestinDataset.setDrawCubic(true);
        progestinDataset.setColor(mColors[1]);
        progestinDataset.setFillAlpha(65);
        progestinDataset.setDrawFilled(true);
        progestinDataset.setFillColor(mColors[1]);

        LineDataSet testosteroneDataset = new LineDataSet(testosterone, "Testosterone");
        testosteroneDataset.setLineWidth(1.5f);
        testosteroneDataset.setDrawValues(false);
        testosteroneDataset.setDrawCircles(false);
        testosteroneDataset.setDrawCubic(true);
        testosteroneDataset.setColor(mColors[2]);
        testosteroneDataset.setFillAlpha(65);
        testosteroneDataset.setDrawFilled(true);
        testosteroneDataset.setFillColor(mColors[2]);

        ArrayList<ILineDataSet> dataSets = new ArrayList();
        dataSets.add(estrogenDataset);
        dataSets.add(progestinDataset);
        dataSets.add(testosteroneDataset);

        LineData data = new LineData(labels, dataSets);
        LineChart lineChart = (LineChart) v.findViewById(R.id.chart);
        lineChart.setData(data);
        lineChart.animateY(1000);
        lineChart.setDescription("");
        lineChart.getAxisRight().setEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(true);
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setDrawAxisLine(true);
        lineChart.invalidate();
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
