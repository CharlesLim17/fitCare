package com.example.fitcare_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    //firebase
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //firebase instance
        auth = FirebaseAuth.getInstance();

        //splash screen
        Intent iHome = new Intent(MainActivity.this, indexActivity.class);
        Intent iLogin = new Intent(MainActivity.this, loginActivity.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (auth.getCurrentUser() != null){
                   startActivity(iHome);
                   finish();
                }
                else{
                    startActivity(iLogin);
                    finish();
                }
           }
        }, 2800);
    }
}