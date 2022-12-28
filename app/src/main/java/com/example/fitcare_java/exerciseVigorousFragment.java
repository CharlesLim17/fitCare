package com.example.fitcare_java;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class exerciseVigorousFragment extends Fragment {

    //declaring variables
    ImageView btnBack;
    String videoPath;
    String dateToday;
    String currentTime;
    String title;
    TextView txtTitle;

    //firebase
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseUser user;
    String uid;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise_vigorous, container, false);

        //video view
        VideoView videoView = view.findViewById(R.id.videoView);
        String videoPath = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.comp_workoutvigorous;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(this.requireContext());
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        //to get date today
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("E yyyy.MM.dd");

        //to get current time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        //setting variables
        btnBack = view.findViewById(R.id.btnBack);
        txtTitle = view.findViewById(R.id.txtTitle);

        //firebase
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        uid = user.getUid();

        //to send video to history
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dateToday = ft.format(dNow).trim();
                currentTime = formatter.format(java.time.LocalTime.now()).trim();
                title = txtTitle.getText().toString().trim();

                videoUpload();
            }
        }, 5000);

        //back onclick
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment exerciseLevelFrag = new exerciseLevelFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, exerciseLevelFrag, null).addToBackStack(null).commit();
            }
        });

        return view;
    }

    //to upload video
    private void videoUpload() {
        HashMap upload = new HashMap();

        //upload.put("video", videoPath);
        upload.put("title", title);
        upload.put("date", dateToday);
        upload.put("time", currentTime);
        databaseReference.child(uid).child("watchedVideos").push().setValue(upload);
    }

}