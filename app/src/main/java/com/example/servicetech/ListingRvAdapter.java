package com.example.servicetech;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
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

public class ListingRvAdapter extends
        RecyclerView.Adapter<ListingRvAdapter.ViewHolder> {

    private List<ParseObject> listingList;
    private Context context;

    public ListingRvAdapter(Context context, List<ParseObject> listingList) {
        this.context = context;
        this.listingList = listingList;
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
                .inflate(R.layout.event_card_rv, parent, false);

        return new ListingRvAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final int itemPos = position;
        ParseObject event = listingList.get(position);
        holder.itemName.setText(event.getString("Item"));
        holder.serviceType.setText(event.getString("Service"));
        holder.place.setText(event.getString("Location"));

        Glide.with(context).load(event.getParseFile("Image").getUrl()).into(holder.itemPhoto);

        String itemId = event.getObjectId();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName, serviceType, place, note;
        public ImageView itemPhoto;


        public ViewHolder(View view) {
            super(view);

            itemPhoto = view.findViewById(R.id.photoView);
            itemName = view.findViewById(R.id.itemView);
            serviceType = view.findViewById(R.id.serviceView);
            place = view.findViewById(R.id.locationView);
            note = view.findViewById(R.id.noteView);


            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    ParseObject event = new ParseObject("events");
                    FragmentManager fm = ((TechnicianActivity)context).getSupportFragmentManager();


                    Bundle bundle = new Bundle();
                    bundle.putParcelable("event", event);
                    BookingFragment createSchedule = new BookingFragment();
                    createSchedule.setArguments(bundle);

                    fm.beginTransaction().replace(R.id.tech_container, createSchedule).commit();
                }
            });
        }
    }
}
