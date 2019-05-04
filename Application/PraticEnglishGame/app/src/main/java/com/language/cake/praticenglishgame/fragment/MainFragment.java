package com.language.cake.praticenglishgame.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.language.cake.praticenglishgame.MyTTS;
import com.language.cake.praticenglishgame.R;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MainFragment extends Fragment implements View.OnClickListener {

    Button btnEasy,btnMedium,btnHard,btnSetting;
//    com.inthecheesefactory.thecheeselibrary.widget.AdjustableImageButton imgBtnSetting;
    public interface FragmentListener{
        void onButtonEasyClicked();
        void onButtonMediunClicked();
        void onButtonHardClicked();
        void onButtonSettingClicked();
    }
    public MainFragment() {
        super();
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        MyTTS.getInstance(Contextor.getInstance().getContext());
        // Init 'View' instance(s) with rootView.findViewById here
        btnEasy = (Button) rootView.findViewById(R.id.btn_easy);
        btnMedium = (Button) rootView.findViewById(R.id.btn_medium);
        btnHard = (Button) rootView.findViewById(R.id.btn_hard);
        btnSetting = (Button) rootView.findViewById(R.id.btn_setting);
        btnEasy.setOnClickListener(this);
        btnMedium.setOnClickListener(this);
        btnHard.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
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
        if(view == btnEasy)
        {
//            Toast.makeText(Contextor.getInstance().getContext(),"Result = Easy", Toast.LENGTH_SHORT).show();
            MainFragment.FragmentListener listener = (MainFragment.FragmentListener)getActivity();
            listener.onButtonEasyClicked();
        }
        else if(view == btnMedium)
        {
//            Toast.makeText(Contextor.getInstance().getContext(),"Result = Medium", Toast.LENGTH_SHORT).show();
            MainFragment.FragmentListener listener = (MainFragment.FragmentListener)getActivity();
            listener.onButtonMediunClicked();
        }
        else  if(view == btnHard)
        {
//            Toast.makeText(Contextor.getInstance().getContext(),"Result = Hard", Toast.LENGTH_SHORT).show();
            MainFragment.FragmentListener listener = (MainFragment.FragmentListener)getActivity();
            listener.onButtonHardClicked();
        }
        else if(view == btnSetting){
            MainFragment.FragmentListener listener = (MainFragment.FragmentListener)getActivity();
            listener.onButtonSettingClicked();
        }
    }
}
