package com.example.bproductive3.ui.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bproductive3.R;

import java.util.ArrayList;

public class TodolistAdapter extends ArrayAdapter<String>
{

    public TodolistAdapter(Context ctx, ArrayList<String> list)
    {
        super(ctx, R.layout.todolist_row, R.id.taskTitle, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View listRowView = inflater.inflate(R.layout.todolist_row, parent, false);

        String taskItem = getItem(position);
        TextView taskDesc = listRowView.findViewById(R.id.taskTitle);
        //Button deleteBtt = listRowView.findViewById(R.id.deleteButton);

//        deleteBtt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                Toast.makeText(view.getContext(), "Task deleted", Toast.LENGTH_SHORT).show();
//            }
//        });
        return  listRowView;
    }
}