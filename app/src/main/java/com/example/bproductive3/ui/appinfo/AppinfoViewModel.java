package com.example.bproductive3.ui.appinfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AppinfoViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public AppinfoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is app info fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
