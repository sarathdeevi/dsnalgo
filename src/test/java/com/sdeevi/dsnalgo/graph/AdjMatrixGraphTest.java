package com.sdeevi.dsnalgo.graph;

import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
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

    @Test
    public void getShortestPathsDijsktra() {
        AdjMatrixGraph<Integer> graph = new AdjMatrixGraph<>(8);

        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);
        graph.addVertex(7);
        graph.addVertex(8);

        graph.addEdgeUndirected(1, 2, 10.0);
        graph.addEdgeUndirected(1, 3, 1.0);
        graph.addEdgeUndirected(2, 5, 14.0);
        graph.addEdgeUndirected(3, 2, 4.0);
        graph.addEdgeUndirected(3, 4, 5.0);
        graph.addEdgeUndirected(3, 5, 18.0);
        graph.addEdgeUndirected(4, 5, 1.0);
        graph.addEdgeUndirected(5, 6, 1.0);
        graph.addEdgeUndirected(4, 6, 6.0);
        graph.addEdgeUndirected(6, 7, 2.0);
        graph.addEdgeUndirected(4, 7, 1.0);

        ShortestPath<Integer> shortestPath = graph.getShortestPathsDijsktra(1);

        assertThat(shortestPath.getPath(1), is(emptyList()));
        assertThat(shortestPath.getPath(2), is(asList(1, 3, 2)));
        assertThat(shortestPath.getPath(3), is(asList(1, 3)));
        assertThat(shortestPath.getPath(4), is(asList(1, 3, 4)));
        assertThat(shortestPath.getPath(5), is(asList(1, 3, 4, 5)));
        assertThat(shortestPath.getPath(6), is(asList(1, 3, 4, 5, 6)));
        assertThat(shortestPath.getPath(7), is(asList(1, 3, 4, 7)));
        assertThat(shortestPath.getPath(8), is(emptyList()));

        assertThat(shortestPath.getDistance(1), is(0.0));
        assertThat(shortestPath.getDistance(2), is(5.0));
        assertThat(shortestPath.getDistance(3), is(1.0));
        assertThat(shortestPath.getDistance(4), is(6.0));
        assertThat(shortestPath.getDistance(5), is(7.0));
        assertThat(shortestPath.getDistance(6), is(8.0));
        assertThat(shortestPath.getDistance(7), is(7.0));

        shortestPath = graph.getShortestPathsDijsktra(5);

        assertThat(shortestPath.getPath(1), is(asList(5, 4, 3, 1)));
        assertThat(shortestPath.getPath(2), is(asList(5, 4, 3, 2)));
        assertThat(shortestPath.getPath(3), is(asList(5, 4, 3)));
        assertThat(shortestPath.getPath(4), is(asList(5, 4)));
        assertThat(shortestPath.getPath(5), is(emptyList()));
        assertThat(shortestPath.getPath(6), is(asList(5, 6)));
        assertThat(shortestPath.getPath(7), is(asList(5, 4, 7)));

        assertThat(shortestPath.getDistance(1), is(7.0));
        assertThat(shortestPath.getDistance(2), is(10.0));
        assertThat(shortestPath.getDistance(3), is(6.0));
        assertThat(shortestPath.getDistance(4), is(1.0));
        assertThat(shortestPath.getDistance(5), is(0.0));
        assertThat(shortestPath.getDistance(6), is(1.0));
        assertThat(shortestPath.getDistance(7), is(2.0));
    }
}