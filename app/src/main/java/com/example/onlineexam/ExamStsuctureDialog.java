package com.example.onlineexam;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class ExamStsuctureDialog extends AppCompatDialogFragment {
    Spinner  number,type,category,chapter;
    String name,numberSelected,typeSelected,categorySelected,chapterSelected;
    ArrayList<String> chapters;
    ExamStructureDialogListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.exam_structure_dialog,null);
        number=view.findViewById(R.id.spin_exam_number);
        type=view.findViewById(R.id.spin_exam_type);
        category=view.findViewById(R.id.spin_exam_category);
        chapter=view.findViewById(R.id.spin_exam_chapter);
        chapters=new ArrayList<>();
        Bundle bundle = getArguments();
       name = bundle.getString("name","");
        builder.setView(view)
                .setTitle("Enter exam structure ..!")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.applyTexts(numberSelected,typeSelected,categorySelected,chapterSelected);
            }
        });
        String [] numbers={"Numbers","1","2","3","4","5","6","7","8","9","10"};

        ArrayAdapter<String> numberAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,numbers);
        numberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        number.setAdapter(numberAdapter);
        numberAdapter.notifyDataSetChanged();
        number.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                numberSelected=(String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String [] types ={"Type","MCQ","True","open"};
        ArrayAdapter<String> typeAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,types);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(typeAdapter);
        typeAdapter.notifyDataSetChanged();
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeSelected=(String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final String [] categories={"Category","A","B","C","D"};
        final ArrayAdapter<String> categoryAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categorySelected=(String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Query query6 = FirebaseDatabase.getInstance().getReference().child("Subjects").child(name).child("Tutorial").orderByChild("chapterName");
        query6.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0 && dataSnapshot.getValue().toString().length() > 0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Object h = snapshot.getValue(Object.class);
                            Map<String, Object> mdata = (Map<String, Object>) h;
                            Object data = mdata.get("chapterName");

                            chapters.add((String) data);
                        }


                       ArrayAdapter<String> chapterAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,chapters);
                        chapterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        chapter.setAdapter(chapterAdapter);
                        chapterAdapter.notifyDataSetChanged();

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        chapter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chapterSelected=(String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return builder.create();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener=(ExamStructureDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+ " Must implemented subjectDialogListener ");
        }
    }
    public interface ExamStructureDialogListener{
        public void applyTexts(String number, String type,String category,String chapter);
    }
}
