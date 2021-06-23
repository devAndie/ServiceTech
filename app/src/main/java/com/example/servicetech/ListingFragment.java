package com.example.servicetech;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
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

public class ListingFragment extends Fragment {
    private static final String TAG = "ListingFragment";
    private ListingRvAdapter listingRvAdapter;
    private RecyclerView listingRv;
    Context context;
    ParseUser tech;
    List<ParseObject> listingList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_listings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listingRv = view.findViewById(R.id.events_lst);

        context = getContext();
        tech = ParseUser.getCurrentUser();
        listingList = new ArrayList<>();
        listingRvAdapter = new ListingRvAdapter(getContext(), listingList);

        LinearLayoutManager recyclerLayoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext());
        listingRv.setLayoutManager(recyclerLayoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(listingRv.getContext(),
                        recyclerLayoutManager.getOrientation());
        listingRv.addItemDecoration(dividerItemDecoration);

		//  Get the events class as a reference.
		ParseQuery<ParseObject> query = new ParseQuery("events");
//		ParseQuery<ParseObject> query = ParseQuery.getQuery("events");

		query.whereEqualTo("Status", "pending");
		query.whereNotEqualTo("RequestedBy", tech);
		
		query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    // Access the array of results here
                    if (objects.size() >= 0) {
                        for (ParseObject object : objects) {

                            object.getObjectId();

                            listingList.add(object);
                        }
                        listingRv.setAdapter(listingRvAdapter);
                        //String firstItemId = objects.get(0).getObjectId();
                    } else if (objects.size() <0){

                        Toast.makeText(getContext(), "No new jobs check later"
                                , Toast.LENGTH_LONG).show();
                    }

                    Toast.makeText(getContext(), "Data retrieved", Toast.LENGTH_SHORT).show();

                } else {
                    Log.d(TAG, "Error: " + e.getMessage());

                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void onItemClick(ParseObject event) {
        FragmentManager fm = getActivity().getSupportFragmentManager();

        Bundle bundle = new Bundle();
        bundle.putParcelable("event", event);

        BookingFragment createSchedule = new BookingFragment();
        createSchedule.setArguments(bundle);

        fm.beginTransaction().replace(R.id.tech_container, createSchedule).commit();
    }
}
