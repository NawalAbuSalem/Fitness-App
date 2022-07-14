package com.nns.youcan.Views.home.DashboardFragments;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.nns.youcan.R;


public class DashboardPagerAdapter extends FragmentPagerAdapter {
    private final int NUMBER_OF_PAGES=3;
   private Context context;
    public DashboardPagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context=context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new DailyViewFragment();
            case 1:
                return new WeekViewFragment();
            case 2:
                return new MonthViewFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUMBER_OF_PAGES;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return context.getString(R.string.dailyview);
            case 1:
                return context.getString(R.string.weekview);
            case 2:
                return context.getString(R.string.monthview);
        }
        return null;
    }


}
