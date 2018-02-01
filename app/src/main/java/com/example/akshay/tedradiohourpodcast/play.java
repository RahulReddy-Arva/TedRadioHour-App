package com.example.akshay.tedradiohourpodcast;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Handler;

public class play extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    private Handler mhandler;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        TextView t1 = (TextView) findViewById(R.id.textView4);
        ImageView iv = (ImageView) findViewById(R.id.imageView3);
        TextView t2 = (TextView) findViewById(R.id.textView5);
        TextView t3 = (TextView) findViewById(R.id.textView6);
        TextView t4 = (TextView) findViewById(R.id.textView7);

        final ImageButton i2 = (ImageButton) findViewById(R.id.imageButton2);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.results_list);
        String image = intent.getStringExtra(MainActivity.img);
        Picasso.with(getApplicationContext()).load(image)
                .into(iv);

        String duration = intent.getStringExtra(MainActivity.dur);
        String description = intent.getStringExtra(MainActivity.desc);
        String pubdate = intent.getStringExtra(MainActivity.pub);
        final String url = intent.getStringExtra(MainActivity.mp3);
        mediaPlayer = new MediaPlayer();

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        i2.setBackgroundResource(R.drawable.play);
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer.isPlaying()) {
                    i2.setBackgroundResource(R.drawable.play);
                    mediaPlayer.pause();
                    Log.d("pause", "pause");
                } else {

                    i2.setBackgroundResource(R.drawable.pause);
                    mediaPlayer.start();
                    getSeekBarStatus();
                }


            }


        });


        SimpleDateFormat sdf = new SimpleDateFormat("EEE,d MMM yyyy HH:mm:ss Z");
        Date date = null;
        try {
            date = sdf.parse(pubdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat out1 = new SimpleDateFormat("EEE,dd,MMM yyyy");
        String finaldate1 = out1.format(date);


        t1.setText(message);
        t4.setText("Duration:   " + duration);
        t2.setText("Description:  " + description);
        t3.setText("Publication Date:   " + finaldate1);
    }
    public void getSeekBarStatus(){
        final SeekBar s1 = (SeekBar) findViewById(R.id.seekBar);
        new Thread(new Runnable() {

            @Override
            public void run() {


                int currentPosition = 0;
                int total = mediaPlayer.getDuration();
                s1.setMax(total);
                while (mediaPlayer != null && currentPosition < total) {
                    try {
                        Thread.sleep(1000);
                        currentPosition = mediaPlayer.getCurrentPosition();
                    } catch (InterruptedException e) {
                        return;
                    }
                    s1.setProgress(currentPosition);


                }
            }
        }).start();
        s1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress=0;

            @Override
            public void onProgressChanged(final SeekBar s1, int ProgressValue, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(ProgressValue);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar s1) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar s1) {

            }
        });
    }


}
