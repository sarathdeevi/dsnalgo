package com.sdeevi.dsnalgo.trees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

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
        if (isNull(node)) return 0;
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
        getLevelOrderTraversal(root, level, levelOrderTraversalList, true);
        return levelOrderTraversalList;
    }

    private List<T> getLevelOrderTraversal(int level, boolean leftToRight) {
        List<T> levelOrderTraversalList = new ArrayList<>();
        getLevelOrderTraversal(root, level, levelOrderTraversalList, leftToRight);
        return levelOrderTraversalList;
    }

    private void getLevelOrderTraversal(TreeNode<T> node, int level, List<T> levelOrderTraversalList, boolean leftToRight) {
        if (isNull(node)) return;
        if (level == 1) {
            levelOrderTraversalList.add(node.getValue());
        } else {
            if (leftToRight) {
                getLevelOrderTraversal(node.getLeft(), level - 1, levelOrderTraversalList, true);
                getLevelOrderTraversal(node.getRight(), level - 1, levelOrderTraversalList, true);
            } else {
                getLevelOrderTraversal(node.getRight(), level - 1, levelOrderTraversalList, false);
                getLevelOrderTraversal(node.getLeft(), level - 1, levelOrderTraversalList, false);
            }
        }
    }

    public List<T> getLevelOrderTraversalUsingQueue() {
        if (isNull(root)) {
            return new ArrayList<>();
        }
        List<T> elements = new ArrayList<>();
        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode<T> temp = queue.poll();
            elements.add(temp.getValue());

            if (nonNull(temp.getLeft())) {
                queue.add(temp.getLeft());
            }
            if (nonNull(temp.getRight())) {
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
        if (nonNull(node)) {
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
        if (isNull(node)) return;
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
        if (isNull(node)) return;

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
        if (isNull(node)) return;

        if (maxLevel.get() < currLevel) {
            rightViewElements.add(node.getValue());
            maxLevel.set(currLevel);
        }

        getRightView(node.getRight(), rightViewElements, currLevel + 1, maxLevel);
        getRightView(node.getLeft(), rightViewElements, currLevel + 1, maxLevel);
    }

    public List<T> getBoundaryTraversal() {
        List<T> boundaryElements = new ArrayList<>();

        if (nonNull(root)) {
            getLeftBoundary(root, boundaryElements, false);
        }
        getLeaves(root, boundaryElements);
        List<T> rightBoundary = new ArrayList<>();
        if (nonNull(root) && nonNull(root.getRight())) {
            getRightBoundary(root.getRight(), rightBoundary, false);
        }
        Collections.reverse(rightBoundary);
        boundaryElements.addAll(rightBoundary);
        return boundaryElements;
    }

    public List<T> getTopView() {
        List<T> boundaryElements = new ArrayList<>();

        getLeftBoundary(root, boundaryElements, true);
        if (nonNull(root) && nonNull(root.getRight())) {
            getRightBoundary(root.getRight(), boundaryElements, true);
        }
        return boundaryElements;
    }

    private void getLeftBoundary(TreeNode<T> node, List<T> boundaryElements, boolean includeLeaf) {
        if (isNull(node)) return;
        if (includeLeaf) {
            boundaryElements.add(node.getValue());
        } else if (nonNull(node.getLeft()) || nonNull(node.getRight())) {
            boundaryElements.add(node.getValue());
        }
        if (nonNull(node.getLeft())) {
            getLeftBoundary(node.getLeft(), boundaryElements, includeLeaf);
        }
    }

    private void getRightBoundary(TreeNode<T> node, List<T> boundaryElements, boolean includeLeaf) {
        if (isNull(node)) return;
        if (includeLeaf) {
            boundaryElements.add(node.getValue());
        } else if (nonNull(node.getLeft()) || nonNull(node.getRight())) {
            boundaryElements.add(node.getValue());
        }
        if (nonNull(node.getRight())) {
            getRightBoundary(node.getRight(), boundaryElements, includeLeaf);
        }
    }

    private void getLeaves(TreeNode<T> node, List<T> elements) {
        if (isNull(node)) return;
        if (isNull(node.getLeft()) && isNull(node.getRight())) {
            elements.add(node.getValue());
        }
        getLeaves(node.getLeft(), elements);
        getLeaves(node.getRight(), elements);
    }

    public List<T> getDiagonalTraversal() {
        Map<Integer, List<T>> slopeElementMap = new TreeMap<>();
        getDiagonalTraversal(root, slopeElementMap, 0);
        return slopeElementMap.values().stream().flatMap(List::stream).collect(Collectors.toList());
    }

    private void getDiagonalTraversal(TreeNode<T> node, Map<Integer, List<T>> slopeElementMap, int slope) {
        if (isNull(node)) return;
        if (!slopeElementMap.containsKey(slope)) {
            slopeElementMap.put(slope, new ArrayList<>());
        }
        slopeElementMap.get(slope).add(node.getValue());

        getDiagonalTraversal(node.getLeft(), slopeElementMap, slope + 1);
        getDiagonalTraversal(node.getRight(), slopeElementMap, slope);
    }

    public List<T> getLevelOrderTraversalSpiral() {
        List<T> levelOrderElements = new ArrayList<>();
        boolean leftToRight = true;
        for (int i = 0; i <= getHeight(); i++) {
            levelOrderElements.addAll(getLevelOrderTraversal(i, leftToRight));
            leftToRight = !leftToRight;
        }
        return levelOrderElements;
    }

    public List<T> getPreOrderTraversal() {
        List<T> preOrderElements = new ArrayList<>();
        getPreOrderTraversal(root, preOrderElements);
        return preOrderElements;
    }

    private void getPreOrderTraversal(TreeNode<T> node, List<T> elements) {
        if (isNull(node)) return;
        elements.add(node.getValue());
        getPreOrderTraversal(node.getLeft(), elements);
        getPreOrderTraversal(node.getRight(), elements);
    }

    public List<T> getPreOrderTraversalIterative() {
        List<T> preOrderElements = new ArrayList<>();
        getPreOrderTraversalIterative(root, preOrderElements);
        return preOrderElements;
    }

    private void getPreOrderTraversalIterative(TreeNode<T> node, List<T> elements) {
        if (isNull(node)) return;
        Stack<TreeNode<T>> stack = new Stack<>();

        stack.push(node);

        while (!stack.empty()) {
            TreeNode<T> currNode = stack.pop();
            elements.add(currNode.getValue());

            if (nonNull(currNode.getRight())) {
                stack.push(currNode.getRight());
            }
            if (nonNull(currNode.getLeft())) {
                stack.push(currNode.getLeft());
            }
        }
    }

    public List<T> getInOrderTraversal() {
        List<T> inOrderTraversal = new ArrayList<>();
        getInOrderTraversal(root, inOrderTraversal);
        return inOrderTraversal;
    }

    private void getInOrderTraversal(TreeNode<T> node, List<T> elements) {
        if (isNull(node)) return;
        getInOrderTraversal(node.getLeft(), elements);
        elements.add(node.getValue());
        getInOrderTraversal(node.getRight(), elements);
    }

    public List<T> getInOrderTraversalIterative() {
        List<T> inOrderElements = new ArrayList<>();
        getInOrderTraversalIterative(root, inOrderElements);
        return inOrderElements;
    }

    private void getInOrderTraversalIterative(TreeNode<T> node, List<T> elements) {
        if (isNull(node)) return;
        Stack<TreeNode<T>> stack = new Stack<>();

        TreeNode<T> curr = node;

        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.getLeft();
            }
            curr = stack.pop();
            elements.add(curr.getValue());
            curr = curr.getRight();
        }
    }

    public List<T> getPostOrderTraversal() {
        List<T> PostOrderTraversal = new ArrayList<>();
        getPostOrderTraversal(root, PostOrderTraversal);
        return PostOrderTraversal;
    }

    private void getPostOrderTraversal(TreeNode<T> node, List<T> elements) {
        if (isNull(node)) return;
        getPostOrderTraversal(node.getLeft(), elements);
        getPostOrderTraversal(node.getRight(), elements);
        elements.add(node.getValue());
    }

    public List<T> getPostOrderTraversalIterative() {
        List<T> PostOrderElements = new ArrayList<>();
        getPostOrderTraversalIterative(root, PostOrderElements);
        return PostOrderElements;
    }

    private void getPostOrderTraversalIterative(TreeNode<T> node, List<T> elements) {
        if (isNull(node)) return;
        Stack<TreeNode<T>> stack = new Stack<>();

        TreeNode<T> curr = node;

        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.getLeft();
            }
            curr = stack.pop();
            elements.add(curr.getValue());
            curr = curr.getRight();
        }
    }
}