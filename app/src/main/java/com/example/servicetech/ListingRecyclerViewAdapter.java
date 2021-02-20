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

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ListingRecyclerViewAdapter extends
        RecyclerView.Adapter<ListingRecyclerViewAdapter.ViewHolder> {

    private List<EventModel> listingList;
    Context context;
    private FirebaseFirestore firestoreDB;
    private String docId;

    public ListingRecyclerViewAdapter(List<EventModel> listingList, Context context, FirebaseFirestore firestoreDB) {
        this.listingList = listingList;
        this.context = context;
        this.firestoreDB = firestoreDB;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName, serviceType, place;
        Button book;

        public ViewHolder(View view) {
            super(view);

            itemName = view.findViewById(R.id.iTname_tv);
            serviceType = view.findViewById(R.id.service_tv);
            place = view.findViewById(R.id.loc_tv);

            book = view.findViewById(R.id.book);

        }
    }

    @Override
    public int getItemCount() {
        return listingList.size();
    }

    @NonNull
    @Override
    public ViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listing_item, parent, false);
        ListingRecyclerViewAdapter.ViewHolder viewHolder =
                 new ListingRecyclerViewAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final int itemPos = position;
        EventModel event = listingList.get(position);
        holder.itemName.setText(event.getItemName());
        holder.serviceType.setText(event.getService());
        holder.place.setText(event.getLocation());

        holder.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bookEvent(event);

            }
        });

    }
    private void bookEvent(EventModel event){
        docId = event.getId();
        FragmentManager fm = ((TechnicianActivity)context).getSupportFragmentManager();

        Bundle bundle=new Bundle();
        bundle.putString("eventID", docId);

        BookingFragment createSchedule = new BookingFragment();
        createSchedule.setArguments(bundle);

        fm.beginTransaction().replace(R.id.tech_container, createSchedule).commit();
    }
}
