package com.example.servicetech;

import android.content.Context;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.servicetech.R.id;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class RequestServiceFragment extends Fragment {
    private static final String TAG = "RequestServiceFragment";

    Context context;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private EditText item, service, location, notes;
    private ImageView itemImage;
    private Button submit, cancel;
    private ImageButton  itemImg;
    private FirebaseStorage storage;
    private DocumentReference ref;

    private DatabaseReference eventDatabase;
    private FirebaseFirestore firestoreDB;

    private String docId, custId, Item, Service, Location, Notes;
    private StorageReference storageReference, fireRef, mStorageRef;

    private Uri itemUri;
    private final int PICK_IMAGE_REQUEST = 71;
    private static final int CAMERA_REQUEST = 1888;

    String imageURL = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_new, container, false);
    }

    @Override
    public void onViewCreated(@NonNull android.view.View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        item = view.findViewById(id.item_name);
        service = view.findViewById(id.item_type_la);
        itemImage = getView().findViewById(id.itm_img);
        location = view.findViewById(id.loc_el);
        notes = view.findViewById(id.notewrap);
        itemImg = getView().findViewById(id.img_plus);
        submit = getView().findViewById(id.submit);
        cancel = getView().findViewById(id.cncl);

        firestoreDB = FirebaseFirestore.getInstance();
        eventDatabase = FirebaseDatabase.getInstance().getReference("events");

        ref = firestoreDB.collection("events").document();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();


        Item = item.getText().toString();
        Service = service.getText().toString();
        Location = location.getText().toString();
        Notes = notes.getText().toString();

        itemImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reload();
            }
        });

        submit.setOnClickListener(v -> {
            UploadImages();
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
            itemUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), itemUri);
                itemImage.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    public void UploadImages() {
        try {
            String strFileName = GetDate() + "img.jpg";
            Uri file = itemUri;
            fireRef = mStorageRef.child("images/" + currentUser.getUid().toString() + "/" + strFileName);

            UploadTask uploadTask = fireRef.putFile(file);
            Log.e("Fire Path", fireRef.toString());
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fireRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        Log.e("Image URL", downloadUri.toString());

                        itemUri = null;
                        imageURL = downloadUri.toString();

                    } else {
                        Toast.makeText(getContext(), "Image upload unsuccessful. Please try again."
                                , Toast.LENGTH_LONG).show();
                    }
                    custId = currentUser.getUid();

                    addEvent();
                }
            });
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }
    public void addEvent(){
        //generate id
        docId = eventDatabase.push().getKey();

        EventModel event = createEventObj();
        addDocumentToCollection(event);
    }
    private EventModel createEventObj(){
        final EventModel event = new EventModel();
        event.setId(docId);
        event.setCustomerId(custId);
        event.setItemName(Item);
        event.setService(Service);
        event.setLocation(Location);
        event.setNotes(Notes);
        event.setImageURL(imageURL);
        event.setPicked("Not picked");

        return event;
    }

    private void addDocumentToCollection(EventModel event){
        eventDatabase.child(docId).setValue(event).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(),
                        "event request sent successfully",
                        Toast.LENGTH_SHORT).show();

                //view pending
                restUi();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding event record", e);
                Toast.makeText(getContext(),
                        "event record could not be added",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void restUi() {
        FragmentManager fm = ((TechnicianActivity)context).getSupportFragmentManager();
        PendingFragment pendingFragment = new PendingFragment();
        fm.beginTransaction().replace(R.id.tech_container, pendingFragment).commit();
    }
    public void Reload(){
        FragmentManager fm = ((TechnicianActivity)context).getSupportFragmentManager();
        RequestServiceFragment requestService = new RequestServiceFragment();
        fm.beginTransaction().replace(R.id.tech_container, requestService).commit();
    }
    public String GetDate() {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentdate = df.format(Calendar.getInstance().getTime());
        return currentdate;
    }
}
