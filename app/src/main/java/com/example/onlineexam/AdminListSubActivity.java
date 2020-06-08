package com.example.onlineexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class AdminListSubActivity extends AppCompatActivity {
        ListView listProf;
        String prof;
        ArrayList<SubProf> profUsers;
        SubjectProfAdapter profListAdapter;
        SubProf subProf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list_sub);
        listProf=findViewById(R.id.list_sub_prof);
        prof=getIntent().getStringExtra("Professor");
            profUsers=new ArrayList<>();
        Query query8 = FirebaseDatabase.getInstance().getReference().child("SubProf").child(prof).child("Subjects").orderByChild("name").equalTo(prof);
        query8.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profUsers.clear();
                if (dataSnapshot != null) {
                    // levels1.clear();
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0 && dataSnapshot.getValue().toString().length() > 0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Object h = snapshot.getValue(Object.class);
                            Map<String, Object> mdata = (Map<String, Object>) h;
                            // = mdata.get("ProfName");
                            // Artist artist = snapshot.getValue(Artist.class);*/
                            subProf=new SubProf();
                            subProf=snapshot.getValue(SubProf.class);
                            profUsers.add(subProf);
                        }

                        profListAdapter = new SubjectProfAdapter(AdminListSubActivity.this,profUsers);
                        listProf.setAdapter(profListAdapter);
                        profListAdapter.notifyDataSetChanged();
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                listProf.smoothScrollToPosition(profUsers.size());
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
}
