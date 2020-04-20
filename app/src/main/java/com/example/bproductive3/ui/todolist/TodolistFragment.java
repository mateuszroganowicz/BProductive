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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bproductive3.R;

import java.util.ArrayList;

public class TodolistFragment extends Fragment
{
    private TodoDAO dao;
    private ListView lv_taskList;
    private static final int RB1_ID = 1;
    private static final int RB2_ID = 2;
    private static final int RB3_ID = 3;

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
                Button addButton = mView.findViewById(R.id.add_button);
                Button cancelButton = mView.findViewById(R.id.cancel_button);
                final RadioGroup priorityRadio = mView.findViewById(R.id.priorityRadioGroup);
                RadioButton lowPriorRadio = mView.findViewById(R.id.radio_low);
                RadioButton medPriorRadio = mView.findViewById(R.id.radio_med);
                RadioButton highPriorRadio = mView.findViewById(R.id.radio_high);

                lowPriorRadio.setId(RB1_ID);
                medPriorRadio.setId(RB2_ID);
                highPriorRadio.setId(RB3_ID);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        if(!taskDescription.getText().toString().isEmpty() ) // == false
                        {
                            try
                            {
                                int checkedBtt = priorityRadio.getCheckedRadioButtonId();
                                Task task = new Task(-1, taskDescription.getText().toString(), checkedBtt);

                                if(checkedBtt == -1)
                                {
                                    Toast.makeText(getActivity(), "Select task priority!", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    dao.insertTodo(task);
                                    Toast.makeText(getActivity(), "Task: " + task.getName() + " added successfully", Toast.LENGTH_SHORT).show();
                                    checkedBtt = 0;
                                    dialog.dismiss();
                                }

                            }
                            catch(Exception e)
                            {
                                Toast.makeText(getActivity(), "Something went wrong when adding a task!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                            loadTaskList();
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Task description field is empty!", Toast.LENGTH_SHORT).show();
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

        ArrayAdapter adapter = new ToDoListAdapter(getActivity(), R.layout.todolist_row, tasks);
        lv_taskList.setAdapter(adapter);
    }
}