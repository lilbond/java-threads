package com.musingscafe.algos;

import com.musingscafe.Utils;
import java.util.Random;

/*
Basic blocking queue.
 */
public class BoundedBlockingQueueDriver {
    public static void main(String[] args) {
        final BoundedBlockingQueue<Integer> boundedBlockingQueue = new BoundedBlockingQueue<>(5);

        Thread writer = new Thread(() -> {
            while (true) {
                Utils.sleep(1000);
                Random random = new Random();
                int next = random.nextInt(100);
                System.out.println("Adding : " + next);
                boundedBlockingQueue.enqueue(next);
                System.out.println("Added : " + next);
            }
        });

        Thread reader = new Thread(() -> {
            while (true) {
                Utils.sleep(300);
                System.out.println("Reading top");
                int top = boundedBlockingQueue.dequeue();
                System.out.println("Read : " + top);
            }
        });


        writer.start();
        reader.start();

        Utils.join(writer);
        Utils.join(reader);
    }
}
