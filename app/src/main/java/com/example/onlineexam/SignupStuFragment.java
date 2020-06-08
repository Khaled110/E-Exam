package com.example.onlineexam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;

public class SignupStuFragment extends Fragment {
    EditText email,password,code,name;
    Button signup;
    Spinner  spinner , spinner1;
    private DatabaseReference mdatabase;
    private FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    ArrayList<String> levels,deparments;
    String emailReg, passwordReg,nameReg,codeReg,typeReg,level,department;



    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.signup_stu_fragment,container,false);
        name=view.findViewById(R.id.name);
        email=view.findViewById(R.id.email);
        password=view.findViewById(R.id.password);
        code=view.findViewById(R.id.code);
        signup=view.findViewById(R.id.signin);
        spinner = (Spinner) view.findViewById(R.id.spin_level);
        spinner1 = (Spinner) view.findViewById(R.id.spin_depart);


        levels=new ArrayList<>();
        deparments=new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        mdatabase =database.getReference("users");
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!= null){

                        Intent intent=new Intent(getContext(),MainStudentPage.class);
                        startActivity(intent);

                }

            }
        };




        Query query6 = FirebaseDatabase.getInstance().getReference().child("Levels").orderByChild("LevelName");
        query6.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                levels.clear();
                if (dataSnapshot != null) {
                    // levels1.clear();
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0 && dataSnapshot.getValue().toString().length() > 0) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Object h = snapshot.getValue(Object.class);
                            Map<String, Object> mdata = (Map<String, Object>) h;
                            Object data = mdata.get("LevelName");

                            levels.add((String) data);
                        }


                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,levels);
                        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        spinner.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                level=parent.getItemAtPosition(position).toString();
                Query query7 = FirebaseDatabase.getInstance().getReference().child("Levels").child(level).child("Department");
                query7.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        deparments.clear();
                        if (dataSnapshot != null) {
                            if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0 && dataSnapshot.getValue().toString().length() > 0) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Object h = snapshot.getValue(Object.class);
                                    Map<String, Object> mdata = (Map<String, Object>) h;
                                    Object data = mdata.get("DeptName");

                                    deparments.add((String) data);
                                }


                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,deparments);
                                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                                spinner1.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                            }


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });



        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                department=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(),MainStudentPage.class);
                intent.putExtra("studentName",name.getText().toString());
                intent.putExtra("Student code",code.getText().toString());
                intent.putExtra("Student email",email.getText().toString());
                intent.putExtra("Student password",password.getText().toString());
                intent.putExtra("Student level",level);
                intent.putExtra("Student department",department);
                startActivity(intent);
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


    public void realtimeRegister() {
        emailReg = email.getText().toString();
        passwordReg = password.getText().toString();
        nameReg = name.getText().toString();
        codeReg = code.getText().toString();
        LoginUser loginUser=new LoginUser.LoginBuilder(nameReg,codeReg,passwordReg,emailReg,"Student",level,department).build();


        final ProfUser user=new ProfUser(loginUser.getName(),loginUser.getCode(),loginUser.getPassword(),
                loginUser.getEmail(),"Student",loginUser.getLevel(),loginUser.getDepartment());

        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mdatabase.child("Student").child(codeReg).setValue(user);
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

}
