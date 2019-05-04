package com.language.cake.praticenglishgame.dao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 005357 on 9/17/2017.
 */

public class PhotoItemCollectionDao {
    @SerializedName("success")         private String success;
    @SerializedName("data")            private List<PhotoItemDao> data;


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<PhotoItemDao> getData() {
        return data;
    }

    public void setData(List<PhotoItemDao> data) {
        this.data = data;
    }
}
