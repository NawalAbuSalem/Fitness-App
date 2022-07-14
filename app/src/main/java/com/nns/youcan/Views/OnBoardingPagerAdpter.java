package com.nns.youcan.Views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.viewpager.widget.PagerAdapter;


import com.nns.youcan.R;

import java.util.List;

public class OnBoardingPagerAdpter extends PagerAdapter {
    List<OnBoardingPager>pagers;
    public OnBoardingPagerAdpter(List<OnBoardingPager>pagers) {
        this.pagers=pagers;
    }

    @Override
    public int getCount() {
        return pagers.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view= LayoutInflater.from(container.getContext()).inflate(R.layout.onboarding_pager,container,false);
        ImageView pagerImage=view.findViewById(R.id.imageView_pager);
        TextView pagerTitle=view.findViewById(R.id.title_textView);
        TextView pagerSubTitle=view.findViewById(R.id.subtitle_textView);
        pagerImage.setImageResource(pagers.get(position).getImageResource());
        pagerTitle.setText(pagers.get(position).getTitle());
        pagerSubTitle.setText(pagers.get(position).getSubTitle());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((View) object);
    }

}
