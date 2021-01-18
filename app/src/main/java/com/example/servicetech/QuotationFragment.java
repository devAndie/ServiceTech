package com.example.servicetech;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class QuotationFragment extends Fragment {
    EditText item1, cost1, item2, cost2, item3, cost3, item4, cost4;
    Button submit;
    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quatations, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
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
