package com.example.servicetech;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListingAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    String[] items, cost, description;

    public ListingAdapter(Context c, String[] items, String[] cost, String[] description) {
        this.items = items;
        this.cost = cost;
        this.description = description;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int i) {
        return items[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View View, ViewGroup viewGroup) {
        View v = mInflater.inflate(R.layout.listing_item, null);
        TextView nametxt = v.findViewById(R.id.detailsL);
        TextView desctxt = v.findViewById(R.id.descr);
        TextView pricetxt = v.findViewById(R.id.costL);

        String name = items[i];
        String desc = description[i];
        String Cost = cost[i];

        nametxt.setText(name);
        desctxt.setText(desc);
        pricetxt.setText("Kshs" + Cost);

        return v;
    }
}
