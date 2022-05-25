package com.mabel.worldchampionship.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MatchDao {

    @Query("SELECT * FROM MatchEntity ORDER by timestamp ASC")
    List<MatchEntity> getAll();

    @Query("SELECT * FROM MatchEntity WHERE id = :id")
    MatchEntity getById(int id);

    @Insert
    void insert(MatchEntity matchEntity);

    @Update
    void update(MatchEntity matchEntity);

    @Delete
    void delete(MatchEntity matchEntity);

    @Query("DELETE FROM MatchEntity WHERE id = :id")
    void deleteById(int id);
}
