package com.example.fitcare_java;

public class User {
    private String firstName;
    private String lastName;
    private int age;
    private float curWeight;
    private float prevWeight;
    private int height;
    private float goal;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getCurWeight() {
        return curWeight;
    }

    public void setCurWeight(float curWeight) {
        this.curWeight = curWeight;
    }

    public float getPrevWeight() {
        return prevWeight;
    }

    public void setPrevWeight(float prevWeight) {
        this.prevWeight = prevWeight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getGoal() {
        return goal;
    }

    public void setGoal(float goal) {
        this.goal = goal;
    }

    public User(String firstName, String lastName, int age, float curWeight, float prevWeight, int height, float goal) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.curWeight = curWeight;
        this.prevWeight = prevWeight;
        this.height = height;
        this.goal = goal;
    }
}

