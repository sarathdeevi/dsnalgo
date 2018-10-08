package com.sdeevi.dsnalgo.trees;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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
                            8
        */
        sampleTree1 = new BinaryTree<>(1);
        TreeNode<Integer> node1 = new TreeNode<>(2);
        TreeNode<Integer> node2 = new TreeNode<>(3);
        TreeNode<Integer> node3 = new TreeNode<>(4);
        TreeNode<Integer> node4 = new TreeNode<>(5);
        TreeNode<Integer> node5 = new TreeNode<>(6);
        TreeNode<Integer> node6 = new TreeNode<>(7);
        TreeNode<Integer> node7 = new TreeNode<>(8);
        sampleTree1.getRoot().setLeft(node1);
        sampleTree1.getRoot().setRight(node2);
        node1.setLeft(node3);
        node1.setRight(node4);
        node2.setLeft(node5);
        node2.setRight(node6);
        node4.setLeft(node7);
    }

    @Test
    public void getHeight_thenReturnsHeight() {
        assertThat(sampleTree1.getHeight(), is(4));
    }

    @Test
    public void getLevelOrderTraversal_givenLevel_thenReturnsTraversalAtLevel() {
        List<Integer> elementsLevel1 = sampleTree1.getLevelOrderTraversal(1);
        List<Integer> elementsLevel2 = sampleTree1.getLevelOrderTraversal(2);
        List<Integer> elementsLevel3 = sampleTree1.getLevelOrderTraversal(3);

        assertThat(elementsLevel1, is(Arrays.asList(1)));
        assertThat(elementsLevel2, is(Arrays.asList(2, 3)));
        assertThat(elementsLevel3, is(Arrays.asList(4, 5, 6, 7)));
    }
}