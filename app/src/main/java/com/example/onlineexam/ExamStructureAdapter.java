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

public class ExamStructureAdapter extends ArrayAdapter<ExamStructure> {
    public ExamStructureAdapter(@NonNull Context context, @NonNull List<ExamStructure> objects) {
        super(context, 0, objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1,parent,false);

        TextView textView=convertView.findViewById(android.R.id.text1);
        ExamStructure examStructure=getItem(position);
        textView.setText("* "+examStructure.getNumber()+" "+examStructure.getType()+" Qestions "+"\n of Categoty "+examStructure.getCategory()
                +" from chapter \n"+"' " +examStructure.getChapter()+" '");
        textView.setTextColor(convertView.getResources().getColor(R.color.colorAccent));
        textView.setTextSize(20);
        return convertView;
    }
}
