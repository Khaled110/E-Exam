package com.example.onlineexam;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;

public class ChapterDialog extends AppCompatDialogFragment {
 EditText name;
 ArrayList<Chapter> chapters;
 chapterDialogListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.chapter_dialog,null);
        name=view.findViewById(R.id.edit_chapter);
        chapters=new ArrayList<>();
        builder.setView(view);
        builder.setTitle("Create Chapter");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String chapter=name.getText().toString();
                listener.applyChapterText(chapter);

            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener= (chapterDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+ " Must implemented chapterDlialogListener ");

        }
    }

    public interface chapterDialogListener{
        public void applyChapterText(String chapter);
    }
}
