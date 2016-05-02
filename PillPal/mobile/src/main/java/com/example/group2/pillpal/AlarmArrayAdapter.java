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
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Josh on 4/29/2016.
 */
public class AlarmArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public AlarmArrayAdapter(Context context, String[] values) {
        super(context, R.layout.rowlayout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
        final TextView textView = (TextView) rowView.findViewById(R.id.label);
        final ImageView imageButton = (ImageView) rowView.findViewById(R.id.icon);
        ImageView delete = (ImageView) rowView.findViewById(R.id.delete);
        textView.setText(values[position].substring(1));
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rowView.setVisibility(View.GONE);
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if ('n' == values[position].charAt(0)) {
                    imageButton.setImageResource(R.drawable.ic_alarm_off_black_36dp);
                    values[position] = "f" + values[position].substring(1);
                    Toast.makeText(getContext(), "Alarm Off", Toast.LENGTH_LONG).show();
                } else if ('f' == values[position].charAt(0)) {
                    String t = values[position].substring(1, values[position].length() - 3);
                    String[] time = t.split(":");
                    int hour = Integer.parseInt(time[0]);
                    int min = Integer.parseInt(time[1]);
                    imageButton.setImageResource(R.drawable.ic_alarm_on_black_36dp);
                    values[position] = "n" + values[position].substring(1);
                    Toast.makeText(getContext(), "Alarm Set", Toast.LENGTH_LONG).show();

                    Intent sendIntent = new Intent(getContext(), AlarmReceiver.class);
                    sendIntent.putExtra("DATA", values[position].substring(1));
                    PendingIntent alarmIntent = PendingIntent.getBroadcast(getContext(), position, sendIntent, 0);

                    AlarmManager alarmMgr = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, min);

                    // With setInexactRepeating(), you have to use one of the AlarmManager interval
                    // constants--in this case, AlarmManager.INTERVAL_DAY.
                    alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
                }
            }
        });
        // Change the icon for Windows and iPhone
        if ('n' == values[position].charAt(0)) {
            imageButton.setImageResource(R.drawable.ic_alarm_on_black_36dp);

//            String t = values[position].substring(1, values[position].length() - 3);
//            String[] time = t.split(":");
//            int hour = Integer.parseInt(time[0]);
//            int min = Integer.parseInt(time[1]);
//
//            Intent sendIntent = new Intent(getContext(), AlarmReceiver.class);
//            sendIntent.putExtra("DATA", values[position].substring(1));
//            PendingIntent alarmIntent = PendingIntent.getBroadcast(getContext(), position, sendIntent, 0);
//
//            AlarmManager alarmMgr = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
//
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTimeInMillis(System.currentTimeMillis());
//            calendar.set(Calendar.HOUR_OF_DAY, hour);
//            calendar.set(Calendar.MINUTE, min);
//
//            // With setInexactRepeating(), you have to use one of the AlarmManager interval
//            // constants--in this case, AlarmManager.INTERVAL_DAY.
//            alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
        } else {
            imageButton.setImageResource(R.drawable.ic_alarm_off_black_36dp);
        }

        return rowView;
    }
}
