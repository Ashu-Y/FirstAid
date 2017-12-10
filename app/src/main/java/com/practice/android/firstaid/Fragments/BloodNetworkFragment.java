package com.practice.android.firstaid.Fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.practice.android.firstaid.Adapters.PagerAdapterBloodNetwork;
import com.practice.android.firstaid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BloodNetworkFragment extends Fragment {


    public BloodNetworkFragment() {
        // Required empty public constructor
    }

    public static BloodNetworkFragment newInstance() {
        BloodNetworkFragment fragment = new BloodNetworkFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //     ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        View inflatedView = inflater.inflate(R.layout.fragment_blood_network, container, false);

        TabLayout tabLayout = inflatedView.findViewById(R.id.tab_layout_bloodNetwork);
        tabLayout.addTab(tabLayout.newTab().setText("Received requests"));
        tabLayout.addTab(tabLayout.newTab().setText("My requests"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = inflatedView.findViewById(R.id.view_pager_bloodNetwork);

        PagerAdapterBloodNetwork pagerAdapterBloodNetwork = new PagerAdapterBloodNetwork(getActivity().getSupportFragmentManager(),
                tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapterBloodNetwork);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return inflatedView;
    }

}
