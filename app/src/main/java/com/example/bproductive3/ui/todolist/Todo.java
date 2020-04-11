package com.example.bproductive3.ui.todolist;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = TodoDatabase.TABLE_NAME_TODO)

public class Todo implements Serializable
{

    @PrimaryKey(autoGenerate = true)
    public int todo_id;
    public String name;
    public String description;
    public String category;

    @Ignore
    public String priority;

}