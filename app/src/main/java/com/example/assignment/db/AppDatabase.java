package com.example.assignment.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.assignment.db.Dao.HikeDao;
import com.example.assignment.db.entity.Hike;

@Database(entities = {Hike.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HikeDao hikeDao();
}

