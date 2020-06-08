package com.example.onlineexam;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class SigninFragment extends Fragment {
    EditText email,password;
    Button signin;
    FirebaseAuth mAuth;
  FirebaseAuth.AuthStateListener mAuthListener;
  FirebaseDatabase firebaseDatabase;
  ProfUser userAdmin;
  ArrayList<ProfUser> profUsers;
  String level,department,name;
ProfUser user;
    String emailSign;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.signin_fragment,container,false);

        email= view.findViewById(R.id.email_signin);
        password=view.findViewById(R.id.password_signin);
        signin=view.findViewById(R.id.signin);
        firebaseDatabase=FirebaseDatabase.getInstance();
        profUsers=new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null){
            name=mAuth.getCurrentUser().getEmail();
        }
        emailSign =email.getText().toString();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()!= null){

                    //getType();
                    //startActivity(new Intent(getActivity(),MainAdminActivity.class));


                }
            }
        };

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignIn();
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
       mAuth.addAuthStateListener(mAuthListener);
    }

    private void startSignIn(){
        emailSign =email.getText().toString();

        String passwrdSign = password.getText().toString();

        if(TextUtils.isEmpty(emailSign)||TextUtils.isEmpty(passwrdSign)){

            Toast.makeText(this.getActivity(), " Insert ya 3am  ", Toast.LENGTH_LONG).show();

        }else {
            mAuth.signInWithEmailAndPassword(emailSign, passwrdSign).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {


                    if (!task.isSuccessful()) {

                        Toast.makeText(getActivity(), " Error ya gma3a ", Toast.LENGTH_LONG).show();

                    }else{

                   // getType();
                        startActivity(new Intent(getActivity(),MainAdminActivity.class));


                    }

                }
            });
        }
    }

/*    public void getType(){


                Query query =FirebaseDatabase.getInstance().getReference().child("users").child("Student").orderByChild("email").equalTo(email.getText().toString());


                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0 && dataSnapshot.getValue().toString().length() > 0) {

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    user = snapshot.getValue(ProfUser.class);
                                }
                                if (user != null) {
                                    if (user.getType().equals("Student")) {
                                        Intent intent = new Intent(getActivity(), MainStudentPage.class);
                                        Toast.makeText(getActivity(),user.getType()+"hello",Toast.LENGTH_SHORT).show();
                                        intent.putExtra("Student level", level);
                                        intent.putExtra("Student department", department);
                                        startActivity(intent);

                                    }

                                }

                            }
                        }else {

                                    Query query =FirebaseDatabase.getInstance().getReference().child("users").child("Admin").orderByChild("email").equalTo(email.getText().toString());


                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot != null) {
                                        if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0 && dataSnapshot.getValue().toString().length() > 0) {

                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                user = snapshot.getValue(ProfUser.class);
                                            }
                                            if (user != null) {
                                                if (user.getType().equals("Admin")) {
                                                    Intent intent = new Intent(getActivity(), MainAdminActivity.class);
                                                    intent.putExtra("Student level", level);
                                                    Toast.makeText(getActivity(),user.getType()+"hrllo",Toast.LENGTH_SHORT).show();

                                                    intent.putExtra("Student department", department);
                                                    startActivity(intent);

                                                }

                                            }

                                        }
                                    }else{

                                        Query query =FirebaseDatabase.getInstance().getReference().child("users").child("Professor").orderByChild("email").equalTo(email.getText().toString());


                                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot != null) {
                                                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0 && dataSnapshot.getValue().toString().length() > 0) {

                                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                            user = snapshot.getValue(ProfUser.class);
                                                        }
                                                        if (user != null) {
                                                            if (user.getType().equals("Professor")) {
                                                                Intent intent = new Intent(getActivity(), MainProfActivity.class);
                                                                intent.putExtra("Student level", level);
                                                                Toast.makeText(getActivity(),user.getType()+"hello3",Toast.LENGTH_SHORT).show();

                                                                intent.putExtra("Student department", department);
                                                                startActivity(intent);

                                                            }

                                                        }

                                                    }
                                                }}

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        }
                            }



                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

 */


    public void signout(){

        FirebaseAuth.getInstance().signOut();
        onDestroy();
        Intent intent=new Intent(getActivity(),MainActivity.class);
        startActivity(intent);
    }

}
