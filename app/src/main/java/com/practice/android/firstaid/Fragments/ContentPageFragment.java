package com.practice.android.firstaid.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.practice.android.firstaid.R;

import static com.practice.android.firstaid.Activities.MainActivity.FTAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentPageFragment extends Fragment {

    private static int NUM_PAGES;
    ProgressBar progressBar;
    TextView progressText, backTv, nextTv;
    Toolbar toolbar;
    String mTitle;
    //public static String FTAG;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private DatabaseReference mDatabase;

    public ContentPageFragment() {
        // Required empty public constructor
    }

    public static ContentPageFragment newInstance(String title, int y) {
        ContentPageFragment fragment = new ContentPageFragment();
        Bundle args = new Bundle();
        args.putString("Title", title);
        args.putInt("NUM", y);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTitle = getArguments().getString("Title");

        NUM_PAGES = getArguments().getInt("NUM");

        mDatabase = FirebaseDatabase.getInstance().getReference("FirstAidContent");


//        mDatabase.child("Animal Bite").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                int x = 0;
//                for (DataSnapshot page: dataSnapshot.getChildren()){
//
//                    if(page.getKey().equals("NUM_PAGES")){
//                        x = (int) page.getValue(Integer.class);
//                    }
//
//                }
//
//
//                        Toast.makeText(getActivity(), "loop : " + x, Toast.LENGTH_SHORT).show();
//                NUM_PAGES = x;
//
//
//                }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contentpage, container, false);

        toolbar = view.findViewById(R.id.toolbarAidInfo);
        toolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        getNum();

        progressBar = view.findViewById(R.id.progressAnimalBite);
        progressBar.setMax(NUM_PAGES);
        progressText = view.findViewById(R.id.progressText);
        progressText.setText("Page 1" + "of " + NUM_PAGES);
        backTv = view.findViewById(R.id.backTv);
        backTv.setVisibility(View.INVISIBLE);
        nextTv = view.findViewById(R.id.nextTv);

        FTAG = "FirstAidFragment";


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

                if (mPager.getChildCount() == 1) {
                    nextTv.setVisibility(View.INVISIBLE);
                }

//                invalidateOptionsMenu();
            }
        });


        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                mPagerAdapter.notifyDataSetChanged();
            }
        });

        nextTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (nextTv.getText().equals("Next")) {
                    mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                    mPagerAdapter.notifyDataSetChanged();
                } else {

                }
            }
        });

        mPagerAdapter.notifyDataSetChanged();
        return view;
    }


    public void getNum() {
//        mDatabase.child("Animal Bite").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                NUM_PAGES = (int) dataSnapshot.getChildrenCount();
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
//            return AnimalBiteFragment.newInstance(position);
            return ContentFragment.newInstance(position, mTitle);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }


}
