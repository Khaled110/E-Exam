package com.example.onlineexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class MainAdminAddSubActivity extends AppCompatActivity implements SubjectDialog.subjectDialogListener {
    ImageView add;
    ArrayList<Subject> strings;
    SubjectAdapter subjectAdapter;
    ListView listView;
    Subject string;
    String levelName,levldi;
    String departName,prof;
    DatabaseReference dbref,dbl,dbd,dbs,dbp,dp2;
    FirebaseDatabase fbdb;
    DatabaseReference ref;
    ToastMaker toastMaker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin_add_sub);
        fbdb=FirebaseDatabase.getInstance();
        dbref =fbdb.getReference().child("Levels");
        dbs =fbdb.getReference();
        add=findViewById(R.id.add_subject);
        listView=findViewById(R.id.list_subject);
        ref = FirebaseDatabase.getInstance().getReference().child("Levels");

        toastMaker=new ToastMaker();
        Toast.makeText(MainAdminAddSubActivity.this,toastMaker.toastSubject(),Toast.LENGTH_SHORT).show();

        strings =new ArrayList<>();
        string =new Subject();


         levelName=getIntent().getStringExtra("levelSub");
         departName=getIntent().getStringExtra("departmentName");
         levldi=getIntent().getStringExtra("levelDi");
         prof=getIntent().getStringExtra("profName");


         if(levldi!=null && departName!=null){
             dbl = dbref.child(levldi).child("LevelName");
             dbd = dbref.child(levldi).child("Department").child(departName);
             dbl.setValue(levldi);
             dbd.child("DeptName").setValue(departName);
         }








        if(levelName!=null&&departName!=null ) {

            Query query6 = FirebaseDatabase.getInstance().getReference().child("Levels").child(levelName).child("Department")
                    .child(departName).child("Subjects");
            query6.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        // levels1.clear();
                        if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0 && dataSnapshot.getValue().toString().length() > 0) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Object h = snapshot.getValue(Object.class);
                                Map<String, Object> mdata = (Map<String, Object>) h;
                                Object data = mdata.get("SubjectName");
                                // Artist artist = snapshot.getValue(Artist.class);*/
                                // level3=new Level();
                                //  level3=snapshot.getValue(Level.class);
                                strings.add(new Subject((String) data));
                            }
                            subjectAdapter = new SubjectAdapter(MainAdminAddSubActivity.this, strings);
                            listView.setAdapter(subjectAdapter);
                            subjectAdapter.notifyDataSetChanged();
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    listView.smoothScrollToPosition(strings.size());
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





       /* Subject levelData []={"Select Level","mohamed","khaled"};
        final Subject depart_mo []={"20","amr diab"};
        final Subject dep_kh []={"21","asala"};*/

      //  ArrayAdapter<Subject> adapter1=new ArrayAdapter<Subject>(this,android.R.layout.simple_spinner_dropdown_item,levelData);
      //  level.setAdapter(adapter1);



        /*level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    depart.setEnabled(false);
                }
                if(position==1){
                    depart.setEnabled(true);
                    ArrayAdapter<Subject> adapter1=new ArrayAdapter<Subject>(MainAdminAddSubActivity.this,android.R.layout.simple_spinner_dropdown_item,depart_mo);
                    depart.setAdapter(adapter1);
                }else if(position==2){
                    depart.setEnabled(true);
                    ArrayAdapter<Subject> adapter1=new ArrayAdapter<Subject>(MainAdminAddSubActivity.this,android.R.layout.simple_spinner_dropdown_item,dep_kh);
                    depart.setAdapter(adapter1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent intent=new Intent(Mainad)
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSubDialog();
                Toast.makeText(MainAdminAddSubActivity.this,levelName+" ay klam "+departName,Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void openSubDialog(){
        SubjectDialog exampleDialog=new SubjectDialog();
        exampleDialog.show(getSupportFragmentManager(),"string dialog");
    }

    @Override
    public void applyTexts(String string, String prof) {
       // Subject subject=new Subject(string);
        strings.add(new Subject(string));
        if(levldi!=null){
            dbref.child(levldi).child("Department").child(departName).child("Subjects").child(string).child("SubjectName").setValue(string);
            dbref.child(levldi).child("Department").child(departName).child("Subjects").child(string).child("profName").setValue(prof);

        }else{
            dbref.child(levelName).child("Department").child(departName).child("Subjects").child(string).child("SubjectName").setValue(string);
            dbref.child(levelName).child("Department").child(departName).child("Subjects").child(string).child("profName").setValue(prof);
            dbref.child(levelName).child("Department").child(departName).child("DeptName").setValue(departName);
        }

        dbp = dbs.child("SubProf").child(prof);
        dbp.child("name").setValue(prof);
        dp2=dbp.child("Subjects").push();
        dp2.child("name").setValue(prof);
        dp2.child("SubName").setValue(string);

        subjectAdapter = new SubjectAdapter(MainAdminAddSubActivity.this, strings);
        listView.setAdapter(subjectAdapter);
        Toast.makeText(MainAdminAddSubActivity.this,prof+" ",Toast.LENGTH_SHORT).show();
    }

}
