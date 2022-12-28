package com.example.fitcare_java;


import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.Locale;


public class finFragment extends Fragment {

    //declare text to speech variable
    private TextToSpeech t1;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fin, container, false);

        //initialize text to speech
        t1 = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR)
                    t1.setLanguage(Locale.ENGLISH);
            }
        });

        //==========================================================================================

        //declaring and setting variables exercise
        ImageButton btnGoToExercise = view.findViewById(R.id.btnGoToExercise);
        ImageButton btnOpenExercise = view.findViewById(R.id.btnOpenExercise);
        ImageButton btnGoToExerciseLow = view.findViewById(R.id.btnGoToExerciseLow);
        ImageButton btnGoToLowExercise = view.findViewById(R.id.btnGoToLowExercise);
        ImageButton btnOpenExerciseLow = view.findViewById(R.id.btnOpenExerciseLow);
        ImageButton btnOpenLowExercise = view.findViewById(R.id.btnOpenLowExercise);
        ImageButton btnGoToExerciseModerate = view.findViewById(R.id.btnGoToExerciseModerate);
        ImageButton btnGoToModerateExercise = view.findViewById(R.id.btnGoToModerateExercise);
        ImageButton btnOpenExerciseModerate = view.findViewById(R.id.btnOpenExerciseModerate);
        ImageButton btnOpenModerateExercise = view.findViewById(R.id.btnOpenModerateExercise);
        ImageButton btnGoToExerciseVigorous = view.findViewById(R.id.btnGoToExerciseVigorous);
        ImageButton btnGoToVigorousExercise = view.findViewById(R.id.btnGoToVigorousExercise);
        ImageButton btnOpenExerciseVigorous = view.findViewById(R.id.btnOpenExerciseVigorous);
        ImageButton btnOpenVigorousExercise = view.findViewById(R.id.btnOpenVigorousExercise);

        TextView txtGoToExercise = view.findViewById(R.id.txtGoToExercise);
        TextView txtOpenExercise = view.findViewById(R.id.txtOpenExercise);
        TextView txtGoToExerciseLow = view.findViewById(R.id.txtGoToExerciseLow);
        TextView txtGoToLowExercise = view.findViewById(R.id.txtGoToLowExercise);
        TextView txtOpenExerciseLow = view.findViewById(R.id.txtOpenExerciseLow);
        TextView txtOpenLowExercise = view.findViewById(R.id.txtOpenLowExercise);
        TextView txtGoToExerciseModerate = view.findViewById(R.id.txtGoToExerciseModerate);
        TextView txtGoToModerateExercise = view.findViewById(R.id.txtGoToModerateExercise);
        TextView txtOpenExerciseModerate = view.findViewById(R.id.txtOpenExerciseModerate);
        TextView txtOpenModerateExercise = view.findViewById(R.id.txtOpenModerateExercise);
        TextView txtGoToExerciseVigorous = view.findViewById(R.id.txtGoToExerciseVigorous);
        TextView txtGoToVigorousExercise = view.findViewById(R.id.txtGoToVigorousExercise);
        TextView txtOpenExerciseVigorous = view.findViewById(R.id.txtOpenExerciseVigorous);
        TextView txtOpenVigorousExercise = view.findViewById(R.id.txtOpenVigorousExercise);

        //Exercise Page on click
        btnGoToExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderGoToExercise = txtGoToExercise.getText().toString();
                t1.speak(txtHolderGoToExercise, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnOpenExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderOpenExercise = txtOpenExercise.getText().toString();
                t1.speak(txtHolderOpenExercise, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        //Exercise low onclick
        btnGoToExerciseLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderGoToExerciseLow = txtGoToExerciseLow.getText().toString();
                t1.speak(txtHolderGoToExerciseLow, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnGoToLowExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderGoToLowExercise = txtGoToLowExercise.getText().toString();
                t1.speak(txtHolderGoToLowExercise, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnOpenExerciseLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderOpenExerciseLow = txtOpenExerciseLow.getText().toString();
                t1.speak(txtHolderOpenExerciseLow, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnOpenLowExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderOpenLowExercise = txtOpenLowExercise.getText().toString();
                t1.speak(txtHolderOpenLowExercise, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        //Exercise Moderate on Click
        btnGoToExerciseModerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderGoToExerciseModerate = txtGoToExerciseModerate.getText().toString();
                t1.speak(txtHolderGoToExerciseModerate, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnGoToModerateExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderGoToModerateExercise = txtGoToModerateExercise.getText().toString();
                t1.speak(txtHolderGoToModerateExercise, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnOpenExerciseModerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderOpenExerciseModerate = txtOpenExerciseModerate.getText().toString();
                t1.speak(txtHolderOpenExerciseModerate, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnOpenModerateExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderOpenModerateExercise = txtOpenModerateExercise.getText().toString();
                t1.speak(txtHolderOpenModerateExercise, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        //Exercise Vigorous onclick
        btnGoToExerciseVigorous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderGoToExerciseVigorous = txtGoToExerciseVigorous.getText().toString();
                t1.speak(txtHolderGoToExerciseVigorous, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnGoToVigorousExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderGoToVigorousExercise = txtGoToVigorousExercise.getText().toString();
                t1.speak(txtHolderGoToVigorousExercise, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnOpenExerciseVigorous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderOpenExerciseVigorous = txtOpenExerciseVigorous.getText().toString();
                t1.speak(txtHolderOpenExerciseVigorous, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnOpenVigorousExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderOpenVigorousExercise = txtOpenVigorousExercise.getText().toString();
                t1.speak(txtHolderOpenVigorousExercise, TextToSpeech.QUEUE_FLUSH, null);
            }
        });


        //==========================================================================================

        //declaring and setting variables
        ImageButton btnGoToSettings = view.findViewById(R.id.btnGoToSettings);
        ImageButton btnOpenSettings = view.findViewById(R.id.btnOpenSettings);
        ImageButton btnGoToAbout = view.findViewById(R.id.btnGoToAbout);
        ImageButton btnOpenAbout = view.findViewById(R.id.btnOpenAbout);
        ImageButton btnGoToPrivacy = view.findViewById(R.id.btnGoToPrivacy);
        ImageButton btnOpenPrivacy = view.findViewById(R.id.btnOpenPrivacy);

        TextView txtGoToSettings = view.findViewById(R.id.txtGoToSettings);
        TextView txtOpenSettings = view.findViewById(R.id.txtOpenSettings);
        TextView txtGoToAbout = view.findViewById(R.id.txtGoToAbout);
        TextView txtOpenAbout = view.findViewById(R.id.txtOpenAbout);
        TextView txtGoToPrivacy = view.findViewById(R.id.txtGoToPrivacy);
        TextView txtOpenPrivacy = view.findViewById(R.id.txtOpenPrivacy);

        //Settings page onclick
        btnGoToSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderGoToSettings = txtGoToSettings.getText().toString();
                t1.speak(txtHolderGoToSettings, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnOpenSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderOpenSettings = txtOpenSettings.getText().toString();
                t1.speak(txtHolderOpenSettings, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        //about page onclick
        btnGoToAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderGoToAbout = txtGoToAbout.getText().toString();
                t1.speak(txtHolderGoToAbout, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnOpenAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderOpenAbout = txtOpenAbout.getText().toString();
                t1.speak(txtHolderOpenAbout, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        //privacy page onclick
        btnGoToPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderGoToPrivacy = txtGoToPrivacy.getText().toString();
                t1.speak(txtHolderGoToPrivacy, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnOpenPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderOpenPrivacy = txtOpenPrivacy.getText().toString();
                t1.speak(txtHolderOpenPrivacy, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        //==========================================================================================

        //declaring and setting tracker variables
        ImageButton btnGoToWorkoutTracker = view.findViewById(R.id.btnGoToWorkoutTracker);
        ImageButton btnOpenWorkoutTracker = view.findViewById(R.id.btnOpenWorkoutTracker);
        ImageButton btnGoToTracker = view.findViewById(R.id.btnGoToTracker);
        ImageButton btnOpenTracker = view.findViewById(R.id.btnOpenTracker);
        ImageButton btnGoToWorkoutHistory = view.findViewById(R.id.btnGoToWorkoutHistory);
        ImageButton btnOpenWorkoutHistory = view.findViewById(R.id.btnOpenWorkoutHistory);
        ImageButton btnGoToTrackerHistory = view.findViewById(R.id.btnGoToTrackerHistory);
        ImageButton btnOpenTrackerHistory = view.findViewById(R.id.btnOpenTrackerHistory);

        TextView txtGoToWorkoutTracker = view.findViewById(R.id.txtGoToWorkoutTracker);
        TextView txtOpenWorkoutTracker = view.findViewById(R.id.txtOpenWorkoutTracker);
        TextView txtGoToTracker = view.findViewById(R.id.txtGoToTracker);
        TextView txtOpenTracker = view.findViewById(R.id.txtOpenTracker);
        TextView txtGoToWorkoutHistory = view.findViewById(R.id.txtGoToWorkoutHistory);
        TextView txtOpenWorkoutHistory = view.findViewById(R.id.txtOpenWorkoutHistory);
        TextView txtGoToTrackerHistory = view.findViewById(R.id.txtGoToTrackerHistory);
        TextView txtOpenTrackerHistory = view.findViewById(R.id.txtOpenTrackerHistory);

        //tracker page onclick
        btnGoToWorkoutTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderGoToWorkoutTracker = txtGoToWorkoutTracker.getText().toString();
                t1.speak(txtHolderGoToWorkoutTracker, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnOpenWorkoutTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderOpenWorkoutTracker = txtOpenWorkoutTracker.getText().toString();
                t1.speak(txtHolderOpenWorkoutTracker, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnGoToTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderGoToTracker = txtGoToTracker.getText().toString();
                t1.speak(txtHolderGoToTracker, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnOpenTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderOpenTracker = txtOpenTracker.getText().toString();
                t1.speak(txtHolderOpenTracker, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        //tracker history page onclick
        btnGoToWorkoutHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderGoToWorkoutHistory = txtGoToWorkoutHistory.getText().toString();
                t1.speak(txtHolderGoToWorkoutHistory, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnOpenWorkoutHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderOpenWorkoutHistory = txtOpenWorkoutHistory.getText().toString();
                t1.speak(txtHolderOpenWorkoutHistory, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnGoToTrackerHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderGoToTrackerHistory = txtGoToTrackerHistory.getText().toString();
                t1.speak(txtHolderGoToTrackerHistory, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnOpenTrackerHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderOpenTrackerHistory = txtOpenTrackerHistory.getText().toString();
                t1.speak(txtHolderOpenTrackerHistory, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        //==========================================================================================

        //declaring and setting variables for meal plan
        ImageButton btnGoToMealPlan = view.findViewById(R.id.btnGoToMealPlan);
        ImageButton btnOpenMealPlan = view.findViewById(R.id.btnOpenMealPlan);
        ImageButton btnGoToMeal = view.findViewById(R.id.btnGoToMeal);
        ImageButton btnOpenMeal = view.findViewById(R.id.btnOpenMeal);

        TextView txtGoToMealPlan = view.findViewById(R.id.txtGoToMealPlan);
        TextView txtOpenMealPlan = view.findViewById(R.id.txtOpenMealPlan);
        TextView txtGoToMeal = view.findViewById(R.id.txtGoToMeal);
        TextView txtOpenMeal = view.findViewById(R.id.txtOpenMeal);

        //meal plan pages onclick
        btnGoToMealPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderGoToMealPlan = txtGoToMealPlan.getText().toString();
                t1.speak(txtHolderGoToMealPlan, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnOpenMealPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderOpenMealPlan = txtOpenMealPlan.getText().toString();
                t1.speak(txtHolderOpenMealPlan, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnGoToMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderGoToMeal = txtGoToMeal.getText().toString();
                t1.speak(txtHolderGoToMeal, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnOpenMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderOpenMeal = txtOpenMeal.getText().toString();
                t1.speak(txtHolderOpenMeal, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        //==========================================================================================

        //declaring and setting variables for workout reminders
        ImageButton btnGoToWorkoutReminders = view.findViewById(R.id.btnGoToWorkoutReminders);
        ImageButton btnOpenWorkoutReminders = view.findViewById(R.id.btnOpenWorkoutReminders);
        ImageButton btnGoToReminders = view.findViewById(R.id.btnGoToReminders);
        ImageButton btnOpenReminders = view.findViewById(R.id.btnOpenReminders);

        TextView txtGoToWorkoutReminders = view.findViewById(R.id.txtGoToWorkoutReminders);
        TextView txtOpenWorkoutReminders = view.findViewById(R.id.txtOpenWorkoutReminders);
        TextView txtGoToReminders = view.findViewById(R.id.txtGoToReminders);
        TextView txtOpenReminders = view.findViewById(R.id.txtOpenReminders);

        //reminder pages onclick
        btnGoToWorkoutReminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderGoToWorkoutReminders = txtGoToWorkoutReminders.getText().toString();
                t1.speak(txtHolderGoToWorkoutReminders, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnOpenWorkoutReminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderOpenWorkoutReminders = txtOpenWorkoutReminders.getText().toString();
                t1.speak(txtHolderOpenWorkoutReminders, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnGoToReminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderGoToReminders = txtGoToReminders.getText().toString();
                t1.speak(txtHolderGoToReminders, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnOpenReminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderOpenReminders = txtOpenReminders.getText().toString();
                t1.speak(txtHolderOpenReminders, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        //==========================================================================================

        //declaring and setting warmup stretches variables
        ImageButton btnGoToWorkoutStretches = view.findViewById(R.id.btnGoToWorkoutStretches);
        ImageButton btnOpenWorkoutStretches = view.findViewById(R.id.btnOpenWorkoutStretches);
        ImageButton btnGoToStretches = view.findViewById(R.id.btnGoToStretches);
        ImageButton btnOpenStretches = view.findViewById(R.id.btnOpenStretches);
        ImageButton btnGoToWarmupStretches = view.findViewById(R.id.btnGoToWarmupStretches);
        ImageButton btnOpenWarmupStretches = view.findViewById(R.id.btnOpenWarmupStretches);
        ImageButton btnGoToWarmup = view.findViewById(R.id.btnGoToWarmup);
        ImageButton btnOpenWarmup = view.findViewById(R.id.btnOpenWarmup);

        TextView txtGoToWorkoutStretches = view.findViewById(R.id.txtGoToWorkoutStretches);
        TextView txtOpenWorkoutStretches = view.findViewById(R.id.txtOpenWorkoutStretches);
        TextView txtGoToStretches = view.findViewById(R.id.txtGoToStretches);
        TextView txtOpenStretches = view.findViewById(R.id.txtOpenStretches);
        TextView txtGoToWarmupStretches = view.findViewById(R.id.txtGoToWarmupStretches);
        TextView txtOpenWarmupStretches = view.findViewById(R.id.txtOpenWarmupStretches);
        TextView txtGoToWarmup = view.findViewById(R.id.txtGoToWarmup);
        TextView txtOpenWarmup = view.findViewById(R.id.txtOpenWarmup);

        //warmup stretches pages onclick
        btnGoToWorkoutStretches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderGoToWorkoutStretches = txtGoToWorkoutStretches.getText().toString();
                t1.speak(txtHolderGoToWorkoutStretches, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnOpenWorkoutStretches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderOpenWorkoutStretches = txtOpenWorkoutStretches.getText().toString();
                t1.speak(txtHolderOpenWorkoutStretches, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnGoToStretches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderGoToStretches = txtGoToStretches.getText().toString();
                t1.speak(txtHolderGoToStretches, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnOpenStretches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderOpenStretches = txtOpenStretches.getText().toString();
                t1.speak(txtHolderOpenStretches, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnGoToWarmupStretches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderGoToWarmupStretches = txtGoToWarmupStretches.getText().toString();
                t1.speak(txtHolderGoToWarmupStretches, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnOpenWarmupStretches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderOpenWarmupStretches = txtOpenWarmupStretches.getText().toString();
                t1.speak(txtHolderOpenWarmupStretches, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnGoToWarmup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderGoToWarmup = txtGoToWarmup.getText().toString();
                t1.speak(txtHolderGoToWarmup, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnOpenWarmup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtHolderOpenWarmup = txtOpenWarmup.getText().toString();
                t1.speak(txtHolderOpenWarmup, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        return view;
    }

}