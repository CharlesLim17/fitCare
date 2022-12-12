package com.example.fitcare_java;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;

import com.example.fitcare_java.databinding.ActivityIndexBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Locale;

public class indexActivity extends AppCompatActivity {

    //view binding
    ActivityIndexBinding binding;
    FloatingActionButton btnMic;

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

            //voice command to navigate to exercise page
            if (arrayList.get(0).toString().equals("go to exercise") || arrayList.get(0).toString().equals("open exercise")) {
                Fragment exerciseLevelFrag = new exerciseLevelFragment();
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, exerciseLevelFrag).commit();
            }

            //voice command to navigate to exercise low
            if (arrayList.get(0).toString().equals("go to exercise low") || arrayList.get(0).toString().equals("open exercise low") || arrayList.get(0).toString().equals("open low exercise") || arrayList.get(0).toString().equals("go to low exercise")) {
                Fragment exerciseLowFrag = new exerciseLowFragment();
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, exerciseLowFrag).commit();
            }

            //voice command to navigate to exercise moderate
            if (arrayList.get(0).toString().equals("go to exercise moderate") || arrayList.get(0).toString().equals("open exercise moderate") || arrayList.get(0).toString().equals("open moderate exercise") || arrayList.get(0).toString().equals("go to moderate exercise")) {
                Fragment exerciseModerateFrag = new exerciseModerateFragment();
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, exerciseModerateFrag).commit();
            }

            //voice command to navigate to exercise vigorous
            if (arrayList.get(0).toString().equals("go to exercise vigorous") || arrayList.get(0).toString().equals("open exercise vigorous") || arrayList.get(0).toString().equals("open vigorous exercise") || arrayList.get(0).toString().equals("go to vigorous exercise")) {
                Fragment exerciseVigorousFrag = new exerciseVigorousFragment();
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, exerciseVigorousFrag).commit();
            }

            //voice command to navigate to settings
            if (arrayList.get(0).toString().equals("go to settings") || arrayList.get(0).toString().equals("open settings")) {
                Fragment settingsFrag = new settingsFragment();
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, settingsFrag).commit();
            }

            //voice command to navigate to about
            if (arrayList.get(0).toString().equals("go to about") || arrayList.get(0).toString().equals("open about")) {
                Fragment aboutFrag = new aboutFragment();
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, aboutFrag).commit();
            }

            //voice command to navigate to privacy
            if (arrayList.get(0).toString().equals("go to privacy") || arrayList.get(0).toString().equals("open privacy")) {
                Fragment privacyFrag = new privacyFragment();
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, privacyFrag).commit();
            }

            //voice command to navigate to meal plan
            if (arrayList.get(0).toString().equals("go to meal plan") || arrayList.get(0).toString().equals("open meal plan")) {
                Fragment mealPlanFrag = new mealPlanFragment();
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, mealPlanFrag).commit();
            }

            //voice command to navigate to tracker page
            if (arrayList.get(0).toString().equals("go to workout tracker") || arrayList.get(0).toString().equals("open workout tracker") || arrayList.get(0).toString().equals("go to tracker") || arrayList.get(0).toString().equals("open tracker")) {
                Fragment trackerFrag = new trackerFragment();
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, trackerFrag).commit();
            }

            //voice command to navigate to stretches page
            if (arrayList.get(0).toString().equals("go to workout stretches") || arrayList.get(0).toString().equals("open workout stretches") || arrayList.get(0).toString().equals("go to stretches") || arrayList.get(0).toString().equals("open stretches") || arrayList.get(0).toString().equals("go to warmup stretches") || arrayList.get(0).toString().equals("open warmup stretches") || arrayList.get(0).toString().equals("go to warmup") || arrayList.get(0).toString().equals("open warmup")) {
                Fragment warmupFrag = new warmupLevelFragment();
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, warmupFrag).commit();
            }

            //voice command to navigate to stretches low
            if (arrayList.get(0).toString().equals("go to workout stretches low") || arrayList.get(0).toString().equals("open workout stretches low") || arrayList.get(0).toString().equals("go to stretches low") || arrayList.get(0).toString().equals("open stretches low") || arrayList.get(0).toString().equals("go to warmup stretches low") || arrayList.get(0).toString().equals("open warmup stretches low") || arrayList.get(0).toString().equals("go to warmup low") || arrayList.get(0).toString().equals("open warmup low")) {
                Fragment warmupLowFrag = new warmupLowFragment();
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, warmupLowFrag).commit();
            }

            //voice command to navigate to stretches moderate
            if (arrayList.get(0).toString().equals("go to workout stretches moderate") || arrayList.get(0).toString().equals("open workout stretches moderate") || arrayList.get(0).toString().equals("go to stretches moderate") || arrayList.get(0).toString().equals("open stretches moderate") || arrayList.get(0).toString().equals("go to warmup stretches moderate") || arrayList.get(0).toString().equals("open warmup stretches moderate") || arrayList.get(0).toString().equals("go to warmup moderate") || arrayList.get(0).toString().equals("open warmup moderate")) {
                Fragment warmupModerateFrag = new warmupModerateFragment();
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, warmupModerateFrag).commit();
            }

            //voice command to navigate to stretches vigorous
            if (arrayList.get(0).toString().equals("go to workout stretches vigorous") || arrayList.get(0).toString().equals("open workout stretches vigorous") || arrayList.get(0).toString().equals("go to stretches vigorous") || arrayList.get(0).toString().equals("open stretches vigorous") || arrayList.get(0).toString().equals("go to warmup stretches vigorous") || arrayList.get(0).toString().equals("open warmup stretches vigorous") || arrayList.get(0).toString().equals("go to warmup vigorous") || arrayList.get(0).toString().equals("open warmup vigorous")) {
                Fragment warmupVigorousFrag = new warmupVigorousFragment();
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, warmupVigorousFrag).commit();
            }

            //voice command to navigate to reminders page
            if (arrayList.get(0).toString().equals("go to workout reminders") || arrayList.get(0).toString().equals("open workout reminders") || arrayList.get(0).toString().equals("go to reminders") || arrayList.get(0).toString().equals("open reminders")) {
                Fragment remindersFrag = new reminderFragment();
                FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, remindersFrag).commit();
            }

        }
    }
}