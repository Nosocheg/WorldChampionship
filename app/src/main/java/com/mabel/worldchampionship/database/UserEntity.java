package com.mabel.worldchampionship.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String login;
    private String password;
    private String userName;
    private boolean isAdmin;
    private boolean isLoggedIn;

    public UserEntity() {

    }

    public UserEntity(String login, String password, String userName, boolean isAdmin) {
        this.login = login;
        this.password = password;
        this.userName = userName;
        this.isAdmin = isAdmin;
    }

    public UserEntity(String login, String password, String userName, boolean isAdmin, boolean isLoggedIn) {
        this.login = login;
        this.password = password;
        this.userName = userName;
        this.isAdmin = isAdmin;
        this.isLoggedIn = isLoggedIn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }
}
