package com.mabel.worldchampionship.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MatchEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String teamFirst;
    private String teamSecond;
    private String group;
    private String score;
    private long timestamp;

    public MatchEntity(String teamFirst, String teamSecond, String group, String score, long timestamp) {
        this.teamFirst = teamFirst;
        this.teamSecond = teamSecond;
        this.group = group;
        this.score = score;
        this.timestamp = timestamp;
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
