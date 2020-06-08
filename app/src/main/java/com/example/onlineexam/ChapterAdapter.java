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

public class ChapterAdapter extends ArrayAdapter<Chapter> {
    ArrayList<Chapter> chapters;
    AlertDialog dialog;
    public ChapterAdapter(@NonNull Context context, @NonNull ArrayList<Chapter> chapters) {
        super(context, 0, chapters);
        this.chapters=chapters;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(getContext()).inflate(R.layout.custom_row_tutorial_chapters,parent,false);
        TextView text=convertView.findViewById(R.id.txt_chapter);
        ImageView edit=convertView.findViewById(R.id.img_edit_chapter_i);
        ImageView remove=convertView.findViewById(R.id.img_remove_chapter_i);

        final Chapter chapter=getItem(position);
        text.setText(chapter.getName());
        text.setTextColor(convertView.getResources().getColor(R.color.colorAccent));
        text.setTextSize(20);


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdataCHapter(chapters.get(position),position);
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("Warning!")
                        .setMessage("Are you sure you want to delete this chapter ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                chapters.remove(position);
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
        return convertView;
    }
    public void showUpdataCHapter(Chapter oldItem,final int index){
        final Dialog dialog=new Dialog(getContext());
        dialog.setTitle("Update Chapter");
        dialog.setContentView(R.layout.activity_updata_level);
        final EditText editText=dialog.findViewById(R.id.edit_level_update);
        String name=oldItem.getName();
        editText.setText(name);
        Button button=dialog.findViewById(R.id.btn_level_update);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chapter chapter=new Chapter(editText.getText().toString());
                chapters.set(index,chapter);
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
