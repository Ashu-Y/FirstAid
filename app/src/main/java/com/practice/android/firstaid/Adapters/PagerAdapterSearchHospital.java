package com.practice.android.firstaid.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.practice.android.firstaid.Fragments.BloodBanks;
import com.practice.android.firstaid.Fragments.SearchHospital;

/**
 * Created by parven on 01-10-2017.
 */

public class PagerAdapterSearchHospital extends FragmentStatePagerAdapter{

    int mNumOfTabs;
    String lat, lng;
    int i;


    public PagerAdapterSearchHospital(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                SearchHospital searchHospital;
               searchHospital  = new SearchHospital();
                return searchHospital;

            case 1:
                BloodBanks check;
                check = new BloodBanks();
                return check;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
