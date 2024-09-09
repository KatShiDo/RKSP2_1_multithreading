package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Task3 {
    public static void main(String[] args) {
        BlockingQueue<File> queue = new LinkedBlockingQueue<>(5);

        var generatorThread = new Thread(new FileGenerator(queue));
        var jsonProcessorThread = new Thread(new FileProcessor(queue, "JSON"));
        var xmlProcessorThread = new Thread(new FileProcessor(queue, "XML"));
        var xlsProcessorThread = new Thread(new FileProcessor(queue, "XLS"));

        generatorThread.start();
        jsonProcessorThread.start();
        xmlProcessorThread.start();
        xlsProcessorThread.start();
    }
}
