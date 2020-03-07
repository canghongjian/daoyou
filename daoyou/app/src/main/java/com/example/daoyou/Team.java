package com.example.daoyou;

import java.util.List;

public class Team {
    private int id;
    private String team_type;
    private int captain_id;
    private int team_maxNumber;
    private int team_currentNumber;
    private List<Integer> members_id;
    private String team_startTime;
    private String team_endTime;
    private String team_info;
    private String team_detail;
    // 还有一个队伍聊天的类
    // private

    public Team(int id, String team_type,int captain_id,int team_maxNumber,int team_currentNumber,
                List<Integer> members_id,String team_startTime,String team_endTime) {
        this.id = id;
        this.team_type = team_type;
        this.captain_id = captain_id;
        this.team_maxNumber = team_maxNumber;
        this.team_currentNumber = team_currentNumber;
        this.members_id = members_id;
        this.team_startTime = team_startTime;
        this.team_endTime = team_endTime;
    }

    public Team(int id,String team_type, int team_maxNumber, int team_currentNumber, String team_startTime, String team_info,String team_detail,int captain_id) {
        this.id = id;
        this.team_type = team_type;
        this.team_maxNumber = team_maxNumber;
        this.team_currentNumber = team_currentNumber;
        this.team_startTime = team_startTime;
        this.team_info = team_info;
        this.team_detail = team_detail;
        this.captain_id = captain_id;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeam_type() {
        return team_type;
    }

    public void setTeam_type(String team_type) {
        this.team_type = team_type;
    }

    public int getCaptain_id() {
        return captain_id;
    }

    public void setCaptain_id(int captain_id) {
        this.captain_id = captain_id;
    }

    public int getTeam_maxNumber() {
        return team_maxNumber;
    }

    public void setTeam_maxNumber(int team_maxNumber) {
        this.team_maxNumber = team_maxNumber;
    }

    public int getTeam_currentNumber() {
        return team_currentNumber;
    }

    public void setTeam_currentNumber(int team_currentNumber) {
        this.team_currentNumber = team_currentNumber;
    }

    public List<Integer> getMembers_id() {
        return members_id;
    }

    public void setMembers_id(List<Integer> members_id) {
        this.members_id = members_id;
    }

    public String getTeam_startTime() {
        return team_startTime;
    }

    public void setTeam_startTime(String team_startTime) {
        this.team_startTime = team_startTime;
    }

    public String getTeam_endTime() {
        return team_endTime;
    }

    public void setTeam_endTime(String team_endTime) {
        this.team_endTime = team_endTime;
    }

    public String getTeam_info() {
        return team_info;
    }

    public void setTeam_info(String team_info) {
        this.team_info = team_info;
    }

    public String getTeam_detail() {
        return team_detail;
    }

    public void setTeam_detail(String team_detail) {
        this.team_detail = team_detail;
    }
}
