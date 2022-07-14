package com.nns.youcan.DataBase;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.nns.youcan.Models.DailyEatenFood;

@Database(entities = {DailyEatenFood.class},version = 1,exportSchema = false)
public abstract class DailyEatenFoodDataBase extends RoomDatabase {
    public static final String DATABASE_NAME="dailyeatenfood_db";
    private static  DailyEatenFoodDataBase instance;
    public static DailyEatenFoodDataBase getInstance(Context context){
       if (instance==null){
           instance= Room.databaseBuilder(context.getApplicationContext(),
                   DailyEatenFoodDataBase.class,
                   DATABASE_NAME)
                   .allowMainThreadQueries().build();
       }
     return instance;
    }
    public abstract DailyEatenFoodDao getEatenFoodDao();
}
