package com.sdeevi.dsnalgo.trees;

import org.junit.Before;
import org.junit.Test;

import static com.sdeevi.dsnalgo.trees.NAryTree.Node;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class NAryTreeTest {

    private NAryTree sampleTree;

    @Before
    public void setUp() throws Exception {
        /*
                                                      10
                                       9              8             7
                               7       6      20                     11 12
                             15      16 17     21
         */
        sampleTree = new NAryTree(10);
        sampleTree.root.children.add(new Node(9));
        sampleTree.root.children.add(new Node(8));
        sampleTree.root.children.add(new Node(7));
        sampleTree.root.children.get(0).children.add(new Node(7));
        sampleTree.root.children.get(0).children.add(new Node(6));
        sampleTree.root.children.get(0).children.add(new Node(5));
        sampleTree.root.children.get(2).children.add(new Node(11));
        sampleTree.root.children.get(2).children.add(new Node(12));
        sampleTree.root.children.get(0).children.get(0).children.add(new Node(15));
        sampleTree.root.children.get(0).children.get(1).children.add(new Node(16));
        sampleTree.root.children.get(0).children.get(1).children.add(new Node(17));
        sampleTree.root.children.get(0).children.get(2).children.add(new Node(18));
    }

    @Test
    public void getDepth_thenReturnsDepth() {
        assertThat(sampleTree.getDepth(), is(4));
    }

    @Test
    public void isMirror_givenTwoNodes_thenReturnsTrueIfIsMirror() {
        NAryTree otherTree = new NAryTree(10);
        otherTree.root.children.add(new Node(7));
        otherTree.root.children.add(new Node(8));
        otherTree.root.children.add(new Node(9));
        otherTree.root.children.get(2).children.add(new Node(5));
        otherTree.root.children.get(2).children.add(new Node(6));
        otherTree.root.children.get(2).children.add(new Node(7));
        otherTree.root.children.get(0).children.add(new Node(12));
        otherTree.root.children.get(0).children.add(new Node(11));
        otherTree.root.children.get(2).children.get(2).children.add(new Node(15));
        otherTree.root.children.get(2).children.get(1).children.add(new Node(17));
        otherTree.root.children.get(2).children.get(1).children.add(new Node(16));
        otherTree.root.children.get(2).children.get(0).children.add(new Node(18));

        assertThat(sampleTree.isMirror(otherTree), is(true));

        otherTree.root.children.set(0, null);
        assertThat(sampleTree.isMirror(otherTree), is(false));

        otherTree.root.children = null;
        assertThat(sampleTree.isMirror(otherTree), is(false));
    }
}