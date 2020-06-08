package com.example.onlinestudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProfessorList extends AppCompatActivity {
    private TextView professorListTxt;
    private ListView professorListView;
    ArrayList<StyleProfessorList> profList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_list);
        professorListTxt=findViewById(R.id.professor_listtxt);
        professorListView=findViewById(R.id.Professor_listview);
        profList=new ArrayList<StyleProfessorList>();
        profList.add(new StyleProfessorList("Gouda"));
        ProfessorListAdapter professorListAdapter =new ProfessorListAdapter(profList);
        professorListView.setAdapter(professorListAdapter);
        professorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final TextView profName=view.findViewById(R.id.professor_name);
                Intent intent=new Intent(ProfessorList.this,ProfessorListSubject.class);
                intent.putExtra("profname",profName.getText().toString());
                startActivity(intent);
            }
        });
    }
    class ProfessorListAdapter extends BaseAdapter{
        ArrayList<StyleProfessorList> profList=new ArrayList<StyleProfessorList>();
        ProfessorListAdapter( ArrayList<StyleProfessorList> profList){
            this.profList=profList;
        }
        @Override
        public int getCount() {
            return profList.size();
        }

        @Override
        public Object getItem(int position) {
            return profList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater=getLayoutInflater();
            View view=inflater.inflate(R.layout.styleprofessorlist,parent,false);
            final TextView profName=view.findViewById(R.id.professor_name);
            profName.setText(profList.get(position).professorName);
           /* profName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(ProfessorList.this,ProfessorListSubject.class);
                    intent.putExtra("profname",profName.getText().toString());
                    startActivity(intent);
                }
            });*/
        return view;
        }
    }
}
