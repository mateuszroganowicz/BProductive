package com.example.bproductive3.ui.todolist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.bproductive3.R;

public class TodolistFragment extends Fragment
{
    private TodolistViewModel todolistViewModel;

    TodoDAO dao;
    ArrayAdapter<String> adapter;
    ListView taskList;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        todolistViewModel = ViewModelProviders.of(this).get(TodolistViewModel.class);
        View root = inflater.inflate(R.layout.fragment_todolist, container, false);

        taskList = (ListView)root.findViewById(R.id.TaskList);
        //loadTaskList();

        return root;
    }

//    private void loadTaskList()
//    {
//        ArrayList <String> todoList = dao.getTasks();
//
//        if(adapter == null)
//        {
//            adapter = new ArrayAdapter<String>(getActivity(), R.layout.todolist_row, R.id.taskTitle, todoList);
//            taskList.setAdapter(adapter);
//        }
//        else
//        {
//            adapter.clear();
//            adapter.addAll(todoList);
//            adapter.notifyDataSetChanged();
//        }
//    }
}