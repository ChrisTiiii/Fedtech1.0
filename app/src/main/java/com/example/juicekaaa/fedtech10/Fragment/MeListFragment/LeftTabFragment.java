package com.example.juicekaaa.fedtech10.Fragment.MeListFragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.juicekaaa.fedtech10.Activity.MainActivity;
import com.example.juicekaaa.fedtech10.FragmentViewHolder.MeLeftViewHolder;
import com.example.juicekaaa.fedtech10.MyAdapter.MeLeftAdapter;
import com.example.juicekaaa.fedtech10.OnclickListener.RecyclerItemClickListener;
import com.example.juicekaaa.fedtech10.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juicekaaa on 17/6/16.
 */

public class LeftTabFragment extends android.support.v4.app.Fragment {
    private View view;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MeLeftAdapter mAdapter;
    List<MeLeftViewHolder> mData = new ArrayList<>();
    private int ArNum = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        if (view == null) {
            view = inflater.inflate(R.layout.fragment_me_left_list, container, false);
            mRecyclerView = (RecyclerView) view.findViewById(R.id.me_left_recyclerView);
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
        if (getNews() != null) {
            mData.addAll(0, getNews());
            mAdapter.notifyDataSetChanged();
        }
    }

    private void initData() {
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mData.addAll(getData());
        mAdapter = new MeLeftAdapter(getActivity(), mData);
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.me_left_recyclerView);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new com.example.juicekaaa.fedtech10.RecyclerDivider.DividerItemDecoration(getActivity(), LinearLayoutManager.HORIZONTAL,
                5, ContextCompat.getColor(getActivity(), R.color.gray2)));
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "click 了" + (position+1) + "消息", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        }));

    }


    public List<MeLeftViewHolder> getData() {

        int icon[] = new int[]{R.drawable.juan, R.drawable.juan2};
        String title[] = {"您收到一张免费的年售后卡", "您的一张限时收费卡即将过期", "您收到一张免费的年售后卡"};
        String rule = "1.该劵仅限于连邦服务产品";
        int year[] = {2017, 2016, 2018}, month[] = {1, 4, 6}, date[] = {3, 22, 12};
        List<MeLeftViewHolder> data = new ArrayList<>();
        for (int i = 0; i < title.length; i++) {
            if (i % 2 == 0)
                data.add(new MeLeftViewHolder(icon[0], title[i], rule, "2.本劵最晚于" + year[i] + "-" + month[i] + "-" + date[i] + " 日前使用，过期无效 ", "2小时前"));
            else
                data.add(new MeLeftViewHolder(icon[1], title[i], rule, "2.本劵最晚于" + year[i] + "-" + month[i] + "-" + date[i] + " 日前使用，过期无效 ", "3小时前"));

        }

        return data;
    }

    public List<MeLeftViewHolder> getNews() {
        if (ArNum != 0) {
            int icon[] = new int[]{R.drawable.juan, R.drawable.juan2};
            String title = "来自研发部的优惠券";
            String rule = "1.该劵仅限于连邦服务产品";
            int year = 2020, month = 9, date = 1;
            List<MeLeftViewHolder> data = new ArrayList<>();
            for (int i = 0; i < ArNum; i++) {
                if (i % 2 == 0)
                    data.add(new MeLeftViewHolder(icon[0], title, rule, "2.本劵最晚于" + year + "-" + month + "-" + date + " 日前使用，过期无效 ", "刚刚"));
                else
                    data.add(new MeLeftViewHolder(icon[1], title, rule, "2.本劵最晚于" + year + "-" + month + "-" + date + " 日前使用，过期无效 ", "刚刚"));

            }

            return data;
        }

        return null;
    }


}
