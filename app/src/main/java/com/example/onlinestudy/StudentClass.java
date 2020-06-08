package com.example.onlinestudy;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class StudentClass extends Fragment {
    private EditText studentFullName ,studentPassword ,studentEmail, studentCode;
    private Spinner studentLevel ,studentDepartment;
    private Button studentSignUp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.student,container,false);
        studentFullName=view.findViewById(R.id.student_fullname);
        studentPassword=view.findViewById(R.id.student_password);
        studentEmail=view.findViewById(R.id.student_email);
        studentCode=view.findViewById(R.id.student_code);
        studentLevel=view.findViewById(R.id.student_level);
        studentDepartment=view.findViewById(R.id.student_department);
        studentSignUp=view.findViewById(R.id.student_signup);
        final List<String> spinnerArrayLevel =new ArrayList<String>();
        spinnerArrayLevel.add("Level 1");
        spinnerArrayLevel.add("Level 2");
        spinnerArrayLevel.add("Level 3");
        spinnerArrayLevel.add("Level 4");
        ArrayAdapter<String> adapterLevel =new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,spinnerArrayLevel);
        adapterLevel.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        studentLevel.setAdapter(adapterLevel);
        final  List<String> spinnerArrayDepartment =new ArrayList<String>();
        spinnerArrayDepartment.add("SE");
        spinnerArrayDepartment.add("IT");
        spinnerArrayDepartment.add("CS");
        spinnerArrayDepartment.add("IS");
        ArrayAdapter<String> adapterDepartment =new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,spinnerArrayDepartment);
        adapterDepartment.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        studentDepartment.setAdapter(adapterDepartment);
        final String departmentItem = studentDepartment.getSelectedItem().toString();
        final String levelItem = studentLevel.getSelectedItem().toString();
        studentSignUp.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainStudentPage.class);
                intent.putExtra("Student name",studentFullName.getText().toString());
                intent.putExtra("Student code",studentCode.getText().toString());
                intent.putExtra("Student email",studentEmail.getText().toString());
                intent.putExtra("Student password",studentPassword.getText().toString());
                intent.putExtra("Student level",levelItem);
                intent.putExtra("Student department",departmentItem);
                startActivity(intent);
            }
        });
        return view;
    }
}
