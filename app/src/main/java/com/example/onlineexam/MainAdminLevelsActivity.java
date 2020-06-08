package com.example.onlineexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class MainAdminLevelsActivity extends AppCompatActivity implements LevelDialog.ExampleDialogListener, DepartDialog.DepartmentDialogListener {
    ListView list;
    ArrayList<Level> levels1;
    LevelAdapter adapter1;
    ImageView imageView;
    ListView departList;
    DepartAdapter departAdapter;
    ArrayList<Department> departments;
    DatabaseReference fbdb;
    Level level3;



    //ArrayList<Subject> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin_levels);

        list = findViewById(R.id.list);
        imageView = findViewById(R.id.img);
        departList=findViewById(R.id.list_depert);

        departments=new ArrayList<>();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();

            }
        });
        levels1=new ArrayList<>();
        ToastMaker toastMaker=new ToastMaker();
        Toast.makeText(MainAdminLevelsActivity.this,toastMaker.toastLevel(),Toast.LENGTH_SHORT).show();


        fbdb = FirebaseDatabase.getInstance().getReference().child("Levels");

      Query query6 = FirebaseDatabase.getInstance().getReference().child("Levels");
        query6.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0&&dataSnapshot.getValue().toString().length()>0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                   Object h = snapshot.getValue(Object.class);
                        HashMap<Subject,Object> mdata = (HashMap<Subject, Object>) h;
                        Object data =  mdata.get("LevelName");
                            levels1.add(new Level((String) data));
                        }
                        adapter1 = new LevelAdapter(MainAdminLevelsActivity.this,levels1);
                        list.setAdapter(adapter1);
                        adapter1.notifyDataSetChanged();
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                list.smoothScrollToPosition(levels1.size());
                            }
                        });
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Level level = (Level) levels1.get(position);
                    Intent intent=new Intent(MainAdminLevelsActivity.this,MainAdminDepartActivity.class);
                    intent.putExtra("level", level.getLevel());
                    startActivity(intent);

                    adapter1.notifyDataSetChanged();




            }
        });
    }



    public void openDialog(){
        LevelDialog exampleDialog=new LevelDialog();
        exampleDialog.show(getSupportFragmentManager(),"level dialog");

    }
    public void openDepartDialog(){
        DepartDialog exampleDialog=new DepartDialog();
        exampleDialog.show(getSupportFragmentManager(),"depart dialog");
    }

    @Override
    public void applyTexts(String levels) {
        levels1.add(new Level(levels));
        adapter1 = new LevelAdapter(MainAdminLevelsActivity.this,levels1);
        list.setAdapter(adapter1);
    }

    @Override
    public void applyTextsDepart(String depart) {
        departments.add(new Department(depart));
        departAdapter=new DepartAdapter(MainAdminLevelsActivity.this,departments);
        departList.setAdapter(departAdapter);
    }


}
