package com.example.haojiapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ItemPictureManager {
    private DBHelper dbHelper;
    private String TBNAME;

    public ItemPictureManager(Context context){
        dbHelper=new DBHelper(context);
        TBNAME=DBHelper.TB_NAME;
    }
    //把图片转换成字节
    public byte[] img2byte(Bitmap bitmap)
   {
             ByteArrayOutputStream baos = new ByteArrayOutputStream();
             bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
             return baos.toByteArray();
         }
         //将字节转换为图片
         public Bitmap ByteArray2Bitmap(byte[] data, int width, int height) {
             int Size = width * height;
             int[] rgba = new int[Size];

             for (int i = 0; i < height; i++)
                 for (int j = 0; j < width; j++) {
                     rgba[i * width + j] = 0xff000000;
                 }

             Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
             bmp.setPixels(rgba, 0 , width, 0, 0, width, height);
             return bmp;
         }
   //添加一条记录  保存图片到数据库
    public void add(PicItem item){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("BookName",item.getBookName());
        values.put("NotePicture",item.getNotePicture());
        db.insert(TBNAME,null,values);
        db.close();
    }

    //查找
    public PicItem findByBookName(String bookname){
        List<PicItem> picList=null;
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query(TBNAME,null,"BookName=?",new String[]{bookname},null,null,null );
        PicItem picItem=null;
        if(cursor!=null&&cursor.moveToNext()){
            picList=new ArrayList<PicItem>();
            picItem=new PicItem();
            picItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            picItem.setBookName(cursor.getString(cursor.getColumnIndex("BOOKNAME")));
            picItem.setNotePicture(cursor.getBlob(cursor.getColumnIndex("NOTEPICTURE")));
            picList.add(picItem);
            cursor.close();
        }
        db.close();
        return picItem;
    }

}
