package com.example.haojiapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookContentActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {
    String bookname,author,readtime,note;
    BookItem bookItem;
    Bitmap bitmap,newbitmap;
    NoteAdapter Adapter;
    ImageButton edit,save,upload;
    EditText NOTE;
    ItemManager Item_manager;
    MyGridView gridView;
    List<Map<String, Object>> listitem;
    ItemPictureManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_content);

        Intent content=getIntent();
        bookname=content.getStringExtra("BookName");
        author=content.getStringExtra("BookAuthor");

        //数据库获取书目详情
        Item_manager = new ItemManager(this);
        bookItem=Item_manager.findByBookNameandAuthor(bookname,author);

        readtime=bookItem.getReadTime();
        note=bookItem.getNote();

        if (bookItem.getFacePicture() != null)
            bitmap = BitmapFactory.decodeByteArray(bookItem.getFacePicture(), 0, bookItem.getFacePicture().length);
        else {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.upload);
        }

        ImageView facepicture=findViewById(R.id.imageView4);
        TextView name=findViewById(R.id.textView);
        TextView Tauthor=findViewById(R.id.textView2);
        TextView time=findViewById(R.id.textView3);
        NOTE=findViewById(R.id.editText);
        edit=findViewById(R.id.Edit);
        save=findViewById(R.id.imageButton2);
        upload=findViewById(R.id.image);

        facepicture.setImageBitmap(bitmap);
        name.setText(bookname);
        Tauthor.setText(author);
        time.setText(readtime);
        if(note!=null)
        NOTE.setText(note);

        listitem = new ArrayList<Map<String, Object>>();
        gridView=(MyGridView)findViewById(R.id.gridview2);
        manager= new ItemPictureManager(this);
       // manager.deleteAll();
        List<PicItem> testList=manager.listALL();
        if(testList!=null) {
            for (PicItem i : testList) {
                if(i.getBookName().equals(bookname)) {
                    bitmap = BitmapFactory.decodeByteArray(i.getNotePicture(), 0, i.getNotePicture().length);
                    Log.i("LLllllllllllllllllll", "onOptionItemSelected:" + bitmap);
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("ItemID", i.getId());
                    map.put("ItemImage", bitmap);
                    listitem.add(map);
                }
            }
            Adapter = new NoteAdapter(this, R.layout.note_picture_item, (ArrayList<Map<String, Object>>) listitem);
            gridView.setAdapter(Adapter);

        }
        else{
            Log.i("LLllllllllllllllllll", "你好像没有图片笔记~" + bitmap);
            Toast.makeText(this, "你好像没有图片笔记~", Toast.LENGTH_SHORT).show();
        }
        gridView.setOnItemClickListener(this);
        //长按事件
        gridView.setOnItemLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Edit:

                NOTE.setFocusableInTouchMode(true);
                NOTE.setFocusable(true);
                NOTE.setCursorVisible(true);
                NOTE.requestFocus();
                break;
            case R.id.imageButton2:
                bookItem.setNote(NOTE.getText().toString());
                Item_manager.update(bookItem);
                NOTE.setText(NOTE.getText().toString());
                NOTE.setFocusableInTouchMode(false);
                NOTE.setFocusable(false);
                break;
            case R.id.image:
                try {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, 1);
                } catch (ActivityNotFoundException e) {
                }
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //用户操作完成，结果码返回是-1，即RESULT_OK
        if (resultCode == RESULT_OK) {
            //获取选中文件的定位符
            Uri uri = data.getData();
            Log.e("uri", uri.toString());
            //使用content的接口
            ContentResolver cr = this.getContentResolver();
            PicItem picItem=new PicItem();
            try {
                //获取图片
                newbitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                picItem.setBookName(bookname);
                if (newbitmap != null) {
                    Log.i("bimap", "onItemClick:ItemValue" + newbitmap);
                    Log.i("bipmap", "onItemClick:ItemValue" + manager.img2byte(newbitmap));
                    newbitmap=compress.compressScale(newbitmap);
                    picItem.setNotePicture(manager.img2byte(newbitmap));
                    manager.add(picItem);
                } else {
                    Toast.makeText(this, "你好像没有选择新的图片笔记欸~", Toast.LENGTH_SHORT).show();
                }
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(), e);
            }
        } else {
            //操作错误或没有选择图片
            Log.i("MainActivtiy", "operation error");
        }
        //刷新当前页面
        Intent content=new Intent(this, Picture.class);
        content.putExtra("BookName",bookname);
        content.putExtra("BookAuthor",author);
        startActivity(content);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Dialog dialog = new Dialog(this);//不能使用AlertDialog
        dialog.setContentView(R.layout.dialog_layout);
        ImageView imageView = (ImageView) dialog.findViewById(R.id.imageView7);
        Map<String, Object>  item=listitem.get(position);
        bitmap=(Bitmap)item.get("ItemImage");
        Log.i("bimap", "onItemClick:ItemValue" + bitmap);
        imageView .setImageBitmap((Bitmap)item.get("ItemImage"));
        dialog.show();
        // 点击图片消失
        dialog.setCanceledOnTouchOutside(true);//点击可以退出
        imageView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


}

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final  int position, long id) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("提示")
                .setMessage("是否删除这张笔记？")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //删除数据项
                        Map<String, Object>  item=listitem.get(position);
                        manager.delete((int)item.get("ItemID"));
                        Adapter.remove(gridView.getItemAtPosition(position));
                        //更新适配器
                        //ArryAdapter会自动调用
                    }
                }).setNegativeButton("否",null);
        builder.create().show();
        return false;
    }
}
