package com.example.carrentalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.LoginFilter;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.util.Calendar;

public class Update_Profile extends AppCompatActivity {

    private FirebaseUser firebaseuser;
    private DatabaseReference reference;
    private String userID;
    private ProgressBar progressbar2;
    private Button update,Exit;
    private DatePickerDialog picker;
     TextView greeting;
     EditText Fullname, Contact_number, DOB, Address,UserName;

    private  User userprofile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        progressbar2 = findViewById(R.id.progressBar2);

        firebaseuser = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference().child("user");
        userID = firebaseuser.getUid();

       progressbar2.setVisibility(View.VISIBLE);

        update = findViewById(R.id.update);

         greeting = (TextView) findViewById(R.id.greeting);
         Fullname = (EditText) findViewById(R.id.Fullname);
         UserName = (EditText) findViewById(R.id.UserName);
        Address = (EditText) findViewById(R.id.Address);
        Contact_number = (EditText) findViewById(R.id.Contact_number);
        DOB= (EditText) findViewById(R.id.DOB);

        DOB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                picker = new DatePickerDialog(Update_Profile.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        DOB.setText(i2 + "/" + (i1 + 1) + "/" + i);
                    }
                }, year, month, day);

                picker.show();
            }
        });

        reference.child(userID).child("user information").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                userprofile = snapshot.getValue(User.class);
                if (userprofile != null) {
                    progressbar2.setVisibility(View.GONE);
                    String username = userprofile.Username;
                    String address = userprofile.Address;
                    String fullname = userprofile.name;
                    String email = userprofile.email;
                    String contact_number = userprofile.phone_number;
                    String dob = userprofile.birthday;

                    greeting.setText("Edit Your Profile Information  " + username);
                    Fullname.setText(fullname);
                    UserName.setText(username);   
                    Contact_number.setText(contact_number);
                    DOB.setText(dob);
                    Address.setText(address);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                progressbar2.setVisibility(View.GONE);
                Toast.makeText(Update_Profile.this, "Something Wrong Happened!.", Toast.LENGTH_SHORT).show();
            }
        });



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressbar2.setVisibility(View.VISIBLE);
                Update();
            }
        });
        Exit = (Button)findViewById(R.id.exit);
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Update_Profile.this,Profile.class);
                startActivity(intent);
                finishAffinity();
            }
        });

    }

    private void Update() {
        if (is_change()==1) {
            Toast.makeText(this, "Data Has been Updated!!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Update_Profile.this,Profile.class);
            startActivity(intent);
            progressbar2.setVisibility(View.GONE);
            finish();

        }
        else {
            Toast.makeText(this, "Data is same can't be Updated!", Toast.LENGTH_SHORT).show();
            progressbar2.setVisibility(View.GONE);
            Intent intent = new Intent(Update_Profile.this,Profile.class);
            startActivity(intent);
            finish();
        }
    }


    private int is_change() {
        int x=0;
        String newName = Fullname.getText().toString().trim();
        if (!userprofile.name.equals(newName)) {
            reference.child(userID).child("user information").child("name").setValue(newName);
            x=1;
        }
        String newusername = UserName.getText().toString().trim();
        if (!userprofile.Username.equals(newusername)) {
            reference.child(userID).child("user information").child("Username").setValue(newusername);
            x=1;
        }

       String newAddress = Address.getText().toString().trim();
        if (!userprofile.Address.equals(newAddress)){
            reference.child(userID).child("user information").child("Address").setValue(newAddress);
            x=1;
        }

        String newPhone_number = Contact_number.getText().toString().trim();
        if (!userprofile.phone_number.equals(newPhone_number)){
            reference.child(userID).child("user information").child("phone_number").setValue(newPhone_number);
            x=1;
        }
        String newbirthday = DOB.getText().toString().trim();
        if (!userprofile.birthday.equals(newbirthday)) {
            reference.child(userID).child("user information").child("birthday").setValue(newbirthday);
            x=1;

        }
        if(x==1)
        {
            return 1;
        }
        else {
            return 0;
        }
    }
}