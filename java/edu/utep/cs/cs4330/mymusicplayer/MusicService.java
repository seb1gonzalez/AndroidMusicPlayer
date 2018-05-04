package edu.utep.cs.cs4330.mymusicplayer;

import android.app.IntentService;

import android.content.Intent;

import android.media.MediaPlayer;
import android.net.Uri;


public class MusicService extends IntentService {
    MediaPlayer player;


    public MusicService() {
        super("MusicService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        player = MediaPlayer.create(this, Uri.parse(intent.getStringExtra("url")));
        int time = intent.getIntExtra("length",0);
        player.start();
        try {
            Thread.sleep(time*1000);
        }
           catch (InterruptedException e){

           }

        }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.reset();
    }
}