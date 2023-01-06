package com.example.fitcare_java;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
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
import java.util.Calendar;
import java.util.HashMap;

public class reminderEditFragment extends Fragment{

    //declaring variables
    NumberPicker numPickerHour, numPickerMin, numPickerAm;
    TextView txtDisplayTimeEdit;
    ImageView btnBack;
    TextView btnUpdate;
    EditText etTaskNameEdit;
    RecyclerView alarmRecyclerView;

    //firebase
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseUser user;
    String uid;

    //declaring NotificationHelper and AlarmManager class
    private NotificationHelper notificationHelper;
    private AlarmManager alarmManager = null;

    //storing hour/min/am_pm values to respective variables
    private int id, hour, min, am_pm;
    private String title;
    private String message;
    private String taskName;
    private String time;

    @SuppressLint({"MissingInflatedId", "DefaultLocale"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reminder_edit, container, false);

        //setting variables
        numPickerHour = view.findViewById(R.id.numPickerHour);
        numPickerMin = view.findViewById(R.id.numPickerMin);
        numPickerAm = view.findViewById(R.id.numPickerAm);
        txtDisplayTimeEdit = view.findViewById(R.id.txtDisplayTimeEdit);
        etTaskNameEdit = view.findViewById(R.id.etTaskNameEdit);
        btnBack = view.findViewById(R.id.btnBack);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        alarmRecyclerView = view.findViewById(R.id.alarmRecyclerView);

        //firebase
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        uid = user.getUid();

        retrieveSpecificAlarm();

        //setting numpicker for hour
        numPickerHour.setMinValue(1);
        numPickerHour.setMaxValue(12);
        numPickerHour.setValue(hour);
        numPickerHour.setFormatter(new NumberPicker.Formatter() {
            @SuppressLint("DefaultLocale")
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });

        //setting numpicker for min
        numPickerMin.setMinValue(0);
        numPickerMin.setMaxValue(59);
        numPickerMin.setValue(min);
        numPickerMin.setFormatter(new NumberPicker.Formatter() {
            @SuppressLint("DefaultLocale")
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });

        //setting am pm
        NumPicker.initNumPicker();
        numPickerAm.setMaxValue(NumPicker.getNumPickerList().size() - 1);
        numPickerAm.setMinValue(0);
        numPickerAm.setDisplayedValues(NumPicker.numPickerNames());

        txtDisplayTimeEdit.setText(String.format("Time: %02d : %02d %s", hour, min, NumPicker.getNumPickerList().get(am_pm).getName()));

        numPickerHour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                txtDisplayTimeEdit.setText(String.format("Time: %02d : %02d %s", i1, min, NumPicker.getNumPickerList().get(am_pm).getName()));
                hour = i1;
            }
        });

        numPickerMin.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                txtDisplayTimeEdit.setText(String.format("Time: %02d : %02d %s", hour, i1, NumPicker.getNumPickerList().get(am_pm).getName()));
                min = i1;
            }
        });

        numPickerAm.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                txtDisplayTimeEdit.setText(String.format("Time: %s : %s %s", hour, min, NumPicker.getNumPickerList().get(i1).getName()));
                am_pm = i1;
            }
        });

        //back onclick
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment reminderFrag = new reminderFragment();
                FragmentTransaction fm = requireActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, reminderFrag).commit();
            }
        });

        //add onclick
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkInputAlarmFields()) {
                    Fragment reminderFrag = new reminderFragment();
                    FragmentTransaction fm = requireActivity().getSupportFragmentManager().beginTransaction();
                    fm.replace(R.id.frameLayout, reminderFrag).commit();
                    setAlarmTime(hour, min);
                }
            }
        });

        return view;
    }

    // setting alarm time
    private void setAlarmTime(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM, NumPicker.getNumPickerList().get(am_pm).getId());

        Calendar calendarForTom = Calendar.getInstance();
        if (calendar.get(Calendar.HOUR) <= calendarForTom.get(Calendar.HOUR)) {
            calendarForTom.set(Calendar.HOUR, hour);
            calendarForTom.set(Calendar.MINUTE, minute);
            calendarForTom.set(Calendar.SECOND, 0);
            calendarForTom.set(Calendar.AM_PM, NumPicker.getNumPickerList().get(am_pm).getId());

            calendarForTom.add(calendarForTom.DATE, 1);
            toastTimeText(calendarForTom);
            setAlarmTitleMessage(calendarForTom);
            startAlarm(calendarForTom);
            taskAlarmUpdate();
        } else {
            toastTimeText(calendar);
            setAlarmTitleMessage(calendar);
            startAlarm(calendar);
            taskAlarmUpdate();
        }
    }

    // toast message
    private void toastTimeText(Calendar cal) {
        String timeText = "Alarm updated for: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(cal.getTime());
        Toast.makeText(getActivity(), "" + timeText, Toast.LENGTH_SHORT).show();
    }

    // alarm Notification Title and Message
    private void setAlarmTitleMessage(Calendar cal) {
        //for displaying in Workout Reminder
        taskName = etTaskNameEdit.getText().toString();
        time = DateFormat.getTimeInstance(DateFormat.SHORT).format(cal.getTime());

        //for notification
        title = "Workout Reminder";
        message = "Task: " + etTaskNameEdit.getText().toString() + " at " + time;
    }

    // starting alarm
    private void startAlarm(Calendar cal) {
        alarmManager = (AlarmManager) requireActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlertReceiver.class);

        intent.putExtra("TITLE", title);
        intent.putExtra("MESSAGE", message);
        
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }

    //to update task reminder
    private void taskAlarmUpdate() {
        HashMap<String, Object> update = new HashMap<String, Object>();

        update.put("taskName", taskName);
        update.put("time", time);
        update.put("id", id);
        update.put("hour", hour);
        update.put("minute", min);
        update.put("am_pm", am_pm);

        databaseReference.child(uid).child("alarms").orderByChild("id").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    dataSnapshot.getRef().updateChildren(update);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled", error.toException());
            }
        });
    }

    // check for null fields
    private boolean checkInputAlarmFields() {
        if (etTaskNameEdit.getText().length() == 0) {
            Toast.makeText(getActivity(), "Please Enter a Workout Task", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void retrieveSpecificAlarm() {
        Bundle bundle = getArguments();

        if(bundle == null) {
            taskName = null;
            time = null;
            id = 0;
            hour = 0;
            min = 0;
            am_pm = 0;

        } else {
            taskName = bundle.getString("taskName");
            time = bundle.getString("time");
            id = bundle.getInt("id");
            hour = bundle.getInt("hour");
            min = bundle.getInt("min");
            am_pm = bundle.getInt("am_pm");
            etTaskNameEdit.setText(taskName, TextView.BufferType.EDITABLE);
        }
    }
}