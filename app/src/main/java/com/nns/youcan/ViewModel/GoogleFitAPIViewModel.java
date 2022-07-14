package com.nns.youcan.ViewModel;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResponse;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.firebase.ui.auth.ui.phone.SubmitConfirmationCodeFragment.TAG;
import static com.github.mikephil.charting.charts.Chart.LOG_TAG;
import static java.text.DateFormat.getDateInstance;
import static java.text.DateFormat.getTimeInstance;

public class GoogleFitAPIViewModel {
    public static final int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 100;
    private Context context;
    int stepsCount;



    public GoogleFitAPIViewModel(Context context) {

        this.context = context;

    }

    public void connectToGoogleFitApi() {
        FitnessOptions fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_CALORIES_EXPENDED, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.AGGREGATE_CALORIES_EXPENDED, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_DISTANCE_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.AGGREGATE_DISTANCE_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_MOVE_MINUTES, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.AGGREGATE_MOVE_MINUTES, FitnessOptions.ACCESS_READ)
                .build();
        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(context), fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    (Activity) context, // your activity
                    GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                    GoogleSignIn.getLastSignedInAccount(context),
                    fitnessOptions);
        } else {
            accessGoogleFit();
        }
    }

    public void accessGoogleFit() {
        // Subscribe to fit recorders
        Fitness.getRecordingClient(context, GoogleSignIn.getLastSignedInAccount(context))
                .subscribe(DataType.TYPE_STEP_COUNT_DELTA);
        Fitness.getRecordingClient(context, GoogleSignIn.getLastSignedInAccount(context))
                .subscribe(DataType.TYPE_MOVE_MINUTES);
        Fitness.getRecordingClient(context, GoogleSignIn.getLastSignedInAccount(context))
                .subscribe(DataType.TYPE_CALORIES_EXPENDED);
        Fitness.getRecordingClient(context, GoogleSignIn.getLastSignedInAccount(context))
                .subscribe(DataType.TYPE_DISTANCE_DELTA);
    }

    public MutableLiveData<String> getStepCount(){
        MutableLiveData<String>stepCountLiveData=new MutableLiveData<>();
        Fitness.getHistoryClient(context, GoogleSignIn.getLastSignedInAccount(context))
                .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
        .addOnSuccessListener((Activity) context, new OnSuccessListener<DataSet>() {
            @Override
            public void onSuccess(DataSet dataSet) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    for (DataPoint dp : dataSet.getDataPoints()) {
                        Log.i(LOG_TAG, "Data point:");
                        Log.i(LOG_TAG, "\tType: " + dp.getDataType().getName());
                        Log.i(LOG_TAG, "\tStart: " + dateFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)));
                        Log.i(LOG_TAG, "\tEnd: " + dateFormat.format(dp.getEndTime(TimeUnit.MILLISECONDS)));
                        for (Field field : dp.getDataType().getFields()) {
                            Log.i(LOG_TAG, "\tField: " + field.getName() + " Value: " + dp.getValue(field));
                            stepCountLiveData.setValue(String.valueOf(dp.getValue(field).asInt()));

                        }

                    }
            }


        });
         return stepCountLiveData;
    }

    public MutableLiveData<String> getCaloriesExpanded(){

        MutableLiveData<String>caloriesExpandedLiveData=new MutableLiveData<>();
        Fitness.getHistoryClient(context, GoogleSignIn.getLastSignedInAccount(context))
                .readDailyTotal(DataType.TYPE_CALORIES_EXPENDED)
                .addOnSuccessListener((Activity) context, new OnSuccessListener<DataSet>() {
                    @Override
                    public void onSuccess(DataSet dataSet) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        for (DataPoint dp : dataSet.getDataPoints()) {
                            Log.i(LOG_TAG, "Data point:");
                            Log.i(LOG_TAG, "\tType: " + dp.getDataType().getName());
                            Log.i(LOG_TAG, "\tStart: " + dateFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)));
                            Log.i(LOG_TAG, "\tEnd: " + dateFormat.format(dp.getEndTime(TimeUnit.MILLISECONDS)));
                            for (Field field : dp.getDataType().getFields()) {
                                Log.i(LOG_TAG, "\tField: " + field.getName() + " Value: " + dp.getValue(field));
                                caloriesExpandedLiveData.setValue(String.valueOf(dp.getValue(field).asFloat()));

                            }

                        }
                    }


                });
        return caloriesExpandedLiveData;
    }
    public MutableLiveData<String> getDistance(){

        MutableLiveData<String>distanceLiveData=new MutableLiveData<>();
        Fitness.getHistoryClient(context, GoogleSignIn.getLastSignedInAccount(context))
                .readDailyTotal(DataType.TYPE_DISTANCE_DELTA)
                .addOnSuccessListener((Activity) context, new OnSuccessListener<DataSet>() {
                    @Override
                    public void onSuccess(DataSet dataSet) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        for (DataPoint dp : dataSet.getDataPoints()) {
                            Log.i(LOG_TAG, "Data point:");
                            Log.i(LOG_TAG, "\tType: " + dp.getDataType().getName());
                            Log.i(LOG_TAG, "\tStart: " + dateFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)));
                            Log.i(LOG_TAG, "\tEnd: " + dateFormat.format(dp.getEndTime(TimeUnit.MILLISECONDS)));
                            for (Field field : dp.getDataType().getFields()) {
                                Log.i(LOG_TAG, "\tField: " + field.getName() + " Value: " + dp.getValue(field));
                                distanceLiveData.setValue(String.valueOf(dp.getValue(field).asFloat()));

                            }

                        }
                    }


                });
        return distanceLiveData;
    }
    public MutableLiveData<String> getMovementDuration(){

        MutableLiveData<String>movementDurationLiveData=new MutableLiveData<>();
        Fitness.getHistoryClient(context, GoogleSignIn.getLastSignedInAccount(context))
                .readDailyTotal(DataType.TYPE_MOVE_MINUTES)
                .addOnSuccessListener((Activity) context, new OnSuccessListener<DataSet>() {
                    @Override
                    public void onSuccess(DataSet dataSet) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        for (DataPoint dp : dataSet.getDataPoints()) {
                            Log.i(LOG_TAG, "Data point:");
                            Log.i(LOG_TAG, "\tType: " + dp.getDataType().getName());
                            Log.i(LOG_TAG, "\tStart: " + dateFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)));
                            Log.i(LOG_TAG, "\tEnd: " + dateFormat.format(dp.getEndTime(TimeUnit.MILLISECONDS)));
                            for (Field field : dp.getDataType().getFields()) {
                                Log.i(LOG_TAG, "\tField: " + field.getName() + " Value: " + dp.getValue(field));
                                movementDurationLiveData.setValue(String.valueOf(dp.getValue(field).asInt()));

                            }

                        }
                    }


                });
        return movementDurationLiveData;
    }
    public boolean isRegistered(){
        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(context))) {
           return false;
        } else {
            return true;
        }
    }



}
