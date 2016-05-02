package com.example.group2.pillpal;

import android.app.AlarmManager;
import android.app.DialogFragment;
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
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.ListFragment;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SetRemindersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SetRemindersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetRemindersFragment extends ListFragment {
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

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        getListView().setDivider(null);
//        String[] values = new String[] { "n10:30 AM", "n5:30 PM", "n6:00 AM", "f1:23 PM"};
//        AlarmArrayAdapter adapter = new AlarmArrayAdapter(getActivity(),values);
//        setListAdapter(adapter);
//    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO implement some logic
        TextView textView = (TextView) v.findViewById(R.id.label);
        ImageView imageView = (ImageView) v.findViewById(R.id.icon);
        DialogFragment newFragment = new TimePickerFragment(textView, imageView, position);
        newFragment.show(getActivity().getFragmentManager(), "timePicker");
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
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onActivityCreated(savedInstanceState);
        View v = inflater.inflate(R.layout.alarm_layout, container, false);
        View fab = v.findViewById(R.id.fab);
        ListView listView = (ListView) v.findViewById(android.R.id.list);
        listView.setDivider(null);
        String[] values = new String[] { "n10:30 AM", "n5:30 PM", "n6:00 AM", "f1:23 PM"};
        AlarmArrayAdapter adapter = new AlarmArrayAdapter(getActivity(),values);
        listView.setAdapter(adapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View rowView = inflater.inflate(R.layout.rowlayout, container, false);
                TextView textView = (TextView) rowView.findViewById(R.id.label);
                ImageView imageButton = (ImageView) rowView.findViewById(R.id.icon);
                ImageView delete = (ImageView) rowView.findViewById(R.id.delete);
                delete.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        rowView.setVisibility(View.GONE);
                    }
                });
                DialogFragment newFragment = new TimePickerFragment(textView, imageButton, 5);
                newFragment.show(getActivity().getFragmentManager(), "timePicker");
            }
        });
        return v;
    }

//    @Override
//    public void onClick(View v) {
        // implements your things
//        FragmentTransaction tx = getFragmentManager().beginTransaction();
//        ReminderSettingsFragment reminderSettingsFragment = new ReminderSettingsFragment();
//        tx.replace(R.id.main, reminderSettingsFragment);
//        tx.addToBackStack(null);
//        tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        tx.commit();
//
//        Intent sendIntent = new Intent(v.getContext(), AlarmReceiver.class);
//        sendIntent.putExtra("DATA", "reminder");
////        v.getContext().startService(sendIntent);
//        PendingIntent alarmIntent = PendingIntent.getBroadcast(v.getContext(), 0, sendIntent, 0);
//
//        AlarmManager alarmMgr = (AlarmManager)v.getContext().getSystemService(Context.ALARM_SERVICE);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 13);
//        calendar.set(Calendar.MINUTE, 33);
//
//        // With setInexactRepeating(), you have to use one of the AlarmManager interval
        // constants--in this case, AlarmManager.INTERVAL_DAY.
//        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
//
//        Toast.makeText(v.getContext(), "Alarm Set", Toast.LENGTH_LONG).show();
//    }
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
