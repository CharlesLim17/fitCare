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


        return view;
    }

}