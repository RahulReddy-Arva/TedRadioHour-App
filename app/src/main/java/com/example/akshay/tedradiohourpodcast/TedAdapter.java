package com.example.akshay.tedradiohourpodcast;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;


import static android.content.Context.MODE_PRIVATE;

/**
 * Created by akshay on 3/4/2017.
 */

public class TedAdapter extends RecyclerView.Adapter<TedAdapter.Viewholder> {
        Context mContext;
    private callback buttoncallback;
    private callback playbutton;

    public    List<TEDRadioHour> mObjects;
    public interface callback{
        void PlayMusicCallback(int i);
        void play(int i);
    }





    public TedAdapter(Context context,List<TEDRadioHour> objects ) {

            this.mContext = context;
            this.mObjects = objects;
          this.buttoncallback= (callback)context;
        this.playbutton=(callback)context;
        }
    public  class Viewholder extends RecyclerView.ViewHolder{
         TextView title;
        ImageView iv;
        TextView date;
        ImageButton b;
        LinearLayout rcv;

        public Viewholder(View itemView){
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.textView);
             iv = (ImageView)itemView.findViewById(R.id.imageView);
             date = (TextView)itemView.findViewById(R.id.textView2);
              b = (ImageButton)itemView.findViewById(R.id.imageButton);
            rcv = (LinearLayout)itemView.findViewById(R.id.myview);

            rcv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    buttoncallback.PlayMusicCallback(getAdapterPosition());
                }
            });
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    playbutton.play(getAdapterPosition());
                }
            });
        }
    }


    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_row, parent, false);

        Viewholder vh = new Viewholder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(Viewholder Viewholder, int i) {
     Viewholder.title.setText("    "+mObjects.get(i).getTitle());
 final int index = i;
        Picasso.with(mContext).load(mObjects.get(i).getImageURL())
                .into(Viewholder.iv);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE,d MMM yyyy HH:mm:ss Z");
        Date date=null;
        try {
            date = sdf.parse(mObjects.get(i).getPublicationdate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat out = new SimpleDateFormat("EEE,dd,MMM yyyy");
        String finaldate = out.format(date);
        Viewholder.date.setText("    posted: " +finaldate);



    }


    @Override
    public int getItemCount() {
        return mObjects.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

