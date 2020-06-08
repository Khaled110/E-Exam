package com.example.onlinestudy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;


public class Signup_class extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            final View view=inflater.inflate(R.layout.signup,container,false);
            tabLayout=view.findViewById(R.id.second_tab);
            viewPager=view.findViewById(R.id.second_pager);
            Second_adapter adapter=new Second_adapter(getChildFragmentManager(),2);
            viewPager.setAdapter(adapter);
            tabLayout.addTab(tabLayout.newTab().setText("Student"));
            tabLayout.addTab(tabLayout.newTab().setText("Professor"));
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
            return view;

        }



}


