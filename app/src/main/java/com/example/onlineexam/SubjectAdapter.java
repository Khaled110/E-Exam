package com.example.onlineexam;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SubjectAdapter extends ArrayAdapter<Subject> {
    AlertDialog dialog;
    ArrayList<Subject> strings;
    public SubjectAdapter(@NonNull Context context, @NonNull ArrayList<Subject> strings) {
        super(context, 0, strings);
        this.strings = strings;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(getContext()).inflate(R.layout.custom_row_subject,parent,false);

        TextView textView=convertView.findViewById(R.id.txt_subject);
        ImageView remove=convertView.findViewById(R.id.img_remove_subject_i);
        ImageView edit=convertView.findViewById(R.id.img_edit_subject_i);
        Subject string =getItem(position);
        textView.setText(string.getSubjectName());


        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("Warning!")
                        .setMessage("Are you sure you want to delete this Subject ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                strings.remove(position);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialog.dismiss();
                            }
                        });
                dialog=builder.create();
                dialog.show();

            }

        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdata3(strings.get(position),position);                }
        });

        return convertView;
    }
    public void showUpdata3(Subject oldItem, final int index){
        final Dialog dialog=new Dialog(getContext());
        dialog.setTitle("Update Subject");
        dialog.setContentView(R.layout.activity_updata_level);
        final EditText editText=dialog.findViewById(R.id.edit_level_update);
        java.lang.String name=oldItem.getSubjectName();
        editText.setText(name);
        Button button=dialog.findViewById(R.id.btn_level_update);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Subject string =new Subject(editText.getText().toString());
                strings.set(index, string);
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
