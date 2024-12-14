package com.example.carrentalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class A_Reservation_Informations extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference ,databaseReference3,databaseReference4,databaseReference5;
    private FirebaseUser firebaseuser;
    private ProgressBar progressBar;
    Reservation_show_adapter show_adapter;
    ArrayList<reservations_info_show> list;

    private TextView Exit;

    public String key,UserID;

    String name,from_City,from_Address,to_City,to_Address,arrival_Date,arrival_Time,additonal_Conatact_Number,vehicle,contact_Number,vehicle_size;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_areservation_informations);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = findViewById(R.id.AReservations_info);

        firebaseuser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        show_adapter = new Reservation_show_adapter(this, list);
        recyclerView.setAdapter(show_adapter);



        databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Pending Reservation Information");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    reservations_info_show user = dataSnapshot.getValue(reservations_info_show.class);
                    user.setKey(dataSnapshot.getKey());
                    list.add(user);

                    progressBar.setVisibility(View.GONE);
                }

                show_adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Exit = (TextView) findViewById(R.id.Aexit);
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(A_Reservation_Informations.this, "Exit", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(A_Reservation_Informations.this, Admin_panel.class);
                startActivity(intent);
                finishAffinity();
            }
        });


        show_adapter.setOnItemClickListener(new Reservation_show_adapter.LongClickListener() {
            @Override
            public View.OnLongClickListener onItemClick(int position, View view) {


                reservations_info_show show = show_adapter.getItemAt(position);
                if(show == null) return null;

                key = show.getKey();
                UserID = show.getUserID();

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(A_Reservation_Informations.this);

                alertDialog.setTitle("FeedBack of the Reservation!");
                alertDialog.setIcon(R.drawable.ic_baseline_info_24);
                alertDialog.setMessage("Accept Decline the Reservation or Exit.");

                alertDialog.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Accept();
                    }
                });

                alertDialog.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Decline();

                    }
                });

                alertDialog.setNeutralButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(A_Reservation_Informations.this, "Exit", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog.show();

                return null;
            }
        });


    }

    public void Accept() {


        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference();
        ref2.child("Admin").child("Pending Reservation Information").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Accepted_info userprofile = snapshot.getValue(Accepted_info.class);
                if (userprofile != null) {

                    name = userprofile.Name;
                    from_City = userprofile.From_City;
                    from_Address = userprofile.From_Address;
                    to_City = userprofile.To_City;
                    to_Address = userprofile.To_Address;
                    arrival_Date = userprofile.Arrival_Date;
                    arrival_Time = userprofile.Arrival_Time ;
                    vehicle = userprofile.Vehicle ;
                    vehicle_size = userprofile.Vehicle_size ;
                    contact_Number = userprofile.Contact_Number ;
                    additonal_Conatact_Number = userprofile.Additonal_Conatact_Number ;



                    HashMap<String, Object> map = new HashMap<>();

                    map.put("Name",name.trim());
                    map.put("From_City",from_City.trim());
                    map.put("From_Address",from_Address.trim());
                    map.put("To_City",to_City.trim());
                    map.put("To_Address", to_Address.trim());
                    map.put("Arrival_Date",arrival_Date.trim());
                    map.put("Arrival_Time",arrival_Time.trim());
                    map.put("Additonal_Conatact_Number",additonal_Conatact_Number.trim());
                    map.put("Vehicle",vehicle.trim());
                    map.put("Vehicle_size",vehicle_size.trim());
                    map.put("Contact_Number",contact_Number.trim());


                    databaseReference3 = FirebaseDatabase.getInstance().getReference();
                    databaseReference3.child("Admin").child("Accepted Reservation Information").push()
                            .setValue(map)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(A_Reservation_Informations.this, " Accepted Reservation!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(A_Reservation_Informations.this,Admin_panel.class);

                                        databaseReference4 = FirebaseDatabase.getInstance().getReference();
                                        databaseReference4.child("user").child(UserID).child("Accepted Reservation Information").push()
                                                .setValue(map)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        if (task.isSuccessful()) {

                                                            Toast.makeText(A_Reservation_Informations.this, "Successful", Toast.LENGTH_SHORT).show();
                                                            startActivity(intent);
                                                            finish();
                                                        }

                                                    }
                                                });
                                    }
                                    else {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(A_Reservation_Informations.this, "Reservation Acceptance Failed. Try again!!" , Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(A_Reservation_Informations.this, "Something Wrong Happened!.", Toast.LENGTH_SHORT).show();
            }
        });

        databaseReference.child(key).removeValue();
    }


    public void Decline() {

        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();
        reference2.child("Admin").child("Pending Reservation Information").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Accepted_info userprofile = snapshot.getValue(Accepted_info.class);
                if (userprofile != null) {

                    name = userprofile.Name;
                    from_City = userprofile.From_City;
                    from_Address = userprofile.From_Address;
                    to_City = userprofile.To_City;
                    to_Address = userprofile.To_Address;
                    arrival_Date = userprofile.Arrival_Date;
                    arrival_Time = userprofile.Arrival_Time ;
                    vehicle = userprofile.Vehicle ;
                    vehicle_size = userprofile.Vehicle_size ;
                    contact_Number = userprofile.Contact_Number ;
                    additonal_Conatact_Number = userprofile.Additonal_Conatact_Number ;



                    HashMap<String, Object> map = new HashMap<>();

                    map.put("Name",name.trim());
                    map.put("From_City",from_City.trim());
                    map.put("From_Address",from_Address.trim());
                    map.put("To_City",to_City.trim());
                    map.put("To_Address", to_Address.trim());
                    map.put("Arrival_Date",arrival_Date.trim());
                    map.put("Arrival_Time",arrival_Time.trim());
                    map.put("Additonal_Conatact_Number",additonal_Conatact_Number.trim());
                    map.put("Vehicle",vehicle.trim());
                    map.put("Vehicle_size",vehicle_size.trim());
                    map.put("Contact_Number",contact_Number.trim());


                    databaseReference5 = FirebaseDatabase.getInstance().getReference();
                    databaseReference5.child("user").child(UserID).child("Declined Reservation Information").push()
                            .setValue(map)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {

                                    }

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child(key).removeValue();

        Toast.makeText(A_Reservation_Informations.this, " Declined Reservation!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(A_Reservation_Informations.this,A_Reservation_Informations.class);
        startActivity(intent);
        finishAffinity();
    }

}