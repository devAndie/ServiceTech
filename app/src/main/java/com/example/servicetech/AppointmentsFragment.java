package com.example.servicetech;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class AppointmentsFragment extends Fragment {

    private static final String TAG = "AppointmentsFragment";
    private RecyclerView appointmentRv;

    private CustObjectsRvAdapter appointmentsAdapter;
    private List<ParseObject> custAptsList;
    private Context context;
    String docId;

    ParseUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_list, container, false);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        context = getContext();
        custAptsList = new ArrayList<>();
        user = ParseUser.getCurrentUser();

        appointmentRv = view.findViewById(R.id.custAptList);
        appointmentsAdapter = new CustObjectsRvAdapter(custAptsList, context);

        LinearLayoutManager recyclerLayoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext());
        appointmentRv.setLayoutManager(recyclerLayoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(appointmentRv.getContext(),
                        recyclerLayoutManager.getOrientation());
        appointmentRv.addItemDecoration(dividerItemDecoration);

        //  Get the events class as a reference.
        ParseQuery<ParseObject> query = new ParseQuery<>("events");
        query.whereEqualTo("Status", "Scheduled");
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

                        docId = object.getObjectId();

                        custAptsList.add(object);
                    }
                    appointmentRv.setAdapter(appointmentsAdapter);

                    Toast.makeText(getContext(), "Data retrieved ", Toast.LENGTH_SHORT).show();
                }else if (objects == null) {
                    Toast.makeText(getContext(), "Zero Appointments, " +
                                    "Return to Pending or request a new one",
                            Toast.LENGTH_LONG).show();

                }else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });

       /* firestoreDB.collection("events")
                .whereEqualTo("custId", custId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Appointment> appointmentList = new ArrayList<>();

                            for(DocumentSnapshot doc : task.getResult()){
                                Appointment apt = doc.toObject(Appointment.class);

                                apt.setId(doc.getId());
                                appointmentList.add(apt);

                                Log.d(TAG, doc.getId() + " => " + doc.getData());
                            }
                            ViewAppointmentItemRecyclerViewAdapter recyclerViewAdapter = new
                                    ViewAppointmentItemRecyclerViewAdapter(appointmentList,
                                    getActivity(), firestoreDB);
                            appointmentRecyclerView.setAdapter(recyclerViewAdapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        */


    }
}
