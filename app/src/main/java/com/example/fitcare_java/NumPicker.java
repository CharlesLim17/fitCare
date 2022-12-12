package com.example.fitcare_java;

import java.util.ArrayList;

public class NumPicker {
    private static ArrayList<NumPicker> numPickerList;

    private int id;
    private String name;

    public NumPicker(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static void initNumPicker() {
    numPickerList = new ArrayList<>();

    NumPicker numPickerAM = new NumPicker(0,"AM");
    numPickerList.add(numPickerAM);

    NumPicker numPickerPM = new NumPicker(1,"PM");
    numPickerList.add(numPickerPM);
    }

    public static ArrayList<NumPicker> getNumPickerList() {
        return numPickerList;
    }

    public static String[] numPickerNames() {
        String[] names = new String[numPickerList.size()];
        for(int i = 0; i < numPickerList.size(); i++){
            names[i] = numPickerList.get(i).name;
        }

        return names;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
