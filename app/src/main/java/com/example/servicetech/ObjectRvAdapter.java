package com.example.servicetech;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseObject;

import java.util.List;

public class ObjectRvAdapter extends RecyclerView.Adapter<ObjectRvAdapter.ViewHolder>{

    private List<ParseObject> objectsList;
    private Context context;

    public ObjectRvAdapter(List<ParseObject> objectsList, Context context) {
        this.objectsList = objectsList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName, status, serviceType, place, tecRec, date, time;
        public ImageView imageView;
        Button deets;

        public ViewHolder(View view) {
            super(view);

            itemName = view.findViewById(R.id.itemObj);
            status = view.findViewById(R.id.statusObj);
            serviceType = view.findViewById(R.id.serviceObj);
            place = view.findViewById(R.id.locationObj);
            date =view.findViewById(R.id.dateObj);
            time = view.findViewById(R.id.timeObj);

            imageView = view.findViewById(R.id.photoObj);

            deets = view.findViewById(R.id.deets);
        }
    }

    @NonNull
    @Override
    public ObjectRvAdapter.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.object_card, parent, false);


        return new ObjectRvAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ObjectRvAdapter.ViewHolder holder, int position) {
        final int itemPos = position;

        final ParseObject object =  objectsList.get(itemPos);

        holder.itemName.setText(object.getString("Item"));
        holder.serviceType.setText(object.getString("Service"));
        holder.place.setText(object.getString("Location"));
        holder.status.setText(object.getString("Status"));
        holder.date.setText(object.getString("Date"));
        holder.time.setText(object.getString("Time"));

        Glide.with(context).load(object.getParseFile("Image").getUrl()).into(holder.imageView);

        holder.deets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String docId = object.getObjectId();

                viewDetails(docId);
            }
        });

    }

    @Override
    public int getItemCount() {
        return objectsList.size();
    }

    private void viewDetails(String docId) {
        FragmentManager fm = ((TechnicianActivity)context).getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString("docId", docId);

        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);

        fm.beginTransaction().replace(R.id.tech_container, detailsFragment).commit();

    }

}
