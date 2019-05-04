package com.language.cake.praticenglishgame.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.language.cake.praticenglishgame.ControlGame;
import com.language.cake.praticenglishgame.R;
import com.squareup.picasso.Picasso;

/**
 * Created by 005357 on 9/10/2017.
 */

public class QuizeGridViewAdapter extends BaseAdapter{
    private ControlGame.quizeData[] mQuizeDatas;
    private int level;
    public QuizeGridViewAdapter(ControlGame.quizeData[] quizeDatas ,int level)
    {
        mQuizeDatas = quizeDatas;
        this.level = level;
    }
    @Override
    public int getCount() {
        return mQuizeDatas.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        com.language.cake.praticenglishgame.view.QuizeCustomViewGroup quizeCustomViewGroup;
        if(view != null){
            quizeCustomViewGroup = (com.language.cake.praticenglishgame.view.QuizeCustomViewGroup) view;
        }
        else {
            quizeCustomViewGroup = new com.language.cake.praticenglishgame.view.QuizeCustomViewGroup(viewGroup.getContext());
        }
        quizeCustomViewGroup.setQuizeData(mQuizeDatas[i], this.level);
        return quizeCustomViewGroup;
    }
}
