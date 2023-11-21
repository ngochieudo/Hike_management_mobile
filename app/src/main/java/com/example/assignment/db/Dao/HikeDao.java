package com.example.assignment.db.Dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.assignment.db.entity.Hike;

import java.util.List;
@Dao
public interface HikeDao {
    @Query("SELECT * FROM hikes")
    List<Hike> getAll();

    // create a new hike
    @Query("INSERT INTO hikes (name, location, date, length, parking, difficulty, description, guide) VALUES (:name, :location, :date, :length, :parking, :difficulty, :desc, :guide)")
    void insertHike(String name, String location, String date, Double length, String parking, String difficulty, String desc, String guide);

    @Query("SELECT * FROM hikes WHERE id = :id")
    Hike getHike(int id);

    //update a hike
    @Query("UPDATE hikes SET name = :name, location = :location, date = :date, length = :length, parking = :parking, difficulty = :difficulty, description = :desc, guide = :guide WHERE id = :id")
    int updateHike(int id, String name, String location, String date, Double length, String parking, String difficulty, String desc, String guide);

    @Query("SELECT * FROM hikes WHERE name LIKE :name")
    List<Hike> findHikeByName(String name);
}
