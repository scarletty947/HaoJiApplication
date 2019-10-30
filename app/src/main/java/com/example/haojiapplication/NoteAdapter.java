package com.example.haojiapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import androidx.annotation.NonNull;

public class NoteAdapter extends ArrayAdapter {
    public NoteAdapter(@NonNull Context context, int resource, ArrayList<Map<String, Object>> list) {
        super(context, resource,list);
    }
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView != null && position == 0) {
            return convertView;
        }
        View itemView=convertView;
        if(itemView==null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.note_picture_item, parent, false);
        }
        Map<String, Object> map=(Map<String, Object>)getItem(position);
        Log.i("LLllllllllllllllllll","onOptionItemSelected:"+position);
        Log.i("LLllllllllllllllllll","onOptionItemSelected:"+(Bitmap)map.get("ItemImage"));

        ImageView ItemImage=itemView.findViewById(R.id.imageView5);
        ItemImage.setImageBitmap((Bitmap)map.get("ItemImage"));

        return  itemView;
    }
}
