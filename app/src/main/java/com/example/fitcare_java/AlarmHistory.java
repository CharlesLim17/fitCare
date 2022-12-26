package com.example.fitcare_java;

public class AlarmHistory implements Comparable<AlarmHistory> {
    String taskName;
    String time;

    public AlarmHistory(){

    }

    public AlarmHistory(String taskName, String time) {
        this.taskName = taskName;
        this.time = time;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int compareTo(AlarmHistory alarmHistory) {
        return 0;
    }
}
