package com.example.bproductive3.ui.todolist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TodolistViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TodolistViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is to do list fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
