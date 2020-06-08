package com.example.onlineexam;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MainAdminProfListActivity extends AppCompatActivity {
    ListView listProf;
     ArrayList<SubProf> profUsers;
     AdminProfListAdapter profListAdapter;
     DatabaseReference fbdb;
    SubProf userAdmin,userProf,sub;
    ArrayList<Subject> arrayList;
    Object data;
    String profName;
    Object adminName, h;
    String x;
    HashSet<SubProf> set;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin_prof_list);
        listProf = findViewById(R.id.list_admin_prof);
        fbdb = FirebaseDatabase.getInstance().getReference().child("Levels");
        profUsers = new ArrayList<>();
        set = new HashSet<>();


        Query query8 = FirebaseDatabase.getInstance().getReference().child("users").child("Admin").orderByChild("name");
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
                            adminName = mdata.get("name");
                            // Artist artist = snapshot.getValue(Artist.class);*/
                            userAdmin = new SubProf();
                            userAdmin = snapshot.getValue(SubProf.class);
                            set.add(userAdmin);
                        }


                        profUsers.clear();
                        profUsers.addAll(set);

                        profListAdapter = new AdminProfListAdapter(MainAdminProfListActivity.this, profUsers);
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
        Query query9 = FirebaseDatabase.getInstance().getReference().child("users").child("Professor").orderByChild("name");
        query9.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profUsers.clear();
                if (dataSnapshot != null) {
                    // levels1.clear();
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0 && dataSnapshot.getValue().toString().length() > 0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Object h = snapshot.getValue(Object.class);
                            Map<String, Object> mdata = (Map<String, Object>) h;
                            adminName = mdata.get("name");
                            // Artist artist = snapshot.getValue(Artist.class);*/
                            userAdmin = new SubProf();
                            userAdmin = snapshot.getValue(SubProf.class);
                            set.add(userAdmin);
                        }


                        profUsers.clear();
                        profUsers.addAll(set);

                        profListAdapter = new AdminProfListAdapter(MainAdminProfListActivity.this, profUsers);
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


        listProf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainAdminProfListActivity.this, AdminListSubActivity.class);
                SubProf subProf = profUsers.get(position);
                intent.putExtra("Professor", subProf.getName());
                startActivity(intent);
            }
        });

    }
}
