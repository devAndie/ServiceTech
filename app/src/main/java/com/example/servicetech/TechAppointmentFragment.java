package com.example.servicetech;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.parse.ParseObject;

public class TechAppointmentFragment extends Fragment {
    private TextInputEditText item, service, location, notes, recommendations, date, startTime;
    private ImageView itemPhoto;
    private String docId;
    Context context;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_appointment, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        item = view.findViewById(R.id.mitem_tv);
        service = view.findViewById(R.id.mitem_type_tv);
        itemPhoto = view.findViewById(R.id.mitemImg);
        location = view.findViewById(R.id.mlocat_tv);
        notes = view.findViewById(R.id.mnotes);
        recommendations = view.findViewById(R.id.mrecommend);
        date = view.findViewById(R.id.mdate);
        startTime = view.findViewById(R.id.mstime);

        context = getContext();

        ParseObject event = null;
        if (getArguments() != null) {
            event = getArguments().getParcelable("event");
        }
        if(event != null) {
            docId = event.getObjectId();
            item.setText(event.getString("Item"));
            service.setText(event.getString("Service"));
            location.setText(event.getString("Location"));
            notes.setText(event.getString("Note"));
            recommendations.setText(event.getString("Recommendation"));
            date.setText(event.getString("Date"));
            startTime.setText(event.getString("StartTime"));

            // Load the image using Glide
            Glide.with(this.context).load(event.getParseFile("Image").getUrl()).into(itemPhoto);
        }


    }

}
