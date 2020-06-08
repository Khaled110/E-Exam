package com.example.onlineexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainAdminActivity extends AppCompatActivity {
    Button levels,professorList,subjects,asProfessor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        levels=findViewById(R.id.levels_admin);
        professorList=findViewById(R.id.professor_list_admin);
        asProfessor=findViewById(R.id.as_professor_admin);

        asProfessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainAdminActivity.this,MainProfActivity.class);
                startActivity(intent);

            }
        });
        levels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainAdminActivity.this,MainAdminLevelsActivity.class);
                startActivity(intent);
            }
        });

        professorList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainAdminActivity.this,MainAdminProfListActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.signout_menu){
            signout();
        }
        return super.onOptionsItemSelected(item);
    }

    public void signout(){
        FirebaseAuth.getInstance().signOut();
        finish();
        Intent intent=new Intent(MainAdminActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
