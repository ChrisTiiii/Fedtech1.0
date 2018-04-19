package com.example.juicekaaa.fedtech10.Fragment.MeListFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.juicekaaa.fedtech10.Activity.MainActivity;
import com.example.juicekaaa.fedtech10.FragmentViewHolder.MeRightViewHolder;
import com.example.juicekaaa.fedtech10.MyAdapter.MeRightAdapter;
import com.example.juicekaaa.fedtech10.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juicekaaa on 17/6/16.
 */

public class RightTabFragment extends android.support.v4.app.Fragment {

    private View view;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MeRightAdapter mAdapter;
    List<MeRightViewHolder> mData = new ArrayList<>();

    int icon = R.drawable.juan3;

    String address = "连邦网站建设产品售后";
    private int ArNum = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_me_right_list, container, false);
            mRecyclerView = (RecyclerView) view.findViewById(R.id.me_right_recyclerView);
            initData();
            initView(view);
            return view;
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        ArNum = ((MainActivity) getActivity()).getArData();
        Log.e("FragmentMeRightAR", String.valueOf(ArNum));
        if (getArData() != null) {
            mData.addAll(0,getArData());
//            mData.addAll(getArData());
            mAdapter.notifyDataSetChanged();
        }

    }


    private void initData() {
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mData.addAll(getData());
        mAdapter = new MeRightAdapter(getActivity(), mData);
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.me_right_recyclerView);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new com.example.juicekaaa.fedtech10.RecyclerDivider.DividerItemDecoration(getActivity(), LinearLayoutManager.HORIZONTAL,
                5, ContextCompat.getColor(getActivity(), R.color.gray2

        )));


    }


    public List<MeRightViewHolder> getData() {


        List<MeRightViewHolder> data = new ArrayList<>();
        String title[] = {"免费1年售后服务卡", "免费2年售后服务卡", "免费3年售后服务卡", "免费3年售后服务卡", "免费3年售后服务卡"};
        int year[] = {2017, 2016, 2018, 2032, 2017}, month[] = {1, 4, 6, 2, 2}, date[] = {3, 22, 12, 12, 23
        };
        for (int i = 0; i < 3; i++) {
            data.add(new MeRightViewHolder(icon, title[i], year[i] + "-" + month[i] + "-" + date[i], address));

        }

        return data;
    }

    public List<MeRightViewHolder> getArData() {
        List<MeRightViewHolder> data = new ArrayList<>();

        String title = "研发部送您一张劵";
        int year = 2020, month = 9, date = 1;
        for (int i = 0; i < ArNum; i++) {
            data.add(new MeRightViewHolder(icon, title, year + "-" + month + "-" + date, address));

        }

        return data;

    }


}
