package com.example.juicekaaa.fedtech10.MyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.juicekaaa.fedtech10.Fragment.FragmentExperience;
import com.example.juicekaaa.fedtech10.FragmentViewHolder.IBeaconEquipModel;
import com.example.juicekaaa.fedtech10.R;

import java.util.ArrayList;

/**
 * Created by Juicekaaa on 17/6/22.
 */

public class IBeaconAdapter extends BaseAdapter {
    private Context context;
    ArrayList<IBeaconEquipModel> data;

    public IBeaconAdapter(Context context, ArrayList<IBeaconEquipModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            //这里确定你listview里面每一个item的layout
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.experience_ibeacon_list_item, null);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.ibeacon_tv1);
            viewHolder.tvDistance = (TextView) view.findViewById(R.id.ibeacon_tv2);
            viewHolder.imgPosition = (ImageView) view.findViewById(R.id.ibeacon_img_position);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag(); //这里是为了提高listview的运行效率
        }

        IBeaconEquipModel item = (IBeaconEquipModel) getItem(i);

        viewHolder.tvTitle.setText(data.get(i).getTitle());
        viewHolder.tvDistance.setText("当前距您" + FragmentExperience.calculateAccuracy(item.txPower, Double.parseDouble(item.rssi + "")) + "m");
        if (i == FragmentExperience.currentposition) {
            viewHolder.imgPosition.setImageResource(item.imgPosition);
        } else
            viewHolder.imgPosition.setImageResource(R.color.white);
        return view;
    }

    static class ViewHolder {
        public TextView tvTitle, tvDistance;
        public ImageView imgPosition;

    }
}
