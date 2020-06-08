package com.example.onlineexam;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public  class PagerAdapter extends FragmentPagerAdapter{
    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {

        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        View view=null;
        switch (position){
            case 0:
                SigninFragment signinFragment=new SigninFragment();
                return signinFragment;
            case 1:SignupFragment signupFragment=new SignupFragment();
                return signupFragment;

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
