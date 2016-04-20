package com.example.group2.pillpal;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StatsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatsFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public StatsFragment() {
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
    public static StatsFragment newInstance(String param1, String param2) {
        StatsFragment fragment = new StatsFragment();
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.stats_fragment, container, false);

        try {
            StringBuilder response = readJSON();
            JSONArray hormones = new JSONArray(response.toString());
            ArrayList<Entry> estrogen = new ArrayList<>();
            ArrayList<Entry> progestin = new ArrayList<>();
            ArrayList<Entry> testosterone = new ArrayList<>();
            ArrayList<String> labels = new ArrayList<>();

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
                labels.add(month + " " + day);
            }

            int[] mColors = new int[] {
                    Color.rgb(255, 87, 24), Color.rgb(68, 138, 255), Color.rgb(0, 230, 118)
            };

            LineDataSet estrogenDataset = new LineDataSet(estrogen, "Estrogen");
            estrogenDataset.setLineWidth(1.5f);
            estrogenDataset.setDrawValues(false);
            estrogenDataset.setDrawCircles(false);
            estrogenDataset.setColor(mColors[0]);
            estrogenDataset.setDrawCubic(true);

            LineDataSet progestinDataset = new LineDataSet(progestin, "Progestin");
            progestinDataset.setLineWidth(1.5f);
            progestinDataset.setDrawValues(false);
            progestinDataset.setDrawCircles(false);
            progestinDataset.setColor(mColors[1]);

            LineDataSet testosteroneDataset = new LineDataSet(testosterone, "Testosterone");
            testosteroneDataset.setLineWidth(1.5f);
            testosteroneDataset.setDrawValues(false);
            testosteroneDataset.setDrawCircles(false);
            testosteroneDataset.setColor(mColors[2]);

            ArrayList<ILineDataSet> dataSets = new ArrayList();
            dataSets.add(estrogenDataset);
            dataSets.add(progestinDataset);
            dataSets.add(testosteroneDataset);

            LineData data = new LineData(labels, dataSets);
            LineChart lineChart = (LineChart) v.findViewById(R.id.chart);
            lineChart.setData(data);
            lineChart.setDescription("Hormone Levels");
            lineChart.animateY(1000);
            lineChart.getAxisRight().setEnabled(false);

            XAxis xAxis = lineChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawAxisLine(true);
            YAxis yAxis = lineChart.getAxisLeft();
            yAxis.setDrawAxisLine(true);
            lineChart.invalidate();
        } catch (JSONException e) {
            Log.d("Error", e.toString());
        }
        ImageButton statsButton = (ImageButton) v.findViewById(R.id.stats_button);
        statsButton.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        // implements your things
        LineChart lineChart = (LineChart) getActivity().findViewById(R.id.chart);
        ImageButton statsButton = (ImageButton) v.findViewById(R.id.stats_button);
        statsButton.setVisibility(View.INVISIBLE);
        lineChart.setVisibility(View.VISIBLE);
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
