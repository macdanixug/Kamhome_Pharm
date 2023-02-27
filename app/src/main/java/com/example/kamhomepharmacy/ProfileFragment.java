package com.example.kamhomepharmacy;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    TextView profileName, profileEmail, profileUsername, profilePassword;
    TextView titleName, titleUsername;
    Button editProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileName = view.findViewById(R.id.profileName);
        profileEmail = view.findViewById(R.id.profileEmail);
        profileUsername = view.findViewById(R.id.profileUsername);
        profilePassword = view.findViewById(R.id.profilePassword);
        titleName = view.findViewById(R.id.titleName);
        titleUsername = view.findViewById(R.id.titleUsername);
        editProfile = view.findViewById(R.id.editProfile);

        passUserData();
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passUserData();
            }
        });

        return view;
    }

    public void passUserData(){
        Intent intent = getIntent();
        String nameUser = intent.getStringExtra("name");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(nameUser);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    String nameFromDB = snapshot.child("name").getValue(String.class);
                    String emailFromDB = snapshot.child("email").getValue(String.class);
                    String usernameFromDB = snapshot.child("username").getValue(String.class);
                    String passwordFromDB = snapshot.child("password").getValue(String.class);

                    titleName.setText(nameFromDB);
                    titleUsername.setText(emailFromDB);
                    profileName.setText(nameFromDB);
                    profileEmail.setText(emailFromDB);
                    profileUsername.setText(usernameFromDB);
                    profilePassword.setText(passwordFromDB);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}