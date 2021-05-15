package com.example.servicetech;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ViewAppointmentItemRecyclerViewAdapter extends
        RecyclerView.Adapter<ViewAppointmentItemRecyclerViewAdapter.ViewHolder>
        implements View.OnClickListener{

    private List<Appointment> appointmentList;
    private Context context;
    private FirebaseFirestore firestoreDB;

    public ViewAppointmentItemRecyclerViewAdapter(List<Appointment> appointmentList,
                                                  Context context, FirebaseFirestore firestoreDB) {
        this.appointmentList = appointmentList;
        this.context = context;
        this.firestoreDB = firestoreDB;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName, serviceType, place, startTime;

        public ViewHolder(View view) {
            super(view);

            itemName = view.findViewById(R.id.name_tv);
            serviceType = view.findViewById(R.id.serviceType_tv);
            place = view.findViewById(R.id.place_tv);
            startTime = view.findViewById(R.id.start_time_tv);

        }

    }


    @Override
    public void onClick(View view) {

//        viewAppointment(appointment);
    }

    @NonNull
    @Override
    public ViewAppointmentItemRecyclerViewAdapter.ViewHolder onCreateViewHolder
            (@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_appointment_item, parent, false);

        ViewAppointmentItemRecyclerViewAdapter.ViewHolder viewHolder =
                new ViewAppointmentItemRecyclerViewAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAppointmentItemRecyclerViewAdapter.ViewHolder holder, int position) {

        final int itemPos = position;
        final Appointment appointment =  appointmentList.get(position);
        holder.itemName.setText(appointment.getItemName());
        holder.serviceType.setText(appointment.getService());
        holder.place.setText(appointment.getLocation());
        holder.startTime.setText(appointment.getStartTime());
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    private void viewAppointment(Appointment appointment){
        FragmentManager fm = ((TechnicianActivity)context).getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putParcelable("event", (Parcelable) appointment);

        BookingFragment bookingFragment = new BookingFragment();
        bookingFragment.setArguments(bundle);

        fm.beginTransaction().replace(R.id.tech_container, bookingFragment).commit();
    }
}
