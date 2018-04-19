package com.example.juicekaaa.fedtech10.Fragment;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.juicekaaa.fedtech10.FragmentViewHolder.IBeaconEquipModel;
import com.example.juicekaaa.fedtech10.MyAdapter.IBeaconAdapter;
import com.example.juicekaaa.fedtech10.R;

import java.util.ArrayList;

/**
 * Created by Juicekaaa on 17/6/13.
 */

public class FragmentExperience extends android.support.v4.app.Fragment {
    private View view;
    TextView tvExperience;
    ImageView imgExperience;
    ListView mListView;
    private BluetoothAdapter mBluetoothAdapter;
    private String TAG = "TAG";
    public static int currentposition = -1;
    final static String FEDTECH = "AB8190D5-D11E-4941-ACC4-42F30510B408";
    ArrayList<IBeaconEquipModel> equipLists = new ArrayList<>();
    IBeaconAdapter IBeaconAdapter;
    String title[] = new String[]{"研发部1", "研发部2"};
    String detail[] = new String[]{"这是研发部1", "这是研发部2"};
    int img[] = new int[]{R.drawable.police, R.drawable.army};
    int major[] = new int[]{21, 24};
    int minor[] = new int[]{101, 101};
    String UUID = FEDTECH;


    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                refesh();
                update();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_experience_ibeacon, container, false);

            initData();
            initView(view);
            initBluetooth();
            IBeaconAdapter = new IBeaconAdapter(getActivity(), equipLists);
            mListView.setAdapter(IBeaconAdapter);

        }

        return view;
    }


    private void initData() {

        for (int i = 0; i < title.length; i++) {
            IBeaconEquipModel IBeaconEquipModel = new IBeaconEquipModel();
            IBeaconEquipModel.setProximityUuid(UUID);
            IBeaconEquipModel.setMajor(Integer.valueOf(major[i]));
            IBeaconEquipModel.setMinor(Integer.valueOf(minor[i]));
            IBeaconEquipModel.setTitle(title[i]);
            IBeaconEquipModel.setDetail(detail[i]);
            IBeaconEquipModel.setPhotoId(img[i]);
            IBeaconEquipModel.setIdent(major[i] + "-" + minor[i]);
            equipLists.add(IBeaconEquipModel);

        }
    }

    private void initBluetooth() {
        //获取蓝牙实例
        BluetoothManager bluetoothManager = (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        //判断蓝牙可用
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 1);
        }
        mBluetoothAdapter.startLeScan(mLeScanCallback);
        Log.d(TAG, "调用蓝牙");
    }


    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi,
                             final byte[] scanRecord) {
            int startByte = 2;
            boolean patternFound = false;
            // 寻找ibeacon
            while (startByte <= 5) {
                if (((int) scanRecord[startByte + 2] & 0xff) == 0x02 && // Identifies
                        // an
                        // iBeacon
                        ((int) scanRecord[startByte + 3] & 0xff) == 0x15) { // Identifies
                    // correct
                    // data
                    // length
                    patternFound = true;
                    break;
                }
                startByte++;
            }
            // 如果找到了的话
            if (patternFound) {
                // 转换为16进制
                byte[] uuidBytes = new byte[16];
                System.arraycopy(scanRecord, startByte + 4, uuidBytes, 0, 16);
                String hexString = bytesToHex(uuidBytes);

                // ibeacon的UUID值
                String uuid = hexString.substring(0, 8) + "-"
                        + hexString.substring(8, 12) + "-"
                        + hexString.substring(12, 16) + "-"
                        + hexString.substring(16, 20) + "-"
                        + hexString.substring(20, 32);

                // ibeacon的Major值
                int major = (scanRecord[startByte + 20] & 0xff) * 0x100
                        + (scanRecord[startByte + 21] & 0xff);

                // ibeacon的Minor值
                int minor = (scanRecord[startByte + 22] & 0xff) * 0x100
                        + (scanRecord[startByte + 23] & 0xff);

                String ibeaconName = device.getName();
                String mac = device.getAddress();
                int txPower = (scanRecord[startByte + 24]);


                Log.d("BLE", bytesToHex(scanRecord));
                Log.d("BLE", "Name：" + ibeaconName + "\nMac：" + mac
                        + " \nUUID：" + uuid + "\nMajor：" + major + "\nMinor："
                        + minor + "\nTxPower：" + txPower + "\nrssi：" + rssi);
                Log.d("BLE", "distance：" + calculateAccuracy(txPower, rssi));

                //判断当前设备的具体信息
                if (uuid.equals(FEDTECH)) {
                    int i = isexsit(major, minor);
                    if (i != -1) {
                        equipLists.get(i).major = major;
                        equipLists.get(i).minor = minor;
                        equipLists.get(i).txPower = txPower;
                        equipLists.get(i).rssi = rssi;
                        handler.sendEmptyMessage(0);
                    }

                    currentposition = rank();

                }

            }


        }
    };

    //得到最近的一次距离的设备位置
    public int rank() {
        int position = 0;
        double min = distance(0);
        for (int i = 0; i < equipLists.size(); i++) {
            if (distance(i) < min) {
                if (distance(i) != 0) {
                    min = distance(i);
                    position = i;
                }

            }
        }
        return position;
    }

    public double distance(int i) {
        return calculateAccuracy(equipLists.get(i).txPower, equipLists.get(i).rssi);
    }

    //major minor 是否存在于equipLists数组里
    public int isexsit(int major, int minor) {
        //存在数组里
        for (int i = 0; i < equipLists.size(); i++) {
            if ((major + "-" + minor).equals(equipLists.get(i).ident)) {
                return i;
            }
        }

        return -1;
    }

    //转化成16进制数
    static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    //计算距离的运算函数
    public static double calculateAccuracy(int txPower, double rssi) {
        if (rssi == 0) {
            return 0; // if we cannot determine accuracy, return -1.
        }

        double ratio = rssi * 1.0 / txPower;
        if (ratio < 1.0) {
            return Math.pow(ratio, 10);
        } else {
            double accuracy = (0.89976) * Math.pow(ratio, 7.7095) + 0.111;
            return accuracy;
        }
    }

    private void initView(View view) {

        tvExperience = (TextView) view.findViewById(R.id.tv_experience);
        imgExperience = (ImageView) view.findViewById(R.id.img_experience);
        mListView = (ListView) view.findViewById(R.id.experience_ibeacon_listview);

    }


    public void refesh() {
        if (currentposition != -1) {
            tvExperience.setText(equipLists.get(currentposition).title);
            imgExperience.setImageResource(equipLists.get(currentposition).photoId);
        }

        update();
    }

    public  void update() {

        if (equipLists.size() != 0) {
            IBeaconAdapter.notifyDataSetChanged();
            mListView.invalidate();
        }
    }
}
