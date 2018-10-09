package com.sdeevi.dsnalgo.trees;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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
        for (int i = 0; i <= getHeight(); i++) {
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

    public List<T> getLevelOrderTraversalUsingQueue() {
        if (root == null) {
            return new ArrayList<>();
        }
        List<T> elements = new ArrayList<>();
        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode<T> temp = queue.poll();
            elements.add(temp.getValue());

            if (Objects.nonNull(temp.getLeft())) {
                queue.add(temp.getLeft());
            }
            if (Objects.nonNull(temp.getRight())) {
                queue.add(temp.getRight());
            }
        }
        return elements;
    }

    public List<T> getVerticalLine(int line_num) {
        List<T> verticalLine = new ArrayList<>();
        return getVerticalLine(root, verticalLine, line_num, 0);
    }

    private List<T> getVerticalLine(TreeNode<T> node, List<T> values, int line_num, int curr) {
        if (Objects.nonNull(node)) {
            if (curr == line_num) {
                values.add(node.getValue());
            }

            getVerticalLine(node.getLeft(), values, line_num, curr - 1);
            getVerticalLine(node.getRight(), values, line_num, curr + 1);
        }
        return values;
    }

    public List<T> getVerticalOrderTraversal() {
        List<T> verticalOrderElements = new ArrayList<>();
        AtomicInteger min = new AtomicInteger(), max = new AtomicInteger();
        findMinMax(root, min, max, 0);

        for (int i = min.get(); i <= max.get(); i++) {
            verticalOrderElements.addAll(getVerticalLine(i));
        }
        return verticalOrderElements;
    }

    private void findMinMax(TreeNode<T> node, AtomicInteger min, AtomicInteger max, int curr) {
        if (node == null) return;
        if (curr < min.get()) {
            min.set(curr);
        } else if (curr > max.get()) {
            max.set(curr);
        }

        findMinMax(node.getLeft(), min, max, curr - 1);
        findMinMax(node.getRight(), min, max, curr + 1);
    }

    public List<T> getLeftView() {
        List<T> leftViewElements = new ArrayList<>();
        AtomicInteger maxLevel = new AtomicInteger();
        getLeftView(root, leftViewElements, 1, maxLevel);
        return leftViewElements;
    }

    private void getLeftView(TreeNode<T> node, List<T> leftViewElements, int currLevel, AtomicInteger maxLevel) {
        if (node == null) return;

        if (maxLevel.get() < currLevel) {
            leftViewElements.add(node.getValue());
            maxLevel.set(currLevel);
        }

        getLeftView(node.getLeft(), leftViewElements, currLevel + 1, maxLevel);
        getLeftView(node.getRight(), leftViewElements, currLevel + 1, maxLevel);
    }

    public List<T> getRightView() {
        List<T> rightViewElements = new ArrayList<>();
        AtomicInteger maxLevel = new AtomicInteger();
        getRightView(root, rightViewElements, 1, maxLevel);
        return rightViewElements;
    }

    private void getRightView(TreeNode<T> node, List<T> rightViewElements, int currLevel, AtomicInteger maxLevel) {
        if (node == null) return;

        if (maxLevel.get() < currLevel) {
            rightViewElements.add(node.getValue());
            maxLevel.set(currLevel);
        }

        getRightView(node.getRight(), rightViewElements, currLevel + 1, maxLevel);
        getRightView(node.getLeft(), rightViewElements, currLevel + 1, maxLevel);
    }

    public List<T> getBoundaryTraversal() {
        List<T> boundaryElements = new ArrayList<>();

        if (Objects.nonNull(root)) {
            getLeftBoundary(root, boundaryElements, false);
        }

        if (Objects.nonNull(root) && Objects.nonNull(root.getRight())) {
            getRightBoundary(root.getRight(), boundaryElements, false);
        }
        return boundaryElements;
    }

    public List<T> getTopView() {
        List<T> boundaryElements = new ArrayList<>();

        getLeftBoundary(root, boundaryElements, true);
        getLeaves(root, boundaryElements);
        if (Objects.nonNull(root) && Objects.nonNull(root.getRight())) {
            getRightBoundary(root.getRight(), boundaryElements, true);
        }
        return boundaryElements;
    }

    private void getLeftBoundary(TreeNode<T> node, List<T> boundaryElements, boolean includeLeaf) {
        if (node == null) return;
        if (includeLeaf || Objects.isNull(node.getLeft())) {
            boundaryElements.add(node.getValue());
        }
        if (Objects.nonNull(node.getLeft())) {
            getLeftBoundary(node.getLeft(), boundaryElements, includeLeaf);
        }
    }

    private void getRightBoundary(TreeNode<T> node, List<T> boundaryElements, boolean includeLeaf) {
        if (node == null) return;
        if (includeLeaf || Objects.isNull(node.getRight())) {
            boundaryElements.add(node.getValue());
        }
        if (Objects.nonNull(node.getRight())) {
            getRightBoundary(node.getRight(), boundaryElements, includeLeaf);
        }
    }

    private void getLeaves(TreeNode<T> node, List<T> elements) {
        if (node == null) return;
        if (node.getLeft() == null && node.getRight() == null) {
            elements.add(node.getValue());
            getLeaves(node.getLeft(), elements);
            getLeaves(node.getRight(), elements);
        }
    }
}