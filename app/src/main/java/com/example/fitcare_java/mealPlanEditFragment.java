package com.example.fitcare_java;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;


public class mealPlanEditFragment extends Fragment {

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
    String newFoodDate;
    int year, month, day;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_plan_edit, container, false);

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
        TextView btnUpdate = view.findViewById(R.id.btnUpdate);
        btnDatePicker = view.findViewById(R.id.btnDatePicker);

        //retrieve/get values
        retrieveMealHistory();

        //initialize date picker
        initDatePicker();
        btnDatePicker.setText(getCurrentSetDate());

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

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                newFoodDate = btnDatePicker.getText().toString().trim();
                foodNameMorning1 = etFoodNameMorning1.getText().toString().trim();
                foodNameMorning2 = etFoodNameMorning2.getText().toString().trim();
                foodNameMorning3 = etFoodNameMorning3.getText().toString().trim();
                foodNameAfternoon1 = etFoodNameAfternoon1.getText().toString().trim();
                foodNameAfternoon2 = etFoodNameAfternoon2.getText().toString().trim();
                foodNameAfternoon3 = etFoodNameAfternoon3.getText().toString().trim();
                foodNameEvening1 = etFoodNameEvening1.getText().toString().trim();
                foodNameEvening2 = etFoodNameEvening2.getText().toString().trim();
                foodNameEvening3 = etFoodNameEvening3.getText().toString().trim();

                if (error()) {
                    updateFood();
                }
            }
        });
        return view;
    }

    //getting current set date
    private String getCurrentSetDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        int curMonth = this.month;
        curMonth -= 1;
        cal.set(Calendar.MONTH, curMonth);
        cal.set(Calendar.DAY_OF_MONTH, day);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("MMM d yyyy");
        return format.format(cal.getTime()).toUpperCase();
    }

    //initialize date picker
    private void initDatePicker() {
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
    private void openDatePicker() {
        datePickerDialog.show();

    }

    //making date string
    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    //Formatting Date
    private String getMonthFormat(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";

        //default
        return "Jan";
    }

    //to update food
    private void updateFood() {
        HashMap<String, Object> update = new HashMap<>();

        update.put("year", getYear());
        update.put("month", getMonth());
        update.put("day", getDay());
        update.put("foodDate", newFoodDate);
        update.put("foodNameMorning1", foodNameMorning1);
        update.put("foodNameMorning2", foodNameMorning2);
        update.put("foodNameMorning3", foodNameMorning3);
        update.put("foodNameAfternoon1", foodNameAfternoon1);
        update.put("foodNameAfternoon2", foodNameAfternoon2);
        update.put("foodNameAfternoon3", foodNameAfternoon3);
        update.put("foodNameEvening1", foodNameEvening1);
        update.put("foodNameEvening2", foodNameEvening2);
        update.put("foodNameEvening3", foodNameEvening3);

        databaseReference.child(uid).child("mealPlan").orderByChild("foodDate").equalTo(foodDate).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    dataSnapshot.getRef().updateChildren(update);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled", error.toException());
            }
        });

        Fragment mealPlanFrag = new mealPlanFragment();
        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.frameLayout, mealPlanFrag, null).addToBackStack(null).commit();
    }

    // function for validation of field
    private boolean error() {
        if (etFoodNameMorning1.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Please input food", Toast.LENGTH_LONG).show();
            return false;
        } else if (etFoodNameMorning2.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Please input food", Toast.LENGTH_LONG).show();
            return false;
        } else if (etFoodNameMorning3.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Please input food", Toast.LENGTH_LONG).show();
            return false;
        } else if (etFoodNameAfternoon1.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Please input food", Toast.LENGTH_LONG).show();
            return false;
        } else if (etFoodNameAfternoon2.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Please input food", Toast.LENGTH_LONG).show();
            return false;
        } else if (etFoodNameAfternoon3.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Please input food", Toast.LENGTH_LONG).show();
            return false;
        } else if (etFoodNameEvening1.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Please input food", Toast.LENGTH_LONG).show();
            return false;
        } else if (etFoodNameEvening2.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Please input food", Toast.LENGTH_LONG).show();
            return false;
        } else if (etFoodNameEvening3.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Please input food", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void retrieveMealHistory() {
        Bundle bundle = getArguments();

        if (bundle == null) {
            year = 0;
            month = 0;
            day = 0;
            foodDate = null;
            foodNameMorning1 = null;
            foodNameMorning2 = null;
            foodNameMorning3 = null;
            foodNameAfternoon1 = null;
            foodNameAfternoon2 = null;
            foodNameAfternoon3 = null;
            foodNameEvening1 = null;
            foodNameEvening2 = null;
            foodNameEvening3 = null;

        } else {
            year = bundle.getInt("year");
            month = bundle.getInt("month");
            day = bundle.getInt("day");
            foodDate = bundle.getString("foodDate");
            foodNameMorning1 = bundle.getString("foodNameMorning1");
            foodNameMorning2 = bundle.getString("foodNameMorning2");
            foodNameMorning3 = bundle.getString("foodNameMorning3");
            foodNameAfternoon1 = bundle.getString("foodNameAfternoon1");
            foodNameAfternoon2 = bundle.getString("foodNameAfternoon2");
            foodNameAfternoon3 = bundle.getString("foodNameAfternoon3");
            foodNameEvening1 = bundle.getString("foodNameEvening1");
            foodNameEvening2 = bundle.getString("foodNameEvening2");
            foodNameEvening3 = bundle.getString("foodNameEvening3");

            etFoodNameMorning1.setText(foodNameMorning1, TextView.BufferType.EDITABLE);
            etFoodNameMorning2.setText(foodNameMorning2, TextView.BufferType.EDITABLE);
            etFoodNameMorning3.setText(foodNameMorning3, TextView.BufferType.EDITABLE);
            etFoodNameAfternoon1.setText(foodNameAfternoon1, TextView.BufferType.EDITABLE);
            etFoodNameAfternoon2.setText(foodNameAfternoon2, TextView.BufferType.EDITABLE);
            etFoodNameAfternoon3.setText(foodNameAfternoon3, TextView.BufferType.EDITABLE);
            etFoodNameEvening1.setText(foodNameEvening1, TextView.BufferType.EDITABLE);
            etFoodNameEvening2.setText(foodNameEvening2, TextView.BufferType.EDITABLE);
            etFoodNameEvening3.setText(foodNameEvening3, TextView.BufferType.EDITABLE);
        }
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