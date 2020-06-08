package com.example.onlinestudy;

import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FirstAdapter extends FragmentPagerAdapter {
    public FirstAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Signin_class signin =new Signin_class();
                return signin;
            case 1:
                Signup_class signup =new Signup_class();
                return signup;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
