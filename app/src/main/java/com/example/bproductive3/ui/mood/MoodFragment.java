package com.example.bproductive3.ui.mood;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.bproductive3.MainActivity;
import com.example.bproductive3.R;
import com.example.bproductive3.Util;

import java.util.Objects;

public class MoodFragment extends Fragment
{
    private MoodViewModel moodViewModel;
    private View moodView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        moodViewModel = ViewModelProviders.of(this).get(MoodViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mood, container, false);

        ImageButton happyBtt = root.findViewById(R.id.button_happy);
        ImageButton sadBtt = root.findViewById(R.id.button_sad);

        happyBtt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getActivity(),"You are Happy!",Toast.LENGTH_SHORT).show();
                Util.changeToTheme(getActivity(), Util.THEME_HAPPY);
            }
        });

        sadBtt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getActivity(),"You are Sad :(",Toast.LENGTH_SHORT).show();
                Util.changeToTheme(getActivity(), Util.THEME_SAD);
            }
        });
        return root;
    }
}