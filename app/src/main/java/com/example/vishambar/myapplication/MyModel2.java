package com.example.vishambar.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


public class MyModel2 implements Parcelable {
    private int rollNum;
    private String name;

    protected MyModel2(Parcel in) {
        rollNum = in.readInt();
        name = in.readString();
        isAboveEighteen = in.readByte() != 0;
        arrayListStrings = in.createStringArrayList();
//        arrayListModels = in.createTypedArrayList(MyModel.CREATOR);
    }

    public static final Creator<MyModel2> CREATOR = new Creator<MyModel2>() {
        @Override
        public MyModel2 createFromParcel(Parcel in) {
            return new MyModel2(in);
        }

        @Override
        public MyModel2[] newArray(int size) {
            return new MyModel2[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(rollNum);
        parcel.writeString(name);
        parcel.writeByte((byte) (isAboveEighteen ? 1 : 0));
        parcel.writeStringList(arrayListStrings);
//        parcel.writeTypedList(arrayListModels);
    }

    private enum GENDER {MALE, FEMALE}

    private GENDER gender;
    private boolean isAboveEighteen;
    private ArrayList<String> arrayListStrings;
    private ArrayList<MyModel> arrayListModels;

}