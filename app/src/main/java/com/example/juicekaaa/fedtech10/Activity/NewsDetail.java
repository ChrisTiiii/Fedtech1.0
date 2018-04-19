package com.example.juicekaaa.fedtech10.Activity;

import android.app.Instrumentation;
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

import com.example.juicekaaa.fedtech10.R;

/**
 * Created by Juicekaaa on 2017/7/4.
 */

public class NewsDetail extends AppCompatActivity {
    private WebView webView;
    private GestureDetector mGestureDetector;
    private LinearLayout linearLayout;
    private ImageView imgBack, imgComment, imgSport, imgShare;

    int isSport = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);
        getSupportActionBar().hide();
        initView();
        initWebView();
        setBottom();
        setMyOnClick();



    }

    private void setMyOnClick() {
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


        imgSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSport == -1) {
                    imgSport.setImageResource(R.drawable.sport2);
                    isSport = 0;
                } else {
                    imgSport.setImageResource(R.drawable.sport);
                    isSport = -1;
                }

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
        webView.loadUrl("http://www.asp168.com/default.php");

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

    private void initView() {
        webView = (WebView) findViewById(R.id.news_web);
        linearLayout = (LinearLayout) findViewById(R.id.layout_news);
        imgBack = (ImageView) findViewById(R.id.news_back);
        imgComment = (ImageView) findViewById(R.id.news_comment);
        imgSport = (ImageView) findViewById(R.id.news_sport);
        imgShare = (ImageView) findViewById(R.id.news_share);

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }




}
