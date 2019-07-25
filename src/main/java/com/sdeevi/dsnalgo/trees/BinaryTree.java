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

public class BinaryTree {
    private final ThreadLocal<Integer> sum = new ThreadLocal<>();
    private final ThreadLocal<Stack<Integer>> stack = new ThreadLocal<>();
    public Node root;

    public BinaryTree(int root) {
        this.root = new Node(root);
    }

    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(Node node) {
        if (isNull(node)) return 0;
        int leftHeight = getHeight(node.left);
        int rightHeight = getHeight(node.right);

        return Math.max(leftHeight, rightHeight) + 1;
    }

    public List<Integer> getLevelOrderTraversal() {
        List<Integer> levelOrderTraversalList = new ArrayList<>();
        for (int i = 0; i <= getHeight(); i++) {
            levelOrderTraversalList.addAll(getLevelOrderTraversal(i));
        }
        return levelOrderTraversalList;
    }

    public List<Integer> getZigZagTraversal() {
        List<Integer> levelOrderTraversalList = new ArrayList<>();
        for (int i = 0; i <= getHeight(); i++) {
            levelOrderTraversalList.addAll(getLevelOrderTraversal(i, i % 2 != 0));
        }
        return levelOrderTraversalList;
    }

    public List<Integer> getLevelOrderTraversal(int level) {
        List<Integer> levelOrderTraversalList = new ArrayList<>();
        getLevelOrderTraversal(root, level, levelOrderTraversalList, true);
        return levelOrderTraversalList;
    }

    private List<Integer> getLevelOrderTraversal(int level, boolean leftToRight) {
        List<Integer> levelOrderTraversalList = new ArrayList<>();
        getLevelOrderTraversal(root, level, levelOrderTraversalList, leftToRight);
        return levelOrderTraversalList;
    }

    private void getLevelOrderTraversal(Node node, int level, List<Integer> levelOrderTraversalList, boolean leftToRight) {
        if (isNull(node)) return;
        if (level == 1) {
            levelOrderTraversalList.add(node.data);
        } else {
            if (leftToRight) {
                getLevelOrderTraversal(node.left, level - 1, levelOrderTraversalList, true);
                getLevelOrderTraversal(node.right, level - 1, levelOrderTraversalList, true);
            } else {
                getLevelOrderTraversal(node.right, level - 1, levelOrderTraversalList, false);
                getLevelOrderTraversal(node.left, level - 1, levelOrderTraversalList, false);
            }
        }
    }

    public List<Integer> getLevelOrderTraversalUsingQueue() {
        if (isNull(root)) {
            return new ArrayList<>();
        }
        List<Integer> elements = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node temp = queue.poll();
            elements.add(temp.data);

            if (nonNull(temp.left)) {
                queue.add(temp.left);
            }
            if (nonNull(temp.right)) {
                queue.add(temp.right);
            }
        }
        return elements;
    }

    public List<Integer> getVerticalLine(int line_num) {
        List<Integer> verticalLine = new ArrayList<>();
        return getVerticalLine(root, verticalLine, line_num, 0);
    }

    private List<Integer> getVerticalLine(Node node, List<Integer> values, int line_num, int curr) {
        if (nonNull(node)) {
            if (curr == line_num) {
                values.add(node.data);
            }

            getVerticalLine(node.left, values, line_num, curr - 1);
            getVerticalLine(node.right, values, line_num, curr + 1);
        }
        return values;
    }

    public List<Integer> getVerticalOrderTraversal() {
        List<Integer> verticalOrderElements = new ArrayList<>();
        AtomicInteger min = new AtomicInteger(), max = new AtomicInteger();
        findMinMax(root, min, max, 0);

        for (int i = min.get(); i <= max.get(); i++) {
            verticalOrderElements.addAll(getVerticalLine(i));
        }
        return verticalOrderElements;
    }

    private void findMinMax(Node node, AtomicInteger min, AtomicInteger max, int curr) {
        if (isNull(node)) return;
        if (curr < min.get()) {
            min.set(curr);
        } else if (curr > max.get()) {
            max.set(curr);
        }

        findMinMax(node.left, min, max, curr - 1);
        findMinMax(node.right, min, max, curr + 1);
    }

    public List<Integer> getLeftView() {
        List<Integer> leftViewElements = new ArrayList<>();
        AtomicInteger maxLevel = new AtomicInteger();
        getLeftView(root, leftViewElements, 1, maxLevel);
        return leftViewElements;
    }

    private void getLeftView(Node node, List<Integer> leftViewElements, int currLevel, AtomicInteger maxLevel) {
        if (isNull(node)) return;

        if (maxLevel.get() < currLevel) {
            leftViewElements.add(node.data);
            maxLevel.set(currLevel);
        }

        getLeftView(node.left, leftViewElements, currLevel + 1, maxLevel);
        getLeftView(node.right, leftViewElements, currLevel + 1, maxLevel);
    }

    public List<Integer> getRightView() {
        List<Integer> rightViewElements = new ArrayList<>();
        AtomicInteger maxLevel = new AtomicInteger();
        getRightView(root, rightViewElements, 1, maxLevel);
        return rightViewElements;
    }

    private void getRightView(Node node, List<Integer> rightViewElements, int currLevel, AtomicInteger maxLevel) {
        if (isNull(node)) return;

        if (maxLevel.get() < currLevel) {
            rightViewElements.add(node.data);
            maxLevel.set(currLevel);
        }

        getRightView(node.right, rightViewElements, currLevel + 1, maxLevel);
        getRightView(node.left, rightViewElements, currLevel + 1, maxLevel);
    }

    public List<Integer> getBoundaryTraversal() {
        List<Integer> boundaryElements = new ArrayList<>();

        if (nonNull(root)) {
            getLeftBoundary(root, boundaryElements, false);
        }
        getLeaves(root, boundaryElements);
        List<Integer> rightBoundary = new ArrayList<>();
        if (nonNull(root) && nonNull(root.right)) {
            getRightBoundary(root.right, rightBoundary, false);
        }
        Collections.reverse(rightBoundary);
        boundaryElements.addAll(rightBoundary);
        return boundaryElements;
    }

    public List<Integer> getTopView() {
        List<Integer> boundaryElements = new ArrayList<>();

        getLeftBoundary(root, boundaryElements, true);
        if (nonNull(root) && nonNull(root.right)) {
            getRightBoundary(root.right, boundaryElements, true);
        }
        return boundaryElements;
    }

    private void getLeftBoundary(Node node, List<Integer> boundaryElements, boolean includeLeaf) {
        if (isNull(node)) return;
        if (includeLeaf) {
            boundaryElements.add(node.data);
        } else if (nonNull(node.left) || nonNull(node.right)) {
            boundaryElements.add(node.data);
        }
        if (nonNull(node.left)) {
            getLeftBoundary(node.left, boundaryElements, includeLeaf);
        }
    }

    private void getRightBoundary(Node node, List<Integer> boundaryElements, boolean includeLeaf) {
        if (isNull(node)) return;
        if (includeLeaf) {
            boundaryElements.add(node.data);
        } else if (nonNull(node.left) || nonNull(node.right)) {
            boundaryElements.add(node.data);
        }
        if (nonNull(node.right)) {
            getRightBoundary(node.right, boundaryElements, includeLeaf);
        }
    }

    private void getLeaves(Node node, List<Integer> elements) {
        if (isNull(node)) return;
        if (isNull(node.left) && isNull(node.right)) {
            elements.add(node.data);
        }
        getLeaves(node.left, elements);
        getLeaves(node.right, elements);
    }

    public List<Integer> getDiagonalTraversal() {
        Map<Integer, List<Integer>> slopeElementMap = new TreeMap<>();
        getDiagonalTraversal(root, slopeElementMap, 0);
        return slopeElementMap.values().stream().flatMap(List::stream).collect(Collectors.toList());
    }

    private void getDiagonalTraversal(Node node, Map<Integer, List<Integer>> slopeElementMap, int slope) {
        if (isNull(node)) return;
        if (!slopeElementMap.containsKey(slope)) {
            slopeElementMap.put(slope, new ArrayList<>());
        }
        slopeElementMap.get(slope).add(node.data);

        getDiagonalTraversal(node.left, slopeElementMap, slope + 1);
        getDiagonalTraversal(node.right, slopeElementMap, slope);
    }

    public List<Integer> getLevelOrderTraversalSpiral() {
        List<Integer> levelOrderElements = new ArrayList<>();
        boolean leftToRight = true;
        for (int i = 0; i <= getHeight(); i++) {
            levelOrderElements.addAll(getLevelOrderTraversal(i, leftToRight));
            leftToRight = !leftToRight;
        }
        return levelOrderElements;
    }

    public List<Integer> getPreOrderTraversal() {
        List<Integer> preOrderElements = new ArrayList<>();
        getPreOrderTraversal(root, preOrderElements);
        return preOrderElements;
    }

    private void getPreOrderTraversal(Node node, List<Integer> elements) {
        if (isNull(node)) return;
        elements.add(node.data);
        getPreOrderTraversal(node.left, elements);
        getPreOrderTraversal(node.right, elements);
    }

    public List<Integer> getPreOrderTraversalIterative() {
        List<Integer> preOrderElements = new ArrayList<>();
        getPreOrderTraversalIterative(root, preOrderElements);
        return preOrderElements;
    }

    private void getPreOrderTraversalIterative(Node node, List<Integer> elements) {
        if (isNull(node)) return;
        Stack<Node> stack = new Stack<>();

        stack.push(node);

        while (!stack.empty()) {
            Node currNode = stack.pop();
            elements.add(currNode.data);

            if (nonNull(currNode.right)) {
                stack.push(currNode.right);
            }
            if (nonNull(currNode.left)) {
                stack.push(currNode.left);
            }
        }
    }

    public List<Integer> getInOrderTraversal() {
        List<Integer> inOrderTraversal = new ArrayList<>();
        getInOrderTraversal(root, inOrderTraversal);
        return inOrderTraversal;
    }

    private void getInOrderTraversal(Node node, List<Integer> elements) {
        if (isNull(node)) return;
        getInOrderTraversal(node.left, elements);
        elements.add(node.data);
        getInOrderTraversal(node.right, elements);
    }

    public List<Integer> getInOrderTraversalIterative() {
        List<Integer> inOrderElements = new ArrayList<>();
        getInOrderTraversalIterative(root, inOrderElements);
        return inOrderElements;
    }

    private void getInOrderTraversalIterative(Node node, List<Integer> elements) {
        if (isNull(node)) return;
        Stack<Node> stack = new Stack<>();

        Node curr = node;

        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            elements.add(curr.data);
            curr = curr.right;
        }
    }

    public List<Integer> getPostOrderTraversal() {
        List<Integer> PostOrderTraversal = new ArrayList<>();
        getPostOrderTraversal(root, PostOrderTraversal);
        return PostOrderTraversal;
    }

    private void getPostOrderTraversal(Node node, List<Integer> elements) {
        if (isNull(node)) return;
        getPostOrderTraversal(node.left, elements);
        getPostOrderTraversal(node.right, elements);
        elements.add(node.data);
    }

    public List<Integer> getPostOrderTraversalIterative() {
        List<Integer> postOrderElements = new ArrayList<>();
        getPostOrderTraversalIterative(root, postOrderElements);
        return postOrderElements;
    }

    private void getPostOrderTraversalIterative(Node node, List<Integer> elements) {
        if (isNull(node)) return;
        Stack<Node> stack = new Stack<>();

        Node curr = node;

        while (curr != null || !stack.isEmpty()) {
            if (curr != null) {
                stack.push(curr);
                curr = curr.left;
            } else {
                Node temp = stack.peek().right;

                if (temp == null) {
                    temp = stack.pop();
                    elements.add(temp.data);

                    while (!stack.isEmpty() && temp == stack.peek().right) {
                        temp = stack.pop();
                        elements.add(temp.data);
                    }
                } else {
                    curr = temp;
                }
            }
        }
    }

    public String printRootToLeafPaths() {
        stack.set(new Stack<>());
        StringBuilder sb = new StringBuilder();
        populateRootToLeafPaths(root, sb);
        return sb.toString();
    }

    private void populateRootToLeafPaths(Node node, StringBuilder sb) {
        if (node == null) {
            return;
        }
        stack.get().push(node.data);
        populateRootToLeafPaths(node.left, sb);
        if (node.left == null && node.right == null) {
            sb.append(stack.get().toString());
        }
        populateRootToLeafPaths(node.right, sb);
        stack.get().pop();
    }

    public String printPathsWithGivenSum(int sum) {
        this.sum.set(0);
        stack.set(new Stack<>());
        StringBuilder sb = new StringBuilder();
        Node node = root;
        populatePathsWithGivenSum(node, sum, sb);
        return sb.toString();
    }

    private void populatePathsWithGivenSum(Node node, Integer sum, StringBuilder sb) {
        if (node == null) {
            return;
        }
        int value = node.data;
        this.sum.set(this.sum.get() + value);
        stack.get().push(value);
        if (this.sum.get().equals(sum)) {
            sb.append(stack.get().toString());
        }
        populatePathsWithGivenSum(node.left, sum, sb);
        populatePathsWithGivenSum(node.right, sum, sb);
        this.sum.set(this.sum.get() - value);
        stack.get().pop();
    }

    public String printNodesAtDistanceIterative(int distance) {
        List<Integer> elements = new ArrayList<>();
        if (root != null) {
            Queue<Node> q = new LinkedList<>();
            q.add(root);
            q.add(null);
            int level = 0;
            while (q.size() > 0) {
                Node temp = q.remove();

                if (level == distance && temp != null) {
                    elements.add(temp.data);
                }
                if (temp == null) {
                    if (q.peek() != null) q.add(null);
                    level += 1;

                    if (level > distance) break;
                } else {
                    if (temp.left != null) {
                        q.add(temp.left);
                    }
                    if (temp.right != null) {
                        q.add(temp.right);
                    }
                }
            }
        }
        return elements.toString();
    }

    public String printNodesAtDistance(int distance) {
        List<Integer> elements = new ArrayList<>();
        populateNodesAtDistanceDown(root, distance, elements);
        return elements.toString();
    }

    private void populateNodesAtDistanceDown(Node n, int distance, List<Integer> elements) {
        if (n == null) return;
        if (distance == 0) elements.add(n.data);
        else {
            populateNodesAtDistanceDown(n.left, distance - 1, elements);
            populateNodesAtDistanceDown(n.right, distance - 1, elements);
        }
    }

    public String printNodesAtDistanceForTargetNode(Node target, int distance) {
        List<Integer> elements = new ArrayList<>();
        populateNodesAtDistanceForTargetNode(root, target, distance, elements);
        return elements.toString();
    }

    private int populateNodesAtDistanceForTargetNode(Node n, Node target, int distance, List<Integer> elements) {
        if (n == null) return -1;
        if (n == target) {
            populateNodesAtDistanceDown(n, distance, elements);
            return 0;
        }

        int d = populateNodesAtDistanceForTargetNode(n.left, target, distance, elements);
        if (d != -1) {
            if (d + 1 == distance) {
                elements.add(n.data);
            } else {
                populateNodesAtDistanceDown(n.right, distance - d - 2, elements);
            }
            return 1 + d;
        }
        d = populateNodesAtDistanceForTargetNode(n.right, target, distance, elements);
        if (d != -1) {
            if (d + 1 == distance) {
                elements.add(n.data);
            } else {
                populateNodesAtDistanceDown(n.left, distance - d - 2, elements);
            }
            return 1 + d;
        }
        return -1;
    }

    public static class Node {

        public Node left;
        public Node right;
        public int data;

        public Node(int data) {
            this.data = data;
        }
    }
}