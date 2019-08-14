package com.sdeevi.dsnalgo.graph;

import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class MGraphTest {

    private MGraph<Integer> graph;

    @Before
    public void setUp() {
        graph = new MGraph<>();

        graph.addVertex(11);
        graph.addVertex(22);
        graph.addVertex(35);
        graph.addVertex(15);

        graph.addEdge(11, 22, 3.0);
        graph.addEdge(11, 35, 5.6);
        graph.addEdge(11, 15, 2.1);
        graph.addEdge(22, 11);
        graph.addEdge(22, 15);
        graph.addEdge(35, 22);
        graph.addEdge(15, 11);
        graph.addEdge(15, 35);

        /*
                    11--> 22, 35, 15
                    22--> 11, 15
                    35--> 22
                    15--> 11, 35
         */
    }

    @Test
    public void getDFS_thenReturnsDfs() {
        assertThat(graph.getDFS(22).toString(), is("[22, 15, 35, 11]"));
        assertThat(graph.getDFS(35).toString(), is("[35, 22, 15, 11]"));
        assertThat(graph.getDFS(15).toString(), is("[15, 35, 22, 11]"));
        assertThat(graph.getDFS(11).toString(), is("[11, 15, 35, 22]"));

        graph.removeVertex(11);
        assertThat(graph.getDFS(22).toString(), is("[22, 15, 35]"));
    }

    @Test
    public void getBFS_thenReturnsBfs() {
        assertThat(graph.getBFS(22).toString(), is("[22, 11, 15, 35]"));
        assertThat(graph.getBFS(35).toString(), is("[35, 22, 11, 15]"));
        assertThat(graph.getBFS(15).toString(), is("[15, 11, 35, 22]"));
        assertThat(graph.getBFS(11).toString(), is("[11, 22, 35, 15]"));

        graph.removeVertex(15);
        assertThat(graph.getBFS(35).toString(), is("[35, 22, 11]"));
    }

    @Test
    public void isAcyclic_thenReturnsTrueIfAcyclic() {
        assertThat(graph.isAcyclic(), is(false));

        MGraph<Integer> graph = new MGraph<>();
        graph.addVertex(11);
        graph.addVertex(22);
        graph.addVertex(35);

        graph.addEdge(11, 22);
        graph.addEdge(11, 35);

        assertThat(graph.isAcyclic(), is(true));

        graph.addVertex(15);
        graph.addEdge(22, 15);
        assertThat(graph.isAcyclic(), is(true));

        graph.addEdge(35, 15);
        assertThat(graph.isAcyclic(), is(false));

        graph.removeVertex(11);
        assertThat(graph.isAcyclic(), is(true));
    }

    @Test
    public void isDirectedAcyclicGraph_thenReturnsTrueIfAcyclic() {
        assertThat(graph.isDirectedAcyclic(), is(false));

        MGraph<Integer> graph = new MGraph<>();
        graph.addVertex(11);
        graph.addVertex(22);
        graph.addVertex(35);

        graph.addEdge(11, 22);
        graph.addEdge(11, 35);

        assertThat(graph.isDirectedAcyclic(), is(true));

        graph.addVertex(15);
        graph.addEdge(22, 15);
        assertThat(graph.isDirectedAcyclic(), is(true));

        graph.addEdge(35, 15);
        assertThat(graph.isDirectedAcyclic(), is(true));

        graph.addEdge(11, 11);
        assertThat(graph.isDirectedAcyclic(), is(false));

        graph.removeEdge(11, 11);
        assertThat(graph.isDirectedAcyclic(), is(true));

        graph.addEdge(35, 11);
        assertThat(graph.isDirectedAcyclic(), is(false));
    }

    @Test
    public void getShortestPathsDijsktra() {
        MGraph<Integer> graph = new MGraph<>();

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