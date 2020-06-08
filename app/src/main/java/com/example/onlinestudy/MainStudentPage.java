package com.example.onlinestudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainStudentPage extends AppCompatActivity {
    private ListView studentSubjects;
    private Button rank,finalResult;
    ArrayList<StyleStudentSubject>stdSubjects;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_student_page);
        Intent intent =getIntent();
        final String stdName =intent.getExtras().getString("Student name");
        final String stdCode= intent.getExtras().getString("Student code");
        final String stdEmail=intent.getExtras().getString("Student email");
        final String stdPassword= intent.getExtras().getString("Student password");
        final String stdLevel= intent.getExtras().getString("Student level");
        final String stdDepartment= intent.getExtras().getString("Student department");
        Toast.makeText(this, "Hello "+stdName, Toast.LENGTH_SHORT).show();
        studentSubjects=findViewById(R.id.subject_view);
        rank=findViewById(R.id.rank);
        finalResult=findViewById(R.id.final_result);
        stdSubjects=new ArrayList<StyleStudentSubject>();
        stdSubjects.add(new StyleStudentSubject("Ethics"));
        stdSubjects.add(new StyleStudentSubject("History"));
        stdSubjects.add(new StyleStudentSubject("Computer Low"));
        stdSubjects.add(new StyleStudentSubject("Systen Analysis"));
        MyStudentSubjectAdapter myStudentSubjectAdapter =new MyStudentSubjectAdapter(stdSubjects);
        studentSubjects.setAdapter(myStudentSubjectAdapter);
        studentSubjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView subjectliststyle=view.findViewById(R.id.subject_list_style);
                Intent intent=new Intent(MainStudentPage.this,AboutSubject.class);
                intent.putExtra("subname",subjectliststyle.getText().toString());
                startActivity(intent);
            }
        });
        rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainStudentPage.this,Rank.class);
                startActivity(intent);
            }
        });
        finalResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainStudentPage.this,FinalResult.class);
                intent.putExtra("stdName",stdName);
                intent.putExtra("stdCode",stdCode);
                intent.putExtra("stdEmail",stdEmail);
                intent.putExtra("stdPassword",stdPassword);
                intent.putExtra("stdLevel",stdLevel);
                intent.putExtra("stdDepartment",stdDepartment);
                startActivity(intent);
            }
        });
    }
    class MyStudentSubjectAdapter extends BaseAdapter{
        ArrayList<StyleStudentSubject>stdSubjects=new ArrayList<StyleStudentSubject>();
        MyStudentSubjectAdapter(  ArrayList<StyleStudentSubject>stdSubjects){this.stdSubjects=stdSubjects;};
        @Override
        public int getCount() {
            return stdSubjects.size();
        }

        @Override
        public Object getItem(int position) {
            return stdSubjects.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater=getLayoutInflater();
            View view =inflater.inflate(R.layout.stylestudentsubject,parent,false);
            TextView subjectliststyle=view.findViewById(R.id.subject_list_style);
            subjectliststyle.setText(stdSubjects.get(position).studentSubject);
            return view;
        }
    }
 }
