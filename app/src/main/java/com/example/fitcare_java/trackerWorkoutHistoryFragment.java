package com.example.fitcare_java;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class trackerWorkoutHistoryFragment extends Fragment {

    //declaring variables
    ImageView btnBack;
    ImageView btnDelete;

    //Recycler view
    RecyclerView recyclerView;
    ArrayList<WatchedHistory> list;
    VideoAdapter adapter;

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
        View view = inflater.inflate(R.layout.fragment_tracker_workout_history, container, false);

        //setting variables
        btnBack = view.findViewById(R.id.btnBack);
        recyclerView = view.findViewById(R.id.recycleview);
        btnDelete = view.findViewById(R.id.btnDelete);

        //initiate loading dialog
        LoadingDialog loadingDialog = new LoadingDialog(getActivity());

        //Start Loading Dialog
        loadingDialog.startLoadingDialog();

        //End Load Dialog
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismissDialog();
            }
        }, 2500);

        //firebase
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        uid = user.getUid();

        //setup recyclerview
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new VideoAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);

        //retrieve history
        readWatchedHistory();

        //delete onclick
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteHistory();

                Fragment trackerWorkoutHistoryFrag = new trackerWorkoutHistoryFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, trackerWorkoutHistoryFrag).commit();
            }
        });

        //back onclick
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment trackerFrag = new trackerFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, trackerFrag, null).addToBackStack(null).commit();
            }
        });

        return view;
    }

    //function to delete all in history
    private void deleteHistory() {
        databaseReference.child(uid).child("watchedVideos").removeValue();
    }

    //funtion to retrieve data
    private void readWatchedHistory() {
        databaseReference.child(uid).child("watchedVideos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                    WatchedHistory watchedHistory = dataSnapshot.getValue(WatchedHistory.class);
                    list.add(watchedHistory);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}