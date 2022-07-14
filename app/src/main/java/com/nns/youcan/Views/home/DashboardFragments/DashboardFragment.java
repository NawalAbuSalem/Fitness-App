package com.nns.youcan.Views.home.DashboardFragments;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nns.youcan.R;

import java.util.Date;

public class DashboardFragment extends Fragment  {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.dashboard_fragment,container,false);
        ViewPager pager=view.findViewById(R.id.dashboard_viewPager);
        TextView currentDate=view.findViewById(R.id.current_date_txt);
        TabLayout tabLayout=view.findViewById(R.id.tabs);
        CharSequence stringDate  = DateFormat.format("MMMM d, yyyy ", new Date().getTime());
        currentDate.setText(stringDate);
        DashboardPagerAdapter dashboardPagerAdapter=new DashboardPagerAdapter(getChildFragmentManager(),getContext());
        pager.setAdapter(dashboardPagerAdapter);

        tabLayout.setupWithViewPager(pager);

        return view;
    }

}
