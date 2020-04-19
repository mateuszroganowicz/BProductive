package com.example.bproductive3.ui.music;


import android.content.Intent;
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

import com.example.bproductive3.PlayerActivity;
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
    int currentSong;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        listView = view.findViewById(R.id.musicListView);



        final ArrayList<File> songs = readSongs(Environment.getExternalStorageDirectory());
        songNames = new String[songs.size()];

        for(int i = 0;  i < songs.size(); ++i){
            songNames[i] = songs.get(i).getName().toString().replace(".mp3", "");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.song_layout, R.id.songView, songNames);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), PlayerActivity.class).putExtra("position", position).putExtra("list", songs));
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




}