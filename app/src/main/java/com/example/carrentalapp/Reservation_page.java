package com.example.carrentalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.HttpCookie;
import java.util.Calendar;
import java.util.HashMap;

public class Reservation_page extends AppCompatActivity {

    private EditText From_Address, TO_Address, Date, Time, Contact_number;
    private TextView greeting;
    private AutoCompleteTextView From, To;
    private Button reservation;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference reference, reference2, reference3;
    private FirebaseUser firebaseUser;
    private String userID;

    private ProgressBar progressBar;
    private TextView Exit;


    private DatePickerDialog picker;
    private TimePickerDialog Tpicker;
    private Calendar calendar;
    private int currentHour;
    private int curretMinute;
    private String ampm;

    private String vehicle, vehicle_size;
    private AlertDialog alertDialog;

    private String Name, phone, fare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_page);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("user");
        userID = firebaseUser.getUid();


        String[] cities = getResources().getStringArray(R.array.Cities);

        From = findViewById(R.id.from);
        To = findViewById(R.id.to);
        From_Address = (EditText) findViewById(R.id.from_address);
        TO_Address = (EditText) findViewById(R.id.to_address);
        Date = (EditText) findViewById(R.id.date);
        Time = (EditText) findViewById(R.id.time);
        Contact_number = (EditText) findViewById(R.id.additional_number);

        reference.child(userID).child("user information").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User userprofile = snapshot.getValue(User.class);
                if (userprofile != null) {
                    Name = userprofile.name;
                    phone = userprofile.phone_number;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });

        greeting = (TextView) findViewById(R.id.vehicle_info);
        vehicle = getIntent().getExtras().getString("Vehicle");
        vehicle_size = getIntent().getExtras().getString("Vehicle_Size");


        greeting.setText("Reservation for " + vehicle_size);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cities);
        From.setAdapter(adapter);
        To.setAdapter(adapter);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                picker = new DatePickerDialog(Reservation_page.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Date.setText(i2 + "-" + (i1 + 1) + "-" + i);
                    }
                }, year, month, day);

                picker.show();
            }
        });

        Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                curretMinute = calendar.get(Calendar.MINUTE);

                Tpicker = new TimePickerDialog(Reservation_page.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        if (i >= 12) {
                            ampm = "PM";
                        } else {
                            ampm = "AM";
                        }
                        if (i > 12 || (i == 12 && i1 > 0)) {
                            Time.setText(String.format("%02d:%02d", i - 12, i1) + ampm);
                        } else {
                            Time.setText(String.format("%02d:%02d", i, i1) + ampm);
                        }
                    }
                }, currentHour, curretMinute, false);
                Tpicker.show();
            }
        });

        Exit = (TextView) findViewById(R.id.exit);
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Reservation_page.this, "Exit", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Reservation_page.this, Sign_In.class);
                startActivity(intent);
                finishAffinity();
            }
        });


        reservation = (Button) findViewById(R.id.Reservations);
        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String from = From.getText().toString().trim();
                String from_address = From_Address.getText().toString().trim();
                String to = To.getText().toString().trim();
                String to_address = TO_Address.getText().toString().trim();
                String date = Date.getText().toString().trim();
                String time = Time.getText().toString().trim();
                String contact_number = Contact_number.getText().toString().trim();

                if (from.isEmpty()) {
                    From.setError("Required");
                    From.requestFocus();
                    return;
                }
                if (from_address.isEmpty()) {
                    From_Address.setError("Required");
                    From_Address.requestFocus();
                    return;
                }
                if (to.isEmpty()) {
                    To.setError("Required");
                    To.requestFocus();
                    return;
                }

                if (to_address.isEmpty()) {
                    TO_Address.setError("Required");
                    TO_Address.requestFocus();
                    return;
                }
                if (from_address == to_address) {
                    TO_Address.setError("From and To address are same!");
                    From_Address.setError("From and To address are same!");
                    TO_Address.requestFocus();
                    From_Address.requestFocus();
                    return;
                }
                if (date.isEmpty()) {
                    Date.setError("Required");
                    Date.requestFocus();
                    return;
                }
                if (time.isEmpty()) {
                    Time.setError("Required");
                    Time.requestFocus();
                    return;
                }
                if (contact_number.isEmpty()) {
                    Contact_number.setError("Required");
                    Contact_number.requestFocus();
                    return;
                }

                fare = Fare(from, to, vehicle_size);


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Reservation_page.this);

                alertDialog.setTitle("Confirmation of the Reservation!");
                alertDialog.setIcon(R.drawable.ic_baseline_info_24);
                alertDialog.setMessage("The Fare  for " + from_address + " To " + to_address + " is " + fare +".");

                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        progressBar.setVisibility(View.VISIBLE);
                        updateUI();
                    }
                });

                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Reservation_page.this, "Reservation Canceled", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog.show();
            }
        });
    }

    private void updateUI() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("From_City", From.getText().toString().trim());
        map.put("From_Address", From_Address.getText().toString().trim());
        map.put("To_City", To.getText().toString().trim());
        map.put("To_Address", TO_Address.getText().toString().trim());
        map.put("Arrival_Date", Date.getText().toString().trim());
        map.put("Arrival_Time", Time.getText().toString().trim());
        map.put("Additonal_Conatact_Number", Contact_number.getText().toString().trim());
        map.put("Vehicle", vehicle.trim());
        map.put("Vehicle_size", vehicle_size.trim());
        map.put("Name", Name.trim());
        map.put("Contact_Number", phone.trim());


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user");
        reference.child(firebaseUser.getUid()).child("Reservation Information").push()
                .setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Reservation_page.this, "Reservation Successfully Done.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Reservation_page.this, Sign_In.class);
                            startActivity(intent);
                            finish();

                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Reservation_page.this, "Reservation failed Try again!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        reference2 = FirebaseDatabase.getInstance().getReference().child("Admin");
        reference2.child("Reservation Information").push()
                .setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                    }
                });


        map.put("UserID", userID.trim());

        reference3 = FirebaseDatabase.getInstance().getReference().child("Admin");
        reference3.child("Pending Reservation Information").push()
                .setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
    }


    // Finding thr fare of the reservation

    public String Fare(String from, String to, String vehicle_size) {

        if (from.equals(to)) {

            if (vehicle_size.equals("Single Pillion Bike")) {

                fare = "400";

            } else if (vehicle_size.equals("4 Seater Car")) {

                fare = "700";

            } else if (vehicle_size.equals("7 Seater Car")) {

                fare = "800";

            } else if (vehicle_size.equals("10 Seater Car")) {
                fare = "1000";

            } else if (vehicle_size.equals("19 Seater Micro Bus")) {

                fare = "2000";

            } else if (vehicle_size.equals("More than 24 seater Bus")) {

                fare = "3000";
            }
        }

        else {

            if (vehicle_size.equals("Single Pillion Bike")) {

                fare = "1300";

            } else if (vehicle_size.equals("4 Seater Car")) {

                fare = "6000";

            } else if (vehicle_size.equals("7 Seater Car")) {

                fare = "8000";

            } else if (vehicle_size.equals("10 Seater Car")) {

                fare = "10000";

            } else if (vehicle_size.equals("19 Seater Micro Bus")) {

                fare = "18000";

            } else if (vehicle_size.equals("More than 24 seater Bus")) {

                fare = "21000";
            }

        }

        return fare;
    }

    // End of  finding fare of the Reservation


}