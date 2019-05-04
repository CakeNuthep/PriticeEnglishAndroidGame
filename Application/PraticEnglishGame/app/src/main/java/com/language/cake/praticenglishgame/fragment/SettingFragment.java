package com.language.cake.praticenglishgame.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.language.cake.praticenglishgame.ControlGame;
import com.language.cake.praticenglishgame.MyTTS;
import com.language.cake.praticenglishgame.R;

import java.util.Locale;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class SettingFragment extends Fragment implements View.OnClickListener {

    private com.inthecheesefactory.thecheeselibrary.widget.AdjustableImageButton imgbtn_effectSound,imgbtn_back;
    private SeekBar seekbar_effectSound;
    public SettingFragment() {
        super();
    }

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
//        imgbtn_reset = (com.inthecheesefactory.thecheeselibrary.widget.AdjustableImageButton)rootView.findViewById(R.id.btnResetSetting);
        imgbtn_back = (com.inthecheesefactory.thecheeselibrary.widget.AdjustableImageButton)rootView.findViewById(R.id.btnBackSettingFragment);
        imgbtn_effectSound = (com.inthecheesefactory.thecheeselibrary.widget.AdjustableImageButton)rootView.findViewById(R.id.btnEffecSound);
        seekbar_effectSound = (SeekBar) rootView.findViewById(R.id.seekBar_effectSound);
//        imgbtn_musicSouund = (com.inthecheesefactory.thecheeselibrary.widget.AdjustableImageButton)rootView.findViewById(R.id.btnMusicSound);
//        seekbar_musicSound = (SeekBar)rootView.findViewById(R.id.seekBar_musicSound);

        AudioManager am = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        int amStreamMusicMaxVol = am.getStreamMaxVolume(am.STREAM_MUSIC);
        int currentIndex = am.getStreamVolume(am.STREAM_MUSIC);
        seekbar_effectSound.setProgress((int)(currentIndex * 100 / amStreamMusicMaxVol));
//        seekbar_musicSound.setProgress((int)(ControlGame.SOUND_TALK * 100));

        updateUI();
        imgbtn_effectSound.setOnClickListener(this);
        imgbtn_back.setOnClickListener(this);
//        imgbtn_musicSouund.setOnClickListener(this);
//        imgbtn_reset.setOnClickListener(this);
        seekbar_effectSound.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ControlGame.SOUND_EFFECT = progress/120.0f;
                ControlGame.SOUND_TALK = progress/100.0f;
//                if(progress == 0){
//                    imgbtn_effectSound.setImageResource(R.drawable.mute);
//                }
//                else {
//                    imgbtn_effectSound.setImageResource(R.drawable.sound);
//                }
                updateUI();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                AudioManager am = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
                int amStreamMusicMaxVol = am.getStreamMaxVolume(am.STREAM_MUSIC);
                int progress = seekBar.getProgress();
                int currentIndex = amStreamMusicMaxVol * progress / 100;
                am.setStreamVolume(am.STREAM_MUSIC, currentIndex, 0);
                testSound();
            }
        });
//        seekbar_musicSound.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                ControlGame.SOUND_TALK = progress/100.0f;
////                if(progress == 0){
////                    imgbtn_musicSouund.setImageResource(R.drawable.mute);
////                }
////                else {
////                    imgbtn_musicSouund.setImageResource(R.drawable.sound);
////                }
//                updateUI();
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });


    }

    private void testSound(){
        MyTTS.getInstance(Contextor.getInstance().getContext()).setEngine("com.google.android.tts").setLocale(Locale.ENGLISH).speak("test");
    }

    private void updateUI() {
        if(seekbar_effectSound.getProgress() == 0){
            imgbtn_effectSound.setImageResource(R.drawable.mute);
        }
        else
        {
            imgbtn_effectSound.setImageResource(R.drawable.sound);
        }

//        if(seekbar_musicSound.getProgress() == 0){
//            imgbtn_musicSouund.setImageResource(R.drawable.mute);
//        }
//        else
//        {
//            imgbtn_musicSouund.setImageResource(R.drawable.sound);
//        }
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
    public void onDestroy() {
        super.onDestroy();
//        SharedPreferences sp = getContext().getSharedPreferences("SettingCustomAppliccation", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putFloat("VolumeEffectSound", ControlGame.SOUND_EFFECT);
//        editor.putFloat("VolumeTalkSound",ControlGame.SOUND_TALK);
//        editor.apply();
    }

    @Override
    public void onClick(View view) {
        if(view == imgbtn_effectSound){
//            SharedPreferences sp =  getContext().getSharedPreferences("SettingDefaultAppliccation", Context.MODE_PRIVATE);
//            ControlGame.SOUND_EFFECT = 0;
//            ControlGame.SOUND_TALK = 0;
//            seekbar_effectSound.setProgress((int)(ControlGame.SOUND_EFFECT * 100));
//            seekbar_musicSound.setProgress((int)(ControlGame.SOUND_TALK * 100));
            testSound();
            updateUI();
        }
        else if(view == imgbtn_back){
            getActivity().finish();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
