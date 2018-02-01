package com.example.akshay.tedradiohourpodcast;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import static android.support.v7.widget.GridLayoutManager.*;
import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements TEDAsync.IData,TedAdapter.callback,TedAdapter1.callback1 {
    final static String results_list = "results_list";
    final static String desc = "desc";
    final static String dur = "duration";
    final static String pub = "pubdate";
    final static String img = "img";
    final static String mp3 = "mp3";
    private TedAdapter tadapt;
    private  TedAdapter1 tadapt1;
    MediaPlayer mediaPlayer1, curSong;
    int convert;
    int i;
    ImageButton b;
    LinearLayoutManager llm;
    GridLayoutManager glm;
    RecyclerView recyclerView;
    ImageButton btn;
    SeekBar skb;
    ProgressDialog pb;
  ArrayList<TEDRadioHour> resultlist = new ArrayList<>();
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb = new ProgressDialog(MainActivity.this);
        pb.setMessage("Loading Episodes...");

      llm=new LinearLayoutManager(MainActivity.this);
         btn = (ImageButton)findViewById(R.id.imageButton4);
        btn.setVisibility(GONE);
        skb = (SeekBar)findViewById(R.id.seekBar3);
        skb.setVisibility(GONE);
         b = (ImageButton)findViewById(R.id.imageButton);
        pb.setIndeterminate(false);

        pb.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pb.setCancelable(false);

        curSong = new MediaPlayer();
        mediaPlayer1 = new MediaPlayer();
        new TEDAsync(MainActivity.this).execute("https://www.npr.org/rss/podcast.php?id=510298");


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:


                break;

            case R.id.action_favorite:

                if(convert == 0){

                     gridViewmethod();
                    convert ++;
                }
                else{
                      recyclerlistview();
                    convert =0 ;
                }

                break;




            default:
                recyclerlistview();
                break;

        }

        return true;
    }

    @Override
    public void onFillData(ArrayList<TEDRadioHour> al) throws ParseException {

        resultlist = al;

        filldetails(resultlist);


    }

    private void filldetails(final ArrayList<TEDRadioHour> resultlist) throws ParseException {

        recyclerView = (RecyclerView) findViewById(R.id.rid);
        tadapt = new TedAdapter(MainActivity.this,resultlist);
        tadapt1 = new TedAdapter1(MainActivity.this, resultlist);
        recyclerlistview();

    }
    public void getSeekBarStatus(){

        new Thread(new Runnable() {

            @Override
            public void run() {


                int currentPosition1 = 0;
                int total1 = mediaPlayer1.getDuration();
                skb.setMax(total1);
                while (mediaPlayer1 != null && currentPosition1 < total1) {
                    try {
                        Thread.sleep(1000);
                        currentPosition1 = mediaPlayer1.getCurrentPosition();
                    } catch (InterruptedException e) {
                        return;
                    }
                    skb.setProgress(currentPosition1);

                }

            }
        }).start();
        mediaPlayer1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                btn.setVisibility(View.INVISIBLE);
                skb.setVisibility(View.INVISIBLE);
            }
        });
        skb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress=0;

            @Override
            public void onProgressChanged(final SeekBar skb, int ProgressValue1, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer1.seekTo(ProgressValue1);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar skb) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar skb) {

            }
        });
    }
    public boolean gridViewmethod(){

        recyclerView = (RecyclerView) findViewById(R.id.rid);

        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2, glm.VERTICAL, false));

        recyclerView.setAdapter(tadapt1);

        Collections.sort(resultlist,

                new Comparator<TEDRadioHour>() {
                    public int compare(TEDRadioHour n1, TEDRadioHour n2) {
                        SimpleDateFormat sdf = new SimpleDateFormat("EEE,d MMM yyyy HH:mm:ss Z");
                        Date date1=null,date2 = null;
                        try {
                            date1 = sdf.parse(n1.getPublicationdate());
                            date2 = sdf.parse(n2.getPublicationdate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }




                        return (date1==date2)?0:(date1.compareTo(date2)>0)?-1:1;
                    }

                });
        return true;
    }

    public boolean recyclerlistview(){

        recyclerView.setAdapter(tadapt);
        recyclerView.setLayoutManager(llm);
        pb.dismiss();

        Collections.sort(resultlist,
                new Comparator<TEDRadioHour>() {
                    public int compare(TEDRadioHour n1, TEDRadioHour n2) {
                        SimpleDateFormat sdf = new SimpleDateFormat("EEE,d MMM yyyy HH:mm:ss Z");
                        Date date1=null,date2 = null;
                        try {
                            date1 = sdf.parse(n1.getPublicationdate());
                            date2 = sdf.parse(n2.getPublicationdate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }




                        return (date1==date2)?0:(date1.compareTo(date2)>0)?-1:1;
                    }

                });


     return true;
    }






    @Override
    public void PlayMusicCallback(int i) {
        Intent intent = new Intent(MainActivity.this,play.class);

        String message = resultlist.get(i).getTitle();
        String duration = resultlist.get(i).getDuration();
        String description = resultlist.get(i).getDescription();
        String pubdate = resultlist.get(i).getPublicationdate();
        String image = resultlist.get(i).getImageURL();
        String music = resultlist.get(i).getMP3URL();
        intent.putExtra(results_list, message);
        intent.putExtra(dur, duration);
        intent.putExtra(desc, description);
        intent.putExtra(pub, pubdate);
        intent.putExtra(img, image);
        intent.putExtra(mp3, music);
        startActivity(intent);
        mediaPlayer1.stop();
        btn.setVisibility(View.GONE);
        skb.setVisibility(View.GONE);
    }

    @Override
    public void play(int i) {

        final String url = resultlist.get(i).getMP3URL();
        //mediaPlayer1 = new MediaPlayer();
        mediaPlayer1.reset();
        mediaPlayer1.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer1.setDataSource(url);
            mediaPlayer1.prepare();

        } catch (IOException e) {
            e.printStackTrace();
        }


        mediaPlayer1.start();

//        if(curSong.isPlaying())
//        {
//            curSong.stop();
//            mediaPlayer1.start();
//            // start your new song
//        }else {
//            // start your new song
//            mediaPlayer1.start();
//            curSong = mediaPlayer1;
//        }
               // mediaPlayer1.start();
        btn.setVisibility(View.VISIBLE);
        skb.setVisibility(View.VISIBLE);
       /* if(mediaPlayer1.isPlaying()){
            mediaPlayer1.stop();
            mediaPlayer1.start();
        }*/
        getSeekBarStatus();
        btn.setBackgroundResource(R.drawable.pause);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer1.isPlaying()) {
                    btn.setBackgroundResource(R.drawable.play);
                    mediaPlayer1.pause();
                    Log.d("pause", "pause");
                } else {

                    btn.setBackgroundResource(R.drawable.pause);
                    mediaPlayer1.start();

                }


            }


        });


    }
    @Override
    public void play1(int i) {

        final String url = resultlist.get(i).getMP3URL();
       // mediaPlayer1 = new MediaPlayer();
        mediaPlayer1.reset();
        mediaPlayer1.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer1.setDataSource(url);

            mediaPlayer1.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer1.start();
        btn.setVisibility(View.VISIBLE);
        skb.setVisibility(View.VISIBLE);
        getSeekBarStatus();
        btn.setBackgroundResource(R.drawable.pause);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer1.isPlaying()) {
                    btn.setBackgroundResource(R.drawable.play);
                    mediaPlayer1.pause();
                    Log.d("pause", "pause");
                } else {

                    btn.setBackgroundResource(R.drawable.pause);
                    mediaPlayer1.start();


                }


            }


        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer1.stop();
        finish();
    }

    @Override
    public void PlayMusicCallback1(int i) {
        Intent intent = new Intent(MainActivity.this,play.class);

        String message = resultlist.get(i).getTitle();
        String duration = resultlist.get(i).getDuration();
        String description = resultlist.get(i).getDescription();
        String pubdate = resultlist.get(i).getPublicationdate();
        String image = resultlist.get(i).getImageURL();
        String music = resultlist.get(i).getMP3URL();
        intent.putExtra(results_list, message);
        intent.putExtra(dur, duration);
        intent.putExtra(desc, description);
        intent.putExtra(pub, pubdate);
        intent.putExtra(img, image);
        intent.putExtra(mp3, music);
        startActivity(intent);
        mediaPlayer1.stop();
        btn.setVisibility(View.GONE);
        skb.setVisibility(View.GONE);

    }
}
