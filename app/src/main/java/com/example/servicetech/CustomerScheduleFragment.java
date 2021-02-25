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

import com.example.servicetech.dummy.DummyContent;
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

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class CustomerScheduleFragment extends Fragment {
    private static final String TAG = "CustomerSchedule";
    private FirebaseFirestore firestoreDB;
    private RecyclerView customerScheduleRv;
    FirebaseAuth cAuth;
    FirebaseUser user;
    String docId;

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

        cAuth = FirebaseAuth.getInstance();
        user = cAuth.getCurrentUser();
        docId = user.getUid();
        firestoreDB = FirebaseFirestore.getInstance();

        customerScheduleRv = view.findViewById(R.id.custAptList);

        LinearLayoutManager recyclerLayoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext());
        customerScheduleRv.setLayoutManager(recyclerLayoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(customerScheduleRv.getContext(),
                        recyclerLayoutManager.getOrientation());
        customerScheduleRv.addItemDecoration(dividerItemDecoration);

        firestoreDB.collection("events")
                .whereEqualTo("id", docId)
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
                            customerScheduleRv.setAdapter(recyclerViewAdapter);
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