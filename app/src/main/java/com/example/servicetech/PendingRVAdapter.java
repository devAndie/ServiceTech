package com.example.servicetech;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseObject;

import java.util.List;

public class PendingRVAdapter extends RecyclerView.Adapter<PendingRVAdapter.ViewHolder>{

    private List<ParseObject> pendingList;
    private Context context;



    public PendingRVAdapter(List<ParseObject> pendingList, Context context) {
        this.pendingList = pendingList;
        this.context = context;
    }

    @NonNull
    @Override
    public PendingRVAdapter.ViewHolder onCreateViewHolder(
	@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_card_rv, parent, false);

        PendingRVAdapter.ViewHolder viewHolder = new PendingRVAdapter.ViewHolder(view);
		
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PendingRVAdapter.ViewHolder holder, int position) {
        final int itemPos = position;
			
        final ParseObject pendingEvent =  pendingList.get(position);

        holder.itemName.setText(pendingEvent.getString("Item"));
        holder.serviceType.setText(pendingEvent.getString("Service"));
        holder.place.setText(pendingEvent.getString("Location"));
		holder.note.setText(pendingEvent.getString("Note"));
		
		holder.imageView.setImageDrawable(Drawable.createFromPath(pendingEvent.getParseFile("Image").getUrl()));
    }

    @Override
    public int getItemCount() {
        return pendingList.size();
    }
	
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName, serviceType, place, note;
		ImageView imageView;

        public ViewHolder(View view) {
            super(view);

            itemName = view.findViewById(R.id.itemView);
            serviceType = view.findViewById(R.id.serviceView);
            place = view.findViewById(R.id.locationView);
            note  = view.findViewById(R.id.noteView);

            imageView = view.findViewById(R.id.photoView);
        }
    }
}
