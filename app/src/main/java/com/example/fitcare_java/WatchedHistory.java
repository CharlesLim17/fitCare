package com.example.fitcare_java;

public class WatchedHistory {
    public String title;
    public String date;
    public String time;


    public WatchedHistory() {
    }

    public WatchedHistory(String title, String date, String time) {
        this.title = title;
        this.date = date;
        this.time = time;
    }

    public WatchedHistory(String title, String date) {
        this.title = title;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
