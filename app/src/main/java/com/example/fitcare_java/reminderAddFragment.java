package com.example.fitcare_java;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
    static String title, message;
    private static String taskName, time;

    //firebase
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseUser user;
    String uid;

    //declaring NotificationHelper class
    private NotificationHelper notificationHelper;

    //storing hour/min/am_pm values to respective variables
    static int hour = Calendar.getInstance().get(Calendar.HOUR);
    static int min = Calendar.getInstance().get(Calendar.MINUTE);
    static int am_pm;

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
        numPickerHour.setValue(Calendar.getInstance().get(Calendar.HOUR));

        //setting numpicker for min
        numPickerMin.setMinValue(0);
        numPickerMin.setMaxValue(59);
        numPickerMin.setValue(Calendar.getInstance().get(Calendar.MINUTE));

        //setting am pm
        NumPicker.initNumPicker();
        numPickerAm.setMaxValue(NumPicker.getNumPickerList().size() - 1);
        numPickerAm.setMinValue(0);
        numPickerAm.setDisplayedValues(NumPicker.numPickerNames());

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
                txtDisplayTimeAdd.setText(String.format("Time: %s : %s %s", hour, (int) min, NumPicker.getNumPickerList().get(i1).getName()));
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
                    taskAlarmUpload();
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

        toastTimeText(calendar);
        setAlarmTitleMessage(calendar);
        startAlarm(calendar);
    }

    // toast message
    private void toastTimeText(Calendar cal) {
        String timeText = "Alarm set for: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(cal.getTime());
        Toast.makeText(getActivity(), "" + timeText, Toast.LENGTH_SHORT).show();
    }

    // alarm Notification Title and Message
    private void setAlarmTitleMessage(Calendar cal) {
        String time = DateFormat.getTimeInstance(DateFormat.SHORT).format(cal.getTime());

        //for notification
        String title = "Workout Reminder";
        String message = "Task: " + etTaskName.getText().toString() + " at " + time;

        //for displaying in Workout Reminder
        String taskName = "Task: " + etTaskName.getText().toString();

        setTitle(title);
        setMessage(message);

        setTaskName(taskName);
        setTime(time);
    }

    // starting alarm
    private void startAlarm(Calendar cal) {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlertReceiver.class);
        intent.putExtra("TITLE", title);
        intent.putExtra("MESSAGE", message);
        final int id = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), id, intent, PendingIntent.FLAG_MUTABLE);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }

    //to upload task reminder
    private void taskAlarmUpload() {
        HashMap upload = new HashMap();

        upload.put("taskName", taskName);
        upload.put("time", time);
        databaseReference.child(uid).child("alarms").push().setValue(upload);
    }

    // check for null fields
    private boolean checkInputAlarmFields() {
        if (etTaskName.getText().length() == 0) {
            Toast.makeText(getActivity(), "Please Enter a Workout Task", Toast.LENGTH_SHORT).show();
            return false;
        } else if(hour == 0 && min == 0) {
            Toast.makeText(getActivity(), "Please Set an Alarm time for you Workout", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(am_pm == 0) {
            Toast.makeText(getActivity(), "Please Set the Alarm for AM or PM", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // setter and getter method for notification title and message
    public static void setTitle(String title) {
        reminderAddFragment.title = title;
    }

    public static void setMessage(String message) {
        reminderAddFragment.message = message;
    }

    public static String getTitle() {
        return title;
    }

    public static String getMessage() {
        return message;
    }

    public static String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public static String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}