package com.musingscafe.algos;

import java.util.ArrayList;
import java.util.List;

public class PrintOneToKUsingKThreadsNTimes {
    public static void main(String[] args) {
        PrintOneToKUsingKThreadsNTimes p = new PrintOneToKUsingKThreadsNTimes();
        p.print();
    }

    public void print() {
        //create k-1 locks
        //create k printer object
        //set locks
        //add calls one after another
        int k = 4;
        Object lock = new Object();

        List<ChainedPrinter> chainedPrinters = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        for (int i = 1; i <= k; i++) {
            chainedPrinters.add(new ChainedPrinter(lock, i, 10));
        }

        for (int i = 0; i < k; i++) {
            ChainedPrinter next = null;
            next = (i < k - 1) ? chainedPrinters.get(i + 1) : chainedPrinters.get(0);
            chainedPrinters.get(i).setNext(next);
            threads.add(new Thread(chainedPrinters.get(i)));
        }

        for (int i = 0; i < k; i++) {
            try {
                threads.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < k; i++) {
            threads.get(i).start();
        }

        chainedPrinters.get(0).setCanExecute(true);
        synchronized (lock) {
            lock.notifyAll();
        }


    }

}
