package com.example.onlineexam;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.MediaRouteButton;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.LogRecord;

class MCQ extends RecyclerView.ViewHolder  {
    Boolean b;
    TextView question;
    CheckBox optionOne,optionTwo,optionThree,optionFour;
    ImageView imgQuestion;
    VideoView  vedioQuestion;
    TextView count1,count2,count3,count4;
    ImageView img,edit;
    EditText editOption1;
    Button audio;
    SeekBar seekBar;
     LinearLayout linearLayout;

    public  MCQ(@NonNull View itemView) {
        super(itemView);
        question=itemView.findViewById(R.id.text_question);
        optionOne=itemView.findViewById(R.id.ch_option1);
        optionTwo=itemView.findViewById(R.id.ch_option2);
        optionThree=itemView.findViewById(R.id.ch_option3);
        optionFour=itemView.findViewById(R.id.ch_option4);
        count1=itemView.findViewById(R.id.count1);
        count2=itemView.findViewById(R.id.count2);
        count3=itemView.findViewById(R.id.count3);
        count4=itemView.findViewById(R.id.count4);
        img=itemView.findViewById(R.id.img_remove_quetion);
        edit=itemView.findViewById(R.id.img_edit_quetion);
        editOption1=itemView.findViewById(R.id.edit_option_1);
        imgQuestion=itemView.findViewById(R.id.image_question);
        vedioQuestion=itemView.findViewById(R.id.vedio_question);
        audio=itemView.findViewById(R.id.audio);
        seekBar=itemView.findViewById(R.id.seek_mcq);
        linearLayout=itemView.findViewById(R.id.liner_mcq);


    }



}
class TrueFalse extends RecyclerView.ViewHolder {
    public MediaRouteButton img;
    TextView question;
    RadioButton trueQ,falseQ;
    ImageView imgQuestionT,edit,remove;
    VideoView  vedioQuestionT;
    Button audio;
    SeekBar seekBar;

    public TrueFalse(@NonNull View itemView) {
        super(itemView);
        question=itemView.findViewById(R.id.text_question_tf);
        trueQ=itemView.findViewById(R.id.radio_true_prof_q);
        falseQ=itemView.findViewById(R.id.radio_false_prof_q);
        imgQuestionT=itemView.findViewById(R.id.image_question_tf);
        vedioQuestionT=itemView.findViewById(R.id.vedio_question_tf);
        seekBar=itemView.findViewById(R.id.seek_tf);
        edit=itemView.findViewById(R.id.img_edit_quetion_tf);
        remove=itemView.findViewById(R.id.img_remove_quetion_tf);
    }
}
class OpenQuestion extends RecyclerView.ViewHolder {
    TextView question;
    EditText answer;
    ImageView imgQuestionT,edit,remove;
    VideoView  vedioQuestionT;
    Button audio;
    SeekBar seekBar;
    Button ok;

    public OpenQuestion(@NonNull View itemView) {
        super(itemView);
        question=itemView.findViewById(R.id.text_openquestion_p);
        answer=itemView.findViewById(R.id.answer_openquestion_prof);
        imgQuestionT=itemView.findViewById(R.id.image_question_open);
        vedioQuestionT=itemView.findViewById(R.id.vedio_question_open);
        audio=itemView.findViewById(R.id.audio_open);
        seekBar=itemView.findViewById(R.id.seek_open);
        edit=itemView.findViewById(R.id.img_edit_quetion_open);
        remove=itemView.findViewById(R.id.img_remove_quetion_open);
        ok=itemView.findViewById(R.id.open_ok);
    }
}

public class QuestionBankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MCQDialog.McqListener {
   Context context;
   ArrayList<QuestionItemComponent> itemComponents;
    AlertDialog dialog;
    ArrayList<ProfUser> arrayList;
    ProfUser user;
    double count1=0;
    double count2=0;
       double count3=0;
    MediaController mediaController;
    MediaPlayer mediaPlayer;
    Runnable runnable;
    android.os.Handler handler;
    FirebaseAuth auth;
    String name;
      double total=0.0;
      String type;
    FirebaseDatabase fbdb=FirebaseDatabase.getInstance();;
    DatabaseReference dbref=fbdb.getReference().child("result");
    DatabaseReference ref=fbdb.getReference();
EvaluateAnswer evaluateAnswer;
    Result result;
    String subjectName;




    public QuestionBankAdapter(Context context,ArrayList<QuestionItemComponent> itemComponents,String type,String subjectName) {
        this.context=context;
        this.itemComponents=itemComponents;
        this.type=type;
        this.subjectName=subjectName;
    }
    public QuestionBankAdapter(Context context,ArrayList<QuestionItemComponent> itemComponents,String type,double count1,double count2,double total) {
        this.context=context;
        this.itemComponents=itemComponents;
        this.type=type;
        this.count1=count1;
        this.count2=count2;
        this.total=total;
    }


    @Override
    public int getItemViewType(int position) {
        if(itemComponents.get(position).isMCQ()){
            return 0;
        }else if(itemComponents.get(position).isTrueandFalse()){
            return 1;
        }else {
            return 2;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)  {
        if(viewType==0){
            View view= LayoutInflater.from(context).inflate(R.layout.custom_mcq_profquestion,parent,false);

            return new MCQ(view);
        }else if(viewType==1) {
            View view= LayoutInflater.from(context).inflate(R.layout.custom_tf_profquestion,parent,false);
            return new TrueFalse(view);
        }else  {
            View view= LayoutInflater.from(context).inflate(R.layout.custom_openquestion_prof,parent,false);
            return new OpenQuestion(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position)  {

            switch (holder.getItemViewType()){

                case 0:{
                    final MCQ viewHolder= (MCQ) holder;
                    final QuestionItemComponent component=itemComponents.get(position);
                    viewHolder.question.setText(component.getQuestion());
                    viewHolder.optionOne.setText(component.getOption1());
                    viewHolder.optionTwo.setText(component.getOption2());
                    viewHolder.optionThree.setText(component.getOption3());
                    viewHolder.optionFour.setText(component.getOption4());



                    arrayList=new ArrayList<>();
                    auth = FirebaseAuth.getInstance();
                    if(auth.getCurrentUser()!=null){
                        name=auth.getCurrentUser().getEmail();
                    }

                    Query query7 = FirebaseDatabase.getInstance().getReference().child("users").child("Student").orderByChild("email").equalTo(name);
                    query7.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot != null) {
                                // levels1.clear();
                                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0 && dataSnapshot.getValue().toString().length() > 0) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        user = snapshot.getValue(ProfUser.class);

                                        arrayList.add(user);
                                    }

                                    if(user.getType().equals("Student")){
                                        viewHolder.edit.setVisibility(View.GONE);
                                        viewHolder.img.setVisibility(View.GONE);
                                        if(type.equals("no")){

                                            viewHolder.optionOne.setChecked(component.isOne());
                                            viewHolder.optionTwo.setChecked(component.isTwo());
                                            viewHolder.optionThree.setChecked(component.isThree());
                                            viewHolder.optionFour.setChecked(component.isFour());
                                            viewHolder.optionOne.setEnabled(false);
                                            viewHolder.optionTwo.setEnabled(false);
                                            viewHolder.optionThree.setEnabled(false);
                                            viewHolder.optionFour.setEnabled(false);


                                        }
                                        else  if(type.equals("yes")){
                                            if(component.getOp1()!=null){

                                                count2++;

                                                viewHolder.optionOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                    @Override
                                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                        if(viewHolder.optionOne.isChecked()==true){
                                                            if(viewHolder.optionOne.getText().toString().equals(component.getOp1())){
                                                                count1++;
                                                                total=Math.round((count1/count2)*100);

                                                                viewHolder.count1.setText(String.valueOf(total));

                                                                Log.e("total", String.valueOf(total));


                                                            }else if(viewHolder.optionOne.isChecked()==false){
                                                                if(viewHolder.optionOne.getText().toString().equals(component.getOp1())){
                                                                    if(count1>0){
                                                                        count1--;

                                                                        //   viewHolder.count1.setText(String.valueOf(total));
                                                                    }
                                                                }
                                                            }

                                                        }else if(viewHolder.optionOne.isChecked()==false){

                                                            if(count1>0){
                                                                count1--;

                                                                //   viewHolder.count1.setText(String.valueOf(total));

                                                            }
                                                        }
                                                        result=new Result(subjectName,total);
                                                        dbref.child(user.getName()).child("Subjects").child(subjectName).setValue(result);
                                                        dbref.child(user.getName()).child("Subjects").child("subjectName").setValue(subjectName);

                                                    }
                                                });
                                            }
                                            if(component.getOp2()!=null){

                                                count2++;

                                                viewHolder.optionTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                    @Override
                                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                        if(viewHolder.optionTwo.isChecked()==true){
                                                            if(viewHolder.optionTwo.getText().toString().equals(component.getOp2())){
                                                                count1++;
                                                                total=Math.round((count1/count2)*100);

                                                            }else if(viewHolder.optionTwo.isChecked()==false){
                                                                if(viewHolder.optionTwo.getText().toString().equals(component.getOp2())){
                                                                    if(count1>0){
                                                                        count1--;
                                                                        //   viewHolder.count1.setText(String.valueOf(total));
                                                                    }
                                                                }
                                                            }
                                                        }else if(viewHolder.optionTwo.isChecked()==false){

                                                            if(count1>0){
                                                                count1--;

                                                                //   viewHolder.count1.setText(String.valueOf(total));

                                                            }
                                                        }
                                                        result=new Result(subjectName,total);
                                                        dbref.child(user.getName()).child("Subjects").child(subjectName).setValue(result);
                                                        dbref.child(user.getName()).child("Subjects").child("subjectName").setValue(subjectName);

                                                    }
                                                });

                                            }
                                            if(component.getOp3()!=null){
                                                count2++;
                                                viewHolder.optionThree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                    @Override
                                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                        if(viewHolder.optionThree.isChecked()==true){
                                                            if(viewHolder.optionThree.getText().toString().equals(component.getOp3())){
                                                                count1++;
                                                                count3=count1/count2;
                                                                total=Math.round((count1/count2)*100);

                                                            }else if(viewHolder.optionThree.isChecked()==false){
                                                                if(viewHolder.optionThree.getText().toString().equals(component.getOp3())){
                                                                    if(count1>0){
                                                                        count1--;

                                                                        //   viewHolder.count1.setText(String.valueOf(total));
                                                                    }
                                                                }
                                                            }
                                                        }else if(viewHolder.optionThree.isChecked()==false){

                                                            if(count1>0){
                                                                count1--;

                                                                //   viewHolder.count1.setText(String.valueOf(total));

                                                            }
                                                        }
                                                        result=new Result(subjectName,total);
                                                        dbref.child(user.getName()).child("Subjects").child(subjectName).setValue(result);
                                                        dbref.child(user.getName()).child("Subjects").child("subjectName").setValue(subjectName);

                                                    }
                                                });
                                            }
                                            if(component.getOp4()!=null){

                                                count2++;

                                                viewHolder.optionFour.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                    @Override
                                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                        if(viewHolder.optionFour.isChecked()==true){
                                                            if(viewHolder.optionFour.getText().toString().equals(component.getOp4())){
                                                                count1++;
                                                                total=Math.round((count1/count2)*100);

                                                            }else if(viewHolder.optionFour.isChecked()==false){
                                                                if(viewHolder.optionFour.getText().toString().equals(component.getOp4())){
                                                                    if(count1>0){
                                                                        count1--;

                                                                        //   viewHolder.count1.setText(String.valueOf(total));
                                                                    }
                                                                }
                                                            }
                                                        }else if(viewHolder.optionFour.isChecked()==false){

                                                            if(count1 >0){
                                                                count1--;

                                                                //   viewHolder.count1.setText(String.valueOf(total));

                                                            }
                                                        }
                                                        result=new Result(subjectName,total);
                                                        dbref.child(user.getName()).child("Subjects").child(subjectName).setValue(result);
                                                        dbref.child(user.getName()).child("Subjects").child("subjectName").setValue(subjectName);

                                                    }

                                                });
                                            }
                                            total=Math.round((count1/count2)*100);
                                            result=new Result(subjectName,total);
                                            dbref.child(user.getName()).child("Subjects").child(subjectName).setValue(result);
                                            dbref.child(user.getName()).child("Subjects").child("subjectName").setValue(subjectName);


                                        }

                                    }

                                }


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    if(component.getGallary()!=null){
                        viewHolder.imgQuestion.setVisibility(View.VISIBLE);
                        Picasso.get().load(component.getGallary()).into(viewHolder.imgQuestion);
                    }
                    if(component.getVedio()!=null){
                        viewHolder.vedioQuestion.setVisibility(View.VISIBLE);
                        mediaController=new MediaController(context);
                        viewHolder.vedioQuestion.setMediaController(mediaController);
                        mediaController.setAnchorView(viewHolder.vedioQuestion);
                        viewHolder.vedioQuestion.setVideoPath(component.getVedio());
                        viewHolder.vedioQuestion.start();
                    }
                    if(component.getAudio()!=null){
                        viewHolder.linearLayout.setVisibility(View.VISIBLE);
                        viewHolder.audio.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(mediaPlayer.isPlaying()){
                                    mediaPlayer.pause();
                                    mediaPlayer.reset();
                                    viewHolder.audio.setText("||");
                                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                        @Override
                                        public void onPrepared(MediaPlayer mp) {
                                            viewHolder.seekBar.setMax(mediaPlayer.getDuration());
                                            mediaPlayer.start();
                                            viewHolder.seekBar.setProgress(mediaPlayer.getCurrentPosition());
                                            if(mediaPlayer.isPlaying()){


                                                runnable=new Runnable() {
                                                    @Override
                                                    public void run() {

                                                    }
                                                };
                                                handler.postDelayed(runnable,1000);
                                            }
                                        }
                                    });
                                }
                                else {
                                    mediaPlayer.start();
                                    viewHolder.audio.setText(">");

                                }



                                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                try {
                                    mediaPlayer.setDataSource(component.getAudio());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    mediaPlayer.prepare();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                mediaPlayer=new MediaPlayer();
                                handler=new Handler();

                                viewHolder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                    @Override
                                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                        if(fromUser){
                                            mediaPlayer.seekTo(progress);
                                        }
                                    }

                                    @Override
                                    public void onStartTrackingTouch(SeekBar seekBar) {

                                    }

                                    @Override
                                    public void onStopTrackingTouch(SeekBar seekBar) {

                                    }
                                });

                            }
                        });


                    }

                   // Toast.makeText(context,total+" ",Toast.LENGTH_LONG).show();

                    viewHolder.img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder=new AlertDialog.Builder(context);
                            builder.setTitle("Warning!")
                                    .setMessage("Are you sure you want to delete this question ?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            itemComponents.remove(position);
                                            notifyDataSetChanged();
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialog.dismiss();
                                        }
                                    });
                            dialog=builder.create();
                            dialog.show();
                        }
                    });
                    viewHolder.edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                          MCQDialog mcqDialog=new MCQDialog();
                          mcqDialog.show(manager,"MCQ Dialog");

                            viewHolder.optionOne.setEnabled(true);
                            viewHolder.optionTwo.setEnabled(true);
                            viewHolder.optionThree.setEnabled(true);
                            viewHolder.optionFour.setEnabled(true);
                        }
                    });
                    viewHolder.edit.setEnabled(false);

                }
                break;
                case 1: {
                    final TrueFalse viewHolder = (TrueFalse) holder;
                    final QuestionItemComponent component = itemComponents.get(position);
                    viewHolder.question.setText(component.getQuestion());
                    viewHolder.trueQ.setText(component.getTrueItem());
                    viewHolder.falseQ.setText(component.getFalseItem());
                    arrayList=new ArrayList<>();
                    auth = FirebaseAuth.getInstance();
                    if (auth.getCurrentUser() != null) {
                        name = auth.getCurrentUser().getEmail();
                    }
                    Query query6 = FirebaseDatabase.getInstance().getReference().child("users").child("Student").orderByChild("email").equalTo(name);
                    query6.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot != null) {
                                // levels1.clear();
                                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0 && dataSnapshot.getValue().toString().length() > 0) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        user = snapshot.getValue(ProfUser.class);

                                        arrayList.add(user);
                                    }

                                    if(type.equals("no")){

                                        viewHolder.trueQ.setChecked(component.isT());
                                        viewHolder.falseQ.setChecked(component.isF());
                                        viewHolder.trueQ.setEnabled(false);
                                        viewHolder.falseQ.setEnabled(false);


                                    }
                                   else if(user.getType().equals("Student")){
                                        viewHolder.remove.setVisibility(View.GONE);
                                        viewHolder.edit.setVisibility(View.GONE);
                                        if(type.equals("yes")){
                                            if (component.getOp1() != null) {
                                                count2++;
                                                viewHolder.trueQ.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                    @Override
                                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                        if (viewHolder.trueQ.isChecked() == true) {
                                                            if (viewHolder.trueQ.getText().toString().equals(component.getTrueItem())) {
                                                                count1++;
                                                                total = Math.round((count1 / count2) * 100);
                                                            } else if (viewHolder.trueQ.isChecked() == false) {
                                                                if (viewHolder.trueQ.getText().toString().equals(component.getTrueItem())) {
                                                                    if (count1 > 0) {
                                                                        count1--;
                                                                        //   viewHolder.count1.setText(String.valueOf(total));
                                                                    }
                                                                }
                                                            }
                                                        } else if (viewHolder.trueQ.isChecked() == false) {

                                                            if (count1 > 0) {
                                                                count1--;
                                                                //   viewHolder.count1.setText(String.valueOf(total));

                                                            }
                                                        }
                                                        result=new Result(subjectName,total);
                                                        dbref.child(user.getName()).child("Subjects").child(subjectName).setValue(result);
                                                        dbref.child(user.getName()).child("Subjects").child("subjectName").setValue(subjectName);

                                                    }

                                                });

                                            }
                                            if (component.getOp2() != null) {

                                                count2++;

                                                viewHolder.falseQ.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                    @Override
                                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                        if (viewHolder.falseQ.isChecked() == true) {
                                                            if (viewHolder.falseQ.getText().toString().equals(component.getFalseItem())) {
                                                                count1++;
                                                                total = Math.round((count1 / count2) * 100);
                                                            } else if (viewHolder.falseQ.isChecked() == false) {
                                                                if (viewHolder.falseQ.getText().toString().equals(component.getFalseItem())) {
                                                                    if (count1 > 0) {
                                                                        count1--;
                                                                        //   viewHolder.count1.setText(String.valueOf(total));
                                                                    }
                                                                }
                                                            }
                                                        } else if (viewHolder.falseQ.isChecked() == false) {

                                                            if (count1 > 0) {
                                                                count1--;
                                                                //   viewHolder.count1.setText(String.valueOf(total));

                                                            }
                                                        }
                                                        result = new Result(subjectName, total);
                                                        dbref.child(user.getName()).child("Subjects").child(subjectName).setValue(result);
                                                        dbref.child(user.getName()).child("Subjects").child("subjectName").setValue(subjectName);

                                                    }

                                                });
                                            }
                                            total=Math.round((count1/count2)*100);

                                        }

                                    }

                                }


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    if (component.getGallary() != null && !component.getGallary().isEmpty()) {
                        viewHolder.imgQuestionT.setVisibility(View.VISIBLE);
                        Picasso.get().load(component.getGallary()).into(viewHolder.imgQuestionT);
                    }
                    if (component.getVedio() != null) {
                        viewHolder.vedioQuestionT.setVisibility(View.VISIBLE);
                        mediaController = new MediaController(context);
                        viewHolder.vedioQuestionT.setMediaController(mediaController);
                        mediaController.setAnchorView(viewHolder.vedioQuestionT);
                        viewHolder.vedioQuestionT.setVideoPath(component.getVedio());
                        viewHolder.vedioQuestionT.start();
                    }


                }
                break;
                case 2:{
                    final OpenQuestion viewHolder= (OpenQuestion) holder;
                    final QuestionItemComponent component=itemComponents.get(position);
                    viewHolder.question.setText(component.getOpenQuestion());
                    arrayList=new ArrayList<>();
                    auth = FirebaseAuth.getInstance();
                    if(auth.getCurrentUser()!=null){
                        name=auth.getCurrentUser().getEmail();
                    }
                    Query query7 = FirebaseDatabase.getInstance().getReference().child("users").child("Student").orderByChild("email").equalTo(name);
                    query7.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot != null) {
                                // levels1.clear();
                                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0 && dataSnapshot.getValue().toString().length() > 0) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        user = snapshot.getValue(ProfUser.class);

                                        arrayList.add(user);
                                    }
                                    if(user.getType().equals("Student")){
                                        viewHolder.edit.setVisibility(View.GONE);
                                        viewHolder.remove.setVisibility(View.GONE);
                                        if(type.equals("yes")){
                                            viewHolder.answer.setVisibility(View.VISIBLE);
                                            viewHolder.ok.setVisibility(View.VISIBLE);
                                            viewHolder.ok.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    if(viewHolder.answer.getText().toString().length()>0){
                                                        evaluateAnswer=new EvaluateAnswer(component.getDegreeOpenQuestion(),viewHolder.answer.getText().toString(),component.getOpenQuestion());
                                                        ref.child("open").child(subjectName).child(component.getOpenQuestion()
                                                        ).child(user.getName()).setValue(evaluateAnswer);



                                                    }else {
                                                        ref.child("open").child(subjectName).child(component.getOpenQuestion())
                                                                .child(user.getName()).child("answer").setValue("empty");
                                                        ref.child("open").child(subjectName).child(component.getOpenQuestion()
                                                        ).child(user.getName()).child("degree").setValue(component.getDegreeOpenQuestion());
                                                        ref.child("open").child(subjectName).child(component.getOpenQuestion())
                                                                .child(user.getName()).child("question").setValue(component.getOpenQuestion());



                                                    }

                                                }
                                            });

                                        }




                                    }

                                }


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                    //viewHolder.answer.setText();
                    if(component.getGallary()!=null && !component.getGallary().isEmpty()){
                        viewHolder.imgQuestionT.setVisibility(View.VISIBLE);
                        Picasso.get().load(component.getGallary()).into(viewHolder.imgQuestionT);
                    }
                    if(component.getVedio()!=null ){
                        viewHolder.vedioQuestionT.setVisibility(View.VISIBLE);
                        mediaController=new MediaController(context);
                        viewHolder.vedioQuestionT.setMediaController(mediaController);
                        mediaController.setAnchorView(viewHolder.vedioQuestionT);
                        viewHolder.vedioQuestionT.setVideoPath(component.getVedio());
                        viewHolder.vedioQuestionT.start();
                    }
                }
                break;
                default:
                    break;
            }
    }


    @Override
    public int getItemCount() {
        return itemComponents.size();
    }




    @Override
    public void applyMCQ(String question, String option1, String option2, String option3, String option4, String level, boolean bopOption1, boolean bolOption2, boolean bolOption3, boolean bolOption4, String correctA, String correctB, String correctC, String correctD, String img,String vedio,String audio) {
    }



}

