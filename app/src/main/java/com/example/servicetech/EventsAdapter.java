package com.example.servicetech;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventsAdapter extends BaseAdapter {

    LayoutInflater  mInflator;
    Map<String, Double> map;
    List<String> Name;
    List<Double> Cost;

    public EventsAdapter(Context c, Map m){
        mInflator = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        map =m;
        Name = new ArrayList<String>(map.keySet());
        Cost = new ArrayList<Double>(map.values());
    }
    @Override
    public int getCount() {
        return map.size();
    }

    @Override
    public Object getItem(int position) {
        return Name.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mInflator.inflate(R.layout.fragment_book, null);
        TextView name = (TextView) v.findViewById(R.id.detailsL);
        TextView cost = (TextView) v.findViewById(R.id.costL);

        name.setText(Name.get(position));
        cost.setText("Kshs" + Cost.get(position).toString());


        return v;
    }
}
