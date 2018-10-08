package com.sdeevi.dsnalgo.trees;

public class BinaryTree<T> {
    private TreeNode<T> root;

    public BinaryTree(T root) {
        this.root = new TreeNode<>(root);
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(TreeNode<T> node) {
        if (node == null) return 0;
        int leftHeight = getHeight(node.getLeft());
        int rightHeight = getHeight(node.getRight());

        return leftHeight > rightHeight ? leftHeight + 1 : rightHeight + 1;
    }

    public void printGivenLevel(int level) {

    }

    private void printGivenLevel(TreeNode<T> node, int level) {
        if (node == null) return;
        if (level == 1) System.out.println(node.getValue());
    }

    public static void main(String[] args) {
        BinaryTree<Integer> tree = new BinaryTree<>(1);
        TreeNode<Integer> node1 = new TreeNode<>(2);
        TreeNode<Integer> node2 = new TreeNode<>(3);
        TreeNode<Integer> node3 = new TreeNode<>(4);
        TreeNode<Integer> node4 = new TreeNode<>(5);
        TreeNode<Integer> node5 = new TreeNode<>(6);
        TreeNode<Integer> node6 = new TreeNode<>(7);
        TreeNode<Integer> node7 = new TreeNode<>(8);
        tree.getRoot().setLeft(node1);
        tree.getRoot().setRight(node2);
        node1.setLeft(node3);
        node1.setRight(node4);
        node2.setLeft(node5);
        node2.setRight(node6);
        node1.setLeft(node7);

        System.out.println(tree.getHeight());
    }
}