package com.nns.youcan.Workers;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.nns.youcan.R;
import com.nns.youcan.Views.WaterReminder.WaterTrackingActivity;

public class WaterAlarmWorker extends Worker {

    private static final String CHANNEL_ID ="water notification channel";
    private static final int WATER_TRACK_REQUEST_CODE =100 ;
    private Context context;
    private static final int NOTIFICATION_ID=100;

    public WaterAlarmWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context=context;
        createNotificationChannel();
    }

    @NonNull
    @Override
    public Result doWork() {
         showBasicNotification();
        return Result.success();
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Water Alarm Channel";
            String description = "This channel for water reminding";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            AudioAttributes audioAttributes=new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
           // Uri soundUri=Uri.parse("android.resource://"+context.getPackageName()+"/"+R.raw.water);
            Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name,
                    importance);
            channel.setDescription(description);
            channel.setShowBadge(false);
            channel.setVibrationPattern(new long[]{0,500,700,900});
            channel.setSound(soundUri,audioAttributes);
            channel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    public void showBasicNotification() {
       // Uri soundUri=Uri.parse("android.resource://"+context.getPackageName()+"/"+R.raw.water);
        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        TaskStackBuilder stackBuilder=TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(new Intent(context, WaterTrackingActivity.class));
        PendingIntent pendingIntent=PendingIntent.getActivities(context,WATER_TRACK_REQUEST_CODE,stackBuilder.getIntents(),0);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setSmallIcon(R.drawable.ic_water)
                        .setContentTitle("Water reminder")
                        .setContentText("Time to drink new cup of water")
                        .setVibrate(new long[]{0,500,700,900})
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setSound(soundUri)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManager =
        NotificationManagerCompat.from(context);
        Notification notification = builder.build();
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}
