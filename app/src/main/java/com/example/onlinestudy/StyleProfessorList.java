package com.example.onlinestudy;

public class StyleProfessorList {
    String professorName;
    StyleProfessorList(String professorName){this.professorName=professorName;}
    public String getProfessorName(){
        return professorName;
    }
    public void getProfessorName(String ProfessorName) {
        this.professorName = ProfessorName;
    }

}

