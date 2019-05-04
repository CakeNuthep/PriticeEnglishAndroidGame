package com.language.cake.praticenglishgame.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.language.cake.praticenglishgame.ControlGame;
import com.language.cake.praticenglishgame.R;
import com.language.cake.praticenglishgame.dao.QuizeDao;
import com.language.cake.praticenglishgame.fragment.QuizeFragment;

import java.util.List;

public class QuizeActivity extends AppCompatActivity implements QuizeFragment.FragmentListener{

    int level;
    QuizeFragment quizeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.level = getIntent().getIntExtra("Level",0);

        setContentView(R.layout.activity_quize);

        if(savedInstanceState == null)
        {
            initInstance();
        }
        else
        {

        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    private void initInstance()
    {
            if(quizeFragment == null) {
                quizeFragment = QuizeFragment.newInstance();
                quizeFragment.setArg(this.level);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.contentContainer2, quizeFragment)
                        .commit();
            }

    }

    @Override
    public void onGridViewClickItem(List<QuizeDao> listQuizedao,int level) {
        Intent intent = new Intent(QuizeActivity.this, PlayActivity.class);
        QuizeDao[] q = listQuizedao.toArray(new QuizeDao[listQuizedao.size()]);
        intent.putExtra("MainLevel",this.level);
        intent.putExtra("QuizeLevel",level);
        intent.putExtra("QuizData",q);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
}
