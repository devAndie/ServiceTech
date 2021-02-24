package com.example.servicetech;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingFragment extends Fragment {
    private static final String TAG = "BookingFragment";
    TextInputEditText item, service, location, notes, recommendations, date, startTime;
    String docId, name, type, place, desc, imagePath, picked, techUid, techRec, time, Date;
    ImageView itemPhoto;
    Context context;
    boolean isEdit;
    Button submit;
    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;
    FirebaseAuth auth;

    private FirebaseFirestore firestoreDB;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Reference to an image file in Cloud Storage
        StorageReference storageReference =  FirebaseStorage.getInstance().getReference().child("myimage");

        item = view.findViewById(R.id.item_tv);
        service = view.findViewById(R.id.item_type_tv);
        itemPhoto = view.findViewById(R.id.itemImg);
        location = view.findViewById(R.id.locat_tv);
        notes = view.findViewById(R.id.notes);
        recommendations = view.findViewById(R.id.recommend);
        date = view.findViewById(R.id.date);
        startTime = view.findViewById(R.id.stime);
        submit = view.findViewById(R.id.schedule);

        firestoreDB = FirebaseFirestore.getInstance();
        techUid = user.getUid();
        techRec = recommendations.getText().toString();
        time = startTime.getText().toString();
        Date = date.getText().toString();

/*
        //get document from firebase
        DocumentReference docRef = firestoreDB.collection("event").document(docId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                EventModel event = documentSnapshot.toObject(EventModel.class);
            }
        });
 */
        EventModel event = null;
        if (getArguments() != null) {
            event = getArguments().getParcelable("event");
        }
        if(event != null){
            name = event.getItemName();
            item.setText(name);
            type = event.getService();
            service.setText(type);
            place = event.getLocation();
            location.setText(place);
            desc = event.getNotes();
            notes.setText(desc);
            imagePath = event.getImageURL();

            isEdit = true;
        }

        // Load the image using Glide
        Glide.with(this)
                .load(imagePath)
                .into(itemPhoto);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAppointment();
            }
        });
    }
    private void createAppointment() {
        final Appointment event = new Appointment();
        event.setId(docId);
        event.setItemName(name);
        event.setService(type);
        event.setLocation(place);
        event.setNotes(desc);
        event.setImageURL(imagePath);
        event.setPicked("picked");
        event.setTechId(techUid);
        event.setRecommendation(techRec);
        event.setStartTime(time);
        event.setDate(Date);

        ref.get().addOnSuccessListener(documentSnapshot -> {
        firestoreDB.collection("events")
        .document(docId)
        .set(event, SetOptions.merge())
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                viewSchedule();
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }});

        });
    }

    private void viewSchedule() {
        FragmentManager fm = ((TechnicianActivity)context).getSupportFragmentManager();
        ScheduleFragment scheduleFragment = new ScheduleFragment();

        fm.beginTransaction().replace(R.id.tech_container, scheduleFragment).commit();
    }

}