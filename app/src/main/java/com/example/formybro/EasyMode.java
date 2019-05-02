package com.example.formybro;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Random;

public class EasyMode extends Mode implements Runnable {

    int currentHeap = -1;
    int currentStone;

    public void generationStartValue(){
        heap = GameMode.getHeapInt();
        stone = GameMode.getStoneInt();
        heapValue = new int[heap];
        for (int i = 0; i < heap; i++){
            heapValue[i] = random.nextInt(stone);
            heapValue[i] += 1;
        }
    }

    public void computerRun() {
        int count = 0;
        for (int i = 0; i < heap; i++) {
            if (heapValue[i] != 0) {
                count++;
                currentHeap = i;
            }
        }
        if ((count == 1)) {
            currentStone = heapValue[currentHeap];
        }
        else {
            do {
                currentHeap = random.nextInt(heap);
            } while (heapValue[currentHeap] <= 0);
            currentStone = random.nextInt(heapValue[currentHeap]) + 1;
        }
        btns[currentHeap].setBackgroundResource(R.drawable.btn_computer);
        btns[currentHeap].postDelayed(this, 1000);
    }

    @Override
    public void run() {
        heapValue[currentHeap] -= currentStone;
        writeBtns(currentHeap);
        btns[currentHeap].setBackgroundResource(R.drawable.heap);
        if (isEnd()) {
            createDialog("Боюсь, вы проиграли");
        }
    }
}
