package com.example.kamhomepharmacy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminDashboard extends AppCompatActivity {

    CardView viewDesc, makeAppointment, profile, logout, chat, shopDrug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        viewDesc = findViewById(R.id.viewDesc);
        makeAppointment = findViewById(R.id.makeAppointment);
        profile = findViewById(R.id.profile);
        logout = findViewById(R.id.logout);
        chat = findViewById(R.id.chat);
        shopDrug = findViewById(R.id.shopDrug);

        viewDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboard.this, ViewDrugPrescription.class);
                startActivity(intent);
            }
        });
        makeAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboard.this, MakeAppointment.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String nameUser = intent.getStringExtra("name");

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboard.this, Profile.class);
                intent.putExtra("name", nameUser);
                startActivity(intent);
            }
        });

        shopDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboard.this, ShopDrugs.class);
                startActivity(intent);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboard.this, ChatBoxDashboard.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboard.this, Login.class);
                startActivity(intent);
            }
        });

    }
}