package com.blueradix.android.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Random;

public class DatabaseHelper extends SQLiteOpenHelper {

    //create database constants
    public static final String DATABASE_NAME = "monster2.db";
    public static final Integer DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "monster";

    //create sql statements
    private static final String CREATE_TABLE_ST = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, DESCRIPTION TEXT, SCARINESS INTEGER, IMAGE TEXT)";
    private static final String DROP_TABLE_ST = "DROP TABLE IF EXISTS " + TABLE_NAME ;
    private static final String GET_ALL_ST = "SELECT * FROM " + TABLE_NAME;

    //create constants for the table's column name
    private static final String COL_ID = "ID";
    private static final String COL_NAME = "NAME";
    private static final String COL_DESCRIPTION = "DESCRIPTION";
    private static final String COL_SCARINESS = "SCARINESS";
    private static final String COL_IMAGE = "IMAGE";

    /**
     * create the database every time this constructor gets called.
     * @param context
     */
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //this method gets executed every time getWritableDatabase or getReadableDatabase is called.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_ST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE_ST);
        onCreate(sqLiteDatabase);
    }

    public boolean insert(String name, String description, Integer scariness){
        //create an instance of SQLITE database
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_DESCRIPTION, description);
        contentValues.put(COL_SCARINESS, scariness);
        //we store the image name, after using
        //long resId = parent.getResources().getIdentifier(arrayOfStrings[position], "drawable", mApplicationContext.getPackageName());
        //you get the Id of the image as drawable, so you can use it in an image view
        //                int resId = getResources().getIdentifier("bomb", "drawable", this.getPackageName());
        //                imageView.setImageResource(resId);
        contentValues.put(COL_IMAGE, getRandomImageName());

        long result = db.insert(TABLE_NAME, null, contentValues);
        //if result -1  insert was not performed, otherwise will have the row ID of the newly inserted row
        return result == -1? false : true;
    }

    public Cursor getAll(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(GET_ALL_ST, null);
        return cursor;
    }

    public boolean update(Integer id, String name, String  description, Integer scariness){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID, id);
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_DESCRIPTION, description);
        contentValues.put(COL_SCARINESS, scariness);

        int numRowsUpdated = db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id.toString()});

        return numRowsUpdated == 1? true : false;
    }

    public boolean delete( Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        int numOfAffectedRows = db.delete(TABLE_NAME, "ID = ?", new String[]{id.toString()});
        return numOfAffectedRows == 1 ? true : false;
    }

    private String getRandomImageName(){
        Random ran = new Random();

        Integer value = ran.nextInt(30) + 1;
        return "ic_monster_" + value;
    }

}