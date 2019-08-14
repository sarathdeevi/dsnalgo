package com.sdeevi.dsnalgo.trees;

import java.util.ArrayList;

public class MaxHeap<V extends Comparable> {

    private ArrayList<V> heap;
    private int maxSize;

    public MaxHeap(int maxSize) {
        this.heap = new ArrayList<>(maxSize);
        this.maxSize = maxSize;
    }

    private int parent(int pos) {
        return pos == 0 ? 0 : (pos - 1) / 2;
    }

    private int leftChild(int pos) {
        return 2 * pos + 1;
    }

    private int rightChild(int pos) {
        return 2 * pos + 2;
    }

    private boolean isLeaf(int pos) {
        return pos >= size() / 2 && pos < size();
    }

    private void swap(int i, int j) {
        V tmp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, tmp);
    }

    private void heapifyDown(int pos) {
        if (!isLeaf(pos)) {
            int left = leftChild(pos);
            int right = rightChild(pos);

            int largest = pos;

            if (left < heap.size() && heap.get(left).compareTo(heap.get(pos)) > 0) {
                largest = left;
            }

            if (right < heap.size() && heap.get(right).compareTo(heap.get(largest)) > 0) {
                largest = right;
            }

            if (largest != pos) {
                swap(pos, largest);
                heapifyDown(largest);
            }
        }
    }

    private void heapifyUp(int pos) {
        if (pos > 0 && heap.get(parent(pos)).compareTo(heap.get(pos)) < 0) {
            swap(pos, parent(pos));
            heapifyUp(parent(pos));
        }
    }

    public int size() {
        return heap.size();
    }

    public void add(V element) {
        if (size() >= maxSize) return;

        heap.add(element);
        int index = size() - 1;

        heapifyUp(index);
    }

    public V remove() {
        V popped = heap.get(0);
        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(size() - 1);
        heapifyDown(0);

        return popped;
    }
}
