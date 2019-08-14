package com.sdeevi.dsnalgo.trees;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
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

    @Test
    public void kthSmallestElement_thenReturnsKthSmallestElement() {
        // [1, 2, 3, 8, 10, 12, 20, 25, 29, 29]

        assertThat(sampleBST.kthSmallestElement(2), is(2));
        assertThat(sampleBST.kthSmallestElement(10), is(29));
        assertThat(sampleBST.kthSmallestElement(12), is(Integer.MAX_VALUE));

        assertThat(sampleBST.kthSmallestElementIterative(2), is(2));
        assertThat(sampleBST.kthSmallestElementIterative(10), is(29));
        assertThat(sampleBST.kthSmallestElementIterative(11), is(Integer.MAX_VALUE));
    }

    @Test
    public void sumOfkSmallestElements_thenReturnsKthSmallestElement() {
        // [1, 2, 3, 8, 10, 12, 20, 25, 29, 29]

        assertThat(sampleBST.sumOfKSmallestElements(2), is(3));
        assertThat(sampleBST.sumOfKSmallestElements(10), is(139));
        assertThat(sampleBST.sumOfKSmallestElements(12), is(Integer.MAX_VALUE));
    }

    @Test
    public void kthLargestElement_thenReturnsKthSmallestElement() {
        // [1, 2, 3, 8, 10, 12, 20, 25, 29, 29]

        assertThat(sampleBST.kthLargestElement(2), is(29));
        assertThat(sampleBST.kthLargestElement(4), is(20));
        assertThat(sampleBST.kthLargestElement(5), is(12));
        assertThat(sampleBST.kthLargestElement(10), is(1));
        assertThat(sampleBST.kthLargestElement(12), is(Integer.MAX_VALUE));
    }

    @Test
    public void lowestCommonAncestor_thenReturnsLowestCommonAncestor() {
        /*
                          10
                   3             20
               1      8       12     29
                 2                 25  29
         */
        assertThat(sampleBST.lowestCommonAncestor(8, 25), is(10));
        assertThat(sampleBST.lowestCommonAncestor(12, 29), is(20));
        assertThat(sampleBST.lowestCommonAncestor(12, 43), is(2147483647));
        assertThat(sampleBST.lowestCommonAncestor(7, 8), is(2147483647));

        assertThat(sampleBST.lowestCommonAncestorRecursive(8, 25), is(10));
        assertThat(sampleBST.lowestCommonAncestorRecursive(12, 29), is(20));
        assertThat(sampleBST.lowestCommonAncestor(12, 43), is(2147483647));
        assertThat(sampleBST.lowestCommonAncestor(7, 8), is(2147483647));
    }

    @Test
    public void searchRecursive_thenReturnsNodeIfFound() {
        assertThat(sampleBST.searchRecursive(3).data, is(3));
        assertThat(sampleBST.searchRecursive(21), is(nullValue()));
        assertThat(sampleBST.searchRecursive(25).data, is(25));
    }

    @Test
    public void correctBSTWithTwoSwappedNodes_thenCorrectsBST() {
        sampleBST.root.left.right.data = 20;
        sampleBST.root.right.data = 8;

        // Note 20 and 8 are in the wrong position
        assertThat(sampleBST.getInOrder().toString(), is("[1, 2, 3, 20, 10, 12, 8, 25, 29, 29]"));

//        sampleBST.correctBSTWithTwoSwappedNodes();
        assertThat(sampleBST.getInOrder().toString(), is("[1, 2, 3, 8, 10, 12, 20, 25, 29, 29]"));
    }
}