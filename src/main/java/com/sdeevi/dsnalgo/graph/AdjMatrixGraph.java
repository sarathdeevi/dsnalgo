package com.sdeevi.dsnalgo.graph;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class AdjMatrixGraph<V> implements IGraph<V, Integer> {

    private Map<V, Integer> vertices;
    private int[][] edges;
    private int count;

    public AdjMatrixGraph(int vertices) {
        this.vertices = new LinkedHashMap<>();
        edges = new int[vertices][vertices];
        count = 0;
    }

    @Override
    public void addVertex(V ele) {
        vertices.put(ele, count++);
    }

    @Override
    public void addEdge(V ele1, V ele2) {
        addEdge(ele1, ele2, 1);
    }

    @Override
    public void addEdge(V ele1, V ele2, Integer weight) {
        edges[vertices.get(ele1)][vertices.get(ele2)] = weight;
    }

    @Override
    public void removeVertex(V ele) {
        int i = vertices.get(ele);

        for (int j = 0; i < edges.length; j++) {
            edges[i][j] = 0;
        }

        for (int j = 0; i < edges.length; j++) {
            edges[j][i] = 0;
        }

        vertices.remove(ele);
    }

    @Override
    public void removeEdge(V ele1, V ele2) {
        int src = vertices.get(ele1);
        int dest = vertices.get(ele2);
        edges[src][dest] = 0;
    }

    @Override
    public Set<V> getDFS(V root) {
        Stack<V> s = new Stack<>();
        Set<V> visited = new LinkedHashSet<>();

        s.push(root);

        while (!s.isEmpty()) {
            V ele = s.peek();
            if (!visited.contains(ele)) {
                visited.add(ele);
            } else {
                s.pop();
                int i = vertices.get(ele);

                for (V other : vertices.keySet()) {
                    int j = vertices.get(other);
                    if (edges[i][j] != 0 && !visited.contains(other)) {
                        s.push(other);
                    }
                }
            }
        }
        return visited;
    }

    @Override
    public Set<V> getBFS(V root) {
        Queue<V> s = new LinkedList<>();
        Set<V> visited = new LinkedHashSet<>();

        s.add(root);

        while (!s.isEmpty()) {
            V ele = s.peek();
            if (!visited.contains(ele)) {
                visited.add(ele);
            } else {
                s.remove();
                int i = vertices.get(ele);

                for (V other : vertices.keySet()) {
                    int j = vertices.get(other);
                    if (edges[i][j] != 0 && !visited.contains(other)) {
                        s.add(other);
                    }
                }
            }
        }
        return visited;
    }

    @Override
    public boolean isAcyclic() {
        return false;
    }

    @Override
    public boolean isDirectedAcyclic() {
        return false;
    }
}
