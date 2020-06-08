package com.example.onlineexam;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.ArrayList;

public class LevelDialog extends AppCompatDialogFragment implements DepartDialog.DepartmentDialogListener {


    EditText editText;
    ExampleDialogListener listener;
    ImageView imageView;
    String level;
    ListView listView,departList;
    DepartAdapter departAdapter;
    ArrayList<Department> departments;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        View v=layoutInflater.inflate(R.layout.level_dialog,null);
        editText=v.findViewById(R.id.edit_level);
        listView=v.findViewById(R.id.list);
        departList=v.findViewById(R.id.list_depert);

        departments=new ArrayList<>();


        builder.setView(v)
                .setTitle("Create level")
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
                            listener.applyTexts(level);
                        Intent intent=new Intent(getActivity(),MainAdminDepartActivity.class);
                        intent.putExtra("levelDialog",level);
                        startActivity(intent);
                        }else {
                            Toast.makeText(getActivity(),"Must Enter Level",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        return builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener= (ExampleDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+ " Must implemented exampleDialogListener ");
        }
    }
    public void openDepartDialog(){
        DepartDialog exampleDialog=new DepartDialog();
        exampleDialog.show(getFragmentManager(),"depart dialog");
    }

    @Override
    public void applyTextsDepart(String depart) {
        departments.add(new Department(depart));
        departAdapter=new DepartAdapter(getActivity(),departments);
        departList.setAdapter(departAdapter);
    }

    public interface ExampleDialogListener{
        public void applyTexts(String level);
    }


}
