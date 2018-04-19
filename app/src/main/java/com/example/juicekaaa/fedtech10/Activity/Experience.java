package com.example.juicekaaa.fedtech10.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juicekaaa.fedtech10.Activity.AR.ArActivity;
import com.example.juicekaaa.fedtech10.Activity.ArCartoon.ArExperience;
import com.example.juicekaaa.fedtech10.Assist.SystemTime;
import com.example.juicekaaa.fedtech10.Assist.WeatherBean;
import com.example.juicekaaa.fedtech10.Init.InitLocation;
import com.example.juicekaaa.fedtech10.R;
import com.example.juicekaaa.fedtech10.RestClient.WeatherRestClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Juicekaaa on 17/6/22.
 */

public class Experience extends AppCompatActivity {
    private GridView mGridView;
    private ImageView imgBack;

    List<Map<String, Object>> mList = new ArrayList<>();
    private SimpleAdapter mAdapter;

    int icon[] = new int[]{R.drawable.ar, R.drawable.game, R.drawable.xiangmu, R.drawable.history};
    String title[] = new String[]{"AR", "游戏", "项目情况", "历史沿革"};
    String detail[] = new String[]{"想要更多可能", "提供更多可能", "此时项目如何", "历史见证实力"};

    private TextView tvYear, tvWeek, tvDate, tvCity, tvWeather;
    private String year, week, day;

    //初始化定位类
    InitLocation mInitLocation;

    private Call<WeatherBean> call;

    //天气的key
    private final String KEY = "1d9914d127ec4e0796cef3259f9f783e";


    //自定义回调类
    private WeatherRestClient mRestcLient;


    private final int AR_CODE = 101;
    private final int GAME_CODE = 102;
    private final int EXPERIENCE_BACKTO_MAIN = 103;

    private int resultData = 0;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.experience);
        getSupportActionBar().hide();

        //获取系统日期
        new Thread(new Runnable() {
            @Override
            public void run() {
                year = SystemTime.Year() + "-" + SystemTime.Month();
                week = SystemTime.Way();
                day = SystemTime.Day();
                tvYear.setText(year);
                tvWeek.setText("星期" + week);
                tvDate.setText(day);

            }
        }).start();

        //初始化组件
        initView();
        mInitLocation = new InitLocation(getApplicationContext());
        //获取位置后获取城市天气
        initUtil(mInitLocation.mLocationClient.getLastKnownLocation().getCity());

        getDatas();

        mAdapter = new SimpleAdapter(this, mList, R.layout.experience_griview_item, new String[]{"icon", "title_fedtech", "detail"},
                new int[]{R.id.experience_imgicon, R.id.experience_text1, R.id.experience_text2});
        //配置适配器
        mGridView.setAdapter(mAdapter);
        myOnClickListener();
    }

    private void myOnClickListener() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Experience.this, MainActivity.class);
                intent.putExtra("ArNum", resultData);
                setResult(EXPERIENCE_BACKTO_MAIN, intent);
                finish();
            }
        });


        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(Experience.this, ArActivity.class);
                        startActivityForResult(intent, AR_CODE);
                        break;
                    case 1:
                        Intent intent1 = new Intent();
                        intent1.putExtra("game", GAME_CODE);
                        setResult(GAME_CODE, intent1);
                        finish();
                        break;
//                    case 2:
//                        Intent intent2 = new Intent(Experience.this, ArExperience.class);
//                        Experience.this.startActivity(intent2);
//                        finish();
//                        break;
//                    case 3:
//                        finish();
//                        break;
                }
            }
        });


    }

    private void initView() {
        mGridView = (GridView) findViewById(R.id.experience_gridview);
        imgBack = (ImageView) findViewById(R.id.img_experience_bottom_back);
        tvYear = (TextView) findViewById(R.id.experience_year);
        tvWeek = (TextView) findViewById(R.id.experience_week);
        tvDate = (TextView) findViewById(R.id.experience_date);
        tvCity = (TextView) findViewById(R.id.experience_city);
        tvWeather = (TextView) findViewById(R.id.experience_weather);

    }


    public List<Map<String, Object>> getDatas() {
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("icon", icon[i]);
            map.put("title_fedtech", title[i]);
            map.put("detail", detail[i]);
            mList.add(map);
        }
        return mList;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case AR_CODE:
                if (requestCode == AR_CODE) {
                    resultData = data.getIntExtra("num", 0);
                    Toast.makeText(Experience.this, "您一共收到了" + resultData + "张优惠券", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Experience.this, MainActivity.class);
            intent.putExtra("ArNum", resultData);
            setResult(EXPERIENCE_BACKTO_MAIN, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void initUtil(String myCity) {
        mRestcLient = new WeatherRestClient();
        call = mRestcLient.getmIWeatherApi().getWeather(myCity, KEY);
//        backData(myCity);

    }

    private void backData(final String myCity) {
        call.enqueue(new Callback<WeatherBean>() {
                         @Override
                         public void onResponse(Call<WeatherBean> call, Response<WeatherBean> response) {
                             final String reuslt = response.body().getHeWeather5().get(0).getNow().getTmp();
                             final String reuslt2 = response.body().getHeWeather5().get(0).getNow().getCond().getTxt();
                             setDataView(myCity, reuslt, reuslt2);
                         }

                         @Override
                         public void onFailure(Call<WeatherBean> call, Throwable t) {

                         }
                     }

        );
    }


    public void setDataView(String myCity, String reuslt, String reuslt2) {
        tvCity.setText(myCity + " ：" + reuslt + "℃");
        tvWeather.setText("户外：" + reuslt2);
        Log.e("address", mInitLocation.mLocationClient.getLastKnownLocation().getAddress());
    }

}
