package com.sdeevi.design.cache;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class LRUCache implements Cache {

    private final Deque<Node> keyList;
    private final Map<Object, Node> cache;
    private final int capacity;
    private final ReentrantLock reentrantLock = new ReentrantLock();

    public LRUCache(int capacity) {
        this.keyList = new ArrayDeque<>();
        this.cache = new HashMap<>();
        this.capacity = capacity;
    }

    @Override
    public void add(Object key, Object value, long expirationTimeInMillis) {
        Node node;
        reentrantLock.lock();
        try {
            if (cache.containsKey(key)) {
                node = cache.get(key);
                keyList.remove(node);
                node.setValue(value);
                keyList.addFirst(node);
            } else {
                node = new Node(key, value);
                if (keyList.size() == capacity) {
                    Node removeNode = keyList.removeLast();
                    cache.remove(removeNode.key);
                }
                keyList.addFirst(node);
            }
            cache.put(key, node);
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public void remove(Object key) {
        reentrantLock.lock();
        try {
            if (cache.containsKey(key)) {
                Node node = cache.get(key);
                keyList.remove(node);
                cache.remove(key);
            }
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public Object get(Object key) {
        return cache.containsKey(key) ? cache.get(key).value : null;
    }

    @Override
    public void clear() {
        cache.clear();
        keyList.clear();
    }

    private class Node {
        private Object key;
        private Object value;

        Node(Object key, Object value) {
            this.key = key;
            this.value = value;
        }

        void setValue(Object value) {
            this.value = value;
        }
    }
}
