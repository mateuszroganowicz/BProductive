package com.example.bproductive3.ui.mood;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.bproductive3.R;
import com.example.bproductive3.Util;

import java.util.Objects;

public class MoodFragment extends Fragment
{
    private MoodViewModel moodViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        moodViewModel = ViewModelProviders.of(this).get(MoodViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mood, container, false);

        final TextView text_mood = root.findViewById(R.id.text_mood);

        ImageButton happyBtt = root.findViewById(R.id.button_happy);
        ImageButton sadBtt = root.findViewById(R.id.button_sad);
        ImageButton confidentBtt = root.findViewById(R.id.button_cool);
        ImageButton stressedBtt = root.findViewById(R.id.button_stress);
        ImageButton angryBtt = root.findViewById(R.id.button_angry);
        ImageButton normalBtt = root.findViewById(R.id.button_shock);

        happyBtt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Util.changeToTheme(Objects.requireNonNull(getActivity()), Util.THEME_HAPPY);
            }
        });

        sadBtt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Util.changeToTheme(Objects.requireNonNull(getActivity()), Util.THEME_SAD);
            }
        });

        confidentBtt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Util.changeToTheme(Objects.requireNonNull(getActivity()), Util.THEME_CONFIDENT);
            }
        });

        stressedBtt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Util.changeToTheme(Objects.requireNonNull(getActivity()), Util.THEME_STRESSED);
            }
        });

        angryBtt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Util.changeToTheme(Objects.requireNonNull(getActivity()), Util.THEME_ANGRY);
            }
        });

        normalBtt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Util.changeToTheme(Objects.requireNonNull(getActivity()), Util.THEME_NORMAL);
            }
        });
        return root;
    }
}