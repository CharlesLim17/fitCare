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
    FloatingActionButton btnMic;
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
        btnMic = view.findViewById(R.id.btnMic);

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

        btnMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voiceautomation();
            }
        });

        return view;
    }

    //function for opening mic
    private void voiceautomation() {
        Intent voice = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        voice.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak...");

        startActivityForResult(voice, 1);
    }

    //to get result and opening inputted page
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            ArrayList<String> arrayList=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            //voice command to navigate to exercise page
            if (arrayList.get(0).toString().equals("go to exercise") || arrayList.get(0).toString().equals("open exercise")) {
                Fragment exerciseLevelFrag = new exerciseLevelFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, exerciseLevelFrag).commit();
            }
            //voice command to navigate to settings
            if (arrayList.get(0).toString().equals("go to settings") || arrayList.get(0).toString().equals("open settings")) {
                Fragment settingsFrag = new settingsFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, settingsFrag).commit();
            }
            //voice command to navigate to meal plan
            if (arrayList.get(0).toString().equals("go to meal plan") || arrayList.get(0).toString().equals("open meal plan")) {
                Fragment mealPlanFrag = new mealPlanFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, mealPlanFrag).commit();
            }
            //voice command to navigate to tracker page
            if (arrayList.get(0).toString().equals("go to workout tracker") || arrayList.get(0).toString().equals("open workout tracker") || arrayList.get(0).toString().equals("go to tracker") || arrayList.get(0).toString().equals("open tracker")) {
                Fragment trackerFrag = new trackerFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, trackerFrag).commit();
            }
            //voice command to navigate to stretches page
            if (arrayList.get(0).toString().equals("go to workout stretches") || arrayList.get(0).toString().equals("open workout stretches") || arrayList.get(0).toString().equals("go to stretches") || arrayList.get(0).toString().equals("open stretches")) {
                Fragment warmupFrag = new warmupLevelFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, warmupFrag).commit();
            }
            //voice command to navigate to reminders page
            if (arrayList.get(0).toString().equals("go to workout reminders") || arrayList.get(0).toString().equals("open workout reminders") || arrayList.get(0).toString().equals("go to reminders") || arrayList.get(0).toString().equals("open reminders")) {
                Fragment remindersFrag = new reminderFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, remindersFrag).commit();
            }
        }
    }
}