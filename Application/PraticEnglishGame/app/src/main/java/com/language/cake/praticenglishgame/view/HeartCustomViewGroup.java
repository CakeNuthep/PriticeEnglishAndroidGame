package com.language.cake.praticenglishgame.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;
import com.language.cake.praticenglishgame.R;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class HeartCustomViewGroup extends BaseCustomViewGroup {

    int numberHeart;
    com.inthecheesefactory.thecheeselibrary.widget.AdjustableImageView heartImageView1,heartImageView2,heartImageView3;
    public HeartCustomViewGroup(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public HeartCustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public HeartCustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public HeartCustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.layout_heart, this);
    }

    private void initInstances() {
         //findViewById here
        this.heartImageView1 = (com.inthecheesefactory.thecheeselibrary.widget.AdjustableImageView)findViewById(R.id.customViewGroupHeartImageView1);
        this.heartImageView2 = (com.inthecheesefactory.thecheeselibrary.widget.AdjustableImageView)findViewById(R.id.customViewGroupHeartImageView2);
        this.heartImageView3 = (com.inthecheesefactory.thecheeselibrary.widget.AdjustableImageView)findViewById(R.id.customViewGroupHeartImageView3);
    }

    public void setRateHeart(int number){
        this.numberHeart = number;
        if(number >= 1){
            this.heartImageView1.setImageResource(R.drawable.full_heart);
        }
        else {
            this.heartImageView1.setImageResource(R.drawable.empty_heart);
        }

        if(number >= 2){
            this.heartImageView2.setImageResource(R.drawable.full_heart);
        }
        else {
            this.heartImageView2.setImageResource(R.drawable.empty_heart);
        }

        if(number >= 3){
            this.heartImageView3.setImageResource(R.drawable.full_heart);
        }
        else {
            this.heartImageView3.setImageResource(R.drawable.empty_heart);
        }
    }

    public int getRateHeart(){
        return numberHeart;
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        /*
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.StyleableName,
                defStyleAttr, defStyleRes);

        try {

        } finally {
            a.recycle();
        }
        */
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        BundleSavedState savedState = new BundleSavedState(superState);
        // Save Instance State(s) here to the 'savedState.getBundle()'
        // for example,
        // savedState.getBundle().putString("key", value);

        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        BundleSavedState ss = (BundleSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        Bundle bundle = ss.getBundle();
        // Restore State from bundle here
    }

    public void setEmptyImage() {
        if(this.heartImageView1 != null) {
            this.heartImageView1.setImageResource(android.R.color.transparent);
        }
        if(this.heartImageView2 != null) {
            this.heartImageView2.setImageResource(android.R.color.transparent);
        }
        if(this.heartImageView3 != null) {
            this.heartImageView3.setImageResource(android.R.color.transparent);
        }
    }
}
