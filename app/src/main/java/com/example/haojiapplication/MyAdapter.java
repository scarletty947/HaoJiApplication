package com.example.haojiapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

public class MyAdapter  extends ArrayAdapter {

    private static final String TAG="MyAdapter";
    public MyAdapter(@NonNull Context context, int resource, ArrayList<Map<String, Object>> list) {
        super(context, resource,list);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View itemView=convertView;
        if(itemView==null){
            itemView= LayoutInflater.from(getContext()).inflate(R.layout.home_fragment_item,parent,false);
        }
        Map<String, Object> map=(Map<String, Object>)getItem(position);

        ImageView ItemImage=itemView.findViewById(R.id.ItemImage);
        TextView ItemText=itemView.findViewById(R.id.ItemText);

        ItemImage.setImageBitmap((Bitmap)map.get("ItemImage"));
        ItemText.setText(map.get("ItemText").toString());

        return  itemView;
    }
}
