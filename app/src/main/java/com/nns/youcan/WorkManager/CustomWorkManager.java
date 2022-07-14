package com.nns.youcan.WorkManager;

import android.content.Context;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.nns.youcan.Workers.WaterAlarmWorker;
import java.util.concurrent.TimeUnit;
public class CustomWorkManager {
    private Context context;

    public CustomWorkManager(Context context){
       this.context=context;
    }

    public void setWaterAlarm(){
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(WaterAlarmWorker.class, 2,
                TimeUnit.HOURS).setInitialDelay(1,TimeUnit.HOURS).build();
        WorkManager workManager = WorkManager.getInstance(context);
        workManager.enqueueUniquePeriodicWork(
                "WaterAlarmWorker",
                ExistingPeriodicWorkPolicy.KEEP,
                periodicWorkRequest);
    }

}
