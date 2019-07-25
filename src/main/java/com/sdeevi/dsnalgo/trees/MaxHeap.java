package com.sdeevi.dsnalgo.trees;

import java.util.Vector;

public class MaxHeap {

    private Vector<Integer> heap;
    private int maxSize;

    public MaxHeap(int maxSize) {
        this.heap = new Vector<>(maxSize);
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
        int tmp = heap.get(i);
        heap.setElementAt(heap.get(j), i);
        heap.setElementAt(tmp, j);
    }

    private void heapifyDown(int pos) {
        if (!isLeaf(pos)) {
            int left = leftChild(pos);
            int right = rightChild(pos);

            int largest = pos;

            if (left < heap.size() && heap.get(left) > heap.get(pos)) {
                largest = left;
            }

            if (right < heap.size() && heap.get(right) > heap.get(largest)) {
                largest = right;
            }

            if (largest != pos) {
                swap(pos, largest);
                heapifyDown(largest);
            }
        }
    }

    private void heapifyUp(int pos) {
        if (pos > 0 && heap.get(parent(pos)) < heap.get(pos)) {
            swap(pos, parent(pos));
            heapifyUp(parent(pos));
        }
    }

    public int size() {
        return heap.size();
    }

    public void add(int element) {
        if (size() >= maxSize) return;

        heap.addElement(element);
        int index = size() - 1;

        heapifyUp(index);
    }

    public int remove() {
        int popped = heap.firstElement();
        heap.setElementAt(heap.lastElement(), 0);
        heap.remove(size() - 1);
        heapifyDown(0);

        return popped;
    }
}
