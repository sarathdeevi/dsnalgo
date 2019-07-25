package com.sdeevi.dsnalgo.trees;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.sdeevi.dsnalgo.trees.BinaryTree.Node;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BinaryTreeTest {

    private BinaryTree sampleTree1;

    @Before
    public void setUp() {
        /*
                            1
                       2            3
                   4      5      6     7
                            8        9     10
        */
        sampleTree1 = new BinaryTree(1);
        Node node1 = new Node(2);
        Node node2 = new Node(3);
        Node node3 = new Node(4);
        Node node4 = new Node(5);
        Node node5 = new Node(6);
        Node node6 = new Node(7);
        Node node7 = new Node(8);
        Node node8 = new Node(9);
        Node node9 = new Node(10);
        sampleTree1.root.left = node1;
        sampleTree1.root.right = node2;
        node1.left = node3;
        node1.right = node4;
        node2.left = node5;
        node2.right = node6;
        node4.left = node7;
        node6.left = node8;
        node6.right = node9;
    }

    @Test
    public void printAllPathsFromRouteToLeaf_thenPrintsAllPathsToLeaves() {
        String allPaths = sampleTree1.printRootToLeafPaths();

        assertThat(allPaths, equalTo("[1, 2, 4]"
                + "[1, 2, 5, 8]"
                + "[1, 3, 6]"
                + "[1, 3, 7, 9]"
                + "[1, 3, 7, 10]"));
    }

    @Test
    public void printAllPathsToSum_thenPrintsAllPathsThatSumTo() {
        String allPaths1 = sampleTree1.printPathsWithGivenSum(16);
        String allPaths2 = sampleTree1.printPathsWithGivenSum(21);

        assertThat(allPaths1, equalTo("[1, 2, 5, 8]"));
        assertThat(allPaths2, equalTo("[1, 3, 7, 10]"));
    }

    @Test
    public void getHeight_thenReturnsHeight() {
        assertThat(sampleTree1.getHeight(), is(4));
    }

    @Test
    public void getLevelOrderTraversal_thenReturnsTraversal() {
        List<Integer> elements = sampleTree1.getLevelOrderTraversal();

        assertThat(elements, is(asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)));
    }

    @Test
    public void getZigZagTraversal_thenReturnsTraversal() {
        List<Integer> elements = sampleTree1.getZigZagTraversal();

        assertThat(elements, is(asList(1, 3, 2, 4, 5, 6, 7, 10, 9, 8)));
    }

    @Test
    public void getLevelOrderTraversal_givenLevel_thenReturnsTraversalAtLevel() {
        List<Integer> elementsLevel1 = sampleTree1.getLevelOrderTraversal(1);
        List<Integer> elementsLevel2 = sampleTree1.getLevelOrderTraversal(2);
        List<Integer> elementsLevel3 = sampleTree1.getLevelOrderTraversal(3);

        assertThat(elementsLevel1, is(singletonList(1)));
        assertThat(elementsLevel2, is(asList(2, 3)));
        assertThat(elementsLevel3, is(asList(4, 5, 6, 7)));
    }

    @Test
    public void getLevelOrderTraversalUsingQueue_thenReturnsTraversal() {
        List<Integer> elements = sampleTree1.getLevelOrderTraversalUsingQueue();

        assertThat(elements, is(asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)));
    }

    @Test
    public void getVerticalLine_givenLineNumber_thenReturnsVerticalTraversal() {
        List<Integer> verticalElements1 = sampleTree1.getVerticalLine(-2);
        List<Integer> verticalElements2 = sampleTree1.getVerticalLine(-1);
        List<Integer> verticalElements3 = sampleTree1.getVerticalLine(0);
        List<Integer> verticalElements4 = sampleTree1.getVerticalLine(1);
        List<Integer> verticalElements5 = sampleTree1.getVerticalLine(2);
        List<Integer> verticalElements6 = sampleTree1.getVerticalLine(3);

        assertThat(verticalElements1, is(singletonList(4)));
        assertThat(verticalElements2, is(asList(2, 8)));
        assertThat(verticalElements3, is(asList(1, 5, 6)));
        assertThat(verticalElements4, is(asList(3, 9)));
        assertThat(verticalElements5, is(singletonList(7)));
        assertThat(verticalElements6, is(singletonList(10)));
    }

    @Test
    public void getVerticalOrderTraversal_thenReturnsVerticalTraversal() {
        List<Integer> verticalElements = sampleTree1.getVerticalOrderTraversal();

        assertThat(verticalElements, is(asList(4, 2, 8, 1, 5, 6, 3, 9, 7, 10)));
    }

    @Test
    public void getLeftView_thenReturnsLeftViewElements() {
        List<Integer> leftViewElements = sampleTree1.getLeftView();

        assertThat(leftViewElements, is(asList(1, 2, 4, 8)));
    }

    @Test
    public void getRightView_thenReturnsRightViewElements() {
        List<Integer> rightViewElements = sampleTree1.getRightView();

        assertThat(rightViewElements, is(asList(1, 3, 7, 10)));
    }

    @Test
    public void getTopView_thenReturnsTopViewElements() {
        List<Integer> topViewElements = sampleTree1.getTopView();

        assertThat(topViewElements, is(asList(1, 2, 4, 3, 7, 10)));
    }

    @Test
    public void getBoundaryTraversal_thenReturnsAllBoundaryElements() {
        List<Integer> boundaryElements = sampleTree1.getBoundaryTraversal();

        assertThat(boundaryElements, is(asList(1, 2, 4, 8, 6, 9, 10, 7, 3)));
    }

    @Test
    public void getDiagonalTraversal_thenReturnsElementsInDiagonalTraversal() {
        List<Integer> diagonalTraversalElements = sampleTree1.getDiagonalTraversal();

        assertThat(diagonalTraversalElements, is(asList(1, 3, 7, 10, 2, 5, 6, 9, 4, 8)));
    }

    @Test
    public void getLevelOrderTraversalSpiral_thenReturnsLevelOrderSpiral() {
        List<Integer> levelOrderTraversalSpiral = sampleTree1.getLevelOrderTraversalSpiral();

        assertThat(levelOrderTraversalSpiral, is(asList(1, 2, 3, 7, 6, 5, 4, 8, 9, 10)));
    }

    @Test
    public void getPreOrderTraversal_thenReturnsPreOrderTraversal() {
        List<Integer> preOrderTraversal = sampleTree1.getPreOrderTraversal();

        assertThat(preOrderTraversal, is(asList(1, 2, 4, 5, 8, 3, 6, 7, 9, 10)));
    }

    @Test
    public void getPreOrderTraversalIterative_thenReturnsPreOrderTraversal() {
        List<Integer> preOrderTraversal = sampleTree1.getPreOrderTraversalIterative();

        assertThat(preOrderTraversal, is(asList(1, 2, 4, 5, 8, 3, 6, 7, 9, 10)));
    }

    @Test
    public void getInOrderTraversal_thenReturnsInOrderTraversal() {
        List<Integer> inOrderTraversal = sampleTree1.getInOrderTraversal();

        assertThat(inOrderTraversal, is(asList(4, 2, 8, 5, 1, 6, 3, 9, 7, 10)));
    }

    @Test
    public void getInOrderTraversalIterative_thenReturnsInOrderTraversal() {
        List<Integer> inOrderTraversal = sampleTree1.getInOrderTraversalIterative();

        assertThat(inOrderTraversal, is(asList(4, 2, 8, 5, 1, 6, 3, 9, 7, 10)));
    }

    @Test
    public void getPostOrderTraversal_thenReturnsPostOrderTraversal() {
        List<Integer> postOrderTraversal = sampleTree1.getPostOrderTraversal();

        assertThat(postOrderTraversal, is(asList(4, 8, 5, 2, 6, 9, 10, 7, 3, 1)));
    }

    @Test
    public void getPostOrderTraversalIterative_thenReturnsPostOrderTraversal() {
        List<Integer> postOrderTraversal = sampleTree1.getPostOrderTraversalIterative();

        assertThat(postOrderTraversal, is(asList(4, 8, 5, 2, 6, 9, 10, 7, 3, 1)));
    }
}