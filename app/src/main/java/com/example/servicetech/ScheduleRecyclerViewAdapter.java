package com.example.servicetech;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.core.view.Event;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ScheduleRecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public TextView name;
    public TextView place;
    public TextView startTime;

    public Button edit;
    public Button delete;

    private List<Event> eventsList;
    private Context context;
    private FirebaseFirestore firestoreDB;

    public ScheduleRecyclerViewAdapter(List<Event> list, Context ctx, FirebaseFirestore firestore) {
        eventsList = list;
        context = ctx;
        firestoreDB = firestore;
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_event_item, parent, false);

        ScheduleRecyclerViewAdapter.ViewHolder viewHolder =
                new ScheduleRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        /*name = view.findViewById(R.id.name_tv);
        place = view.findViewById(R.id.place_tv);
        startTime = view.findViewById(R.id.start_time_tv);

        edit = view.findViewById(R.id.edit_event_b);
        delete = view.findViewById(R.id.delete_event_b);
        
        final int itemPos = position;
        final Event event = eventsList.get(position);
        holder.name.setText(event.getName());
        holder.place.setText(event.getPlace());
        holder.startTime.setText("" + event.getStartTime());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editEventFragment(event);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEvent(event.getId(), itemPos);
            }
        });

         */

    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView place;
        public TextView startTime;

        public Button edit;
        public Button delete;

        public ViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.name_tv);
            place = view.findViewById(R.id.place_tv);
            startTime = view.findViewById(R.id.start_time_tv);

            edit = view.findViewById(R.id.edit_event_b);
            delete = view.findViewById(R.id.delete_event_b);
        }
    }
    private void editEventFragment(Event event){
        FragmentManager fm = ((TechnicianActivity)context).getSupportFragmentManager();
        Bundle bundle=new Bundle();
        bundle.putParcelable("event", (Parcelable) event);

        ScheduleFragment scheduleFragment = new ScheduleFragment();
        scheduleFragment.setArguments(bundle);

        fm.beginTransaction().replace(R.id.tech_container, scheduleFragment).commit();
    }
    private void deleteEvent(String docId, final int position){
        firestoreDB.collection("events").document(docId).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        eventsList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, eventsList.size());
                        Toast.makeText(context,
                                "Event document has been deleted",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }




}
