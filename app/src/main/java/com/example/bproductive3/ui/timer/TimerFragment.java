package com.example.bproductive3.ui.timer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bproductive3.R;

public class TimerFragment extends Fragment  {

    private final static long start_time = 6000; //In milliseconds
    private final static long break_time = 16000; //In milliseconds
    private long timeInMs = start_time;
    private TextView textCountDown;
    private ImageButton ButtonStartPause;
    private ImageButton ButtonRestart;
    private ImageView Circle0;
    private ImageView Circle1;
    private ImageView Circle2;
    private ImageView Circle3;
    private TextView workStatus;

    private CountDownTimer countDownTimer;
    private boolean timerRun;
    private boolean timeToBreak = true;
    private int session = 0;
    private int totalSessions = 4;
    View view;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_timer, container, false);

        textCountDown = view.findViewById(R.id.countdown_text);
        ButtonStartPause = view.findViewById(R.id.button_start);
        ButtonRestart = view.findViewById(R.id.button_reset);
        Circle0 = view.findViewById(R.id.circle_0);
        Circle1 = view.findViewById(R.id.circle_1);
        Circle2 = view.findViewById(R.id.circle_2);
        Circle3 = view.findViewById(R.id.circle_3);
        workStatus = view.findViewById(R.id.workStatus);

        ButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStop();
            }
        });

        ButtonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                System.out.println(timerRun);
                System.out.println("Time to break: " + timeToBreak);
            }
        });

        return view;
    }

    public void startStop(){
        if(timerRun){
            stopTimer();
        }
        else{
            startTimer();
        }
    }

    public void startTimer(){
        countDownTimer = new CountDownTimer(timeInMs, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeInMs = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();
        workStatus.setVisibility(view.VISIBLE);
        ButtonStartPause.setImageResource(R.drawable.ic_pause);

        timerRun = true;
    }

    public void stopTimer(){
        countDownTimer.cancel();
        ButtonStartPause.setImageResource(R.drawable.ic_play);
        timerRun = false;
    }

    public void updateTimer(){
        int minutes = (int) timeInMs / 60000;
        int seconds = (int) timeInMs % 60000 / 1000;

        String timeLeft = null;
        if(minutes < 10) timeLeft = "0";
        timeLeft += minutes;
        timeLeft += ":";
        if(seconds < 10) timeLeft += "0";
        timeLeft += seconds;

        textCountDown.setText(timeLeft);
        if(timeToBreak){
            workStatus.setText("Study hard!");
        }
        else {
            workStatus.setText("Time to break!");
        }
        if(timeLeft.equals("00:00") && session < totalSessions){
            breakTime();

        }
        else if(timeLeft.equals("00:00") && session == totalSessions){
            reset();
            System.out.println("Time left: " + timeInMs);
            System.out.println(timerRun);
            System.out.println("Time to break: " + timeToBreak);

        }

    }

    public void reset(){
        Circle0.setImageResource(R.drawable.ic_circle);
        Circle1.setImageResource(R.drawable.ic_circle);
        Circle2.setImageResource(R.drawable.ic_circle);
        Circle3.setImageResource(R.drawable.ic_circle);
        session = 0;
        workStatus.setVisibility(view.GONE);


        if(timerRun){
            countDownTimer.cancel();
            timeInMs = start_time;
            ButtonStartPause.setImageResource(R.drawable.ic_play);
            timerRun = false;
            timeToBreak = true;
            updateTimer();

        }
        else{
            timeInMs = start_time;
            timerRun = false;
            timeToBreak = true;
            updateTimer();

        }
    }

    public void breakTime(){
        if(timeToBreak) {

            timeInMs = break_time;
            startTimer();
            session++;
            System.out.println("Number of sessions: " + session);
            if(session == 1){
                Circle0.setImageResource(R.drawable.ic_moon);
            }
            else if(session == 2){

                    Circle1.setImageResource(R.drawable.ic_moon);

            }
            else if(session == 3){

                Circle2.setImageResource(R.drawable.ic_moon);

            }
            else if(session == 4){
                Circle3.setImageResource(R.drawable.ic_moon);
            }

            timeToBreak = false;
        }
        else{
            timeInMs = start_time;
            startTimer();
            timeToBreak = true;
        }

    }

}
