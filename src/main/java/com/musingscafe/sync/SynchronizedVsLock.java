package com.musingscafe.sync;

import com.musingscafe.Utils;

/*
If used only one object and one lock, behaviour is same as expected. Calls are synchronous.
The problem will arise if used different objects or different locks in same object.
 */
public class SynchronizedVsLock {
    public static void main(String[] args) {
        //Both works exactly the same
        callSynchronizedSample();
        callLockedSample();
    }

    private static void callLockedSample() {
        final LockedSample lockedSample = new LockedSample();
        Thread callLockAndSleep = new Thread(new Runnable() {
            @Override
            public void run() {
                lockedSample.lockAndSleep();
            }
        });

        Thread callLockAndDoWork = new Thread(new Runnable() {
            @Override
            public void run() {
                //ensure that it access after the other thread has already accessed
                Utils.sleep(10);
                long tryingToGetHandleSince = System.nanoTime();
                System.out.println("Trying to execute synchronizeAndDoWork since: " + tryingToGetHandleSince);
                lockedSample.lockAndWork(tryingToGetHandleSince);
            }
        });

        callLockAndSleep.start();
        callLockAndDoWork.start();

        Utils.join(callLockAndSleep);
        Utils.join(callLockAndDoWork);
    }

    private static void callSynchronizedSample() {
        final SynchronizedSample synchronizedSample = new SynchronizedSample();
        Thread callSynchronizeAndSleep = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizedSample.synchronizeAndSleep();
            }
        });

        Thread callSynchronizeAndDoWork = new Thread(new Runnable() {
            @Override
            public void run() {
                //ensure that it access after the other thread has already accessed
                Utils.sleep(10);
                long tryingToGetHandleSince = System.nanoTime();
                System.out.println("Trying to execute synchronizeAndDoWork since: " + tryingToGetHandleSince);
                synchronizedSample.synchronizeAndDoWork(tryingToGetHandleSince);
            }
        });

        callSynchronizeAndSleep.start();
        callSynchronizeAndDoWork.start();

        Utils.join(callSynchronizeAndSleep);
        Utils.join(callSynchronizeAndDoWork);
    }
}

class SynchronizedSample {
    public synchronized void synchronizeAndSleep() {
        System.out.println("synchronizeAndSleep: I have synchronized on 'this', now I will sleep and and my sibling methods won't be able to run before I finish");
        Utils.sleep(5000);
        System.out.println("synchronizeAndSleep: I am done sleeping, now only can my siblings work.");
    }

    public synchronized void synchronizeAndDoWork(long startTime) {
        long gotHandleAt = System.nanoTime();
        System.out.println("SynchronizeAndDoWork: doing my job at : " + gotHandleAt);
        System.out.println("SynchronizeAndDoWork: I had to wait : " + (gotHandleAt - startTime) / (1000 * 1000) + " millis to execute");
    }
}

class LockedSample {
    Object lock = new Object();

    public void lockAndSleep() {
        synchronized (lock) {
            System.out.println("synchronizeAndSleep: I have synchronized on 'this', now I will sleep and and my sibling methods won't be able to run before I finish");
            Utils.sleep(5000);
            System.out.println("synchronizeAndSleep: I am done sleeping, now only can my siblings work.");
        }
    }

    public void lockAndWork(long startTime) {
        synchronized (lock) {
            long gotHandleAt = System.nanoTime();
            System.out.println("SynchronizeAndDoWork: doing my job at : " + gotHandleAt);
            System.out.println("SynchronizeAndDoWork: I had to wait : " + (gotHandleAt - startTime) / (1000 * 1000) + " millis to execute");
        }
    }
}

