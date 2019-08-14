package com.sdeevi.dsnalgo.graph;

import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

final class ShortestPath<V> {
    private Map<V, MinRoute<V>> routes = new HashMap<>();

    void setParent(V ele, V parent, Double distance) {
        routes.put(ele, new MinRoute<>(parent, distance));
    }

    List<V> getPath(V ele) {
        LinkedList<V> path = Lists.newLinkedList();

        V curr = ele;
        while (curr != null) {
            MinRoute<V> route = routes.get(curr);
            if (route != null && route.parent != null) {
                path.addFirst(route.parent);
            }
            curr = route != null ? route.parent : null;
        }
        if (!path.isEmpty()) path.addLast(ele);
        return path;
    }

    boolean isNotDefined(V ele) {
        return !routes.containsKey(ele);
    }

    Double getDistance(V ele) {
        return routes.get(ele) == null ? 0.0 : routes.get(ele).minDistance;
    }

    private static final class MinRoute<V> {
        Double minDistance;
        private V parent;

        public MinRoute(V parent, Double minDistance) {
            this.parent = parent;
            this.minDistance = minDistance;
        }
    }
}
