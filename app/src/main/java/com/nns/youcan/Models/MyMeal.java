package com.nns.youcan.Models;

import androidx.room.Ignore;

import java.io.Serializable;

public class MyMeal  implements Serializable {
    private String name;
    private double calories;
    private double fat;
    private double protien  ;
    private double carbs;
    private double serving;
    private String mealImage;
    private int type; // 0 breakfast,1 lunch,2 snacks,3 dinner
    private String ingredientLines ;// recipe content
    public MyMeal(String name, double calories, double fat, double protien, double carbs, double serving, String mealImage, String ingredientLines) {
        this.name = name;
        this.calories = calories;
        this.fat = fat;
        this.protien = protien;
        this.carbs = carbs;
        this.serving = serving;
        this.mealImage = mealImage;
        this.ingredientLines = ingredientLines;
    }


    public MyMeal(String name, double calories, double fat, double protien, double carbs, double serving, String mealImage, int type, String ingredientLines) {
        this.name = name;
        this.calories = calories;
        this.fat = fat;
        this.protien = protien;
        this.carbs = carbs;
        this.serving = serving;
        this.mealImage = mealImage;
        this.type = type;
        this.ingredientLines = ingredientLines;
    }
    public MyMeal() {

    }

    public String getIngredientLines() {
        return ingredientLines;
    }

    public void setIngredientLines(String ingredientLines) {
        this.ingredientLines = ingredientLines;
    }

    public String getMealImage() {
        return mealImage;
    }

    public void setMealImage(String mealImage) {
        this.mealImage = mealImage;
    }

    public MyMeal(String name, double calories, double fat, double protien, double serving, double carbs, int type) {
        this.name = name;
        this.calories = calories;
        this.fat = fat;
        this.protien = protien;
        this.serving = serving;
        this.carbs = carbs;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getProtien() {
        return protien;
    }

    public void setProtien(double protien) {
        this.protien = protien;
    }

    public double getServing() {
        return serving;
    }

    public void setServing(double serving) {
        this.serving = serving;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }
}
