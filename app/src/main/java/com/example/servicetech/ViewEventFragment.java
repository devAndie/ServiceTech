package com.example.servicetech;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;

public class ViewEventFragment extends Fragment {

    private TextView name, service, location, notes;
    ImageView itemPhoto;
    Context context;
    private String Name, type, place, imagePath, desc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.event_view_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        name = view.findViewById(R.id.nmtv);
        service = view.findViewById(R.id.serTv);
        itemPhoto = view.findViewById(R.id.evtImg);
        location = view.findViewById(R.id.locTv);
        notes = view.findViewById(R.id.notTv);

        EventModel event = null;
        if (getArguments() != null) {
            event = getArguments().getParcelable("event");
        }
        if(event != null){
            Name = event.getItemName();
            name.setText(Name);
            type = event.getService();
            service.setText(type);
            place = event.getLocation();
            location.setText(place);
            desc = event.getNote();
            notes.setText(desc);
            imagePath = event.getImageURL();

//            submit.setText("Book");
        }
        // Load the image using Glide
        Glide.with(this)
                .load(imagePath)
                .into(itemPhoto);


        Button back = view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = ((HomeActivity)context).getSupportFragmentManager();
                PendingFragment pendingFragment = new PendingFragment();
                fm.beginTransaction().replace(R.id.fragment_container, pendingFragment).commit();

            }
        });
    }

}
