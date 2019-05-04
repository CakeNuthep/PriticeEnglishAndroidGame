package com.language.cake.praticenglishgame.manager.http;

import com.language.cake.praticenglishgame.dao.MainQuizeDao;
import com.language.cake.praticenglishgame.dao.PhotoItemCollectionDao;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by 005357 on 9/17/2017.
 */

public interface ApiService {
    @GET("MainQuize.json")//list
    Call<MainQuizeDao> loadPhotoList();
}
