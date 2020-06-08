package com.example.onlineexam;

public class Subject implements ISubjectDetails {
    public Subject() {
    }

    private String SubjectName;


    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String SubjectsName) {
        this.SubjectName = SubjectsName;
    }

    public Subject(String SubjectsName) {
        this.SubjectName = SubjectsName;
    }

    @Override
    public String show() {
        return "Subjects";
    }
}
