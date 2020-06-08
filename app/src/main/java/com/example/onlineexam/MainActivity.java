package com.example.onlineexam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Spinner level,depart;
    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    TextView textView ,textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        level=findViewById(R.id.spin_level);
        Log.d("level","done");
        depart=findViewById(R.id.spin_depart);
        tabLayout=findViewById(R.id.tab);
        viewPager=findViewById(R.id.pag);
        textView=findViewById(R.id.txt_depart_signup);
        textView1=findViewById(R.id.txt_level_signup);

        pagerAdapter=new PagerAdapter(getSupportFragmentManager(),2);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.addTab(tabLayout.newTab().setText("Signin"));
        tabLayout.addTab(tabLayout.newTab().setText("Signup"));
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

      /*  ArrayList<Level> levels=new ArrayList<>();
        levels.add(new Level("First") );
        levels.add(new Level("second") );
        levels.add(new Level("third") );
        levels.add(new Level("fourth"));
        Log.d("level","ok");
        LevelAdapter levelAdapter=new LevelAdapter(MainActivity.this,levels);
        level.setAdapter(levelAdapter);
        Log.d("level","tmm");

        ArrayList<Department> departments=new ArrayList<>();
        departments.add(new Department("SE"));
        departments.add(new Department("IT"));
        departments.add(new Department("CS"));
        departments.add(new Department("IS"));
        DepartAdapter departAdapter=new DepartAdapter(MainActivity.this,departments);
       //depart.setAdapter(departAdapter);*/



    }

}
