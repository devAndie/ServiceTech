package com.example.servicetech;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class CompleteFragment extends Fragment {
    private static final String TAG = "CustomerSchedule";

    private RecyclerView completeRecyclerView;
    private CustObjectsRvAdapter completeAdapter;
    List<ParseObject> completedList;
    ParseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_item_list, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        user = ParseUser.getCurrentUser();
        completeRecyclerView = view.findViewById(R.id.custAptList);

        completedList = new ArrayList<>();
        completeAdapter = new CustObjectsRvAdapter(completedList, getContext());

        LinearLayoutManager recyclerLayoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext());

        completeRecyclerView.setLayoutManager(recyclerLayoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(completeRecyclerView.getContext(),
                        recyclerLayoutManager.getOrientation());

        completeRecyclerView.addItemDecoration(dividerItemDecoration);

        //  Get the events class as a reference.
        ParseQuery<ParseObject> query = new ParseQuery<>("events");
        query.whereEqualTo("Status", "completed");
        query.whereEqualTo("RequestedBy", user);
        //query.orderByDescending()

        // Execute the find asynchronously
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    // Access the array of results here
                    for (ParseObject object : objects){
                        //ParseObject doc = object.toObject;

                        object.getObjectId();
                        completedList.add(object);
                    }
                    completeRecyclerView.setAdapter(completeAdapter);

//                    String firstItemId = objects.get(0).getObjectId();
                    Toast.makeText(getContext(), "Data retrieved", Toast.LENGTH_SHORT).show();
                } else if (objects == null){
                    Toast.makeText(getContext(), "No completed jobs return to schedule and attend appointments"
                            , Toast.LENGTH_LONG).show();

                } else {
                    Log.d(TAG, "Error: " + e.getMessage());

                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}