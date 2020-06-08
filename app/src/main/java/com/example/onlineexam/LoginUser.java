package com.example.onlineexam;

public class LoginUser {
    //required parameters
    private String name,code,password,email,type,level,department;

    public String getLevel() {
        return level;
    }

    public String getDepartment() {
        return department;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getType() {
        return type;
    }

    private LoginUser(LoginBuilder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
        this.code = builder.code;
        this.type=builder.type;
        this.level=builder.level;
        this.department=builder.department;

    }

    //Builder Class
    public static class LoginBuilder {

        //required parameters
        private String name;
        private String email;
        private String password;
        private String type;
        private String code;
        private String level;
        private String department;


        public LoginBuilder(String name, String code, String password,String email,String type,String level,String department) {
            this.name = name;
            this.email = email;
            this.password = password;
            this.code=code;
            this.type=type;
            this.level=level;
            this.department=department;
        }



        public LoginUser build() {
            return new LoginUser(this);
        }

    }

}
