package com.example.onlineexam;

public class ToastMaker {
    private Department department;
    private Subject subject;
    private Level level;

    public ToastMaker() {
        department=new Department();
        subject=new Subject();
        level=new Level();
    }
    public String toastLevel(){
            return level.show();
    }
    public String toastDepartment(){
        return department.show();
    }
    public String toastSubject(){
        return subject.show();
    }

}
