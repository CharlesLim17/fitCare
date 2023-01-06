package com.example.fitcare_java;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class settingsEditFragment extends Fragment {

    //declare variables
    EditText etHeight;
    ImageView btnBack;
    TextView btnUpdate;
    TextView btnTrackerEdit;

    //firebase
    FirebaseUser user;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    String uid;

    //Holder
    float newHeight;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings_edit, container, false);

        //setting variables
        etHeight = view.findViewById(R.id.etHeight);
        btnBack = view.findViewById(R.id.btnBack);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnTrackerEdit = view.findViewById(R.id.btnTrackerEdit);

        //spanning string in front end
        SpannableStringBuilder spannable = new SpannableStringBuilder("Note: To edit weight and goal weight. Click here.");
        spannable.setSpan(
                new ForegroundColorSpan(Color.rgb(51,153,255)),
                38, // start
                49, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        );

        //setting spanned string
        btnTrackerEdit.setText(spannable);

        //firebase instance
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        uid = user.getUid();

        //read data
        readData();

        //tracker edit onclick
        btnTrackerEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment trackerEditFrag = new trackerEditFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, trackerEditFrag, null).addToBackStack(null).commit();
            }
        });


        //Back onclick
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment settingsFrag = new settingsFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, settingsFrag, null).addToBackStack(null).commit();
            }
        });

        //Update onclick
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check fields
                if (error()){
                    //get text to string
                    newHeight= Float.parseFloat(etHeight.getText().toString().trim());

                    //update data
                    updateData(newHeight);
                }
            }
        });

        return view;
    }

    //to read data
    private void readData() {
        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if (user !=null) {
                    float height = user.height;
                    etHeight.setText(height + "");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    //to update data
    private void updateData(float newHeight) {

        HashMap update = new HashMap();

        update.put("height", newHeight);

        databaseReference.child(uid).updateChildren(update).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

                if (task.isSuccessful()){
                    etHeight.setText("");
                    Toast.makeText(getActivity(), "Succesfully Updated", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(), "Update failed", Toast.LENGTH_SHORT).show();
                }
                Fragment settingsFrag = new settingsFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, settingsFrag, null).addToBackStack(null).commit();
            }
        });
    }

    //check fields
    private boolean error() {
        if (etHeight.getText().toString().length() == 0){
            Toast.makeText(getActivity(), "Please enter new height", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (!etHeight.getText().toString().matches("[0-9]*\\.?[0-9]+") || !etHeight.getText().toString().matches("[0-9]+")){
            Toast.makeText(getActivity(), "Please enter a valid height", Toast.LENGTH_LONG).show();
            return  false;
        }
        return true;
    }
}