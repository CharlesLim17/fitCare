package com.example.fitcare_java;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class mealPlanDayFragment extends Fragment {

    //declaring variables
    ImageView btnBack;
    ImageView btnEditMorning;
    ImageView btnEditAfternoon;
    ImageView btnEditEvening;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_plan_day, container, false);

        //setting variable
        btnBack = view.findViewById(R.id.btnBack);
        btnEditMorning = view.findViewById(R.id.btnEditMorning);
        btnEditAfternoon = view.findViewById(R.id.btnEditAfternoon);
        btnEditEvening = view.findViewById(R.id.btnEditEvening);

        //back onclick
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment homeFrag = new homeFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, homeFrag, null).addToBackStack(null).commit();
            }
        });

        //edit morning onclick
        btnEditMorning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment mealPlanAddFrag = new mealPlanAddFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, mealPlanAddFrag, null).addToBackStack(null).commit();
            }
        });

        //edit afternoon onclick
        btnEditAfternoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment mealPlanAddFrag = new mealPlanAddFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, mealPlanAddFrag, null).addToBackStack(null).commit();
            }
        });


        //edit evening onlcick
        btnEditEvening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment mealPlanAddFrag = new mealPlanAddFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, mealPlanAddFrag, null).addToBackStack(null).commit();
            }
        });

        return view;
    }

}