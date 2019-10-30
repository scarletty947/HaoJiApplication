package com.example.haojiapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {
    private DBHelper dbHelper;
    private String TBNAME;
    public ItemManager(Context context){
        dbHelper=new DBHelper(context);
        TBNAME=DBHelper.TB_NAME2;
    }
    //添加一条记录
    public void add(BookItem item){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("BookName",item.getBookName());
        values.put("FacePicture",item.getFacePicture());
        values.put("Author",item.getAuthor());
        values.put("ReadTime",item.getReadTime());
        values.put("Note",item.getNote());
        values.put("Type",item.getType());
        db.insert(TBNAME,null,values);
        db.close();
    }
    //查询所有记录
    public List<BookItem>listALL(){
        List<BookItem> BookList=null;
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query(TBNAME,null,null,null,null,null,null);
        if(cursor!=null){
            BookList=new ArrayList<BookItem>();
            while (cursor.moveToNext()){
                BookItem item=new BookItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setBookName(cursor.getString(cursor.getColumnIndex("BOOKNAME")));
                item.setFacePicture(cursor.getBlob(cursor.getColumnIndex("FACEPICTURE")));
                item.setAuthor(cursor.getString(cursor.getColumnIndex("AUTHOR")));
                item.setReadTime(cursor.getString(cursor.getColumnIndex("READTIME")));
                item.setNote(cursor.getString(cursor.getColumnIndex("NOTE")));
                item.setType(cursor.getString(cursor.getColumnIndex("TYPE")));
                BookList.add(item);
            }
            cursor.close();
        }
        db.close();
        return BookList;
    }

    //按书名查找
    public BookItem findByBookName(String bookname){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query(TBNAME,null,"BookName=?",new String[]{bookname},null,null,null );
        BookItem BookItem=null;
        if(cursor!=null&&cursor.moveToNext()){
            BookItem=new BookItem();
            BookItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            BookItem.setBookName(cursor.getString(cursor.getColumnIndex("BOOKNAME")));
            BookItem.setFacePicture(cursor.getBlob(cursor.getColumnIndex("FACEPICTURE")));
            BookItem.setAuthor(cursor.getString(cursor.getColumnIndex("AUTHOR")));
            BookItem.setReadTime(cursor.getString(cursor.getColumnIndex("READTIME")));
            BookItem.setNote(cursor.getString(cursor.getColumnIndex("NOTE")));
            BookItem.setType(cursor.getString(cursor.getColumnIndex("TYPE")));
            cursor.close();
        }
        db.close();
        return BookItem;
    }

    //按书名和作者查找
    public BookItem findByBookNameandAuthor(String bookname,String author){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query(TBNAME,null,"BookName=? and AUTHOR=?",new String[]{bookname,author},null,null,null );
        BookItem BookItem=null;
        if(cursor!=null&&cursor.moveToNext()){
            BookItem=new BookItem();
            BookItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            BookItem.setBookName(cursor.getString(cursor.getColumnIndex("BOOKNAME")));
            BookItem.setFacePicture(cursor.getBlob(cursor.getColumnIndex("FACEPICTURE")));
            BookItem.setAuthor(cursor.getString(cursor.getColumnIndex("AUTHOR")));
            BookItem.setReadTime(cursor.getString(cursor.getColumnIndex("READTIME")));
            BookItem.setNote(cursor.getString(cursor.getColumnIndex("NOTE")));
            BookItem.setType(cursor.getString(cursor.getColumnIndex("TYPE")));
            cursor.close();
        }
        db.close();
        return BookItem;
    }

    //按书名或作者模糊查找
    public List<BookItem>  findByBookNameandAuthor2(String bookname,String author){
        List<BookItem> BookList=new ArrayList<BookItem>();
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query(TBNAME,null,"BookName LIKE? or AUTHOR LIKE?",new String[]{"%"+bookname+"%","%"+author+"%"},null,null,null );
        if(cursor!=null){
            while (cursor.moveToNext()){
                BookItem item=new BookItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setBookName(cursor.getString(cursor.getColumnIndex("BOOKNAME")));
                item.setFacePicture(cursor.getBlob(cursor.getColumnIndex("FACEPICTURE")));
                item.setAuthor(cursor.getString(cursor.getColumnIndex("AUTHOR")));
                item.setReadTime(cursor.getString(cursor.getColumnIndex("READTIME")));
                item.setNote(cursor.getString(cursor.getColumnIndex("NOTE")));
                item.setType(cursor.getString(cursor.getColumnIndex("TYPE")));
                BookList.add(item);
            }
            cursor.close();
        }
        db.close();
        return BookList;
    }


    //按类别查找所有书目
    public List<BookItem> findByBookType(String booktype){
        List<BookItem> BookList=new ArrayList<BookItem>();
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.query(TBNAME,null,"TYPE=?",new String[]{booktype},null,null,null );
        if(cursor!=null){
            while (cursor.moveToNext()){
                BookItem item=new BookItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setBookName(cursor.getString(cursor.getColumnIndex("BOOKNAME")));
                item.setFacePicture(cursor.getBlob(cursor.getColumnIndex("FACEPICTURE")));
                item.setAuthor(cursor.getString(cursor.getColumnIndex("AUTHOR")));
                item.setReadTime(cursor.getString(cursor.getColumnIndex("READTIME")));
                item.setNote(cursor.getString(cursor.getColumnIndex("NOTE")));
                item.setType(cursor.getString(cursor.getColumnIndex("TYPE")));
                BookList.add(item);
            }
            cursor.close();
        }
        db.close();
        return BookList;
    }



    //列表批量添加记录
    public void addAll(List<BookItem> list){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        for (BookItem item:list){
            ContentValues values=new ContentValues();
            values.put("BookName",item.getBookName());
            values.put("FacePicture",item.getFacePicture());
            values.put("Author",item.getAuthor());
            values.put("ReadTime",item.getReadTime());
            values.put("Note",item.getNote());
            values.put("Type",item.getType());
            db.insert(TBNAME,null,values);
        }
        db.close();
    }
    //更新一条记录
    public void  update(BookItem item){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("BookName",item.getBookName());
        values.put("FacePicture",item.getFacePicture());
        values.put("Author",item.getAuthor());
        values.put("ReadTime",item.getReadTime());
        values.put("Note",item.getNote());
        values.put("Type",item.getType());
        db.update(TBNAME,values,"BookName=? and AUTHOR=?",new String[]{item.getBookName(),item.getAuthor()});
        db.close();
    }
    //删除一条记录
    public void  delete(String bookname,String author){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete(TBNAME,"BookName=? and AUTHOR=?",new String[]{bookname,author});
        db.close();
    }
    public void  deleteByType(String type){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete(TBNAME,"TYPE=?",new String[]{type});
        db.close();
    }
    //删除全部
    public void deleteAll(){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete(TBNAME,null,null);
        db.close();
    }



}
