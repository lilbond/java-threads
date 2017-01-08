package com.musingscafe.sync;

import com.musingscafe.Utils;

import java.util.Scanner;

public class App {

    public static void main(String[] args) throws Exception {
        Processor processor = new Processor();
        Thread firstThread = new Thread(() -> processor.produce());
        Thread secondThread = new Thread(() -> processor.consume());

        firstThread.start();
        secondThread.start();

        firstThread.join();
        secondThread.join();
    }
}

class Processor {

    public void produce() {
        System.out.println("In produce....");
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("finishing produce...");
    }

    public void consume() {
        Utils.sleep(100);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter to resume...");
        scanner.nextLine();

        synchronized (this) {
            notify();
        }
    }
}
