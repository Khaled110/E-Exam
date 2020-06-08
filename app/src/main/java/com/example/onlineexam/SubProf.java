package com.example.onlineexam;

public class SubProf {

    private String name, SubName;

    public SubProf() {
    }

    public SubProf(String profName, String subName) {
        name= profName;
        SubName = subName;
    }

    public String getName() {
        return name;
    }

    public void setProfName(String profName) {
        name = profName;
    }

    public String getSubName() {
        return SubName;
    }

    public void setSubName(String subName) {
        SubName = subName;
    }
}
