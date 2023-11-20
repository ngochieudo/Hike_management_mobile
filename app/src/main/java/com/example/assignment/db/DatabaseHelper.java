package com.example.assignment.db;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.assignment.db.entity.Hike;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "hike_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Hike.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Hike.TABLE_NAME);

        onCreate(sqLiteDatabase);

    }
    public Hike getHike(long id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Hike.TABLE_NAME,
                new String[]{
                        Hike.COLUMN_ID,
                        Hike.COLUMN_NAME,
                        Hike.COLUMN_LOCATION,
                        Hike.COLUMN_DATE,
                        Hike.COLUMN_LENGTH,
                        Hike.COLUMN_PARKING,
                        Hike.COLUMN_DESCRIPTION,
                        Hike.COLUMN_GUIDE,
                        Hike.COLUMN_DIFFICULTY,
                        },
                Hike.COLUMN_ID + "=?",
                new String[]{
                        String.valueOf(id)
                },
                null,null,null,null
        );
        if(cursor != null) cursor.moveToFirst();
        Hike hike = new Hike(
                cursor.getInt(cursor.getColumnIndexOrThrow(Hike.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(Hike.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(Hike.COLUMN_LOCATION)),
                cursor.getString(cursor.getColumnIndexOrThrow(Hike.COLUMN_DATE)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(Hike.COLUMN_LENGTH)),
                cursor.getString(cursor.getColumnIndexOrThrow(Hike.COLUMN_PARKING)),
                cursor.getString(cursor.getColumnIndexOrThrow(Hike.COLUMN_DIFFICULTY)),
                cursor.getString(cursor.getColumnIndexOrThrow(Hike.COLUMN_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndexOrThrow(Hike.COLUMN_GUIDE))
        );
        cursor.close();
        return hike;
    }
    public ArrayList<Hike> getAllHikes(){
        ArrayList<Hike> hikes = new ArrayList<>();
        String selectQuery ="SELECT * FROM "+ Hike.TABLE_NAME+
                " ORDER BY "+ Hike.COLUMN_ID + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Hike hike = new Hike();
                hike.setName(cursor.getString(cursor.getColumnIndexOrThrow(Hike.COLUMN_NAME)));
                hike.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(Hike.COLUMN_LOCATION)));
                hike.setLength(cursor.getDouble(cursor.getColumnIndexOrThrow(Hike.COLUMN_LENGTH)));
                hike.setParking(cursor.getString(cursor.getColumnIndexOrThrow(Hike.COLUMN_PARKING)));
                hike.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(Hike.COLUMN_DESCRIPTION)));
                hike.setGuide(cursor.getString(cursor.getColumnIndexOrThrow(Hike.COLUMN_GUIDE)));

            } while ((cursor.moveToNext()));
        }
        db.close();

        return hikes;
    }
    public long insertHike(String name,
                           String location,
                           Double length,
                           String parking,
                           String desc,
                           String difficulty,
                           String guide
                            ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Hike.COLUMN_NAME, name);
        values.put(Hike.COLUMN_LOCATION, location);
        values.put(Hike.COLUMN_LENGTH, length);
        values.put(Hike.COLUMN_PARKING, parking);
        values.put(Hike.COLUMN_DIFFICULTY, difficulty);
        values.put(Hike.COLUMN_DESCRIPTION, desc);
        values.put(Hike.COLUMN_GUIDE, guide);
//        values.put(Hike.COLUMN_IMAGE, image);

        long id = db.insert(Hike.TABLE_NAME, null, values);
        db.close();
        return id;
    }
    public int updateHike(Hike hike){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Hike.COLUMN_NAME, hike.getName());
        values.put(Hike.COLUMN_LOCATION, hike.getLocation());
        values.put(Hike.COLUMN_DATE, hike.getDate());
        values.put(Hike.COLUMN_LENGTH, hike.getLength());
        values.put(Hike.COLUMN_PARKING, hike.getParking());
        values.put(Hike.COLUMN_DESCRIPTION, hike.getDesc());
        values.put(Hike.COLUMN_GUIDE, hike.getGuide());
//        values.put(Hike.COLUMN_IMAGE, hike.getImage());

        int rowsUpdated = db.update(Hike.TABLE_NAME, values, Hike.COLUMN_ID+ " =? ",
                new String[]{String.valueOf(hike.getId())}
        );
        db.close();
        return rowsUpdated;
    }
    public void deleteHike(Hike hike){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Hike.TABLE_NAME,Hike.COLUMN_ID+ " =? ",
                new String[]{String.valueOf(hike.getId())} );
        db.close();
    }

}

