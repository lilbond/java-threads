package com.musingscafe.algos;

import java.util.ArrayList;
import java.util.List;

public class BoundedBlockingQueue <T extends Comparable> {
    private final List<T> queue;
    private final int bound;

    public BoundedBlockingQueue(int bound) {
        this.queue = new ArrayList<T>();
        this.bound = bound;

        if (this.bound <= 0) throw new IllegalArgumentException("Positive integer required");
    }

    public synchronized void enqueue(T value) {
        if (queue.size() == bound) {
            waitOnOperation();
        }

        queue.add(value);
        notify();
    }

    public synchronized T dequeue() {
        if (queue.isEmpty()) {
            waitOnOperation();
        }

        T value = queue.remove(0);
        notify();
        return value;
    }

    private void waitOnOperation() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
