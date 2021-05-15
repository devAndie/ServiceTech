package com.example.servicetech;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AppointmentsFragment extends Fragment {

    private static final String TAG = "AppointmentsFragment";
    private FirebaseFirestore firestoreDB;
    private FirebaseUser currentUser;
    private RecyclerView appointmentRecyclerView;

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
        appointmentRecyclerView = view.findViewById(R.id.events_lst);

        LinearLayoutManager recyclerLayoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext());
        appointmentRecyclerView.setLayoutManager(recyclerLayoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(appointmentRecyclerView.getContext(),
                        recyclerLayoutManager.getOrientation());
        appointmentRecyclerView.addItemDecoration(dividerItemDecoration);

        firestoreDB.collection("events")
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
        firestoreDB.collection("events")
                .addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        for(DocumentChange doc : documentSnapshots.getDocumentChanges()){
                            doc.getDocument().toObject(Appointment.class);
                            //do something...
                        }
                    }
                });


    }
}
