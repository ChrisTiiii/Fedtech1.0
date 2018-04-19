package com.example.juicekaaa.fedtech10.FragmentViewHolder;

import android.graphics.Bitmap;

/**
 * Created by Juicekaaa on 2017/7/14.
 */

public class PersonalPageViewHolder {

    private String Name;
    private String Address;
    private Bitmap bitmap;
    private int Id;


    public PersonalPageViewHolder() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}