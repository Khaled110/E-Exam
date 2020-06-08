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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EvaluateQListActivity extends AppCompatActivity {
    ListView listView;
    QuestionItemComponent component;
   ArrayList<QuestionItemComponent> items;
   String subjectName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_q_list);
        listView=findViewById(R.id.list_evaluate);
        items=new ArrayList<>();
        subjectName=getIntent().getStringExtra("name");

        viewQuestion();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name=parent.getItemAtPosition(position).toString();
                Intent intent=new Intent(EvaluateQListActivity.this,EvaluateAnswerActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("subjectName",subjectName);
                startActivity(intent);
            }
        });

    }
    private void viewQuestion(){
        Query query =  FirebaseDatabase.getInstance().getReference().child("openQ").child(subjectName).orderByChild("openQuestion");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    component=snapshot.getValue(QuestionItemComponent.class);
                    items.add(component);
                }
                String [] values=new String[items.size()];

                for(int i=0;i<values.length;i++){
                    values[i]=items.get(i).getOpenQuestion();
                }
                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(EvaluateQListActivity.this,android.R.layout.simple_list_item_1,values){
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view=super.getView(position, convertView, parent);
                        TextView textView=view.findViewById(android.R.id.text1);
                        //   textView.setText(pdf.getName());
                        textView.setTextColor(getResources().getColor(R.color.colorAccent));
                        textView.setTextSize(20);

                        return view;
                    }
                };

                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
