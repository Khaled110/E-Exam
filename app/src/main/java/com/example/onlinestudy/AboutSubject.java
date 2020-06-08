package com.example.onlinestudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AboutSubject extends AppCompatActivity {
    private TextView subname;
    private Button startExam,tutorial,questionBank,additionalInfo,trainingExam,result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_subject);
        subname=findViewById(R.id.sub_name);
        startExam=findViewById(R.id.student_start_exam);
        tutorial=findViewById(R.id.student_tutorial);
        questionBank=findViewById(R.id.student_question_bank);
        additionalInfo=findViewById(R.id.student_additonal_info);
        trainingExam=findViewById(R.id.student_training_exam);
        result=findViewById(R.id.student_result);
        Intent intent=getIntent();
        String getsubname=intent.getExtras().getString("subname");
        Toast.makeText(this, getsubname, Toast.LENGTH_SHORT).show();

        subname.setText(getsubname);

        startExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AboutSubject.this, StudentStartExam.class);
                startActivity(intent);
            }
        });

        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AboutSubject.this, StudentTutorial.class);
                startActivity(intent);
            }
        });

        questionBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AboutSubject.this, StudentQuestionBank.class);
                startActivity(intent);
            }
        });

        additionalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AboutSubject.this, StudentAdditionalInfo.class);
                startActivity(intent);
            }
        });

        trainingExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AboutSubject.this, StudentTrainingExam.class);
                startActivity(intent);
            }
        });

        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AboutSubject.this,StudentResult.class);
                startActivity(intent);
            }
        });
    }
}
