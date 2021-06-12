package com.example.servicetech;

import android.content.Context;
import android.icu.text.Replaceable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TechAttendFragment extends Fragment {

    TextView item, service, location, notes, recommendations, date, startTime;
    private TextInputEditText  followUp;
    private ImageView itemPhoto;
    private String docId, StartTime, FollowUp, endTime;
    Context context;

    Button start, stop;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_attend, container, false);
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
        start = view.findViewById(R.id.attend);
        followUp = view.findViewById(R.id.Followup);
        stop = view.findViewById(R.id.mendApt);

        followUp.setVisibility(View.INVISIBLE);
        stop.setVisibility(View.INVISIBLE);

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
            startTime.setText(event.getString("Time"));

            // Load the image using Glide
            Glide.with(this.context).load(event.getParseFile("Image").getUrl()).into(itemPhoto);
        }
        ParseQuery<ParseObject> query = ParseQuery.getQuery("events");

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTime = GetDate();

                start.setVisibility(View.INVISIBLE);
                followUp.setVisibility(View.VISIBLE);
                stop.setVisibility(View.VISIBLE);
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTime = GetDate();
                FollowUp = followUp.getText().toString();

                query.getInBackground(docId, new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e == null){
                            object.put("Status", "completed");
                            object.put("StartTime", StartTime);
                            object.put("FollowUp", FollowUp);
                            object.put("EndTime", endTime);

                            object.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    Toast.makeText(getContext(), "Appointment Finished at " + endTime,
                                            Toast.LENGTH_LONG).show();

                                    viewSchedule();
                                }
                            });
                        } else {
                            Toast.makeText(getContext(), e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    public String GetDate () {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        return df.format(Calendar.getInstance().getTime());
    }

    private void viewSchedule() {
        FragmentManager fm = ((TechnicianActivity)context).getSupportFragmentManager();
        TechScheduleFragment techScheduleFragment = new TechScheduleFragment();

        fm.beginTransaction().replace(R.id.tech_container, techScheduleFragment).commit();
    }
}
