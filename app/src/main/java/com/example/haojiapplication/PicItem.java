package com.example.haojiapplication;
//创建实体对象
public class PicItem {
    private int id;
    private String BookName;
    private  byte[] NotePicture;

    public PicItem(){
        super();
        BookName="";
        NotePicture=null;
    }
    public PicItem(String BookName, byte[] NotePicture){
        super();
        this.BookName=BookName;
        this.NotePicture=NotePicture;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
    public  String getBookName(){
        return  BookName;
    }
    public void setBookName(String BookName) {
        this. BookName = BookName;
    }
    public byte[] getNotePicture() {
        return NotePicture;
    }
    public void setNotePicture(byte[] NotePicture) {
        this.NotePicture =NotePicture;
    }
}
