package com.example.group2.pillpal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SetRemindersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SetRemindersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetRemindersFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SetRemindersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SetRemindersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SetRemindersFragment newInstance(String param1, String param2) {
        SetRemindersFragment fragment = new SetRemindersFragment();
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
        View v = inflater.inflate(R.layout.fragment_set_reminders, container, false);
        ImageButton setupButton = (ImageButton) v.findViewById(R.id.set_reminders_button);
        setupButton.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        // implements your things
        FragmentTransaction tx = getFragmentManager().beginTransaction();
        ReminderSettingsFragment reminderSettingsFragment = new ReminderSettingsFragment();
        tx.replace(R.id.main, reminderSettingsFragment);
        tx.addToBackStack(null);
        tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        tx.commit();

        Intent sendIntent = new Intent(v.getContext(), PhoneToWatchService.class);
        sendIntent.putExtra("DATA", "reminder");
        PendingIntent alarmIntent = PendingIntent.getActivity(v.getContext(), 1, sendIntent, PendingIntent.FLAG_ONE_SHOT);

        AlarmManager alarmMgr = (AlarmManager)v.getContext().getSystemService(Context.ALARM_SERVICE);
        // Set the alarm to start at approximately 2:00 p.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis() + 10000);

        // With setInexactRepeating(), you have to use one of the AlarmManager interval
        // constants--in this case, AlarmManager.INTERVAL_DAY.
        alarmMgr.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);

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
