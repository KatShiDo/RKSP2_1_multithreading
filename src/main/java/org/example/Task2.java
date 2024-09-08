package org.example;

import java.util.Scanner;
import java.util.concurrent.*;

public class Task2 {
    public static void main(String[] args) {
        try (ExecutorService executorService = Executors.newFixedThreadPool(5)) {
            while (true) {
                System.out.println("Enter the number (or 'exit' to quit): ");
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("exit")) {
                    break;
                }

                int number = Integer.parseInt(input);
                Future<Integer> future = executorService.submit(() -> calculateSquare(number));
                int result = future.get();
                System.out.println("Result: " + result);
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted: " + e.getMessage());
        } catch (ExecutionException e) {
            System.out.println("Execution exception: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Wrong format of the number");
        }
    }

    private static int calculateSquare(int number) {
        int delaySeconds = ThreadLocalRandom.current().nextInt(1, 6);
        try {
            Thread.sleep(delaySeconds * 1000L);
        } catch (InterruptedException e) {
            System.out.println("Interrupted: " + e.getMessage());
        }
        return number * number;
    }
}
