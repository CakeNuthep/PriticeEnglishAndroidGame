package com.language.cake.praticenglishgame.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 005357 on 9/27/2017.
 */

public class MainQuizeDao implements Parcelable {
    @SerializedName("QuizeData") List<List<QuizeDao>> data;
    @SerializedName("DateTimeUpdateData") String dateTimeUpdate;

    protected MainQuizeDao(Parcel in) {
    }

    public static final Creator<MainQuizeDao> CREATOR = new Creator<MainQuizeDao>() {
        @Override
        public MainQuizeDao createFromParcel(Parcel in) {
            return new MainQuizeDao(in);
        }

        @Override
        public MainQuizeDao[] newArray(int size) {
            return new MainQuizeDao[size];
        }
    };

    public List<List<QuizeDao>> getData() {
        return data;
    }

    public void setData(List<List<QuizeDao>> data) {
        this.data = data;
    }

    public String getDateTimeUpdate() {
        return dateTimeUpdate;
    }

    public void setDateTimeUpdate(String dateTimeUpdate) {
        this.dateTimeUpdate = dateTimeUpdate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
