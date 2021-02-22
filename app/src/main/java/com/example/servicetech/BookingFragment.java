package com.example.servicetech;

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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingFragment extends Fragment {
    private TAG;
    TextInputEditText item, service, location, notes, recommendations, date, startTime;
    String docId ,imagePath, picked;
    ImageView itemPhoto;
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

        submit=view.findViewById(R.id.schedule);

        firestoreDB = FirebaseFirestore.getInstance();

        //get document from firebase
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

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


        Appointment aptevent = null;
        if(aptevent != null){
            docId = aptevent.getId();
            item.setText(aptevent.getItemName());
            service.setText(aptevent.getService());
            location.setText(aptevent.getLocation());
            notes.setText(aptevent.getNotes());
            imagePath = aptevent.getImageURL();
            picked = aptevent.getPicked();

            isEdit = true;
        }

/*
// Load the image using Glide
        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .into(itemPhoto);
*/



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submit();
            }
        });


    }
    private EventModel createEventObj(){
        final EventModel event = new EventModel();
        event.setId(docID);
        event.setItemName(((TextView)getActivity()
                .findViewById(R.id.item_name)).getText().toString());
        event.setService(((TextView)getActivity()
                .findViewById(R.id.item_type_la)).getText().toString());
        event.setLocation(((TextView)getActivity()
                .findViewById(R.id.loc_el)).getText().toString());
        event.setNotes(((TextView)getActivity()
                .findViewById(R.id.notewrap)).getText().toString());
        event.setImageURL(imageURL);
        event.setPicked("Not picked");

        return event;
    }
    public void submit(){


            ref.get().addOnSuccessListener(documentSnapshot -> {
                Map<String, Object> reg_entry = new HashMap<>();
                //String myId = ref.getId();
                firebaseFirestore.collection("quotations")
                        .add(reg_entry)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getContext(), "Submission successful", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                             @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("Error", e.getMessage());
                            }
                        })
                ;
            });

        Toast.makeText(getContext(), "quotation submitted successfully ", Toast.LENGTH_SHORT).show();

    }
}
