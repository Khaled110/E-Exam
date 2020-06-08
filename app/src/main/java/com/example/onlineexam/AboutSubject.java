package com.example.onlineexam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AboutSubject extends AppCompatActivity {
    private TextView subname;
    private Button startExam,tutorial,questionBank,additionalInfo,trainingExam,result,examStructure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_subject);
        subname=findViewById(R.id.sub_name);
        startExam=findViewById(R.id.student_start_exam);
        tutorial=findViewById(R.id.student_tutorial);
        questionBank=findViewById(R.id.student_question_bank);
        additionalInfo=findViewById(R.id.student_additonal_info);
        examStructure=findViewById(R.id.student_exam_structure);
        result=findViewById(R.id.student_result_t);
        trainingExam=findViewById(R.id.tranning);
        final String getsubname=getIntent().getStringExtra("subname");
        Toast.makeText(this, getsubname, Toast.LENGTH_SHORT).show();

        subname.setText(getsubname);

        startExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AboutSubject.this, QuestionBankProfActivity.class);
                intent.putExtra("type","Student");
                intent.putExtra("name",getsubname);
                intent.putExtra("startexam","yes");

                startActivity(intent);

            }
        });



        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AboutSubject.this, ToturialChaptersActivity.class);
                intent.putExtra("type","Student");
                intent.putExtra("name",getsubname);
                startActivity(intent);


            }
        });
            examStructure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(AboutSubject.this, ExamStructureActivity.class);
                    intent.putExtra("name",getsubname);
                    intent.putExtra("type","Student");


                    startActivity(intent);

                }
            });
        questionBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AboutSubject.this, QuestionBankChapterActivity.class);
                intent.putExtra("name",getsubname);
                startActivity(intent);


            }
        });

        additionalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent =new Intent(AboutSubject.this, StudentInfoActivity.class);
                intent.putExtra("name",getsubname);
                startActivity(intent);

            }
        });

        trainingExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent =new Intent(AboutSubject.this, StudentTrainingExam.class);
                startActivity(intent);

                 */
            }
        });

        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AboutSubject.this,ResultStudentActivity.class);
                intent.putExtra("name",getsubname);
                startActivity(intent);


            }
        });
    }
}
