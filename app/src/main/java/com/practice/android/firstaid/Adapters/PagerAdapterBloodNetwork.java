package com.practice.android.firstaid.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.practice.android.firstaid.Fragments.BloodMyRequest;
import com.practice.android.firstaid.Fragments.ReceivedBloodRequest;

/**
 * Created by parven on 24-09-2017.
 */

public class PagerAdapterBloodNetwork extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PagerAdapterBloodNetwork(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                ReceivedBloodRequest receivedBloodRequest = new ReceivedBloodRequest();
                return receivedBloodRequest;

            case 1:
                BloodMyRequest bloodMyRequest = new BloodMyRequest();
                return bloodMyRequest;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
