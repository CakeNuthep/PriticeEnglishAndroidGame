package com.language.cake.praticenglishgame.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.language.cake.praticenglishgame.ControlGame;
import com.language.cake.praticenglishgame.R;
import com.language.cake.praticenglishgame.fragment.MainFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.FragmentListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, MainFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void onButtonEasyClicked() {
        Intent intent = new Intent(MainActivity.this, QuizeActivity.class);
        intent.putExtra("Level", ControlGame.LEVEL_EASY);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    @Override
    public void onButtonMediunClicked() {
        Intent intent = new Intent(MainActivity.this, QuizeActivity.class);
        intent.putExtra("Level",ControlGame.LEVEL_MEDIUM);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    @Override
    public void onButtonHardClicked() {
        Intent intent = new Intent(MainActivity.this, QuizeActivity.class);
        intent.putExtra("Level",ControlGame.LEVEL_HARD);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    @Override
    public void onButtonSettingClicked() {
        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
}
