package com.nns.youcan.Models;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;


@Entity (tableName = "DailyEatenFood")
public class DailyEatenFood {
    public int get_ID() {
        return _ID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    @PrimaryKey(autoGenerate = true)
    private int _ID;
    private String date;
    private String userID;
    private double waterDrankAmount;
    @Embedded
    private MyMeal myMeal;
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getWaterDrankAmount() {
        return waterDrankAmount;
    }

    public void setWaterDrankAmount(double waterDrankAmount) {
        this.waterDrankAmount = waterDrankAmount;
    }

    public MyMeal getMyMeal() {
        return myMeal;
    }

    public void setMyMeal(MyMeal myMeal) {
        this.myMeal = myMeal;
    }


}
