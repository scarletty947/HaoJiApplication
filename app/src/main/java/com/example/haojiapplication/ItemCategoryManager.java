package com.example.haojiapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ItemCategoryManager {
    private DBHelper dbHelper;
    private String TBNAME;

    public ItemCategoryManager(Context context){
        dbHelper=new DBHelper(context);
        TBNAME=DBHelper.TB_NAME3;
    }

    //添加一个类别
    public void add(CategoryItem item){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("Category",item.getCategory());
        values.put("CategoryPicture",item.getCategoryPicture());
        db.insert(TBNAME,null,values);
        db.close();
    }

    //查找
    public CategoryItem findByCategory(String Category){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query(TBNAME,null,"Category=?",new String[]{Category},null,null,null );
        CategoryItem CategoryItem=null;
        if(cursor!=null&&cursor.moveToNext()){
            CategoryItem=new CategoryItem();
            CategoryItem.setCategory(cursor.getString(cursor.getColumnIndex("CATEGORY")));
            CategoryItem.setCategoryPicture(cursor.getBlob(cursor.getColumnIndex("CATEGORYPICTURE")));
            cursor.close();
        }
        db.close();
        return CategoryItem;
    }

    //查询所有类别
    public List<CategoryItem>listALL(){
        List<CategoryItem> CategoryList=null;
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query(TBNAME,null,null,null,null,null,null);
        if(cursor!=null){
            CategoryList=new ArrayList<CategoryItem>();
            while (cursor.moveToNext()){
                CategoryItem item=new CategoryItem();
                item.setCategory(cursor.getString(cursor.getColumnIndex("CATEGORY")));
                item.setCategoryPicture(cursor.getBlob(cursor.getColumnIndex("CATEGORYPICTURE")));//大小写也要和建表的时候对应
                CategoryList.add(item);
            }
            cursor.close();
        }
        db.close();
        return CategoryList;
    }

    //修改类别名称或封面
    //更新一条记录
    public void   update( CategoryItem item){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(" Category",item.getCategory());
        values.put(" CategoryPicture",item.getCategoryPicture());
        db.update(TBNAME,values,"Category=?",new String[]{item.getCategory()});
        db.close();
    }

    //删除一条记录
    public void  delete(String Category){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete(TBNAME,"Category=?",new String[]{Category});
        db.close();
    }
}
