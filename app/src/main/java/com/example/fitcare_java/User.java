package com.example.fitcare_java;

public class User {

    // declaration of variables for database
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private float curWeight;
    private float prevWeight;
    private int height;
    private float goal;

    public User() {

    }

    // constructor / passing the input values to the declared variables
    public User(String email, String password, String firstName, String lastName, int age, String gender, float curWeight, float prevWeight, int height, float goal) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.curWeight = curWeight;
        this.prevWeight = prevWeight;
        this.height = height;
        this.goal = goal;
    }

    //accessors and mutators
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}

