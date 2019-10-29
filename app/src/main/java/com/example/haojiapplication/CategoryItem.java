package com.example.haojiapplication;

public class CategoryItem {
    private String Category;
    private  byte[] CategoryPicture;

    public CategoryItem(){
        super();
        Category="";
        CategoryPicture=null;
    }
    public CategoryItem(String Category, byte[] CategoryPicture){
        super();
        this.Category=Category;
        this.CategoryPicture=CategoryPicture;
    }
    public  String getCategory(){
        return  Category;
    }
    public void setCategory(String Category) {
        this. Category = Category;
    }
    public byte[] getCategoryPicture() {
        return CategoryPicture;
    }
    public void setCategoryPicture(byte[] CategoryPicture) {
        this.CategoryPicture =CategoryPicture;
    }
}
