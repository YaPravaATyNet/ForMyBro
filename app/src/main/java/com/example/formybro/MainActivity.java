package com.example.formybro;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //public final static String mode_flag = "GAME_MODE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int heightScreen = this.getResources().getConfiguration().screenHeightDp;
        if (heightScreen < 620) {
            LinearLayout layoutButton = (LinearLayout) findViewById(R.id.layout_button);
            layoutButton.setOrientation(LinearLayout.HORIZONTAL);
        }
    }
    public void onClickInfo(View v) {
        //Toast.makeText(v.getContext(), "Была нажата кнопочка", Toast.LENGTH_LONG).show();
        Intent intention = new Intent(v.getContext(), Info.class);
        //intention.putExtra(mode_flag, 1);
        startActivity(intention);
    }

    public void onClickRules(View v) {
        Intent intention = new Intent(v.getContext(), Rules.class);
        startActivity(intention);
    }

    public void onClickSetting(View v) {
        Intent intention = new Intent(v.getContext(), Setting.class);
        startActivity(intention);
    }

    public void onClickEasy(View v) {
        Intent intention = new Intent(v.getContext(), EasyMode.class);
        startActivity(intention);
    }

    public void onClickNormal(View v) {
        Intent intention = new Intent(v.getContext(), NormalMode.class);
        startActivity(intention);
    }

    public void onClickHard(View v) {
        Intent intention = new Intent(v.getContext(), HardMode.class);
        startActivity(intention);
    }
}

