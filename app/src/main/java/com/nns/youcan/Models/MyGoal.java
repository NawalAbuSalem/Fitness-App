package com.nns.youcan.Models;

public class MyGoal {
    private String userId;
    private String calories;
    private String carbs;
    private String fat;
    private String protein;
    private String currentWeight;
    private String goalWeight;
    private String height;
    private String goalDistance;
    private String goalWaterAmount;
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }



    public MyGoal(String userId, String calories, String carbs, String fat, String protein, String goalWeight, String goalDistance, String goalWaterAmount) {
        this.calories = calories;
        this.carbs = carbs;
        this.fat = fat;
        this.protein = protein;
        this.goalWeight = goalWeight;
        this.goalDistance = goalDistance;
        this.goalWaterAmount = goalWaterAmount;
        this.userId=userId;
    }


    public MyGoal() {
    }



    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(String currentWeight) {
        this.currentWeight = currentWeight;
    }

    public String getGoalWeight() {
        return goalWeight;
    }

    public void setGoalWeight(String goalWeight) {
        this.goalWeight = goalWeight;
    }

    public String getGoalDistance() {
        return goalDistance;
    }

    public void setGoalDistance(String goalDistance) {
        this.goalDistance = goalDistance;
    }

    public String getGoalWaterAmount() {
        return goalWaterAmount;
    }

    public void setGoalWaterAmount(String goalWaterAmount) {
        this.goalWaterAmount = goalWaterAmount;
    }
}
