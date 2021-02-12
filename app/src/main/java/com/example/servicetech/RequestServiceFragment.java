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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.servicetech.R.id;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.database.core.view.Event;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    FirebaseStorage storage;
    StorageReference storageReference, fireRef;
    DocumentReference ref;
    StorageReference ImgRef;
    FirebaseUser currentUser;

    private DatabaseReference databaseReference, childReference;
    private FirebaseFirestore firestoreDB;
    private FirebaseAuth mAuth;
    private String docId, picked;
    private StorageReference mStorageRef;
    private boolean isEdit;
    private Uri itemimg;
    private final int PICK_IMAGE_REQUEST = 71;
    private static final int CAMERA_REQUEST = 1888;
    private View View;
    String imageURL = "";
    String imagePath;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View = inflater.inflate(R.layout.fragment_new, container, false);

        return View;
    }

    @Override
    public void onViewCreated(@NonNull android.view.View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        item = view.findViewById(id.item_name);
        service = view.findViewById(id.event_type_a);
        location = view.findViewById(id.loc);
        notes = view.findViewById(id.info);

        submit = getView().findViewById(id.submit);
        cancel = getView().findViewById(id.cncl);
        itemImage = getView().findViewById(id.itm_img);
        itemImg = getView().findViewById(id.img_plus);

        firestoreDB = FirebaseFirestore.getInstance();
        ref = firestoreDB.collection("Service Requests").document();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        mStorageRef = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        itemImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                //startActivityForResult(cameraIntent, CAMERA_REQUEST);
                chooseImage();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        EventModel event = null;
        if(getArguments() != null){
            event = getArguments().getParcelable("event");
            ((TextView)view.findViewById(id.rqsv_tv)).setText("Edit Details");
        }
        if(event != null){
            docId = event.getId();
            item.setText(event.getItemName());
            service.setText(event.getService());
            location.setText(event.getLocation());
            notes.setText(event.getNotes());
            imagePath = event.getImageURL();
            picked = event.getPicked();

            isEdit = true;
        }
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
            itemimg = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), itemimg);
                itemImage.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    public void addEvent(){
        EventModel event = createEventObj();
        addDocumentToCollection(event);
    }
    private EventModel createEventObj(){
        final EventModel event = new EventModel();
        event.setItemName(((TextView)getActivity().findViewById(id.item_name)).getText().toString());
        event.setService(((TextView)getActivity()
                .findViewById(id.event_type_a)).getText().toString());
        event.setLocation(((TextView)getActivity()
                .findViewById(R.id.loc)).getText().toString());
        event.setNotes(((TextView)getActivity()
                .findViewById(R.id.info)).getText().toString());
        event.setImageURL(imageURL);
        event.setPicked(null);

        return event;
    }

  /*  public void submit(){
        ImgRef = storageReference.child("images/"+ UUID.randomUUID().toString());
        
        if(itemImg != null){
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            ImgRef = storageReference.child("images/"+ UUID.randomUUID().toString());
            ImgRef.putFile(itemimg)
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
            ref.get().addOnSuccessListener(documentSnapshot -> {
                Map<String, Object> reg_entry = new HashMap<>();
                reg_entry.put("Item", item.getText().toString());
                reg_entry.put("Service type", service.getText().toString());
                reg_entry.put("Location", location.getText().toString());
                reg_entry.put("Notes", notes.getText().toString());
                reg_entry.put("Item Image Uri", ImgRef.getPath());

                String svId = ref.getId();
                firestoreDB.collection("Service Requests")
                        .add(reg_entry)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                showAppointments();
                                Toast.makeText(getContext(), "Successfully added",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("Error", e.getMessage());
                            }
                        });
            });
        } else
            Toast.makeText(getContext(), "Input item image",
                Toast.LENGTH_SHORT).show();
    }
   */
    public void UploadImages() {
        try {
            String strFileName = GetDate() + "img.jpg";
            Uri file = itemimg;
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

                        itemimg = null;
                        imageURL = downloadUri.toString();

                    } else {
                        Toast.makeText(getContext(), "Image upload unsuccessful. Please try again."
                                , Toast.LENGTH_LONG).show();
                    }

                    addEvent();
                }
            });
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }
    private void addDocumentToCollection(EventModel event){
        firestoreDB.collection("events")
                .add(event)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Event document added - id: "
                                + documentReference.getId());
                        restUi();
                        Toast.makeText(getActivity(),
                                "Event document has been added",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding event document", e);
                        Toast.makeText(getActivity(),
                                "Event document could not be added",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }
    private void restUi() {
        Intent i = new Intent();
        i.setClass(getActivity(), AppointmentsFragment.class);
        startActivity(i);
    }
    public String GetDate() {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentdate = df.format(Calendar.getInstance().getTime());
        return currentdate;
    }
}
