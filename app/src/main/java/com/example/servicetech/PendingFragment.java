package com.example.servicetech;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class PendingFragment extends Fragment{

    private static final String TAG = "PendingFragment";

    private RecyclerView pendingRecyclerView;
    private List<ParseObject> pendingList;
    ParseUser user;

    PendingRVAdapter pendingAdapter;
    private String customer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        user = ParseUser.getCurrentUser();

        pendingRecyclerView = view.findViewById(R.id.events_lst);
        pendingList = new ArrayList<>();
        pendingAdapter = new PendingRVAdapter(pendingList, getContext());


        LinearLayoutManager recyclerLayoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext());

        pendingRecyclerView.setLayoutManager(recyclerLayoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(pendingRecyclerView.getContext(),
                        recyclerLayoutManager.getOrientation());

        pendingRecyclerView.addItemDecoration(dividerItemDecoration);

		//  Get the events class as a reference.
        ParseQuery<ParseObject> query = new ParseQuery<>("events");
		query.whereEqualTo("Status", "pending");
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

                        pendingList.add(object);
                    }
                    pendingRecyclerView.setAdapter(pendingAdapter);

                    String firstItemId = objects.get(0).getObjectId();
                    Toast.makeText(getContext(), firstItemId, Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });

    

    }
}
