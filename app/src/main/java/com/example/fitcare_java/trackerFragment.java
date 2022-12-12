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
    FloatingActionButton btnMic;
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
        btnMic = view.findViewById(R.id.btnMic);
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

        //mic onclick
        btnMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voiceautomation();
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

                    txtRetrieveCurWeight.setText(curWeight + "kgs");
                    txtRetrievePrevWeight.setText(prevWeight + "kgs");
                    txtRetrieveGoal.setText(goal + "kgs");

                    //Current weight vs Previous Weight
                    float curVSprev;
                    String descVS;
                    if (curWeight > prevWeight) {
                        curVSprev = curWeight - prevWeight;
                        descVS = "- ";

                        //retrieve 2 decimal places for curVsPrev
                        @SuppressLint("DefaultLocale") String formattedString1 = String.format("%.02f", curVSprev);
                        txtRetrieveVS.setText(descVS + formattedString1 + "kgs");
                    }
                    else if (curWeight < prevWeight){
                        curVSprev = prevWeight - curWeight;
                        descVS = "+ ";

                        //retrieve 2 decimal places for curVsPrev
                        @SuppressLint("DefaultLocale") String formattedString1 = String.format("%.02f", curVSprev);
                        txtRetrieveVS.setText(descVS + formattedString1 + "kgs");
                    }
                    else{
                        curVSprev = curWeight - prevWeight;
                        descVS = "= ";

                        //retrieve 2 decimal places for curVsPrev
                        @SuppressLint("DefaultLocale") String formattedString1 = String.format("%.02f", curVSprev);
                        txtRetrieveVS.setText(descVS + formattedString1 + "kgs");
                    }

                    //To achieve Goal
                    float toAchieveGoal;
                    if (goal > curWeight){
                        toAchieveGoal = goal - curWeight;

                        //retrieve 2 decimal places for to achieve goal
                        @SuppressLint("DefaultLocale") String formattedString2 = String.format("%.02f", toAchieveGoal);
                        txtRetrieveToAchieveGoal.setText("You need to gain "+formattedString2 + "kgs");
                    }
                    else if(goal < curWeight){
                        toAchieveGoal = curWeight - goal;

                        //retrieve 2 decimal places for to achieve goal
                        @SuppressLint("DefaultLocale") String formattedString2 = String.format("%.02f", toAchieveGoal);
                        txtRetrieveToAchieveGoal.setText("You need to lose " + formattedString2 + "kgs");
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