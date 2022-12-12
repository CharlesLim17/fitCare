package com.example.fitcare_java;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Locale;


public class reminderAddFragment extends Fragment {

    //declaring variables
    NumberPicker numPickerHour, numPickerMin, numPickerAm;
    TextView txtDisplayTimeAdd;
    ImageView btnBack;
    TextView btnAdd;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reminder_add, container, false);

        //setting variables
        numPickerHour = view.findViewById(R.id.numPickerHour);
        numPickerMin = view.findViewById(R.id.numPickerMin);
        numPickerAm = view.findViewById(R.id.numPickerAm);
        txtDisplayTimeAdd = view.findViewById(R.id.txtDisplayTimeAdd);
        btnBack = view.findViewById(R.id.btnBack);
        btnAdd = view.findViewById(R.id.btnAdd);

        //setting numpicker for hour
        numPickerHour.setMinValue(0);
        numPickerHour.setMaxValue(12);

        //setting numpicker for min
        numPickerMin.setMinValue(0);
        numPickerMin.setMaxValue(59);

        //setting am pm
        NumPicker.initNumPicker();
        numPickerAm.setMaxValue(NumPicker.getNumPickerList().size() - 1);
        numPickerAm.setMinValue(0);
        numPickerAm.setDisplayedValues(NumPicker.numPickerNames());

        //storing hour/min/am_pm values to respective variables
        final int[] hour = new int[1];
        final int[] min = new int[1];
        final int[] am_pm = new int[1];

        numPickerHour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                txtDisplayTimeAdd.setText(String.format("Time: %s : %s %s", i1, (int) min[0], NumPicker.getNumPickerList().get(am_pm[0]).getName()));
                hour[0] = i1;
            }
        });

        numPickerMin.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                txtDisplayTimeAdd.setText(String.format("Time: %s : %s %s", (int) hour[0], i1, NumPicker.getNumPickerList().get(am_pm[0]).getName()));
                min[0] = i1;
            }
        });

        numPickerAm.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                txtDisplayTimeAdd.setText(String.format("Time: %s : %s %s", (int) hour[0], (int) min[0], NumPicker.getNumPickerList().get(i1).getName()));
                am_pm[0] = i1;
            }
        });

        //back onclick
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment reminderFrag = new reminderFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, reminderFrag).commit();
            }
        });

        //add onclick
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment reminderFrag = new reminderFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, reminderFrag).commit();
            }
        });

        return view;
    }

}