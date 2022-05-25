package com.mabel.worldchampionship.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {UserEntity.class, MatchEntity.class, TeamResultEntity.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract MatchDao matchDao();
    public abstract TeamResultDao teamResultDao();
}