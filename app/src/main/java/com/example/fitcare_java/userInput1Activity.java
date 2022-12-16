package com.example.fitcare_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class userInput1Activity extends AppCompatActivity {

    //declaring variables for validation of fields
    static EditText etFirstName;
    static EditText etLastName;
    static EditText etAge;
    static AutoCompleteTextView ddlGender;
    Button button;
    ImageView btnBack;

    //declaring variables for gender ddl
    final String[] gender = {"Male", "Female", "Prefer not to say"};
    ArrayAdapter<String> adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input1);

        //hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //Gender String
        ddlGender = findViewById(R.id.ddlGender);
        adapterItems = new ArrayAdapter<String>(this, R.layout.gender_list, gender);
        ddlGender.setAdapter(adapterItems);

        ddlGender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String gender = adapterView.getItemAtPosition(i).toString();
            }
        });

        //Setting Variables
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etAge = findViewById(R.id.etAge);
        button = findViewById(R.id.button);
        btnBack = findViewById(R.id.btnBack);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (error()) {
                    Intent intent = new Intent(userInput1Activity.this, userInput2Activity.class);
                    startActivity(intent);
                }
            }
        });

        //btn back onclick
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(userInput1Activity.this, userInput0Activity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    // function for validation of field
    private boolean error() {
        if (etFirstName.getText().toString().length() == 0){
            Toast.makeText(userInput1Activity.this, "Please input first name", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (etLastName.getText().toString().length() == 0){
            Toast.makeText(userInput1Activity.this, "Please input last name", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (etAge.getText().toString().length() == 0){
            Toast.makeText(userInput1Activity.this, "Please input age", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (ddlGender.getText().toString().length() == 0){
            Toast.makeText(userInput1Activity.this, "Please Gender", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    //to use the built in back button of android
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(userInput1Activity.this, userInput0Activity.class);
        startActivity(intent);
        finish();
    }
}