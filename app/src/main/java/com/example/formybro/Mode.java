package com.example.formybro;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Random;

public abstract class Mode extends AppCompatActivity {

    int heap;
    int stone;
    int[] heapValue;
    Random random = new Random();
    TableLayout tableLayout;
    ToggleButton[] btns;
    EditText editText;
    Button btnPlus;
    final int heapInRow = 3;
    int btnOn = -1;
    int currentStoneValue = 1;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);

        int heightScreen = this.getResources().getConfiguration().screenHeightDp;
        if (heightScreen < 620) {
            ImageView nameGameInMode = (ImageView) findViewById(R.id.name_game_in_mode);
            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mode);
            if (GameMode.getHeapInt() < 7) {
                nameGameInMode.setImageDrawable(null);
                nameGameInMode.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 100));
            }
            else {
                mainLayout.removeView(nameGameInMode);
            }
        }

        generationStartValue();
        tableLayout = (TableLayout) findViewById(R.id.table);
        tableLayout.setStretchAllColumns(true);
        tableLayout.setShrinkAllColumns(true);
        btns = new ToggleButton[heap];
        int rows = (1 + (heap - 1) / heapInRow);
        for (int i = 0; i < rows; i++) {
            TableRow tableRow = new TableRow(this);
            TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);
            int margin = (int) getResources().getDimension(R.dimen.marg);
            layoutParams.setMargins(0, margin, 0, margin);
            tableRow.setLayoutParams(layoutParams);
            int columns = Math.min(heapInRow, heap - i * heapInRow);
            for (int j = 0; j < columns; j++) {
                final ToggleButton btn = new ToggleButton(this);
                final int numberHeap = i * heapInRow + j;
                btn.setText(Integer.toString(heapValue[numberHeap]));
                btn.setTextOn(Integer.toString(heapValue[numberHeap]));
                btn.setTextOff(Integer.toString(heapValue[numberHeap]));
                btn.setTextColor((int) getResources().getColor(R.color.color));
                btn.setTextSize(getResources().getDimension(R.dimen.digit));
                btn.setBackgroundResource(R.drawable.heap);
                btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            if (btnOn != -1 && btnOn != numberHeap) {
                                btns[btnOn].setChecked(false);
                            }
                            btnOn = numberHeap;
                        } else {
                            btnOn = -1;
                        }
                    }
                });
                tableRow.addView(btn, j);
                btns[numberHeap] = btn;
            }
            tableLayout.addView(tableRow, i);

            editText = (EditText) findViewById(R.id.enter);
            editText.setText(Integer.toString(currentStoneValue));
            editText.clearFocus();
            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editText.setCursorVisible(true);
                }
            });
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    editText.setCursorVisible(false);
                    return false;
                }
            });
            btnPlus = (Button) findViewById(R.id.plus);
        }
    }

    public abstract void generationStartValue();
    public abstract void computerRun();

    public void writeBtns(int index) {
        btns[index].setText(Integer.toString(heapValue[index]));
        btns[index].setTextOn(Integer.toString(heapValue[index]));
        btns[index].setTextOff(Integer.toString(heapValue[index]));
    }

    public void close(View v) {
        finish();
    }

    public void restart(View v) {
        generationStartValue();
        for (int i = 0; i < heap; i++) {
            writeBtns(i);
        }
    }

    public void plus(View v) {
        int current;
        if (editText.getText().toString().equals("")) {
            current = stone;
        }
        else {
            current = Integer.parseInt(editText.getText().toString());
        }
        current++;
        int maxValue = stone;
        if (btnOn != -1) {
            maxValue = heapValue[btnOn];
        }
        if (current > maxValue) {
            current = 1;
        }
        currentStoneValue = current;
        editText.setText(Integer.toString(currentStoneValue));
    }

    public void minus(View v) {
        int current;
        if (editText.getText().toString().equals("")) {
            current = 1;
        }
        else {
            current = Integer.parseInt(editText.getText().toString());
        }
        current--;
        int maxValue = stone;
        if (btnOn != -1) {
            maxValue = heapValue[btnOn];
        }
        if ((current < 1) || (current > maxValue)) {
            current = maxValue;
        }
        currentStoneValue = current;
        editText.setText(Integer.toString(currentStoneValue));
    }

    public boolean isEnd() {
        for (int i = 0; i < heap; i++){
            if (heapValue[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public void apply(View v) {
        if (editText.getText().toString().equals("")) {
            Toast.makeText(v.getContext(), "Значение не введено", Toast.LENGTH_SHORT).show();
            return;
        }
        int enterStone = Integer.parseInt(editText.getText().toString());
        if (btnOn == -1) {
            Toast.makeText(v.getContext(), "Кучка не выбрана", Toast.LENGTH_SHORT).show();
            return;
        }
        if ((enterStone > heapValue[btnOn]) || (enterStone < 1)) {
            Toast.makeText(v.getContext(), "Введено неправильное значение", Toast.LENGTH_SHORT).show();
            return;
        }
        heapValue[btnOn] -= enterStone;
        writeBtns(btnOn );
        btns[btnOn].setChecked(false);
        if (isEnd()) {
            createDialog("Хорошо, хорошо, вы выиграли!");
        }
        else {
            computerRun();
        }
    }

    public void createDialog(String str) {
        final AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = this.getLayoutInflater().inflate(R.layout.activity_dialog, null);
        TextView textView = (TextView)view.findViewById(R.id.res);
        textView.setText(str);
        ImageButton btnRestart = (ImageButton)view.findViewById(R.id.btn_restart);
        ImageButton btnClose = (ImageButton)view.findViewById(R.id.btn_close);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restart(v);
                dialog.dismiss();
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             dialog.dismiss();
            }
        });
    }
}
