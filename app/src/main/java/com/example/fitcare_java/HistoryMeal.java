package com.example.fitcare_java;

public class HistoryMeal {
    public String foodDate;
    public String foodNameMorning1;
    public String foodNameMorning2;
    public String foodNameMorning3;
    public String foodNameAfternoon1;
    public String foodNameAfternoon2;
    public String foodNameAfternoon3;
    public String foodNameEvening1;
    public String foodNameEvening2;
    public String foodNameEvening3;

    public HistoryMeal() {
    }

    public HistoryMeal(String foodDate, String foodNameMorning1, String foodNameMorning2, String foodNameMorning3, String foodNameAfternoon1, String foodNameAfternoon2, String foodNameAfternoon3, String foodNameEvening1, String foodNameEvening2, String foodNameEvening3) {
        this.foodDate = foodDate;
        this.foodNameMorning1 = foodNameMorning1;
        this.foodNameMorning2 = foodNameMorning2;
        this.foodNameMorning3 = foodNameMorning3;
        this.foodNameAfternoon1 = foodNameAfternoon1;
        this.foodNameAfternoon2 = foodNameAfternoon2;
        this.foodNameAfternoon3 = foodNameAfternoon3;
        this.foodNameEvening1 = foodNameEvening1;
        this.foodNameEvening2 = foodNameEvening2;
        this.foodNameEvening3 = foodNameEvening3;
    }

    public String getFoodDate() {
        return foodDate;
    }

    public void setFoodDate(String foodDate) {
        this.foodDate = foodDate;
    }

    public String getFoodNameMorning1() {
        return foodNameMorning1;
    }

    public void setFoodNameMorning1(String foodNameMorning1) {
        this.foodNameMorning1 = foodNameMorning1;
    }

    public String getFoodNameMorning2() {
        return foodNameMorning2;
    }

    public void setFoodNameMorning2(String foodNameMorning2) {
        this.foodNameMorning2 = foodNameMorning2;
    }

    public String getFoodNameMorning3() {
        return foodNameMorning3;
    }

    public void setFoodNameMorning3(String foodNameMorning3) {
        this.foodNameMorning3 = foodNameMorning3;
    }

    public String getFoodNameAfternoon1() {
        return foodNameAfternoon1;
    }

    public void setFoodNameAfternoon1(String foodNameAfternoon1) {
        this.foodNameAfternoon1 = foodNameAfternoon1;
    }

    public String getFoodNameAfternoon2() {
        return foodNameAfternoon2;
    }

    public void setFoodNameAfternoon2(String foodNameAfternoon2) {
        this.foodNameAfternoon2 = foodNameAfternoon2;
    }

    public String getFoodNameAfternoon3() {
        return foodNameAfternoon3;
    }

    public void setFoodNameAfternoon3(String foodNameAfternoon3) {
        this.foodNameAfternoon3 = foodNameAfternoon3;
    }

    public String getFoodNameEvening1() {
        return foodNameEvening1;
    }

    public void setFoodNameEvening1(String foodNameEvening1) {
        this.foodNameEvening1 = foodNameEvening1;
    }

    public String getFoodNameEvening2() {
        return foodNameEvening2;
    }

    public void setFoodNameEvening2(String foodNameEvening2) {
        this.foodNameEvening2 = foodNameEvening2;
    }

    public String getFoodNameEvening3() {
        return foodNameEvening3;
    }

    public void setFoodNameEvening3(String foodNameEvening3) {
        this.foodNameEvening3 = foodNameEvening3;
    }
}
