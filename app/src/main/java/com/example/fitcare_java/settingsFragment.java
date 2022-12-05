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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class settingsFragment extends Fragment {

    //declaring variables
    TextView txtGreet, txtRetrieveWeight, txtRetrieveHeight;
    TextView btnAbout;
    TextView btnPrivacy;
    ImageView btnEdit;

    //firebase
    DatabaseReference reference;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);

        //retrieve data
        readData();

        //setting variables
        txtGreet = view.findViewById(R.id.txtGreet);
        txtRetrieveWeight = view.findViewById(R.id.txtRetrieveWeight);
        txtRetrieveHeight = view.findViewById(R.id.txtRetrieveHeight);
        btnAbout = view.findViewById(R.id.btnAbout);
        btnPrivacy = view.findViewById(R.id.btnPrivacy);
        btnEdit = view.findViewById(R.id.btnEdit);


        //txtGreet.setText("Hello " + userInput1Activity.etFirstName.getText().toString() +", welcome to your profile!");

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

        reference = FirebaseDatabase.getInstance().getReference().child("User");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String firstName = String.valueOf(snapshot.child("firstName").getValue());
                String weight = String.valueOf(snapshot.child("curWeight").getValue());
                String height = String.valueOf(snapshot.child("height").getValue());

                txtGreet.setText("Hello " + firstName + ", welcome to your profile!");
                txtRetrieveHeight.setText(height + " cm");
                txtRetrieveWeight.setText(weight + " kgs");
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
        }); //end of function to retrieve data pota o

    }


}