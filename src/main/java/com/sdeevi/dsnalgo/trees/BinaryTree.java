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

    private int getHeight(Node n) {
        if (n == null) return 0;
        int leftHeight = getHeight(n.left);
        int rightHeight = getHeight(n.right);

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

    private void getLevelOrderTraversal(Node n, int level, List<Integer> levelOrderTraversalList, boolean leftToRight) {
        if (n == null) return;
        if (level == 1) {
            levelOrderTraversalList.add(n.data);
        } else {
            if (leftToRight) {
                getLevelOrderTraversal(n.left, level - 1, levelOrderTraversalList, true);
                getLevelOrderTraversal(n.right, level - 1, levelOrderTraversalList, true);
            } else {
                getLevelOrderTraversal(n.right, level - 1, levelOrderTraversalList, false);
                getLevelOrderTraversal(n.left, level - 1, levelOrderTraversalList, false);
            }
        }
    }

    public List<Integer> getLevelOrderTraversalUsingQueue() {
        if (root == null) {
            return new ArrayList<>();
        }
        List<Integer> elements = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node temp = queue.poll();
            elements.add(temp.data);

            if (temp.left != null) {
                queue.add(temp.left);
            }
            if (temp.right != null) {
                queue.add(temp.right);
            }
        }
        return elements;
    }

    public List<Integer> getVerticalLine(int line_num) {
        List<Integer> verticalLine = new ArrayList<>();
        return getVerticalLine(root, verticalLine, line_num, 0);
    }

    private List<Integer> getVerticalLine(Node n, List<Integer> values, int line_num, int curr) {
        if (n != null) {
            if (curr == line_num) {
                values.add(n.data);
            }

            getVerticalLine(n.left, values, line_num, curr - 1);
            getVerticalLine(n.right, values, line_num, curr + 1);
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

    private void findMinMax(Node n, AtomicInteger min, AtomicInteger max, int curr) {
        if (n == null) return;
        if (curr < min.get()) {
            min.set(curr);
        } else if (curr > max.get()) {
            max.set(curr);
        }

        findMinMax(n.left, min, max, curr - 1);
        findMinMax(n.right, min, max, curr + 1);
    }

    public List<Integer> getLeftView() {
        List<Integer> leftViewElements = new ArrayList<>();
        AtomicInteger maxLevel = new AtomicInteger();
        getLeftView(root, leftViewElements, 1, maxLevel);
        return leftViewElements;
    }

    private void getLeftView(Node n, List<Integer> leftViewElements, int currLevel, AtomicInteger maxLevel) {
        if (n == null) return;

        if (maxLevel.get() < currLevel) {
            leftViewElements.add(n.data);
            maxLevel.set(currLevel);
        }

        getLeftView(n.left, leftViewElements, currLevel + 1, maxLevel);
        getLeftView(n.right, leftViewElements, currLevel + 1, maxLevel);
    }

    public List<Integer> getRightView() {
        List<Integer> rightViewElements = new ArrayList<>();
        AtomicInteger maxLevel = new AtomicInteger();
        getRightView(root, rightViewElements, 1, maxLevel);
        return rightViewElements;
    }

    private void getRightView(Node n, List<Integer> rightViewElements, int currLevel, AtomicInteger maxLevel) {
        if (n == null) return;

        if (maxLevel.get() < currLevel) {
            rightViewElements.add(n.data);
            maxLevel.set(currLevel);
        }

        getRightView(n.right, rightViewElements, currLevel + 1, maxLevel);
        getRightView(n.left, rightViewElements, currLevel + 1, maxLevel);
    }

    public List<Integer> getBoundaryTraversal() {
        List<Integer> boundaryElements = new ArrayList<>();

        if (root != null) {
            getLeftBoundary(root, boundaryElements, false);
        }
        getLeaves(root, boundaryElements);
        List<Integer> rightBoundary = new ArrayList<>();
        if (root != null && root.right != null) {
            getRightBoundary(root.right, rightBoundary, false);
        }
        Collections.reverse(rightBoundary);
        boundaryElements.addAll(rightBoundary);
        return boundaryElements;
    }

    public List<Integer> getTopView() {
        List<Integer> boundaryElements = new ArrayList<>();

        getLeftBoundary(root, boundaryElements, true);
        if (root != null && root.right != null) {
            getRightBoundary(root.right, boundaryElements, true);
        }
        return boundaryElements;
    }

    private void getLeftBoundary(Node n, List<Integer> boundaryElements, boolean includeLeaf) {
        if (n == null) return;
        if (includeLeaf) {
            boundaryElements.add(n.data);
        } else if (n.left != null || n.right != null) {
            boundaryElements.add(n.data);
        }
        if (n.left != null) {
            getLeftBoundary(n.left, boundaryElements, includeLeaf);
        }
    }

    private void getRightBoundary(Node n, List<Integer> boundaryElements, boolean includeLeaf) {
        if (n == null) return;
        if (includeLeaf) {
            boundaryElements.add(n.data);
        } else if (n.left != null || n.right != null) {
            boundaryElements.add(n.data);
        }
        if (n.right != null) {
            getRightBoundary(n.right, boundaryElements, includeLeaf);
        }
    }

    private void getLeaves(Node n, List<Integer> elements) {
        if (n == null) return;
        if (n.left == null && n.right == null) {
            elements.add(n.data);
        }
        getLeaves(n.left, elements);
        getLeaves(n.right, elements);
    }

    public List<Integer> getDiagonalTraversal() {
        Map<Integer, List<Integer>> slopeElementMap = new TreeMap<>();
        getDiagonalTraversal(root, slopeElementMap, 0);
        return slopeElementMap.values().stream().flatMap(List::stream).collect(Collectors.toList());
    }

    private void getDiagonalTraversal(Node n, Map<Integer, List<Integer>> slopeElementMap, int slope) {
        if (n == null) return;
        if (!slopeElementMap.containsKey(slope)) {
            slopeElementMap.put(slope, new ArrayList<>());
        }
        slopeElementMap.get(slope).add(n.data);

        getDiagonalTraversal(n.left, slopeElementMap, slope + 1);
        getDiagonalTraversal(n.right, slopeElementMap, slope);
    }

    public List<Integer> getPreOrderTraversal() {
        List<Integer> preOrderElements = new ArrayList<>();
        getPreOrderTraversal(root, preOrderElements);
        return preOrderElements;
    }

    private void getPreOrderTraversal(Node n, List<Integer> elements) {
        if (n == null) return;
        elements.add(n.data);
        getPreOrderTraversal(n.left, elements);
        getPreOrderTraversal(n.right, elements);
    }

    public List<Integer> getPreOrderTraversalIterative() {
        List<Integer> preOrderElements = new ArrayList<>();
        getPreOrderTraversalIterative(root, preOrderElements);
        return preOrderElements;
    }

    private void getPreOrderTraversalIterative(Node n, List<Integer> elements) {
        if (n == null) return;
        Stack<Node> stack = new Stack<>();

        stack.push(n);

        while (!stack.empty()) {
            Node currNode = stack.pop();
            elements.add(currNode.data);

            if (currNode.right != null) {
                stack.push(currNode.right);
            }
            if (currNode.left != null) {
                stack.push(currNode.left);
            }
        }
    }

    public List<Integer> getInOrderTraversal() {
        List<Integer> inOrderTraversal = new ArrayList<>();
        getInOrderTraversal(root, inOrderTraversal);
        return inOrderTraversal;
    }

    private void getInOrderTraversal(Node n, List<Integer> elements) {
        if (n == null) return;
        getInOrderTraversal(n.left, elements);
        elements.add(n.data);
        getInOrderTraversal(n.right, elements);
    }

    public List<Integer> getInOrderTraversalIterative() {
        List<Integer> inOrderElements = new ArrayList<>();
        getInOrderTraversalIterative(root, inOrderElements);
        return inOrderElements;
    }

    private void getInOrderTraversalIterative(Node n, List<Integer> elements) {
        if (n == null) return;
        Stack<Node> stack = new Stack<>();

        Node curr = n;

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

    private void getPostOrderTraversal(Node n, List<Integer> elements) {
        if (n == null) return;
        getPostOrderTraversal(n.left, elements);
        getPostOrderTraversal(n.right, elements);
        elements.add(n.data);
    }

    public List<Integer> getPostOrderTraversalIterative() {
        List<Integer> postOrderElements = new ArrayList<>();
        getPostOrderTraversalIterative(root, postOrderElements);
        return postOrderElements;
    }

    private void getPostOrderTraversalIterative(Node n, List<Integer> elements) {
        if (n == null) return;
        Stack<Node> stack = new Stack<>();

        Node curr = n;

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

    private void populateRootToLeafPaths(Node n, StringBuilder sb) {
        if (n == null) {
            return;
        }
        stack.get().push(n.data);
        populateRootToLeafPaths(n.left, sb);
        if (n.left == null && n.right == null) {
            sb.append(stack.get().toString());
        }
        populateRootToLeafPaths(n.right, sb);
        stack.get().pop();
    }

    public String printPathsWithGivenSum(int sum) {
        this.sum.set(0);
        stack.set(new Stack<>());
        StringBuilder sb = new StringBuilder();
        Node n = root;
        populatePathsWithGivenSum(n, sum, sb);
        return sb.toString();
    }

    private void populatePathsWithGivenSum(Node n, Integer sum, StringBuilder sb) {
        if (n == null) {
            return;
        }
        int value = n.data;
        this.sum.set(this.sum.get() + value);
        stack.get().push(value);
        if (this.sum.get().equals(sum)) {
            sb.append(stack.get().toString());
        }
        populatePathsWithGivenSum(n.left, sum, sb);
        populatePathsWithGivenSum(n.right, sum, sb);
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

    public String getNodesWithKLeaves(int k) {
        List<Integer> elements = new ArrayList<>();
        kLeaves(root, k, elements);
        return elements.toString();
    }

    private int kLeaves(Node n, int k, List<Integer> elements) {
        if (n == null) return 0;
        if (n.left == null && n.right == null) {
            if (k == 0) {
                elements.add(n.data);
            }
            return 1;
        }

        int total = kLeaves(n.left, k, elements) + kLeaves(n.right, k, elements);
        if (k == total) {
            elements.add(n.data);
        }
        return total;
    }

    public int getDiameter() {
        return getDiameter(root);
    }

    private int getDiameter(Node n) {
        if (n == null) return 0;
        int leftHeight = getHeight(n.left);
        int rightHeight = getHeight(n.right);

        int lDiameter = getDiameter(n.left);
        int rDiameter = getDiameter(n.right);

        return Math.max(leftHeight + rightHeight + 1, Math.max(lDiameter, rDiameter));
    }

    public boolean isBST() {
        return isBST(root, null, null);
    }

    private boolean isBST(Node n, Node left, Node right) {
        if (n == null) return true;
        if (left != null && n.data <= left.data) return false;
        if (right != null && n.data >= right.data) return false;

        return isBST(n.left, left, n) && isBST(n.right, n, right);
    }

    public void mirror() {
        mirror(root);
    }

    private void mirror(Node n) {
        if (n == null) return;
        Node temp = n.left;
        n.left = n.right;
        n.right = temp;

        mirror(n.left);
        mirror(n.right);
    }

    public boolean isSelfMirror() {
        if (root == null) return true;
        return isMirror(root.left, root.right);
    }

    public boolean isMirrorOf(BinaryTree tree2) {
        return isMirror(root, tree2.root);
    }

    private boolean isMirror(Node n1, Node n2) {
        if (n1 == null && n2 == null) return true;
        if (n1 == null || n2 == null) return false;

        return n1.data == n2.data && isMirror(n1.left, n2.right) && isMirror(n1.right, n2.left);
    }

    public boolean isIsomorphic(BinaryTree tree2) {
        return isIsomorphic(root, tree2.root);
    }

    private boolean isIsomorphic(Node n1, Node n2) {
        if (n1 == null && n2 == null) return true;
        if (n1 == null || n2 == null) return false;

        if (n1.data != n2.data) {
            return false;
        }

        return (isIsomorphic(n1.left, n2.left) && isIsomorphic(n1.right, n2.right)) ||
                (isIsomorphic(n1.left, n2.right) && isIsomorphic(n1.right, n2.left));
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