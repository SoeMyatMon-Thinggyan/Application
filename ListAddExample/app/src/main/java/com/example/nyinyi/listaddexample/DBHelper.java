package com.example.nyinyi.listaddexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by SMM on 10/10/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="EDMTDEV";
    private static final String DB_TABLE="Task";
    private static final String DB_COLUMN="TaskName";
    private static final int DB_VER=1;


    public DBHelper(Context context){
        super(context,DB_NAME,null,DB_VER);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query= String.format("create table %s (ID INTEGER PRIMARY KEY AUTO INCREMENT,%s TEXT not null",DB_TABLE,DB_COLUMN);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query=String.format("Delete TABLE IF EXISTS %s",DB_TABLE);
        onCreate(db);

    }

    public void insertNewTask(String task){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DB_COLUMN,task);
        db.insertWithOnConflict(DB_TABLE,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void deleteNewTask(String task){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(DB_TABLE,DB_COLUMN+ "- ?",new String[]{task});
        db.close();
    }

    public ArrayList<String> getTaskList(){
        ArrayList<String> taskList=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(DB_TABLE,new String[]{DB_COLUMN},null,null,null,null,null);
        cursor.close();
        db.close();
        return taskList;
    }
    
}
