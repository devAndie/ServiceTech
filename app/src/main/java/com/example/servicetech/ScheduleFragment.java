package com.example.servicetech;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.core.view.Event;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ScheduleFragment extends Fragment {
    private static final String TAG = "ScheduleFragment";
    private FirebaseFirestore firestoreDB;
    private RecyclerView scheduleRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
        @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firestoreDB = FirebaseFirestore.getInstance();

        scheduleRecyclerView = view.findViewById(R.id.events_lst);

        LinearLayoutManager recyclerLayoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext());
        scheduleRecyclerView.setLayoutManager(recyclerLayoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(scheduleRecyclerView.getContext(),
                        recyclerLayoutManager.getOrientation());
        scheduleRecyclerView.addItemDecoration(dividerItemDecoration);

        Button button = getView().findViewById(R.id.view_event);
        button.setOnClickListener(v -> viewEvents());

        firestoreDB.collection("Appointments")
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
                            ScheduleRecyclerViewAdapter recyclerViewAdapter = new
                                    ScheduleRecyclerViewAdapter(appointmentList,
                                    getActivity(), firestoreDB);
                            scheduleRecyclerView.setAdapter(recyclerViewAdapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        firestoreDB.collection("Appointments")
                .addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        for(DocumentChange doc : documentSnapshots.getDocumentChanges()){
                            doc.getDocument().toObject(EventModel.class);
                            //do something...
                        }
                    }
                });


    }

    public void viewEvents() {
        String eventType = ((TextView) getActivity()
                .findViewById(R.id.event_type_v)).getText().toString();
        getDocumentsFromCollection(eventType);
    }

    private void getDocumentsFromCollection(String eventType) {
        firestoreDB.collection("Appointments")
                .whereEqualTo("Service type", eventType)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Appointment> appointmentList = new ArrayList<>();

                            for(DocumentSnapshot doc : task.getResult()){
                                Appointment apt = doc.toObject(Appointment.class);

                                //apt.setId(doc.getId());
                                appointmentList.add(apt);
                            }
                            ScheduleRecyclerViewAdapter recyclerViewAdapter = new
                                    ScheduleRecyclerViewAdapter(appointmentList,
                                    getActivity(), firestoreDB);
                            scheduleRecyclerView.setAdapter(recyclerViewAdapter);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        firestoreDB.collection("Appointments")
                .whereEqualTo("Service type", eventType)
                .addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        for(DocumentChange doc : documentSnapshots.getDocumentChanges()){
                            doc.getDocument().toObject(EventModel.class);
                            //do something...
                        }
                    }
                });
    }
}
