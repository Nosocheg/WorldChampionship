package com.mabel.worldchampionship.ui.data;

import androidx.annotation.DrawableRes;

public class Match {

    private int id;
    private String teamFirst;
    private String teamSecond;
    private String group;
    private String score;
    private String datetime;
    @DrawableRes private int teamFirstFlag;
    @DrawableRes private int teamSecondFlag;

    public Match(int id, String teamFirst, String teamSecond, String group, String score, String datetime, int teamFirstFlag, int teamSecondFlag) {
        this.id = id;
        this.teamFirst = teamFirst;
        this.teamSecond = teamSecond;
        this.group = group;
        this.score = score;
        this.datetime = datetime;
        this.teamFirstFlag = teamFirstFlag;
        this.teamSecondFlag = teamSecondFlag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeamFirst() {
        return teamFirst;
    }

    public void setTeamFirst(String teamFirst) {
        this.teamFirst = teamFirst;
    }

    public String getTeamSecond() {
        return teamSecond;
    }

    public void setTeamSecond(String teamSecond) {
        this.teamSecond = teamSecond;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getTeamFirstFlag() {
        return teamFirstFlag;
    }

    public void setTeamFirstFlag(int teamFirstFlag) {
        this.teamFirstFlag = teamFirstFlag;
    }

    public int getTeamSecondFlag() {
        return teamSecondFlag;
    }

    public void setTeamSecondFlag(int teamSecondFlag) {
        this.teamSecondFlag = teamSecondFlag;
    }
}
