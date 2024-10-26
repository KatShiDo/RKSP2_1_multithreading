package org.example;

public class Task1 {
    public static void main(String[] args) {
        CustomArray array = CustomArray.createArray();
        Runtime runtime = Runtime.getRuntime();

        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        long startTime = System.nanoTime();

        int min = array.findMin();

        long endTime = System.nanoTime();
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();

        long duration = (endTime - startTime) / 1000000;
        long memoryUsed = (memoryAfter - memoryBefore) / 1024;

        System.out.println("Sync:\nTime: " + duration + " ms\nMemory: " + memoryUsed + " KB\nResult: " + min);
        System.out.println();

        memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        startTime = System.nanoTime();
        min = array.findMinMultiThread();
        endTime = System.nanoTime();
        memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        duration = (endTime - startTime) / 1000000;
        memoryUsed = (memoryAfter - memoryBefore)  / 1024;
        System.out.println("\nMulti-thread:\nTime: " + duration + " ms\nMemory: " + memoryUsed + " KB\nResult: " + min);

        memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        startTime = System.nanoTime();
        min = array.findMinFork();
        endTime = System.nanoTime();
        memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        duration = (endTime - startTime) / 1000000;
        memoryUsed = (memoryAfter - memoryBefore)  / 1024;
        System.out.println("\nFork:\nTime: " + duration + " ms\nMemory: " + memoryUsed + " KB\nResult: " + min);
    }
}