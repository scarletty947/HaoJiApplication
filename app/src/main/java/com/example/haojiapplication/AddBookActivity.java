package com.example.haojiapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;

public class AddBookActivity extends AppCompatActivity implements  View.OnClickListener {
    Button yes,cancel;
    ImageView CfImage;
    EditText Bname,Bahtor,Btime;
    Bitmap bitmap;
    String categoryName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        Bname = findViewById(R.id.editText2);
        Bahtor= findViewById(R.id.editText3);
        Btime= findViewById(R.id.editText4);
        yes= findViewById(R.id.yes2);
        cancel = findViewById(R.id.cancel2);
        CfImage = findViewById(R.id.imageView3);

        Intent category=getIntent();
        categoryName=category.getStringExtra("CategoryName");

        CfImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, 1);
                } catch (ActivityNotFoundException e) {
                }
            }
        });

    }
    @Override
    public void onClick(View v) {
        String bookName = Bname.getText().toString();
        String bookAuthor = Bahtor.getText().toString();
        String bookTime = Btime.getText().toString();
        ItemManager manager = new ItemManager(this);
        BookItem item = new BookItem();

        if(manager.findByBookNameandAuthor(bookName,bookAuthor)!=null){
            Toast.makeText(this, "这本书已经存在了哦~", Toast.LENGTH_SHORT).show();
        }
        else {
            item.setType(categoryName);
            item.setBookName(bookName);
            item.setAuthor(bookAuthor);
            item.setReadTime(bookTime);
            ItemPictureManager picmanager = new ItemPictureManager(this);
            item.setType(categoryName);
            if (bitmap != null) {
                Log.i("bimap", "onItemClick:ItemValue" + bitmap);
                Log.i("bipmap", "onItemClick:ItemValue" + picmanager.img2byte(bitmap));
                item.setFacePicture(picmanager.img2byte(bitmap));
            } else {
                Toast.makeText(this, "未选择封面，将使用默认封面~", Toast.LENGTH_SHORT).show();
            }
            manager.add(item);
            Intent seccess = new Intent(this, BookListActivity.class);
            seccess.putExtra("CategoryName", categoryName);
            startActivity(seccess);
        }


    }
    public void onClick1(View v) {
        Intent sback=new Intent(this,BookListActivity.class);
        sback.putExtra("CategoryName",categoryName);
        startActivity(sback);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //用户操作完成，结果码返回是-1，即RESULT_OK
        if (resultCode == RESULT_OK) {
            //获取选中文件的定位符
            Uri uri = data.getData();
            Log.e("uri", uri.toString());
            //使用content的接口
            ContentResolver cr = this.getContentResolver();
            try {
                //获取图片
                bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                CfImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(), e);
            }
        } else {
            //操作错误或没有选择图片
            Log.i("MainActivtiy", "operation error");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
