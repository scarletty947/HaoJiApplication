package com.example.haojiapplication.ui.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.haojiapplication.BookListActivity;
import com.example.haojiapplication.CategoryItem;
import com.example.haojiapplication.ItemCategoryManager;
import com.example.haojiapplication.ItemPictureManager;
import com.example.haojiapplication.MyAdapter;
import com.example.haojiapplication.Picture;
import com.example.haojiapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {
    GridView gridView;
    private final String TAG ="Gridview";
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        gridView=(GridView)getView().findViewById(R.id.gridview);
        String Category;
        Bitmap bitmap;
        ItemPictureManager manager = new ItemPictureManager(getActivity());
        //数据库获取类别数据
        ItemCategoryManager Categorymanager = new ItemCategoryManager(getActivity());
        List<CategoryItem> testList=Categorymanager.listALL();

        for (CategoryItem i:testList){
            Log.i(TAG,"onOptionItemSelected:取出数据]Name="+i.getCategory()+i.getCategoryPicture());
            Map<String, Object> map = new HashMap<String, Object>();
            Category=i.getCategory();
            if(i.getCategoryPicture()!=null)
            bitmap = BitmapFactory.decodeByteArray(i.getCategoryPicture(), 0, i.getCategoryPicture().length);
            else {
                bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.upload);
            }
           // bitmap=manager.ByteArray2Bitmap(i.getCategoryPicture(), 50,50);
            Log.i(TAG,"onOptionItemSelected:取出数据]Name="+bitmap);
            map.put("ItemImage", bitmap);
            map.put("ItemText",Category );
            listitem.add(map);
        }

        //自定义Adapter
        MyAdapter myAdapter=new MyAdapter(getActivity(),R.layout.home_fragment_item, (ArrayList<Map<String,  Object>>) listitem);
        gridView.setAdapter(myAdapter);
        //当列表没有数据显示设置
       // gridView.setEmptyView(findViewById(R.id.nodata));
//        int imageCount=9;
//        int[] mImageIds= new int[imageCount];  //图片的 数量代表当前分类的数量，后面需要存储
//        for (int i = 0; i < imageCount; i++) {
//            // getIdentifier()有三个参数，
//            //第一个为你的图片资源名称也就是 use1 等，由于我的图片是从1开始所以要加i+1
//            // 第二个为资源目录名称，PS：如果你的图片是放在mipmap目录下，可以将drawable修改为mipmap即可
//            int imageResId= getResources().getIdentifier("use" + (i + 1), "mipmap","com.example.haojiapplication");
//            //fragment没有继承Activity   在getIdentifier方法中，第三个参数应该是当前Activity的包名 这里就是该fragment属于的MainActivity的包名
//            //Log.e("ImageResID=", this.getClass().getPackage().getName());
////          将图片ID添加到数组中
//            mImageIds[i] = imageResId;
//            Log.e("ImageResID=", mImageIds[i] + " ----");
//        }
//        String[] defalt_category = new String[] {"图片", "电影","书籍","漫画","种草","爱豆","美食","音频","每日灵感"};
//        for (int i = 0; i <mImageIds.length; i++)
//            {
//                 Map<String, Object> map = new HashMap<String, Object>();
//                 map.put("ItemImage", mImageIds[i]);
//                 map.put("ItemText", defalt_category[i]);
//                 listitem.add(map);
//             }
        //创建适配器
        // 第一个参数是上下文对象
         // 第二个是listitem
         // 第三个是指定每个列表项的布局文件
         // 第四个是指定Map对象中定义的两个键（这里通过字符串数组来指定）
         // 第五个是用于指定在布局文件中定义的id（也是用数组来指定）
//        SimpleAdapter adapter = new SimpleAdapter(getActivity()
//                , listitem
//                , R.layout.home_fragment_item
//                , new String[]{"ItemImage", "ItemText"}
//                , new int[]{R.id.ItemImage, R.id.ItemText});
//        gridView.setAdapter(adapter);

        //点击事件
        gridView.setOnItemClickListener(this);
        //长按事件
        gridView.setOnItemLongClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView ItemText = view.findViewById(R.id.ItemText);
        String value = String.valueOf(ItemText.getText());
        Log.i(TAG,"onItemClick:ItemValue"+value);
        Intent content=new Intent(getActivity(), BookListActivity.class);
        content.putExtra("CategoryName",value);
//        //label[]=取全部数据库中的label
//        switch (value){
//            case "图片"://后面在数据库中取label对应的class 文件
//                change = new Intent(getActivity(), Picture.class);
//                break;
//            case "电影"://后面在数据库中取label对应的class 文件
//                change = new Intent(getActivity(), Picture.class);
//                break;
//            case "书籍"://后面在数据库中取label对应的class 文件
//                change = new Intent(getActivity(), Picture.class);
//                break;
//            case "漫画"://后面在数据库中取label对应的class 文件
//                change = new Intent(getActivity(), Picture.class);
//                break;
//            case "种草"://后面在数据库中取label对应的class 文件
//                change = new Intent(getActivity(), Picture.class);
//                break;
//            case "爱豆"://后面在数据库中取label对应的class 文件
//                change = new Intent(getActivity(), Picture.class);
//                break;
//            case "美食"://后面在数据库中取label对应的class 文件
//                change = new Intent(getActivity(), Picture.class);
//                break;
//            case "音频"://后面在数据库中取label对应的class 文件
//                change = new Intent(getActivity(), Picture.class);
//                break;
//            case "每日灵感"://后面在数据库中取label对应的class 文件
//                change = new Intent(getActivity(), Picture.class);
//                break;
//            default:break;
//        }
        startActivity(content);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }
}