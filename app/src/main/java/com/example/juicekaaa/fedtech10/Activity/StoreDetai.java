package com.example.juicekaaa.fedtech10.Activity;

import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.juicekaaa.fedtech10.DiaLog.CustomDialog;
import com.example.juicekaaa.fedtech10.R;

/**
 * Created by Juicekaaa on 2017/7/5.
 */

public class StoreDetai extends AppCompatActivity {
    private ImageView imgBack, imgComment;
    private TextView tvConnect;
    private GestureDetector mGestureDetector;
    private LinearLayout linearLayout;
    private WebView webView;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_detail);
        getSupportActionBar().hide();
        initView();
        tvConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickDialogIos(view);
            }
        });


        initWebView();
        setBottom();
        myOnClick();

    }

    private void myOnClick() {

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread() {
                    public void run() {
                        try {
                            Instrumentation inst = new Instrumentation();
                            inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                        } catch (Exception e) {
                            Log.e("Exception when sendKeyDownUpSync", e.toString());
                        }
                    }
                }.start();

            }
        });

    }

    private void initWebView() {
        //声明WebSettings子类
        WebSettings webSettings = webView.getSettings();

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);


        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webView.loadUrl("http://www.asp168.com/default.php?mod=c&s=ss6d302f2");

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }

    private void setBottom() {

        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mGestureDetector.onTouchEvent(motionEvent);
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });


        mGestureDetector = new GestureDetector(getApplication(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float distanceX, float distanceY) {
                if (Math.abs(distanceY) > 10) return false;
                if (distanceY > 0) {
                    if (linearLayout.getVisibility() == View.GONE) return false;
                    linearLayout.startAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.bottom_out));
                    linearLayout.setVisibility(View.GONE);
                } else if (distanceY < 0) {
                    if (linearLayout.getVisibility() == View.VISIBLE) return false;
                    linearLayout.setVisibility(View.VISIBLE);
                    linearLayout.startAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.bottom_in));
                }
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }
        });

    }


    public void clickDialogIos(View v) {
        final CustomDialog dialog = new CustomDialog(this, R.style.customDialog, R.layout.connect_dialog);
        dialog.show();
        TextView tvCancel = (TextView) dialog.findViewById(R.id.cancel);
        TextView tvOk = (TextView) dialog.findViewById(R.id.ok);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                //url:统一资源定位符
                //uri:统一资源标示符（更广）
                String phoneNumber = "400-680-1551";
                intent.setData(Uri.parse("tel:" + phoneNumber));
                //开启系统拨号器
                startActivity(intent);
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void initView() {
        imgBack = (ImageView) findViewById(R.id.store_back);
        imgComment = (ImageView) findViewById(R.id.store_comment);
        tvConnect = (TextView) findViewById(R.id.store_connect);
        webView = (WebView) findViewById(R.id.store_webview);
        linearLayout = (LinearLayout) findViewById(R.id.layout_store_comment);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);


    }
}
