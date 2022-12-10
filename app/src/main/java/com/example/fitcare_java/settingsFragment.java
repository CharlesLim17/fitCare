package com.example.fitcare_java;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class settingsFragment extends Fragment {

    //Declaring variables
    TextView txtGreet, txtRetrieveWeight, txtRetrieveHeight, txtRetrieveBMI, txtRetrieveBMIResult;
    TextView btnAbout;
    TextView btnPrivacy;
    ImageView btnEdit;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);

        // Setting variables
        txtGreet = view.findViewById(R.id.txtGreet);
        txtRetrieveWeight = view.findViewById(R.id.txtRetrieveWeight);
        txtRetrieveHeight = view.findViewById(R.id.txtRetrieveHeight);
        txtRetrieveBMI = view.findViewById(R.id.txtRetrieveBMI);
        txtRetrieveBMIResult = view.findViewById(R.id.txtRetrieveBmiResult);
        btnAbout = view.findViewById(R.id.btnAbout);
        btnPrivacy = view.findViewById(R.id.btnPrivacy);
        btnEdit = view.findViewById(R.id.btnEdit);

        // Retrieve Data and Display
        readData();

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

    //function to retrieve data
    private void readData() {
        DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference().child("User");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String firstName = String.valueOf(snapshot.child("firstName").getValue());
                float weight = Float.parseFloat(String.valueOf(snapshot.child("curWeight").getValue()));
                float height = Float.parseFloat(String.valueOf(snapshot.child("height").getValue()));

                txtGreet.setText("Hello " + firstName + ", welcome to your profile!");
                txtRetrieveHeight.setText((int)height + " cm");
                txtRetrieveWeight.setText(weight + " kgs");

                height = height/100;
                float bmi = weight / (height * height);

                if (bmi < 16) {
                    txtRetrieveBMIResult.setText("Severely Underweight");
                }
                else if (bmi < 18.5) {
                    txtRetrieveBMIResult.setText("Under Weight");
                }
                else if (bmi >= 18.5 && bmi <= 24.9) {
                    txtRetrieveBMIResult.setText("Normal Weight");

                }
                else if (bmi >= 25 && bmi <= 29.9) {
                    txtRetrieveBMIResult.setText("Overweight");
                }
                else {
                    txtRetrieveBMIResult.setText("Obese");
                }

                @SuppressLint("DefaultLocale") String formattedString = String.format("%.02f", bmi);
                txtRetrieveBMI.setText(formattedString + "");

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}