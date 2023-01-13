package com.example.fitcare_java;

import android.annotation.SuppressLint;
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

public class settingsUserEdit extends Fragment {

    EditText etFirstName, etLastName, etAge;
    ImageView btnBack;
    TextView btnUpdate;

    //firebase
    FirebaseUser user;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    String uid;

    //Holder
    String newFirstName, newLastName;
    int newAge;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings_user_edit, container, false);

        //setting variables
        etFirstName = view.findViewById(R.id.etFirstName);
        etLastName = view.findViewById(R.id.etLastName);
        etAge = view.findViewById(R.id.etAge);
        btnBack = view.findViewById(R.id.btnBack);
        btnUpdate = view.findViewById(R.id.btnUpdate);

        //firebase instance
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        uid = user.getUid();

        //read data
        readData();

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
                    newFirstName = etFirstName.getText().toString().trim();
                    newLastName = etLastName.getText().toString().trim();
                    newAge = Integer.parseInt(etAge.getText().toString().trim());

                    //update data
                    updateData(newFirstName, newLastName,newAge);
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
                    String firstName = user.firstName;
                    String lastName = user.lastName;
                    int age = user.age;

                    etFirstName.setText(firstName);
                    etLastName.setText(lastName);
                    etAge.setText(age + "");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    //to update data
    private void updateData(String newFirstName, String newLastName, int newAge) {

        HashMap update = new HashMap();

        update.put("firstName", newFirstName);
        update.put("lastName", newLastName);
        update.put("age" , newAge);

        databaseReference.child(uid).updateChildren(update).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

                if (task.isSuccessful()){
                    etFirstName.setText("");
                    etLastName.setText("");
                    etAge.setText("");
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
        if (etFirstName.getText().toString().length() == 0){
            Toast.makeText(getActivity(), "Please enter new first name", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (etLastName.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Please enter new last name", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (etAge.getText().toString().length() == 0){
            Toast.makeText(getActivity(), "Please enter new last name", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (!etAge.getText().toString().matches("[0-9]*\\.?[0-9]+") || !etAge.getText().toString().matches("[0-9]+")){
            Toast.makeText(getActivity(), "Please enter a valid age", Toast.LENGTH_LONG).show();
            return  false;
        }
        return true;
    }
}