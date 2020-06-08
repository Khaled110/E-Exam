package com.example.onlineexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class QuestionBankProfActivity extends AppCompatActivity implements MCQDialog.McqListener , TrueFalseDialog.TruefalseListener,
        OpenQuestionDialog.OpenQuestionListener {
        ImageView imag;
        RecyclerView recyclerView;
        ArrayList<QuestionItemComponent> itemComponents;
        String subjecName;
        String chapterName,type,general,start;
        QuestionBankAdapter adapter;
    DatabaseReference ref,reference;
    QuestionItemComponent component;
    ExamStructure examStructure;
    ArrayList<QuestionItemComponent> ques,views;
    ArrayList<ExamStructure> examStructures;
    int randomInt,count,i;
    Button done;
    double count1,count2,total;
    TextView exam,examName,professor,professorName,time ,timeCount,timer;
    EditText text;
    static final long  START_TIME_IN_MILLS=60000;
    CountDownTimer countDownTimer;
    boolean timeRunning;
    long timeLeftInMills=START_TIME_IN_MILLS;
    String x,y;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_bank_prof);
        imag=findViewById(R.id.img_add_question);
        recyclerView=findViewById(R.id.recycler);
        exam=findViewById(R.id.exam);
        examName=findViewById(R.id.subject_name);
        professor=findViewById(R.id.professor);
        professorName=findViewById(R.id.name_prof);
        time=findViewById(R.id.time);
        text=findViewById(R.id.textView7);
        timeCount=findViewById(R.id.hours);
        itemComponents=new ArrayList<>();
        examStructures=new ArrayList<>();
        timer=findViewById(R.id.time_count);
        done=findViewById(R.id.done);
        ques=new ArrayList<>();
        views=new ArrayList<>();
        start=getIntent().getStringExtra("startexam");
        general=getIntent().getStringExtra("no");
        type=getIntent().getStringExtra("type");
        subjecName=getIntent().getStringExtra("name");
        chapterName=getIntent().getStringExtra("chapterName");
        Toast.makeText(QuestionBankProfActivity.this,subjecName +" " +chapterName,Toast.LENGTH_LONG).show();
        if (start != null) {
            if(type.equals("Student") && start.equals("yes")){
                imag.setVisibility(View.GONE);
                text.setEnabled(false);
                examName.setText(subjecName);
                getStudentData();

            }
        }

        else{
            ref = FirebaseDatabase.getInstance().getReference().child("Subjects").child(subjecName).child("Qbank").child(chapterName);
            reference=FirebaseDatabase.getInstance().getReference().child("openQ");

        }
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        imag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQuestionDialog();

            }
        });

        getRecyclerView(recyclerView,itemComponents);
        if(general!=null){
            if(general.equals("no"));{
                //imag.setVisibility(View.GONE);
                exam.setVisibility(View.GONE);
                examName.setVisibility(View.GONE);
                professor.setVisibility(View.GONE);
                professorName.setVisibility(View.GONE);
                timeCount.setVisibility(View.GONE);
                timer.setVisibility(View.GONE);
                time.setVisibility(View.GONE);
                text.setVisibility(View.GONE);
                done.setVisibility(View.GONE);
                viewData("open");
                viewData("MCQ");
                viewData("True");
            }
        }


      //  adapter = new QuestionBankAdapter(QuestionBankProfActivity.this,itemComponents);
       // adapter.notifyDataSetChanged();



    }


    private void openQuestionDialog(){
        BuildQuestionBank buildQuestionBank=new BuildQuestionBank();
        buildQuestionBank.show(getSupportFragmentManager() ,"Question Bank");

    }



    @Override
    public void applyMCQ(final String question, String option1, String option2, String option3, String option4, String category,
                         boolean bopOption1, boolean bolOption2, boolean bolOption3, boolean bolOption4,
                         String correctA, String correctB, String correctC, String correctD,String img,String vedio,String audio) {
        QuestionItemComponent  component=new QuestionItemComponent(question,option1,option2,option3,option4,null,null,null,null,
                null,img,null,category,correctA,correctB,correctC,correctD,null,null,vedio,audio,true,false,bopOption1,bolOption2,bolOption3,bolOption4,false,false);
        //itemComponents.add(component);
        getRecyclerView(recyclerView,itemComponents);

        ref.child("MCQ").child(question).setValue(component);
        itemComponents.clear();
        if(general!=null){
            if(general.equals("no")){
                //imag.setVisibility(View.GONE);
                exam.setVisibility(View.GONE);
                examName.setVisibility(View.GONE);
                professor.setVisibility(View.GONE);
                professorName.setVisibility(View.GONE);
                timeCount.setVisibility(View.GONE);
                done.setVisibility(View.GONE);
                timer.setVisibility(View.GONE);
                time.setVisibility(View.GONE);
                text.setVisibility(View.GONE);
                imag.setVisibility(View.GONE);
                viewData("open");
                viewData("MCQ");
                viewData("True");
            }
        }
        adapter.notifyDataSetChanged();

    }



    @Override
    public void applyTF(String question, String trueName, String falseName,String img,String video,String audio,String category,String correctA,String correctB,boolean t,boolean f) {
        QuestionItemComponent component=new QuestionItemComponent(question,null,null,null,null,trueName,falseName,null,null,
                null,img,null,category,correctA,correctB,null,null,null,null,audio,video,false,true,false,false,false,false,t,f);
        getRecyclerView(recyclerView,itemComponents);
        ref.child("True").child(question).setValue(component);
        itemComponents.clear();

        if(general!=null){
            if(general.equals("no"));{
              //  imag.setVisibility(View.GONE);
                exam.setVisibility(View.GONE);
                examName.setVisibility(View.GONE);
                professor.setVisibility(View.GONE);
                professorName.setVisibility(View.GONE);
                timeCount.setVisibility(View.GONE);
                timer.setVisibility(View.GONE);
                time.setVisibility(View.GONE);
                text.setVisibility(View.GONE);
                imag.setVisibility(View.GONE);
                viewData("open");
                viewData("MCQ");
                viewData("True");
            }
        }
        adapter.notifyDataSetChanged();



        //viewData("True");

    }
    @Override
    public void applyOpenQuestion(String question, String degree,String img,String vedio,String audio,String catrgory) {
        QuestionItemComponent component=new QuestionItemComponent(null,null,null,null,null,null,null,question,degree,
                null,img,null,catrgory,null,null,null,null,null,null,vedio,audio,false,false,
                false,false,false,false,false,false);
        getRecyclerView(recyclerView,itemComponents);
        ref.child("open").child(question).setValue(component);
       reference.child(subjecName).child(question).child("openQuestion").setValue(question);
        itemComponents.clear();
        if(general!=null){
            if(general.equals("no"));{
               // imag.setVisibility(View.GONE);
                exam.setVisibility(View.GONE);
                examName.setVisibility(View.GONE);
                professor.setVisibility(View.GONE);
                professorName.setVisibility(View.GONE);
                timer.setVisibility(View.GONE);
                timeCount.setVisibility(View.GONE);
                time.setVisibility(View.GONE);
                text.setVisibility(View.GONE);
                imag.setVisibility(View.GONE);
                viewData("open");
                viewData("MCQ");
                viewData("True");
            }
        }
        adapter.notifyDataSetChanged();
        // viewData("open");

    }
    private void getRecyclerView(RecyclerView recyclerView,ArrayList<QuestionItemComponent> itemComponents){
//         adapter = new QuestionBankAdapter(QuestionBankProfActivity.this,itemComponents);
        recyclerView.setLayoutManager(new LinearLayoutManager(QuestionBankProfActivity.this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL));

       // recyclerView.setAdapter(adapter);
    }


    private void viewData(final String type){
        final Query query6 = FirebaseDatabase.getInstance().getReference().child("Subjects").child(subjecName).child("Qbank").child(chapterName).
                child(type).orderByChild("question");
        query6.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0&&dataSnapshot.getValue().toString().length()>0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                             component=new QuestionItemComponent();
                             component.setTrueandFalse(false);
                             component.setMCQ(true);
                             component=snapshot.getValue(QuestionItemComponent.class);


                           itemComponents.add(component);

                        }
                        adapter = new QuestionBankAdapter(QuestionBankProfActivity.this,itemComponents,"no",subjecName);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.smoothScrollToPosition(itemComponents.size());
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
    private void getStudentData(){

        final Query query =FirebaseDatabase.getInstance().getReference().child("Subjects").child(subjecName).child("ExamStructure").orderByChild("type");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        examStructure=snapshot.getValue(ExamStructure.class);
                        examStructures.add(examStructure);
                        String chapterN=examStructure.getChapter();
                        final String typeN=examStructure.getType();
                        String categoryN=examStructure.getCategory();
                        final String numberN=examStructure.getNumber();
                        Query query6 = FirebaseDatabase.getInstance().getReference().child("Subjects").child(subjecName).child("Qbank").child(chapterN).
                                child(typeN).orderByChild("level").equalTo(categoryN);

                        query6.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot!=null){
                                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0&&dataSnapshot.getValue().toString().length()>0) {
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                            component=new QuestionItemComponent();
                                            component.setTrueandFalse(false);
                                            component.setMCQ(true);
                                            component=snapshot.getValue(QuestionItemComponent.class);


                                            itemComponents.add(component);
                                            count=itemComponents.size();
                                            Log.e("sizeFor",String.valueOf(itemComponents.size()));
                                            Log.e("num",String.valueOf(examStructure.getNumber()));
                                            Log.e("typeofQ",typeN);




                                        }
                                        if(ques.size()<Integer.parseInt(numberN)){
                                            for(i=0 ;i<Integer.parseInt(numberN);i++ ){
                                                int size=count;
                                                randomInt=(int)(size*Math.random());
                                             //  itemComponents.get(randomInt);
                                                try {
                                                    ques.add(itemComponents.get(randomInt));

                                                }catch (Exception e){
                                                    Toast.makeText(QuestionBankProfActivity.this,"Error",Toast.LENGTH_LONG).show();
                                                }
                                                views.addAll(ques);

                                                itemComponents.removeAll(ques);

                                             //   itemComponents.remove(randomInt);
                                                Log.e("random",String.valueOf(randomInt));
                                                Log.e("size",String.valueOf(count));
                                                Log.e("i",String.valueOf(i));
                                                ques.clear();

                                                adapter = new QuestionBankAdapter(QuestionBankProfActivity.this,itemComponents,start,subjecName);
                                                recyclerView.setAdapter(adapter);
                                                adapter.notifyDataSetChanged();
                                            }
                                        }





                                        new Handler().post(new Runnable() {
                                            @Override
                                            public void run() {
                                                recyclerView.smoothScrollToPosition(itemComponents.size());
                                            }
                                        });
                                    }


                                }else{
                                    Toast.makeText(QuestionBankProfActivity.this,examStructure.getChapter()+"error com ",Toast.LENGTH_LONG).show();


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                }else{
                    Toast.makeText(QuestionBankProfActivity.this,examStructure.getChapter()+"error exam ",Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onBackPressed() {
        if (start != null) {
            if (type.equals("Student") && start.equals("yes")) {
            }

        }
        else  if(general!=null) {
            if (general.equals("no")) {
                super.onBackPressed();

            }


            }
        }


    public void startTimer(){
        countDownTimer=new CountDownTimer(timeLeftInMills,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                    timeLeftInMills=millisUntilFinished;
                    updateCounTimeText();
            }

            @Override
            public void onFinish() {
                finish();
            timeRunning=false;
            }
        }.start();
        timeRunning=true;
    }
    public void updateCounTimeText(){
        int minutes=(int)(timeLeftInMills/1000)/60;
        int senonds=(int)(timeLeftInMills/1000)%60;
        String timeLeftFormated =String.format(Locale.getDefault(),"%02d:%02d",minutes,senonds);
        timer.setText(timeLeftFormated);
        //timeCount.setText(String.valueOf(minutes+1));


    }

    public void resetTimer(){
    timeLeftInMills=START_TIME_IN_MILLS;
    updateCounTimeText();
    }
    public void pauseTimer(){
        countDownTimer.cancel();
        timeRunning=false;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (start != null) {
            if(type.equals("Student") && start.equals("yes")){
                startTimer();
               // done.setVisibility(View.GONE);


            }
        }

        count1=0;
        count2=0;
        total=0;
        adapter = new QuestionBankAdapter(QuestionBankProfActivity.this,itemComponents,start,count1,count2,total);

    }
}
