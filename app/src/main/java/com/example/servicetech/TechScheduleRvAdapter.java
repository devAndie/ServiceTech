package com.example.servicetech;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


import com.parse.ParseObject;

import java.util.List;

public class TechScheduleRvAdapter extends
        RecyclerView.Adapter<TechScheduleRvAdapter.ViewHolder>{

    private List<ParseObject> techAptList;
    private Context context;

    public TechScheduleRvAdapter(List<ParseObject> techAptList, Context context) {
        this.techAptList = techAptList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName, serviceType, place, startTime;
        Button attend, deets;

        public ViewHolder(View view) {
            super(view);

            itemName = view.findViewById(R.id.name_tv);
            serviceType = view.findViewById(R.id.serviceType_tv);
            place = view.findViewById(R.id.place_tv);
            startTime = view.findViewById(R.id.start_time_tv);
            attend = view.findViewById(R.id.attendT);
            deets = view.findViewById(R.id.viewAptB);
        }

    }

    @Override
    public int getItemCount() {
        return techAptList.size();
    }

    @NonNull
    @Override
    public TechScheduleRvAdapter.ViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_event_item, parent, false);

        return new TechScheduleRvAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final int itemPos = position;
        final ParseObject appointment =  techAptList.get(position);
        holder.itemName.setText(appointment.getString("Item"));
        holder.serviceType.setText(appointment.getString("Service"));
        holder.place.setText(appointment.getString("Location"));
        holder.startTime.setText(appointment.getString("StartTime"));

        holder.attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attendAppointmentFragment(appointment);
            }
        });

        holder.deets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String docId = appointment.getObjectId();
                viewDeets(docId);
            }
        });
    }

    private void attendAppointmentFragment(ParseObject appointment){
        FragmentManager fm = ((TechnicianActivity)context).getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putParcelable("event", (Parcelable) appointment);

        TechAppointmentFragment attendApt = new TechAppointmentFragment();
        attendApt.setArguments(bundle);

        fm.beginTransaction().replace(R.id.tech_container, attendApt).commit();
    }

    private void viewDeets(String docId){
        FragmentManager fm = ((TechnicianActivity)context).getSupportFragmentManager();
        Bundle bundle=new Bundle();
        bundle.putString("docId", docId);

        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);


        fm.beginTransaction().replace(R.id.tech_container, detailsFragment).commit();
    }

}
