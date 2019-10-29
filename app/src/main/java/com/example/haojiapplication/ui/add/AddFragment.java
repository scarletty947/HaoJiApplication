package com.example.haojiapplication.ui.add;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haojiapplication.CategoryItem;
import com.example.haojiapplication.ItemCategoryManager;
import com.example.haojiapplication.ItemPictureManager;
import com.example.haojiapplication.MainActivity;
import com.example.haojiapplication.PicItem;
import com.example.haojiapplication.R;
import com.example.haojiapplication.ui.dashboard.DashboardViewModel;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class AddFragment extends Fragment{

    Button yes1,cancel1;
    ImageView CfImage;
    TextView Cname;
    Bitmap bitmap;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_fragment, container, false);
        return root;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Cname = getView().findViewById(R.id.Cname);
        yes1 = getView().findViewById(R.id.yes1);
        cancel1 = getView().findViewById(R.id.cancel1);
        CfImage = getView().findViewById(R.id.imageView2);

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


        yes1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String categoryName = Cname.getText().toString();
                ItemCategoryManager Categorymanager = new ItemCategoryManager(getActivity());
                if (Categorymanager.findByCategory(categoryName) != null) {
                    Toast.makeText(getActivity(), "该类别已存在，可前往首页修改~", Toast.LENGTH_SHORT).show();
                } else {
                    CategoryItem item = new CategoryItem();
                    ItemPictureManager manager = new ItemPictureManager(getActivity());
                    item.setCategory(categoryName);
                    if (bitmap != null) {
                        Log.i("bimap", "onItemClick:ItemValue" + bitmap);
                        Log.i("bipmap", "onItemClick:ItemValue" + manager.img2byte(bitmap));
                        item.setCategoryPicture(manager.img2byte(bitmap));
                    } else {
                        Toast.makeText(getActivity(), "未选择封面，将使用默认封面~", Toast.LENGTH_SHORT).show();
                    }
                    Categorymanager.add(item);
                    Intent seccess=new Intent(getActivity(),MainActivity.class);
                    startActivity(seccess);
                }
            }
        });

        cancel1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent search=new Intent(getActivity(), MainActivity.class);
                startActivity(search);
            }
        });

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //用户操作完成，结果码返回是-1，即RESULT_OK
        if (resultCode == RESULT_OK) {
            //获取选中文件的定位符
            Uri uri = data.getData();
            Log.e("uri", uri.toString());
            //使用content的接口
            ContentResolver cr = getActivity().getContentResolver();
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
