package com.example.onlineexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ToturialChaptersActivity extends AppCompatActivity implements ChapterDialog.chapterDialogListener {
      ImageView img;
      ListView listView;
      ArrayList<Chapter> chapters;
      ChapterAdapter chapterAdapter;
    DatabaseReference fbdb;
    String subjectName,type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toturial_chapters);
        img=findViewById(R.id.img_toturial);
        listView=findViewById(R.id.list_toturial);
        chapters=new ArrayList<>();
        type=getIntent().getStringExtra("type");
        subjectName=getIntent().getStringExtra("name");
        fbdb = FirebaseDatabase.getInstance().getReference().child("Subjects").child(subjectName).child("Tutorial");
        if(type!=null){
            if(type.equals("Student")){
                img.setVisibility(View.GONE);
            }
        }


        Query query6 = FirebaseDatabase.getInstance().getReference().child("Subjects").child(subjectName).child("Tutorial").orderByChild("chapterName");
        query6.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0&&dataSnapshot.getValue().toString().length()>0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Object h = snapshot.getValue(Object.class);
                            HashMap<String,Object> mdata = (HashMap<String, Object>) h;
                            Object data =  mdata.get("chapterName");
                            chapters.add(new Chapter((String) data));
                        }
                        chapterAdapter = new ChapterAdapter(ToturialChaptersActivity.this,chapters);
                        listView.setAdapter(chapterAdapter);
                        chapterAdapter.notifyDataSetChanged();
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                listView.smoothScrollToPosition(chapters.size());
                            }
                        });
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChapterDialog();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Chapter chapter= chapters.get(position);
                if(type!=null){
                    if(type.equals("Student")) {
                        Intent intent = new Intent(ToturialChaptersActivity.this, StudentTutorialActivity.class);
                        startActivity(intent);
                        }
                    }

                else{
                    Intent intent=new Intent(ToturialChaptersActivity.this,ProfChapterPDFActivity.class);
                    intent.putExtra("chapter",chapter.getName());
                    intent.putExtra("name",subjectName);
                    startActivity(intent);
                }

                chapterAdapter.notifyDataSetChanged();
            }
        });
    }
    public void openChapterDialog(){
        ChapterDialog chapterDialog=new ChapterDialog();
        chapterDialog.show(getSupportFragmentManager(),"chapter_dialog");
    }


    @Override
    public void applyChapterText(String chapter) {
        chapters.add(new Chapter(chapter));
        chapterAdapter=new ChapterAdapter(ToturialChaptersActivity.this,chapters);
        listView.setAdapter(chapterAdapter);
        fbdb.child(chapter).child("chapterName").setValue(chapter);




    }
}
