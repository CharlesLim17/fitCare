package com.example.fitcare_java;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
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

import java.text.DateFormat;
import java.util.Calendar;


public class reminderAddFragment extends Fragment {

    //declaring variables
    private NumberPicker numPickerHour, numPickerMin, numPickerAm;
    private TextView txtDisplayTimeAdd;
    private EditText etTaskName;
    private ImageView btnBack;
    private TextView btnAdd;
    static String title, message;

    //declaring NotificationHelper class
    private NotificationHelper notificationHelper;

    //storing hour/min/am_pm values to respective variables
    final static int[] hour = new int[1];
    final static int[] min = new int[1];
    final static int[] am_pm = new int[1];

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

        //setting numpicker for hour
        numPickerHour.setMinValue(0);
        numPickerHour.setMaxValue(12);

        //setting numpicker for min
        numPickerMin.setMinValue(0);
        numPickerMin.setMaxValue(59);

        //setting am pm
        NumPicker.initNumPicker();
        numPickerAm.setMaxValue(NumPicker.getNumPickerList().size() - 1);
        numPickerAm.setMinValue(0);
        numPickerAm.setDisplayedValues(NumPicker.numPickerNames());

        numPickerHour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                txtDisplayTimeAdd.setText(String.format("Time: %s : %s %s", i1, (int) min[0], NumPicker.getNumPickerList().get(am_pm[0]).getName()));
                hour[0] = i1;
            }
        });

        numPickerMin.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                txtDisplayTimeAdd.setText(String.format("Time: %s : %s %s", (int) hour[0], i1, NumPicker.getNumPickerList().get(am_pm[0]).getName()));
                min[0] = i1;
            }
        });

        numPickerAm.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                txtDisplayTimeAdd.setText(String.format("Time: %s : %s %s", (int) hour[0], (int) min[0], NumPicker.getNumPickerList().get(i1).getName()));
                am_pm[0] = i1;
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
                Fragment reminderFrag = new reminderFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, reminderFrag).commit();
                setAlarmTime(hour[0], min[0]);
            }
        });
        return view;
    }

    // method for NotificationHelper
    public void sendOnChannel1(String title, String message) {
        NotificationCompat.Builder notificationCompatBuilder = notificationHelper.getChannel1Notification(title,message);
        notificationHelper.getManager().notify(1, notificationCompatBuilder.build());

    }

    // setting alarm time
    private void setAlarmTime(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM, NumPicker.getNumPickerList().get(am_pm[0]).getId());

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

    // alarm Notification title and Message
    private void setAlarmTitleMessage(Calendar cal) {
        String title = "Workout Reminder";
        String taskName = etTaskName.getText().toString();
        String time = DateFormat.getTimeInstance(DateFormat.SHORT).format(cal.getTime());
        String message = "Task: " + taskName + " at " + time;

        setTitle(title);
        setMessage(message);
    }

    // starting alarm
    private void startAlarm(Calendar cal) {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlertReceiver.class);
        final int id = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), id, intent, PendingIntent.FLAG_MUTABLE);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
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
}