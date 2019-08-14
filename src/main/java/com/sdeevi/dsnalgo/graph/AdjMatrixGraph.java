package com.sdeevi.dsnalgo.graph;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.sdeevi.dsnalgo.trees.MinHeap;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class AdjMatrixGraph<V> implements IGraph<V, Double> {

    private BiMap<V, Integer> vertices;
    private Double[][] edges;
    private int count;

    public AdjMatrixGraph(int vertices) {
        this.vertices = HashBiMap.create();
        edges = new Double[vertices][vertices];
        count = 0;
    }

    @Override
    public void addVertex(V ele) {
        vertices.put(ele, count++);
    }

    @Override
    public void addEdge(V ele1, V ele2) {
        addEdge(ele1, ele2, 1.0);
    }

    @Override
    public void addEdge(V ele1, V ele2, Double weight) {
        edges[vertices.get(ele1)][vertices.get(ele2)] = weight;
    }

    @Override
    public void removeVertex(V ele) {
        int i = vertices.get(ele);

        for (int j = 0; j < edges.length; j++) {
            edges[i][j] = 0.0;
        }

        for (int j = 0; j < edges.length; j++) {
            edges[j][i] = 0.0;
        }

        vertices.remove(ele);
        count--;
    }

    @Override
    public void removeEdge(V ele1, V ele2) {
        int src = vertices.get(ele1);
        int dest = vertices.get(ele2);
        edges[src][dest] = 0.0;
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

    @Override
    public ShortestPath<V> getShortestPathsDijsktra(V src) {
        ShortestPath<V> shortestPath = new ShortestPath<>();
        Set<V> visited = new HashSet<>();

        MinHeap<AdjNode<V>> queue = new MinHeap<>();
        queue.add(new AdjNode<>(src, 0.0));
        shortestPath.setParent(src, null, 0.0);

        while (!queue.isEmpty()) {
            AdjNode<V> adjNode = queue.remove();

            V currEle = adjNode.vertex;
            Double weight = adjNode.weight;

            if (!visited.contains(currEle)) {
                visited.add(currEle);

                int vIndex = vertices.get(currEle);
                for (int i = 0; i < edges.length; i++) {
                    if (i != vIndex) {
                        Double dist = edges[vIndex][i] == null || edges[vIndex][i] == 0.0 ? null : weight + edges[vIndex][i];

                        V destVertex = vertices.inverse().get(i);
                        if (dist != null && (shortestPath.isNotDefined(destVertex) || dist < shortestPath.getDistance(destVertex))) {
                            shortestPath.setParent(destVertex, currEle, dist);

                            queue.add(new AdjNode<>(destVertex, dist));
                        }
                    }
                }
            }
        }
        return shortestPath;
    }

    private static final class AdjNode<V> implements Comparable<AdjNode<V>> {
        V vertex;
        Double weight;

        public AdjNode(V vertex, Double weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        @Override
        public int compareTo(AdjNode<V> o) {
            return weight.compareTo(o.weight);
        }
    }
}
