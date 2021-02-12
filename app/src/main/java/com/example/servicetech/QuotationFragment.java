package com.example.servicetech;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class QuotationFragment extends Fragment {
    EditText item1, cost1, item2, cost2, item3, cost3, item4, cost4;
    Button submit;
    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;
    FirebaseAuth auth;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quatations, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Reference to an image file in Cloud Storage
        StorageReference storageReference =  FirebaseStorage.getInstance().getReference().child("myimage");

/*
        ImageView image = (ImageView)findViewById(R.id.imageView);

// Load the image using Glide
        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .into(image );
*/


        submit=view.findViewById(R.id.schedule);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submit();
            }
        });


    }

    public void submit(){
        if(item1.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Please provide an item", Toast.LENGTH_SHORT).show();
        }else if(item2.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Please input the cost", Toast.LENGTH_SHORT).show();
        }else {
            ref.get().addOnSuccessListener(documentSnapshot -> {
                Map<String, Object> reg_entry = new HashMap<>();
                reg_entry.put("Technician", user.getDisplayName());
                reg_entry.put("Item1", item1.getText().toString());
                reg_entry.put("Cost1", cost1.getText().toString());
                reg_entry.put("Item2", item2.getText().toString());
                reg_entry.put("Cost2", cost2.getText().toString());
                reg_entry.put("Item3", item3.getText().toString());
                reg_entry.put("Cost3", cost3.getText().toString());
                reg_entry.put("Item4", item4.getText().toString());
                reg_entry.put("Cost4", cost4.getText().toString());
                //reg_entry.put("Quotatation Total", quotTotal.getText().toString());

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
        }
        Toast.makeText(getContext(), "quotation submitted successfully ", Toast.LENGTH_SHORT).show();

    }
}
