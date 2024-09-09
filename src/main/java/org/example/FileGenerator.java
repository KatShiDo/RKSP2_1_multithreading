package org.example;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class FileGenerator implements Runnable {

    private BlockingQueue<File> queue;

    public FileGenerator(BlockingQueue<File> queue) {
        this.queue = queue;
    }

    public void run() {
        var random = new Random();
        String[] fileTypes = {"XML", "JSON", "XLS"};
        while (true) {
            try {
                Thread.sleep(random.nextInt(901) + 100);
                String randomFileType = fileTypes[random.nextInt(fileTypes.length)];
                int randomFileSize = random.nextInt(91) + 10;
                var file = new File(randomFileType, randomFileSize);
                queue.put(file);
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
