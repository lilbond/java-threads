package com.musingscafe.algos;

/**
 * Created by ayadav on 1/9/17.
 */
public class PrintOneAndTwoUsingTwoThreadsNTimes {


    final Integer times;
    public PrintOneAndTwoUsingTwoThreadsNTimes(Integer times) {
        this.times = times;
    }

    public static void main(String[] args) {
        PrintOneAndTwoUsingTwoThreadsNTimes p = new PrintOneAndTwoUsingTwoThreadsNTimes(10);
        p.print();
    }

    public void print() {
        Thread runner = new Thread(new Runnable() {
            final Object lock = new Object();
            Thread oneThread = new Thread(new Printer(lock, 1, times));
            Thread twoThread = new Thread(new Printer(lock, 2, times));

            @Override
            public void run() {
                oneThread.start();
                twoThread.start();
            }
        });
        runner.start();
        try {
            runner.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

