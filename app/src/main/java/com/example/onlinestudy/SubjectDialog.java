package com.example.onlinestudy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import javax.security.auth.Subject;

public class SubjectDialog extends AppCompatDialogFragment {
    private EditText subject;
    private Spinner spinnerProf;
    private ExampleDialogListenrSubject listenr;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater =getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.subjectdialog,null);
        builder.setView(view)
                .setTitle("Create Subject")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (subject.getText().toString().length()>0)
                        {
                            String Subject= subject.getText().toString();
                            listenr.applyTextSubject(Subject);
                        }else {
                            Toast.makeText(getContext(), "Please add subject", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        subject=view.findViewById(R.id.textsubject);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listenr =(ExampleDialogListenrSubject)context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must implement Example Dialog subject");
        }
    }

    public interface ExampleDialogListenrSubject{
        void applyTextSubject(String Subject);

    }
}


