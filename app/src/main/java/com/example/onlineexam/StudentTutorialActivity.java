package com.example.onlineexam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class StudentTutorialActivity extends AppCompatActivity {
    TextView chapter;
    ListView listView;
    ArrayList<Chapter> uploadPDFS;
    Chapter pdf;
    String subjectName, chapterName;
    DatabaseReference databaseReference;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_tutorial);
        chapter=findViewById(R.id.text_name_chapter_stu);
        listView=findViewById(R.id.list_chapter_stu);

        uploadPDFS=new ArrayList<>();
        subjectName=getIntent().getStringExtra("");
        chapterName=getIntent().getStringExtra("");
        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Subjects").child("data").child("Tutorial").child("uploads");
        viewFiles();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Chapter uploadPDF=uploadPDFS.get(position);
                Intent intent=new Intent();
                intent.setType(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(uploadPDF.getUrl()));

                startActivity(intent);

            }
        });


    }
    private void viewFiles(){
        Query query =FirebaseDatabase.getInstance().getReference().child("Subjects").child("data").child("Tutorial").child("chapter one").child("uploads");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    pdf=snapshot.getValue(Chapter.class);
                    uploadPDFS.add(pdf);
                }
                String [] values=new String[uploadPDFS.size()];

                for(int i=0;i<values.length;i++){
                    values[i]=uploadPDFS.get(i).getName();
                }
                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(StudentTutorialActivity.this,android.R.layout.simple_list_item_1,values){
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

                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
