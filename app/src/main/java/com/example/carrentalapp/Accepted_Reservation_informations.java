package com.example.carrentalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class Accepted_Reservation_informations extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    private FirebaseUser firebaseuser;
    private ProgressBar progressBar;

    Reservation_show_adapter show_adapter;
    ArrayList<reservations_info_show> list;

    private TextView Exit;
    public String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_reservation_informations);

        progressBar = (ProgressBar) findViewById(R.id.acc_progressBar);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = findViewById(R.id.acc_Reservations_info);

        firebaseuser = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Accepted Reservation Information");
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

        Exit=(TextView) findViewById(R.id.Aexit);
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Accepted_Reservation_informations.this, "Exit", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Accepted_Reservation_informations.this, Admin_panel.class);
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Accepted_Reservation_informations.this);

                alertDialog.setTitle("FeedBack of the Service!");
                alertDialog.setIcon(R.drawable.ic_baseline_info_24);
                alertDialog.setMessage("If the Service is Done then Delete or Exit.");

                alertDialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        databaseReference.child(key).removeValue();

                        Toast.makeText(Accepted_Reservation_informations.this, "Service Done!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Accepted_Reservation_informations.this,Admin_panel.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                });

                alertDialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                });


                alertDialog.show();

                return null;
            }
        });
    }
}