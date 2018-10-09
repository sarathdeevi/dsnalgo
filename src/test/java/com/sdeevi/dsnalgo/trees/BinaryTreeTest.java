package com.sdeevi.dsnalgo.trees;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BinaryTreeTest {

    private BinaryTree<Integer> sampleTree1;

    @Before
    public void setUp() {
        /*
                            1
                       2           3
                   4      5     6     7
                            8      9     10
        */
        sampleTree1 = new BinaryTree<>(1);
        TreeNode<Integer> node1 = new TreeNode<>(2);
        TreeNode<Integer> node2 = new TreeNode<>(3);
        TreeNode<Integer> node3 = new TreeNode<>(4);
        TreeNode<Integer> node4 = new TreeNode<>(5);
        TreeNode<Integer> node5 = new TreeNode<>(6);
        TreeNode<Integer> node6 = new TreeNode<>(7);
        TreeNode<Integer> node7 = new TreeNode<>(8);
        TreeNode<Integer> node8 = new TreeNode<>(9);
        TreeNode<Integer> node9 = new TreeNode<>(10);
        sampleTree1.getRoot().setLeft(node1);
        sampleTree1.getRoot().setRight(node2);
        node1.setLeft(node3);
        node1.setRight(node4);
        node2.setLeft(node5);
        node2.setRight(node6);
        node4.setLeft(node7);
        node6.setLeft(node8);
        node6.setRight(node9);
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
    public void getLevelOrderTraversal_givenLevel_thenReturnsTraversalAtLevel() {
        List<Integer> elementsLevel1 = sampleTree1.getLevelOrderTraversal(1);
        List<Integer> elementsLevel2 = sampleTree1.getLevelOrderTraversal(2);
        List<Integer> elementsLevel3 = sampleTree1.getLevelOrderTraversal(3);

        assertThat(elementsLevel1, is(asList(1)));
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

        assertThat(verticalElements1, is(asList(4)));
        assertThat(verticalElements2, is(asList(2, 8)));
        assertThat(verticalElements3, is(asList(1, 5, 6)));
        assertThat(verticalElements4, is(asList(3, 9)));
        assertThat(verticalElements5, is(asList(7)));
        assertThat(verticalElements6, is(asList(10)));
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
}