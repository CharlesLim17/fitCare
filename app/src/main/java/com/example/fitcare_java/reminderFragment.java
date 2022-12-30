package com.example.fitcare_java;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.BufferUnderflowException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;


public class reminderFragment extends Fragment  implements RecyclerViewInterface{

    //declaring variables
    ImageView btnAdd, btnBack;
    TextView dateDisplay, txtRetrieveTime;
    RecyclerView alarmRecyclerView;
    AlarmAdapter alarmAdapter;
    ArrayList<AlarmHistory> alarms;

    //firebase
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseUser user;
    String uid;

    private String taskName, time;
    private int id, hour, min, am_pm;

    @SuppressLint("MissingInflatedId")
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
        txtRetrieveTime = view.findViewById(R.id.txtRetrieveTime);
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
        //alarmIsTriggered();

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

    // retrieve alarms
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

    // opening edit Fragment
    @Override
    public void onItemClick(int position) {
        Fragment reminderEditFrag = new reminderEditFragment();

        taskName = alarms.get(position).getTaskName();
        time = alarms.get(position).getTime();
        id = alarms.get(position).getId();
        hour = alarms.get(position).getHour();
        min = alarms.get(position).getMinute();
        am_pm = alarms.get(position).getAm_pm();

        Bundle bundle = new Bundle();
        bundle.putString("taskName", taskName);
        bundle.putString("time", time);
        bundle.putInt("id", id);
        bundle.putInt("hour", hour);
        bundle.putInt("min", min);
        bundle.putInt("am_pm", am_pm);
        reminderEditFrag.setArguments(bundle);


        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.frameLayout, reminderEditFrag).commit();
    }

    // delete data in recyclerview and in database
    @Override
    public void onItemLongClick(int position) {
        taskName = alarms.get(position).getTaskName();
        time = alarms.get(position).getTime();
        id = alarms.get(position).getId();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Workout Reminder");
        builder.setMessage("Are you sure you want to remove selected Alarm?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alarms.remove(position);
                alarmAdapter.notifyItemRemoved(position);
                cancelAlarm(id);

            databaseReference.child(uid).child("alarms").orderByChild("taskName").equalTo(taskName).addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            dataSnapshot.getRef().removeValue();
                        }
                        alarmAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "Alarm Removed", Toast.LENGTH_SHORT).show();
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

    // canceling alarm
    public void cancelAlarm(int id) {
        reminderAddFragment reminderAddFrag = (reminderAddFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.reminder_add);

        Intent intent = new Intent(getActivity(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if(reminderAddFrag != null) {
            reminderAddFrag.getAlarmManager().cancel(pendingIntent);
        }
        pendingIntent.cancel();
    }

    @SuppressLint("SetTextI18n")
    public void alarmIsTriggered() {
        Calendar cal = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("h:mm a");
        String curTime = format.format(cal.getTime());

        for (int i = 0; i < alarms.size(); i++) {
            if(Objects.equals(alarms.get(i).getTime(), curTime)) {
                txtRetrieveTime.setText(curTime + " (Done)");
            }
        }
    }

    public void sendData() {
        reminderEditFragment reminderEditFrag = new reminderEditFragment();

        Bundle bundle = new Bundle();
        bundle.putString("TASKNAME", taskName);
        bundle.putString("TIME", time);
        bundle.putInt("ID", id);
        reminderEditFrag.setArguments(bundle);
    }
}