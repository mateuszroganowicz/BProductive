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
    private static final String DB_TABLE = "TODO";
    private static final String DB_COLUMN_TASKNAME = "TASK_NAME";
    private static final String DB_COLUMN_PRIORITY = "PRIORITY";
    private static final String DB_COLUMN_ID= "ID";

    public TodoDAO(@Nullable Context context)
    {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query_createTable = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s INTEGER NOT NULL);", DB_TABLE, DB_COLUMN_TASKNAME, DB_COLUMN_PRIORITY);
        db.execSQL(query_createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        String query_upgrade = String.format("DELETE TABLE IF EXISTS %s", DB_TABLE);
        db.execSQL(query_upgrade);
        onCreate(db);
    }

    public boolean insertTodo(Task task)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();

        val.put(DB_COLUMN_TASKNAME, task.getName());
        val.put(DB_COLUMN_PRIORITY, task.getPriority());

        long insert = db.insert(DB_TABLE, null, val);
        if(insert == -1)    //insert fails
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean deleteTodo(Task task)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query_delete = String.format("DELETE FROM %s WHERE %s = %s", DB_TABLE, DB_COLUMN_ID, task.getId());
        Cursor cursor = db.rawQuery(query_delete, null);

        if(cursor.moveToFirst())
        {
            db.close();
            return true;
        }
        else
        {
            db.close();
            return false;
        }
    }

    public ArrayList<Task> getTasks()
    {
        ArrayList<Task> tasks = new ArrayList<>();
        String query_getAll = String.format("SELECT * FROM %s", DB_TABLE);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query_getAll, null);

        if(cursor.moveToFirst())
        {
            do
            {
                int taskId = cursor.getInt(0);
                String taskName = cursor.getString(1);
                int taskPriority = cursor.getInt(2);

                Task task = new Task(taskId, taskName, taskPriority);
                tasks.add(task);
            }
            while(cursor.moveToNext());
        }
        else
        {
            //Do nothing in case of failure
        }

        cursor.close();
        db.close();
        return tasks;
    }
}