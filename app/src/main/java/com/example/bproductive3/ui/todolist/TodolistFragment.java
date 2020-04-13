package com.example.bproductive3.ui.todolist;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bproductive3.R;

import java.util.ArrayList;
import java.util.Objects;

public class TodolistFragment extends Fragment
{
    private TodoDAO dao;
    private ArrayAdapter<String> adapter;
    private ListView taskList;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_todolist, container, false);
        View todoList_row = inflater.inflate(R.layout.todolist_row, container, false);

        dao = new TodoDAO(getActivity());
        taskList = (ListView)root.findViewById(R.id.TaskList);
        loadTaskList();

        ImageButton addTodoButton = root.findViewById(R.id.addTaskButton);
        Button deleteTodoButton = todoList_row.findViewById(R.id.deleteButton);

        addTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.todo_dialog_layout, null);

                final EditText taskDescription = mView.findViewById(R.id.taskDesc);
                Button addButton = mView.findViewById(R.id.add_button);
                Button cancelButton = mView.findViewById(R.id.cancel_button);

                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        if(!taskDescription.getText().toString().isEmpty()) // == false
                        {
                            String task = String.valueOf(taskDescription.getText());
                            dao.insertTodo(task);
                            loadTaskList();
                            Toast.makeText(getActivity(), "Task added", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Task description field is empty!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        dialog.dismiss();
                    }
                });
            }
        });

        deleteTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getActivity(), "Task deleted", Toast.LENGTH_SHORT).show();
                View parent = (View)v.getParent();
                TextView taskTextView = parent.findViewById(R.id.taskTitle);
                String task = String.valueOf(taskTextView.getText());

                dao.deleteTodo(task);
                loadTaskList();
            }
        });

        return root;
    }

    private void loadTaskList()
    {
        ArrayList<String> todos = dao.getTasks();

        if(adapter == null)
        {
            adapter = new ArrayAdapter<String>(Objects.requireNonNull(getActivity()), R.layout.todolist_row, R.id.taskTitle, todos);
            taskList.setAdapter(adapter);
        }
        else
        {
            adapter.clear();
            adapter.addAll(todos);
            adapter.notifyDataSetChanged();
        }
    }
}