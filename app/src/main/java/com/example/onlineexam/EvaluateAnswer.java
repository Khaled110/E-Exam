package com.example.onlineexam;

public class EvaluateAnswer {

    String degree;
    String answer;
    String question;

    public EvaluateAnswer() {
    }


    public EvaluateAnswer(String degree, String answer, String question) {
        this.degree = degree;
        this.answer = answer;
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
