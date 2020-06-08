package com.example.onlinestudy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SubjectOfDepartment extends AppCompatActivity implements SubjectDialog.ExampleDialogListenrSubject {
    private ListView subjects;
    private Button addSubject;
    private TextView subjectOfDepartment;
    ArrayList<StyleSubjectDepartment> sub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_of_department);
        subjects=findViewById(R.id.subjects);
        addSubject=findViewById(R.id.add_subject);
        subjectOfDepartment=findViewById(R.id.subject_of_department);
        Intent intent =this.getIntent();
        String getInfo=intent.getExtras().getString("department");
        Toast.makeText(this,getInfo, Toast.LENGTH_LONG).show();
        subjectOfDepartment.setText("Subject Of "+getInfo);
        sub=new ArrayList<StyleSubjectDepartment>();
        addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogSubject();
            }
        });


    }
    public void openDialogSubject(){
        SubjectDialog subjectDialog =new SubjectDialog();
        subjectDialog.show(getSupportFragmentManager(),"Level Dialog");
    }

    @Override
    public void applyTextSubject(String Subject) {
        sub.add(new StyleSubjectDepartment(Subject));
        MySubjectAdapter subjectAdapter =new MySubjectAdapter(sub);
        subjects.setAdapter(subjectAdapter);
    }

    class MySubjectAdapter extends BaseAdapter {
        ArrayList<StyleSubjectDepartment> sub=new ArrayList<StyleSubjectDepartment>();
        MySubjectAdapter( ArrayList<StyleSubjectDepartment> sub){this.sub=sub;}
        @Override
        public int getCount() {
            return sub.size();
        }

        @Override
        public Object getItem(int position) {
            return sub.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater =getLayoutInflater();
            View view = inflater.inflate(R.layout.stylesubjectdepartment,parent,false);
            TextView subject =view.findViewById(R.id.subject);
            ImageView editSubject=view.findViewById(R.id.edit_subject);
            ImageView deleteSubject=view.findViewById(R.id.delete_subject);
            subject.setText(sub.get(position).subject);
            deleteSubject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SubjectOfDepartment.this);
                    builder.setMessage("Are you sure you want delete").setTitle("Delete")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    sub.remove(position);
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }
                });
            return view;
        }
    }
}
