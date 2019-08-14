package com.sdeevi.dsnalgo.graph;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AdjMatrixGraphTest {

    private AdjMatrixGraph<Integer> graph;

    @Before
    public void setUp() {
        graph = new AdjMatrixGraph<>(40);

        graph.addVertex(10);
        graph.addVertex(20);
        graph.addVertex(30);
        graph.addVertex(40);

        graph.addEdge(10, 20);
        graph.addEdge(10, 30);
        graph.addEdge(10, 40);
        graph.addEdge(20, 10);
        graph.addEdge(20, 40);
        graph.addEdge(30, 20);
        graph.addEdge(40, 10);
        graph.addEdge(40, 30);

        /*
                    10--> 20, 30, 40
                    20--> 10, 40
                    30--> 20
                    40--> 10, 30
         */
    }

    @Test
    public void getDFS_thenReturnsDfs() {
        assertThat(graph.getDFS(20).toString(), is("[20, 40, 30, 10]"));
        assertThat(graph.getDFS(30).toString(), is("[30, 20, 40, 10]"));
        assertThat(graph.getDFS(40).toString(), is("[40, 30, 20, 10]"));
        assertThat(graph.getDFS(10).toString(), is("[10, 40, 30, 20]"));
    }

    @Test
    public void getBFS_thenReturnsBfs() {
        assertThat(graph.getBFS(20).toString(), is("[20, 10, 40, 30]"));
        assertThat(graph.getBFS(30).toString(), is("[30, 20, 10, 40]"));
        assertThat(graph.getBFS(40).toString(), is("[40, 10, 30, 20]"));
        assertThat(graph.getBFS(10).toString(), is("[10, 20, 30, 40]"));
    }

    @Test
    public void isAcyclic_thenReturnsTrueIfAcyclic() {
        assertThat(graph.isAcyclic(), is(false));

        AdjMatrixGraph<Integer> graph = new AdjMatrixGraph<>(100);
        graph.addVertex(10);
        graph.addVertex(20);
        graph.addVertex(30);

        graph.addEdge(10, 20);
        graph.addEdge(10, 30);

        assertThat(graph.isAcyclic(), is(true));

        graph.addVertex(40);
        graph.addEdge(20, 40);
        assertThat(graph.isAcyclic(), is(true));

        graph.addEdge(30, 40);
        assertThat(graph.isAcyclic(), is(false));

        graph.removeVertex(10);
        assertThat(graph.isAcyclic(), is(true));
    }

    @Test
    public void isDirectedAcyclicGraph_thenReturnsTrueIfAcyclic() {
        assertThat(graph.isDirectedAcyclic(), is(false));

        AdjMatrixGraph<Integer> graph = new AdjMatrixGraph<>(100);
        graph.addVertex(10);
        graph.addVertex(20);
        graph.addVertex(30);

        graph.addEdge(10, 20);
        graph.addEdge(10, 30);

        assertThat(graph.isDirectedAcyclic(), is(true));

        graph.addVertex(40);
        graph.addEdge(20, 40);
        assertThat(graph.isDirectedAcyclic(), is(true));

        graph.addEdge(30, 40);
        assertThat(graph.isDirectedAcyclic(), is(true));

        graph.addEdge(10, 10);
        assertThat(graph.isDirectedAcyclic(), is(false));

        graph.removeEdge(10, 10);
        assertThat(graph.isDirectedAcyclic(), is(true));

        graph.addEdge(30, 10);
        assertThat(graph.isDirectedAcyclic(), is(false));
    }
}