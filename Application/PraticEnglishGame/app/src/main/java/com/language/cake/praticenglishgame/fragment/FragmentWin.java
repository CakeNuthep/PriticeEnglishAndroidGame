package com.language.cake.praticenglishgame.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.language.cake.praticenglishgame.R;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class FragmentWin extends Fragment implements View.OnClickListener {

    RelativeLayout relativeWin;
    int rateHeart;
    com.inthecheesefactory.thecheeselibrary.widget.AdjustableImageView winHeart1,winHeart2,winHeart3;
    public FragmentWin() {
        super();
    }

    public static FragmentWin newInstance(int rateHeart) {
        FragmentWin fragment = new FragmentWin();
        Bundle args = new Bundle();
        args.putInt("RateHeart",rateHeart);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_win, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        Bundle args = getArguments();
        this.rateHeart = args.getInt("RateHeart",0);
        // Init 'View' instance(s) with rootView.findViewById here
        relativeWin = (RelativeLayout)rootView.findViewById(R.id.relativeWin);
        relativeWin.setOnClickListener(this);
        winHeart1 = (com.inthecheesefactory.thecheeselibrary.widget.AdjustableImageView)rootView.findViewById(R.id.winHeart1);
        winHeart2 = (com.inthecheesefactory.thecheeselibrary.widget.AdjustableImageView)rootView.findViewById(R.id.winHeart2);
        winHeart3 = (com.inthecheesefactory.thecheeselibrary.widget.AdjustableImageView)rootView.findViewById(R.id.winHeart3);
        setRateHeart(this.rateHeart);
    }

    private void setRateHeart(int rateHeart){
        if(rateHeart >= 1){
            winHeart1.setImageResource(R.drawable.full_heart);
        }
        if(rateHeart >= 2){
            winHeart2.setImageResource(R.drawable.full_heart);
        }
        if(rateHeart >= 3){
            winHeart3.setImageResource(R.drawable.full_heart);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }

    @Override
    public void onClick(View view) {
        if(view == relativeWin){
            this.getActivity().finish();
        }
    }
}
