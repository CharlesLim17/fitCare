package com.example.fitcare_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //splash screen
        Intent iHome = new Intent(MainActivity.this, termsAndConditionsActivity.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(iHome);
            }
        }, 3200);
    }
}