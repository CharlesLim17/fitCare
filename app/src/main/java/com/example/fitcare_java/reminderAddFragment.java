package com.example.fitcare_java;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;


public class reminderAddFragment extends Fragment {

    //declaring variables
    NumberPicker numPickerHour, numPickerMin, numPickerAm;
    ImageView btnBack;
    TextView btnAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reminder_add, container, false);

        //setting variables
        numPickerHour = view.findViewById(R.id.numPickerHour);
        numPickerMin = view.findViewById(R.id.numPickerMin);
        numPickerAm = view.findViewById(R.id.numPickerAm);
        btnBack = view.findViewById(R.id.btnBack);
        btnAdd = view.findViewById(R.id.btnAdd);

        //setting numpicker for hour
        numPickerHour.setMinValue(0);
        numPickerHour.setMaxValue(12);

        //setting numpicker for min
        numPickerMin.setMinValue(0);
        numPickerMin.setMaxValue(59);

        //setting am pm
        String[] time = {"am", "pm"};
        numPickerAm.setDisplayedValues(time);

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