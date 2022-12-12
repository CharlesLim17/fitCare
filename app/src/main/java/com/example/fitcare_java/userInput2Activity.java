package com.example.fitcare_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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

public class userInput2Activity extends AppCompatActivity {

    //declaring variables to compute BMI
    static EditText etWeight;
    static EditText etHeight;
    EditText etGoal;
    TextView txtBmiResult;
    String calculation, bmiResult;
    Button submit_button;
    Button calculate_button;

    //holder for inputs
    String email, password, firstName, lastName, gender;
    int age, height;
    float  curWeight, prevWeight, goal;

    //firebase
    FirebaseAuth auth;
    DatabaseReference databaseReference;

    //checking if user is logged in / or registered
    //public static String PREFS_NAME="MyPrefsFile";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input2);

        //hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //firebase instance
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");


        //Setting variables
        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        etGoal = findViewById(R.id.etGoal);
        txtBmiResult = findViewById(R.id.txtBmiResult);
        calculate_button = findViewById(R.id.calculate_button);
        submit_button = findViewById(R.id.submit_button);


        //Button for Calculating BMI
        calculate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Instantiate User class
                if (checkFieldsBmi()){
                    calculateBMI(view);
                }
            }
        });

        //Button to Submit
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //convert to string
                email = userInput0Activity.etEmail.getText().toString();
                password = userInput0Activity.etPassword.getText().toString();
                firstName = userInput1Activity.etFirstName.getText().toString();
                lastName = userInput1Activity.etLastName.getText().toString();
                age = Integer.parseInt(userInput1Activity.etAge.getText().toString());
                gender = userInput1Activity.ddlGender.getText().toString();
                curWeight = Float.parseFloat(etWeight.getText().toString());
                prevWeight = Float.parseFloat(etWeight.getText().toString());
                height = Integer.parseInt(etHeight.getText().toString());
                goal = Float.parseFloat(etGoal.getText().toString());

                //validation of fields
                if (checkFieldsSubmit()){
                    signUp();
                }
            }
        });
    }

    //function to calculate bmi
    public void calculateBMI (View view) {
        String weight = etWeight.getText().toString();
        String height = etHeight.getText().toString();

        float weightValue = Float.parseFloat(weight);
        float heightValue = Float.parseFloat(height) / 100;

        float bmi = weightValue / (heightValue * heightValue);

        if (bmi < 16) {
            bmiResult = "Severely Underweight";
        }
        else if (bmi < 18.5) {
            bmiResult = "Under Weight";
        }
        else if (bmi >= 18.5 && bmi <= 24.9) {
            bmiResult = "Normal Weight";
        }
        else if (bmi >= 25 && bmi <= 29.9) {
            bmiResult = "Overweight";
        }
        else {
            bmiResult = "Obese";
        }

        String formattedString = String.format("%.02f", bmi);
        calculation = formattedString + "\n" + bmiResult;

        txtBmiResult.setText(calculation);
    }

    //function for validation of field to calculate bmi
    private boolean checkFieldsBmi() {
        if (etWeight.getText().toString().length() == 0){
            Toast.makeText(userInput2Activity.this, "Please input weight", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (etHeight.getText().toString().length() == 0){
            Toast.makeText(userInput2Activity.this, "Please input height", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    //function for validation of field to go to next fragment
    private boolean checkFieldsSubmit() {
        if (etWeight.getText().toString().length() == 0){
            Toast.makeText(userInput2Activity.this, "Please input weight", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (etHeight.getText().toString().length() == 0){
            Toast.makeText(userInput2Activity.this, "Please input height", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (etGoal.getText().toString().length() == 0){
            Toast.makeText(userInput2Activity.this, "Please input weight goal", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    //sign up
    private void signUp() {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    String userAuth = FirebaseAuth.getInstance().getUid();
                    User user = new User(email, password, firstName, lastName, age, gender, curWeight, prevWeight, height, goal);
                    databaseReference.child(userAuth).setValue(user);
                    Toast.makeText(userInput2Activity.this, "Please accept the terms and agreement then login", Toast.LENGTH_SHORT).show();

                    //start new activity
                    Intent intent = new Intent(userInput2Activity.this, termsAndConditionsActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(userInput2Activity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(userInput2Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}