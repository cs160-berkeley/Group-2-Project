package com.example.group2.pillpal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by unzi on 5/4/16.
 */
public class RefillHistoryArrayAdapter extends ArrayAdapter<HashMap<String, String>> {
    private static UserInstance currentUser;

    public RefillHistoryArrayAdapter(Context context, ArrayList<HashMap<String, String>> entries) {
        super(context, R.layout.refill_entry, entries);
        currentUser = UserInstance.getInstance();
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View rowLayout = LayoutInflater.from(getContext()).inflate(R.layout.refill_entry, parent, false);
        HashMap<String, String> entry = getItem(position);

        TextView prescription_name = (TextView) rowLayout.findViewById(R.id.prescription_name);
        TextView prescription_date = (TextView) rowLayout.findViewById(R.id.prescription_date);
        prescription_name.setText(entry.get("name"));
        prescription_date.setText(entry.get("date"));

        return rowLayout;
    }

}

