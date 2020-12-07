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

public class StakesAdapter extends BaseAdapter {

    LayoutInflater  mInflator;
    Map<String, String> map;
    List<String> Details;
    List<String> Cost;

    public StakesAdapter(Context c, Map m){
        mInflator = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        map =m;
        Details = new ArrayList<String>(map.keySet());
        Cost = new ArrayList<String>(map.keySet());
    }
    @Override
    public int getCount() {
        return map.size();
    }

    @Override
    public Object getItem(int position) {
        return Details.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mInflator.inflate(R.layout.fragment_book, null);
        TextView details = (TextView) v.findViewById(R.id.detailsL);
        TextView status = (TextView) v.findViewById(R.id.costL);

        details.setText(Details.get(position));
        status.setText(Cost.get(position).toString());


        return v;
    }
}
