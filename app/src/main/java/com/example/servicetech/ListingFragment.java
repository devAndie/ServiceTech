package com.example.servicetech;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ListingFragment extends Fragment {
    private static final String TAG = "ListingFragment";
    private FirebaseFirestore firestoreDB;
    private RecyclerView eventsRecyclerView;

    FragmentActivity listener;
    ListingAdapter listingAdapter;
    String[] technician, rItems, description, cost;

    Context thisContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
/*        eventsRecyclerView = (RecyclerView) getView().findViewById(R.id.events_lst);


        LinearLayoutManager recyclerLayoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext());
        eventsRecyclerView.setLayoutManager(recyclerLayoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(eventsRecyclerView.getContext(),
                        recyclerLayoutManager.getOrientation());
        eventsRecyclerView.addItemDecoration(dividerItemDecoration);
 */

        return inflater.inflate(R.layout.fragment_home, container, false);
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

        Resources res = getResources();
        technician = res.getStringArray(R.array.technicians);
        rItems = res.getStringArray(R.array.rItems);
        cost = res.getStringArray(R.array.cost);
        description = res.getStringArray(R.array.description);

//        listView.setAdapter(listingAdapter);
//        listingAdapter = new ListingAdapter(thisContext, technician, rItems, description);

    }



}
