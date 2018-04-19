package com.example.juicekaaa.fedtech10.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.juicekaaa.fedtech10.Fragment.FragmentAfterSale;
import com.example.juicekaaa.fedtech10.Fragment.FragmentExperience;
import com.example.juicekaaa.fedtech10.Fragment.FragmentMe;
import com.example.juicekaaa.fedtech10.Fragment.FragmentNews;
import com.example.juicekaaa.fedtech10.Fragment.FragmentStore;
import com.example.juicekaaa.fedtech10.R;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {

    private FragmentNews mFragmentNews;
    private FragmentAfterSale mFragmentAfterSale;
    private FragmentStore mFragmentStore;
    private FragmentExperience mFragmentExperience;

    private FragmentMe mFragmentMe;
    private BottomNavigationBar mBottomNavigationBar;

    private int lastSelectedPosition = 0;
    private long exitTime = 0;
    public int ArNum = 0;

    private final static int GAME_CODE = 102;
    private final static int AR_CODE = 101;
    private final static int BACK_CODE = 100;
    private final static int EXPERIENCE_BACKTO_MAIN = 103;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        init();

    }

    public void back(int data) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mFragmentExperience = new FragmentExperience();
        switch (data) {
            case GAME_CODE:
                transaction.replace(R.id.content, mFragmentExperience).commit();
                break;
        }


    }


    private void init() {



        mBottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        mBottomNavigationBar.setBarBackgroundColor(R.color.white);
        mBottomNavigationBar.setInActiveColor(R.color.gray2);//未选中时的颜色
        mBottomNavigationBar.setActiveColor(R.color.red);//选中时的颜色
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

//        //设置隐藏
//        mBottomNavigationBar.isAutoHideEnabled();
//        mBottomNavigationBar.setAutoHideEnabled(true);


        mBottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.news, "新闻").setInActiveColor(R.color.black))
                .addItem(new BottomNavigationItem(R.drawable.store, "商城").setInActiveColor(R.color.black))
                .addItem(new BottomNavigationItem(R.drawable.experience, "体验").setInActiveColor(R.color.black))
                .addItem(new BottomNavigationItem(R.drawable.aftersale, "售后").setInActiveColor(R.color.black))
                .addItem(new BottomNavigationItem(R.drawable.me, "我的").setInActiveColor(R.color.black))
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise();//初始化


        mBottomNavigationBar.setTabSelectedListener(this);
        setDefaultFragment();


    }


    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.content, new FragmentNews());
        mFragmentTransaction.commit();

    }


    @Override
    public void onTabSelected(int position) {
        FragmentManager mFragmentManager = this.getSupportFragmentManager();
        //开启事务
        android.support.v4.app.FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();


        switch (position) {
            case 0:
                if (mFragmentNews == null) {
                    mFragmentNews = new FragmentNews();
                }
                mFragmentTransaction.replace(R.id.content, mFragmentNews);

                break;
            case 1:
                if (mFragmentStore == null) {
                    mFragmentStore = new FragmentStore();
                }
                mFragmentTransaction.replace(R.id.content, mFragmentStore);
                break;
            case 2:

                Intent intent = new Intent(this, Experience.class);
                startActivityForResult(intent, BACK_CODE);


                break;
            case 3:
                if (mFragmentAfterSale == null) {
                    mFragmentAfterSale = new FragmentAfterSale();
                }
                mFragmentTransaction.replace(R.id.content, mFragmentAfterSale);
                break;
            case 4:
                if (mFragmentMe == null) {
                    mFragmentMe = new FragmentMe(MainActivity.this);
                }
                mFragmentTransaction.replace(R.id.content, mFragmentMe);
            default:
                break;

        }
        mFragmentTransaction.commitAllowingStateLoss();
    }


    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int resultData;

        switch (resultCode) {
            case GAME_CODE:
                resultData = data.getIntExtra("game", 1);
                back(resultData);
                break;
            case AR_CODE:
                if (requestCode == BACK_CODE) {
                    resultData = data.getIntExtra("xiangmu", 2);
                    back(resultData);
                }
                break;
            case 3:
                if (requestCode == BACK_CODE) {
                    resultData = data.getIntExtra("history", 3);
                    back(resultData);
                }
                break;
            case EXPERIENCE_BACKTO_MAIN:
                ArNum = 0;
                setDefaultFragment();
                mBottomNavigationBar.initialise();
                resultData = data.getIntExtra("ArNum", 0);
                ArNum = resultData;
                Log.e("ARdata", String.valueOf(resultData));
                break;
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public int getArData() {
        if (ArNum != 0) {
            return this.ArNum;
        }
        return 0;
    }


}
