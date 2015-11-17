package org.janastu.annotationapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.janastu.annotationapp.rest.AudioAnnotationFile;

import java.util.List;

/**
 * Created by murali on 14/11/15.
 */public class SimpleAnnotationAdapter extends ArrayAdapter<AudioAnnotationFile> {

    private List<AudioAnnotationFile> itemList;
    private Context context;

    public SimpleAnnotationAdapter(List<AudioAnnotationFile> itemList, Context ctx) {
        super(ctx, android.R.layout.activity_list_item, itemList);
        this.itemList = itemList;
        this.context = ctx;
    }

    public int getCount() {
        if (itemList != null)
            return itemList.size();
        return 0;
    }

    public AudioAnnotationFile getItem(int position) {
        if (itemList != null)
            return itemList.get(position);
        return null;
    }

    public long getItemId(int position) {
        if (itemList != null)
            return itemList.get(position).hashCode();
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.program_list, null);
        }

        AudioAnnotationFile c = itemList.get(position);
        TextView text = (TextView) v.findViewById(R.id.textViewId);
        text.setText(c.getId());

        TextView text1 = (TextView) v.findViewById(R.id.textViewDate);
        text1.setText(c.getUploadDate());

        TextView text2 = (TextView) v.findViewById(R.id.textViewTags);
        text2.setText(c.getTags().toString());



        return v;

    }

    public List<AudioAnnotationFile> getItemList() {
        return itemList;
    }

    public void setItemList(List<AudioAnnotationFile> itemList) {
        this.itemList = itemList;
    }

}