package com.language.cake.praticenglishgame.fragment;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.language.cake.praticenglishgame.ControlGame;
import com.language.cake.praticenglishgame.Dialog.NetworkPloblemDialog;
import com.language.cake.praticenglishgame.R;
import com.language.cake.praticenglishgame.dao.QuizeDao;
import com.language.cake.praticenglishgame.view.CardGroupCustomViewGroup;
import com.language.cake.praticenglishgame.view.HeartCustomViewGroup;

import java.util.Arrays;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class PlayFragment extends Fragment implements
        CardGroupCustomViewGroup.FragmentListener{

    CardGroupCustomViewGroup cardGroup1,cardGroup2;
    FrameLayout loadFrameLayout;
    TextView timeTextView;
    boolean isTimePause=true,isTimeStop=false;
    boolean beginClick = false;
    HeartCustomViewGroup heartCustomViewGroup;
    HandlerThread backgroundHandlerThread;
    Handler backgroundHandler;
    Handler mainHandler;
    int threadDelay;
    int mainLevel;
    int number_success =0;
    int currentNumberCardMatch = 0;
    QuizeDao[] arrayQuizeDao;
    int sumSuccess = 0;
    boolean isDestroy=false;
    boolean isLost = false, isWin = false;

    private SoundPool soundPool;
    private static final int MAX_STREAMS=50;

    private int soundMatch;
    private int soundNotMatch;
    private int soundWin;
    private int soundLost;
    private int soundWarning;

    private boolean soundPoolLoaded;
    private boolean soundMatchLoaded;
    private boolean soundNotMatchLoaded;
    private boolean soundWinLoaded;
    private boolean soundLostLoaded;
    private boolean soundWarningLoaded;

    public interface FragmentListener{
        void onTimeOut();
        void onLost();
        void onWin(int numHeartRate);
    }
    public PlayFragment() {
        super();
    }

    public static PlayFragment newInstance() {
        PlayFragment fragment = new PlayFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static PlayFragment newInstance(QuizeDao[] arrayQuizeData, int mainLevel) {
        PlayFragment fragment = new PlayFragment();
        Bundle args = new Bundle();
        Parcelable[] rawMsgs = new Parcelable[arrayQuizeData.length];
        if (arrayQuizeData != null) {
            rawMsgs = Arrays.copyOf(arrayQuizeData, arrayQuizeData.length, Parcelable[].class);
        }
        args.putParcelableArray("RawData",rawMsgs);
        args.putInt("MainLevel",mainLevel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_play, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void readArgs(){
        Bundle args = getArguments();
        this.mainLevel = args.getInt("MainLevel");
        Parcelable[] parcelable = args.getParcelableArray("RawData");
        this.arrayQuizeDao = new QuizeDao[parcelable.length];
        if (parcelable != null) {
            this.arrayQuizeDao = Arrays.copyOf(parcelable, parcelable.length, QuizeDao[].class);
        }
        else {
            this.arrayQuizeDao = null;
        }
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        initSoundPool();
        readArgs();
        this.isDestroy = false;
//        this.isTimePause = false;
//        this.isTimeStop = false;
        this.threadDelay = 1000;
        cardGroup1 = (CardGroupCustomViewGroup)rootView.findViewById(R.id.GroupCard1);
        cardGroup2 = (CardGroupCustomViewGroup)rootView.findViewById(R.id.GroupCard2);
        cardGroup1.setFragment(this);
        cardGroup2.setFragment(this);
        timeTextView = (TextView)rootView.findViewById(R.id.timeForPlay);
        heartCustomViewGroup = (HeartCustomViewGroup)rootView.findViewById(R.id.heartCustomviewGroup);
        heartCustomViewGroup.setRateHeart(3);
        loadFrameLayout = (FrameLayout) rootView.findViewById(R.id.loadFrameLayoutPlayFragment);

//        ControlGame.playData[] playDatas = new ControlGame.playData[6];
//        for(int i=0;i<this.arrayQuizeDao.length;i++){
//            ControlGame.quizeData quizeData;
//
//            ControlGame.playData datas = new ControlGame.playData();
//            datas.urlImage = "http://pngimg.com/uploads/banana/banana_PNG850.png";
//            datas.textVocap = "Banana";
//            datas.id=i;
//            playDatas[i] = datas;
//        }
        boolean isShowImage1,isShowText1,isPlaySound1;
        boolean isShowImage2,isShowText2,isPlaySound2;
        if(this.mainLevel == ControlGame.LEVEL_EASY){
            isShowImage1 = true;
            isShowText1 = true;
            isPlaySound1 = true;

            isShowImage2 = true;
            isShowText2 = true;
            isPlaySound2 = true;
        }
        else if (this.mainLevel == ControlGame.LEVEL_MEDIUM){
            isShowImage1 = true;
            isShowText1 = false;
            isPlaySound1 = true;

            isShowImage2 = false;
            isShowText2 = true;
            isPlaySound2 = true;
        }
        else {
            isShowImage1 = true;
            isShowText1 = false;
            isPlaySound1 = false;

            isShowImage2 = false;
            isShowText2 = false;
            isPlaySound2 = true;
        }
        cardGroup1.setSound(isPlaySound1);
        cardGroup2.setSound(isPlaySound2);
        cardGroup1.setDatas(this.arrayQuizeDao,isShowImage1,isShowText1,isPlaySound1);
        cardGroup2.setDatas(this.arrayQuizeDao,isShowImage2,isShowText2,isPlaySound2);


        backgroundHandlerThread = new HandlerThread("BackgroundHandlerThread");
        backgroundHandlerThread.start();

        backgroundHandler = new Handler(backgroundHandlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                // Run with background
                Message msgMain = new Message();
                if(!isTimePause) {

                    msgMain.arg1 = msg.arg1 - threadDelay;
                    if(msgMain.arg1 <= 5000){
                        playSoundWarning();
                    }
                }
                else {
                    msgMain.arg1 = msg.arg1;
                }
                mainHandler.sendMessage(msgMain);
            }

        };

        mainHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //Run With Main Thread


                //pwOne.incrementProgress(msg.arg1);
                setTimeText(msg.arg1);
                if(!isTimeStop) {
                    if (msg.arg1 > 0) {

                        Message msgBack = new Message();
                        msgBack.arg1 = msg.arg1;
                        backgroundHandler.sendMessageDelayed(msgBack, threadDelay);
                    } else {
                        onTimeout();
                    }
                }

            }
        };

        Message msgBack = new Message();
        msgBack.arg1 = ControlGame.TIME_COUNT;
        backgroundHandler.sendMessageDelayed(msgBack, threadDelay);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.isDestroy = true;
        this.soundPool.release();
    }

    public void onTimeout()
    {
        playSoundLost();
        FragmentListener fragmentListener = (FragmentListener)getActivity();
        fragmentListener.onTimeOut();
    }

    public void onLost(){
        isLost = true;
        playSoundLost();
        FragmentListener fragmentListener = (FragmentListener)getActivity();
        fragmentListener.onLost();
    }

    public void onWin(){
        isWin = true;
        playSoundWin();
        FragmentListener fragmentListener = (FragmentListener)getActivity();
        fragmentListener.onWin(heartCustomViewGroup.getRateHeart());
    }


    private void setTimeText(int milisecond){
        int all_second = milisecond / 1000;
        int second = all_second%60;
        int minute = all_second/60;
        if(second < 0){
            second = 0;
        }
        this.timeTextView.setText(String.format("%02d:%02d",minute,second));
    }

    @Override
    public void onResume() {
        super.onResume();
        if(beginClick) {
            if(!(isLost || isWin))
            this.isTimePause = false;
        }
    }

    @Override
    public void onStart() {

        super.onStart();
        this.isTimeStop = false;
    }

    @Override
    public void onStop() {

        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.isTimeStop = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        this.isTimePause = true;
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
    public void cardClcik(CardGroupCustomViewGroup cardGroupCustomViewGroup) {
        if(!beginClick){
            this.isTimePause = false;
            beginClick = true;
        }
        if(cardGroup1 == cardGroupCustomViewGroup){
            processMatchCard();

        }else if(cardGroup2 == cardGroupCustomViewGroup){
            processMatchCard();
        }
    }

    private void initSoundPool()  {
        // With Android API >= 21.
        if (Build.VERSION.SDK_INT >= 21 ) {

            AudioAttributes audioAttrib = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            SoundPool.Builder builder= new SoundPool.Builder();
            builder.setAudioAttributes(audioAttrib).setMaxStreams(MAX_STREAMS);

            this.soundPool = builder.build();
        }
        // With Android API < 21
        else {
            // SoundPool(int maxStreams, int streamType, int srcQuality)
            this.soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        }

        // When SoundPool load complete.
        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPoolLoaded = soundMatchLoaded&soundNotMatchLoaded&soundWinLoaded&soundLostLoaded&soundWarningLoaded;
                if(!soundPoolLoaded && !isDestroy) {
                    if(status == 0) {
                        switch (sampleId) {
                            case 1:
                                soundMatchLoaded = true;
                                soundPoolLoaded = true;
                                break;
                            case 2:
                                soundNotMatchLoaded = true;
                                break;
                            case 3:
                                soundWinLoaded = true;
                                break;
                            case 4:
                                soundLostLoaded = true;
                                break;
                            case 5:
                                soundWarningLoaded = true;
                                break;
                        }
                    }
                }


                // Playing background sound.

            }
        });


        // Load the sound background.mp3 into SoundPool
        this.soundMatch= this.soundPool.load(this.getContext(), R.raw.match,1);

        // Load the sound explosion.wav into SoundPool
        this.soundNotMatch = this.soundPool.load(this.getContext(), R.raw.not_match,1);

        this.soundWin = this.soundPool.load(this.getContext(), R.raw.win,1);

        this.soundLost = this.soundPool.load(this.getContext(),R.raw.lost,1);

        this.soundWarning = this.soundPool.load(this.getContext(),R.raw.warning,1);
    }

    public void playSoundMatch()  {
        if(this.soundMatchLoaded) {
            float leftVolumn =   ControlGame.SOUND_EFFECT;
            float rightVolumn =  ControlGame.SOUND_EFFECT;
            // Play sound background.mp3
            int streamID = this.soundPool.play(this.soundMatch,leftVolumn, rightVolumn, 1, 0, 1f);

        }
    }

    public void playSoundNotMatch()  {
        if(this.soundNotMatchLoaded) {
            float leftVolumn =   ControlGame.SOUND_EFFECT;
            float rightVolumn =  ControlGame.SOUND_EFFECT;
            // Play sound background.mp3
            int streamID = this.soundPool.play(this.soundNotMatch,leftVolumn, rightVolumn, 1, 0, 1f);

        }
    }

    public void playSoundWin()  {
        if(this.soundWinLoaded) {
            float leftVolumn =   ControlGame.SOUND_EFFECT;
            float rightVolumn =  ControlGame.SOUND_EFFECT;
            // Play sound background.mp3
            int streamID = this.soundPool.play(this.soundWin,leftVolumn, rightVolumn, 1, 0, 1f);

        }
    }

    public void playSoundLost()  {
        if(this.soundLostLoaded) {
            float leftVolumn =   ControlGame.SOUND_EFFECT;
            float rightVolumn =  ControlGame.SOUND_EFFECT;
            // Play sound background.mp3
            int streamID = this.soundPool.play(this.soundLost,leftVolumn, rightVolumn, 1, 0, 1f);

        }
    }

    public void playSoundWarning()  {
        if(this.soundWarningLoaded) {
            float leftVolumn =   ControlGame.SOUND_EFFECT;
            float rightVolumn =  ControlGame.SOUND_EFFECT;
            // Play sound background.mp3
            int streamID = this.soundPool.play(this.soundWarning,leftVolumn, rightVolumn, 1, 0, 1f);

        }
    }

    public void setTimePause(){
        this.isTimePause = true;
    }

    private void processMatchCard(){
        int result = isCardMatch();
        if(result == ControlGame.MATCH){
            playSoundMatch();
            cardGroup1.setCardLastClickVisible(false);
            cardGroup2.setCardLastClickVisible(false);
            cardGroup1.clearIndexCardlastClick();
            cardGroup2.clearIndexCardlastClick();
            currentNumberCardMatch++;
            if(currentNumberCardMatch == ControlGame.MAX_CARD_PAIR){
                this.isTimePause = true;
                onWin();
            }
        }
        else if(result == ControlGame.NOT_MATCH){
            playSoundNotMatch();
            cardGroup1.setCardCloseAll();
            cardGroup2.setCardCloseAll();
            cardGroup1.clearIndexCardlastClick();
            cardGroup2.clearIndexCardlastClick();
            int number = heartCustomViewGroup.getRateHeart();
            number = number - 1;
            heartCustomViewGroup.setRateHeart(number);
            if(number < 0){
                this.isTimePause = true;
                onLost();
            }

        }
    }

    private int isCardMatch(){
        int indexCardGroup1 = cardGroup1.getIndexLastCardClick();
        int indexCardGroup2 = cardGroup2.getIndexLastCardClick();
        if(indexCardGroup1 != -1 && indexCardGroup2 != -1){
            boolean isOpenCardGroup1 = cardGroup1.getCardOpenOrClose(indexCardGroup1);
            boolean isOpenCardGroup2 = cardGroup2.getCardOpenOrClose(indexCardGroup2);
            if(isOpenCardGroup1 && isOpenCardGroup2){
                int idCardGroup1 = cardGroup1.getCardIDLastClick(indexCardGroup1);
                int idCardGroup2 = cardGroup2.getCardIDLastClick(indexCardGroup2);
                if(idCardGroup1 == idCardGroup2)
                {
                    return ControlGame.MATCH;
                }
                else {
                    return ControlGame.NOT_MATCH;
                }
            }
        }
        return ControlGame.NOTTHING;
    }


    @Override
    public void onLoadSuccess(int sumSuccess) {
        this.number_success = this.number_success + 1;
        this.sumSuccess = this.sumSuccess + sumSuccess;
        if(this.number_success == 2){
            loadFrameLayout.setVisibility(View.GONE);
            if(this.sumSuccess != 12){
                confirmFireMissiles();
            }
        }
    }

    public void confirmFireMissiles() {
        this.isTimePause=true;
        NetworkPloblemDialog newFragment = new NetworkPloblemDialog();
        newFragment.setCancelable(false);
        newFragment.show(getActivity().getFragmentManager(),"NetWorkPloblem");
    }
}
