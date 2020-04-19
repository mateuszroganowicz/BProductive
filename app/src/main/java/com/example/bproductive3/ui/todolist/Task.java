package com.example.bproductive3.ui.todolist;

public class Task
{
    private int id;
    private String name;
    private int priority;

    public Task(int id, String name, int priority)
    {
        this.id = id;
        this.name = name;
        this.priority = priority;
    }

    public Task()
    { }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getPriority()
    {
        return priority;
    }

    public void setPriority(int priority)
    {
        this.priority = priority;
    }

    @Override
    public String toString()
    {
        return "Task : " +  name + " \nPriority : " + priority;
    }
}
