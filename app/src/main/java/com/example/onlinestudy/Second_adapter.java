package com.example.onlinestudy;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class Second_adapter extends FragmentPagerAdapter {
    public Second_adapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                StudentClass student=new StudentClass();
                return student;
            case 1:
                ProfessorClass professor =new ProfessorClass();
                return professor;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
