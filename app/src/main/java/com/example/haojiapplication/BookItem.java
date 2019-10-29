package com.example.haojiapplication;

public class BookItem {
    private int id;
    private String BookName,Author,ReadTime,Note,Type;
    private  byte[] FacePicture;
    public BookItem(){
        super();
        BookName="";
        Author="";
        ReadTime="";
        Note="";
        Type="";
        FacePicture=null;
    }
    public BookItem(String BookName, byte[] FacePicture,String Author,String ReadTime,String Note){
        super();
        this.BookName=BookName;
        this.FacePicture=FacePicture;
        this.Author=Author;
        this.ReadTime=ReadTime;
        this.Note=Note;
        this.Type=Type;
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
    public byte[] getFacePicture() {
        return FacePicture;
    }
    public void setFacePicture(byte[] FacePicture) {
        this.FacePicture =FacePicture;
    }
    public  String getAuthor(){
        return  Author;
    }
    public void setAuthor(String Author) {
        this.Author=Author;
    } public  String getReadTime(){
        return  ReadTime;
    }
    public void setReadTime(String ReadTime) {
        this.ReadTime=ReadTime;
    }
    public  String getNote(){
        return  Note;
    }
    public void setNote(String Note) {
        this.Note=Note;
    }
    public  String getType(){
        return  Type;
    }
    public void setType(String Type ) {
        this.Type=Type;
    }

}
