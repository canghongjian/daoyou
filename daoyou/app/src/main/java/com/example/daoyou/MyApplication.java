package com.example.daoyou;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    public static User current_user;
    public static List<Team> teams = new ArrayList<>();

    //八个种类队伍的存储
    public static List<Team> teams1 = new ArrayList<>();
    public static List<Team> teams2 = new ArrayList<>();
    public static List<Team> teams3 = new ArrayList<>();
    public static List<Team> teams4 = new ArrayList<>();
    public static List<Team> teams5 = new ArrayList<>();
    public static List<Team> teams6 = new ArrayList<>();
    public static List<Team> teams7 = new ArrayList<>();
    public static List<Team> teams8 = new ArrayList<>();
    //当前登录用户拥有或加入队伍的存储
    public static List<Team> teams_belong = new ArrayList<>();
    public static List<Team> teams_join = new ArrayList<>();
    public void setCurrent_user(User user)
    {
        this.current_user = user;
    }

    public static User getCurrent_user()
    {
        return current_user;
    }

    public static List<Team> getTeams() {
        return teams;
    }

    public static void setTeams(List<Team> teams) {
        MyApplication.teams = teams;
    }

    public static List<Team> getTeams1() {
        return teams1;
    }

    public static void setTeams1(List<Team> teams1) {
        MyApplication.teams1 = teams1;
    }

    public static List<Team> getTeams2() {
        return teams2;
    }

    public static void setTeams2(List<Team> teams2) {
        MyApplication.teams2 = teams2;
    }

    public static List<Team> getTeams3() {
        return teams3;
    }

    public static void setTeams3(List<Team> teams3) {
        MyApplication.teams3 = teams3;
    }

    public static List<Team> getTeams4() {
        return teams4;
    }

    public static void setTeams4(List<Team> teams4) {
        MyApplication.teams4 = teams4;
    }

    public static List<Team> getTeams5() {
        return teams5;
    }

    public static void setTeams5(List<Team> teams5) {
        MyApplication.teams5 = teams5;
    }

    public static List<Team> getTeams6() {
        return teams6;
    }

    public static void setTeams6(List<Team> teams6) {
        MyApplication.teams6 = teams6;
    }

    public static List<Team> getTeams7() {
        return teams7;
    }

    public static void setTeams7(List<Team> teams7) {
        MyApplication.teams7 = teams7;
    }

    public static List<Team> getTeams8() {
        return teams8;
    }

    public static void setTeams8(List<Team> teams8) {
        MyApplication.teams8 = teams8;
    }

    public static List<Team> getTeams_belong() {
        return teams_belong;
    }

    public static void setTeams_belong(List<Team> teams_belong) {
        MyApplication.teams_belong = teams_belong;
    }

    public static List<Team> getTeams_join() {
        return teams_join;
    }

    public static void setTeams_join(List<Team> teams_join) {
        MyApplication.teams_join = teams_join;
    }
}
