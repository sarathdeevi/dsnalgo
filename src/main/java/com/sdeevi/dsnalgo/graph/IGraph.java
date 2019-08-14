package com.sdeevi.dsnalgo.graph;

import java.util.Set;

public interface IGraph<T, Weight> {

    void addVertex(T ele);

    void addEdge(T ele1, T ele2);

    default void addEdgeUndirected(T ele1, T ele2) {
        addEdge(ele1, ele2);
        addEdge(ele2, ele1);
    }

    void addEdge(T ele1, T ele2, Weight weight);

    default void addEdgeUndirected(T ele1, T ele2, Weight weight) {
        addEdge(ele1, ele2, weight);
        addEdge(ele2, ele1, weight);
    }

    void removeVertex(T ele);

    void removeEdge(T ele1, T ele2);

    Set<T> getDFS(T root);

    Set<T> getBFS(T root);

    boolean isAcyclic();

    boolean isDirectedAcyclic();

    ShortestPath<T> getShortestPathsDijsktra(T src);
}
