package com.example.formybro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

public class HardMode extends Mode implements Runnable{

    int currentHeap;
    int newStoneValue;


    @Override
    public void generationStartValue() {
        int xOr = 0;
        heap = GameMode.getHeapInt();
        stone = GameMode.getStoneInt();
        heapValue = new int[heap];
        do {
            for (int i = 0; i < heap - 1; i++){
                heapValue[i] = random.nextInt(stone);
                heapValue[i] += 1;
                xOr ^= heapValue[i];
            }
        } while ((xOr == 0) || (xOr > stone));
        heapValue[heap - 1] = xOr;
    }

    @Override
    public void computerRun() {
        ArrayList<Integer> needHeaps = new ArrayList<>();
        int xOr = 0;
        for(int i = 0; i < heap; i++) {
            xOr ^= heapValue[i];
        }
        int highestBit = Integer.highestOneBit(xOr);
        for (int i = 0; i < heap; i++) {
            if ((heapValue[i] | highestBit) == heapValue[i]) {
                needHeaps.add(i);
            }
        }
        currentHeap = needHeaps.get(random.nextInt(needHeaps.size()));
        int xOrNew = 0;
        for (int i = 0; i < heap; i++) {
            if (i != currentHeap) {
                xOrNew ^= heapValue[i];
            }
        }
        newStoneValue = xOrNew;
        btns[currentHeap].setBackgroundResource(R.drawable.btn_computer);
        btns[currentHeap].postDelayed(this, 1000);
    }

    @Override
    public void run() {
        heapValue[currentHeap] = newStoneValue;
        writeBtns(currentHeap);
        btns[currentHeap].setBackgroundResource(R.drawable.heap);
        if (isEnd()) {
            createDialog("Боюсь, вы проиграли");
        }
    }
}
