package com.language.cake.praticenglishgame.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;
import com.language.cake.praticenglishgame.ControlGame;
import com.language.cake.praticenglishgame.R;

import java.util.logging.Level;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class QuizeCustomViewGroup extends BaseCustomViewGroup {
    ControlGame.quizeData quizeData;
    TextView textView;
    com.inthecheesefactory.thecheeselibrary.widget.AdjustableImageView imageView;
    com.language.cake.praticenglishgame.view.HeartCustomViewGroup heartCustomViewGroup;

    public ControlGame.quizeData getQuizeData(){
        return quizeData;
    }

    public QuizeCustomViewGroup(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public QuizeCustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public QuizeCustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public QuizeCustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.layout_quize, this);
    }

    private void initInstances() {
        // findViewById here
        heartCustomViewGroup = (com.language.cake.praticenglishgame.view.HeartCustomViewGroup) findViewById(R.id.heartCustomviewGroup);
        textView = (TextView)findViewById(R.id.customViewGroupQuizeText);
        imageView = (com.inthecheesefactory.thecheeselibrary.widget.AdjustableImageView)findViewById(R.id.customViewGroupQuizeImageView);
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

    public void setQuizeData(ControlGame.quizeData quizeData ,int level){
        this.quizeData = quizeData;
        if(!quizeData.isRock) {
            if(imageView != null){
                if(level == ControlGame.LEVEL_EASY){
                    imageView.setImageResource(R.drawable.custombordereasy);
                }
                else if(level == ControlGame.LEVEL_MEDIUM){
                    imageView.setImageResource(R.drawable.custombordermedium);
                }
                else if(level == ControlGame.LEVEL_HARD){
                    imageView.setImageResource(R.drawable.customborderhard);
                }

            }
            if (textView != null) {
                textView.setText(String.format("Quize %d", quizeData.numberQuize));
            }

            if (heartCustomViewGroup != null) {
                heartCustomViewGroup.setRateHeart(quizeData.rateHeart);
            }
        }
        else {
            if(imageView != null){
                imageView.setImageResource(R.drawable.lock);
            }
            if (textView != null) {
                textView.setText(String.format("", quizeData.numberQuize));
            }

            if (heartCustomViewGroup != null) {
                heartCustomViewGroup.setEmptyImage();
            }
        }



    }




}
