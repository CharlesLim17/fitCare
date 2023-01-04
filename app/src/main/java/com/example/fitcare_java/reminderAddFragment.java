package com.example.fitcare_java;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;


public class reminderAddFragment extends Fragment {

    //declaring variables
    private NumberPicker numPickerHour, numPickerMin, numPickerAm;
    private TextView txtDisplayTimeAdd;
    private EditText etTaskName;
    private ImageView btnBack;
    private TextView btnAdd;
    private String title, message, taskName, time;

    //firebase
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseUser user;
    String uid;

    //declaring NotificationHelper and AlarmManager class
    private NotificationHelper notificationHelper;
    private AlarmManager alarmManager = null;

    //storing hour/min/am_pm values to respective variables
    private int hour = Calendar.getInstance().get(Calendar.HOUR);
    private int min = Calendar.getInstance().get(Calendar.MINUTE);
    private int am_pm = Calendar.getInstance().get(Calendar.AM_PM);
    private int id;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reminder_add, container, false);

        // instantiate NotificationHelper class
        notificationHelper = new NotificationHelper(getActivity());

        //setting variables
        numPickerHour = view.findViewById(R.id.numPickerHour);
        numPickerMin = view.findViewById(R.id.numPickerMin);
        numPickerAm = view.findViewById(R.id.numPickerAm);
        txtDisplayTimeAdd = view.findViewById(R.id.txtDisplayTimeAdd);
        etTaskName = view.findViewById(R.id.etTaskName);
        btnBack = view.findViewById(R.id.btnBack);
        btnAdd = view.findViewById(R.id.btnAdd);

        //firebase
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        uid = user.getUid();

        //setting numpicker for hour
        numPickerHour.setMinValue(0);
        numPickerHour.setMaxValue(12);
        numPickerHour.setValue(hour);

        //setting numpicker for min
        numPickerMin.setMinValue(0);
        numPickerMin.setMaxValue(59);
        numPickerMin.setValue(min);

        //setting am pm
        NumPicker.initNumPicker();
        numPickerAm.setMaxValue(NumPicker.getNumPickerList().size() - 1);
        numPickerAm.setMinValue(0);
        numPickerAm.setDisplayedValues(NumPicker.numPickerNames());

        txtDisplayTimeAdd.setText(String.format("Time: %s : %s %s", hour, min, NumPicker.getNumPickerList().get(am_pm).getName()));

        numPickerHour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                txtDisplayTimeAdd.setText(String.format("Time: %s : %s %s", i1, min, NumPicker.getNumPickerList().get(am_pm).getName()));
                hour = i1;
            }
        });

        numPickerMin.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                txtDisplayTimeAdd.setText(String.format("Time: %s : %s %s", hour, i1, NumPicker.getNumPickerList().get(am_pm).getName()));
                min = i1;
            }
        });

        numPickerAm.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                txtDisplayTimeAdd.setText(String.format("Time: %s : %s %s", hour, min, NumPicker.getNumPickerList().get(i1).getName()));
                am_pm = i1;
            }
        });

        //back onclick
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment reminderFrag = new reminderFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, reminderFrag).commit();
            }
        });

        //add onclick
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInputAlarmFields()) {
                    Fragment reminderFrag = new reminderFragment();
                    FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
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
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM, NumPicker.getNumPickerList().get(am_pm).getId());

        Calendar calendarForTom = Calendar.getInstance();
        if (calendar.get(Calendar.HOUR_OF_DAY) <= calendarForTom.get(Calendar.HOUR)) {
            calendarForTom.set(Calendar.HOUR, hour);
            calendarForTom.set(Calendar.MINUTE, minute);
            calendarForTom.set(Calendar.SECOND, 0);
            calendarForTom.set(Calendar.AM_PM, NumPicker.getNumPickerList().get(am_pm).getId());

            calendarForTom.add(calendarForTom.DATE, 1);
            toastTimeText(calendarForTom);
            setAlarmTitleMessage(calendarForTom);
            startAlarm(calendarForTom);
            taskAlarmUpload();
        } else {
            toastTimeText(calendar);
            setAlarmTitleMessage(calendar);
            startAlarm(calendar);
            taskAlarmUpload();
        }
    }

    // toast message
    private void toastTimeText(Calendar cal) {
        String timeText = "Alarm set for: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(cal.getTime());
        Toast.makeText(getActivity(), "" + timeText, Toast.LENGTH_SHORT).show();
    }

    // alarm Notification Title and Message
    private void setAlarmTitleMessage(Calendar cal) {
        //for displaying in Workout Reminder
        taskName = etTaskName.getText().toString();
        time = DateFormat.getTimeInstance(DateFormat.SHORT).format(cal.getTime());

        //for notification
        title = "Workout Reminder";
        message = "Task: " + etTaskName.getText().toString() + " at " + time;
    }

    // starting alarm
    private void startAlarm(Calendar cal) {
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlertReceiver.class);

        intent.putExtra("TITLE", title);
        intent.putExtra("MESSAGE", message);

        id = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }

    //to upload task reminder
    private void taskAlarmUpload() {
        HashMap upload = new HashMap();

        upload.put("taskName", taskName);
        upload.put("time", time);
        upload.put("id", id);
        upload.put("hour", hour);
        upload.put("minute", min);
        upload.put("am_pm", am_pm);

        databaseReference.child(uid).child("alarms").push().setValue(upload);
    }

    // check for null fields
    private boolean checkInputAlarmFields() {
        if (etTaskName.getText().length() == 0) {
            Toast.makeText(getActivity(), "Please Enter a Workout Task", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    
    // check and get alarm manager
    public AlarmManager getAlarmManager() {
        if (alarmManager == null) {
            alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        }
        return alarmManager;
    }
}