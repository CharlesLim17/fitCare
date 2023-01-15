package com.example.fitcare_java;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Toast;

import com.example.fitcare_java.databinding.ActivityIndexBinding;
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

public class indexActivity extends AppCompatActivity {

    //view binding
    ActivityIndexBinding binding;
    static FloatingActionButton btnMic;
    private TextToSpeech t1;

    //firebase
    FirebaseUser user;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    String uid;

    //holders
    String holderHeight, holderCurWeight, holderPrevWeight, holderGoal, holderToAchieveGoal, holderCurVsPrev, holderBMI;

    //speech
    String speechHeight, speechCurWeight, speechPrevWeight, speechGoal, speechToAchieveGoal, speechCurVsPrev;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIndexBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //replacing fragment
        replaceFragment(new homeFragment());

        //hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //setting variables
        btnMic = findViewById(R.id.btnMic);

        //firebase instance
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        uid = user.getUid();


        //initialize text to speech
        t1 = new TextToSpeech(indexActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR)
                    t1.setLanguage(Locale.ENGLISH);
            }
        });

        //mic onclick
        btnMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voiceautomation();
            }
        });

        //bottom navigation bar
        binding.navBar.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.home:
                    replaceFragment(new homeFragment());
                    break;
                case R.id.fin:
                    replaceFragment(new finFragment());
                    break;
                case R.id.settings:
                    replaceFragment(new settingsFragment());
                    break;
            }
            return true;
        });
    }

    //function for changing fragments
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    //function for opening mic
    private void voiceautomation() {
        Intent voice = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        voice.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak...");

        startActivityForResult(voice, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {

            ArrayList<String> arrayList=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            //voice command to navigate to home page
            if (arrayList.get(0).toString().equals("go to home") || arrayList.get(0).toString().equals("go home")) {
                Fragment homeFrag = new homeFragment();
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, homeFrag, null).addToBackStack(null).commit();
            }

            //voice command to navigate to exercise page
            if (arrayList.get(0).toString().equals("go to exercise") || arrayList.get(0).toString().equals("open exercise")) {
                Fragment exerciseLevelFrag = new exerciseLevelFragment();
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, exerciseLevelFrag, null).addToBackStack(null).commit();
            }

            //voice command to navigate to exercise low
            if (arrayList.get(0).toString().equals("go to exercise low") || arrayList.get(0).toString().equals("open exercise low") || arrayList.get(0).toString().equals("open low exercise") || arrayList.get(0).toString().equals("go to low exercise")) {
                Intent intent = new Intent(this, exerciseLow.class);
                startActivity(intent);
            }

            //voice command to navigate to exercise moderate
            if (arrayList.get(0).toString().equals("go to exercise moderate") || arrayList.get(0).toString().equals("open exercise moderate") || arrayList.get(0).toString().equals("open moderate exercise") || arrayList.get(0).toString().equals("go to moderate exercise")) {
                Intent intent = new Intent(this, exerciseModerate.class);
                startActivity(intent);
            }

            //voice command to navigate to exercise vigorous
            if (arrayList.get(0).toString().equals("go to exercise vigorous") || arrayList.get(0).toString().equals("open exercise vigorous") || arrayList.get(0).toString().equals("open vigorous exercise") || arrayList.get(0).toString().equals("go to vigorous exercise")) {
                Intent intent = new Intent(this, exerciseVigorous.class);
                startActivity(intent);
            }

            //voice command to navigate to settings
            if (arrayList.get(0).toString().equals("go to settings") || arrayList.get(0).toString().equals("open settings")) {
                Fragment settingsFrag = new settingsFragment();
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, settingsFrag, null).addToBackStack(null).commit();
            }

            //voice command to navigate to about
            if (arrayList.get(0).toString().equals("go to about") || arrayList.get(0).toString().equals("open about")) {
                Fragment aboutFrag = new aboutFragment();
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, aboutFrag, null).addToBackStack(null).commit();
            }

            //voice command to navigate to privacy
            if (arrayList.get(0).toString().equals("go to privacy") || arrayList.get(0).toString().equals("open privacy")) {
                Fragment privacyFrag = new privacyFragment();
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, privacyFrag, null).addToBackStack(null).commit();
            }

            //voice command to navigate to meal plan
            if (arrayList.get(0).toString().equals("go to meal plan") || arrayList.get(0).toString().equals("open meal plan") || arrayList.get(0).toString().equals("go to meal") || arrayList.get(0).toString().equals("open meal")) {
                Fragment mealPlanFrag = new mealPlanFragment();
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, mealPlanFrag, null).addToBackStack(null).commit();
            }

            //voice command to navigate to tracker page
            if (arrayList.get(0).toString().equals("go to workout tracker") || arrayList.get(0).toString().equals("open workout tracker") || arrayList.get(0).toString().equals("go to tracker") || arrayList.get(0).toString().equals("open tracker")) {
                Fragment trackerFrag = new trackerFragment();
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, trackerFrag, null).addToBackStack(null).commit();
            }

            //voice command to navigate to tracker history
            if (arrayList.get(0).toString().equals("go to workout history") || arrayList.get(0).toString().equals("open workout history") || arrayList.get(0).toString().equals("go to tracker history") || arrayList.get(0).toString().equals("open tracker history") || arrayList.get(0).toString().equals("go to history") || arrayList.get(0).toString().equals("open history")) {
                Fragment trackerHistoryFrag = new trackerWorkoutHistoryFragment();
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, trackerHistoryFrag, null).addToBackStack(null).commit();
            }

            //voice command to navigate to stretches page
            if (arrayList.get(0).toString().equals("go to workout stretches") || arrayList.get(0).toString().equals("open workout stretches") || arrayList.get(0).toString().equals("go to stretches") || arrayList.get(0).toString().equals("open stretches") || arrayList.get(0).toString().equals("go to warm up stretches") || arrayList.get(0).toString().equals("open warm up stretches") || arrayList.get(0).toString().equals("go to warm up") || arrayList.get(0).toString().equals("open warm up")) {
                Fragment warmupFrag = new warmupLevelFragment();
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, warmupFrag, null).addToBackStack(null).commit();
            }

            //voice command to navigate to stretches low
            if (arrayList.get(0).toString().equals("go to workout stretches low") || arrayList.get(0).toString().equals("open workout stretches low") || arrayList.get(0).toString().equals("go to stretches low") || arrayList.get(0).toString().equals("open stretches low") || arrayList.get(0).toString().equals("go to warm up stretches low") || arrayList.get(0).toString().equals("open warm up stretches low") || arrayList.get(0).toString().equals("go to warm up low") || arrayList.get(0).toString().equals("open warm up low")) {
                Intent intent = new Intent(this, warmupLow.class);
                startActivity(intent);
            }

            //voice command to navigate to stretches moderate
            if (arrayList.get(0).toString().equals("go to workout stretches moderate") || arrayList.get(0).toString().equals("open workout stretches moderate") || arrayList.get(0).toString().equals("go to stretches moderate") || arrayList.get(0).toString().equals("open stretches moderate") || arrayList.get(0).toString().equals("go to warm up stretches moderate") || arrayList.get(0).toString().equals("open warm up stretches moderate") || arrayList.get(0).toString().equals("go to warm up moderate") || arrayList.get(0).toString().equals("open warm up moderate")) {
                Intent intent = new Intent(this, warmupModerate.class);
                startActivity(intent);
            }

            //voice command to navigate to stretches vigorous
            if (arrayList.get(0).toString().equals("go to workout stretches vigorous") || arrayList.get(0).toString().equals("open workout stretches vigorous") || arrayList.get(0).toString().equals("go to stretches vigorous") || arrayList.get(0).toString().equals("open stretches vigorous") || arrayList.get(0).toString().equals("go to warm up stretches vigorous") || arrayList.get(0).toString().equals("open warm up stretches vigorous") || arrayList.get(0).toString().equals("go to warm up vigorous") || arrayList.get(0).toString().equals("open warm up vigorous")) {
                Intent intent = new Intent(this, warmupVigorous.class);
                startActivity(intent);
            }

            //voice command to navigate to reminders page
            if (arrayList.get(0).toString().equals("go to workout reminders") || arrayList.get(0).toString().equals("open workout reminders") || arrayList.get(0).toString().equals("go to reminders") || arrayList.get(0).toString().equals("open reminders")) {
                Fragment remindersFrag = new reminderFragment();
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, remindersFrag, null).addToBackStack(null).commit();
            }

            //voice command to say statistics
            if (arrayList.get(0).toString().equals("check my statistics") || arrayList.get(0).toString().equals("check statistics") || arrayList.get(0).toString().equals("my statistics")) {
                //retrieve data
                readStatistics();

            }

            //voice command for goal weight
            if (arrayList.get(0).toString().equals("check my goal") || arrayList.get(0).toString().equals("check goal") || arrayList.get(0).toString().equals("my goal")) {
                //retrieve data
                readGoal();
            }

            //voice command for cur vs prev weight
            if (arrayList.get(0).toString().equals("check my weight") || arrayList.get(0).toString().equals("check weight") || arrayList.get(0).toString().equals("my weight")) {
                //retrieve data
                readCurVsPrev();
            }

            //voice command for cur vs prev weight
            if (arrayList.get(0).toString().equals("who am i")){
                //retrieve data
                readWhoAmI();
            }

            //exit the application
            if (arrayList.get(0).toString().equals("close the app") || arrayList.get(0).toString().equals("exit the app") || arrayList.get(0).toString().equals("close") || arrayList.get(0).toString().equals("exit")){
                finish();
            }
        }
    }

    //function to read and say statistics
    private void readStatistics() {
        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if (user !=null) {
                    float height = user.height;
                    float curWeight = user.curWeight;
                    float goal = user.goal;

                    holderHeight = height + "";
                    holderCurWeight = curWeight + "";
                    holderGoal = goal + "";

                    //Attaching variables to speech
                    speechHeight = holderHeight;
                    speechCurWeight = holderCurWeight;
                    speechGoal = holderGoal;

                    //String speech
                    String speechStatistics = "Your statistics is" + speechHeight + " centimeters in height, " + speechCurWeight +
                            " kilograms in weight, and your goal is " + speechGoal + " kilograms.";

                    t1.speak(speechStatistics, TextToSpeech.QUEUE_FLUSH, null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(indexActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    //function to read and say goal
    private void readGoal(){
        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if (user !=null) {
                    float curWeight = user.curWeight;
                    float goal = user.goal;

                    holderCurWeight = curWeight + "";
                    holderGoal = goal + "";

                    //To achieve Goal
                    float toAchieveGoal;
                    if (goal > curWeight){
                        toAchieveGoal = goal - curWeight;

                        //retrieve 2 decimal places for to achieve goal
                        @SuppressLint("DefaultLocale") String formattedString2 = String.format("%.02f", toAchieveGoal);
                        holderToAchieveGoal = "You need to gain "+formattedString2 + " kilograms.";
                    }
                    else if(goal < curWeight){
                        toAchieveGoal = curWeight - goal;

                        //retrieve 2 decimal places for to achieve goal
                        @SuppressLint("DefaultLocale") String formattedString2 = String.format("%.02f", toAchieveGoal);
                        holderToAchieveGoal = "You need to lose " + formattedString2 + " kilograms.";
                    }
                    else {
                        holderToAchieveGoal = "You have already achieved your goal!";
                    }

                    //Attaching variables to speech
                    speechCurWeight = holderCurWeight;
                    speechGoal = holderGoal;
                    speechToAchieveGoal = holderToAchieveGoal;

                    //String speech
                    String speechOverallGoal = "Your goal is, " + speechGoal + " kilograms, " + speechToAchieveGoal;

                    t1.speak(speechOverallGoal, TextToSpeech.QUEUE_FLUSH, null);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(indexActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    //function for cur vs prev weight
    private void readCurVsPrev(){
        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if (user !=null) {
                    float curWeight = user.curWeight;
                    float prevWeight = user.prevWeight;

                    holderCurWeight = curWeight + "";
                    holderPrevWeight = prevWeight + "";

                    //Current weight vs Previous Weight
                    float curVSprev;
                    String descVS;
                    if (curWeight > prevWeight) {
                        curVSprev = curWeight - prevWeight;
                        descVS = "You gained ";

                        //retrieve 2 decimal places for curVsPrev
                        @SuppressLint("DefaultLocale") String formattedString1 = String.format("%.02f", curVSprev);
                        holderCurVsPrev = descVS + formattedString1 + " kilograms.";
                    }
                    else if (curWeight < prevWeight){
                        curVSprev = prevWeight - curWeight;
                        descVS = "You lost ";

                        //retrieve 2 decimal places for curVsPrev
                        @SuppressLint("DefaultLocale") String formattedString1 = String.format("%.02f", curVSprev);
                        holderCurVsPrev = descVS + formattedString1 + " kilograms.";
                    }
                    else{
                        curVSprev = curWeight - prevWeight;
                        descVS = "There is no difference in weight ";

                        //retrieve 2 decimal places for curVsPrev
                        @SuppressLint("DefaultLocale") String formattedString1 = String.format("%.02f", curVSprev);
                        holderCurVsPrev = descVS + formattedString1 + " kilograms.";
                    }

                    //Attaching variables to speech
                    speechCurWeight = holderCurWeight;
                    speechPrevWeight = holderPrevWeight;
                    speechCurVsPrev = holderCurVsPrev;

                    //String speech
                    String speechOverallVS = "Your current weight is " + speechCurWeight + " kilograms, and your previous weight is " + speechPrevWeight + "kilograms. " + speechCurVsPrev;
                    t1.speak(speechOverallVS, TextToSpeech.QUEUE_FLUSH, null);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(indexActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    //function to read and say who am i
    private void readWhoAmI() {
        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if (user !=null) {
                    String firstName = user.firstName;
                    String lastName = user.lastName;
                    String gender = user.gender;
                    String fullName = firstName + " " + lastName;

                    //set greeting based on gender
                    String speechGreeting;
                    if (gender.equals("Male")) {
                        speechGreeting = "You're the handsome " + fullName;
                    }
                    else if (gender.equals("Female")) {
                        speechGreeting = "You're the pretty " + fullName;
                    }
                    else {
                        speechGreeting = "You're my bestfriend " + fullName;
                    }

                    t1.speak(speechGreeting, TextToSpeech.QUEUE_FLUSH, null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(indexActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}