package com.example.fitcare_java;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class userInput2Activity extends AppCompatActivity {

    //declaring variables to compute BMI
    static EditText etWeight;
    static EditText etHeight;
    EditText etGoal;
    TextView txtBmiResult;
    String calculation, bmiResult;
    Button submit_button;
    Button calculate_button;

    //checking if user is logged in / or registered
    public static String PREFS_NAME="MyPrefsFile";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input2);

        //hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //Setting variables
        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        etGoal = findViewById(R.id.etGoal);
        txtBmiResult = findViewById(R.id.txtBmiResult);
        calculate_button = findViewById(R.id.calculate_button);
        submit_button = findViewById(R.id.submit_button);

        // instantiate DAOuser class
        DAOUser dao = new DAOUser();

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
                //insert to database
                User user = new User(userInput1Activity.etFirstName.getText().toString(),
                        userInput1Activity.etLastName.getText().toString(),
                        Integer.parseInt(userInput1Activity.etAge.getText().toString()),
                        userInput1Activity.ddlGender.getText().toString(),
                        Float.parseFloat(etWeight.getText().toString()),
                        Float.parseFloat(etWeight.getText().toString()),
                        Integer.parseInt(etHeight.getText().toString()),
                        Float.parseFloat(etGoal.getText().toString()));
                dao.add(user).addOnSuccessListener(suc -> Toast.makeText(userInput2Activity.this, "Welcome, " + userInput1Activity.etFirstName.getText().toString() + " " + userInput1Activity.etLastName.getText().toString(), Toast.LENGTH_SHORT).show()).addOnFailureListener(er -> Toast.makeText(userInput2Activity.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show());

                //validation of fields
                if (checkFieldsSubmit()){
                    //Stay logged in
                    SharedPreferences sharedPreferences = getSharedPreferences(userInput2Activity.PREFS_NAME, 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putBoolean("hasLoggedIn", true);
                    editor.commit();

                    //start new activity
                    Intent intent = new Intent(userInput2Activity.this, termsAndConditionsActivity.class);
                    startActivity(intent);
                    finish();
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
}