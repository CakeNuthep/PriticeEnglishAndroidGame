package com.language.cake.praticenglishgame.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 005357 on 9/27/2017.
 */

public class QuizeDao implements Parcelable{
    @SerializedName("ID") int id;
    @SerializedName("TextVocap") String textVocap;
    @SerializedName("Url") String url;

    protected QuizeDao(Parcel in) {
        id = in.readInt();
        textVocap = in.readString();
        url = in.readString();
    }

    public static final Creator<QuizeDao> CREATOR = new Creator<QuizeDao>() {
        @Override
        public QuizeDao createFromParcel(Parcel in) {
            return new QuizeDao(in);
        }

        @Override
        public QuizeDao[] newArray(int size) {
            return new QuizeDao[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTextVocap() {
        return textVocap;
    }

    public void setTextVocap(String textVocap) {
        this.textVocap = textVocap;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(textVocap);
        parcel.writeString(url);
    }
}
