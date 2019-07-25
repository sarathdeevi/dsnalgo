package com.sdeevi.dsnalgo.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class Graph {

    private List<Integer> edges[];

    public Graph(int vertices) {
        this.edges = new LinkedList[vertices];

        IntStream.of(0, vertices).forEach(i -> edges[i] = new LinkedList<>());
    }

    public void addEdge(int src, int dest) {
        this.edges[src].add(dest);
    }
}
