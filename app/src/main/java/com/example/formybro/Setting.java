package com.example.formybro;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class Setting extends AppCompatActivity {

    SeekBar seekBarHeap;
    SeekBar seekBarStone;
    TextView textViewHeap;
    TextView textViewStone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        seekBarHeap = (SeekBar)findViewById(R.id.heap);
        seekBarHeap.setProgress(GameMode.getHeapInt() - 3);
        seekBarHeap.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewHeap.setText(String.valueOf(seekBar.getProgress() + 3));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textViewHeap.setText(String.valueOf(seekBar.getProgress() + 3));
            }
        });

        textViewHeap = (TextView)findViewById(R.id.count_heap);
        textViewHeap.setText(GameMode.getHeapString());

        seekBarStone = (SeekBar)findViewById(R.id.stone);
        seekBarStone.setProgress(GameMode.getStoneInt() - 15);
        seekBarStone.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewStone.setText(String.valueOf(seekBar.getProgress() + 15));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textViewStone.setText(String.valueOf(seekBar.getProgress() + 15));
            }
        }) ;

        textViewStone = (TextView) findViewById(R.id.count_stone);
        textViewStone.setText(GameMode.getStoneString());
    }

    public void save(View v) {
        GameMode.setHeap(seekBarHeap.getProgress() + 3);
        GameMode.setStones(seekBarStone.getProgress() + 15);
        finish();
    }

    public void close(View v) {
        finish();
    }
}
