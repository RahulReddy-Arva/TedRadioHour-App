package com.example.akshay.tedradiohourpodcast;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by akshay on 3/4/2017.
 */

public class TEDAsync extends AsyncTask<String,Void,ArrayList<TEDRadioHour>>{

    MainActivity activity;
    ProgressDialog pb;

    public TEDAsync(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {




        super.onPreExecute();
    }



    @Override
    protected ArrayList<TEDRadioHour> doInBackground(String... params) {
        try {
            URL url= new URL(params[0]);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int statusCode= connection.getResponseCode();
            if(statusCode == HttpURLConnection.HTTP_OK){
                InputStream inputStream= connection.getInputStream();
                return TEDParser.podcastparser(inputStream);

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return null;


    }


    @Override
    protected void onPostExecute(ArrayList<TEDRadioHour> TED) {


        try {
            activity.onFillData(TED);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        super.onPostExecute(TED);
    }

    static public  interface IData{
        public void onFillData(ArrayList<TEDRadioHour> al) throws ParseException;
    }
}
