package com.example.bproductive3.ui.music;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bproductive3.R;

public class MusicFragment extends Fragment {

    private MusicViewModel musicViewModel;

    private ImageButton playButton;
    private SeekBar positionBar;
    private SeekBar volumeBar;
    private TextView currentTimeLabel;
    private TextView remainingTimeLabel;
    private MediaPlayer mp;
    int totalTime;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_music, container, false);
        mp = MediaPlayer.create(getActivity() , R.raw.music);
        playButton = view.findViewById(R.id.playmusic_button);
        positionBar = view.findViewById(R.id.musicTime);
        volumeBar = view.findViewById(R.id.volume);
        currentTimeLabel = view.findViewById(R.id.currentTime);
        remainingTimeLabel = view.findViewById(R.id.remainingTime);

        mp.setLooping(true);
        mp.seekTo(0);
        mp.setVolume(0.5f, 0.5f);
        totalTime = mp.getDuration();

        positionBar.setMax(totalTime);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusic();
            }
        });

        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float volumeNumber = progress / 100f;
                mp.setVolume(volumeNumber, volumeNumber);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

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
}