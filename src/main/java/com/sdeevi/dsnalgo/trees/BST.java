package com.sdeevi.dsnalgo.trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

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

    public int kthSmallestElement(int k) {
        AtomicInteger count = new AtomicInteger();
        return kthSmallestElement(root, k, count);
    }

    private int kthSmallestElement(Node n, int k, AtomicInteger count) {
        if (n == null) return Integer.MAX_VALUE;

        int left = kthSmallestElement(n.left, k, count);
        if (left != Integer.MAX_VALUE) return left;

        if (k == count.incrementAndGet()) return n.data;

        return kthSmallestElement(n.right, k, count);
    }

    public int kthSmallestElementIterative(int k) {
        if (root == null) return Integer.MAX_VALUE;

        Node curr = root;
        Stack<Node> stack = new Stack<>();

        int count = 0;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }

            curr = stack.pop();
            count++;
            if (count == k) {
                return curr.data;
            }
            curr = curr.right;
        }
        return Integer.MAX_VALUE;
    }

//    public BST merge(BST tree2) {
//
//    }

//    private Node merge(Node n1, Node n2) {
//        Node n;
//        Node curr1 = n1, curr2 = n2;
//        if (n1.data < n2.data) {
//            n = n1;
//            curr1 = n1.left;
//        } else {
//            n = n2;
//            curr2 = curr2.left;
//        }
//    }

    public static class Node {
        public int data;
        public Node left;
        public Node right;

        public Node(int data) {
            this.data = data;
        }
    }
}
