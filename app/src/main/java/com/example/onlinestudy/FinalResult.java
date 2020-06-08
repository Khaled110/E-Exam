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

import java.util.ArrayList;

public class FinalResult extends AppCompatActivity {
    private TextView result,name,code,gpa,level,department,subject,degree,estimate;
    private ListView degrees;
    ArrayList<FinalResultStyle> fResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_result);
        result =findViewById(R.id.final_result_txt);
        name=findViewById(R.id.student_name_txt);
        code=findViewById(R.id.student_code_txt);
        gpa=findViewById(R.id.student_gpa_txt);
        level=findViewById(R.id.student_level_txt);
        department=findViewById(R.id.student_department_txt);
        degrees=findViewById(R.id.degree_list);
        subject=findViewById(R.id.subject_txt);
        degree=findViewById(R.id.degree_txt);
        estimate=findViewById(R.id.estimate_txt);

        Intent intent=getIntent();
        intent.getExtras().getString("stdEmail");
        intent.getExtras().getString("stdPassword");

        result.setText("Final Result");
        name.setText("Name : "+intent.getExtras().getString("stdName"));
        code.setText("Code : "+intent.getExtras().getString("stdCode"));
        gpa.setText("GPA : ");
        level.setText(intent.getExtras().getString("stdLevel"));
        department.setText("Department : "+intent.getExtras().getString("stdDepartment"));
        subject.setText("Subject");
        degree.setText("Deg");
        estimate.setText("Est");

        fResult =new ArrayList<FinalResultStyle>();
        fResult.add(new FinalResultStyle("Ethics","90","A+"));
        fResult.add(new FinalResultStyle("SE","70","C"));
        fResult.add(new FinalResultStyle("Data Base","80","B"));
        fResult.add(new FinalResultStyle("Visual Programming","64","D"));
        fResult.add(new FinalResultStyle("Mathmatics","89","A"));
        fResult.add(new FinalResultStyle("Computer Low","85","B+"));
        fResult.add(new FinalResultStyle("History","92","A+"));
        MyfinalResultAdapter myfinalResultAdapter =new MyfinalResultAdapter(fResult);
        degrees.setAdapter(myfinalResultAdapter);
    }
    class MyfinalResultAdapter extends BaseAdapter{
        ArrayList<FinalResultStyle>result=new ArrayList<FinalResultStyle>();
        MyfinalResultAdapter(ArrayList<FinalResultStyle>result){this.result=result;};
        @Override
        public int getCount() {
            return result.size();
        }

        @Override
        public Object getItem(int position) {
            return result.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater =getLayoutInflater();
            View view =inflater.inflate(R.layout.finalresultstyle,parent,false);
            TextView subcolumn=view.findViewById(R.id.subject_col);
            TextView degcolumn=view.findViewById(R.id.degree_col);
            TextView estcolumn=view.findViewById(R.id.estimate_col);
            subcolumn.setText(result.get(position).subjectColumn);
            degcolumn.setText(result.get(position).degreeColumn);
            estcolumn.setText(result.get(position).estimateColumn);
            return view;
        }
    }
}