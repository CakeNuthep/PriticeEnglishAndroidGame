package com.language.cake.praticenglishgame.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.ContentObservable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.language.cake.praticenglishgame.ControlGame;
import com.language.cake.praticenglishgame.Dialog.NetworkPloblemDialog;
import com.language.cake.praticenglishgame.R;
import com.language.cake.praticenglishgame.adapter.PhotoListAdapter;
import com.language.cake.praticenglishgame.adapter.QuizeGridViewAdapter;
import com.language.cake.praticenglishgame.dao.MainQuizeDao;
import com.language.cake.praticenglishgame.dao.PhotoItemCollectionDao;
import com.language.cake.praticenglishgame.dao.QuizeDao;
import com.language.cake.praticenglishgame.manager.HttpManager;
import com.language.cake.praticenglishgame.view.QuizeCustomViewGroup;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class QuizeFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    int level;
    MainQuizeDao quizeData;
    GridView gridView;
    FrameLayout loadFrameLayout;
    QuizeGridViewAdapter gridViewAdapterAdapter;
    TextView textViewDateTimeUpdate;
    private com.inthecheesefactory.thecheeselibrary.widget.AdjustableImageButton imgbtn_back;


    public void writeData(String jsonData){
        SharedPreferences sp1 = this.getActivity().getSharedPreferences(ControlGame.parentQuizeFolderName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sp1.edit();
        editor1.putString(ControlGame.childOfParentQuizeFolderName , jsonData);
        editor1.apply();
    }

    public MainQuizeDao readData(){
        SharedPreferences sp =  this.getActivity().getSharedPreferences(ControlGame.parentQuizeFolderName, Context.MODE_PRIVATE);


        String jsonInString = sp.getString(ControlGame.childOfParentQuizeFolderName,"");
        MainQuizeDao q = null;
        if(jsonInString != ""){
            Gson gson = new Gson();
            q = gson.fromJson(jsonInString,MainQuizeDao.class);
        }
        return q;
    }

    public SharedPreferences getSharedPreferencesGame(){
        SharedPreferences sp;
        if(this.level == ControlGame.LEVEL_EASY) {
            sp = this.getActivity().getSharedPreferences(ControlGame.ParentEasyFolderName, Context.MODE_PRIVATE);
        }
        else if(this.level == ControlGame.LEVEL_MEDIUM) {
            sp = this.getActivity().getSharedPreferences(ControlGame.ParentMediumFolderName, Context.MODE_PRIVATE);
        }
        else{
            sp = this.getActivity().getSharedPreferences(ControlGame.ParentHardFolderName, Context.MODE_PRIVATE);
        }
        return sp;
    }

    public int readCurrentLevel(){
        SharedPreferences sp = getSharedPreferencesGame();

        int currentLevel = sp.getInt(ControlGame.ChildCurrentLevelName,0);
        return currentLevel;
    }


    public int readRateHeartLevel(int level){
        SharedPreferences sp = getSharedPreferencesGame();


        int rateHeart = sp.getInt(String.format("%s%d",ControlGame.ChildRateHeartFolderName,level),0);
        return rateHeart;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        Toast.makeText(getContext(),String.format("I just click %d",i), Toast.LENGTH_SHORT).show();
        if(view != null){
            QuizeCustomViewGroup viewGroup = (QuizeCustomViewGroup)view;
            ControlGame.quizeData quizeData = viewGroup.getQuizeData();
            if(!quizeData.isRock) {
                FragmentListener listener = (FragmentListener) getActivity();
                listener.onGridViewClickItem(this.quizeData.getData().get(i),i);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view == imgbtn_back){
            getActivity().finish();
        }
    }

    public interface FragmentListener{
        void onGridViewClickItem(List<QuizeDao> listQuizeDao,int level);
    }

    public QuizeFragment() {
        super();
    }

    public static QuizeFragment newInstance() {
        QuizeFragment fragment = new QuizeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            level = bundle.getInt("Level",1);

        }

        View rootView;
        if(level== ControlGame.LEVEL_EASY){
             rootView = inflater.inflate(R.layout.fragment_quize_easy, container, false);
        }
        else if(level == ControlGame.LEVEL_MEDIUM){
             rootView = inflater.inflate(R.layout.fragment_quize_medium, container, false);
        }
        else
        {
             rootView = inflater.inflate(R.layout.fragment_quize_hard, container, false);
        }

        initInstances(rootView);
        return rootView;
    }

    private void initialAtapter(){
        final ControlGame.quizeData[] quizeDatas = new ControlGame.quizeData[quizeData.getData().size()];

        int currentLevel = readCurrentLevel();
        for(int i=0;i<quizeDatas.length;i++){
            if(i <= currentLevel) {
                int rateHeart = readRateHeartLevel(i);
                ControlGame.quizeData datas = new ControlGame.quizeData();
                datas.isRock = false;
                datas.numberQuize = i+1;
                datas.rateHeart = rateHeart;
                datas.url = "www.google.com";
                quizeDatas[i] = datas;
            }
            else {
                ControlGame.quizeData datas = new ControlGame.quizeData();
                datas.isRock = true;
                datas.numberQuize = i+1;
                datas.rateHeart = 0;
                datas.url = "www.google.com";
                quizeDatas[i] = datas;
            }
        }


        gridViewAdapterAdapter = new QuizeGridViewAdapter(quizeDatas ,this.level);
        gridView.setAdapter(gridViewAdapterAdapter);
        if(level== ControlGame.LEVEL_EASY){
            gridView.setSelection(ControlGame.indexGridViewEasy);
        }
        else if(level == ControlGame.LEVEL_MEDIUM){
            gridView.setSelection(ControlGame.indexGridViewMedium);
        }
        else
        {
            gridView.setSelection(ControlGame.indexGridViewHard);
        }
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        textViewDateTimeUpdate = (TextView)rootView.findViewById(R.id.textUpdateData);
        gridView = (GridView) rootView.findViewById(R.id.quizeGridview);
        gridView.setOnItemClickListener(this);

        loadFrameLayout = (FrameLayout) rootView.findViewById(R.id.loadFrameLayoutFragment);
        imgbtn_back = (com.inthecheesefactory.thecheeselibrary.widget.AdjustableImageButton)rootView.findViewById(R.id.btnBackQuizeFragment);
        imgbtn_back.setOnClickListener(this);

        Call<MainQuizeDao> call = HttpManager.getInstance().getService().loadPhotoList();
        call.enqueue(new Callback<MainQuizeDao>() {
            @Override
            public void onResponse(Call<MainQuizeDao> call, Response<MainQuizeDao> response) {
                if(response.isSuccessful()){
                    MainQuizeDao mainQuizeData = response.body();


                    Gson gson = new Gson();
                    String jsonInString = gson.toJson(mainQuizeData);
                    writeData(jsonInString);
//                    MainQuizeDao q = gson.fromJson(jsonInString,MainQuizeDao.class);


//                    Toast.makeText(Contextor.getInstance().getContext(),
//                            mainQuizeData.getData().get(0).get(0).getTextVocap(),Toast.LENGTH_SHORT)
//                            .show();
                }
                else {

                }

                loadFrameLayout.setVisibility(View.GONE);
                quizeData = readData();

                if(quizeData == null){
//                    getActivity().finish();
                    confirmFireMissiles();
                }
                else {
                    textViewDateTimeUpdate.setText(quizeData.getDateTimeUpdate());
                    initialAtapter();
                }
            }

            @Override
            public void onFailure(Call<MainQuizeDao> call, Throwable t) {
//                Toast.makeText(Contextor.getInstance().getContext(),
//                        "Can not connect Internet.",Toast.LENGTH_SHORT)
//                        .show();
                loadFrameLayout.setVisibility(View.GONE);
                quizeData = readData();

                if(quizeData == null){

//                    getActivity().finish();
                    confirmFireMissiles();
                }
                else{
                    textViewDateTimeUpdate.setText(quizeData.getDateTimeUpdate());
                    initialAtapter();
                }
            }
        });
    }

    public void confirmFireMissiles() {
        NetworkPloblemDialog newFragment = new NetworkPloblemDialog();
        newFragment.setCancelable(false);
        newFragment.show(getActivity().getFragmentManager(),"NetWorkPloblem");
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
        if(level== ControlGame.LEVEL_EASY){
            ControlGame.indexGridViewEasy = gridView.getFirstVisiblePosition();
        }
        else if(level == ControlGame.LEVEL_MEDIUM){
            ControlGame.indexGridViewMedium = gridView.getFirstVisiblePosition();
        }
        else
        {
            ControlGame.indexGridViewHard = gridView.getFirstVisiblePosition();
        }
//        if(gridView != null) {
//            outState.putInt("GridViewPosision", gridView.getFirstVisiblePosition());
//        }
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

    public void setArg(int level){
        Bundle args = new Bundle();
        args.putInt("Level", level);
        setArguments(args);
    }

}
