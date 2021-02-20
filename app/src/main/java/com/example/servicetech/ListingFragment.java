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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListingFragment extends Fragment {
    private static final String TAG = "ListingFragment";
    private FirebaseFirestore firestoreDB;
    private RecyclerView eventsRecyclerView;


    FragmentActivity listener;
    ListingRecyclerViewAdapter listingRecyclerViewAdapter;



    Context thisContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

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
        eventsRecyclerView = (RecyclerView) getView().findViewById(R.id.events_lst);

        firestoreDB = FirebaseFirestore.getInstance();

        LinearLayoutManager recyclerLayoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext());
        eventsRecyclerView.setLayoutManager(recyclerLayoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(eventsRecyclerView.getContext(),
                        recyclerLayoutManager.getOrientation());
        eventsRecyclerView.addItemDecoration(dividerItemDecoration);


        getListings();

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
                            ListingRecyclerViewAdapter listingRecyclerViewAdapter = new
                                    ListingRecyclerViewAdapter(listingList,
                                    getActivity(), firestoreDB);
                            eventsRecyclerView.setAdapter(listingRecyclerViewAdapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
/*        firestoreDB.collection("Service Requests")
                .addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        for(DocumentChange doc : documentSnapshots.getDocumentChanges()){
                            doc.getDocument().toObject(EventModel.class);
                            //do something...
                        }
                    }
                });
 */
    }


}
