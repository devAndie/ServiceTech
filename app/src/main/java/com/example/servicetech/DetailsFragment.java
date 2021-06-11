package com.example.servicetech;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class DetailsFragment extends Fragment {

    TextView name, service, location, note, recomdact, cName, cTel, techName, techCont,
            date, sTime, endTime;
    ImageView imageView;
    Context context;

    String docId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();

        name = view.findViewById(R.id.pItem);
        service = view.findViewById(R.id.pService);
        imageView = view.findViewById(R.id.pImg);
        location =view.findViewById(R.id.pLocation);
        note = view.findViewById(R.id.cNote);
        recomdact = view.findViewById(R.id.tRec);
        cName = view.findViewById(R.id.cName);
        cTel = view.findViewById(R.id.cTelNo);
        techName = view.findViewById(R.id.tName);
        techCont = view.findViewById(R.id.tTelNo);
        date = view.findViewById(R.id.sDate);
        sTime = view.findViewById(R.id.sTime);
        endTime = view.findViewById(R.id.eTime);

        ParseObject object = null;
        if (getArguments() != null) {
            object = getArguments().getParcelable("event");
        }
        if(object != null){
            docId = object.getObjectId();
            name.setText(object.getString("Item"));
            service.setText(object.getString("Service"));

            Glide.with(this.context).load(object.getParseFile("Image").getUrl()).into(imageView);
            location.setText(object.getString("Location"));
            note.setText(object.getString("Note"));
            recomdact.setText(object.getString("Recommendation"));

            String custId = object.getString("RequestedBy");
            if (custId != null){
                ParseQuery creator = ParseUser.getQuery();
                creator.whereEqualTo("userId", custId);
                creator.findInBackground(new FindCallback<ParseUser>(){
                    @Override
                    public void done(List<ParseUser> objects, ParseException e) {
                        if (e == null) {
                            // The query was successful.
                            for (ParseUser user : objects){
                                cName.setText(user.getUsername());
                                cTel.setText(user.getString("Phone"));
                            }
                        }
                    }
                });
            }
            ParseUser tech = (ParseUser) object.get("PickedBy");
            if (tech != null){
                ParseQuery<ParseUser> Tech = ParseUser.getQuery();
                Tech.whereEqualTo("objectId", tech);
                Tech.findInBackground(new FindCallback<ParseUser>() {
                    @Override
                    public void done(List<ParseUser> objects, ParseException e) {
                        if (e == null) {
                            // The query was successful.
                            for (ParseUser user : objects){
                                techName.setText(user.getUsername());
                                techCont.setText(user.getString("Phone"));
                            }
                        }
                    }
                });
            }
            date.setText(object.getString("Date"));
            sTime.setText(object.getString("Time"));
            endTime.setText(object.getString("EndTime"));
        }
    }

    public void onBackPressed() {
        //moveTaskToBack(true);
    }
}