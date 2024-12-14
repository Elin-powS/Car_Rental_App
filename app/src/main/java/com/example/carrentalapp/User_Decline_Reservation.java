package com.example.carrentalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class User_Decline_Reservation extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    private FirebaseUser firebaseuser;
    private ProgressBar progressBar;
    private String userID;
    Reservation_show_adapter show_adapter;
    ArrayList<reservations_info_show> list;

    private TextView Exit;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_decline_reservation);

        progressBar = (ProgressBar) findViewById(R.id.DprogressBar);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = findViewById(R.id.Decline_Reservations_info);

        firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        userID = firebaseuser.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("user").child(userID).child("Declined Reservation Information");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        show_adapter = new Reservation_show_adapter(this,list);
        recyclerView.setAdapter(show_adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot :snapshot.getChildren()){

                    reservations_info_show user = dataSnapshot.getValue(reservations_info_show.class);
                    list.add(user);
                    progressBar.setVisibility(View.GONE);
                }

                show_adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Exit=(TextView) findViewById(R.id.Dexit);
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(User_Decline_Reservation.this, "Exit", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(User_Decline_Reservation.this, Sign_In.class);
                startActivity(intent);
            }
        });

        show_adapter.setOnItemClickListener(new Reservation_show_adapter.LongClickListener() {
            @Override
            public View.OnLongClickListener onItemClick(int position, View view) {
                return  null;
            }


        });
    }
}