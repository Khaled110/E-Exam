package com.example.onlineexam;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DepartDialog extends AppCompatDialogFragment {


    EditText editText;
    DepartmentDialogListener listener;
    ImageView imageView;
    String level;
    ListView listView;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        final View v=layoutInflater.inflate(R.layout.depart_dialog,null);
        editText=v.findViewById(R.id.edit_depart);
        listView=v.findViewById(R.id.list_depert);



        builder.setView(v)
                .setTitle("create Department ")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(editText.getText().toString().length()>0){
                            level =editText.getText().toString();
                            listener.applyTextsDepart(level);

                        }else {
                            Toast.makeText(getActivity(),"Must Enter Department",Toast.LENGTH_SHORT).show();
                        }



                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener= (DepartmentDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+ " Must implemented exampleDialogListener ");
        }
    }

    public interface DepartmentDialogListener{
        public void applyTextsDepart(String depart);
    }


}
