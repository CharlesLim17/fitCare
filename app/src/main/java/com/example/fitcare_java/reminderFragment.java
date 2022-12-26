package com.example.fitcare_java;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class reminderFragment extends Fragment  implements RecyclerViewInterface{

    //declaring variables
    ImageView btnAdd, btnBack;
    TextView dateDisplay;
    RecyclerView alarmRecyclerView;
    AlarmAdapter alarmAdapter;
    ArrayList<AlarmHistory> alarms;

    //firebase
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseUser user;
    String uid;

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

        //firebase
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        uid = user.getUid();

        // displaying date
        dateDisplay.setText(currentDate);

        // display multiple alarms
        alarms = new ArrayList<>();
        alarmRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        alarmAdapter = new AlarmAdapter(getActivity(), alarms, this);
        alarmRecyclerView.setAdapter(alarmAdapter);

        readAlarmHistory();

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
        return view;
    }

    private void readAlarmHistory() {
        databaseReference.child(uid).child("alarms").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    AlarmHistory alarmHistory = dataSnapshot.getValue(AlarmHistory.class);
                    alarms.add(alarmHistory);
                }
                alarmAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled", error.toException());
            }
        });
    }

    @Override
    public void onItemClick(int position) {
//        Fragment reminderEditFrag = new reminderEditFragment();
//        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
//        fm.replace(R.id.frameLayout, reminderEditFrag).commit();
    }

    @Override
    public void onItemLongClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Workout Reminder");
        builder.setMessage("Are you sure you want to remove selected Alarm?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databaseReference.child(uid).child("alarms").orderByChild(String.valueOf(position)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            alarms.remove(position);
                            dataSnapshot.getRef().removeValue();
                        }
                        alarmAdapter.notifyItemRemoved(position);
                        alarmAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, "onCancelled", error.toException());
                    }
                });
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // do nothing
            }
        });
        builder.setIcon(R.drawable.ic_remove_dialog);
        builder.show();
    }
}