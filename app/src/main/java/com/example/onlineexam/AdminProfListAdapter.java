package com.example.onlineexam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AdminProfListAdapter extends ArrayAdapter<SubProf> {

    public AdminProfListAdapter(@NonNull Context context, @NonNull List<SubProf> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView= LayoutInflater.from(getContext()).inflate(R.layout.custom_admin_prof_list_row,parent,false);
        TextView name=convertView.findViewById(R.id.text_admin_prof_name);
       SubProf user=getItem(position);
        name.setText(user.getName());
        name.setTextColor(convertView.getResources().getColor(R.color.colorAccent));
        name.setTextSize(20);





        return convertView;
    }
}
