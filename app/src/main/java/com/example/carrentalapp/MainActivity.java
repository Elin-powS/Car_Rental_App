package com.example.carrentalapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Button B1;
    private EditText Email, password;
    private TextView sign_up, forget_password;
    private ProgressBar progressBar;

    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;

    String Aemail,Apasword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.teal_700));

        Email = (EditText) findViewById(R.id.Email);
        password = (EditText) findViewById(R.id.password);
        B1 = (Button) findViewById(R.id.B1);
        sign_up = (TextView) findViewById(R.id.sign_up);
        forget_password = (TextView) findViewById(R.id.forget_password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        firebaseAuth = FirebaseAuth.getInstance();


        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Admin").child("Login Info").addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Admin_login admin = snapshot.getValue(Admin_login.class);
                if (admin != null) {

                    Aemail= admin.Email;
                    Apasword= admin.Password;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        B1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Sign_In();

            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                Sign_Up();
            }
        });

        forget_password.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                Forget_password();
            }
        });
    }

    private void Forget_password() {
        progressBar.setVisibility(View.GONE);
        Intent intent = new Intent(MainActivity.this, Forget_password.class);
        startActivity(intent);
    }

    private void Sign_Up() {
        progressBar.setVisibility(View.GONE);
        Intent intent = new Intent(MainActivity.this, Sign_Up.class);
        startActivity(intent);
    }

    private void Sign_In() {
        progressBar.setVisibility(View.GONE);
        String email =Email.getText().toString().trim();
        String Password=password.getText().toString().trim();

        if(email.isEmpty()){
            Email.setError("Email is required!");
            Email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("Please Provide valid email!");
            Email.requestFocus();
            return;
        }
        if(Password.isEmpty()) {
            password.setError("Required");
            password.requestFocus();
            return;
        }
        if(Password.length()<6)
        {
            password.setError("Minimum password length should be 6 character!");
            password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);



        if((email.equals(Aemail)) && (Password.equals(Apasword)))
            {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Successfully LogIn to Admin Panel!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,Admin_panel.class ));
                finish();
            }

        else{

            firebaseAuth.signInWithEmailAndPassword(email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "Successfully LogIn", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,Sign_In.class));
                        finish();
                    }
                    else{
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "Failed to Log In! Please check your credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}