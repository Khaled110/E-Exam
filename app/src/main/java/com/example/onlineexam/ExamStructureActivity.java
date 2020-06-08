package com.example.onlineexam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExamStructureActivity extends AppCompatActivity implements ExamStsuctureDialog.ExamStructureDialogListener {
        ListView listView;
        ImageView imageView;
        String name,type;
    DatabaseReference dbref;
    ExamStructure examStructure;
    ArrayList<ExamStructure> examStructures;
    ExamStructureAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_structure);
        listView=findViewById(R.id.list_exam_structure);
        imageView=findViewById(R.id.img_exam_structure);
        name=getIntent().getStringExtra("name");
        examStructures=new ArrayList<>();
        type=getIntent().getStringExtra("type");
        dbref=FirebaseDatabase.getInstance().getReference();
        viewStructure();
        if(type!=null){
            if(type.equals("Student")){
                imageView.setVisibility(View.GONE);
            }

        }






        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }
    private void openDialog(){
        ExamStsuctureDialog exampleDialog=new ExamStsuctureDialog();
        Bundle bundle = new Bundle();
        bundle.putString("name",name);
        exampleDialog.setArguments(bundle);
        exampleDialog.show((ExamStructureActivity.this).getSupportFragmentManager(),"Exam Structure");
    }

    @Override
    public void applyTexts(String number, String type, String category, String chapter) {
        ExamStructure examStructure=new ExamStructure(number,type,category,chapter);
        dbref.child("Subjects").child(name).child("ExamStructure").push().setValue(examStructure);
        examStructures.clear();
        viewStructure();



    }
    private void viewStructure(){
        Query query =FirebaseDatabase.getInstance().getReference().child("Subjects").child(name).child("ExamStructure").orderByChild("type");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    examStructure=snapshot.getValue(ExamStructure.class);
                    examStructures.add(examStructure);
                }


                arrayAdapter=new ExamStructureAdapter(ExamStructureActivity.this,examStructures);
                listView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        listView.smoothScrollToPosition(examStructures.size());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
