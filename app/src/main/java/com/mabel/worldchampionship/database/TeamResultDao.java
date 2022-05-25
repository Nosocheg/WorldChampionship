package com.mabel.worldchampionship.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TeamResultDao {

    @Query("SELECT * FROM TeamResultEntity ORDER by score DESC")
    List<TeamResultEntity> getAll();

    @Query("SELECT * FROM TeamResultEntity WHERE id = :id")
    TeamResultEntity getById(int id);

    @Insert
    void insert(TeamResultEntity teamResultEntity);

    @Update
    void update(TeamResultEntity teamResultEntity);

    @Delete
    void delete(TeamResultEntity teamResultEntity);

    @Query("DELETE FROM TeamResultEntity WHERE id = :id")
    void deleteById(int id);
}
