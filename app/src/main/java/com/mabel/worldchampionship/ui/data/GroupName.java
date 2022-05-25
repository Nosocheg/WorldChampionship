package com.mabel.worldchampionship.ui.data;

// Класс для отображения группы над результатами команд
public class GroupName implements TournamentItem{

    private String name;

    public GroupName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
