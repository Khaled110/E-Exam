package com.example.onlineexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.Map;

public class MainAdminDepartActivity extends AppCompatActivity implements DepartDialog.DepartmentDialogListener {
    LinearLayout layout;
    ImageView img;
    ListView listView;
    TextView textView;
    DepartAdapter departAdapter;
    ArrayList<Department> departments;
    Button button;
    Department department;
    String levelDialogDept;
    String levelName;
    DatabaseReference fbdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin_depart);
        layout=findViewById(R.id.layout_depart);
        listView=findViewById(R.id.list_depert);
        img=findViewById(R.id.img_depart);
        textView=findViewById(R.id.depart_faculty_text);
        levelDialogDept=getIntent().getStringExtra("levelDialog");

         levelName=getIntent().getStringExtra("level");
        ToastMaker toastMaker=new ToastMaker();
        Toast.makeText(MainAdminDepartActivity.this,toastMaker.toastDepartment(),Toast.LENGTH_SHORT).show();


        textView.setText(levelName);
        fbdb = FirebaseDatabase.getInstance().getReference().child("Levels");


            if(levelName!=null) {


                Query query6 = FirebaseDatabase.getInstance().getReference().child("Levels").child(levelName).child("Department");
                query6.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            // levels1.clear();
                            if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0 && dataSnapshot.getValue().toString().length() > 0) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Object h = snapshot.getValue(Object.class);
                                    Map<Subject, Object> mdata = (Map<Subject, Object>) h;
                                    Object data = mdata.get("DeptName");
                                    departments.add(new Department((String) data));
                                }
                                departAdapter = new DepartAdapter(MainAdminDepartActivity.this, departments);
                                listView.setAdapter(departAdapter);
                                departAdapter.notifyDataSetChanged();
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        listView.smoothScrollToPosition(departments.size());
                                    }
                                });
                            }


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        department=new Department();

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openDepartDialog();
            }
        });
        departments=new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Department department= (Department) departments.get(position);
                Intent intent=new Intent(MainAdminDepartActivity.this,MainAdminAddSubActivity.class);
                intent.putExtra("departmentName",department.getDepartment());
                intent.putExtra("levelSub",levelName);
                intent.putExtra("levelDi",levelDialogDept);
                startActivity(intent);
                departAdapter.notifyDataSetChanged();
            }
        });
    }
    public void openDepartDialog(){
        DepartDialog exampleDialog=new DepartDialog();
        exampleDialog.show(getSupportFragmentManager(),"depart dialog");
    }


    @Override
    public void applyTextsDepart(String depart) {
        departments.add(new Department(depart));
        departAdapter=new DepartAdapter(MainAdminDepartActivity.this,departments);
        listView.setAdapter(departAdapter);
    }


}
