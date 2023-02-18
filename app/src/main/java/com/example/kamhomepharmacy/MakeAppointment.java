package com.example.kamhomepharmacy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MakeAppointment extends AppCompatActivity {

    EditText appointment_name, appointment_email, appointment_contact, appointment_address, appointment_reason;
    Button appointment_button;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);

        appointment_name = findViewById(R.id.appointment_name);
        appointment_email = findViewById(R.id.appointment_email);
        appointment_contact = findViewById(R.id.appointment_contact);
        appointment_address = findViewById(R.id.appointment_address);
        appointment_reason = findViewById(R.id.appointment_reason);
        appointment_button = findViewById(R.id.appointment_button);

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

                Toast.makeText(MakeAppointment.this, "Appointment successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MakeAppointment.this, MakeAppointment.class);
                startActivity(intent);

            }
        });

    }
}