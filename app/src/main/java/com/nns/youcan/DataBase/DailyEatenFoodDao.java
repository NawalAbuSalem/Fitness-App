package com.nns.youcan.DataBase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.nns.youcan.Models.DailyEatenFood;

import java.util.List;

@Dao
public interface DailyEatenFoodDao {

    @Query("SELECT * FROM dailyeatenfood where date=:todayDate and userID=:userId")
    LiveData<List<DailyEatenFood>> getDailyEatenFood(String todayDate, String userId);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long addNewDailyFood(DailyEatenFood dailyEatenFood);

    @Query("UPDATE dailyeatenfood SET waterDrankAmount=:waterAmount where date=:todayDate and userID=:userId")
    int updateDailyFood(double waterAmount,String todayDate, String userId );


    @Query("SELECT waterDrankAmount FROM dailyeatenfood where date=:todayDate and userID=:userId")
    LiveData<Double> getDailyWaterAmount(String todayDate, String userId);
}
