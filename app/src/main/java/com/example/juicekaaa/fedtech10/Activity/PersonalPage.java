package com.example.juicekaaa.fedtech10.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juicekaaa.fedtech10.FragmentViewHolder.PersonalPageViewHolder;
import com.example.juicekaaa.fedtech10.R;
import com.lljjcoder.citypickerview.widget.CityPicker;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Juicekaaa on 2017/7/13.
 */

public class PersonalPage extends AppCompatActivity implements View.OnClickListener {
    private Bitmap head = null;// 头像Bitmap
    private static String path = "/sdcard/myHead/";// sd路径
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    TextView tvCity, tvBack, tvName, tvSave;
    EditText etId;
    ImageView imgBack, imgHead;
    String province, city, district, name;
    private final int PERSON_BACK_CODE = 99;
    private final int PERSON_ERROR_CODE = -1;
    PersonalPageViewHolder personalPageViewHolder = new PersonalPageViewHolder();

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_page);
        getSupportActionBar().hide();
        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        initView();
        readSharePerferences();
        initListener();
        onMyClick();

    }


    private void saveSharePreferences(String name, String address, Bitmap head) throws IOException {
        editor = sharedPreferences.edit();  //获取存储器
        editor.putString("address", address);  //采用key-value键值对存放
        editor.putString("city", city);
        editor.putString("province", province);
        editor.putString("name", name);

        //保存图片
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //使用BitmapFactory拿到压缩文件，compress方法压缩并保存到byteArrayOutputStream
        head.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);
        //通过Base64.encode将字节文件转换成Base64编码保存在String中。
        String imageStr = new String(Base64.encode(byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
        editor.putString("head", imageStr);
        byteArrayOutputStream.close();
        editor.putBoolean("remember", true);
        editor.commit();  //提交


    }


    public void readSharePerferences() {
        boolean isRemember = sharedPreferences.getBoolean("remember", false);
        if (isRemember) {
            tvCity.setText(sharedPreferences.getString("address", "null"));
            tvName.setText(sharedPreferences.getString("name", "null"));
            ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decode(sharedPreferences.getString("head", "").getBytes(), Base64.DEFAULT));
            imgHead.setImageDrawable(Drawable.createFromStream(bais, "null"));
        }

    }

    private void initListener() {
        findViewById(R.id.person_manager).setOnClickListener(this);
        imgHead.setOnClickListener(this);
    }


    private void onMyClick() {


        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isBack();
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isBack();
            }
        });

        tvName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                tvName.setVisibility(View.GONE);
                etId.setVisibility(View.VISIBLE);
                etId.setFocusable(true);
                etId.requestFocus();
                PersonalPage.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                return true;
            }
        });


        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveInfo();
                if (city != null && name != null && province != null) {
                    Intent intent = new Intent();
                    setResult(PERSON_BACK_CODE, intent);
                    finish();
                }


            }


        });


        tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initCity();
            }
        });

    }

    private void saveInfo() {

        if (city != null && name != null && province != null && district != null) {
            personalPageViewHolder.setName(name);
            personalPageViewHolder.setAddress(province + "-" + city + "-" + district);
            Toast.makeText(PersonalPage.this, "保存成功", Toast.LENGTH_SHORT).show();
            try {
                saveSharePreferences(name, province + "-" + city + "-" + district, head);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            Toast.makeText(PersonalPage.this, "是不是漏了点什么？", Toast.LENGTH_SHORT).show();

    }

    private void isBack() {

        Intent intent = new Intent();
        setResult(PERSON_ERROR_CODE, intent);
        finish();


    }

    private void initCity() {
        CityPicker cityPicker = new CityPicker.Builder(this)
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .textColor(Color.parseColor("#000000"))
                .cancelTextColor("#696969")
                .confirTextColor("#696969")
                .province("江苏省")
                .city("南京")
                .district("鼓楼区")
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();

        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                province = citySelected[0];
                //城市
                city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                district = citySelected[2];
                //邮编
                String code = citySelected[3];
                //为TextView赋值 
                tvCity.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
            }

            @Override
            public void onCancel() {
                Toast.makeText(PersonalPage.this, "设置失败哦~", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void initView() {
        tvCity = (TextView) findViewById(R.id.tv_personal_city);
        etId = (EditText) findViewById(R.id.et_personal_name);
        imgBack = (ImageView) findViewById(R.id.img_person_back);
        tvBack = (TextView) findViewById(R.id.tv_personal_back);
        tvName = (TextView) findViewById(R.id.tv_personal_name);
        tvSave = (TextView) findViewById(R.id.person_save);
        imgHead = (ImageView) findViewById(R.id.img_person_head);

        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");// 从SD卡中找头像，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);// 转换成drawable
            imgHead.setImageDrawable(drawable);
        } else {
            /**
             * 如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
             *
             */
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.person_manager:
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                name = etId.getText().toString();
                etId.setVisibility(View.GONE);
                tvName.setVisibility(View.VISIBLE);
                if (name != null) {
                    tvName.setText(name);
                }
                break;

            case R.id.img_person_head:// 更换头像
                showTypeDialog();
                break;
        }

    }


    private void showTypeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_choose_head, null);

        TextView tv_select_gallery = (TextView) view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = (TextView) view.findViewById(R.id.tv_select_camera);
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                dialog.dismiss();
            }
        });
        tv_select_camera.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
                startActivityForResult(intent2, 2);// 采用ForResult打开
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                }

                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
                }

                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        setPicToView(head);// 保存在SD卡中
                        imgHead.setImageBitmap(head);// 用ImageView显示出来
                        personalPageViewHolder.setBitmap(head);
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 调用系统的裁剪功能
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "head.jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
