package com.example.group2.pillpal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Josh on 4/29/2016.
 */
public class AlarmArrayAdapter extends ArrayAdapter<Alarm> {

    private static UserInstance currentUser;

    public AlarmArrayAdapter(Context context, ArrayList<Alarm> alarms) {
        super(context, R.layout.rowlayout, alarms);
        currentUser = UserInstance.getInstance();
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View rowLayout = LayoutInflater.from(getContext()).inflate(R.layout.rowlayout, parent, false);
        Alarm alarm = getItem(position);
        TextView alarm_time = (TextView) rowLayout.findViewById(R.id.alarm_time);
        TextView day_time = (TextView) rowLayout.findViewById(R.id.day_time);
        alarm_time.setText(alarm.timeStringFormat);
        day_time.setText(alarm.timeOfDay);

        final ImageView imageButton = (ImageView) rowLayout.findViewById(R.id.alarm_icon);
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Alarm alarm = getItem(position);
                if (alarm.isOn) {
                    // turn alarm off
                    alarm.isOn = false;
                    // actually turn the alarm off in the calendar..
                    imageButton.setImageResource(R.drawable.ic_alarm_off_24dp);
                } else {
                    // turn alarm on
                    alarm.isOn = true;
                    imageButton.setImageResource(R.drawable.ic_alarm_on_24dp);

                    Intent sendIntent = new Intent(getContext(), AlarmReceiver.class);

                    sendIntent.putExtra("DATA", alarm.timeStringFormat);
                    PendingIntent alarmIntent = PendingIntent.getBroadcast(getContext(), position, sendIntent, 0);

                    AlarmManager alarmMgr = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.HOUR_OF_DAY, alarm.hours);
                    calendar.set(Calendar.MINUTE, alarm.minutes);

                    alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
                }

            }
        });
        ImageView delete = (ImageView) rowLayout.findViewById(R.id.delete_alarm);
        DeleteAlarmListener delete_alarm_listener = new DeleteAlarmListener(this, position);
        delete.setOnClickListener(delete_alarm_listener);

        return rowLayout;
    }

    private class DeleteAlarmListener implements View.OnClickListener {

        private AlarmArrayAdapter mAdapter;
        private int position;

        public DeleteAlarmListener(AlarmArrayAdapter adapter, int position) {
            mAdapter = adapter;
            this.position = position;
        }
        @Override
        public void onClick(View v) {
            Alarm alarm = getItem(position);
            currentUser.deleteAlarm(alarm);
            mAdapter.notifyDataSetChanged();
        }
    }
}
