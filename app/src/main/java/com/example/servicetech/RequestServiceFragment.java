package com.example.servicetech;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
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

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class RequestServiceFragment extends Fragment {

    private static final String TAG = "RequestServiceFragment";

    Context context;
    private EditText item, service, location, notes;
    private ImageView itemImage;
    private Button submit, cancel;
    private ImageButton  itemImg;
    private String Item, Service, Location, Notes;
    private Uri itemUri;

    private final int PICK_IMAGE_REQUEST = 71;

    Bitmap bmp;
    ParseUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_new, container, false);
    }

    @Override
    public void onViewCreated(@NonNull android.view.View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getContext();

        user = ParseUser.getCurrentUser();

        item = view.findViewById(id.item_name);
        service = view.findViewById(id.service);
        itemImage = getView().findViewById(id.itm_img);
        location = view.findViewById(id.location);
        notes = view.findViewById(id.note);
        itemImg = getView().findViewById(id.img_plus);
        submit = getView().findViewById(id.submit);
        cancel = getView().findViewById(id.cncl);

        itemImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        submit.setOnClickListener(v -> {
            Item = item.getText().toString();
            Service = service.getText().toString();
            Location = location.getText().toString();
            Notes = notes.getText().toString();


            if(TextUtils.isEmpty(Item)){
                item.setError("Enter item name");
                return;
            }else if(TextUtils.isEmpty(Service)){
                service.setError("This field can't be empty");
                return;
            }else if(TextUtils.isEmpty(Location)){
                location.setError("This field can't be empty");
                return;
            }else if(itemUri == null){
                Toast.makeText(getContext(), "Please upload Image."
                        , Toast.LENGTH_LONG).show();
            }else
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
                bmp = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), itemUri);
                itemImage.setImageBitmap(bmp);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void UploadImages () {
        // Convert it to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imageByte = stream.toByteArray();

        ParseFile imageFile = new ParseFile(Item + Service+".jpg", imageByte);
		
		ParseObject event = new ParseObject("events");

        imageFile.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){

                    event.put("Item", Item);
                    event.put("Service", Service);
                    event.put("Image", imageFile);
                    event.put("Location", Location);
                    event.put("Note", Notes);
                    event.put("Status", "pending");
                    event.put("RequestedBy", user);

                    event.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null){
                                Toast.makeText(getContext(), "Service request sent successfully",
                                        Toast.LENGTH_LONG).show();
                                restUi();
                            } else
                                Toast.makeText(getContext(), e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                        }
                    });
                }else
                    Toast.makeText(getContext(), e.getMessage()
                            , Toast.LENGTH_LONG).show();
            }
        });
    }


    private void restUi () {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        PendingFragment pendingFragment = new PendingFragment();
        fm.beginTransaction().replace(id.fragment_container, pendingFragment).commit();
    }


    public void cancel () {
        int w = 44, h = 44;

        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        Bitmap bmp = Bitmap.createBitmap(w, h, conf); // this creates a MUTABLE bitmap

        item.setText("");
        service.setText("");
        itemImage.setImageBitmap(bmp);
        location.setText("");
        notes.setText("");
    }
    public String GetDate () {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

        return df.format(Calendar.getInstance().getTime());
    }

}
