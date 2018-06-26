package com.project.putting_game.DijkstraGraphStyle;

import java.util.*;

public class Node {

    private String name;

    private List<Node> shortestPath = new LinkedList<Node>();

    private Integer distance = Integer.MAX_VALUE;

    Map<Node, Integer> adjacentNodes = new HashMap<Node, Integer>();

    public void addDestination(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
    }

    public Node(String name) {
        this.name = name;
    }

    public void setDistance(int dist) {
        distance = dist;
    }

    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }
}