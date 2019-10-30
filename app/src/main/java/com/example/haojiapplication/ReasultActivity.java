package com.example.haojiapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReasultActivity extends AppCompatActivity implements SwipeMenuListView.OnMenuItemClickListener, AdapterView.OnItemClickListener{
    SwipeMenuListView listView;
    String bookname,author,readtime;
    Bitmap bitmap;
    List<Map<String, Object>> listitem;
    private final String TAG ="booklist";
    BookAdapter bookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reasult);
        Intent intent=getIntent();
        String query =intent.getStringExtra("query");

        listitem = new ArrayList<Map<String, Object>>();
        listView=(SwipeMenuListView)findViewById(R.id.rel);
            SwipeMenuCreator creater = new SwipeMenuCreator() {
                @Override
                public void create(SwipeMenu menu) {
                    // create置顶item
                    SwipeMenuItem item1 = new SwipeMenuItem(getApplicationContext());
                    // set item background
                    item1.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
                    // set item width
                    item1.setWidth(dp2px(80));
                    // set item title
                    item1.setTitle("置顶");
                    // set item title fontsize
                    item1.setTitleSize(18);
                    // set item title font color
                    item1.setTitleColor(Color.WHITE);
                    // add to menu
                    menu.addMenuItem(item1);

                    //同理create删除item
                    SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                    // set item background
                    deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                    // set item width
                    deleteItem.setWidth(dp2px(80));
                    // set a icon
                    deleteItem.setIcon(R.drawable.delete);//图片不能过大，否则会顶出去
                    // add to menu
                    menu.addMenuItem(deleteItem);
                }
            };
            // set creator
        listView.setMenuCreator(creater);

            ActionBar actionBar = getSupportActionBar();
            if(actionBar != null){
                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }

            //数据库查询
            ItemManager Item_manager = new ItemManager(this);

            if(Item_manager.findByBookNameandAuthor2(query,query)==null){
                Toast.makeText(this, "这儿什么也没有哦~", Toast.LENGTH_SHORT).show();
            }
            else {
                List<BookItem> testList = Item_manager.findByBookNameandAuthor2(query,query);

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
                bookAdapter = new BookAdapter(this, R.layout.book_list_item, (ArrayList<Map<String, Object>>) listitem);
                listView.setAdapter(bookAdapter);


                //2.菜单点击事件
                listView.setOnMenuItemClickListener(this);
                listView.setOnItemClickListener(this);
            }



    }

    public int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                this.getResources().getDisplayMetrics());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String, Object>  item=listitem.get(position);
        bookname=item.get("ItemText").toString();
        author=item.get("ItemAuthor").toString();
        Log.i(TAG, "================" + bookname);
        Intent content=new Intent(this, BookContentActivity.class);
        content.putExtra("BookName",bookname);
        content.putExtra("BookAuthor",author);
        startActivity(content);
    }

    @Override
    public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
        switch (index) {
            case 0:
                //置顶的逻辑
                if (position == 0) {
                    Toast.makeText(this, "此项已经置顶", Toast.LENGTH_SHORT).show();
                    return false;
                }
                Map<String, Object>  item=listitem.get(position);
                //String str = listitem.get(position);
                for (int i = position; i > 0; i--) {
                    Map<String, Object>  s = listitem.get(i - 1);
                    listitem.remove(i);
                    listitem.add(i, s);
                }
                listitem.remove(0);
                listitem.add(0, item);
                bookAdapter.notifyDataSetChanged();
                break;
            case 1:
                //删除的逻辑
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("提示")
                        .setMessage("是否删除这本书？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Log.i(TAG,"onClick:对话框事件");
                                //删除数据项

                                Map<String, Object>  item=listitem.get(position);
                                bookname=item.get("ItemText").toString();
                                author=item.get("ItemAuthor").toString();
                                Log.i(TAG, "================" + bookname);
                                ItemManager Item_manager1 = new ItemManager(getApplicationContext());
                                Item_manager1.delete(bookname,author);
                                ItemPictureManager  pictureManager=new ItemPictureManager(getApplicationContext());
                                pictureManager.deleteByBook(bookname);
                                bookAdapter.remove(listView.getItemAtPosition(position));
                                //bookAdapter.notifyDataSetChanged();
                                //更新适配器
                                //ArryAdapter会自动调用
                            }
                        }).setNegativeButton("否",null);
                builder.create().show();
                break;
        }
        return false;
    }

}
