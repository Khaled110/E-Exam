package com.example.onlineexam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainProfActivity extends AppCompatActivity {
   private Spinner level,depart;
   private ListView list;
   private Subject string;
    ArrayList<Level> levels1;
    ArrayList<Department> departments;
    ArrayList<Subject> subjects;
    String [] values;
    LevelAdapter adapter1;
    String levelName;
    String [] values2;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    String profName;
    Object data;
    ArrayList<String> nasa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_prof);

        level=findViewById(R.id.spiner_level_prof);
        depart=findViewById(R.id.spiner_depart_prof);
        list=findViewById(R.id.list_prof);
        levels1=new ArrayList<>();
        departments=new ArrayList<>();
        subjects=new ArrayList<>();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Levels");
        auth = FirebaseAuth.getInstance();

        if(auth!=null){
            profName=auth.getCurrentUser().getEmail();
        }
        ProfUser user=new ProfUser(profName);

        Query query6 = FirebaseDatabase.getInstance().getReference().child("users").child("Admin").orderByChild("email").equalTo(profName);
        query6.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    // levels1.clear();
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0&&dataSnapshot.getValue().toString().length()>0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Object h = snapshot.getValue(Object.class);
                            HashMap<String,Object> mdata = (HashMap<String, Object>) h;
                           data =  mdata.get("name");
                            Toast.makeText(MainProfActivity.this,"hi "+data,Toast.LENGTH_LONG).show();

                        }

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Query query7 = FirebaseDatabase.getInstance().getReference().child("users").child("Professor").orderByChild("email").equalTo(profName);
        query7.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    // levels1.clear();
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0&&dataSnapshot.getValue().toString().length()>0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Object h = snapshot.getValue(Object.class);
                            HashMap<String,Object> mdata = (HashMap<String, Object>) h;
                            data =  mdata.get("name");
                            Toast.makeText(MainProfActivity.this,"hi "+data,Toast.LENGTH_LONG).show();

                        }

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query query22 = FirebaseDatabase.getInstance().getReference().child("Levels");
        query22.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                levels1.clear();
                if(dataSnapshot!=null){
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0&&dataSnapshot.getValue().toString().length()>0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Object h = snapshot.getValue(Object.class);
                            HashMap<String,Object> mdata = (HashMap<String, Object>) h;
                            Object data =  mdata.get("LevelName");
                            levels1.add(new Level((String) data));
                        }
                       values=new String[levels1.size()];

                        for(int i=0;i<values.length;i++){
                            values[i]=levels1.get(i).getLevel();
                        }
                        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainProfActivity.this,android.R.layout.simple_spinner_item,values){
                            @NonNull
                            @Override
                            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                View view =super.getView(position, convertView, parent);
                                TextView textView=view.findViewById(android.R.id.text1);
                                textView.setTextColor(getResources().getColor(R.color.colorAccent));
                                textView.setTextSize(20);

                                return view;
                            }
                        };
                        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        level.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               levelName=parent.getItemAtPosition(position).toString();

                Query query6 = FirebaseDatabase.getInstance().getReference().child("Levels").child(parent.getItemAtPosition(position).toString()).child("Department");
                query6.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        departments.clear();
                        if(dataSnapshot!=null){
                            levels1.clear();
                            if (dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0&&dataSnapshot.getValue().toString().length()>0) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Object h = snapshot.getValue(Object.class);
                                    HashMap<String,Object> mdata = (HashMap<String, Object>) h;
                                    Object data =  mdata.get("DeptName");
                                    departments.add(new Department((String) data));
                                }
                                String [] values1=new String[departments.size()];

                                for(int i=0;i<values1.length;i++){
                                    values1[i]=departments.get(i).getDepartment();
                                }

                                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainProfActivity.this,android.R.layout.simple_spinner_item,values1){
                                    @NonNull
                                    @Override
                                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                        View view =super.getView(position, convertView, parent);
                                        TextView textView=view.findViewById(android.R.id.text1);
                                        textView.setTextColor(getResources().getColor(R.color.colorAccent));
                                        textView.setTextSize(20);

                                        return view;
                                    }
                                };
                                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                                depart.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        depart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Query query6 = FirebaseDatabase.getInstance().getReference().child("Levels").child(levelName)
                        .child("Department").child(departments.get(position).getDepartment()).child("Subjects").orderByChild("profName").equalTo((String) data);

                query6.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       subjects.clear();



                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Object h = snapshot.getValue(Object.class);
                                    HashMap<String,Object> mdata = (HashMap<String, Object>) h;
                                    Object data =  mdata.get("SubjectName");
                                    subjects.add(new Subject((String) data));
                                }
                                values2=new String[subjects.size()];

                                for(int i=0;i<values2.length;i++){
                                    values2[i]=subjects.get(i).getSubjectName();
                                }

                                final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(MainProfActivity.this,android.R.layout.simple_list_item_1,values2){
                                    @NonNull
                                    @Override
                                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                        View view =super.getView(position, convertView, parent);
                                        TextView textView=view.findViewById(android.R.id.text1);
                                        textView.setTextColor(getResources().getColor(R.color.colorAccent));
                                        textView.setTextSize(20);

                                        return view;
                                    }
                                };
                                if(subjects.size()==0){
                                    list.setVisibility(View.INVISIBLE);
                                }else{
                                    list.setVisibility(View.VISIBLE);
                                    list.setAdapter(adapter2);
                                    adapter2.notifyDataSetChanged();
                                }


                            }



                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainProfActivity.this,SubjectProfDetailsActivity.class);
                    Subject subject=subjects.get(position);
                intent.putExtra("stringName", subject.getSubjectName());
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
        Intent intent=new Intent(MainProfActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
