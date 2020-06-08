package com.example.onlineexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SubjectProfDetailsActivity extends AppCompatActivity {
TextView name;
Button toturial,info,questionBank,evaluateQuestion,examStructure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_prof_details);
        name=findViewById(R.id.txt_subject_name);
        toturial=findViewById(R.id.sub_prof_tutorial);
        info=findViewById(R.id.sub_prof_additional);
        questionBank=findViewById(R.id.sub_prof_bank);
        evaluateQuestion=findViewById(R.id.sub_prof_evaluate_questions);
        examStructure=findViewById(R.id.sub_prof_structure);

        final String nameSubject=getIntent().getStringExtra("stringName");
        name.setText(nameSubject);
        toturial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SubjectProfDetailsActivity.this,ToturialChaptersActivity.class);
                intent.putExtra("name",nameSubject);
                startActivity(intent);
            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SubjectProfDetailsActivity.this,ProfInfoActivity.class);
                startActivity(intent);
            }
        });
        questionBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SubjectProfDetailsActivity.this,QuestionBankChapterActivity.class);
                intent.putExtra("name",nameSubject);
                startActivity(intent);
            }
        });
        evaluateQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SubjectProfDetailsActivity.this,EvaluateQListActivity.class);
                intent.putExtra("name",nameSubject);
                startActivity(intent);
            }
        });
        examStructure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SubjectProfDetailsActivity.this,ExamStructureActivity.class);
                intent.putExtra("name",nameSubject);
                startActivity(intent);
            }
        });


    }
}
