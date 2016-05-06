package com.example.group2.pillpal;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageButton;

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
 * {@link StatFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public StatFragment() {
        // Required empty public constructor
    }
    public static StatFragment newInstance(String hormone, String period, String difference, String status) {
        StatFragment fragment = new StatFragment();
        Bundle args = new Bundle();
        args.putString("hormone", hormone);
        args.putString("period", period);
        args.putString("difference", difference);
        args.putString("status", status);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        View v = inflater.inflate(R.layout.fragment_stat, container, false);
        TextView hormone = (TextView) v.findViewById(R.id.hormone);
        TextView period = (TextView) v.findViewById(R.id.period);
        TextView difference = (TextView) v.findViewById(R.id.difference);
        TextView status = (TextView) v.findViewById(R.id.status);

        String hormoneString = getArguments().getString("hormone", "");
        String periodString = getArguments().getString("period", "");
        String differenceString = getArguments().getString("difference", "");
        String statusString = getArguments().getString("status", "");

        Log.d("T", "MADE: " + hormoneString + periodString + differenceString + statusString);

        if (hormoneString.equalsIgnoreCase("Estrogen")) {
            difference.setTextColor(Color.rgb(68, 138, 255));
        } else if (hormoneString.equalsIgnoreCase("Progestin")) {
            difference.setTextColor(Color.rgb(255, 87, 34));
        } else if (hormoneString.equalsIgnoreCase("Testosterone")) {
            difference.setTextColor(Color.rgb(0,230,118));
        }

        hormone.setText(hormoneString);
        period.setText(periodString);
        difference.setText(differenceString);
        status.setText(statusString);
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
//            throw new RuntimeException(context.toString());
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
