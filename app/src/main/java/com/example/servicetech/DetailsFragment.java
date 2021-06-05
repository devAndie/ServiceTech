package com.example.servicetech;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = view.findViewById(R.id.pImg);

        name = view.findViewById(R.id.pItem);
        service = view.findViewById(R.id.pService);
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

        ParseQuery<ParseObject> query = new ParseQuery<>("events");

        Bundle b = getArguments();
        String docID = b.getString("objectId");

        query.whereEqualTo("objectId", docID);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null){
                    for (ParseObject object : objects){

                        name.setText(object.getString("Item"));
                        service.setText(object.getString("Service"));
                        location.setText(object.getString("Location"));
                        note.setText(object.getString("Note"));
                        recomdact.setText(object.getString("Recommendation"));

                        String custId = object.getString("RequestedBy");

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

                        String techId = object.getString("PickedBy");

                        ParseQuery<ParseUser> tech = ParseUser.getQuery();
                        tech.whereEqualTo("objectId", techId);
                        tech.findInBackground(new FindCallback<ParseUser>() {
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

                        date.setText(object.getString("Date"));
                        sTime.setText(object.getString("Time"));
                        endTime.setText(object.getString("EndTime"));
                    }
                }
            }
        });
    }
}