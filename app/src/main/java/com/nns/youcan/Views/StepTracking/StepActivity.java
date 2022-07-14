package com.nns.youcan.Views.StepTracking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignIn;

import com.nns.youcan.Models.MyGoal;
import com.nns.youcan.R;
import com.nns.youcan.ViewModel.GoogleFitAPIViewModel;
import com.nns.youcan.ViewModel.UsersFireStoreViewModel;

public class StepActivity extends AppCompatActivity {
    private GoogleFitAPIViewModel fitAPIViewModel;
    private TextView stepCounterTextView;
    private TextView movementDurationTextView;
    private TextView caloriesExpandedTextView;
    private TextView distanceTextView;
    private View[]runProgress;
    private UsersFireStoreViewModel fireStoreViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        stepCounterTextView=findViewById(R.id.step_tracking_txt);
        movementDurationTextView=findViewById(R.id.step_tracking_duration);
        caloriesExpandedTextView=findViewById(R.id.step_tracking_calories);
        distanceTextView=findViewById(R.id.step_tracking__distance);
        fireStoreViewModel=new UsersFireStoreViewModel();
        runProgress= new View[]{findViewById(R.id.vt1),
               findViewById(R.id.vt2),
               findViewById(R.id.vt3),
               findViewById(R.id.vt4),
               findViewById(R.id.vt5),
               findViewById(R.id.vt6),
               findViewById(R.id.vt7),
               findViewById(R.id.vt8),
               findViewById(R.id.vt9),
               findViewById(R.id.vt10),
               findViewById(R.id.vt11),
               findViewById(R.id.vt12),
               findViewById(R.id.vt13),
               findViewById(R.id.vt14),
               findViewById(R.id.vt15),
               findViewById(R.id.vt16),
               findViewById(R.id.vt17),
               findViewById(R.id.vt18),
               findViewById(R.id.vt19),
               findViewById(R.id.vt20),
               findViewById(R.id.vt21),
               findViewById(R.id.vt22),
               findViewById(R.id.vt23),
               findViewById(R.id.vt24)};

        fitAPIViewModel=new GoogleFitAPIViewModel(this);
        fitAPIViewModel.connectToGoogleFitApi();
        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this))) {
            GoogleSignIn.requestPermissions(
                    this, // your activity
                    GoogleFitAPIViewModel.GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                    GoogleSignIn.getLastSignedInAccount(this));
        }else {
            retrieveGoogleFitData();
        }

    }

    private void retrieveGoogleFitData () {
        MutableLiveData<String >stepCountLiveData=fitAPIViewModel.getStepCount();
        if (stepCountLiveData!=null){
            stepCountLiveData.observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    stepCounterTextView.setText(s);
                }
            });
        }
        MutableLiveData<String >movementDurationLiveData=fitAPIViewModel.getMovementDuration();
        if (movementDurationLiveData!=null){
            movementDurationLiveData.observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    movementDurationTextView.setText(s);
                }
            });
        }
        MutableLiveData<String >caloriesExpandedLiveData=fitAPIViewModel.getCaloriesExpanded();
        if (caloriesExpandedLiveData!=null){
            caloriesExpandedLiveData.observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    caloriesExpandedTextView.setText(String.format("%.0f",Double.parseDouble(s)));
                }
            });
        }
        MutableLiveData<String >distanceLiveData=fitAPIViewModel.getDistance();
        if (distanceLiveData!=null){
            distanceLiveData.observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    double result=Math.ceil(Double.parseDouble(s))/1000;
                    distanceTextView.setText(String.format("%.2f", result));
                    MutableLiveData<MyGoal>myGoalLiveData=fireStoreViewModel.getMyGoal();
                    myGoalLiveData.observe(StepActivity.this, new Observer<MyGoal>() {
                        @Override
                        public void onChanged(MyGoal myGoal) {
                            double goalDistance=Double.parseDouble(myGoal.getGoalDistance());
                                setRunProgressAnimation(goalDistance,result);

                        }
                    });

                }
            });
        }



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GoogleFitAPIViewModel.GOOGLE_FIT_PERMISSIONS_REQUEST_CODE) {
            fitAPIViewModel.accessGoogleFit();
            }
        }
    }
    public void goTobackActivity(View view) {
        finish();
    }
    private void setRunProgressAnimation(final double goalDistance, final double walkedDistance) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                int fillRatio=0;
               if (goalDistance!=0){
                   if (walkedDistance>=goalDistance){
                       fillRatio=24;
                   }else{
                       fillRatio= (int) Math.ceil(((walkedDistance/goalDistance)*24));
                   }
               }
                for (int i=0;i<fillRatio;i++){
                    try {
                        Thread.sleep(50);
                        final int finalI = i;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (runProgress[finalI]!=null){
                                    runProgress[finalI].setBackground(getResources().getDrawable(R.drawable.active_dot_gold));
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
