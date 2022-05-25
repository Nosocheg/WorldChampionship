package com.mabel.worldchampionship.ui.data;

import androidx.annotation.DrawableRes;

// Класс описывает результат команды в турнирной сетке
public class TeamResult implements TournamentItem {

    private int id;
    private String name;
    @DrawableRes
    private int teamFlag;
    private int games;
    private int wins;
    private int draws;
    private int loses;
    private String goals;
    private int score;

    public TeamResult(int id, String name, int teamFlag, int games, int wins, int draws, int loses, String goals, int score) {
        this.id = id;
        this.name = name;
        this.teamFlag = teamFlag;
        this.games = games;
        this.wins = wins;
        this.draws = draws;
        this.loses = loses;
        this.goals = goals;
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

    public int getTeamFlag() {
        return teamFlag;
    }

    public void setTeamFlag(int teamFlag) {
        this.teamFlag = teamFlag;
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

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
