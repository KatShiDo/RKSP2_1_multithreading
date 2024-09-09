package org.example;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class FileProcessor implements Runnable {

    private BlockingQueue<File> queue;
    private String allowedFileType;

    public FileProcessor(BlockingQueue<File> queue, String allowedFileType) {
        this.queue = queue;
        this.allowedFileType = allowedFileType;
    }

    public void run() {
        while (true) {
            try {
                File file = queue.take();
                if (file.getFileType().equals(allowedFileType)) {
                    long processingTime = file.getFileSize() * 7;
                    Thread.sleep(processingTime);
                    System.out.println("File processed with type: " + file.getFileType()
                                        + " and size: " + file.getFileSize()
                                        + "\nProcessing time: " + processingTime);
                }
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
