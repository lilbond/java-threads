package com.musingscafe.algos;

public class Printer implements Runnable {
    final Object lock;
    final Integer number;
    final Integer times;
    Integer count;

    Printer(Object lock, Integer number, Integer times) {
        this.lock = lock;
        this.number = number;
        this.times = times;
        count = 0;
    }

    @Override
    public void run() {
        synchronized (lock) {
            try {
                print();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void print() throws InterruptedException {
        if (count >= times) return;

        System.out.print(" " + this.number);
        count++;
        lock.notify();
        lock.wait();
        print();
    }
}
