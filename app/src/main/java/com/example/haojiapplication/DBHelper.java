package com.example.haojiapplication;
//创建数据库访问对象
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private  static final int VERSION=1;
    private  static final String DB_NAME="book.db";
    public   static final String TB_NAME="picture";
    public   static final String TB_NAME2="book";
    public   static final String TB_NAME3="category";
    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public DBHelper(Context context){
        super(context,DB_NAME,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TB_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,BOOKNAME TEXT,NOTEPICTURE BLOB)");
        db.execSQL("CREATE TABLE "+TB_NAME2+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,BOOKNAME TEXT,FACEPICTURE BLOB,AUTHOR TEXT,READTIME TEXT,NOTE TEXT,TYPE TEXT)");
        db.execSQL("CREATE TABLE "+TB_NAME3+"(CATEGORY TEXT PRIMARY KEY ,CATEGORYPICTURE BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
