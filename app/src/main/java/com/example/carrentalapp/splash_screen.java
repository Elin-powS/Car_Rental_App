package com.example.carrentalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class splash_screen extends AppCompatActivity {

    private ProgressBar progressBar;
    private  int progress;
    FirebaseUser firebaseUser ;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        progressBar=(ProgressBar) findViewById(R.id.progressBarID);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
                startApp();
            }
        });
        thread.start();
    }
    public  void doWork(){
        for(progress=20;progress<=100;progress=progress+20){
            try {
                Thread.sleep(500);
                progressBar.setProgress(progress);
            }
            catch (InterruptedException e){
                e.printStackTrace();

            }
        }

    }
    public void startApp(){

        if (firebaseAuth != null )
        {
            Intent intent = new Intent(splash_screen.this, Sign_In.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Intent intent = new Intent(splash_screen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}