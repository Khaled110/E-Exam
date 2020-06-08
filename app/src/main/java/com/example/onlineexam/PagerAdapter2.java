package com.example.onlineexam;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter2 extends FragmentPagerAdapter {
    public PagerAdapter2(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                SignupStuFragment stuFragment=new SignupStuFragment();
                return stuFragment;
            case 1:SignupProfFragment profFragment=new SignupProfFragment();
                return profFragment;

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
