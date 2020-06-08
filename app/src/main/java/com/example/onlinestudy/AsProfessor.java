package com.example.onlinestudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AsProfessor extends AppCompatActivity {

    private Button examStructure,tutorial,questionBank,additionalInfo,evaluateQuestion,studentResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_as_professor);
        examStructure=findViewById(R.id.professor_exam_structure);
        tutorial=findViewById(R.id.professor_tutorial);
        questionBank=findViewById(R.id.professor_question_bank);
        additionalInfo=findViewById(R.id.professor_additonal_info);
        evaluateQuestion=findViewById(R.id.professor_evaluate_question);
        studentResult=findViewById(R.id.professor_result);

        examStructure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AsProfessor.this, ProfessorExamStructure.class);
                startActivity(intent);
            }
        });

        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AsProfessor.this, PreofessorTutorial.class);
                startActivity(intent);
            }
        });

        questionBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AsProfessor.this, ProfessorQuestionBank.class);
                startActivity(intent);
            }
        });

        additionalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AsProfessor.this, ProfessorAdditionalInfo.class);
                startActivity(intent);
            }
        });

        evaluateQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AsProfessor.this, ProfessorEvaluateQuestion.class);
                startActivity(intent);
            }
        });

        studentResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AsProfessor.this, ProfessorResult.class);
                startActivity(intent);
            }
        });
    }
}
