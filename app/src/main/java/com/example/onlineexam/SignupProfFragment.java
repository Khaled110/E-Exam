package com.example.onlineexam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.widget.Toast.LENGTH_SHORT;

public class SignupProfFragment extends Fragment {
    RadioButton professor,admin;
    EditText name,email,password,phone;
    Button signup_prof;
    FirebaseAuth mAuth;
    String typeReg,emailReg, passwordReg,nameReg,codeReg;
    FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mdatabase;
    private FirebaseDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.signup_prof_fragment,container,false);
            professor=view.findViewById(R.id.radio_prof);
            admin=view.findViewById(R.id.radio_admin);
            name=view.findViewById(R.id.name_prof);
            email=view.findViewById(R.id.email_prof);
            password=view.findViewById(R.id.password_prof);
            phone=view.findViewById(R.id.phone_prof);
            signup_prof=view.findViewById(R.id.prof_signup);
             mAuth = FirebaseAuth.getInstance();

             database=FirebaseDatabase.getInstance();
             mdatabase =database.getReference("users");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!= null){
                    if(admin.isChecked()){
                        Intent intent=new Intent(getContext(),MainAdminActivity.class);
                        startActivity(intent);

                    }
                    else if(professor.isChecked()){
                        Intent intent=new Intent(getContext(),MainProfActivity.class);
                        startActivity(intent);

                    }

                }

            }
        };

        signup_prof.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkEmail();

                }
            });



        return view;
    }
    private void registerUser(){

       String emailReg = email.getText().toString();
      String passwordReg = password.getText().toString();

       if(emailReg.isEmpty())
        {
            email.setError("Email is Required");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailReg).matches())
        {
            email.setError("Email is not valid");
            email.requestFocus();
            return;
        }
        if(passwordReg.isEmpty())
        {
            password.setError("Password is Required");
            password.requestFocus();
            return;
        }
        if(passwordReg.length()<6)
        {
            password.setError("Minimum Length is 6");
            password.requestFocus();
            return;
        }
        if(!admin.isChecked() && !professor.isChecked()){
            Toast.makeText(getActivity(),"You must select type", LENGTH_SHORT).show();
            return;
        }



        mAuth.createUserWithEmailAndPassword(emailReg,passwordReg).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(getActivity(), " Success ya gma3a ", Toast.LENGTH_LONG).show();

                }

            }
        });
    }
    public void checkEmail(){
        String emailReg = email.getText().toString();
        String passwordReg = password.getText().toString();

        if(emailReg.isEmpty())
        {
            email.setError("Email is Required");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailReg).matches())
        {
            email.setError("Email is not valid");
            email.requestFocus();
            return;
        }
        if(passwordReg.isEmpty())
        {
            password.setError("Password is Required");
            password.requestFocus();
            return;
        }
        if(passwordReg.length()<6)
        {
            password.setError("Minimum Length is 6");
            password.requestFocus();
            return;
        }

        mAuth.fetchSignInMethodsForEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                boolean check=!task.getResult().getSignInMethods().isEmpty();
                if(!check){
                    registerUser();
                    realtimeRegister();

                }else {
                    Toast.makeText(getActivity(),"Already exist", LENGTH_SHORT).show();
                }
            }
        });

    }


    public void realtimeRegister(){
         emailReg = email.getText().toString();
          passwordReg = password.getText().toString();
         nameReg = name.getText().toString();
        codeReg = phone.getText().toString();
        if(professor.isChecked()){
             typeReg=professor.getText().toString();

        }else if(admin.isChecked()){
             typeReg=admin.getText().toString();

        }

        final ProfUser user=new ProfUser(nameReg,codeReg,passwordReg,emailReg,typeReg);

       mdatabase.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            mdatabase.child(typeReg).child(codeReg).setValue(user);
            Toast.makeText(getActivity(),"data inserted",LENGTH_SHORT).show();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    //firebasedatabase.keepsynced(true);
}
