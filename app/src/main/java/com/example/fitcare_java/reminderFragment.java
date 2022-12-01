package com.example.fitcare_java;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class reminderFragment extends Fragment {

    //declaring variables
    ImageView btnAdd, btnBack;
    TextView btnEdit1, btnEdit2, btnEdit3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reminder, container, false);

        //setting variables
        btnBack = view.findViewById(R.id.btnBack);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnEdit1 = view.findViewById(R.id.btnEdit1);
        btnEdit2 = view.findViewById(R.id.btnEdit2);
        btnEdit3 = view.findViewById(R.id.btnEdit3);

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

        //edit1 onclick
        btnEdit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment reminderEditFrag = new reminderEditFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, reminderEditFrag).commit();
            }
        });

        //edit2 onlick
        btnEdit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment reminderEditFrag = new reminderEditFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, reminderEditFrag).commit();
            }
        });

        //edit3 onclick
        btnEdit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment reminderEditFrag = new reminderEditFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, reminderEditFrag).commit();
            }
        });


        return view;
    }
}