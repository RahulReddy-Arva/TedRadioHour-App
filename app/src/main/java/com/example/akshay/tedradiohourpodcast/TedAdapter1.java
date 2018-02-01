package com.example.akshay.tedradiohourpodcast;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by akshay on 3/4/2017.
 */

public class TedAdapter1 extends RecyclerView.Adapter<TedAdapter1.Viewholder1> {
    Context mContext;
    private callback1 buttoncallback1;
    private callback1 playbutton1;
    public List<TEDRadioHour> mObjects;
    public interface callback1{
        void PlayMusicCallback1(int i);
        void play1(int i);
    }




    public TedAdapter1(Context context,List<TEDRadioHour> objects ) {

        this.mContext = context;
        this.mObjects = objects;
        this.buttoncallback1= (callback1)context;
        this.playbutton1=(callback1)context;
    }
    public  class Viewholder1 extends RecyclerView.ViewHolder{
        TextView title1;
        ImageView iv1;
        RelativeLayout ll;
        ImageButton pl;

        public Viewholder1(View itemView){
            super(itemView);
            title1 = (TextView)itemView.findViewById(R.id.textView3);
            iv1 = (ImageView)itemView.findViewById(R.id.imageView2);
            ll = (RelativeLayout)itemView.findViewById(R.id.myview1);
            pl = (ImageButton)itemView.findViewById(R.id.imageButton6);
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    buttoncallback1.PlayMusicCallback1(getAdapterPosition());
                }
            });
            pl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    playbutton1.play1(getAdapterPosition());
                }
            });

        }
    }


    @Override
    public Viewholder1 onCreateViewHolder(ViewGroup parent, int viewType) {

        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid, parent, false);

        Viewholder1 vh1 = new Viewholder1(v);
        return vh1;
    }



    @Override
    public void onBindViewHolder(Viewholder1 Viewholder1, int i) {


       Viewholder1.title1.setText("    "+mObjects.get(i).getTitle());

        Picasso.with(mContext).load(mObjects.get(i).getImageURL())
                .into(Viewholder1.iv1);

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
