package com.example.onlineexam;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class SubjectDialog extends AppCompatDialogFragment {
    EditText editText;
    String subject;
    Spinner subProf;
    ArrayList<String> prof;
    ArrayAdapter profAdapter;
    private subjectDialogListener listener;
    DatabaseReference fbdb;
    String profSelected;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        View v=layoutInflater.inflate(R.layout.subject_dialog,null);
        editText=v.findViewById(R.id.edit_subject_name);
        subProf=v.findViewById(R.id.spiner_subject_prof);
        fbdb = FirebaseDatabase.getInstance().getReference().child("users");
        prof=new ArrayList<>();


        Query query6 = FirebaseDatabase.getInstance().getReference().child("users").child("Professor");
        query6.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    // levels1.clear();
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0 && dataSnapshot.getValue().toString().length() > 0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Object h = snapshot.getValue(Object.class);
                            Map<String, Object> mdata = (Map<String, Object>) h;
                            Object data = mdata.get("name");

                            prof.add((String) data);
                        }


                        profAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,prof);
                        profAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subProf.setAdapter(profAdapter);
                        profAdapter.notifyDataSetChanged();
                       /* new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                listView.smoothScrollToPosition(departments.size());
                            }
                         });*/
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Query query7 = FirebaseDatabase.getInstance().getReference().child("users").child("Admin");
        query7.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    // levels1.clear();
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0 && dataSnapshot.getValue().toString().length() > 0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Object h = snapshot.getValue(Object.class);
                            Map<Subject, Object> mdata = (Map<Subject, Object>) h;
                            Object data = mdata.get("name");

                            prof.add((String) data);
                        }


                        profAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,prof);
                        profAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subProf.setAdapter(profAdapter);
                        profAdapter.notifyDataSetChanged();
                       /* new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                listView.smoothScrollToPosition(departments.size());
                            }
                        });*/
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        subProf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                profSelected= (String) parent.getItemAtPosition(position);
                Toast.makeText(getActivity(),profSelected+" ",Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
      //  profSelected=subProf.getSelectedItem().toString();


        builder.setView(v)
                .setTitle("The creation ")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(editText.getText().toString().length()>0){
                        subject =editText.getText().toString();
                        listener.applyTexts(subject,profSelected);

                        }else {
                            Toast.makeText(getActivity(),"Must Enter Subject",Toast.LENGTH_SHORT).show();
                        }



                    }
                });


        return builder.create();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener=(subjectDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+ " Must implemented subjectDialogListener ");
        }
    }
    public interface subjectDialogListener{
        public void applyTexts(String subject, String prof);
    }
}
