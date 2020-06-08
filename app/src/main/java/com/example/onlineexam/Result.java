package com.example.onlineexam;

public class Result {
    String subjectName;
    double degree;

    public Result(String subjectName, double degree) {
        this.subjectName = subjectName;
        this.degree = degree;
    }

    public Result() {
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public double getDegree() {
        return degree;
    }

    public void setDegree(double degree) {
        this.degree = degree;
    }
}
