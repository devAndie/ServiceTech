package com.example.servicetech;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class CustomerScheduleFragment extends Fragment {
    private static final String TAG = "CustomerSchedule";

    private RecyclerView customerScheduleRv;
    private CustomerScheduleRecyclerViewAdapter customerScheduleAdapter;
    ParseUser user;
    String docId;
    List<ParseObject> customerSchedule;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_schedule_list, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = ParseUser.getCurrentUser();

        customerScheduleRv = view.findViewById(R.id.custAptList);

        customerSchedule = new ArrayList<>();

        customerScheduleAdapter = new CustomerScheduleRecyclerViewAdapter(customerSchedule, getContext());

        LinearLayoutManager recyclerLayoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext());
        customerScheduleRv.setLayoutManager(recyclerLayoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(customerScheduleRv.getContext(),
                        recyclerLayoutManager.getOrientation());
        customerScheduleRv.addItemDecoration(dividerItemDecoration);


        //  Get the events class as a reference.
        ParseQuery<ParseObject> query = new ParseQuery<>("events");
        query.whereEqualTo("Status", "picked");
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

                        customerSchedule.add(object);
                    }
                    customerScheduleRv.setAdapter(customerScheduleAdapter);

                    String firstItemId = objects.get(0).getObjectId();
                    Toast.makeText(getContext(), firstItemId, Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });
    }
}