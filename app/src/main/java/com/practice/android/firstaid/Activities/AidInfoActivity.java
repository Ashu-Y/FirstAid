package com.practice.android.firstaid.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.practice.android.firstaid.Fragments.AnimalBiteFragment;
import com.practice.android.firstaid.R;

public class AidInfoActivity extends AppCompatActivity {

    private static final int NUM_PAGES = 3;
    ProgressBar progressBar;
    TextView progressText, backTv, nextTv;
    Toolbar toolbar;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aid_info);

        toolbar = (Toolbar) findViewById(R.id.toolbarAidInfo);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        progressBar = (ProgressBar) findViewById(R.id.progressAnimalBite);
        progressBar.setMax(NUM_PAGES);
        progressText = (TextView) findViewById(R.id.progressText);
        progressText.setText("Page 1" + "of " + NUM_PAGES);
        backTv = (TextView) findViewById(R.id.backTv);
        backTv.setVisibility(View.INVISIBLE);
        nextTv = (TextView) findViewById(R.id.nextTv);


        mPager = (ViewPager) findViewById(R.id.viewPager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
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

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.activity_screen_slide, menu);
//
//        menu.findItem(R.id.action_previous).setEnabled(mPager.getCurrentItem() > 0);
//
//        // Add either a "next" or "finish" button to the action bar, depending on which page
//        // is currently selected.
//        MenuItem item = menu.add(Menu.NONE, R.id.action_next, Menu.NONE,
//                (mPager.getCurrentItem() == mPagerAdapter.getCount() - 1)
//                        ? R.string.action_finish
//                        : R.string.action_next);
//        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                // Navigate "up" the demo structure to the launchpad activity.
//                // See http://developer.android.com/design/patterns/navigation.html for more.
//                NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
//                return true;
//
//            case R.id.action_previous:
//                // Go to the previous step in the wizard. If there is no previous step,
//                // setCurrentItem will do nothing.
//                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
//                return true;
//
//            case R.id.action_next:
//                // Advance to the next step in the wizard. If there is no next step, setCurrentItem
//                // will do nothing.
//                mPager.setCurrentItem(mPager.getCurrentItem() + 1);
//                return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

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
