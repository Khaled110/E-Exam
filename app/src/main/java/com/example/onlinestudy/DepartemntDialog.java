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

public class DepartemntDialog extends AppCompatDialogFragment {
    private EditText department;
    private ExampleDialogListenrDepatment listenr;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater =getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.departmentdialog,null);
        builder.setView(view)
                .setTitle("Create Department")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (department.getText().toString().length()>0)
                        {
                            String Department= department.getText().toString();
                            listenr.applyTextDepartment(Department);
                        }else {
                            Toast.makeText(getContext(), "Please add department", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        department=view.findViewById(R.id.textdepartment);
        return builder.create();
    }
     public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    try {
        listenr =(ExampleDialogListenrDepatment) context;
    }catch (ClassCastException e){
        throw new ClassCastException(context.toString()+"must implement Example Dialog department");
    }
}

public interface ExampleDialogListenrDepatment{
    void applyTextDepartment(String Department);

}
}


