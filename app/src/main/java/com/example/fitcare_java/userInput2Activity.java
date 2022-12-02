package com.example.fitcare_java;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class userInput2Activity extends AppCompatActivity {

    //declaring variables to compute BMI
    EditText etWeight;
    EditText etHeight;
    EditText etGoal;
    TextView txtBmiResult;
    String calculation, bmiResult;
    Button submit_button;
    Button calculate_button;

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


        //Button for Calculating BMI
        calculate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkFieldsBmi()){
                    calculateBMI(view);
                }
            }
        });

        //Button to Submit
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkFieldsSubmit()){
                    Intent intent = new Intent(userInput2Activity.this, indexActivity.class);
                    startActivity(intent);
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

        calculation = bmi + "\n" + bmiResult;

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