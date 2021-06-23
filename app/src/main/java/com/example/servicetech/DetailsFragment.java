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
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class DetailsFragment extends Fragment {

    TextView name, service, location, note, recomAct, cName, cTel, techName, techCont,
            date, time,  startTime, endTime, status, followUp;
    ImageView imageView;
    Context context;

    String docId, custId, techId, uId, tId;
    ParseUser customer, technician;

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

        status = view.findViewById(R.id.eStatus);

        name = view.findViewById(R.id.pItem);
        service = view.findViewById(R.id.pService);
        location =view.findViewById(R.id.pLocation);
        imageView = view.findViewById(R.id.pImg);

        note = view.findViewById(R.id.cNote);
        recomAct = view.findViewById(R.id.tRec);

        cName = view.findViewById(R.id.cName);
        cTel = view.findViewById(R.id.cTelNo);

        techName = view.findViewById(R.id.tName);
        techCont = view.findViewById(R.id.tTelNo);

        date = view.findViewById(R.id.sDate);
        time = view.findViewById(R.id.sTime);

        startTime = view.findViewById(R.id.startTime);
        endTime = view.findViewById(R.id.eTime);

        followUp = view.findViewById(R.id.folUp);

        ParseObject object = null;
        if (getArguments() != null) {
            object = getArguments().getParcelable("event");
        }
        if(object != null){
            docId = object.getObjectId();
            status.setText(object.getString("Status"));
            name.setText(object.getString("Item"));
            service.setText(object.getString("Service"));
            location.setText(object.getString("Location"));
            note.setText("Problem :" + object.getString("Note"));

            if (object.getString("Recommendation")!= null){
                recomAct.setText("Solution :" + object.getString("Recommendation"));
            }

            Glide.with(this.context).load(object.getParseFile("Image").getUrl()).into(imageView);


            date.setText(object.getString("Date"));
            time.setText(object.getString("Time"));

            startTime.setText(object.getString("StartTime"));
            endTime.setText(object.getString("EndTime"));

            followUp.setText(object.getString("FollowUp"));

            ParseObject owner = object.getParseObject("RequestedBy");

            ParseQuery<ParseUser> custQuery = ParseUser.getQuery();
            custQuery.whereEqualTo("objectId", owner.getObjectId());
            custQuery.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if (e == null){
                        for (ParseUser user: objects){
                            cName.setText(user.getUsername());
                            cTel.setText(user.getString("Phone"));
                        }
                    }
                }
            });

            if (object.getParseObject("PickedBy") != null){
                ParseObject tech = object.getParseObject("PickedBy");
                 
                ParseQuery<ParseUser> expert = ParseUser.getQuery();
                expert.whereEqualTo("objectId", tech.getObjectId());
                expert.findInBackground(new FindCallback<ParseUser>() {
                    @Override
                    public void done(List<ParseUser> objects, ParseException e) {
                        if (e == null){
                            for (ParseUser user: objects){
                                techName.setText(user.getUsername());
                                techCont.setText(user.getString("Phone"));
                            }
                        }
                    }
                });
            }
        }
    }
}