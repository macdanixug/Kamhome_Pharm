package com.example.kamhomepharmacy;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AppointmentFragment extends Fragment {

    EditText appointment_name, appointment_email, appointment_contact, appointment_address, appointment_reason,appointment_message;
    Button appointment_button;
    FirebaseDatabase database;
    DatabaseReference reference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointment, container, false);
        appointment_name = view.findViewById(R.id.appointment_name);
        appointment_email = view.findViewById(R.id.appointment_email);
        appointment_contact = view.findViewById(R.id.appointment_contact);
        appointment_address = view.findViewById(R.id.appointment_address);
        appointment_reason = view.findViewById(R.id.appointment_reason);
        appointment_message = view.findViewById(R.id.appointment_message);
        appointment_button = view.findViewById(R.id.appointment_button);

        appointment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                database = FirebaseDatabase.getInstance();
                reference = database.getReference("appointments");

                String name = appointment_name.getText().toString();
                String email = appointment_email.getText().toString();
                String contact = appointment_contact.getText().toString();
                String address = appointment_address.getText().toString();
                String reason = appointment_reason.getText().toString();

                HelperClass2 helperClass = new HelperClass2(name, email, contact, address, reason);
                reference.child(name).setValue(helperClass);

                Toast.makeText(getActivity(), "Appointment successfully!", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(AppointmentFragment.this, AppointmentFragment.class);
//                startActivity(intent);

            }
        });


        return view;
    }
}