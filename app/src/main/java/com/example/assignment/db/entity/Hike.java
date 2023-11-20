package com.example.assignment.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity(tableName = "hikes")
public class Hike implements Serializable {
    public static final String TABLE_NAME = "hikes";
    public static final String COLUMN_ID = "hike_id";
    public static final String COLUMN_NAME = "hike_name";
    public static final String COLUMN_LOCATION = "hike_location";
    public static final String COLUMN_DATE = "hike_date";
    public static final String COLUMN_LENGTH = "hike_length";
    public static final String COLUMN_DIFFICULTY = "hike_difficulty";
    public static final String COLUMN_PARKING = "hike_parking";
    public static final String COLUMN_DESCRIPTION = "hike_description";
    public static final String COLUMN_GUIDE = "hike_guide";
    public static final String COLUMN_IMAGE = "hike_image";
    public static final String COLUMN_OBSERVATION = "hike_observation";
//    public static final String COLUMN_IMAGE = "hike_image";
@PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String location;
    private String date;
    private Double length;
    private String parking;
    @ColumnInfo(name = "description")
    private String desc;
    private String guide;
    private String difficulty;

//    private Observation[] observations;


    public Hike(){}
    public Hike(int id,
                String name,
                String location,
                String date,
                Double length,
                String parking,
                String difficulty,
                String desc,
                String guide
//                Observation[] observations
//                byte[] image
                ) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.length = length;
        this.parking = parking;
        this.difficulty = difficulty;
        this.desc = desc;
        this.guide = guide;
//        this.observations = observations;
//        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
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
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public Double getLength() {
        return length;
    }
    public void setLength(Double length) { this.length = length;}
    public String getParking() {
        return parking;
    }
    public void setParking(String parking) {this.parking = parking; }

    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getGuide() {
        return guide;
    }
    public void setGuide(String guide) {
        this.guide = guide;
    }

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_LOCATION + " TEXT,"
                    + COLUMN_DATE + " TEXT DEFAULT (datetime('now')),"
                    + COLUMN_LENGTH + " TEXT,"
                    + COLUMN_DIFFICULTY + " TEXT, "
                    + COLUMN_PARKING + " TEXT, "
                    + COLUMN_DESCRIPTION + " TEXT DEFAULT \"\","
                    + COLUMN_GUIDE + " TEXT TEXT DEFAULT \"\","
                    + ")";

}
