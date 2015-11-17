package org.janastu.annotationapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListAdapter;

/**
 * Created by murali on 12/11/15.
 */import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.janastu.annotationapp.rest.AudioAnnotationFile;
import org.janastu.annotationapp.rest.JsonRestData;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomAdapter extends BaseAdapter{
    String [] idResult;
    Context context;
    int [] imageId;
    List<AudioAnnotationFile> files;
    private static LayoutInflater inflater=null;
    public CustomAdapter(CardViewActivity mainActivity, JsonRestData data) {
        // TODO Auto-generated constructor stub


       files   = data.getFiles();

        context=mainActivity;
        //imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public CustomAdapter(CardViewActivity mainActivity, List<AudioAnnotationFile> files2) {
        // TODO Auto-generated constructor stub


        files   = files2;

        context=mainActivity;
        //imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return files.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView id;
        TextView tags;
        TextView uploadDate;

        ImageView img;
        Button playAudio;
        Button  tagAudio;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        AudioAnnotationFile f = files.get( position );
      //  String id = f.getId();
        String id =  f.getUrl();///ID not needed so putting URL;
        String tags = f.getTags().toString();
        String date = f.getUploadDate();
        String url = f.getUrl();


        View rowView;
        rowView = inflater.inflate(R.layout.program_list, null);
        holder.id=(TextView) rowView.findViewById(R.id.textViewId);
        holder.tags=(TextView) rowView.findViewById(R.id.textViewTags);
        holder.uploadDate=(TextView) rowView.findViewById(R.id.textViewDate);
        holder.playAudio = (Button)rowView.findViewById(R.id.voiceButton);
        holder.tagAudio = (Button)rowView.findViewById(R.id.tagButton);

      //  holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
        try {
       // holder.tv.setText(files.get( position ).getId().toString();

            holder.id.setText(id);
            holder.tags.setText(tags);
            holder.uploadDate.setText(date);


            holder.playAudio.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    AudioAnnotationFile f2 = files.get( position );
                 ;
                    Intent intent = new Intent(context , StreamingMp3Player.class);
                    Bundle b = new Bundle();
                    b.putString("keyURL",    f2.getUrl()); //Your id
                    intent.putExtras(b); //Put your id to your next Intent
                    context.startActivity(intent);


                }
            });


            holder.tagAudio.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    AudioAnnotationFile f2 = files.get( position );
                    ;
                    Intent intent = new Intent(context , PostTagActivity.class);
                    Bundle b = new Bundle();
                    b.putString("keyID",    f2.getId()); //Your id
                    intent.putExtras(b); //Put your id to your next Intent
                    context.startActivity(intent);


                }
            });
            rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
             //    Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_LONG).show();
            }
        });
        } catch(Exception e)
        {
            Logger.getLogger("").log(Level.WARNING, "exception" +e);
        }
        return rowView;
    }

}