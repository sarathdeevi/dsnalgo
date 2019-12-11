package com.sdeevi.design.threads;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.Collectors;

public class ProducerConsumerPipeline<T> {

    private int bufferSize;
    private Queue<T> buffer;

    public ProducerConsumerPipeline(int bufferSize) {
        this.bufferSize = bufferSize;
        this.buffer = new LinkedList<>();
    }

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(new CyclicBarrierProcessor(cyclicBarrier));
            thread.start();
        }

        try {

            for(int i=0; i<Integer.MAX_VALUE; i++) {}
            cyclicBarrier.await();


            cyclicBarrier.reset();
            System.out.println("Comes here");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(new CyclicBarrierProcessor(cyclicBarrier));
            thread.start();
        }
//        cyclicBarrier.reset();
//        cyclicBarrier.await();
    }

    public void send(T message) {
        synchronized (this) {
            while (buffer.size() == bufferSize) {
                try {
                    System.out.println("Waiting to send messages");
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            buffer.add(message);
            this.notifyAll();
        }
    }

    public T receive() {
        synchronized (this) {
            try {
                while (buffer.isEmpty()) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                return buffer.remove();
            } finally {
                this.notifyAll();
            }
        }
    }

    public static final class Producer implements Runnable {

        private ProducerConsumerPipeline<String> pipeline;
        private String name;

        public Producer(String name, ProducerConsumerPipeline<String> pipeline) {
            this.pipeline = pipeline;
            this.name = name;
        }

        @Override
        public void run() {
            int i = 0;
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String val = name + "-" + i++;
                System.out.println(name + ", value = " + val);
                pipeline.send(val);
            }
        }
    }

//    public static void main(String[] args) throws InterruptedException {
//        ProducerConsumerPipeline<String> pipeline = new ProducerConsumerPipeline<>(10);
//
//        for (int i = 0; i < 3; i++) {
//            Thread thread = new Thread(new Consumer<>("c-" + (i + 1), pipeline));
//            thread.start();
//        }
//        for (int i = 0; i < 10; i++) {
//            Thread thread = new Thread(new Producer("p-" + (i + 1), pipeline));
//            thread.start();
//        }
//
//        Thread.sleep(100000);
//    }


//    public static void main(String[] args) {
//        CountDownLatch countDownLatch = new CountDownLatch(5);
//
//        for (int i = 0; i < 10; i++) {
//            Thread thread = new Thread(new CountDownLatchProcessor(countDownLatch, i));
//            thread.start();
//        }
//        try {
//            countDownLatch.await();
//            System.out.println("abc");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    public static final class Consumer<T> implements Runnable {

        private ProducerConsumerPipeline<T> pipeline;
        private String name;

        public Consumer(String name, ProducerConsumerPipeline<T> pipeline) {
            this.pipeline = pipeline;
            this.name = name;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // do nothing.
                }
                System.out.println(name + ", received value = " + pipeline.receive());
            }
        }
    }

    private static final class CountDownLatchProcessor implements Runnable {

        private CountDownLatch countDownLatch;
        private int n;

        public CountDownLatchProcessor(CountDownLatch countDownLatch, int n) {
            this.countDownLatch = countDownLatch;
            this.n = n;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(n * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(n);
            countDownLatch.countDown();
        }
    }

    private static class CyclicBarrierProcessor implements Runnable {
        static int count = 0;
        CyclicBarrier cyclicBarrier;

        public CyclicBarrierProcessor(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
//                Thread.sleep(1000);
                System.out.println(count++);
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
