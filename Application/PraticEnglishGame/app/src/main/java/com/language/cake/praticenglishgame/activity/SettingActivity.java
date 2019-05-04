package com.language.cake.praticenglishgame.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.language.cake.praticenglishgame.R;
import com.language.cake.praticenglishgame.fragment.MainFragment;
import com.language.cake.praticenglishgame.fragment.SettingFragment;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer4, SettingFragment.newInstance())
                    .commit();
        }
    }
}
