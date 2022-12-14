package com.example.fitcare_java;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fitcare_java.databinding.ActivityIndexBinding;
import com.example.fitcare_java.databinding.ActivityUserInput0Binding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class userInput0Activity extends AppCompatActivity {

    static EditText etEmail;
    //static EditText etPhone;
    static EditText etPassword;
    EditText etConPass;
    Button button;
    ImageView btnBack;

    //private FirebaseAuth auth;
    //private DatabaseReference databaseReference;
    //private Dialog dialog;
    //private User user;
    //private String uid;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input0);

        //hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //setting variables
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        button = findViewById(R.id.button);
        btnBack = findViewById(R.id.btnBack);
        etConPass = findViewById(R.id.etConPass);

        //get current user
        //auth = FirebaseAuth.getInstance();
        //uid = auth.getCurrentUser().getUid().toString();

        //button onclick listener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (error()) {
                    Intent intent = new Intent(userInput0Activity.this, userInput1Activity.class);
                    startActivity(intent);
                }
            }
        });

        //btn back onclick
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(userInput0Activity.this, loginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    // function for validation of field
    private boolean error() {
        if (etEmail.getText().toString().length() == 0){
            Toast.makeText(userInput0Activity.this, "Please enter email", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (etPassword.getText().toString().length() == 0){
            Toast.makeText(userInput0Activity.this, "Please enter password", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (!etConPass.getText().toString().equals(etPassword.getText().toString())) {
            Toast.makeText(userInput0Activity.this, "Password does not match", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}