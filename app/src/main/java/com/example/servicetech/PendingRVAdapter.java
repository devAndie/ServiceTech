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

import java.util.List;

public class PendingRVAdapter extends RecyclerView.Adapter<PendingRVAdapter.ViewHolder> implements View.OnClickListener{

    private List<EventModel> pendingList;
    private Context context;

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

    public PendingRVAdapter(List<EventModel> pendingList, Context context) {
        this.pendingList = pendingList;
        this.context = context;
    }

    @NonNull
    @Override
    public PendingRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_appointment_item, parent, false);

        PendingRVAdapter.ViewHolder viewHolder = new PendingRVAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PendingRVAdapter.ViewHolder holder, int position) {
        final int itemPos = position;
        final EventModel pendingEvent =  pendingList.get(position);
        holder.itemName.setText(pendingEvent.getItemName());
        holder.serviceType.setText(pendingEvent.getService());
        holder.place.setText(pendingEvent.getLocation());

    }

    @Override
    public void onClick(View v) {
//        passBundle();
    }

    private EventModel passBundle(EventModel eventModel) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("event", (Parcelable) eventModel);

        FragmentManager fm = ((HomeActivity)context).getSupportFragmentManager();
        ViewEventFragment viewEventFragment = new ViewEventFragment();
        fm.beginTransaction().replace(R.id.fragment_container, viewEventFragment).commit();

        return null;
    }



    @Override
    public int getItemCount() {
        return 0;
    }
}
