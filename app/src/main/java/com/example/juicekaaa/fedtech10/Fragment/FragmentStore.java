package com.example.juicekaaa.fedtech10.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.juicekaaa.fedtech10.Activity.StoreDetai;
import com.example.juicekaaa.fedtech10.FragmentViewHolder.StoreViewHolder;
import com.example.juicekaaa.fedtech10.MyAdapter.StoreAdapter;
import com.example.juicekaaa.fedtech10.OnclickListener.RecyclerItemClickListener;
import com.example.juicekaaa.fedtech10.R;

import java.util.ArrayList;

/**
 * Created by Juicekaaa on 17/6/13.
 */

public class FragmentStore extends android.support.v4.app.Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private StoreAdapter mAdapter;
    private View view;
    private int photo[] = new int[]{R.drawable.police, R.drawable.procuratorate, R.drawable.party, R.drawable.government, R.drawable.army};

    private String title[] = new String[]{
            "智慧公安", "智慧检察院", "智慧党建", "智慧政务", "智慧军队"
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_store, container, false);
            initData();
            initView(view);
            return view;
        }

        return view;
    }

    private void initData() {
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new StoreAdapter(getActivity(), getData());


    }


    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.store_recyclerView);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        //设置底线
//        mRecyclerView.addItemDecoration(new com.example.juicekaaa.fedtech10.RecyclerDivider.DividerItemDecoration(getActivity(), LinearLayoutManager.HORIZONTAL,
//                20, ContextCompat.getColor(getActivity(), R.color.gray2)));
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), StoreDetai.class);
                getActivity().startActivity(intent);

//                Toast.makeText(getActivity(), "click 了" + title[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
//                Toast.makeText(getActivity(), "click " + title_fedtech[position], Toast.LENGTH_SHORT).show();
            }
        }));


    }

    private ArrayList<StoreViewHolder> getData() {
        ArrayList<StoreViewHolder> data = new ArrayList<>();
        for (int i = 0; i < title.length; i++) {
            data.add(new StoreViewHolder(title[i], "欢迎联系" + title[i] + "客户经理", photo[i]));

        }
        return data;
    }

}
