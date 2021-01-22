package com.example.servicetech;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.servicetech.R.id;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.core.view.Event;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.events.Event;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class RequestServiceFragment extends Fragment {
    private static final String TAG = "RequestServiceFragment";

    EditText item, service, location, notes;
    ImageView itemImage;
    Button submit, cancel;
    ImageButton  itemImg;
    Spinner servSpinner;
    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;
    FirebaseStorage storage;
    StorageReference storageReference;
    private FirebaseFirestore firestoreDB;
    private int docId;
    DocumentReference ref;
    StorageReference storageRef;
    StorageReference ImgRef;
    UploadTask uploadTask;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri urlItemImage;
    private View View;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View = inflater.inflate(R.layout.fragment_new, container, false);




        return View;
    }

    @Override
    public void onViewCreated(@NonNull android.view.View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        submit = getView().findViewById(R.id.submit);
        cancel = getView().findViewById(id.cncl);
        itemImage = getView().findViewById(id.itm_img);
        itemImg = getView().findViewById(id.img_plus);

        firestoreDB = FirebaseFirestore.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
        ref = firebaseFirestore.collection("events").document();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        itemImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        submit.setOnClickListener(v -> {

            Intent bookIntent = new Intent(getContext(), BookFragment.class);
            startActivity(bookIntent);
            if(item.getText().toString().equals("")) {
                Toast.makeText(getContext(), "Please type a username", Toast.LENGTH_SHORT).show();
            }else if(service.getText().toString().equals("")) {
                Toast.makeText(getContext(), "Please type a service", Toast.LENGTH_SHORT).show();
            }else if(location.getText().toString().equals("")) {
                Toast.makeText(getContext(), "Please provide a Location", Toast.LENGTH_SHORT).show();
            }else
                submit();

        });


    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                itemImage.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    public void submit(){
        ImgRef = storageReference.child("images/"+ UUID.randomUUID().toString());
        
        if(filePath != null){
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            ImgRef = storageReference.child("images/"+ UUID.randomUUID().toString());
            ImgRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Uploaded",
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed "+e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
        
        ref.get().addOnSuccessListener(documentSnapshot -> {
            Map<String, Object> reg_entry = new HashMap<>();
            reg_entry.put("Item", item.getText().toString());
            reg_entry.put("Service", service.getText().toString());
            reg_entry.put("Location", location.getText().toString());
            reg_entry.put("Notes", notes.getText().toString());
            reg_entry.put("Item Image Uri", ImgRef.getPath());

            //String myId = ref.getId();
            firebaseFirestore.collection("events")
            .add(reg_entry)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(getContext(), "Successfully added",
                            Toast.LENGTH_SHORT).show();
                    Intent stakes = new Intent(getContext(), StakesFragment.class);
                    startActivity(stakes);
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Error", e.getMessage());
                }
            });
        });
    }



}
