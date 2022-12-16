package com.example.fitcare_java;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class exerciseLevelFragment extends Fragment {

    //declaring variables
    TextView btnLow;
    TextView btnModerate;
    TextView btnVigorous;
    ImageView btnBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise_level, container, false);

        //setting variables
        btnBack = view.findViewById(R.id.btnBack);
        btnLow = view.findViewById(R.id.btnLow);
        btnModerate = view.findViewById(R.id.btnModerate);
        btnVigorous = view.findViewById(R.id.btnVigorous);

        //low onclick
        btnLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment exerciseLowFrag = new exerciseLowFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, exerciseLowFrag, null).addToBackStack(null).commit();
            }
        });

        //moderate onlcik
        btnModerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment exerciseModerateFrag = new exerciseModerateFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, exerciseModerateFrag, null).addToBackStack(null).commit();
            }
        });

        //vigorous onclick
        btnVigorous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment exerciseVigorousFrag = new exerciseVigorousFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, exerciseVigorousFrag, null).addToBackStack(null).commit();
            }
        });

        //back onlcick
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment homeFrag = new homeFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, homeFrag, null).addToBackStack(null).commit();
            }
        });

        return view;
    }

}