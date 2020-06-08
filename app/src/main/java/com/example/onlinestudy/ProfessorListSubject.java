package com.example.onlinestudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProfessorListSubject extends AppCompatActivity {
    private TextView professorSubjectListTxt;
    private ListView professorSubjectListView;
    ArrayList<StyleProfessorSubjectList> profListSub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_list_subject);
        professorSubjectListTxt=findViewById(R.id.professor_subject_listtxt);
        professorSubjectListView=findViewById(R.id.Professor_subject_listview);
        Intent intent =this.getIntent();
        String getprofname=intent.getExtras().getString("profname");
        Toast.makeText(this,getprofname, Toast.LENGTH_LONG).show();
        professorSubjectListTxt.setText(getprofname+"'s Subjects");
        profListSub=new ArrayList<StyleProfessorSubjectList>();
        profListSub.add(new StyleProfessorSubjectList("SE"));
        profListSub.add(new StyleProfessorSubjectList("History"));
        ProfessorSubjectListAdapter professorSubjectListAdapter =new ProfessorSubjectListAdapter(profListSub);
        professorSubjectListView.setAdapter(professorSubjectListAdapter);
    }

        class ProfessorSubjectListAdapter extends BaseAdapter {
            ArrayList<StyleProfessorSubjectList> profListSub = new ArrayList<StyleProfessorSubjectList>();

            ProfessorSubjectListAdapter(ArrayList<StyleProfessorSubjectList> profListSub) {
                this.profListSub = profListSub;
            }

            @Override
            public int getCount() {
                return profListSub.size();
            }

            @Override
            public Object getItem(int position) {
                return profListSub.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.styleprofessorsubjectlist, parent, false);
                final TextView profsubject= view.findViewById(R.id.professor_subject);
                profsubject.setText(profListSub.get(position).professorSubject);
                return view;
            }
        }
    }


