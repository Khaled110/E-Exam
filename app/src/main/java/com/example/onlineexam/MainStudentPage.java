package com.example.onlineexam;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class MainStudentPage extends AppCompatActivity {
    private ListView studentSubjects;
    private Button rank,finalResult;
    String stdName,stdCode,stdEmail,stdPassword,stdLevel,stdDepartment,name;
    ArrayList<String>stdSubjects;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener mAuthListener;

    ProfUser userAdmin;
    ArrayList<ProfUser> profUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_student_page);
          stdName =getIntent().getStringExtra("studentName");
        stdCode= getIntent().getStringExtra("Student code");
       stdEmail=getIntent().getStringExtra("Student email");
         stdPassword= getIntent().getStringExtra("Student password");
       stdLevel= getIntent().getStringExtra("Student level");
        stdDepartment= getIntent().getStringExtra("Student department");
        studentSubjects=findViewById(R.id.subject_view);
        profUsers =new ArrayList<>();
        rank=findViewById(R.id.rank);
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null){
            name=auth.getCurrentUser().getEmail();
        }
        finalResult=findViewById(R.id.final_result);
        stdSubjects=new ArrayList();
        Query query8 = FirebaseDatabase.getInstance().getReference().child("users").child("Student").orderByChild("email").equalTo(name);
        query8.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    // levels1.clear();
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0 && dataSnapshot.getValue().toString().length() > 0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Object h = snapshot.getValue(Object.class);
                            Map<String, Object> mdata = (Map<String, Object>) h;
                            //  adminName = mdata.get("name");
                            // Artist artist = snapshot.getValue(Artist.class);*/
                            userAdmin=new ProfUser();
                            userAdmin=snapshot.getValue(ProfUser.class);
                            profUsers.add(userAdmin);
                        }
                        if(userAdmin!=null){
                            stdLevel=userAdmin.getLevel();
                            stdDepartment=userAdmin.getDepartment();
                        }

    Query query6 = FirebaseDatabase.getInstance().getReference().child("Levels").child(stdLevel).child("Department")
                .child(stdDepartment).child("Subjects");
        query6.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    // levels1.clear();
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0 && dataSnapshot.getValue().toString().length() > 0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Object h = snapshot.getValue(Object.class);
                            Map<String, Object> mdata = (Map<String, Object>) h;
                            Object data = mdata.get("SubjectName");

                            stdSubjects.add(((String) data));
                        }
                        ArrayAdapter <String> subjectAdapter = new ArrayAdapter<String>(MainStudentPage.this,android.R.layout.simple_list_item_1 ,stdSubjects){

                            @NonNull
                            @Override
                            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                View view=super.getView(position, convertView, parent);
                                TextView textView=view.findViewById(android.R.id.text1);
                                  textView.setTextColor(getResources().getColor(R.color.colorAccent));
                                  textView.setTextSize(20);
                                return view;

                            }
                        };
                        studentSubjects.setAdapter(subjectAdapter);
                        subjectAdapter.notifyDataSetChanged();
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                studentSubjects.smoothScrollToPosition(stdSubjects.size());
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


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        studentSubjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = parent.getItemAtPosition(position).toString();
               Intent intent=new Intent(MainStudentPage.this,AboutSubject.class);
                intent.putExtra("subname",name);
                startActivity(intent);


            }
        });
        rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent=new Intent(MainStudentPage.this,Rank.class);
                startActivity(intent);

                */
            }
        });
        finalResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent intent=new Intent(MainStudentPage.this,FinalResult.class);
                intent.putExtra("stdName",stdName);
                intent.putExtra("stdCode",stdCode);
                intent.putExtra("stdEmail",stdEmail);
                intent.putExtra("stdPassword",stdPassword);
                intent.putExtra("stdLevel",stdLevel);
                intent.putExtra("stdDepartment",stdDepartment);
                startActivity(intent);

              */
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
        Intent intent=new Intent(MainStudentPage.this,MainActivity.class);
        startActivity(intent);
    }

 }
