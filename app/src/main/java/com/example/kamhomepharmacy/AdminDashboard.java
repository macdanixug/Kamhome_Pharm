package com.example.kamhomepharmacy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class AdminDashboard extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationview;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationview = findViewById(R.id.navigationView);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_menu, R.string.close_menu);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.user_nav_home:
                        Log.i("MENU_DRAWER_TAG", "Home is selected");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(AdminDashboard.this, Login.class));
                        break;
                    case R.id.user_nav_settings:
                        Log.i("MENU_DRAWER_TAG", "settings is selected");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(AdminDashboard.this, Signup.class));
                        break;
                    case R.id.user_nav_chat:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.user_nav_profile:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.user_nav_pharmacies:
                        // Log.i("MENU_DRAWER_TAG","Pharmacies selected");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
