package com.example.onlinestudy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class StyleDepartmentLevel {
    String department;
    StyleDepartmentLevel (String department){
        this.department=department;
    }
    public String getDepartment(){
        return department;
    }

    public void setDepartment(java.lang.String department) {
        this.department = department;
    }
}

