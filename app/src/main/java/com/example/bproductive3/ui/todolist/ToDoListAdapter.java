package com.example.bproductive3.ui.todolist;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bproductive3.R;

import java.util.ArrayList;

public class ToDoListAdapter extends ArrayAdapter<Task>
{
    private Context mContext;
    private int mResource;

    public ToDoListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Task> objects)
    {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        int taskId = getItem(position).getId();
        String taskName = getItem(position).getName();
        int taskPriority = getItem(position).getPriority();

        Task task = new Task(taskId, taskName, taskPriority);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tv_taskName = (TextView) convertView.findViewById(R.id.row_taskTitle);
        TextView tv_taskPriority = (TextView) convertView.findViewById(R.id.row_taskPrior);

        tv_taskName.setText(taskName);
        tv_taskPriority.setText("Priority: " +  taskPriority);

        switch(taskPriority)
        {
            case 3:
                tv_taskName.setTextColor(Color.parseColor("#D2222D"));
                tv_taskPriority.setTextColor(Color.parseColor("#D2222D"));
                break;
            case 2:
                tv_taskName.setTextColor(Color.parseColor("#FFBF00"));
                tv_taskPriority.setTextColor(Color.parseColor("#FFBF00"));
                break;
            case 1:
                tv_taskName.setTextColor(Color.parseColor("#238823"));
                tv_taskPriority.setTextColor(Color.parseColor("#238823"));
                break;
        }

        return convertView;
    }
}