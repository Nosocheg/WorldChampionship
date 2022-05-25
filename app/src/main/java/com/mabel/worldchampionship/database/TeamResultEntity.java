package com.mabel.worldchampionship.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TeamResultEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String group;
    private int games;
    private int wins;
    private int draws;
    private int loses;
    private int goals;
    private int goalsMisses;
    private int score;

    public TeamResultEntity(String name, String group, int games, int wins, int draws, int loses, int goals, int goalsMisses, int score) {
        this.name = name;
        this.group = group;
        this.games = games;
        this.wins = wins;
        this.draws = draws;
        this.loses = loses;
        this.goals = goals;
        this.goalsMisses = goalsMisses;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getGoalsMisses() {
        return goalsMisses;
    }

    public void setGoalsMisses(int goalsMisses) {
        this.goalsMisses = goalsMisses;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
