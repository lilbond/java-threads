package com.musingscafe.create;

import com.musingscafe.Utils;

import java.util.stream.IntStream;

public class App {
    public static void main(String[] args) {

        SimpleThread simpleThread = new SimpleThread();

        SimpleRunnable simpleRunnable = new SimpleRunnable();
        Thread runnableThread = new Thread(simpleRunnable);

        Thread anonymousThread = new Thread(new Runnable() {
            @Override
            public void run() {
                IntStream.range(0, 10)
                        .forEach(i -> System.out.println("AnonymousThread says :- " + i));
            }
        });

        Thread lambdaThread = new Thread(() -> IntStream.range(0, 10)
                .forEach(i -> System.out.println("LambdaThread says :- " + i)));

        simpleThread.start();
        runnableThread.start();
        anonymousThread.start();
        lambdaThread.start();

        Utils.sleep(100);
        System.out.println("main ends");
    }
}

class SimpleThread extends Thread {

    @Override
    public void run() {
        IntStream.range(0, 10)
                .forEach(i -> System.out.println("Hello from SimpleThread :- " + i));
    }
}

class SimpleRunnable implements Runnable {

    @Override
    public void run() {
        IntStream.range(0, 10)
                .forEach(i -> System.out.println("Hello from SimpleRunnable :- " + i));
    }
}
