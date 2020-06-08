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
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class DepartAdapter extends ArrayAdapter<Department> {
    ListView listView;
    AlertDialog dialog;
    ArrayList<Department> departments;
    public DepartAdapter(@NonNull Context context, @NonNull ArrayList<Department> departments) {
        super(context, 0, departments);
        this.departments=departments;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(getContext()).inflate(R.layout.custom_row_depart,parent,false);
        TextView textView=convertView.findViewById(R.id.txt_depart);
        ImageView edit=convertView.findViewById(R.id.img_edit_depart_i);
        ImageView remove=convertView.findViewById(R.id.img_remove_depart_i);
        listView=convertView.findViewById(R.id.layout_depart);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("Warning!")
                        .setMessage("Are you sure you want to delete this department ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                departments.remove(position);
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
                showUpdata1(departments.get(position),position);
            }
        });



        Department department=getItem(position);
        textView.setText(department.getDepartment());

        return convertView;
    }

    public void showUpdata1(Department oldItem,final int index){
        final Dialog dialog=new Dialog(getContext());
        dialog.setTitle("Update Department");
        dialog.setContentView(R.layout.activity_updata_level);
        final EditText editText=dialog.findViewById(R.id.edit_level_update);
        String name=oldItem.getDepartment();
        editText.setText(name);
        Button button=dialog.findViewById(R.id.btn_level_update);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Department level=new Department(editText.getText().toString());
                departments.set(index,level);
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
