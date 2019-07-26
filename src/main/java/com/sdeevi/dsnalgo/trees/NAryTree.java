package com.sdeevi.dsnalgo.trees;

import java.util.ArrayList;

public class NAryTree {

    public Node root;

    public NAryTree(int data) {
        root = new Node(data);
    }

    public boolean isSelfMirror() {
        return isMirror(root, root);
    }

    public boolean isMirror(NAryTree tree) {
        return isMirror(root, tree.root);
    }

    private boolean isMirror(Node n1, Node n2) {
        if (!isEqualNode(n1, n2)) return false;
        if (n1.children == null && n2.children == null) return true;
        if (n1.children == null || n2.children == null) return false;
        if (n1.children.size() != n2.children.size()) return false;

        int start = 0;
        int end = n1.children.size() - 1;

        while (start < n1.children.size()) {
            if (!isMirror(n1.children.get(start), n2.children.get(end))) return false;
            start++;
            end--;
        }
        return true;
    }

    private boolean isEqualNode(Node n1, Node n2) {
        if (n1 == null && n2 == null) return true;
        if (n1 == null || n2 == null) return false;
        return n1.data == n2.data;
    }

    public int getDepth() {
        return getDepth(root);
    }

    private int getDepth(Node n) {
        if (n == null) return 0;

        int maxDepth = 0;
        for (int i = 0; i < n.children.size(); i++) {
            maxDepth = Math.max(maxDepth, getDepth(n.children.get(i)));
        }
        return maxDepth + 1;
    }

    public static class Node {

        public ArrayList<Node> children;
        public int data;

        public Node(int data) {
            this.data = data;
            children = new ArrayList<>();
        }
    }
}