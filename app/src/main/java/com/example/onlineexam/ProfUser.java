package com.example.onlineexam;

public class ProfUser {

    private String name,code,password,email,type,level,department;
     Subject subjectName;

    public ProfUser() {
    }

    public ProfUser(String name, String code, String password, String email, String type, String level, String department) {
        this.name = name;
        this.code = code;
        this.password = password;
        this.email = email;
        this.type = type;
        this.level = level;
        this.department = department;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Subject getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(Subject subjectsName) {
        this.subjectName = subjectsName;
    }

    public ProfUser(String name, String code, String password, String email, String type) {
        this.name = name;
        this.code = code;
        this.password = password;
        this.email = email;
        this.type = type;
    }

    public ProfUser(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
