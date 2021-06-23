package com.example.servicetech;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class TechScheduleFragment extends Fragment {
    private static final String TAG = "ScheduleFragment";
    private RecyclerView techScheduleRV;
    private TechScheduleRvAdapter techScheduleRvAdapter;
    private List<ParseObject> techScheduleList;
    Context context;

    ParseUser user = ParseUser.getCurrentUser();
    //  Get the events class as a reference.
    ParseQuery<ParseObject> query = new ParseQuery<>("events");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
        @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        techScheduleRV = view.findViewById(R.id.events_lst);

        techScheduleList = new ArrayList<>();
        context = getContext();

        techScheduleRvAdapter = new TechScheduleRvAdapter(techScheduleList, context);

        LinearLayoutManager recyclerLayoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext());
        techScheduleRV.setLayoutManager(recyclerLayoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(techScheduleRV.getContext(),
                        recyclerLayoutManager.getOrientation());
        techScheduleRV.addItemDecoration(dividerItemDecoration);

        Button button = getView().findViewById(R.id.view_event);
        button.setOnClickListener(v -> viewEvents());

        query.whereEqualTo("Status", "scheduled");
        query.whereEqualTo("PickedBy", user);
        //query.orderByDescending("Date");

        // Execute the find asynchronously
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    // Access the array of results here
                    if (objects.size() >= 0) {

                        for (ParseObject object : objects) {
                            //ParseObject doc = object.toObject;
                            object.getObjectId();
                            techScheduleList.add(object);
                        }
                        techScheduleRV.setAdapter(techScheduleRvAdapter);
                    } else if (objects.size() < 0){
                        Toast.makeText(getContext(), "Zero Jobs booked, Return to Listing and book one",
                                Toast.LENGTH_LONG).show();

                    }

                     Toast.makeText(getContext(), "Data retrieved", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });
    }

    public void viewEvents() {
        String eventType = ((TextView) getActivity()
                .findViewById(R.id.event_type_v)).getText().toString();
        getDocumentsFromCollection(eventType);
    }

    private void getDocumentsFromCollection(String eventType) {
        query.whereEqualTo("Status", "scheduled");
        query.whereEqualTo("PickedBy", user);
        query.whereEqualTo("Service", eventType);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    // Access the array of results here
                    for (ParseObject object : objects){
                        //ParseObject doc = object.toObject;

                        object.getObjectId();
                        techScheduleList.add(object);
                    }
                    techScheduleRV.setAdapter(techScheduleRvAdapter);

//                    String firstItemId = objects.get(0).getObjectId();
                    Toast.makeText(getContext(), "Data retrieved", Toast.LENGTH_SHORT).show();
                } else if (objects == null) {
                    Toast.makeText(getContext(), "Zero Jobs booked, Return to Listing and book one",
                            Toast.LENGTH_LONG).show();

                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });
    }
}
