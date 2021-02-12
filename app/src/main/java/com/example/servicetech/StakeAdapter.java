package com.example.servicetech;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StakeAdapter extends BaseAdapter {
    String[] technician, rItems, description;
    LayoutInflater sInflater;

    public StakeAdapter(Context c, String[] technician, String[] rItems,String[] description) {
        this.technician = technician;
        this.rItems = rItems;
        this.description = description;
        sInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return technician.length;
    }

    @Override
    public Object getItem(int i) {
        return technician[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View v = sInflater.inflate(R.layout.events_layout, null);

       // TextView nametxt = v.findViewById(R.id.event_name_la);
       // TextView desctxt = v.findViewById(R.id.);

        String name = technician[i];
        String desc = description[i];

       // nametxt.setText(name);
       // desctxt.setText(desc);

        return v;
    }
}
