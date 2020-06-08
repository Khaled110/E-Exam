package com.example.onlineexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ResultStudentActivity extends AppCompatActivity {
    FirebaseDatabase fbdb;
    DatabaseReference dbref;
    TextView result;
    String subjectName,name;
    Result resultExam;
    ProfUser user;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_student);
        fbdb=FirebaseDatabase.getInstance();;
         result=findViewById(R.id.result_student);
         subjectName=getIntent().getStringExtra("name");
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null){
            name=auth.getCurrentUser().getEmail();
        }

        viewStudentResult();



    }
    private void viewStudentResult(){

        Query query7 = FirebaseDatabase.getInstance().getReference().child("users").child("Student").orderByChild("email").equalTo(name);
        query7.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    // levels1.clear();
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0 && dataSnapshot.getValue().toString().length() > 0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            user = snapshot.getValue(ProfUser.class);
                        }
                        Log.e("username",user.getName()+"     "+subjectName);
                        Query query =FirebaseDatabase.getInstance().getReference().child("result").child(user.getName()).
                                child("Subjects").orderByChild("subjectName").equalTo(subjectName);

                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                    resultExam=snapshot.getValue(Result.class);
                                    result.setText((int)resultExam.getDegree()+" %");
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


    }
}
