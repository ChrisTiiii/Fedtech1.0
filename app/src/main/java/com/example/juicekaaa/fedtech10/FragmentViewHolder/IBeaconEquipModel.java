package com.example.juicekaaa.fedtech10.FragmentViewHolder;

import com.example.juicekaaa.fedtech10.R;

/**
 * Created by Juicekaaa on 16/12/13.
 */

public class IBeaconEquipModel {

    public String proximityUuid;
    public int major;
    public int minor;

    public int txPower;
    public int rssi;


    public int imgPosition = R.drawable.fedtech;
    public String title;
    public String detail;
    public int photoId;
    public String ident;
    public int ibeaconID;



    public IBeaconEquipModel() {

    }


    public int getIbeaconID() {
        return ibeaconID;
    }

    public void setIbeaconID(int ibeaconID) {
        this.ibeaconID = ibeaconID;
    }

    public String getProximityUuid() {
        return proximityUuid;
    }

    public void setProximityUuid(String proximityUuid) {
        this.proximityUuid = proximityUuid;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public int getTxPower() {
        return txPower;
    }

    public void setTxPower(int txPower) {
        this.txPower = txPower;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

}