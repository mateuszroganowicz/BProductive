package com.example.bproductive3.ui.todolist;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bproductive3.R;

import java.util.ArrayList;

public class TodolistFragment extends Fragment
{
    private TodoDAO dao;
    private ArrayAdapter<String> adapter;
    private ListView lv_taskList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_todolist, container, false);

        dao = new TodoDAO(getActivity());
        lv_taskList = root.findViewById(R.id.TaskList);
        ImageButton addTodoButton = root.findViewById(R.id.addTaskButton);
        loadTaskList();

        //ADD TASKS
        addTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.todo_dialog_layout, null);

                final EditText taskDescription = mView.findViewById(R.id.taskDesc);
                final EditText taskPriority = mView.findViewById(R.id.taskPrior);
                Button addButton = mView.findViewById(R.id.add_button);
                Button cancelButton = mView.findViewById(R.id.cancel_button);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        if(!taskDescription.getText().toString().isEmpty() && !taskPriority.getText().toString().isEmpty()) // == false
                        {
                            try
                            {
                                Task task = new Task(-1, taskDescription.getText().toString(), Integer.parseInt(taskPriority.getText().toString()));
                                dao.insertTodo(task);
                                loadTaskList();
                                Toast.makeText(getActivity(), "Task: " + task.getName() + " added successfully", Toast.LENGTH_SHORT).show();
                            }
                            catch(Exception e)
                            {
                                Task task = new Task(-1, "Error", -1);
                                dao.insertTodo(task);
                                loadTaskList();
                                Toast.makeText(getActivity(), "Something went wrong when adding a task!", Toast.LENGTH_SHORT).show();
                            }
                            loadTaskList();
                            dialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Task description or priority field is empty!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        dialog.dismiss();
                    }
                });
            }
        });

        //DELETE TASKS
        lv_taskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id)
            {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.todo_delete_dialog, null);
                Button deleteBttnYes = mView.findViewById(R.id.buttonDeleteYes);
                Button deleteBttnNo = mView.findViewById(R.id.buttonDeleteNo);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                deleteBttnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        dialog.dismiss();
                    }
                });

                deleteBttnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Task taskToDelete = (Task) parent.getItemAtPosition(position);  //Normally returns Object type
                        dao.deleteTodo(taskToDelete);
                        loadTaskList();
                        Toast.makeText(getActivity(), "Task: " + taskToDelete.getName() + " Deleted", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                return true;
            }
        });
        return root;
    }

    private void loadTaskList()
    {
        dao = new TodoDAO(getActivity());
        ArrayList<Task> tasks = dao.getTasks();

        ArrayAdapter adapter = new ArrayAdapter<Task>(getActivity(), android.R.layout.simple_list_item_1, tasks);
        lv_taskList.setAdapter(adapter);
    }
}