package com.example.servicetech;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import static android.content.ContentValues.TAG;

public class ScheduleRecyclerViewAdapter extends
        RecyclerView.Adapter<ScheduleRecyclerViewAdapter.ViewHolder> implements View.OnClickListener{

    String docID;

    private List<Appointment> appointmentList;
    private Context context;
    private FirebaseFirestore firestoreDB;

    public ScheduleRecyclerViewAdapter(List<Appointment> list, Context ctx, FirebaseFirestore firestore) {
        appointmentList = list;
        context = ctx;
        firestoreDB = firestore;
    }

    @Override
    public void onClick(View v) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName, serviceType, place, startTime;
        Button update, attend;

        public ViewHolder(View view) {
            super(view);

            itemName = view.findViewById(R.id.name_tv);
            serviceType = view.findViewById(R.id.serviceType_tv);
            place = view.findViewById(R.id.place_tv);
            startTime = view.findViewById(R.id.start_time_tv);
            update = view.findViewById(R.id.edit_event_b);
            attend = view.findViewById(R.id.att_event);
        }

    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    @NonNull
    @Override
    public ScheduleRecyclerViewAdapter.ViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_event_item, parent, false);

        ScheduleRecyclerViewAdapter.ViewHolder viewHolder =
                new ScheduleRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final int itemPos = position;
        final Appointment appointment =  appointmentList.get(position);
        holder.itemName.setText(appointment.getItemName());
        holder.serviceType.setText(appointment.getService());
        holder.place.setText(appointment.getLocation());
        holder.startTime.setText(appointment.getStartTime());

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editEventFragment(appointment);
            }
        });

        holder.attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                docID = appointment.getId();
                FragmentManager fragMan = ((TechnicianActivity)context).getSupportFragmentManager();
                Bundle apptID = new Bundle();
                apptID.putString("appointmentId", docID);
                //Intent attend = new Intent()

                AppointmentFragment appointmentFragment = new AppointmentFragment();
                appointmentFragment.setArguments(apptID);


                attendEvent(appointment.getId(), itemPos);
            }
        });



    }

    private void editEventFragment(Appointment appointment){
        //db.collection('books').where('id', '==', 'fK3ddutEpD2qQqRMXNW5').get();
        //db.collection('books').where(firebase.firestore.FieldPath.documentId(),
        // '==', 'fK3ddutEpD2qQqRMXNW5').get();

        FragmentManager fm = ((TechnicianActivity)context).getSupportFragmentManager();
        Bundle bundle=new Bundle();
        bundle.putParcelable("event", (Parcelable) appointment);

        ScheduleFragment scheduleFragment = new ScheduleFragment();
        scheduleFragment.setArguments(bundle);

        fm.beginTransaction().replace(R.id.tech_container, scheduleFragment).commit();
    }

    private void attendEvent(String docId, final int position){




    }
    /*private void addDocumentToCollection(EventModel event){
        firestoreDB.collection("events")
                .add(event)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Event document added - id: "
                                + documentReference.getId());
                        restUi();
                        Toast.makeText(getActivity(),
                                "Event document has been added",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding event document", e);
                        Toast.makeText(getActivity(),
                                "Event document could not be added",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
     */


}
