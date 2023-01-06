package com.example.fitcare_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class loginActivity extends AppCompatActivity {

    //declare variables
    TextView btnSignUp;
    EditText etEmail, etPassword;
    Button btnLogin;
    String email, password;

    //password visibility
    boolean passwordVisible;

    //firebase
    FirebaseAuth auth;
    DatabaseReference databaseReference;

    //initiate loading dialog
    private final LoadingDialog loadingDialog = new LoadingDialog(loginActivity.this);

    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //firebase instance
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");

        //spanning string in front end
        SpannableStringBuilder spannable = new SpannableStringBuilder("Don't have an account? Register here!");
        spannable.setSpan(
                new ForegroundColorSpan(Color.rgb(51,153,255)),
                23, // start
                37, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        );

        //setting variables
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogin = findViewById(R.id.btnLogin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        //setting spanned string
        btnSignUp.setText(spannable);


        //register on click
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginActivity.this, userInput0Activity.class);
                startActivity(intent);
                finish();
            }
        });

        //login on click
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadingDialog.startLoadingDialog();
                //converting to string and storing in variables
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();

                if (checkFields()){
                    logIn();
                }
                else {
                    loadingDialog.dismissDialog();
                }
            }
        });

        //password visibility
        etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                final int right=2;

                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX()>=etPassword.getRight()-etPassword.getCompoundDrawables()[right].getBounds().width()){
                        int selection=etPassword.getSelectionEnd();
                        if(passwordVisible){
                            //set drawable image here
                            etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.password_visibility, 0);
                            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        }
                        else{
                            etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.password_visibility_on, 0);
                            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;
                        }
                        etPassword.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    //login
    private void logIn() {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(loginActivity.this, "Welcome to Fitcare", Toast.LENGTH_LONG).show();

                    loadingDialog.dismissDialog();
                    Intent intent = new Intent(loginActivity.this, indexActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(loginActivity.this, "Please check email or password", Toast.LENGTH_LONG).show();
                loadingDialog.dismissDialog();
            }
        });
    }

    //validation of fields
    private boolean checkFields(){
        //check fields
        if (email.isEmpty()){
            Toast.makeText(loginActivity.this, "Please enter email address", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(loginActivity.this, "Please enter valid email address", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (password.isEmpty()) {
            Toast.makeText(loginActivity.this, "Please enter password", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}