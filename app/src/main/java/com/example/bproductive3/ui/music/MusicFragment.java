package com.example.bproductive3.ui.music;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bproductive3.PlayerActivity;
import com.example.bproductive3.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class MusicFragment extends Fragment
{

    private String songNames[];
    private ListView listView;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);


        listView = view.findViewById(R.id.musicListView);
         runtimePermission();

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
                String songName = listView.getItemAtPosition(position).toString();
                startActivity(new Intent(getActivity(), PlayerActivity.class).putExtra("position", position).putExtra("list", songs).putExtra("SongName", songName));
            }
        });


        return view;
    }

    public void runtimePermission(){
        Dexter.withContext(getActivity())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }


    private ArrayList<File> readSongs(File root)
    {
        ArrayList<File> arrayList = new ArrayList<File>();
        File files[] = root.listFiles();

        try
        {
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
        }
        catch (Exception e)
        {
            Toast.makeText(getActivity(), "No songs found in memory!", Toast.LENGTH_SHORT).show();
        }
        return arrayList;
    }
}