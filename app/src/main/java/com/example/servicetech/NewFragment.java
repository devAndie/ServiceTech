package com.example.servicetech;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.servicetech.R.id;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewFragment extends Fragment {
    EditText item, service, location, notes;
    Button submit, itemImage, cancel;
    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;
    StorageReference storageRef;
    UploadTask uploadTask;
    private Uri urlItemImage;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        itemImage = view.findViewById(id.img_plus);
        submit = getView().findViewById(R.id.submit);
        cancel = view.findViewById(id.cncl);

        firebaseFirestore= FirebaseFirestore.getInstance();
        ref = firebaseFirestore.collection("events").document();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        submit.setOnClickListener(v -> {
            Intent bookIntent = new Intent(getContext(), BookFragment.class);
            startActivity(bookIntent);
            submit();
        });


    }

    public void submit(){
        if(item.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Please type a username", Toast.LENGTH_SHORT).show();
        }else if(service.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Please type a service", Toast.LENGTH_SHORT).show();
        }else if(location.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Please provide a Location", Toast.LENGTH_SHORT).show();
        }else {
            ref.get().addOnSuccessListener(documentSnapshot -> {
                Map<String, Object> reg_entry = new HashMap<>();
                reg_entry.put("Item", item.getText().toString());
                reg_entry.put("Service", service.getText().toString());
                reg_entry.put("Location", location.getText().toString());
                reg_entry.put("Notes", notes.getText().toString());
                //reg_entry.put("Item Image", itmImg.getText().toString());

                //String myId = ref.getId();
                firebaseFirestore.collection("events")
                        .add(reg_entry)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                               Toast.makeText(getContext(), "Successfully added", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(getContext(), "event submitted successfully ", Toast.LENGTH_SHORT).show();

    }
}
