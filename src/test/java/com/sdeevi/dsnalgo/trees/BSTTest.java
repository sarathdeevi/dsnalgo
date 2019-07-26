package com.sdeevi.dsnalgo.trees;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BSTTest {

    private BST sampleBST;

    @Before
    public void setUp() throws Exception {
        /*
                          10
                   3             20
               1      8       12     29
                 2                 25  29
         */
        sampleBST = new BST();
        sampleBST.add(10);
        sampleBST.add(20);
        sampleBST.add(3);
        sampleBST.addIterative(12);
        sampleBST.add(8);
        sampleBST.add(1);
        sampleBST.add(29);
        sampleBST.addIterative(29);
        sampleBST.add(25);
        sampleBST.add(2);
    }

    @Test
    public void add_thenAddsElementsAccordingToOrder() {
        BST bst = new BST();
        bst.add(10);
        bst.add(20);
        bst.add(3);
        bst.add(12);
        bst.addIterative(8);
        bst.add(1);
        bst.add(29);
        bst.add(29);
        bst.add(25);

        assertThat(bst.getInOrder().toString(), is("[1, 3, 8, 10, 12, 20, 25, 29, 29]"));
    }

    @Test
    public void preOrder_thenReturnsPreOrderTraversal() {
        assertThat(sampleBST.getPreOrder().toString(), is("[10, 3, 1, 2, 8, 20, 12, 29, 25, 29]"));
    }
}