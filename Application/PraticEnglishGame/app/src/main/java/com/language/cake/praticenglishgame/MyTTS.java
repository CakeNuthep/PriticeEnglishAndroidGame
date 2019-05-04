package com.language.cake.praticenglishgame;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import java.util.Locale;

/**
 * Created by 005357 on 9/22/2017.
 */

public class MyTTS extends UtteranceProgressListener implements TextToSpeech.OnInitListener{

    public static MyTTS myTTS;

    public static MyTTS getInstance(Context context){
        if(myTTS == null){
            myTTS = new MyTTS(context);

        }
        return myTTS;
    }


    private Context context;
    private TextToSpeech tts;
    private Locale locale = Locale.getDefault();
    private String enginePackageName;
    private String message;
    private boolean isRunning;
    private int speakCount;

    public MyTTS(Context context){
        this.context = context;
        initTextToSpeech();
    }

    public void initTextToSpeech(){
        if(enginePackageName != null && !enginePackageName.isEmpty()){
            tts = new TextToSpeech(context,this,enginePackageName);
        }else {
            tts = new TextToSpeech(context,this);
        }
    }

    public  void speak(String message){
        this.message = message;

        if(tts == null || !isRunning){
            speakCount = 0;

            initTextToSpeech();

            isRunning = true;
        }else {
            startSpeak();
        }
    }



    public MyTTS setEngine(String packageName) {
        enginePackageName = packageName;
        return this;
    }

    public MyTTS setLocale(Locale locale) {
        this.locale = locale;
        return this;
    }

    private void startSpeak() {
        speakCount++;

        if (locale != null) {
            tts.setLanguage(locale);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null, "");
        } else {
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    private void clear() {
        speakCount--;

        if (speakCount == 0) {
            tts.shutdown();
            isRunning = false;
        }
    }



    public void playEarcon(String earcon) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.playEarcon(earcon, TextToSpeech.QUEUE_FLUSH, null, "");
        } else {
            tts.playEarcon(earcon, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void onStart(String s) {

    }

    @Override
    public void onDone(String s)
    {

        clear();
    }

    @Override
    public void onError(String s) {
        clear();
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            startSpeak();
        }
    }
}
