package com.example.onlinestudy;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfessorClass extends Fragment {
    private EditText professorFullName,professorPassword ,professorEmail, professorId,professorPhone;
    private RadioButton professor,admin;
    private Button professorSignUp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.professor,container,false);
        professorFullName=view.findViewById(R.id. prof_fullname);
        professorPassword=view.findViewById(R.id.prof_password);
        professorEmail=view.findViewById(R.id.prof_email);
        professorId=view.findViewById(R.id.prof_id);
        professorPhone=view.findViewById(R.id.prof_phone);
        professor=view.findViewById(R.id.prof_professor);
        admin=view.findViewById(R.id.prof_admin);
        professorSignUp=view.findViewById(R.id.prof_signup);
        professorSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (professor.isChecked()) {
                    Intent intent = new Intent(getContext(),MainProfessorPage.class);
                    startActivity(intent);
                }else if (admin.isChecked()){
                    Intent intent = new Intent(getContext(), MainAdminPage.class);
                    startActivity(intent);
                }
            }
        });
        return view;
    }
}
