package com.example.fitcare_java;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Locale;


public class mealPlanAddFragment extends Fragment {

    //declaring variables
    ImageView btnBack;
    TextView btnAdd;
    ImageView btnEdit1, btnEdit2, btnEdit3, btnEdit4;
    AutoCompleteTextView ddlFoodCat;

    //declaring variables for gender ddl
    final String[] food = {"Morning", "Afternoon", "Evening"};

    ArrayAdapter<String> adapterItems;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_plan_add, container, false);

        //setting variables
        btnBack = view.findViewById(R.id.btnBack);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnEdit1 = view.findViewById(R.id.btnEdit1);
        btnEdit2 = view.findViewById(R.id.btnEdit2);
        btnEdit3 = view.findViewById(R.id.btnEdit3);
        btnEdit4 = view.findViewById(R.id.btnEdit4);

        //FoodCat String values
        ddlFoodCat = view.findViewById(R.id.ddlFoodCat);
        adapterItems = new ArrayAdapter<String>(this.requireContext(), R.layout.gender_list, food); //gender_list nakalagay pero design lang yan for list - ni reuse ko lang
        ddlFoodCat.setAdapter(adapterItems);


        //back onclick
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment mealPlanFrag = new mealPlanFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, mealPlanFrag).commit();
            }
        });

        //add onclick

        //edit onclick
        btnEdit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment mealPlanEditFrag = new mealPlanEditFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, mealPlanEditFrag).commit();
            }
        });

        btnEdit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment mealPlanEditFrag = new mealPlanEditFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, mealPlanEditFrag).commit();
            }
        });

        btnEdit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment mealPlanEditFrag = new mealPlanEditFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, mealPlanEditFrag).commit();
            }
        });

        btnEdit4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment mealPlanEditFrag = new mealPlanEditFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, mealPlanEditFrag).commit();
            }
        });

        return view;
    }

}