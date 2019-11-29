package com.example.aiclock.alarmmanager;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.aiclock.R;

import java.io.IOException;

public class myService extends IntentService {
    public Uri uri;
    MediaPlayer mediaPlayer;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public myService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String temp = intent.getStringExtra("soundtrack");
        uri = Uri.parse(temp);
mediaPlayer = MediaPlayer.create(this, R.raw.loud_alarm_sound);
mediaPlayer.setLooping(true);
mediaPlayer.start();
//        try {
//                mediaPlayer = new MediaPlayer();
//
//               mediaPlayer.setDataSource(getApplicationContext(), uri);
//                mediaPlayer.setLooping(true);
//                mediaPlayer.setVolume(50,50);
//                mediaPlayer.prepare();
//                mediaPlayer.start();
//            } catch (IOException e) {
//                e.printStackTrace();
//                Log.d("Alarm error","media player cant run");
//            }

    }
}
