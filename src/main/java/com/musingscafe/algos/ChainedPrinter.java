package com.musingscafe.algos;

public class ChainedPrinter implements Runnable{
    private final Object lock;
    private final Integer number;
    private final Integer times;
    private ChainedPrinter next;
    private Integer count;
    private boolean canExecute;

    public ChainedPrinter(Object lock, Integer number, Integer times) {
        this.lock = lock;
        this.number = number;
        this.times = times;
        count = 0;
    }

    public boolean isCanExecute() {
        return canExecute;
    }

    public void setCanExecute(boolean canExecute) {
        this.canExecute = canExecute;
    }

    public Integer getCount() {
        return count;
    }

    private void setCount(Integer count) {
        this.count = count;
    }

    private void print() throws InterruptedException {
        synchronized (lock) {
            //if (count == 0) Thread.sleep(500);
            if (isCanExecute() && getCount() < times) {
                System.out.print(" " + number);
                setCanExecute(false);
                setCount(getCount() + 1);

                if (next != null) {
                    next.setCanExecute(true);
                }
                lock.notifyAll();
            }

            if (count < times) {
                lock.wait();
                print();
            }
        }
    }

    @Override
    public void run() {
        try {
            print();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setNext(ChainedPrinter next) {
        this.next = next;
    }

    public Integer getNumber() {
        return number;
    }

    public ChainedPrinter getNext() {
        return next;
    }
}
