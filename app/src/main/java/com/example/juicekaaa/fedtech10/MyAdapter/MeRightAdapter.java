package com.example.juicekaaa.fedtech10.MyAdapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juicekaaa.fedtech10.FragmentViewHolder.MeRightViewHolder;
import com.example.juicekaaa.fedtech10.R;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.util.List;

/**
 * Created by Juicekaaa on 17/6/16.
 */

public class MeRightAdapter extends RecyclerView.Adapter<MeRightAdapter.ViewHolder> {
    private List<MeRightViewHolder> mData;
    private Context mContext;
    private View view;
    private CardView mCardView;

    public MeRightAdapter(Context mContext, List<MeRightViewHolder> mData) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.fragment_me_list_right_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        initCardView(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.imgIcon.setImageResource(mData.get(position).getImgIcon());
        holder.tvTitle.setText(mData.get(position).getTvTitle());
        holder.tvDate.setText(mData.get(position).getTvDate());
        holder.tvAddress.setText(mData.get(position).getTvAddress());

        holder.btnUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final NiftyDialogBuilder niftyDialogBuilder = NiftyDialogBuilder.getInstance(mContext);
                niftyDialogBuilder
                        .withTitle(mData.get(position).getTvTitle())                    // 黑#121212 灰#D9D9D9 粉色#FFAEB9
                        .withTitleColor("#708090")                                      //    一级标题的颜色 #FFFFFF   me #778899
                        .withDividerColor("#FFAEB9")                                    //  一级与二级底线的颜色  #11000000
                        .withMessage("您确定使用优惠券" + (position + 1) + "么？？")
                        .withMessageColor("#778899")                                     //二级标题的颜色 #FFFFFFFF    me #778899
                        .withDialogColor("#EBEBEB")                                     // 整体背景色 #FFE74C3C   me #D9D9D9
                        .withIcon(mContext.getResources().getDrawable(R.drawable.juan3))
                        .withDuration(700)
                        .withButton1Text("是的")
                        .withButton2Text("在考虑下")
                        .isCancelableOnTouchOutside(true)
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                niftyDialogBuilder.dismiss();
                                Toast.makeText(v.getContext(), "使用成功", Toast.LENGTH_SHORT).show();
                                dataChange(position);
                            }
                        })
                        .setButton2Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                niftyDialogBuilder.dismiss();
                            }
                        })
                        .show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgIcon;
        private TextView tvTitle, tvDate, tvAddress;
        private Button btnUse;

        public ViewHolder(View itemView) {
            super(itemView);
            imgIcon = (ImageView) itemView.findViewById(R.id.img_me_right_list_icon);
            tvTitle = (TextView) itemView.findViewById(R.id.me_right_list_title);
            tvDate = (TextView) itemView.findViewById(R.id.me_right_list_date);
            tvAddress = (TextView) itemView.findViewById(R.id.me_right_list_address);
            btnUse = (Button) itemView.findViewById(R.id.btn_me_list_right_use);
        }
    }

    private void initCardView(View view) {
        mCardView = (CardView) view.findViewById(R.id.me_right_cardview);
        mCardView.setRadius(8);//设置图片圆角的半径大小
        mCardView.setCardElevation(8);//设置阴影部大小
        mCardView.setContentPadding(5, 5, 5, 5);//设置图片距离阴影大小
        mCardView.setBackgroundResource(R.drawable.kapian);
    }

    public void dataChange(int position) {
        mData.remove(position);
        notifyDataSetChanged();
    }

}
