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
             bitmap.compress(Bitmap.CompressFormat.PNG, 10, baos);
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
    //删除一条记录
    public void  delete(int id){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete(TBNAME,"ID=? ",new String[]{String.valueOf(id)});
        db.close();
    }
    //删除一本书
    public void  deleteByBook(String bookname){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete(TBNAME,"BOOKNAME=? ",new String[]{bookname});
        db.close();
    }
    //查找id
    public List<PicItem>  findByBookName(String bookname){
        List<PicItem> picList=null;
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query(TBNAME,null,"BOOKNAME=?",new String[]{bookname},null,null,null );
        PicItem picItem;
        if(cursor!=null){
            picList=new ArrayList<PicItem>();
            while (cursor.moveToNext()){
                picItem=new PicItem();
                picItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                picItem.setBookName(cursor.getString(cursor.getColumnIndex("BOOKNAME")));
                picItem.setNotePicture(cursor.getBlob(cursor.getColumnIndex("NOTEPICTURE")));
                picList.add(picItem);
            }
            cursor.close();
        }
        db.close();
        return picList;
    }


    //查询所有类别
    public List<PicItem>listALL(){
        List<PicItem> PicItemList=null;
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query(TBNAME,null,null,null,null,null,null);
        if(cursor!=null){
            PicItemList=new ArrayList<PicItem>();
            while (cursor.moveToNext()){
                PicItem item=new PicItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setBookName(cursor.getString(cursor.getColumnIndex("BOOKNAME")));
                item.setNotePicture(cursor.getBlob(cursor.getColumnIndex("NOTEPICTURE")));//大小写也要和建表的时候对应
                PicItemList.add(item);
            }
            cursor.close();
        }
        db.close();
        return PicItemList;
    }

    //删除全部
    public void deleteAll(){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete(TBNAME,null,null);
        db.close();
    }
}
