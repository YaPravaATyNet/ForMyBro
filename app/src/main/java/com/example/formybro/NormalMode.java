package com.example.formybro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class NormalMode extends Mode implements Runnable {

    int currentHeap = -1;
    int currentStone;

    @Override
    public void generationStartValue() {
        heap = GameMode.getHeapInt();
        stone = GameMode.getStoneInt();
        heapValue = new int[heap];
        for (int i = 0; i < heap; i++){
            heapValue[i] = random.nextInt(stone);
            heapValue[i] += 1;
        }
    }

    @Override
    public void computerRun() {
        int count = 0;
        for (int i = 0; i < heap; i++) {
            if (heapValue[i] != 0) {
                count++;
                currentHeap = i;
            }
        }

        ArrayList<Integer> needHeaps = new ArrayList<>();
        int xOr = 0;
        for(int i = 0; i < heap; i++) {
            xOr ^= heapValue[i];
        }
        if ((count <= 3) && (currentHeap != -1) && (xOr != 0)) {
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
            currentStone = heapValue[currentHeap] - xOrNew;
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
