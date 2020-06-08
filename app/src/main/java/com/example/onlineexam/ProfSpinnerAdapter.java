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

public class ProfSpinnerAdapter extends ArrayAdapter<ProfUser> {
    public ProfSpinnerAdapter(@NonNull Context context, @NonNull List<ProfUser> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner_prof,parent,false);

        TextView textView=convertView.findViewById(R.id.custom_spinner_profitem);

        ProfUser user=getItem(position);

        textView.setText(user.getType());
        return super.getView(position, convertView, parent);
    }
}
