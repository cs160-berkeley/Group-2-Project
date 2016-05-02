package com.example.group2.pillpal;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {
    public TextView textView;
    public ImageView imageView;
    public int p;

    public TimePickerFragment() {
    }

    public TimePickerFragment(TextView v, ImageView i, int pos) {
        textView = v;
        imageView = i;
        p = pos;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(new ContextThemeWrapper(getActivity(), R.style.Dialog), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user

        imageView.setImageResource(R.drawable.ic_alarm_on_black_36dp);

        String hour = String.valueOf(hourOfDay%12);
        String time = "";
        if (hourOfDay > 11) {
            time = "PM";
        } else {
            time = "AM";
        }
        if (hourOfDay == 12 || hourOfDay == 0){
            hour = "12";
        }
        String min = String.valueOf(minute);
        if (minute < 10) {
            min = "0" + min;
        }
        textView.setText(hour + ":" + min + " " + time);


        Intent sendIntent = new Intent(view.getContext(), AlarmReceiver.class);
        sendIntent.putExtra("DATA", hour + ":" + min + " " + time);
        //        v.getContext().startService(sendIntent);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(view.getContext(), p, sendIntent, 0);

        AlarmManager alarmMgr = (AlarmManager) view.getContext().getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        // With setInexactRepeating(), you have to use one of the AlarmManager interval
        // constants--in this case, AlarmManager.INTERVAL_DAY.
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);

//        Toast.makeText(view.getContext(), "Alarm Set", Toast.LENGTH_LONG).show();
    }
}