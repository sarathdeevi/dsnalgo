package com.sdeevi.dsnalgo.trees;

import java.util.ArrayList;
import java.util.List;

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

    public List<T> getLevelOrderTraversal() {
        List<T> levelOrderTraversalList = new ArrayList<>();
        for (int i=0; i<=getHeight(); i++) {
            levelOrderTraversalList.addAll(getLevelOrderTraversal(i));
        }
        return levelOrderTraversalList;
    }

    public List<T> getLevelOrderTraversal(int level) {
        List<T> levelOrderTraversalList = new ArrayList<>();
        getLevelOrderTraversal(root, level, levelOrderTraversalList);
        return levelOrderTraversalList;
    }

    private void getLevelOrderTraversal(TreeNode<T> node, int level, List<T> levelOrderTraversalList) {
        if (node == null) return;
        if (level == 1) {
            levelOrderTraversalList.add(node.getValue());
        } else {
            getLevelOrderTraversal(node.getLeft(), level - 1, levelOrderTraversalList);
            getLevelOrderTraversal(node.getRight(), level - 1, levelOrderTraversalList);
        }
    }
}