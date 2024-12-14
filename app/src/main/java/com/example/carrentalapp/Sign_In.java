package com.example.carrentalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Sign_In extends AppCompatActivity{

    ArrayList<contact_model> arrContact_model = new ArrayList<>();

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    RecyclerView recyclerView;


    ImageView imageMenu;
    private FirebaseAuth firebaseAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_View);
        imageMenu = findViewById(R.id.imageMenu);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();


        if (user == null) {
            startActivity(new Intent(Sign_In.this, MainActivity.class));
            finish();
        }


        toggle = new ActionBarDrawerToggle(Sign_In.this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Intent intent;
                switch (item.getItemId()) {

                    case R.id.mHome:
                        Toast.makeText(Sign_In.this, "Clicked to Home", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.mProfile:
                        Toast.makeText(Sign_In.this, "Personal Information", Toast.LENGTH_SHORT).show();
                        intent = new Intent(Sign_In.this,Profile.class) ;
                        startActivity(intent);
                      break;

                    case R.id.mAbout_App:
                        Toast.makeText(Sign_In.this, "About App", Toast.LENGTH_SHORT).show();
                        intent = new Intent(Sign_In.this,About.class) ;
                        startActivity(intent);
                        break;

                    case R.id.mReservation_Information:
                        Toast.makeText(Sign_In.this, "Accepted Reservations Information", Toast.LENGTH_SHORT).show();
                        intent = new Intent(Sign_In.this,User_Reservation.class);
                        startActivity(intent);
                        break;

                    case R.id.mDecline_Reservation:
                        Toast.makeText(Sign_In.this, "Decline Reservations Information", Toast.LENGTH_SHORT).show();
                        intent = new Intent(Sign_In.this,User_Decline_Reservation.class);
                        startActivity(intent);
                        break;

                    case R.id.mReservations_info:
                        Toast.makeText(Sign_In.this, "Reservations Record", Toast.LENGTH_SHORT).show();
                        intent = new Intent(Sign_In.this,Reservations_Information.class);
                        startActivity(intent);
                        break;

                    case R.id.mlog_out:
                        firebaseAuth.signOut();
                        Toast.makeText(Sign_In.this, "Log Out Successful.", Toast.LENGTH_SHORT).show();
                        intent = new Intent(Sign_In.this,MainActivity.class) ;
                        startActivity(intent);
                        finish();
                        break;
                }

                return false;
            }
        });


        imageMenu = findViewById(R.id.imageMenu);

        imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        arrContact_model.add( new contact_model(R.drawable.bike,"Bike","Single Pillion Bike"));
        arrContact_model.add( new contact_model(R.drawable.car_4,"Car", "4 Seater Car"));
        arrContact_model.add( new contact_model(R.drawable.car_7,"Car", "7 Seater Car"));
        arrContact_model.add( new contact_model(R.drawable.car_10,"Car", "10 Seater Car"));
        arrContact_model.add( new contact_model(R.drawable.micro_bus,"Micro Bus", "19 Seater Micro Bus"));
        arrContact_model.add( new contact_model(R.drawable.bus,"Bus", "More than 24 seater Bus"));

        Recycler_contact_Adapter adapter = new Recycler_contact_Adapter(this,arrContact_model);
        recyclerView.setAdapter(adapter);

        Recycler_contact_Adapter.setOnItemClickListener(new Recycler_contact_Adapter.ClickListener() {
            @Override
            public View.OnClickListener onItemClick(int position, View view) {

                Toast.makeText(Sign_In.this, "Resevation Page", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Sign_In.this,Reservation_page.class);
                intent.putExtra("Vehicle",arrContact_model.get(position).vehicle_nam);
                intent.putExtra("Vehicle_Size",arrContact_model.get(position).vehicle_siz);
                startActivity(intent);
                return null;
            }
        });

    }
}