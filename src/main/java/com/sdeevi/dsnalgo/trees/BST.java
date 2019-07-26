package com.sdeevi.dsnalgo.trees;

import java.util.ArrayList;
import java.util.List;

public class BST {

    private Node root;

    public void add(int ele) {
        if (root == null) {
            root = new Node(ele);
        } else {
            insert(root, ele);
        }
    }

    public void addIterative(int ele) {
        Node eleNode = new Node(ele);
        if (root == null) {
            root = eleNode;
            return;
        }
        Node curr = root;
        Node temp = null;

        while (curr != null) {
            temp = curr;
            if (ele < curr.data) curr = curr.left;
            else curr = curr.right;
        }

        if (ele < temp.data) temp.left = eleNode;
        else if (ele >= temp.data) temp.right = eleNode;
    }

    private Node insert(Node n, int ele) {
        if (n == null) return new Node(ele);

        if (ele < n.data)
            n.left = insert(n.left, ele);
        else
            n.right = insert(n.right, ele);
        return n;
    }

    public List<Integer> getInOrder() {
        List<Integer> elements = new ArrayList<>();
        getInOrder(root, elements);
        return elements;
    }

    private void getInOrder(Node n, List<Integer> elements) {
        if (n == null) return;
        getInOrder(n.left, elements);
        elements.add(n.data);
        getInOrder(n.right, elements);
    }

    public List<Integer> getPreOrder() {
        List<Integer> elements = new ArrayList<>();
        getPreOrder(root, elements);
        return elements;
    }

    private void getPreOrder(Node n, List<Integer> elements) {
        if (n == null) return;
        elements.add(n.data);
        getPreOrder(n.left, elements);
        getPreOrder(n.right, elements);
    }

    public static class Node {
        public int data;
        public Node left;
        public Node right;

        public Node(int data) {
            this.data = data;
        }
    }
}
