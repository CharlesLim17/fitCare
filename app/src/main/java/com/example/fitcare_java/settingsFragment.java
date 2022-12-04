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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class settingsFragment extends Fragment {

    TextView txtGreet;
    TextView btnAbout;
    TextView btnPrivacy;
    ImageView btnEdit;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);

        //setting variables
        txtGreet = view.findViewById(R.id.txtGreet);
        btnAbout = view.findViewById(R.id.btnAbout);
        btnPrivacy = view.findViewById(R.id.btnPrivacy);
        btnEdit = view.findViewById(R.id.btnEdit);

        txtGreet.setText("Hello " + userInput1Activity.etFirstName.getText().toString() +", welcome to your profile!");

        //Privacy onclick
        btnPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment privacyFrag = new privacyFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, privacyFrag).commit();
            }
        });

        //About onclick
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment aboutFrag = new aboutFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, aboutFrag).commit();
            }
        });

        //Edit onclick
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment settingsEditFrag = new settingsEditFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, settingsEditFrag).commit();
            }
        });


        return view;
    }


}