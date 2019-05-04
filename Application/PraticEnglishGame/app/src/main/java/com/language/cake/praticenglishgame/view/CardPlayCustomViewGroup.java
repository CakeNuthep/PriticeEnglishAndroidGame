package com.language.cake.praticenglishgame.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;
import com.language.cake.praticenglishgame.ControlGame;
import com.language.cake.praticenglishgame.MyTTS;
import com.language.cake.praticenglishgame.R;

import java.util.Locale;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class CardPlayCustomViewGroup extends BaseCustomViewGroup {

    com.inthecheesefactory.thecheeselibrary.widget.AdjustableImageView imageViewBack,imageViewFront;
    com.inthecheesefactory.thecheeselibrary.widget.AdjustableImageView imageView;
    TextView textViewVocap;
    boolean isLoadSuccess;
    boolean isOpen;
    boolean isSound = false;
    View parentView;
    int cardId;
    String vocap;


    public interface ViewListener{
        void onLoadImageSuccess(int sumSuccess);
    }

    public CardPlayCustomViewGroup(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public CardPlayCustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public CardPlayCustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public CardPlayCustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        isLoadSuccess = false;
        inflate(getContext(), R.layout.card_play, this);
    }

    private void initInstances() {
        this.isOpen = false;
        setClickable(true);
        imageViewFront = (com.inthecheesefactory.thecheeselibrary.widget.AdjustableImageView)findViewById(R.id.customviewGroupCardFrontPlayImageView);
        //imageViewBack = (com.inthecheesefactory.thecheeselibrary.widget.AdjustableImageView)findViewById(R.id.customviewGroupCardBackPlayImageView);
//        imageView = (com.inthecheesefactory.thecheeselibrary.widget.AdjustableImageView)findViewById(R.id.customviewGroupCardPlayImageView);
        textViewVocap = (TextView)findViewById(R.id.customviewGroupCardPlayTextView);
        MyTTS.getInstance(Contextor.getInstance().getContext());


        // findViewById here
    }

    public int getCardID(){
        return cardId;
    }

    public void setImageVocap(String url, boolean isShow){
        if(isShow) {
            Glide.with(getContext())
                    .load(url)//url = "http://www.quickanddirtytips.com/sites/default/files/styles/insert_small/public/images/4348/dice.jpg"
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            if (parentView != null) {
                                CardPlayCustomViewGroup.ViewListener listener = (CardPlayCustomViewGroup.ViewListener) parentView;
                                listener.onLoadImageSuccess(0);
                            }
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            isLoadSuccess = true;
                            if (parentView != null) {
                                CardPlayCustomViewGroup.ViewListener listener = (CardPlayCustomViewGroup.ViewListener) parentView;
                                listener.onLoadImageSuccess(1);
                            }
                            return false;
                        }
                    })
                    .error(R.drawable.not_load)
                    .into(imageViewFront);
        }
        else {
            //imageViewFront.setBackgroundColor(Color.GRAY);
            CardPlayCustomViewGroup.ViewListener listener = (CardPlayCustomViewGroup.ViewListener) parentView;
            listener.onLoadImageSuccess(1);
        }
    }

    public void setTextViewVocap(String vocap, boolean isShow){
        if(isShow) {
            textViewVocap.setText(vocap);
        }
        this.vocap = vocap;
    }

    public void setCardID(int id){
        this.cardId = id;
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

    public boolean isOpen(){
        return this.isOpen;
    }

    public void setOpenOrCloseCard(boolean isOpen){
        if(isOpen){
            this.isOpen = true;
            if(isSound) {
                MyTTS.getInstance(Contextor.getInstance().getContext()).setEngine("com.google.android.tts").setLocale(Locale.ENGLISH).speak(this.vocap);

            }
//            imageViewFront.setImageResource(R.drawable.full_heart);
        }
        else {
            this.isOpen = false;
//            imageViewFront.setImageResource(R.drawable.lock);
        }
    }

    public void setParentView(View v){
        this.parentView = v;
    }

    public void setIsSound(boolean isSound){
        this.isSound = isSound;
    }

}
