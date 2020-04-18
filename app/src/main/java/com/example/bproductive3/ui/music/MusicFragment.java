package com.example.bproductive3.ui.music;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.bproductive3.R;

import java.util.ArrayList;

public class MusicFragment extends Fragment
{
    private MusicViewModel musicViewModel;
    private ImageButton playButton;
    private SeekBar positionBar;
    private TextView currentTimeLabel;
    private TextView remainingTimeLabel;
    private MediaPlayer mp;
    int totalTime;

    private static final int MY_PERMISSION_REQUEST = 1;
    ArrayList<String> songsList;
    private String songNames[];
    private ListView listView;
    ArrayAdapter<String> adapter;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_music, container, false);

        playButton = view.findViewById(R.id.playmusic_button);
        positionBar = view.findViewById(R.id.musicTime);
        currentTimeLabel = view.findViewById(R.id.currentTime);
        remainingTimeLabel = view.findViewById(R.id.remainingTime);
        listView = view.findViewById(R.id.musicListView);
        //mp = MediaPlayer.create(getActivity(), MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);

        //Permissions
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }
            else
            {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }
        }
        else
        {
            doStuff();
        }

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusic();
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

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(mp != null){
                   try {
                       Message msg = new Message();
                       msg.what = mp.getCurrentPosition();
                       handler.sendMessage(msg);
                       Thread.sleep(1000);
                   }catch (InterruptedException e){}
                }
            }
        }).start();

        return view;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            int currentPosition = msg.what;
            positionBar.setProgress(currentPosition);
            String currentTime = createTimeLabel(currentPosition);

            currentTimeLabel.setText(currentTime);

            String remainingTime = createTimeLabel(totalTime - currentPosition);
            remainingTimeLabel.setText("-" + remainingTime);

        }


    };

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
        if(!mp.isPlaying()){
            mp.start();
            playButton.setImageResource(R.drawable.ic_pause);
        }
        else {
            mp.pause();
            playButton.setImageResource(R.drawable.ic_play);
        }
    }

    public void getMusic()
    {
        ContentResolver contentResolver = getActivity().getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null, null, null, null);

        if(songCursor != null && songCursor.moveToFirst())
        {
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songLocation = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            do
            {
                String currentTitle = songCursor.getString(songTitle);
                String currentArtist = songCursor.getString(songArtist);
                String currentLocation = songCursor.getString(songLocation);
                songsList.add(currentTitle + "\n" + currentArtist + currentLocation);
            }
            while(songCursor.moveToNext());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode)
        {
            case MY_PERMISSION_REQUEST:
                {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                    {
                        Toast.makeText(getActivity(), "Permission granted!", Toast.LENGTH_SHORT).show();
                        doStuff();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "No permission!", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
                break;
            }
        }
    }

     public void doStuff()
     {
        songsList = new ArrayList<>();
        getMusic();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, songsList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
               // mp = MediaPlayer.create(getActivity(), Uri.parse(songsList.get(position).toString()));
                mp = MediaPlayer.create(getActivity(), MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);

                //mp jest NULL
                System.out.println("mp:" + mp);
                totalTime = mp.getDuration();
                positionBar.setMax(totalTime);

                if(mp != null && !mp.isPlaying()){
                    mp.start();
                    playButton.setImageResource(R.drawable.ic_pause);
                }
                else {
                    mp.pause();
                    playButton.setImageResource(R.drawable.ic_play);
                }

            }
        });
    }
}