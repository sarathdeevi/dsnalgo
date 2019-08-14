package com.sdeevi.dsnalgo.graph;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class MGraph<V> implements IGraph<V, Double> {

    private Map<V, List<AdjNode<V>>> adj;

    public MGraph() {
        this.adj = new LinkedHashMap<>();
    }

    @Override
    public void addVertex(V ele) {
        adj.putIfAbsent(ele, new ArrayList<>());
    }

    @Override
    public void addEdge(V ele1, V ele2) {
        adj.get(ele1).add(new AdjNode<>(ele2));
    }

    @Override
    public void removeVertex(V ele) {
        adj.values().forEach(e -> e.remove(new AdjNode<>(ele)));
        adj.remove(ele);
    }

    @Override
    public void addEdge(V ele1, V ele2, Double weight) {
        adj.get(ele1).add(new AdjNode<>(ele2, weight));
    }

    @Override
    public void removeEdge(V ele1, V ele2) {
        adj.get(ele1).remove(new AdjNode<>(ele2));
    }

    @Override
    public Set<V> getDFS(V ele) {
        if (!adj.containsKey(ele)) return new HashSet<>();

        Set<V> visited = new LinkedHashSet<>();
        Stack<V> stack = new Stack<>();
        stack.push(ele);

        while (!stack.isEmpty()) {
            V currEle = stack.pop();

            if (!visited.contains(currEle)) {
                visited.add(currEle);
                adj.get(currEle).forEach(i -> stack.push(i.vertex));
            }
        }
        return visited;
    }

    @Override
    public Set<V> getBFS(V ele) {
        if (!adj.containsKey(ele)) return new HashSet<>();

        Set<V> visited = new LinkedHashSet<>();
        Queue<V> q = new LinkedList<>();
        q.add(ele);

        while (!q.isEmpty()) {
            V currEle = q.remove();

            if (!visited.contains(currEle)) {
                visited.add(currEle);
                adj.get(currEle).forEach(x -> q.add(x.vertex));
            }
        }
        return visited;
    }

    private V getRoot() {
        return adj.keySet().iterator().next();
    }

    @Override
    public boolean isAcyclic() {
        return !isCyclic();
    }

    private boolean isCyclic() {
        Set<V> visited = new HashSet<>();
        Stack<V> s = new Stack<>();

        V root = getRoot();
        s.push(root);

        while (!s.isEmpty()) {
            V i = s.peek();
            if (visited.contains(i)) {
                return true;
            }

            s.pop();
            visited.add(i);

            adj.get(i).forEach(x -> s.add(x.vertex));
        }
        return false;
    }

    @Override
    public boolean isDirectedAcyclic() {
        return isDirectedCyclicGraph();
    }

    private boolean isDirectedCyclicGraph() {
        Set<V> vertices = adj.keySet();

        Map<V, Integer> indices = new HashMap<>();
        int i = 0;
        Iterator<V> itr = vertices.iterator();
        while (itr.hasNext()) {
            indices.put(itr.next(), i++);
        }

        boolean[] visited = new boolean[vertices.size()];

        itr = vertices.iterator();
        while (itr.hasNext()) {
            V ele = itr.next();
            if (visited[indices.get(ele)]) return false;

            visited[indices.get(ele)] = true;

            for (AdjNode<V> j : adj.get(ele)) {
                if (visited[indices.get(j.vertex)]) return false;
            }
        }
        return true;
    }

    public ShortestPathResult<V> getShortestPathsDijsktra(V src) {
        ShortestPathResult<V> shortestPathResult = new ShortestPathResult<>();
        Set<V> visited = Sets.newHashSet();

        V currEle;
        PriorityQueue<AdjNode<V>> q = new PriorityQueue<>(Comparator.comparing(x -> x.weight));

        q.add(new AdjNode<>(src, 0.0));
        shortestPathResult.setParent(src, null);

        while (q.size() > 0) {
            AdjNode<V> c = q.remove();
            currEle = c.vertex;

            if (!visited.contains(currEle)) {
                visited.add(currEle);

                for (AdjNode<V> adjNode : adj.get(currEle)) {
                    V dest = adjNode.vertex;
                    Double weight = adjNode.weight;

                    Double dist = weight + shortestPathResult.getDistance(currEle);
                    if (!shortestPathResult.isDistanceDefined(dest) || shortestPathResult.getDistance(dest) > dist) {
                        shortestPathResult.setDistance(dest, dist);

                        shortestPathResult.setParent(dest, currEle);
                        q.add(new AdjNode<>(dest, dist));
                    }
                }
            }
        }
        return shortestPathResult;
    }

    static class ShortestPathResult<V> {
        private Map<V, V> parents = new HashMap<>();
        private Map<V, Double> distances = new HashMap<>();

        private void setParent(V ele, V parent) {
            parents.put(ele, parent);
        }

        private void setDistance(V ele, Double distance) {
            distances.put(ele, distance);
        }

        List<V> getPath(V ele) {
            LinkedList<V> path = Lists.newLinkedList();

            V curr = ele;
            while (curr != null) {
                V parent = parents.get(curr);
                if (parent != null) {
                    path.addFirst(parent);
                }
                curr = parent;
            }
            if (path.size() > 0) path.addLast(ele);
            return path;
        }

        private boolean isDistanceDefined(V ele) {
            return parents.containsKey(ele);
        }

        Double getDistance(V ele) {
            return distances.getOrDefault(ele, 0.0);
        }
    }

    private static class AdjNode<V> {
        private V vertex;
        private Double weight;

        private AdjNode(V v) {
            this(v, null);
        }

        private AdjNode(V v, Double weight) {
            this.vertex = v;
            this.weight = weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AdjNode<?> adjNode = (AdjNode<?>) o;
            return Objects.equals(vertex, adjNode.vertex);
        }

        @Override
        public int hashCode() {
            return Objects.hash(vertex);
        }
    }
}
