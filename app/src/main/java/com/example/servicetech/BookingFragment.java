package com.example.servicetech;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;

import com.google.android.material.textfield.TextInputEditText;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class BookingFragment extends Fragment {
    private static final String TAG = "BookingFragment";
    private TextInputEditText item, service, location, notes, recommendations, date, startTime;
    private String docId;
    private String name;
    private String type;
    private String place;
    private String desc;
    private ParseFile image;
    private String techId;
    private String techRec;
    private String time;
    private String Date;
    private ImageView itemPhoto;
    private Context context;
    boolean isEdit;
    Button submit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        item = view.findViewById(R.id.item_tv);
        service = view.findViewById(R.id.item_type_tv);
        itemPhoto = view.findViewById(R.id.itemImg);
        location = view.findViewById(R.id.locat_tv);
        notes = view.findViewById(R.id.notes);
        recommendations = view.findViewById(R.id.recommend);
        date = view.findViewById(R.id.date);
        startTime = view.findViewById(R.id.stime);
        submit = view.findViewById(R.id.schedule);

        techRec = recommendations.getText().toString();
        time = startTime.getText().toString();
        Date = date.getText().toString();

        ParseObject event = null;
        if (getArguments() != null) {
            event = getArguments().getParcelable("event");
        }
        if(event != null){
            docId = event.getObjectId();
            name = event.getString("Item");
            item.setText(name);
            type = event.getString("Service");
            service.setText(type);
            place = event.getString("Location");
            location.setText(place);
            desc = event.getString("Note");
            notes.setText(desc);
            image = event.getParseFile("Image");

            submit.setText("Book");

        }

        // Load the image using Glide
        Glide.with(this)
                .load(image)
                .into(itemPhoto);

//      sync data

//set button invincible

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recommendations.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please type Your Official Names",
                            Toast.LENGTH_SHORT).show();

                }else if(date.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please type a valid email",
                            Toast.LENGTH_SHORT).show();

                }else if(startTime.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Please type a password",
                            Toast.LENGTH_SHORT).show();
                }else {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("events");

                    query.getInBackground(docId, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            if (e == null ) {
                                String status = object.getString("Status");
                                if (status == "Pending"){
                                    object.put("Status", "Picked");
                                    object.put("Reccommendation", techRec);
                                    object.put("Date", Date);
                                    object.put("Time", time);

                                    object.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            Toast.makeText(getContext(), "Appointment booked successfully",
                                                    Toast.LENGTH_SHORT).show();

                                            viewSchedule();
                                        }
                                    });
                                }else {
                                    Toast.makeText(getContext(), "Event arleady picked!" +
                                                    "Return to Listing",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    private void viewSchedule() {
        FragmentManager fm = ((TechnicianActivity)context).getSupportFragmentManager();
        ScheduleFragment scheduleFragment = new ScheduleFragment();

        fm.beginTransaction().replace(R.id.tech_container, scheduleFragment).commit();
    }

}