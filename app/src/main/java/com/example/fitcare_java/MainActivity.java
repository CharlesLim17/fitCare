package com.example.fitcare_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
        Intent iRegister = new Intent(MainActivity.this, userInput0Activity.class);
        Intent iHome = new Intent(MainActivity.this, indexActivity.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //algo for staying logged in
                //SharedPreferences sharedPreferences = getSharedPreferences(userInput2Activity.PREFS_NAME, 0);
                //boolean hasLoggedIn = sharedPreferences.getBoolean("hasLoggedIn", false);

                //if(hasLoggedIn){
                    //startActivity(iHome);
                    //finish();

                //else {
                    startActivity(iRegister);
                    finish();
                //}
           }
        }, 3200);
    }
}