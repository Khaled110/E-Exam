package com.example.onlineexam;

public class ExamStructure {
    String number,type,category,chapter;

    public ExamStructure() {
    }

    public ExamStructure(String number, String type, String category, String chapter) {
        this.number = number;
        this.type = type;
        this.category = category;
        this.chapter = chapter;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }
}
