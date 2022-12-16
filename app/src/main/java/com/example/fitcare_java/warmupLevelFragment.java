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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Locale;


public class warmupLevelFragment extends Fragment {

   //declaring variables
    TextView btnLow;
    TextView btnModerate;
    TextView btnVigorous;
    ImageView btnBack;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_warmup_level, container, false);

        //setting variables
        btnBack = view.findViewById(R.id.btnBack);
        btnLow = view.findViewById(R.id.btnLow);
        btnModerate = view.findViewById(R.id.btnModerate);
        btnVigorous = view.findViewById(R.id.btnVigorous);

        //back onclick
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment homeFrag = new homeFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, homeFrag, null).addToBackStack(null).commit();
            }
        });

        //low onclick
        btnLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment warmupLowFrag = new warmupLowFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, warmupLowFrag, null).addToBackStack(null).commit();
            }
        });

        //moderate onlcik
        btnModerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment warmupModerateFrag = new warmupModerateFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, warmupModerateFrag, null).addToBackStack(null).commit();
            }
        });

        //vigorous onclick
        btnVigorous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment warmupVigorousFrag = new warmupVigorousFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, warmupVigorousFrag, null).addToBackStack(null).commit();
            }
        });

        return view;
    }

}