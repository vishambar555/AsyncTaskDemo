package com.example.vishambar.myapplication;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MyModel  {
    private int rollNum;
    private String name;

    private enum GENDER {MALE, FEMALE}

    private GENDER gender;
    private boolean isAboveEighteen;
    private ArrayList<String> arrayListStrings;
    private ArrayList<String> arrayListModels;

    public MyModel() {

    }


}