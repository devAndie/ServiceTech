package com.example.servicetech;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

public class ListingFragment extends Fragment {
    private static final String TAG = "ListingFragment";

    ListingRvAdapter listingRvAdapter;
    private FirebaseFirestore firestoreDB;
    private RecyclerView listingRv;
    private DatabaseReference listingDb;

    private List<EventModel> listingList;
    FragmentActivity listener;
    Context thisContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_listings, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            this.listener = (FragmentActivity) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listingRv = getView().findViewById(R.id.events_lst);

        firestoreDB = FirebaseFirestore.getInstance();
        listingDb = FirebaseDatabase.getInstance().getReference("events");

        listingList = new ArrayList<>();

        LinearLayoutManager recyclerLayoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext());

        listingRv.setLayoutManager(recyclerLayoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(listingRv.getContext(),
                        recyclerLayoutManager.getOrientation());
        listingRv.addItemDecoration(dividerItemDecoration);


        getListings();

    }
    public void initializeRV(){
        final ListingRvAdapter listingRvAdapter = new ListingRvAdapter(listingList, getActivity());

        Query query = listingDb.child("status").equalTo("pending").limitToFirst(100);

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                listingList.add(snapshot.getValue(EventModel.class));

                listingRvAdapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                listingRvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                listingList.remove(snapshot.getValue(EventModel.class));
                listingRvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                listingRvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listingList.add(snapshot.getValue(EventModel.class));

                listingRvAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listingRv.setAdapter(listingRvAdapter);
    }
    public void getListings() {
        firestoreDB.collection("Service Requests")
                .whereEqualTo("picked", "Not picked")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<EventModel> listingList = new ArrayList<>();

                            for(DocumentSnapshot doc : task.getResult()){
                                EventModel listing = doc.toObject(EventModel.class);

                                listing.setId(doc.getId());
                                listingList.add(listing);

                                Log.d(TAG, doc.getId() + " => " + doc.getData());
                            }
                            listingRvAdapter = new
                                    ListingRvAdapter(listingList, getActivity());

                            listingRv.setAdapter(listingRvAdapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


}
