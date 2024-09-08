package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class CustomArray {

    private final List<Integer> array;

    private CustomArray() {
        array = generateArray();
    }

    private List<Integer> generateArray() {
        List<Integer> list = new ArrayList<>();
        var random = new Random();
        for (int i = 0; i < 10000; i++) {
            int randomInt = random.nextInt();
            list.add(randomInt);
        }
        return list;
    }

    public static CustomArray createArray() {
        return new CustomArray();
    }

    public List<Integer> getArray() {
        return array;
    }

    public int findMin() {
        return findMinSync(array);
    }

    public int findMinMultiThread() {
        int min = Integer.MAX_VALUE;
        int numThreads = Runtime.getRuntime().availableProcessors();
        try (ExecutorService executorService = Executors.newFixedThreadPool(numThreads)) {
            List<Callable<Integer>> tasks = new ArrayList<>();
            int batchSize = array.size() / numThreads;
            for (int i = 0; i < numThreads; i++) {
                final int startIndex = i * batchSize;
                final int endIndex = (i == numThreads - 1)
                        ? (array.size() - 1)
                        : (i + 1) * batchSize;
                tasks.add(() -> findMinSync(array.subList(startIndex, endIndex)));
            }

            List<Future<Integer>> futures = executorService.invokeAll(tasks);
            for (Future<Integer> future : futures) {
                int partialMin = future.get();
                if (partialMin < min) {
                    min = partialMin;
                }
                Thread.sleep(1);
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted: " + e.getMessage());
        } catch (ExecutionException e) {
            System.out.println("Future's execution exception: " + e.getMessage());
        }
        return min;
    }

    private int findMinSync(List<Integer> sublist) {
        int min = Integer.MAX_VALUE;
        try {
            for (Integer i : sublist) {
                if (i < min) {
                    min = i;
                }
                Thread.sleep(1);
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted: " + e.getMessage());
        }
        return min;
    }

    public int findMinFork() {
        try (var forkJoin = new ForkJoinPool()) {
            var task = new MinFinderTask(array, 0, array.size());
            return forkJoin.invoke(task);
        }
    }

    private class MinFinderTask extends RecursiveTask<Integer> {
        private final List<Integer> sublist;
        private final int start;
        private final int end;

        MinFinderTask(List<Integer> sublist, int start, int end) {
            this.sublist = sublist;
            this.start = start;
            this.end = end;
        }

        protected Integer compute() {
            if (end - start <= 1000) {
                return findMinSync(sublist.subList(start, end));
            }

            int mid = start + (end - start) / 2;
            MinFinderTask left = new MinFinderTask(sublist, start, mid);
            MinFinderTask right = new MinFinderTask(sublist, mid + 1, end);

            left.fork();

            int rightResult = right.compute();
            int leftResult = left.join();

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
            }

            return Math.min(leftResult, rightResult);
        }
    }
}
