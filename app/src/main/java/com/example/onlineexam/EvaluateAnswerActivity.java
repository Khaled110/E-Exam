package com.example.onlineexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EvaluateAnswerActivity extends AppCompatActivity {
    ListView listView;
ArrayList<EvaluateAnswer> itemComponents;
EvaluateAnswerAdapter answerAdapter;
EvaluateAnswer evaluateAnswer;
String questionName,subjectName,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_answer);
        listView=findViewById(R.id.list_answer);
        itemComponents=new ArrayList<>();

        questionName=getIntent().getStringExtra("name");
        subjectName=getIntent().getStringExtra("subjectName");

                getData();



    }

    public void getData(){
        final Query query6 =  FirebaseDatabase.getInstance().getReference().child("open").child(subjectName).
                child(questionName).orderByChild("question").equalTo(questionName);
        Toast.makeText(EvaluateAnswerActivity.this,questionName+" "+subjectName,Toast.LENGTH_LONG).show();

        query6.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0&&dataSnapshot.getValue().toString().length()>0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Toast.makeText(EvaluateAnswerActivity.this," hello "+subjectName,Toast.LENGTH_LONG).show();

                            evaluateAnswer=snapshot.getValue(EvaluateAnswer.class);
                            itemComponents.add(evaluateAnswer);
                            Toast.makeText(EvaluateAnswerActivity.this,evaluateAnswer.getDegree()+" "+evaluateAnswer.getAnswer(),Toast.LENGTH_LONG).show();

                        }
                        Toast.makeText(EvaluateAnswerActivity.this,evaluateAnswer.getDegree()+" "+evaluateAnswer.getAnswer(),Toast.LENGTH_LONG).show();

                        answerAdapter=new EvaluateAnswerAdapter(EvaluateAnswerActivity.this,itemComponents);
                        listView.setAdapter(answerAdapter);
                        answerAdapter.notifyDataSetChanged();
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                listView.smoothScrollToPosition(itemComponents.size());
                            }
                        });
                    }else{
                        Toast.makeText(EvaluateAnswerActivity.this," no",Toast.LENGTH_LONG).show();

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


      /*  Query query = FirebaseDatabase.getInstance().getReference().child("open").child(subjectName).
                child(questionName).orderByChild("question").equalTo(questionName);
        Toast.makeText(EvaluateAnswerActivity.this,questionName+" "+subjectName,Toast.LENGTH_LONG).show();

        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    evaluateAnswer=snapshot.getValue(EvaluateAnswer.class);
                    itemComponents.add(evaluateAnswer);
                    Toast.makeText(EvaluateAnswerActivity.this,evaluateAnswer.getDegree()+" "+evaluateAnswer.getAnswer(),Toast.LENGTH_LONG).show();
                }

                answerAdapter=new EvaluateAnswerAdapter(EvaluateAnswerActivity.this,itemComponents);
                listView.setAdapter(answerAdapter);
                answerAdapter.notifyDataSetChanged();
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        listView.smoothScrollToPosition(itemComponents.size());
                    }
                });



        }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


       */





    }


}
