package com.example.bproductive3.ui.appinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.bproductive3.R;


public class AppinfoFragment extends Fragment {

    private AppinfoViewModel appinfoViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        appinfoViewModel =
                ViewModelProviders.of(this).get(AppinfoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_appinfo, container, false);
        final TextView textView = root.findViewById(R.id.text_appinfo);
        appinfoViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}