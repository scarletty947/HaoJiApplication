package com.example.haojiapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookListActivity extends AppCompatActivity {
    ListView booklist;
    String category,bookname,author,readtime;
    Bitmap bitmap;

    private final String TAG ="booklist";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        booklist=(ListView)findViewById(R.id.booklist);

        Intent Category=getIntent();
        //intent获取参数
       category=Category.getStringExtra("CategoryName");

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

       //数据库获取列表数据

        ItemPictureManager manager = new ItemPictureManager(this);
        //数据库获取类别数据
        ItemManager Item_manager = new ItemManager(this);
        if(Item_manager.findByBookType(category)==null){
            Toast.makeText(this, "这儿什么也没有哦~", Toast.LENGTH_SHORT).show();
        }
        else {
            List<BookItem> testList = Item_manager.findByBookType(category);

            for (BookItem i : testList) {
                Log.i(TAG, "onOptionItemSelected:取出数据]Name=" + i.getBookName() + i.getFacePicture());
                Map<String, Object> map = new HashMap<String, Object>();
                bookname = i.getBookName();
                author = i.getAuthor();
                readtime = i.getReadTime();
                if (i.getFacePicture() != null)
                    bitmap = BitmapFactory.decodeByteArray(i.getFacePicture(), 0, i.getFacePicture().length);
                else {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.upload);
                }
                // bitmap=manager.ByteArray2Bitmap(i.getCategoryPicture(), 50,50);
                Log.i(TAG, "onOptionItemSelected:取出数据]Name=" + bitmap);
                map.put("ItemImage", bitmap);
                map.put("ItemText", bookname);
                map.put("ItemAuthor", author);
                map.put("ItemReadTime", readtime);
                listitem.add(map);
            }
            //自定义Adapter
            BookAdapter bookAdapter = new BookAdapter(this, R.layout.book_list_item, (ArrayList<Map<String, Object>>) listitem);
            booklist.setAdapter(bookAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_new_book,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
               this.finish();
                break;
            case R.id.addNewBook://监听菜单按钮
                Intent content=new Intent(this, AddBookActivity.class);
                content.putExtra("CategoryName",category);
                startActivity(content);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
