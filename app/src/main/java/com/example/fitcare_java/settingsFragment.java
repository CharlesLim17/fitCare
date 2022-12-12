package com.example.fitcare_java;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class settingsFragment extends Fragment {

    //Declaring variables
    TextView txtGreet, txtRetrieveWeight, txtRetrieveHeight, txtRetrieveBMI, txtRetrieveBMIResult;
    TextView btnAbout;
    TextView btnPrivacy;

    //used for logout
    Activity context;

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
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);

        // Setting variables
        txtGreet = view.findViewById(R.id.txtGreet);
        txtRetrieveWeight = view.findViewById(R.id.txtRetrieveWeight);
        txtRetrieveHeight = view.findViewById(R.id.txtRetrieveHeight);
        txtRetrieveBMI = view.findViewById(R.id.txtRetrieveBMI);
        txtRetrieveBMIResult = view.findViewById(R.id.txtRetrieveBmiResult);
        btnAbout = view.findViewById(R.id.btnAbout);
        btnPrivacy = view.findViewById(R.id.btnPrivacy);
        context = getActivity();

        //firebase instance
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        uid = user.getUid();

        //retrieve data
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

        return view;
    }

    //function to retrieve data
    private void readData() {
        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if (user !=null){
                    String firstName = user.firstName;
                    float weight = user.curWeight;
                    float height = user.height;

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    //button logout
    public void onStart(){
        super.onStart();
        TextView btnLogout = (TextView) context.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent = new Intent(context, loginActivity.class);
                startActivity(intent);
            }
        });
    }

}