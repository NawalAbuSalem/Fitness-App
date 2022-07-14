package com.nns.youcan.Views;

public class OnBoardingPager {
   private int imageResource;
   private String title;
   private String subTitle;

    public OnBoardingPager(int imageResource, String title, String subTitle) {
        this.imageResource = imageResource;
        this.title = title;
        this.subTitle = subTitle;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
