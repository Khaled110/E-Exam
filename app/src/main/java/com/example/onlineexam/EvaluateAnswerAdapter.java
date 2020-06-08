package com.example.onlineexam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class EvaluateAnswerAdapter extends ArrayAdapter<EvaluateAnswer> {

    TextView question,answer,degree;
    Button save;
    ImageView top,down;
    int i =0;

    public EvaluateAnswerAdapter(@NonNull Context context, @NonNull List<EvaluateAnswer> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       convertView= LayoutInflater.from(getContext()).inflate(R.layout.custom_row_evalute_answer,parent,false);
       question=convertView.findViewById(R.id.txt_stu_open);
       answer=convertView.findViewById(R.id.txt_stu_answer);
       degree=convertView.findViewById(R.id.degree_open_question);
       save=convertView.findViewById(R.id.save_answer);
       top=convertView.findViewById(R.id.top_evaluate);
       down=convertView.findViewById(R.id.down_evaluate);



        final EvaluateAnswer component=getItem(position);
        question.setText(component.getQuestion());
       answer.setText(component.getAnswer());
        degree.setText(String.valueOf(i));
       top.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                if(i<= Integer.parseInt(component.getDegree())){
                    i++;
                    degree.setText(String.valueOf(i));
                    down.setEnabled(true);

                }


           }
       });
       down.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(i<=0){
                   down.setEnabled(false);
               }else {
                   i--;
               }
               degree.setText(String.valueOf(i));



           }
       });

        return convertView;
    }
}
