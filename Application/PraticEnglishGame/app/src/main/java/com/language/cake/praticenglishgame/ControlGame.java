package com.language.cake.praticenglishgame;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 005357 on 9/9/2017.
 */

public class ControlGame {
    public static int indexGridViewEasy=0;
    public static int indexGridViewMedium=0;
    public static int indexGridViewHard=0;
    public static final int LEVEL_EASY=1;
    public static final int LEVEL_MEDIUM=2;
    public static final int LEVEL_HARD=3;
    public static final int MATCH = 1;
    public static final int NOT_MATCH = -1;
    public static final int NOTTHING = 0;
    public static final int MAX_CARD_PAIR = 6;
    public static final int TIME_COUNT = 30000;
    public static float SOUND_EFFECT = 0.8f;
    public static float SOUND_TALK = 0.8f;
    public static final String parentQuizeFolderName = "MainQuizeData";
    public static final String childOfParentQuizeFolderName = "QuizeAllData";
    public static final String ParentEasyFolderName = "Easy";
    public static final String ParentMediumFolderName = "Medium";
    public static final String ParentHardFolderName = "Hard";
    public static final String ChildCurrentLevelName = "CurrentLevel";
    public static final String ChildRateHeartFolderName = "RateHeart";

    public static final int persentRandomAds = 50;
    public static final int numAds = 3;
    public static int currentNumAds = 0;


    public static class playData implements  Parcelable{
        public String urlImage;
        public String textVocap;
        public int id;

        public playData(){

        }

        protected playData(Parcel in) {
            urlImage = in.readString();
            textVocap = in.readString();
            id = in.readInt();
        }

        public static final Creator<playData> CREATOR = new Creator<playData>() {
            @Override
            public playData createFromParcel(Parcel in) {
                return new playData(in);
            }

            @Override
            public playData[] newArray(int size) {
                return new playData[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(urlImage);
            parcel.writeString(textVocap);
            parcel.writeInt(id);
        }
    }

    public static class quizeData implements Parcelable{
        public boolean isRock;
        public int rateHeart;
        public int numberQuize;
        public String url;


        protected quizeData(Parcel in) {
            isRock = in.readByte() != 0;
            rateHeart = in.readInt();
            numberQuize = in.readInt();
            url = in.readString();
        }

        public quizeData(){

        }

        public static final Creator<quizeData> CREATOR = new Creator<quizeData>() {
            @Override
            public quizeData createFromParcel(Parcel in) {
                return new quizeData(in);
            }

            @Override
            public quizeData[] newArray(int size) {
                return new quizeData[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeByte((byte) (isRock ? 1 : 0));
            parcel.writeInt(rateHeart);
            parcel.writeInt(numberQuize);
            parcel.writeString(url);
        }


    }
}
