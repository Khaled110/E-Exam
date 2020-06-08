package com.example.onlineexam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class QuestionBankChapterActivity extends AppCompatActivity {
        ListView listView;
    DatabaseReference ref;
    ArrayList<String> chapterValue;
    String subjectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_bank_chapter);
        listView=findViewById(R.id.list_q_chapter);
        subjectName=getIntent().getStringExtra("name");
        chapterValue=new ArrayList<>();

          Query query7 = FirebaseDatabase.getInstance().getReference().child("Subjects").child(subjectName).child("Tutorial").orderByChild("chapterName");
        query7.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0 && dataSnapshot.getValue().toString().length() > 0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Object h = snapshot.getValue(Object.class);
                            Map<String, Object> mdata = (Map<String, Object>) h;
                            Object data = mdata.get("chapterName");
                            chapterValue.add((String) data);
                        }
                        ArrayAdapter<String> chapterAdapter = new ArrayAdapter(QuestionBankChapterActivity.this,android.R.layout.simple_list_item_1,chapterValue){

                            @NonNull
                            @Override
                            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                View view=super.getView(position, convertView, parent);
                                TextView textView=view.findViewById(android.R.id.text1);
                                textView.setTextColor(view.getResources().getColor(R.color.colorAccent));
                                textView.setTextSize(20);

                                return view;

                            }
                        };
                        chapterAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                        listView.setAdapter(chapterAdapter);
                        chapterAdapter.notifyDataSetChanged();
                    }


                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(QuestionBankChapterActivity.this,QuestionBankProfActivity.class);
                String name=parent.getItemAtPosition(position).toString();
                intent.putExtra("chapterName",name);
                intent.putExtra("name",subjectName);
                intent.putExtra("type","Student");
                intent.putExtra("no","no");

                Toast.makeText(QuestionBankChapterActivity.this,subjectName +" " +parent.getItemAtPosition(position),Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });




    }
}
