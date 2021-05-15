package com.example.servicetech;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PendingFragment extends Fragment{

    private static final String TAG = "PendingFragment";
    private FirebaseFirestore firestoreDB;
    private FirebaseUser currentUser;

    private FirebaseDatabase database;
    private DatabaseReference eventsDB;
    
    private RecyclerView pendingRecyclerView;
    private List<EventModel> pendingList;

    private String custId;

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
        firestoreDB = FirebaseFirestore.getInstance();
        custId = currentUser.getUid();
        pendingRecyclerView = view.findViewById(R.id.events_lst);

        pendingList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        eventsDB = database.getReference("events");

        LinearLayoutManager recyclerLayoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext());
        pendingRecyclerView.setLayoutManager(recyclerLayoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(pendingRecyclerView.getContext(),
                        recyclerLayoutManager.getOrientation());


        initializeRV();


        /*
        firestoreDB.collection("events")
                .whereEqualTo("custId", custId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Appointment> appointmentList = new ArrayList<>();

                            for (DocumentSnapshot doc : task.getResult()) {
                                Appointment apt = doc.toObject(Appointment.class);

                                apt.setId(doc.getId());
                                appointmentList.add(apt);

                                Log.d(TAG, doc.getId() + " => " + doc.getData());
                            }
                            PendingRVAdapter pendingRVAdapter = new
                                    PendingRVAdapter(pending,
                                    getActivity());
                            pendingRecyclerView.setAdapter(pendingRVAdapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
         */
    }
    public void initializeRV(){
        final PendingRVAdapter pendingRVAdapter = new PendingRVAdapter(pendingList, getActivity());

        Query query = eventsDB.child("status").equalTo("pending").orderByChild("custId")
                .startAt(custId).endAt(custId);

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                pendingList.add(snapshot.getValue(EventModel.class));

                pendingRVAdapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                pendingRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                pendingList.remove(snapshot.getValue(EventModel.class));
                pendingRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                pendingRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pendingList.add(snapshot.getValue(EventModel.class));

                pendingRVAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        pendingRecyclerView.setAdapter(pendingRVAdapter);
    }
}
