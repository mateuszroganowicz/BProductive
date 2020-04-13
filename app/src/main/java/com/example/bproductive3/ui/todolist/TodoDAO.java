package com.example.bproductive3.ui.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TodoDAO extends SQLiteOpenHelper
{
    private static final String DB_NAME="TodoDB";
    private static final int DB_VER = 1;
    private static final String DB_TABLE = "Todo";
    private static final String DB_COLUMN = "Task";

    public TodoDAO(@Nullable Context context)
    {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL);", DB_TABLE, DB_COLUMN);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        String query = String.format("DELETE TABLE IF EXISTS %s", DB_TABLE);
        db.execSQL(query);
        onCreate(db);
    }

    public void insertTodo(String task)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(DB_COLUMN, task);
        db.insertWithOnConflict(DB_TABLE, null, val, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void deleteTodo(String task)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE, DB_COLUMN + "== ?", new String[]{task});
        db.close();
    }

    public ArrayList<String> getTasks()
    {
        ArrayList<String> tasks = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[] {DB_COLUMN}, null, null, null, null, null);

        while(cursor.moveToNext())
        {
            int index = cursor.getColumnIndex(DB_COLUMN);
            tasks.add(cursor.getString(index));
        }
        cursor.close();
        db.close();

        return tasks;
    }
}