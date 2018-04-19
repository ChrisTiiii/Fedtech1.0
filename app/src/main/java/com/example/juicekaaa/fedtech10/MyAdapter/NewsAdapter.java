package com.example.juicekaaa.fedtech10.MyAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.juicekaaa.fedtech10.Activity.NewsDetail;
import com.example.juicekaaa.fedtech10.FragmentViewHolder.NewsViewHolder;
import com.example.juicekaaa.fedtech10.R;

import java.util.List;

/**
 * Created by Juicekaaa on 17/6/14.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<NewsViewHolder> mList;
    String TAG = "position";
    private Context context;

    private LoadStatus mLoadStatus = LoadStatus.CLICK_LOAD_MORE;//上拉加载的状态

    private static final int VIEW_TYPE_FOOTER = 0;
    private static final int VIEW_TYPE_ITEM = 1;


    public NewsAdapter(Context context, List<NewsViewHolder> mList) {
        this.mList = mList;
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_FOOTER) {
            return onCreateFooterViewHolder(parent, viewType);

        } else if (viewType == VIEW_TYPE_ITEM) {

            return onCreateItemViewHolder(parent, viewType);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_ITEM:
                onBindItemViewHolder((ViewItemHolder) holder, position);
                break;
            case VIEW_TYPE_FOOTER:
                onBindFooterViewHolder(holder, position, mLoadStatus);
                break;
            default:
                break;
        }
    }


    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.footer_view, null);
        return new FooterViewHolder(view);
    }


    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        //实例化展示的view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_news_list_item, parent, false);
        // 实例化viewholder
        ViewItemHolder viewItemHolder = new ViewItemHolder(view);
        return viewItemHolder;
    }


    private void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position, LoadStatus mLoadStatus) {

        FooterViewHolder mFooterViewHolder = (FooterViewHolder) holder;
        switch (mLoadStatus) {
            case CLICK_LOAD_MORE:
                mFooterViewHolder.mLoadingLayout.setVisibility(View.GONE);
                mFooterViewHolder.mClickLoad.setVisibility(View.VISIBLE);
                break;
            case LOADING_MORE:
                mFooterViewHolder.mLoadingLayout.setVisibility(View.VISIBLE);
                mFooterViewHolder.mClickLoad.setVisibility(View.GONE);
                break;
        }
    }


    public void onBindItemViewHolder(ViewItemHolder holder, int position) {


//        holder.newsId.setText(mList.get(position).getNewsId());
//        holder.newsTitle.setText(mList.get(position).getNewsTitle());
//        holder.newsTime.setText(mList.get(position).getNewsTime());
//        holder.newsContent.setText(mList.get(position).getNewsContent());
//        holder.newsSpot.setText(mList.get(position).getNewsSport());
//        holder.newsWriter.setText(mList.get(position).getNewsWriter());

        // 绑定数据
        holder.newsId.setText(mList.get(position).getNewsId());
        holder.newsWriter.setText(mList.get(position).getNewsWriter());
//        holder.imgHeader.setImageResource(mList.get(position).getImgHeader());
//        holder.imgPhoto.setImageResource(mList.get(position).getImgPhoto());
        holder.newsContent.setText(mList.get(position).getNewsContent());
        holder.newsTitle.setText(mList.get(position).getNewsTitle());
        holder.newsTime.setText(mList.get(position).getNewsTime());

        Glide.with(context).load(mList.get(position).getImgHeader()).centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .crossFade().into(holder.imgHeader);
        Glide.with(context).load(mList.get(position).getImgPhoto()).into(holder.imgPhoto);
        setMyOnClickLinstener(holder, position);


    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size() + 1;
    }


    @Override
    public int getItemViewType(int position) {

        if (position + 1 == getItemCount()) {//最后一条为FooterView
            return VIEW_TYPE_FOOTER;
        }
        return VIEW_TYPE_ITEM;
    }

    public NewsViewHolder getItem(int position) {

        return mList.get(position);
    }


    public void setMyOnClickLinstener(final ViewItemHolder holder, final int position) {


        holder.newsTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "click " + position + " Detail", Toast.LENGTH_SHORT).show();
            }
        });

        holder.imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewsDetail.class);
                context.startActivity(intent);
//                Toast.makeText(context, "click " + position + " Detail", Toast.LENGTH_SHORT).show();
            }
        });


        holder.newsContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "click " + position + " Detail", Toast.LENGTH_SHORT).show();
            }
        });

        holder.imgSport.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   if (mList.get(position).getIsSport() == -1) {
                                                       mList.get(position).setIsSport(1);
                                                       setSport(holder, position, mList.get(position).getIsSport());
                                                   } else {
                                                       setSport(holder, position, mList.get(position).getIsSport());
                                                   }


                                               }

                                           }
        );


        holder.newsComment.setOnClickListener(new View.OnClickListener()

                                              {
                                                  @Override
                                                  public void onClick(View view) {
                                                      Toast.makeText(context, "click Comment", Toast.LENGTH_SHORT).show();
                                                  }
                                              }

        );


    }

    private void setSport(ViewItemHolder holder, int position, int sport) {
        if (sport == 1) {
            int addSport = mList.get(position).getNewsSport() + 1;
            mList.get(position).setNewsSport(addSport);
            holder.newsSpot.setText(String.valueOf(addSport));
            holder.imgSport.setImageResource(R.drawable.sport2);
            Log.e(TAG, String.valueOf(position));
            updateData(position);
            mList.get(position).setIsSport(0);
        } else {
            int cutSport = mList.get(position).getNewsSport() - 1;
            mList.get(position).setNewsSport(cutSport);
            holder.newsSpot.setText(String.valueOf(cutSport));
            holder.imgSport.setImageResource(R.drawable.news_sport);
            updateData(position);
            mList.get(position).setIsSport(-1);
        }


    }


    public static class ViewItemHolder extends RecyclerView.ViewHolder {


        public TextView newsId, newsTime, newsTitle, newsWriter, newsContent, newsSpot, newsComment;
        public ImageView imgHeader, imgPhoto, imgSport;

        public ViewItemHolder(View itemView) {
            super(itemView);
            newsId = (TextView) itemView.findViewById(R.id.tv_news_list_id);
            newsTime = (TextView) itemView.findViewById(R.id.tv_news_list_time);
            newsTitle = (TextView) itemView.findViewById(R.id.tv_news_list_title);
            newsWriter = (TextView) itemView.findViewById(R.id.tv_news_writer);
            newsContent = (TextView) itemView.findViewById(R.id.tv_news_list_content);
            newsSpot = (TextView) itemView.findViewById(R.id.tv_news_item_spot);
            newsComment = (TextView) itemView.findViewById(R.id.tv_news_item_comment);

            imgHeader = (ImageView) itemView.findViewById(R.id.img_news_list_header);
            imgPhoto = (ImageView) itemView.findViewById(R.id.img_news_list_photo);
            imgSport = (ImageView) itemView.findViewById(R.id.img_news_list_sport);


        }

    }


    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mLoadingLayout;
        public TextView mClickLoad;

        public FooterViewHolder(View itemView) {

            super(itemView);
            mLoadingLayout = (LinearLayout) itemView.findViewById(R.id.loading);
            mClickLoad = (TextView) itemView.findViewById(R.id.click_load_txt);
        }
    }


    public void updateData(int position) {
        notifyDataSetChanged();
        notifyItemChanged(position);
    }


    public enum LoadStatus {
        CLICK_LOAD_MORE,//点击加载更多
        LOADING_MORE//正在加载更多
    }


    public void setLoadStatus(LoadStatus loadStatus) {

        this.mLoadStatus = loadStatus;
    }


}

