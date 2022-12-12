package com.example.fitcare_java;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class trackerFragment extends Fragment {

    //declaring variables
    ImageView btnBack, btnEdit;
    TextView txtRetrieveCurWeight, txtRetrievePrevWeight, txtRetrieveGoal, txtRetrieveVS, txtRetrieveToAchieveGoal;

    //firebase
    FirebaseUser user;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    String uid;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tracker, container, false);

        //setting variables
        btnBack = view.findViewById(R.id.btnBack);
        btnEdit = view.findViewById(R.id.btnEdit);
        txtRetrieveCurWeight = view.findViewById(R.id.txtRetrieveCurWeight);
        txtRetrievePrevWeight = view.findViewById(R.id.txtRetrievePrevWeight);
        txtRetrieveGoal = view.findViewById(R.id.txtRetrieveGoal);
        txtRetrieveVS = view.findViewById(R.id.txtRetrieveVS);
        txtRetrieveToAchieveGoal = view.findViewById(R.id.txtRetrieveToAchieveGoal);

        //firebase instance
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        uid = user.getUid();

        //retrieve data
        readData();

        //back onclick
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment homeFrag = new homeFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, homeFrag).commit();
            }
        });

        //edit onclick
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment trackerEditFrag = new trackerEditFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, trackerEditFrag).commit();
            }
        });

        return view;
    }

    //function to retrieve data
    private void readData(){
        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if (user !=null) {
                    float curWeight = user.curWeight;
                    float prevWeight = user.prevWeight;
                    float goal = user.goal;

                    txtRetrieveCurWeight.setText(curWeight + " kgs");
                    txtRetrievePrevWeight.setText(prevWeight + " kgs");
                    txtRetrieveGoal.setText(goal + " kgs");

                    //Current weight vs Previous Weight
                    float curVSprev;
                    String descVS;
                    if (curWeight > prevWeight) {
                        curVSprev = curWeight - prevWeight;
                        descVS = "-";

                        //retrieve 2 decimal places for curVsPrev
                        @SuppressLint("DefaultLocale") String formattedString1 = String.format("%.02f", curVSprev);
                        txtRetrieveVS.setText(descVS + formattedString1 + " kgs");
                    }
                    else if (curWeight < prevWeight){
                        curVSprev = prevWeight - curWeight;
                        descVS = "+";

                        //retrieve 2 decimal places for curVsPrev
                        @SuppressLint("DefaultLocale") String formattedString1 = String.format("%.02f", curVSprev);
                        txtRetrieveVS.setText(descVS + formattedString1 + " kgs");
                    }
                    else{
                        curVSprev = curWeight - prevWeight;
                        descVS = "=";

                        //retrieve 2 decimal places for curVsPrev
                        @SuppressLint("DefaultLocale") String formattedString1 = String.format("%.02f", curVSprev);
                        txtRetrieveVS.setText(descVS + formattedString1 + " kgs");
                    }

                    //To achieve Goal
                    float toAchieveGoal;
                    if (goal > curWeight){
                        toAchieveGoal = goal - curWeight;

                        //retrieve 2 decimal places for to achieve goal
                        @SuppressLint("DefaultLocale") String formattedString2 = String.format("%.02f", toAchieveGoal);
                        txtRetrieveToAchieveGoal.setText("You need to gain "+formattedString2 + " kgs");
                    }
                    else if(goal < curWeight){
                        toAchieveGoal = curWeight - goal;

                        //retrieve 2 decimal places for to achieve goal
                        @SuppressLint("DefaultLocale") String formattedString2 = String.format("%.02f", toAchieveGoal);
                        txtRetrieveToAchieveGoal.setText("You need to lose " + formattedString2 + " kgs");
                    }
                    else {
                        txtRetrieveToAchieveGoal.setText("Congratulations! You achieved your weight goal!");
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}