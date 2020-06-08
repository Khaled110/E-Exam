package com.example.onlinestudy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.LauncherActivity;
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
import java.util.zip.Inflater;

public class DepartmentOfLevel extends AppCompatActivity implements DepartemntDialog.ExampleDialogListenrDepatment{
    private ListView departments;
    private Button addDepartment;
    private TextView departmentOfLevel;
    ArrayList<StyleDepartmentLevel>depart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departmentoflevel);
        departments=findViewById(R.id.departments);
        addDepartment=findViewById(R.id.add_department);
        departmentOfLevel=findViewById(R.id.department_of_level);
        Intent intent =this.getIntent();
        String getInfo=intent.getExtras().getString("level");
        Toast.makeText(this, getInfo, Toast.LENGTH_LONG).show();
        departmentOfLevel.setText("Department Of "+getInfo);
        depart=new ArrayList<StyleDepartmentLevel>();
        addDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogDepartment();
            }
        });
    }
    public void openDialogDepartment(){
        DepartemntDialog departemntDialog =new DepartemntDialog();
        departemntDialog.show(getSupportFragmentManager(),"Level Dialog");
    }

    @Override
    public void applyTextDepartment(String Department) {
        depart.add(new StyleDepartmentLevel(Department));
        MyDepartmentAdpter adpterDepartment=new MyDepartmentAdpter(depart);
        departments.setAdapter(adpterDepartment);
    }


    class MyDepartmentAdpter extends BaseAdapter{
        ArrayList<StyleDepartmentLevel>depart=new ArrayList<StyleDepartmentLevel>();
        MyDepartmentAdpter(ArrayList<StyleDepartmentLevel>depart){
            this.depart=depart;
        }
        @Override
        public int getCount() {
            return depart.size();
        }

        @Override
        public Object getItem(int position) {
            return depart.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View view=inflater.inflate(R.layout.styledepartmentlevel,parent,false);
            final TextView department=view.findViewById(R.id.department);
            ImageView editDepartment=view.findViewById(R.id.edit_department);
            ImageView deleteDepartment=view.findViewById(R.id.delete_depaartment);
            department.setText(depart.get(position).department);
            department.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Intent intent=new Intent(DepartmentOfLevel.this,SubjectOfDepartment.class);
                   intent.putExtra("department",department.getText().toString());
                    startActivity(intent);
                }
            });
            deleteDepartment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DepartmentOfLevel.this);
                    builder.setMessage("Are you sure you want delete").setTitle("Delete")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    depart.remove(position);
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
