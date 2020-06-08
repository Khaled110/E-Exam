package com.example.onlinestudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainAdminPage extends AppCompatActivity {
    private Button level;
    private Button professorList;
    private Button subject;
    private Button asprofessor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin_page);
        level=findViewById(R.id.level_department);
        professorList=findViewById(R.id.professorlist);
        subject=findViewById(R.id.subject);
        asprofessor=findViewById(R.id.asprofessor);
        level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAdminPage.this,LevelOfFaculty.class);
                startActivity(intent);
            }
        });
        professorList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAdminPage.this,ProfessorList.class);
                startActivity(intent);
            }
        });
        asprofessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAdminPage.this,AsProfessor.class);
                startActivity(intent);
            }
        });
    }
}
