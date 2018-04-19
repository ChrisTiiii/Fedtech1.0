package com.example.juicekaaa.fedtech10.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.juicekaaa.fedtech10.FragmentViewHolder.NewsViewHolder;
import com.example.juicekaaa.fedtech10.MyAdapter.NewsAdapter;
import com.example.juicekaaa.fedtech10.R;
import com.example.juicekaaa.fedtech10.RecyclerDivider.DividerItemDecoration;
import com.example.juicekaaa.fedtech10.RestClient.NewsClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Juicekaaa on 17/6/13.
 */

public class FragmentNews extends android.support.v4.app.Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private NewsAdapter mAdapter;
    private View view;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private final static String REFRESH = "REFRESH";
    private LinearLayoutManager linearLayoutManager;


    Handler handler = new Handler();

    List<NewsViewHolder> dataList = new ArrayList<>();

    private int lastVisibleItem;

    private NewsClient newsClient;
    private Call<NewsViewHolder> call;

    private final int TOP_REFRESH = 1;
    private final int BOTTOM_REFRESH = 2;
    private int page = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {//优化View减少View的创建次数
            view = inflater.inflate(R.layout.fragment_news, container, false);
//接口数据
//            initUitilData(page);
// 模拟数据
            initData();
            initView(view);
            mRecyclerView.setAdapter(mAdapter);
            return view;
        }

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refresh();
        rollRefresh();
    }


    public void initData() {
        dataList.addAll(getData());
        mAdapter = new NewsAdapter(getActivity(), dataList);

    }

    private void initView(View view) {


        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.news_recyclerView);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.HORIZONTAL,
                35, ContextCompat.getColor(getActivity(), R.color.gray2)));

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.news_swiperefreshlayout);
        //设置刷新时动画的颜色，可以设置4个
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);


    }

    private void refresh() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            //在此处实现刷新获取数据，然后更新RecyclerView的数据源即可

            @Override
            public void onRefresh() {
                Log.e(REFRESH, REFRESH);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (getActivity() != null) {

                            dataOption(TOP_REFRESH);

                            dataList.clear();
                            dataList.addAll(getNewData());
                            dataList.addAll(getData());
                            mAdapter.notifyDataSetChanged();
                            mSwipeRefreshLayout.setRefreshing(false);
                            Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 3000);
            }
        });
    }


    private void rollRefresh() {
        //RecyclerView滑动监听
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.setLoadStatus(NewsAdapter.LoadStatus.LOADING_MORE);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(1000);
                                        Log.e("getActivity", String.valueOf(getActivity()));
                                        if (getActivity() != null) {
                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
//                                                    dataOption(BOTTOM_REFRESH);


                                                    mAdapter.setLoadStatus(NewsAdapter.LoadStatus.CLICK_LOAD_MORE);
                                                    dataList.addAll(getData());
                                                    mAdapter.notifyDataSetChanged();
                                                    Toast.makeText(getActivity(), "更新了2条数据...", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        } else onPause();

                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }).start();
                        }
                    }, 1000);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }


    private ArrayList<NewsViewHolder> getData() {
        ArrayList<NewsViewHolder> data = new ArrayList<>();
        data.clear();
        String temp1 = "新华日报";
        String temp2 = "时代周刊";
        for (int i = 0; i < 2; i++) {
            if (i % 2 != 0) {
                data.add(new NewsViewHolder(temp1, "百色公安项目第三周完成详情", "FREDTECH", "我们将项目的时间分为5个阶段（Phase），在不影响", "3小时", R.drawable.index_img_head, R.drawable.news_photo, 0));
            } else
                data.add(new NewsViewHolder(temp2, "百色公安项目第二周完成详情", "周敏", "我们将项目的时间分为5个阶段（Phase），在不影响", "2小时", R.drawable.index_img_head, R.drawable.news_photo2, 0));
        }

        return data;
    }


    private ArrayList<NewsViewHolder> getNewData() {

        ArrayList<NewsViewHolder> newDatas = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            newDatas.add(new NewsViewHolder("项目报导", "南京民政项目第三周完成详情", "销售", "我们将项目的时间分为5个阶段（Phase），在不影响", "刚刚", R.drawable.index_img_head, R.drawable.news_photo, 0));
        }

        return newDatas;
    }


    @Override
    public void onPause() {
        super.onPause();
        if (getNewData() != null) {
            mAdapter.setLoadStatus(NewsAdapter.LoadStatus.CLICK_LOAD_MORE);
            dataList.addAll(getNewData());
            mAdapter.notifyDataSetChanged();
        }

    }


    public void initUitilData(int page) {
        newsClient = new NewsClient();
        call = newsClient.getiNewsApi().getNews(5, page);
        call.enqueue(new Callback<NewsViewHolder>() {
            @Override
            public void onResponse(Call<NewsViewHolder> call, Response<NewsViewHolder> response) {
                dataList.addAll(response.body().mList);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsViewHolder> call, Throwable t) {
                Log.e("News接口回调:", String.valueOf(t));
            }
        });


    }


    public void dataOption(int option) {
        switch (option) {
            case TOP_REFRESH:
                dataList.clear();
                initUitilData(1);
                break;
            case BOTTOM_REFRESH:
                page++;
                initUitilData(page);
                break;
        }

    }

}
