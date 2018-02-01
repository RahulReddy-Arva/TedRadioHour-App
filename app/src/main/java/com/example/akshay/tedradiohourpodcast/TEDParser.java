package com.example.akshay.tedradiohourpodcast;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;


import static java.lang.System.in;

/**
 * Created by akshay on 3/4/2017.
 */

public class TEDParser {


    static ArrayList<TEDRadioHour> podcastparser(InputStream in) throws XmlPullParserException, IOException {
        XmlPullParser parser= XmlPullParserFactory.newInstance().newPullParser();
        parser.setInput(in,"UTF-8");
        TEDRadioHour radioHour1 = null;
        ArrayList<TEDRadioHour> radioHourArrayList= new ArrayList<>();
        int event=parser.getEventType();

        while(event != XmlPullParser.END_DOCUMENT) {
            //Log.d("TAGNAME",parser.getName()+"");
            switch (event) {
                case XmlPullParser.START_TAG:
                    if (parser.getName().equals("item")) {
                        radioHour1 = new TEDRadioHour();
                    }
                    if (radioHour1 != null) {
                        if (parser.getName().equals("title")) {
                            radioHour1.setTitle(parser.nextText().trim());
                        }
                    }
                    if (radioHour1 != null) {
                        if (parser.getName().equals("description")) {
                            radioHour1.setDescription(parser.nextText().trim());
                        }
                    }
                    if (radioHour1 != null) {
                        if (parser.getName().equals("pubDate")) {
                            radioHour1.setPublicationdate(parser.nextText().trim());

                        }
                    }
                    if (radioHour1 != null) {
                        if (parser.getName().equals("itunes:image")) {
                            radioHour1.setImageURL(parser.getAttributeValue(null,"href"));
                        }
                    }
                    if (radioHour1 != null) {
                        if (parser.getName().equals("itunes:duration")) {
                            radioHour1.setDuration(parser.nextText().trim());
                        }
                    }
                    if (radioHour1 != null) {
                        if (parser.getName().equals("enclosure")) {
                            radioHour1.setMP3URL(parser.getAttributeValue(null,"url"));
                        }
                    }


                    break;
                case XmlPullParser.END_TAG:
                    if (parser.getName().equals("item"))
                        radioHourArrayList.add(radioHour1);
                default:
                    break;
            }
            event = parser.next();
        } Log.d("demoooo",radioHourArrayList.toString());
        return  radioHourArrayList;

    }
}
