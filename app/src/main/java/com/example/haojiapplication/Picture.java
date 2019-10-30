package com.example.haojiapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;

import com.example.haojiapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.FileNotFoundException;

public class Picture extends AppCompatActivity implements View.OnClickListener{
    String bookname,author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        Intent content=getIntent();
        bookname=content.getStringExtra("BookName");
        author=content.getStringExtra("BookAuthor");
        Intent content1=new Intent(this, BookContentActivity.class);
        content1.putExtra("BookName",bookname);
        content1.putExtra("BookAuthor",author);
        startActivity(content1);

    }
    @Override
    public void onClick(View v) {
        Intent content1=new Intent(this, MainActivity.class);
        startActivity(content1);
        }

}
