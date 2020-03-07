package com.example.daoyou;

public class User {
    String uname;
    String sex;
    int id;

    public User(String uname, String sex,int id) {
        this.uname = uname;
        this.sex = sex;
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
