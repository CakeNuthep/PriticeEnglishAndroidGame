package com.language.cake.praticenglishgame.activity;

import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.language.cake.praticenglishgame.ControlGame;
import com.language.cake.praticenglishgame.R;
import com.language.cake.praticenglishgame.dao.QuizeDao;
import com.language.cake.praticenglishgame.fragment.FragmentLost;
import com.language.cake.praticenglishgame.fragment.FragmentWin;
import com.language.cake.praticenglishgame.fragment.PlayFragment;

import java.util.Arrays;
import java.util.Random;

public class PlayActivity extends AppCompatActivity implements
        PlayFragment.FragmentListener{

    QuizeDao[] listQuizedao;
    PlayFragment playFragment;
    FragmentLost lostFragment;
    FragmentWin winFragment;
    int quizelevel;
    int mainLevel;
    InterstitialAd iAd;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.listQuizedao = getIntent().getParcelableExtra("QuizData");
        MobileAds.initialize(this);

        AdRequest.Builder adBuilder = new AdRequest.Builder();
//        adBuilder.addTestDevice("3EC3C9E0A765A6EF65A6F4C9A0BA6E68");
        adBuilder.tagForChildDirectedTreatment(true);
        AdRequest adRequest = adBuilder.build();

        iAd = new InterstitialAd(this);
        iAd.setAdUnitId(getString(R.string.UnitID));
        iAd.loadAd(adRequest);
        iAd.setAdListener(new AdListener() {
            public void onAdClosed() {
//                finish();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Log.d("Ads test",String.format("%d",i));
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d("Ads test","Loaded");
            }
        });

        this.quizelevel = getIntent().getIntExtra("QuizeLevel",-1);
        this.mainLevel = getIntent().getIntExtra("MainLevel",-1);
        Parcelable[] rawMsgs =  getIntent().getParcelableArrayExtra("QuizData");
        if (rawMsgs != null) {
            listQuizedao = Arrays.copyOf(rawMsgs, rawMsgs.length, QuizeDao[].class);
        }
        setContentView(R.layout.activity_play);

        if(savedInstanceState == null)
        {
            initInstance();
        }
        else
        {

        }
    }

    private void initInstance()
    {
        if(playFragment == null) {
            playFragment = PlayFragment.newInstance(this.listQuizedao,this.mainLevel);
            //playFragment.setArg(this.level);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer3, playFragment)
                    .commit();
        }
    }

    public void showAds(){
        if(iAd.isLoaded()) {
            Random rand = new Random();
            int  n = rand.nextInt(100) + 1;
            if(ControlGame.currentNumAds < ControlGame.numAds && n < ControlGame.persentRandomAds) {
                ControlGame.currentNumAds = ControlGame.currentNumAds + 1;
                iAd.show();
            }
        }
    }


    @Override
    public void onTimeOut() {
        showAds();
        addLostFragment();
//        Toast.makeText(this,String.format("Time Out"), Toast.LENGTH_SHORT).show();
//        finish();
    }

    @Override
    public void onLost() {
        showAds();
        addLostFragment();
//        Toast.makeText(this,String.format("LOST"), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWin(int numHeartRate) {
        showAds();
        int currentLevel = readCurrentLevel();
        int rateHeart = readRateHeartLevel(this.quizelevel);
        if(currentLevel== this.quizelevel) {
            writeCurrentLevel(this.quizelevel);
        }
        if(rateHeart < numHeartRate) {
            writeRateHeartLevel(this.quizelevel, numHeartRate);
        }
        addWinFragment(numHeartRate);
//        Toast.makeText(this,String.format("WIN Level: %d numheartRate: %d",quizelevel,numHeartRate), Toast.LENGTH_SHORT).show();
    }

    public SharedPreferences getSharedPreferencesGame(){
        SharedPreferences sp;
        if(this.mainLevel == ControlGame.LEVEL_EASY) {
            sp = getSharedPreferences(ControlGame.ParentEasyFolderName, this.MODE_PRIVATE);
        }
        else if(this.mainLevel == ControlGame.LEVEL_MEDIUM) {
            sp = getSharedPreferences(ControlGame.ParentMediumFolderName, this.MODE_PRIVATE);
        }
        else{
            sp = getSharedPreferences(ControlGame.ParentHardFolderName, this.MODE_PRIVATE);
        }
        return sp;
    }

    public void writeCurrentLevel(int currentLevel){
        SharedPreferences sp1 = getSharedPreferencesGame();
        SharedPreferences.Editor editor1 = sp1.edit();
        editor1.putInt(ControlGame.ChildCurrentLevelName , currentLevel+1);
        editor1.apply();
    }


    public void writeRateHeartLevel(int level,int rateHeart){
        SharedPreferences sp =  getSharedPreferencesGame();
        SharedPreferences.Editor editor1 = sp.edit();
        editor1.putInt(String.format("%s%d",ControlGame.ChildRateHeartFolderName,level) , rateHeart);
        editor1.apply();
    }

    public int readCurrentLevel(){
        SharedPreferences sp =  getSharedPreferencesGame();


        int currentLevel = sp.getInt(ControlGame.ChildCurrentLevelName,0);
        return currentLevel;
    }

    private void addLostFragment()
    {
//        Calendar c = Calendar.getInstance();
//        ControlerGame.SECONDEND =  c.get(Calendar.SECOND);

        if(lostFragment == null) {
            lostFragment = FragmentLost.newInstance();
//            processWheel.setTimer(this.timerWheel);
            String tagLostFragmnet = "lostFragment";
            getSupportFragmentManager().beginTransaction()
                        .add(R.id.contentContainer3, lostFragment, tagLostFragmnet)
                        .commit();
        }
    }

    private void removeLostFragment()
    {
        if(lostFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .remove(lostFragment)
                        .commit();
                lostFragment = null;
        }
    }

    private void addWinFragment(int rateHeart)
    {
//        Calendar c = Calendar.getInstance();
//        ControlerGame.SECONDEND =  c.get(Calendar.SECOND);
        if(winFragment == null) {
            winFragment = FragmentWin.newInstance(rateHeart);
//            processWheel.setTimer(this.timerWheel);
            String tagWinFragmnet = "winFragment";
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.contentContainer3, winFragment, tagWinFragmnet)
                        .commit();
        }
    }

    private void removeWinFragment()
    {
        if(winFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .remove(winFragment)
                        .commit();
                winFragment = null;
        }
    }

    public int readRateHeartLevel(int level){
        SharedPreferences sp =  getSharedPreferencesGame();


        int rateHeart = sp.getInt(String.format("%s%d",ControlGame.ChildRateHeartFolderName,level),0);
        return rateHeart;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(this.playFragment != null) {
            outState.putBoolean("IsPlayFragment", true);
            getSupportFragmentManager().putFragment(outState, "PlayFragment", this.playFragment);
        }
        else
        {
            outState.putBoolean("IsPlayFragment", false);
        }

        if(this.winFragment != null) {
            outState.putBoolean("IsWinFragment", true);
            getSupportFragmentManager().putFragment(outState, "WinFragment", this.winFragment);
        }
        else
        {
            outState.putBoolean("IsWinFragment", false);
        }

        if(this.lostFragment != null) {
            outState.putBoolean("IsLostFragment", true);
            getSupportFragmentManager().putFragment(outState, "LostFragment", this.lostFragment);
        }
        else
        {
            outState.putBoolean("IsLostFragment", false);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState.getBoolean("IsPlayFragment"))
        {
            this.playFragment = (PlayFragment) getSupportFragmentManager().getFragment(savedInstanceState,"PlayFragment");
        }

        if(savedInstanceState.getBoolean("IsWinFragment"))
        {
            this.winFragment = (FragmentWin) getSupportFragmentManager().getFragment(savedInstanceState,"WinFragment");
            //this.playFragment.onPause();
        }

        if(savedInstanceState.getBoolean("IsLostFragment"))
        {
            this.lostFragment = (FragmentLost) getSupportFragmentManager().getFragment(savedInstanceState,"LostFragment");
            //this.playFragment.onPause();
        }
    }
}
