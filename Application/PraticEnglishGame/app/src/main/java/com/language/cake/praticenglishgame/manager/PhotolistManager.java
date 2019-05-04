package com.language.cake.praticenglishgame.manager;

import android.content.Context;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.language.cake.praticenglishgame.dao.PhotoItemCollectionDao;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class PhotolistManager {

    private static PhotolistManager instance;

    public static PhotolistManager getInstance() {
        if (instance == null)
            instance = new PhotolistManager();
        return instance;
    }

    private Context mContext;
    private PhotoItemCollectionDao dao;


    private PhotolistManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public PhotoItemCollectionDao getDao() {
        return dao;
    }

    public void setDao(PhotoItemCollectionDao dao) {
        this.dao = dao;
    }
}
