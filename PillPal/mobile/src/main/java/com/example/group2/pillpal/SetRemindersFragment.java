package com.example.group2.pillpal;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
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

    private OnFragmentInteractionListener mListener;

//    private static ArrayList<Alarm> alarm_values;
    private static UserInstance currentUser;
    private static ArrayAdapter<Alarm> mAdapter;

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
        fragment.setArguments(args);
        currentUser = UserInstance.getInstance();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = UserInstance.getInstance();
    }

    @Override
    public void onClick(View v) {
        TimePickerDialog.OnTimeSetListener mTimeSetListener =
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(android.widget.TimePicker view,
                                          int hourOfDay, int minute) {
                        System.out.println(hourOfDay);
                        System.out.println(minute);
                        Alarm alarm = new Alarm(hourOfDay, minute, true);

                        AlarmManager alarmMgr = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.set(Calendar.HOUR_OF_DAY, alarm.hours);
                        calendar.set(Calendar.MINUTE, alarm.minutes);
                        Intent sendIntent = new Intent(getContext(), PhoneToWatchService.class);
                        sendIntent.putExtra("DATA", "reminder");
                        sendIntent.putExtra("time", alarm.timeStringFormat + alarm.timeOfDay);
                        System.out.println(alarm.timeStringFormat + alarm.timeOfDay);
                        PendingIntent alarmIntent = PendingIntent.getService(getContext(), 0, sendIntent, PendingIntent.FLAG_ONE_SHOT);
                        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
                        System.out.println(calendar.getTime());
                        currentUser.addAlarm(alarm);
                        mAdapter.notifyDataSetChanged();
                    }
                };
        TimePickerFragment newFragment = new TimePickerFragment(mTimeSetListener);
        newFragment.show(getActivity().getFragmentManager(), "timePicker");
        Alarm new_alarm = newFragment.alarm;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onActivityCreated(savedInstanceState);
        View v = inflater.inflate(R.layout.alarm_layout, container, false);

        View newAlarmButton = v.findViewById(R.id.fab);
        newAlarmButton.setOnClickListener(this);

        ListView listView = (ListView) v.findViewById(android.R.id.list);
        mAdapter = new AlarmArrayAdapter(getActivity(), currentUser.getAlarms());
        listView.setAdapter(mAdapter);

        return v;
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
