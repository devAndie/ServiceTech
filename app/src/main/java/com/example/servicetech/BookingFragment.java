package com.example.servicetech;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;

import com.google.android.material.textfield.TextInputEditText;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class BookingFragment extends Fragment {
    private static final String TAG = "BookingFragment";

    TextView item, service, location, notes;
    private EditText techRec, date, startTime;
    private String docId, recommendations, time, Date;

    private ImageView itemPhoto;
    private Context context;
    Button submit, back;

    ParseUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        context = getContext();
        user = ParseUser.getCurrentUser();

        item = view.findViewById(R.id.item_tv);
        service = view.findViewById(R.id.item_type_tv);
        itemPhoto = view.findViewById(R.id.itemImg);
        location = view.findViewById(R.id.locat_tv);
        notes = view.findViewById(R.id.notes);

        techRec = view.findViewById(R.id.rec);
        date = view.findViewById(R.id.date);
        startTime = view.findViewById(R.id.time);

        submit = view.findViewById(R.id.schedule);
        back = view.findViewById(R.id.cncl);

        ParseObject event = null;
        if (getArguments() != null) {
            event = getArguments().getParcelable("event");
        }
        if(event != null){
            docId = event.getObjectId();
            item.setText(event.getString("Item"));
            service.setText(event.getString("Service"));
            location.setText(event.getString("Location"));
            notes.setText(event.getString("Note"));
            // Load the image using Glide
            Glide.with(this.context).load(event.getParseFile("Image").getUrl()).into(itemPhoto);

            submit.setText("Book");
        }

        ParseQuery<ParseObject> query = new ParseQuery<>("events");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recommendations = techRec.getText().toString();
                time = startTime.getText().toString();
                Date = date.getText().toString();

                if(TextUtils.isEmpty(recommendations)) {
                    techRec.setError("Please provide action needed");
                }else if(TextUtils.isEmpty(Date)) {
                    date.setError("Provide appointment Date");

                }else if(TextUtils.isEmpty(time)){
                    startTime.setError("Please provide a time");

                }else {

                    query.getInBackground(docId, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            if (e == null ) {
                                String status = object.getString("Status");
                                if (status == "pending"){
                                    object.put("Status", "scheduled");
                                    object.put("Recommendation", techRec);
                                    object.put("Date", Date);
                                    object.put("Time", time);
                                    object.put("PickedBy", user);

                                    object.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            Toast.makeText(getContext(), "Appointment booked successfully",
                                                    Toast.LENGTH_LONG).show();

                                            viewSchedule();
                                        }
                                    });
                                }else if (status != "pending"){
                                    Toast.makeText(getContext(), "Event already picked!" +
                                                    "Return to Listing",
                                            Toast.LENGTH_LONG).show();
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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewSchedule();
            }
        });
    }
    private void viewSchedule() {
        FragmentManager fm = ((TechnicianActivity)context).getSupportFragmentManager();
        TechScheduleFragment techScheduleFragment = new TechScheduleFragment();

        fm.beginTransaction().replace(R.id.tech_container, techScheduleFragment).commit();
    }
}