package com.example.fitcare_java;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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



public class trackerEditFragment extends Fragment {

    //declaring variables
    ImageView btnBack;
    TextView btnUpdate, txtRetrieveCur, txtRetrievePrev, txtRetrieveGoal;
    EditText etNewGoal, etNewWeight, etNewPrevWeight;


    //firebase
    FirebaseUser user;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    String uid;

    //read and update data
    float newCurWeight , newPrevWeight, newGoal ;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tracker_edit, container, false);

        //setting variables
        btnBack = view.findViewById(R.id.btnBack);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        etNewGoal = view.findViewById(R.id.etNewGoal);
        etNewWeight = view.findViewById(R.id.etNewWeight);
        etNewPrevWeight = view.findViewById(R.id.etNewPrevWeight);
        txtRetrieveCur = view.findViewById(R.id.txtRetrieveCur);
        txtRetrievePrev = view.findViewById(R.id.txtRetrievePrev);
        txtRetrieveGoal = view.findViewById(R.id.txtRetrieveGoal);


        //firebase instance
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        uid = user.getUid();

        //read data
        readData();

        //back onclick
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment trackerFrag = new trackerFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, trackerFrag, null).addToBackStack(null).commit();
            }
        });

        //update onclick
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check fields
                if (error()){
                    //get text to string
                    newCurWeight = Float.parseFloat(etNewWeight.getText().toString().trim());
                    newGoal = Float.parseFloat(etNewGoal.getText().toString().trim());
                    newPrevWeight = Float.parseFloat(etNewPrevWeight.getText().toString().trim());

                    //update data
                    updateData(newCurWeight, newGoal, newPrevWeight);
                }
            }
        });

        return view;
    }

    //to user update data in firebase
    private void updateData(float newCurWeight, float newGoal, float newPrevWeight) {

        HashMap update = new HashMap();

        update.put("prevWeight", newPrevWeight);
        update.put("curWeight", newCurWeight);
        update.put("goal", newGoal);

        databaseReference.child(uid).updateChildren(update).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

                if (task.isSuccessful()){
                    etNewGoal.setText("");
                    etNewWeight.setText("");
                    Toast.makeText(getActivity(), "Succesfully Updated", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(), "Update failed", Toast.LENGTH_SHORT).show();
                }

                Fragment trackerFrag = new trackerFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, trackerFrag, null).addToBackStack(null).commit();
            }
        });

    }

    //read data
    private void readData(){
        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if (user !=null) {
                    float curWeight = user.curWeight;
                    float prevWeight = user.prevWeight;
                    float goal = user.goal;

                    etNewPrevWeight.setText(curWeight + "");
                    etNewGoal.setText(goal + "");
                    txtRetrieveCur.setText("(Your Current Weight: " + curWeight + " kgs)");
                    txtRetrievePrev.setText("(Your Previous Weight: " + prevWeight + " kgs)");
                    txtRetrieveGoal.setText("(Your Goal: " + goal + " kgs)");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    //check fields
    private boolean error() {
        if (etNewWeight.getText().toString().length() == 0){
            Toast.makeText(getActivity(), "Please enter new weight", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (etNewPrevWeight.getText().toString().length() == 0){
            Toast.makeText(getActivity(), "Please enter previous weight", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (etNewGoal.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Please enter new goal", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (!etNewWeight.getText().toString().matches("[0-9]*\\.?[0-9]+")){
            Toast.makeText(getActivity(), "Please enter a valid weight", Toast.LENGTH_LONG).show();
            return  false;
        }
        else if (!etNewPrevWeight.getText().toString().matches("[0-9]*\\.?[0-9]+")){
            Toast.makeText(getActivity(), "Please enter a valid weight", Toast.LENGTH_LONG).show();
            return  false;
        }
        else if (!etNewGoal.getText().toString().matches("[0-9]*\\.?[0-9]+")){
            Toast.makeText(getActivity(), "Please enter a valid weight", Toast.LENGTH_LONG).show();
            return  false;
        }
        return true;
    }
}