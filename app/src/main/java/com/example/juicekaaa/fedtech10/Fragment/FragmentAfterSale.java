package com.example.juicekaaa.fedtech10.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.juicekaaa.fedtech10.FragmentViewHolder.AfterSaleViewHolder;
import com.example.juicekaaa.fedtech10.R;

import java.util.ArrayList;

/**
 * Created by Juicekaaa on 17/6/13.
 */

public class FragmentAfterSale extends android.support.v4.app.Fragment {
    private View view;
    private EditText etvNumber, etvName;
    private Button btnSearch;
    private ArrayList<AfterSaleViewHolder> mData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_aftersale, container, false);
            mData = setData();
            initView(view);
            return view;
        }

        return view;
    }

    private void initView(View view) {
        etvNumber = (EditText) view.findViewById(R.id.etv_aftersale_number);
        etvName = (EditText) view.findViewById(R.id.etv_aftersale_name);
        btnSearch = (Button) view.findViewById(R.id.btn_aftersale_search);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etvName.getText().toString().isEmpty() || etvNumber.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "您是不是漏了点什么？", Toast.LENGTH_SHORT).show();
                } else if (etvNumber.getText().toString().equals(String.valueOf(mData.get(0))) && etvName.getText().toString().equals(String.valueOf(mData.get(1)))) {
                    etvName.setText("");
                    etvNumber.setText("");
                    Toast.makeText(getActivity(), "查询成功", Toast.LENGTH_SHORT).show();
                } else if (etvName.getText().toString().equals("1") && etvNumber.getText().toString().equals("1")) {
                    etvName.setText("");
                    etvNumber.setText("");
                    Toast.makeText(getActivity(), "查询成功", Toast.LENGTH_SHORT).show();
                } else {
                    etvName.setText("");
                    etvNumber.setText("");
                    Toast.makeText(getActivity(), "查询失败", Toast.LENGTH_SHORT).show();

                }


            }
        });


    }

    public ArrayList<AfterSaleViewHolder> setData() {
        ArrayList<AfterSaleViewHolder> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add(new AfterSaleViewHolder(Integer.toString(i), Integer.toString(i)));
        }
        return data;
    }
}
