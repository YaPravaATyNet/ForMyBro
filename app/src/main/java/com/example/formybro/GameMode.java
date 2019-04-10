package com.example.formybro;

public class GameMode {
    private static int heap = 6;
    private static int stone = 15;

    public static int getHeapInt() {
        return heap;
    }

    public static String getHeapString() {
        return Integer.toString(heap);
    }

    public static int getStoneInt() {
        return stone;
    }

    public static String getStoneString() {
        return Integer.toString(stone);
    }

    public static void setHeap(int heap) {
        GameMode.heap = heap;
    }

    public static void setStones(int stone) {
        GameMode.stone = stone;
    }
}
