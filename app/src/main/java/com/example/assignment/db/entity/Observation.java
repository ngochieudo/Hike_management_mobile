package com.example.assignment.db.entity;

public class Observation {
    public static final String TABLE_NAME = "observations";
    public static final String COLUMN_ID = "observation_id";
    public static final String COLUMN_TYPE = "observation_type";
    public static final String COLUMN_TIME = "observation_time";
    public static final String COLUMN_COMMENT = "observation_comment";

    private String type;
    private int id;
    private String time;
    private String comment;

    public Observation() {}

    public Observation(int id, String type, String time, String comment) {
        this.id = id;
        this.type = type;
        this.time = time;
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComments(String comment) {
        this.comment = comment;
    }

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TYPE + " TEXT,"
                    + COLUMN_TIME + " TEXT DATETIME DEFAULT CURRENT_TIMESTAMP,"
                    + COLUMN_COMMENT + "TEXT"
                    + ")";

}
