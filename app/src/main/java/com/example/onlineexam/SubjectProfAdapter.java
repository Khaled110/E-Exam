package com.example.onlineexam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class SubjectProfAdapter extends ArrayAdapter<SubProf> {
    public SubjectProfAdapter(@NonNull Context context, @NonNull List<SubProf> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView= LayoutInflater.from(getContext()).inflate(R.layout.custom_row,parent,false);
            TextView textView=convertView.findViewById(R.id.txtview1);
        SubProf subProf=getItem(position);

        textView.setText(subProf.getSubName());
        return convertView;
    }
}
