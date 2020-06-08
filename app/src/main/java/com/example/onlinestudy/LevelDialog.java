package com.example.onlinestudy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class LevelDialog extends AppCompatDialogFragment {
    private EditText level;
    private ExampleDialogListenr listenr;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater =getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.leveldialog,null);
        builder.setView(view)
                .setTitle("Create Level")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       if (level.getText().toString().length()>0)
                       {
                           String Level= level.getText().toString();
                           listenr.applyText(Level);
                       }else {
                           Toast.makeText(getContext(), "Please add level", Toast.LENGTH_SHORT).show();
                       }

                    }
                });
        level=view.findViewById(R.id.textlevel);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listenr =(ExampleDialogListenr)context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must implement Example Dialog");
        }
           }

    public interface ExampleDialogListenr{
        void applyText(String Level);

    }
}
