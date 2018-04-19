package com.example.juicekaaa.fedtech10.MyAdapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juicekaaa.fedtech10.FragmentViewHolder.MeLeftViewHolder;
import com.example.juicekaaa.fedtech10.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juicekaaa on 17/6/16.
 */

public class MeLeftAdapter extends RecyclerView.Adapter<MeLeftAdapter.ViewHolder> {
    private List<MeLeftViewHolder> data = new ArrayList<>();
    private Context context;
    private CardView mCardView;

    public MeLeftAdapter(Context context, List<MeLeftViewHolder> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //实例化展示的view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_me_list_left_item, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(view);
        initCardView(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.imgIcon.setImageResource(data.get(position).getImgIcon());
        holder.tvTitle.setText(data.get(position).getTvTitle());
        holder.tvExplain.setText(data.get(position).getTvExplain());
        holder.tvUseDate.setText(data.get(position).getTvUseDate());
        holder.tvTime.setText(data.get(position).getTvTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "您查看了优惠券", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgIcon;
        private TextView tvTitle, tvExplain, tvUseDate, tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            imgIcon = (ImageView) itemView.findViewById(R.id.img_me_left_list_item_icon);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_me_left_list_item_title);
            tvExplain = (TextView) itemView.findViewById(R.id.tv_me_left_list_item_explain);
            tvUseDate = (TextView) itemView.findViewById(R.id.tv_me_left_list_item_usedate);
            tvTime = (TextView) itemView.findViewById(R.id.tv_me_left_list_item_time);
        }
    }

    private void initCardView(View view) {
        mCardView = (CardView) view.findViewById(R.id.me_left_cardview);
        mCardView.setRadius(8);//设置图片圆角的半径大小
        mCardView.setCardElevation(8);//设置阴影部大小
        mCardView.setContentPadding(5, 5, 5, 5);//设置图片距离阴影大小
        mCardView.setBackgroundResource(R.drawable.juxing);
    }


}
