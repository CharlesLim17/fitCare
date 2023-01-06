package com.example.fitcare_java;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Calendar;
import java.util.HashMap;


public class mealPlanAddFragment extends Fragment {

    //declaring variables
    private EditText etFoodNameMorning1, etFoodNameMorning2, etFoodNameMorning3, etFoodNameAfternoon1, etFoodNameAfternoon2, etFoodNameAfternoon3, etFoodNameEvening1, etFoodNameEvening2, etFoodNameEvening3;
    private Button btnDatePicker;

    //date picker dialog
    private DatePickerDialog datePickerDialog;

    //firebase
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseUser user;
    String uid;

    //input holders
    String foodDate, foodNameMorning1, foodNameMorning2, foodNameMorning3, foodNameAfternoon1, foodNameAfternoon2, foodNameAfternoon3, foodNameEvening1, foodNameEvening2, foodNameEvening3;
    int year;
    int month;
    int day;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_plan_add, container, false);

        //setting variables
        etFoodNameMorning1 = view.findViewById(R.id.etFoodNameMorning1);
        etFoodNameMorning2 = view.findViewById(R.id.etFoodNameMorning2);
        etFoodNameMorning3 = view.findViewById(R.id.etFoodNameMorning3);
        etFoodNameAfternoon1 = view.findViewById(R.id.etFoodNameAfternoon1);
        etFoodNameAfternoon2 = view.findViewById(R.id.etFoodNameAfternoon2);
        etFoodNameAfternoon3 = view.findViewById(R.id.etFoodNameAfternoon3);
        etFoodNameEvening1 = view.findViewById(R.id.etFoodNameEvening1);
        etFoodNameEvening2 = view.findViewById(R.id.etFoodNameEvening2);
        etFoodNameEvening3 = view.findViewById(R.id.etFoodNameEvening3);
        ImageView btnBack = view.findViewById(R.id.btnBack);
        TextView btnAdd = view.findViewById(R.id.btnAdd);
        btnDatePicker = view.findViewById(R.id.btnDatePicker);

        //initialize date picker
        initDatePicker();
        btnDatePicker.setText(getTodaysDate());

        //firebase
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        uid = user.getUid();


        //back onclick
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment mealPlanFrag = new mealPlanFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, mealPlanFrag, null).addToBackStack(null).commit();
            }
        });

        //date onclick
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker();
            }
        });

        //add onclick
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                foodDate = btnDatePicker.getText().toString().trim();
                foodNameMorning1 = etFoodNameMorning1.getText().toString().trim();
                foodNameMorning2 = etFoodNameMorning2.getText().toString().trim();
                foodNameMorning3 = etFoodNameMorning3.getText().toString().trim();
                foodNameAfternoon1 = etFoodNameAfternoon1.getText().toString().trim();
                foodNameAfternoon2 = etFoodNameAfternoon2.getText().toString().trim();
                foodNameAfternoon3 = etFoodNameAfternoon3.getText().toString().trim();
                foodNameEvening1 = etFoodNameEvening1.getText().toString().trim();
                foodNameEvening2 = etFoodNameEvening2.getText().toString().trim();
                foodNameEvening3 = etFoodNameEvening3.getText().toString().trim();

                if (error()){
                    addFood();
                }
            }
        });
        return view;
    }

    //getting today date
    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return makeDateString(day, month, year);
    }

    //initialize date picker
    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                btnDatePicker.setText(date);
                setYear(year);
                setMonth(month);
                setDay(day);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(getActivity(), style, dateSetListener, year, month, day);
    }

    //onclick of datepicker
    private void openDatePicker(){
        datePickerDialog.show();

    }

    //making date string
    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    //Formatting Date
    private String getMonthFormat(int month) {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default
        return "Jan";
    }

    //to upload food
    private void addFood() {
        HashMap<String, java.io.Serializable> upload = new HashMap<>();

        upload.put("year", getYear());
        upload.put("month", getMonth());
        upload.put("day", getDay());
        upload.put("foodDate", foodDate);
        upload.put("foodNameMorning1", foodNameMorning1);
        upload.put("foodNameMorning2", foodNameMorning2);
        upload.put("foodNameMorning3", foodNameMorning3);
        upload.put("foodNameAfternoon1", foodNameAfternoon1);
        upload.put("foodNameAfternoon2", foodNameAfternoon2);
        upload.put("foodNameAfternoon3", foodNameAfternoon3);
        upload.put("foodNameEvening1", foodNameEvening1);
        upload.put("foodNameEvening2", foodNameEvening2);
        upload.put("foodNameEvening3", foodNameEvening3);

        databaseReference.child(uid).child("mealPlan").push().setValue(upload);

        Fragment mealPlanFrag = new mealPlanFragment();
        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.frameLayout, mealPlanFrag, null).addToBackStack(null).commit();
    }

    // function for validation of field
    private boolean error() {
        if (etFoodNameMorning1.getText().toString().length() == 0){
            Toast.makeText(getActivity(), "Please input food", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (etFoodNameMorning2.getText().toString().length() == 0){
            Toast.makeText(getActivity(), "Please input food", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (etFoodNameMorning3.getText().toString().length() == 0){
            Toast.makeText(getActivity(), "Please input food", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (etFoodNameAfternoon1.getText().toString().length() == 0){
            Toast.makeText(getActivity(), "Please input food", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (etFoodNameAfternoon2.getText().toString().length() == 0){
            Toast.makeText(getActivity(), "Please input food", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (etFoodNameAfternoon3.getText().toString().length() == 0){
            Toast.makeText(getActivity(), "Please input food", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (etFoodNameEvening1.getText().toString().length() == 0){
            Toast.makeText(getActivity(), "Please input food", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (etFoodNameEvening2.getText().toString().length() == 0){
            Toast.makeText(getActivity(), "Please input food", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (etFoodNameEvening3.getText().toString().length() == 0){
            Toast.makeText(getActivity(), "Please input food", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}