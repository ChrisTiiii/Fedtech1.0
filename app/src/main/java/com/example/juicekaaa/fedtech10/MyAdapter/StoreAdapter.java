package com.example.juicekaaa.fedtech10.MyAdapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.juicekaaa.fedtech10.FragmentViewHolder.StoreViewHolder;
import com.example.juicekaaa.fedtech10.R;

import java.util.ArrayList;

/**
 * Created by Juicekaaa on 17/6/14.
 */

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {
    private ArrayList<StoreViewHolder> mData;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private String TAG = "position";
    private Context context;
    private CardView mCardView;

    public StoreAdapter(Context context, ArrayList<StoreViewHolder> mData) {
        this.context = context;
        this.mData = mData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //实例化展示的view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_store_list_item, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(view);
        initCardView(view);
        return viewHolder;
    }

    private void initCardView(View view) {
        mCardView = (CardView) view.findViewById(R.id.me_cardview);
        mCardView.setRadius(8);//设置图片圆角的半径大小
        mCardView.setCardElevation(8);//设置阴影部大小
        mCardView.setContentPadding(5, 5, 5, 5);//设置图片距离阴影大小
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.tvTitle.setText(mData.get(position).getTvTitle());
        holder.tvDetail.setText(mData.get(position).getTvDetail());
        holder.imgPhoto.setImageResource(mData.get(position).getImgPhoth());

        //判断是否设置了监听器
        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }

        //判断是否设置了监听器
        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemLongClickListener.onItemLongClick(holder.itemView, position);
                    //返回true 表示消耗了事件 事件不会继续传递
                    return true;
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvDetail;
        public ImageView imgPhoto;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_store_title);
            tvDetail = (TextView) itemView.findViewById(R.id.tv_store_detail);
            imgPhoto = (ImageView) itemView.findViewById(R.id.img_store_photo);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

}
