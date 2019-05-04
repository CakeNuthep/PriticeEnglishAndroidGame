package com.language.cake.praticenglishgame.view;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;
import com.language.cake.praticenglishgame.ControlGame;
import com.language.cake.praticenglishgame.R;
import com.language.cake.praticenglishgame.dao.QuizeDao;
import com.language.cake.praticenglishgame.fragment.PlayFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class CardGroupCustomViewGroup extends BaseCustomViewGroup implements View.OnClickListener
    ,CardPlayCustomViewGroup.ViewListener, Animator.AnimatorListener {

    Fragment fragment;
    ArrayList<CardPlayCustomViewGroup> card_list;
    boolean isSound = true;
    int indexLastCardClick;
    int number_success =0;
    int sumSuccess = 0;

    @Override
    public void onLoadImageSuccess(int sumSuccess) {
        number_success = number_success + 1;
        this.sumSuccess = this.sumSuccess + sumSuccess;
        if(number_success == 6) {
            CardGroupCustomViewGroup.FragmentListener fragmentListener = (CardGroupCustomViewGroup.FragmentListener) fragment;
            fragmentListener.onLoadSuccess(this.sumSuccess);
        }
    }

    public void setCardLastClickVisible(boolean isVisible) {
        card_list.get(indexLastCardClick).setVisibility(View.INVISIBLE);
    }

    public void clearIndexCardlastClick(){
        this.indexLastCardClick = -1;
    }

    public void setCardCloseAll() {
        AnimatorSet setRightIn = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),R.animator.flip_right_in);
        AnimatorSet setLeftOut = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),R.animator.flip_left_out);


        for(int i=0;i<card_list.size();i++){
                if(card_list.get(i).isOpen) {
                    LinearLayout imgFront = (LinearLayout)card_list.get(i).findViewById(R.id.fav_grid_single_Front);
                    LinearLayout imgBack = (LinearLayout)card_list.get(i).findViewById(R.id.fav_grid_single_Back);
//                    com.language.cake.praticenglishgame.view.CardBackPlayCustomViewGroup imgBack = new com.language.cake.praticenglishgame.view.CardBackPlayCustomViewGroup(getContext());
                    setLeftOut.setTarget(imgFront);
                    setRightIn.setTarget(imgBack);
                    setLeftOut.start();
                    setRightIn.start();
                    card_list.get(i).setOpenOrCloseCard(false);
                }
        }
    }

    @Override
    public void onAnimationStart(Animator animator) {

    }

    @Override
    public void onAnimationEnd(Animator animator) {
        if(this.fragment != null) {
            CardGroupCustomViewGroup.FragmentListener fragmentListener = (CardGroupCustomViewGroup.FragmentListener) fragment;
            fragmentListener.cardClcik(this);
        }
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }

    public interface FragmentListener{
        void cardClcik(CardGroupCustomViewGroup cardGroupCustomViewGroup);
        void onLoadSuccess(int sumSuccess);
    }

    public CardGroupCustomViewGroup(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public CardGroupCustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public CardGroupCustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public CardGroupCustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.layout_group_card, this);
    }

    private void initInstances() {
        setClickable(true);
        indexLastCardClick = -1;
        card_list = new ArrayList<CardPlayCustomViewGroup>();
        card_list.add((CardPlayCustomViewGroup) findViewById(R.id.customViewGroupCard1));
        card_list.add((CardPlayCustomViewGroup)findViewById(R.id.customViewGroupCard2));
        card_list.add((CardPlayCustomViewGroup)findViewById(R.id.customViewGroupCard3));
        card_list.add((CardPlayCustomViewGroup)findViewById(R.id.customViewGroupCard4));
        card_list.add((CardPlayCustomViewGroup)findViewById(R.id.customViewGroupCard5));
        card_list.add((CardPlayCustomViewGroup)findViewById(R.id.customViewGroupCard6));

        for(CardPlayCustomViewGroup card:card_list){
            card.setOnClickListener(this);
        }
        setParentView();
        // findViewById here


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

    @Override
    public void onClick(View view) {

        CardPlayCustomViewGroup cardPlay = (CardPlayCustomViewGroup) view;

        LinearLayout imgFront = (LinearLayout)view.findViewById(R.id.fav_grid_single_Front);
        LinearLayout imgBack = (LinearLayout)view.findViewById(R.id.fav_grid_single_Back);
        if(cardPlay.isOpen)
        {
//            ObjectAnimator anim = ObjectAnimator.ofFloat(cardPlay,View.ROTATION_Y,0f);
//
//            anim.setDuration(500);
//            anim.setInterpolator(new AccelerateInterpolator());
//            anim.start();

            AnimatorSet setRightIn = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),R.animator.flip_right_in);
            AnimatorSet setLeftOut = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),R.animator.flip_left_out);
            setLeftOut.setTarget(imgFront);
            setRightIn.setTarget(imgBack);
            setLeftOut.start();
            setRightIn.start();
            setRightIn.addListener(this);
            cardPlay.setOpenOrCloseCard(false);
        }
        else {
//            ObjectAnimator anim = ObjectAnimator.ofFloat(cardPlay,View.ROTATION_Y,200f);
//            anim.setDuration(500);
//            anim.setInterpolator(new AccelerateInterpolator());
//            anim.start();
            AnimatorSet setRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),R.animator.flip_right_out);
            AnimatorSet setLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),R.animator.flip_left_in);
            setRightOut.setTarget(imgBack);
            setLeftIn.setTarget(imgFront);
            setRightOut.start();
            setLeftIn.start();
            setLeftIn.addListener(this);
            cardPlay.setOpenOrCloseCard(true);
        }
        justOpenOneCard(cardPlay);

    }

    public int getIndexLastCardClick(){
        return indexLastCardClick;
    }

    public int getCardIDLastClick(int index){
        return card_list.get(index).getCardID();
    }

    public boolean getCardOpenOrClose(int index){
        return card_list.get(index).isOpen;
    }

    private void justOpenOneCard(CardPlayCustomViewGroup cardOpen){
        AnimatorSet setRightIn = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),R.animator.flip_right_in);
        AnimatorSet setLeftOut = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),R.animator.flip_left_out);


        for(int i=0;i<card_list.size();i++){
            if(card_list.get(i) != cardOpen){
                if(card_list.get(i).isOpen) {
                    LinearLayout imgFront = (LinearLayout)card_list.get(i).findViewById(R.id.fav_grid_single_Front);
                    LinearLayout imgBack = (LinearLayout)card_list.get(i).findViewById(R.id.fav_grid_single_Back);
//                    com.language.cake.praticenglishgame.view.CardBackPlayCustomViewGroup imgBack = new com.language.cake.praticenglishgame.view.CardBackPlayCustomViewGroup(getContext());
                    setLeftOut.setTarget(imgFront);
                    setRightIn.setTarget(imgBack);
                    setLeftOut.start();
                    setRightIn.start();
                    card_list.get(i).setOpenOrCloseCard(false);
                }
            }
            else {
                this.indexLastCardClick = i;
            }
        }
    }

    public void openAll(){
        AnimatorSet setRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),R.animator.flip_right_out);
        AnimatorSet setLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),R.animator.flip_left_in);


        for(int i=0;i<card_list.size();i++){
                if(!(card_list.get(i).isOpen)) {
                    LinearLayout imgFront = (LinearLayout)card_list.get(i).findViewById(R.id.fav_grid_single_Front);
                    LinearLayout imgBack = (LinearLayout)card_list.get(i).findViewById(R.id.fav_grid_single_Back);
//                    com.language.cake.praticenglishgame.view.CardBackPlayCustomViewGroup imgBack = new com.language.cake.praticenglishgame.view.CardBackPlayCustomViewGroup(getContext());

                    setRightOut.setTarget(imgBack);
                    setLeftIn.setTarget(imgFront);
                    setRightOut.start();
                    setLeftIn.start();
                    setLeftIn.addListener(this);
                    card_list.get(i).setOpenOrCloseCard(true);
                }
        }
    }



    public void setFragment(Fragment fragment){
        this.fragment = fragment;
    }

    public void setSound(boolean isSound){
        this.isSound = isSound;
        for(int i=0;i<card_list.size();i++){
//                    LinearLayout imgFront = (LinearLayout)card_list.get(i).findViewById(R.id.fav_grid_single_Front);
//                    LinearLayout imgBack = (LinearLayout)card_list.get(i).findViewById(R.id.fav_grid_single_Back);
//                    com.language.cake.praticenglishgame.view.CardBackPlayCustomViewGroup imgBack = new com.language.cake.praticenglishgame.view.CardBackPlayCustomViewGroup(getContext());

            card_list.get(i).setIsSound(isSound);
        }
    }

    public void setParentView(){
        for(int i=0;i<card_list.size();i++){
//                    LinearLayout imgFront = (LinearLayout)card_list.get(i).findViewById(R.id.fav_grid_single_Front);
//                    LinearLayout imgBack = (LinearLayout)card_list.get(i).findViewById(R.id.fav_grid_single_Back);
//                    com.language.cake.praticenglishgame.view.CardBackPlayCustomViewGroup imgBack = new com.language.cake.praticenglishgame.view.CardBackPlayCustomViewGroup(getContext());

            card_list.get(i).setParentView(this);
        }
    }

    public void setDatas(QuizeDao[] playDatas, boolean isShowImage,boolean isShowText, boolean isPlaySound){
        QuizeDao[] q = randomCard(playDatas);
        for(int i=0;i<card_list.size();i++){
//                    LinearLayout imgFront = (LinearLayout)card_list.get(i).findViewById(R.id.fav_grid_single_Front);
//                    LinearLayout imgBack = (LinearLayout)card_list.get(i).findViewById(R.id.fav_grid_single_Back);
//                    com.language.cake.praticenglishgame.view.CardBackPlayCustomViewGroup imgBack = new com.language.cake.praticenglishgame.view.CardBackPlayCustomViewGroup(getContext());
            card_list.get(i).setTextViewVocap(q[i].getTextVocap(),isShowText);
            card_list.get(i).setImageVocap(q[i].getUrl(),isShowImage);
            card_list.get(i).setCardID(q[i].getId());
        }
    }

    public QuizeDao[] randomCard(QuizeDao[] playData) {
        ArrayList<QuizeDao> listPlayData = new ArrayList<>();
        ArrayList<QuizeDao> listPlayDataTemp = new ArrayList<>();
        for(int i=0;i<card_list.size();i++){
            listPlayData.add(playData[i]);
        }
        while (listPlayData.size() != 0){
            int index = getRandom(listPlayData.size());
            QuizeDao q = listPlayData.get(index);
            listPlayDataTemp.add(q);
            listPlayData.remove(index);

        }
        return listPlayDataTemp.toArray(new QuizeDao[listPlayDataTemp.size()]);
    }

    private int getRandom(int size) {
        int rnd = new Random().nextInt(size);
        return rnd;
    }
}
