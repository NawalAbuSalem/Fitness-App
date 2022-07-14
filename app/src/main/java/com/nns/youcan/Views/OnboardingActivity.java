package com.nns.youcan.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


import com.nns.youcan.R;
import com.nns.youcan.Views.home.HomeActivity;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class OnboardingActivity extends AppCompatActivity {
    int currentPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        currentPage=0;
        final ViewPager viewPager=findViewById(R.id.viewpager);
        DotsIndicator dotsIndicator =  findViewById(R.id.dots_indicator);
        List<OnBoardingPager> pagers=new ArrayList();
        pagers.add(new OnBoardingPager(R.drawable.track1,getResources().getString(R.string.track_activity),getResources().getString(R.string.track_activity_content)));
        pagers.add(new OnBoardingPager(R.drawable.track2,getResources().getString(R.string.invite_your_friend),getResources().getString(R.string.invite_your_friend_content)));
        pagers.add(new OnBoardingPager(R.drawable.track3,getResources().getString(R.string.consult_doctor),getResources().getString(R.string.consult_doctor_content)));
        OnBoardingPagerAdpter onBoardingPagerAdpter=new OnBoardingPagerAdpter(pagers);
        viewPager.setAdapter(onBoardingPagerAdpter);
        dotsIndicator.setViewPager(viewPager);
         Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (currentPage == 3) {
                            startActivity(new Intent(OnboardingActivity.this, HomeActivity.class));
                            finish();
                        }
                        viewPager.setCurrentItem(currentPage++, true);
                    }
                });
            }
        }, 200, 2000);
        TextView skip=findViewById(R.id.btn_skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OnboardingActivity.this,HomeActivity.class));
                finish();
            }
        });

    }
}
