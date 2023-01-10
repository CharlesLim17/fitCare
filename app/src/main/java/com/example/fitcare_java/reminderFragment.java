package com.example.fitcare_java;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class reminderFragment extends Fragment  implements RecyclerViewInterface {

    //declaring variables
    private ImageView btnAdd, btnBack;
    private TextView dateDisplay, txtRetrieveTime;
    private RecyclerView alarmRecyclerView;
    private AlarmAdapter alarmAdapter;
    private ArrayList<AlarmHistory> alarms;
    private WorkoutReminderViewModel viewModel;

    //firebase
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseUser user;
    String uid;

    private String taskName;
    private int id;

    //live retrieving/updating alarms in recycler view
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(WorkoutReminderViewModel.class);
        viewModel.getItems().observe(this, new Observer<List<AlarmHistory>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<AlarmHistory> items) {
                alarms.clear();
                alarms.addAll(items);
                alarmAdapter.notifyDataSetChanged();
            }
        });
        alarms = new ArrayList<>();
    }

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

        // displaying date
        dateDisplay.setText(currentDate);

        // display multiple alarms
        alarmRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        alarmAdapter = new AlarmAdapter(getActivity(), alarms, this);
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
        return view;
    }

    // retrieve alarms
    private void readAlarmHistory() {
        databaseReference.child(uid).child("alarms").addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
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
        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.frameLayout, reminderEditFrag).commit();
        passValues(reminderEditFrag, fm, position);
    }

    // delete data in recyclerview and in database
    @Override
    public void onItemLongClick(int position) {
        taskName = alarms.get(position).getTaskName();
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
        PendingIntent pendingIntent = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(getActivity(), id, intent, PendingIntent.FLAG_IMMUTABLE);

        } else {
            pendingIntent = PendingIntent.getBroadcast(getActivity(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        }

        if (reminderAddFrag != null) {
            reminderAddFrag.getAlarmManager().cancel(pendingIntent);
        }
        pendingIntent.cancel();
    }

    public void passValues(Fragment reminderEditFrag, FragmentTransaction fm, int position) {
        String taskName = alarms.get(position).getTaskName();
        String time = alarms.get(position).getTime();
        int id = alarms.get(position).getId();
        int hour = alarms.get(position).getHour();
        int min = alarms.get(position).getMinute();
        int am_pm = alarms.get(position).getAm_pm();

        Bundle bundle = new Bundle();
        bundle.putString("taskName", taskName);
        bundle.putString("time", time);
        bundle.putInt("id", id);
        bundle.putInt("hour", hour);
        bundle.putInt("min", min);
        bundle.putInt("am_pm", am_pm);
        reminderEditFrag.setArguments(bundle);
    }
}