package com.wallpaper71.fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    FragmentManager fm;
    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);

        this.mNumOfTabs = NumOfTabs;
        this.fm = fm;


    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FragmentView fragmentView=new FragmentView();
                return fragmentView;
            case 1:
                FragmentCollection fragmentCollection=new FragmentCollection();
                return fragmentCollection;
            case 2:
                FragmentFavorite fragmentView3=new FragmentFavorite();
                return fragmentView3;

            default:
                return null;
        }
    }
   /* @Override
    public Fragment getItem(int position) {

        FragmentView fragmentView=new FragmentView();
        return fragmentView;

    }*/


    @Override
    public int getCount() {
        return mNumOfTabs;
    }


}