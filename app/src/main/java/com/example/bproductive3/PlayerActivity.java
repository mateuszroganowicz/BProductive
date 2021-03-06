package com.example.bproductive3;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {

    private ImageButton nextSong, prevSong;
    private ImageButton play;
    static private MediaPlayer mp;
    private Runnable runnable;
    private Handler handler;
    private SeekBar positionBar;
    private TextView currentTimeLabel;
    private TextView remainingTimeLabel;
    private TextView nowSong;
    String songName;
    int position;
    ArrayList<File> songs;
    Bundle bundle;
    int totalTime;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Util.onActivityCreateSetTheme(this);
        setContentView(R.layout.player_activity);

        play = findViewById(R.id.playmusic_button);
        nextSong = findViewById(R.id.next_song_button);
        prevSong = findViewById(R.id.previous_song_button);
        positionBar = findViewById(R.id.musicTime);
        currentTimeLabel = findViewById(R.id.currentTime);
        remainingTimeLabel = findViewById(R.id.remainingTime);
        nowSong = findViewById(R.id.nowPlaying);

        handler = new Handler();

        if(mp != null){
            mp.stop();
            mp.release();
        }

        bundle = getIntent().getExtras();


        songs = (ArrayList) bundle.getParcelableArrayList("list");
        position = bundle.getInt("position");
        songName = songs.get(position).getName().toString();
        String sName = getIntent().getStringExtra("SongName");

        nowSong.setText(sName);

        Uri uri = Uri.parse(songs.get(position).toString());
        mp = MediaPlayer.create(this, uri);
        totalTime = mp.getDuration();
        positionBar.setMax(totalTime);

        playMusic();


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mp.isPlaying()){
                    mp.pause();
                    play.setImageResource(R.drawable.ic_play);

                }
                else{
                    mp.start();
                    play.setImageResource(R.drawable.ic_pause);
                    updateSeekBar();
                }
            }
        });

        nextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position < songs.size() - 1){
                    position++;
                }
                else{
                    position = 0;
                }



                mp.stop();
                mp.release();
                songName = songs.get(position).getName().toString();
                nowSong.setText(songName);
                Uri uri = Uri.parse(songs.get(position).toString());
                mp = MediaPlayer.create(PlayerActivity.this, uri);
                play.setImageResource(R.drawable.ic_pause);
                totalTime = mp.getDuration();
                positionBar.setMax(totalTime);
                mp.start();
            }
        });

        prevSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position == 0){
                    position = songs.size()-1;
                }
                else{
                    position --;
                }

                mp.stop();
                mp.release();
                songName = songs.get(position).getName().toString();
                nowSong.setText(songName);
                Uri uri = Uri.parse(songs.get(position).toString());
                mp = MediaPlayer.create(PlayerActivity.this, uri);
                play.setImageResource(R.drawable.ic_pause);
                totalTime = mp.getDuration();
                positionBar.setMax(totalTime);
                mp.start();
            }
        });


        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                positionBar.setMax(mp.getDuration());
                mp.start();
                updateSeekBar();
            }
        });

        positionBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mp.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void updateSeekBar(){
        positionBar.setProgress(mp.getCurrentPosition());
        String currentTime = createTimeLabel(mp.getCurrentPosition());

        currentTimeLabel.setText(currentTime);

        String remainingTime = createTimeLabel(totalTime - mp.getCurrentPosition());
        remainingTimeLabel.setText("-" + remainingTime);

        if(remainingTime.equals("0:00")){
            if(position < songs.size() - 1){
                position++;
            }
            else{
                position = 0;
            }
            mp.stop();
            mp.release();
            songName = songs.get(position).getName().toString();
            nowSong.setText(songName);
            Uri uri = Uri.parse(songs.get(position).toString());
            mp = MediaPlayer.create(PlayerActivity.this, uri);
            play.setImageResource(R.drawable.ic_pause);
            totalTime = mp.getDuration();
            positionBar.setMax(totalTime);
            mp.start();

        }


        if(mp.isPlaying()){
            runnable = new Runnable() {
                @Override
                public void run() {
                    updateSeekBar();
                }

            };
            handler.postDelayed(runnable, 1000);
        }
    }

    public String createTimeLabel(int time){
        String timeLabel = "";
        int minutes = (int)time / 60000;
        int seconds = (int)time % 60000 / 1000;

        timeLabel += minutes + ":";
        if(seconds < 10){
            timeLabel += "0";
        }
        timeLabel += seconds;

        return timeLabel;
    }


    public void playMusic(){
        if(!mp.isPlaying() && mp != null){
            mp.start();
            play.setImageResource(R.drawable.ic_pause);
            updateSeekBar();

        }
        else {
            mp.pause();
            play.setImageResource(R.drawable.ic_play);
            updateSeekBar();

        }
    }

}
