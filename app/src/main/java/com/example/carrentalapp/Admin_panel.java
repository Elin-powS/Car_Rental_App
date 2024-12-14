package com.example.carrentalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class Admin_panel extends AppCompatActivity {

    String match;
    ArrayList<Admin_model> arrAdmin_model = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        RecyclerView recyclerView = findViewById(R.id.admin_panel);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        arrAdmin_model.add(new Admin_model(R.drawable.reservation_information,"Pending Reservations Information"));
        arrAdmin_model.add(new Admin_model(R.drawable.reservation_details,"Reservation Informations"));
        arrAdmin_model.add(new Admin_model(R.drawable.edit_reservations,"Reservations Record"));
        arrAdmin_model.add(new Admin_model(R.drawable.user_profile2,"User Informations"));
        arrAdmin_model.add(new Admin_model(R.drawable.exit,"Log Out"));

        Admin_panel_adapter adapter = new Admin_panel_adapter(this,arrAdmin_model);
        recyclerView.setAdapter(adapter);

        Admin_panel_adapter.setOnItemClickLister(new Admin_panel_adapter.ClickListener() {
            @Override
            public View.OnClickListener onItemClick(int position, View view) {
                match= arrAdmin_model.get(position).Tittle;

                if(match.equals("Pending Reservations Information"))
                {
                    Toast.makeText(Admin_panel.this, "Pending Reservations Information!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Admin_panel.this,A_Reservation_Informations.class);
                    startActivity(intent);
                     return null;
                }
                if(match.equals("Reservation Informations"))
                {
                    Toast.makeText(Admin_panel.this, "Reservations Information!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Admin_panel.this,Accepted_Reservation_informations.class);
                    startActivity(intent);
                    return null;
                }

                else if(match.equals("Reservations Record"))
                {
                    Toast.makeText(Admin_panel.this, "Reservations Record!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Admin_panel.this,Edit_A_Reservation_Information.class);
                    startActivity(intent);
                    return null;
                }

                else if(match.equals("User Informations"))
                {
                    Toast.makeText(Admin_panel.this, "Users Information!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Admin_panel.this,A_User_info.class);
                    startActivity(intent);
                    return null;
                }

                else if(match.equals("Log Out"))
                {
                    Toast.makeText(Admin_panel.this, "Log Out !", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Admin_panel.this,MainActivity.class);
                    startActivity(intent);
                    finishAffinity();
                    return null;
                }

                return null;
            }
        });

    }
}