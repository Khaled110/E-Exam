package com.example.onlineexam;

public class Department  implements ISubjectDetails{
    private String department;
    private Subject string;

    public Department(String department, Subject string) {
        this.department = department;
        this.string = string;
    }

    public Department() {
    }

    public Subject getString() {
        return string;
    }

    public void setString(Subject string) {
        this.string = string;
    }

    public Department(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String show() {
        return "Departments";
    }
}
