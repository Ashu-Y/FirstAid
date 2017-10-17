package com.practice.android.firstaid.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.practice.android.firstaid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpiderContentFragment extends Fragment {

    private static final int NUM_PAGES = 3;
    ProgressBar progressBar;
    TextView progressText, backTv, nextTv;
    Toolbar toolbar;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;


    public SpiderContentFragment() {
        // Required empty public constructor
    }

    public static SpiderContentFragment newInstance() {
        SpiderContentFragment fragment = new SpiderContentFragment();
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_aid_info, container, false);

        toolbar = view.findViewById(R.id.toolbarAidInfo);
        toolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        progressBar = view.findViewById(R.id.progressAnimalBite);
        progressBar.setMax(NUM_PAGES);
        progressText = view.findViewById(R.id.progressText);
        progressText.setText("Page 1" + "of " + NUM_PAGES);
        backTv = view.findViewById(R.id.backTv);
        backTv.setVisibility(View.INVISIBLE);
        nextTv = view.findViewById(R.id.nextTv);


        mPager = view.findViewById(R.id.viewPager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When changing pages, reset the action bar actions since they are dependent
                // on which page is currently active. An alternative approach is to have each
                // fragment expose actions itself (rather than the activity exposing actions),
                // but for simplicity, the activity provides the actions in this sample.

                progressBar.setProgress(position + 1);

                progressText.setText("Page " + (position + 1) + "of " + NUM_PAGES);

                if (mPager.getCurrentItem() > 0) {
                    backTv.setVisibility(View.VISIBLE);
                } else {
                    backTv.setVisibility(View.INVISIBLE);
                }

                if (mPager.getCurrentItem() == mPagerAdapter.getCount() - 1) {
                    nextTv.setVisibility(View.INVISIBLE);
                } else {
                    nextTv.setVisibility(View.VISIBLE);
                }

//                invalidateOptionsMenu();
            }
        });


        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
            }
        });

        nextTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (nextTv.getText().equals("Next")) {
                    mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                } else {

                }
            }
        });

        return view;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return AnimalBiteFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}
