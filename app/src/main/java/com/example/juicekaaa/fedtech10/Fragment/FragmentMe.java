package com.example.juicekaaa.fedtech10.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juicekaaa.fedtech10.Activity.PersonalPage;
import com.example.juicekaaa.fedtech10.Fragment.MeListFragment.LeftTabFragment;
import com.example.juicekaaa.fedtech10.Fragment.MeListFragment.RightTabFragment;
import com.example.juicekaaa.fedtech10.MyAdapter.MeFragmentAdapter;
import com.example.juicekaaa.fedtech10.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juicekaaa on 17/6/13.
 */

public class FragmentMe extends Fragment {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FragmentStatePagerAdapter mFragmentStatePagerAdapter;//定义adapter
    private View view;
    private List<Fragment> mListFragment;                            //定义要装fragment的列表
    private List<String> titles;                                     //tab名称列表
    private ImageView imgHead;
    private TextView tvName, tvProvince, tvCity;
    private LeftTabFragment mLeftTabFragment;
    private RightTabFragment mRightTabFragment;
    Context context;
    private final int PERSON_BACK_CODE = 99;
    private final int PERSON_ERROR_CODE = -1;

    public FragmentMe(Context context) {
        this.context = context;
    }

    private SharedPreferences sharedPreferences;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_me, container, false);
            initViewPager();
            sharedPreferences = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
            readSharePerferences();
            return view;
        }
        return view;
    }

    private void initViewPager() {

        initview();
        setMyOnClistener();

        //初始化fragment
        mLeftTabFragment = new LeftTabFragment();
        mRightTabFragment = new RightTabFragment();

        //将fragment装进列表中
        mListFragment = new ArrayList<>();
        mListFragment.add(mLeftTabFragment);
        mListFragment.add(mRightTabFragment);


        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        titles = new ArrayList<>();
        titles.add("我的消息");
        titles.add("我的卡劵");
        //为TabLayout添加tab名称
        for (int i = 0; i < titles.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(i)));
        }

        //设置TabLayout的模式
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mFragmentStatePagerAdapter = new MeFragmentAdapter(getActivity().getSupportFragmentManager(), mListFragment, titles);
        //viewpager加载adapter
        mViewPager.setAdapter(mFragmentStatePagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

    }

    private void initview() {

        mTabLayout = (TabLayout) view.findViewById(R.id.me_tablelayout);
        mViewPager = (ViewPager) view.findViewById(R.id.me_viewpager);
        imgHead = (ImageView) view.findViewById(R.id.me_header);
        tvProvince = (TextView) view.findViewById(R.id.tv_me_province);
        tvCity = (TextView) view.findViewById(R.id.tv_me_city);
        tvName = (TextView) view.findViewById(R.id.tv_me_name);
    }

    private void setMyOnClistener() {
        imgHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PersonalPage.class);
                startActivityForResult(intent, PERSON_BACK_CODE);
            }


        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {

            case PERSON_ERROR_CODE:
                Toast.makeText(getActivity(), "设置失败哦,缺一不可", Toast.LENGTH_SHORT).show();
                break;

            case PERSON_BACK_CODE:
                readSharePerferences();
                break;

        }


    }


    public void readSharePerferences() {
        boolean isRemember = sharedPreferences.getBoolean("remember", false);
        if (isRemember) {
            tvCity.setText(sharedPreferences.getString("city", "null"));
            tvProvince.setText(sharedPreferences.getString("province", "null"));
            tvName.setText(sharedPreferences.getString("name", "null"));
            ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decode(sharedPreferences.getString("head", "").getBytes(), Base64.DEFAULT));
            imgHead.setImageDrawable(Drawable.createFromStream(bais, "null"));
        }

    }

}
