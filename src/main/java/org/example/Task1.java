package org.example;

public class Task1 {
    public static void main(String[] args) {
        CustomArray array = CustomArray.createArray();

        long startTime = System.nanoTime();
        int min = array.findMin();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        System.out.println("Sync: " + duration + " ms. Result: " + min);

        startTime = System.nanoTime();
        min = array.findMinMultiThread();
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000;
        System.out.println("Multi-thread: " + duration + " ms. Result: " + min);

        startTime = System.nanoTime();
        min = array.findMinFork();
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000;
        System.out.println("Fork: " + duration + " ms. Result: " + min);
    }
}