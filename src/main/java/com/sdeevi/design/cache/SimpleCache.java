package com.sdeevi.design.cache;

import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class SimpleCache implements Cache {

    private final ConcurrentHashMap<Object, SoftReference<Object>> concurrentHashMap = new ConcurrentHashMap<>();
    private final DelayQueue<DelayCacheObject> queue = new DelayQueue<>();

    public SimpleCache() {
        Thread cleanerThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    DelayCacheObject delayCacheObject = queue.take();
                    concurrentHashMap.remove(delayCacheObject.getKey());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        cleanerThread.setDaemon(true);
        cleanerThread.start();
    }

    @Override
    public void add(Object key, Object value, long expirationTimeInMillis) {
        if (key == null) {
            return;
        }
        concurrentHashMap.put(key, new SoftReference<>(value));
        queue.put(new DelayCacheObject(System.currentTimeMillis() + expirationTimeInMillis, key));
    }

    @Override
    public void remove(Object key) {
        concurrentHashMap.remove(key);
    }

    @Override
    public Object get(Object key) {
        if (key == null || !concurrentHashMap.containsKey(key)) {
            return null;
        }
        return concurrentHashMap.get(key).get();
    }

    @Override
    public void clear() {
        concurrentHashMap.clear();
    }

    static class DelayCacheObject implements Delayed {

        private final long expirationTime;
        private final Object key;

        DelayCacheObject(long expirationTime, Object key) {
            this.expirationTime = expirationTime;
            this.key = key;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(expirationTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return (int) (expirationTime - ((DelayCacheObject) o).expirationTime);
        }

        Object getKey() {
            return key;
        }
    }
}
