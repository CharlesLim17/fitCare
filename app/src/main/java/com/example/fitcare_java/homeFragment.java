package com.example.fitcare_java;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class homeFragment extends Fragment {

    //declaring variables
    TextView btnExercise;
    TextView btnMeal;
    TextView btnTracker;
    TextView btnWarmup;
    TextView btnReminder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //setting variables
        btnExercise = view.findViewById(R.id.btnExercise);
        btnMeal = view.findViewById(R.id.btnMeal);
        btnTracker = view.findViewById(R.id.btnTracker);
        btnWarmup = view.findViewById(R.id.btnWarmup);
        btnReminder = view.findViewById(R.id.btnReminder);

        //Exercise onclick
        btnExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment exerciseLevelFrag = new exerciseLevelFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, exerciseLevelFrag).commit();
            }
        });

        //Meal Plan onclick
        btnMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment mealPlanFrag = new mealPlanFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, mealPlanFrag).commit();
            }
        });

        //Tracker onclick
        btnTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment trackerFrag = new trackerFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, trackerFrag).commit();
            }
        });

        //Reminder onclick
        btnReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment reminderFrag = new reminderFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, reminderFrag).commit();
            }
        });

        //Warmup onclick
        btnWarmup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment warmupFrag = new warmupLevelFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, warmupFrag).commit();
            }
        });

        return view;
    }
}