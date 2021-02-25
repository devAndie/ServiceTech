package com.example.servicetech;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

public class AppointmentFragment extends Fragment {

    TextView name, service, location, notes, recomdact, date, startTime;
    String Name, Service, imagePath, Location, Notes, RecomdAct, Date, StartTime;
    Button attend;
    boolean isEdit;
    FirebaseFirestore firestoreDB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_appointment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = view.findViewById(R.id.name_aptv);
        service = view.findViewById(R.id.service_aptv);
        location =view.findViewById(R.id.location_aptv);
        notes = view.findViewById(R.id.notes_aptv);
        recomdact = view.findViewById(R.id.recmd_aptv);
        date = view.findViewById(R.id.date_aptv);
        startTime = view.findViewById(R.id.time_aptv);

        firestoreDB = FirebaseFirestore.getInstance();

        Bundle b = getArguments();
        String docID = b.getString("appointmentId");


        Appointment appointment = null;
        if (getArguments() != null) {
            appointment = getArguments().getParcelable("event");
        }
        if(appointment != null){
            Name = appointment.getItemName();
            name.setText(Name);
            Service = appointment.getService();
            service.setText(Service);
            Location = appointment.getLocation();
            location.setText(Location);
            Notes = appointment.getNotes();
            notes.setText(Notes);
            imagePath = appointment.getImageURL();
            RecomdAct = appointment.getRecommendation();
            recomdact.setText(RecomdAct);
            Date = appointment.getDate();
            date.setText(Date);
            StartTime = appointment.getStartTime();
            startTime.setText(StartTime);

            isEdit = true;
        }



    }
}
