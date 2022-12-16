package com.example.fitcare_java;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class reminderFragment extends Fragment {

    //declaring variables
    ImageView btnAdd, btnBack;
    TextView btnEdit1, btnEdit2, btnEdit3, dateDisplay;
    RecyclerView alarmRecyclerView;
    AlarmAdapter alarmAdapter;
    ArrayList<AlarmHistory> alarms;

    private AlarmHistory alarmHistory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reminder, container, false);

        //display date
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        //setting variables
        alarmRecyclerView = view.findViewById(R.id.alarmRecyclerView);
        dateDisplay = view.findViewById(R.id.dateDisplay);
        btnBack = view.findViewById(R.id.btnBack);
        btnAdd = view.findViewById(R.id.btnAdd);
        //btnEdit1 = view.findViewById(R.id.btnEdit1);
        //btnEdit2 = view.findViewById(R.id.btnEdit2);
        //btnEdit3 = view.findViewById(R.id.btnEdit3);

        // displaying date
        dateDisplay.setText(currentDate);

        // display multiple alarms
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        alarmRecyclerView.setLayoutManager(linearLayoutManager);
        alarms = new ArrayList<>();
        alarmAdapter = new AlarmAdapter(getActivity(), alarms);
        alarmHistory();
        alarmRecyclerView.setAdapter(alarmAdapter);


        //back onclick
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment homeFrag = new homeFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, homeFrag).commit();
            }
        });

        //add onclick
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment reminderAddFrag = new reminderAddFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, reminderAddFrag).commit();
            }
        });

//        //edit1 onclick
//        btnEdit1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Fragment reminderEditFrag = new reminderEditFragment();
//                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
//                fm.replace(R.id.frameLayout, reminderEditFrag).commit();
//            }
//        });
//
//        //edit2 onlick
//        btnEdit2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Fragment reminderEditFrag = new reminderEditFragment();
//                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
//                fm.replace(R.id.frameLayout, reminderEditFrag).commit();
//            }
//        });
//
//        //edit3 onclick
//        btnEdit3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Fragment reminderEditFrag = new reminderEditFragment();
//                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
//                fm.replace(R.id.frameLayout, reminderEditFrag).commit();
//            }
//        });

        return view;
    }

    private void alarmHistory() {
        alarmHistory = new AlarmHistory(reminderAddFragment.getTitle(), reminderAddFragment.getMessage());
        alarms.add(alarmHistory);
        alarmAdapter.notifyItemInserted(alarms.size()-1);
    }
}