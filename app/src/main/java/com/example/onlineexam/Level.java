package com.example.onlineexam;

public class Level implements ISubjectDetails {

    private String level;
    private int img;
    private String department;

    public Level(String level, String department) {
        this.level = level;
        this.department = department;
    }

    public Level() {
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public Level(String level, int img) {
        this.level = level;
        this.img = img;
    }

    public Level(String level) {
        this.level = level;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }


    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String show() {
        return "Levels";
    }
}
