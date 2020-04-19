package com.example.bproductive3.ui.music;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bproductive3.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import android.os.Handler;

public class MusicFragment extends Fragment
{

    private Button nextSong, prevSong;
    private ImageButton play;
    static private MediaPlayer mp;
    private Runnable runnable;
    private SeekBar positionBar;
    private TextView currentTimeLabel;
    private TextView remainingTimeLabel;
    private String songNames[];
    private ListView listView;
    ArrayAdapter<String> adapter;
    int totalTime;
    private Handler handler;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        listView = view.findViewById(R.id.musicListView);
        play = view.findViewById(R.id.playmusic_button);
        nextSong = view.findViewById(R.id.next_song_button);
        prevSong = view.findViewById(R.id.previous_song_button);
        positionBar = view.findViewById(R.id.musicTime);
        currentTimeLabel = view.findViewById(R.id.currentTime);
        remainingTimeLabel = view.findViewById(R.id.remainingTime);
        handler = new Handler();

        final ArrayList<File> songs = readSongs(Environment.getExternalStorageDirectory());
        songNames = new String[songs.size()];

        for(int i = 0;  i < songs.size(); ++i){
            songNames[i] = songs.get(i).getName().toString().replace(".mp3", "");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.song_layout, R.id.songView, songNames);
        listView.setAdapter(adapter);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mp != null){
                    playMusic();
                }


            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mp != null){
                    mp.stop();
                    mp.release();
                }

                Uri uri = Uri.parse(songs.get(position).toString());
                mp = MediaPlayer.create(getActivity(), uri);
                totalTime = mp.getDuration();
                //System.out.println("total" + totalTime);
                positionBar.setMax(totalTime);
                playMusic();

                System.out.println("songs size: " + songs.size());
                System.out.println("position: " + position);


                //TO NIE DZIALA :////
                /*if(mp.getCurrentPosition() == totalTime){
                    position++;
                    mp.stop();
                    mp.release();
                    for(int i = position; i < songs.size(); i++){
                        uri = Uri.parse(songs.get(position).toString());
                        mp = MediaPlayer.create(getActivity(), uri);
                        playMusic();
                    }
                }*/



            }

        });




        positionBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mp.seekTo(progress);
                    positionBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return view;

    }


    private ArrayList<File> readSongs(File root){
        ArrayList<File> arrayList = new ArrayList<File>();
        File files[] = root.listFiles();

        for(File file : files){
            if(file.isDirectory()){
                arrayList.addAll(readSongs(file));
            }
            else {
                if(file.getName().endsWith(".mp3")){
                    arrayList.add(file);
                }
            }
        }
        return arrayList;
    }

    private void updateSeekBar(){
        positionBar.setProgress(mp.getCurrentPosition());
        String currentTime = createTimeLabel(mp.getCurrentPosition());

        currentTimeLabel.setText(currentTime);

        String remainingTime = createTimeLabel(totalTime - mp.getCurrentPosition());
        remainingTimeLabel.setText("-" + remainingTime);


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