package com.mabel.worldchampionship.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM UserEntity")
    List<UserEntity> getAll();

    @Query("SELECT * FROM UserEntity WHERE id = :id")
    UserEntity getById(int id);

    @Query("SELECT * FROM UserEntity WHERE isLoggedIn = 1")
    UserEntity getLoggedInUser();

    @Query("SELECT * FROM UserEntity WHERE login = :login AND password = :password")
    UserEntity getByCredential(String login, String password);

    @Insert
    void insert(UserEntity userEntity);

    @Update
    void update(UserEntity userEntity);

    @Delete
    void delete(UserEntity userEntity);

    @Query("DELETE FROM UserEntity WHERE id = :id")
    void deleteById(int id);
}
