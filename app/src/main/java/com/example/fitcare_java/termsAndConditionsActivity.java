package com.example.fitcare_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class termsAndConditionsActivity extends AppCompatActivity {

    //declaring variables
    RadioButton radioButton;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);

        //hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //Setting Variables
        radioButton = findViewById(R.id.radioButton);
        button = findViewById(R.id.button);

        //button onclick with validation
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (error()){
                    Intent intent = new Intent(termsAndConditionsActivity.this, loginActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    //function for checkbox validation
    private boolean error(){
        if (!radioButton.isChecked()){
            Toast.makeText(termsAndConditionsActivity.this, "Please check the button", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}