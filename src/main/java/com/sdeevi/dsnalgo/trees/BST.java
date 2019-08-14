package com.sdeevi.dsnalgo.trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BST {

    Node root;

    /*
    Auxiliary variables for some examples. Not safe for multi-threaded scenarios
     */
    private int kthSmallestElementCount;
    private Node kthSmallestNode;
    private int kthLargestElementCount;
    private Node kthLargestNode;
    private int sumOfKSmallestElements;

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
        kthSmallestNode = null;
        kthSmallestElementCount = k;
        kthSmallestElement(root);
        return kthSmallestNode != null ? kthSmallestNode.data : Integer.MAX_VALUE;
    }

    private void kthSmallestElement(Node n) {
        if (n == null) return;

        kthSmallestElement(n.left);

        kthSmallestElementCount--;
        if (kthSmallestElementCount == 0) {
            kthSmallestNode = n;
        }

        kthSmallestElement(n.right);
    }

    public int sumOfKSmallestElements(int k) {
        sumOfKSmallestElements = 0;
        kthSmallestElementCount = k;

        sumOfKSmallestElements(root, k);
        return kthSmallestElementCount != 0 ? Integer.MAX_VALUE : sumOfKSmallestElements;
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

    public int kthLargestElement(int k) {
        kthLargestNode = null;
        kthLargestElementCount = k;
        kthLargestElement(root);
        return kthLargestNode != null ? kthLargestNode.data : Integer.MAX_VALUE;
    }

    private void kthLargestElement(Node n) {
        if (n == null) return;

        kthLargestElement(n.right);

        kthLargestElementCount--;
        if (kthLargestElementCount == 0) {
            kthLargestNode = n;
        }

        kthLargestElement(n.left);
    }


//    public BST merge(BST tree2) {
//
//    }
//
//    private Node merge(Node n1, Node n2) {
//        Node n;
//        Node curr1 = n1, curr2 = n2;
//        if (curr1.data < curr2.data) {
//            curr1 = curr1.left;
//        } else {
//            curr2 = curr2.left;
//        }
//    }

    public int lowestCommonAncestor(int k1, int k2) {
        if (root == null) return Integer.MAX_VALUE;

        Node curr = root;
        while (curr != null) {
            if (k1 < curr.data && k2 < curr.data) {
                curr = curr.left;
            } else if (k1 > curr.data && k2 > curr.data) {
                curr = curr.right;
            } else {
                return search(curr, k1) != null && search(curr, k2) != null ? curr.data : Integer.MAX_VALUE;
            }
        }
        return Integer.MAX_VALUE;
    }

    private void sumOfKSmallestElements(Node n, int k) {
        if (n == null) return;
        sumOfKSmallestElements(n.left, k);
        if (kthSmallestElementCount != 0) {
            sumOfKSmallestElements += n.data;
            kthSmallestElementCount--;
        }
        sumOfKSmallestElements(n.right, k);
    }

    public int lowestCommonAncestorRecursive(int k1, int k2) {
        return lowestCommonAncestorRecursive(root, k1, k2);
    }

    private Node search(Node n, int val) {
        Node curr = n;
        while (curr != null) {
            if (val < curr.data) {
                curr = curr.left;
            } else if (val > curr.data) {
                curr = curr.right;
            } else {
                return curr;
            }
        }
        return null;
    }

    private int lowestCommonAncestorRecursive(Node n, int k1, int k2) {
        if (n == null) return Integer.MAX_VALUE;
        if (k1 < n.data && k2 < n.data) {
            return lowestCommonAncestorRecursive(n.left, k1, k2);
        } else if (k1 > n.data && k2 > n.data) {
            return lowestCommonAncestorRecursive(n.right, k1, k2);
        } else {
            Node k1Node = searchRecursive(n, k1);
            Node k2Node = searchRecursive(n, k2);
            return k1Node != null && k2Node != null ? n.data : Integer.MAX_VALUE;
        }
    }

    public Node searchRecursive(int val) {
        return searchRecursive(root, val);
    }

    private Node searchRecursive(Node n, int val) {
        if (n == null) return null;
        if (n.data == val) return n;

        if (val < n.data) return searchRecursive(n.left, val);
        if (val > n.data) return searchRecursive(n.right, val);
        return null;
    }

//    public void correctBSTWithTwoSwappedNodes() {
//        Node wrongNodeInLeftSubTree =
//    }
//
//    private Node getNodeGreaterThanInLeftSubTree(Node n) {
//        if (n == null) return null;
//        if (n.left.data > n.data) return n;
//
//        getNodeGreaterThanInLeftSubTree(n.left);
//        getNodeGreaterThanInLeftSubTree(n.right);
//    }

    private void swap(Node n1, Node n2) {
        int temp = n1.data;
        n1.data = n2.data;
        n2.data = temp;
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
