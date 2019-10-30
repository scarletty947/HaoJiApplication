package com.example.haojiapplication.ui.notifications;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.haojiapplication.R;

public class NotificationsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
       ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        return root;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Uri uri = Uri.parse("https://www.tuijianshu.net/portal.php?mod=list&catid=81");
//
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//
//        startActivity(intent);

        WebView webView=getView().findViewById(R.id.webview);
        webView.loadUrl("https://www.tuijianshu.net/portal.php?mod=list&catid=81");
        webView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制网页在WebView中打开，
                //为false的时候，调用系统浏览器或者第三方浏览器打开
                view.loadUrl(url);
                return false;
            }
            //webViewCilent是帮助WebView去处理一些页面控制和请求通知

            //public void onPageStarted(WebView view, String url, Bitmap favicon)
            //是处理页面开启时的操作
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

    }
}