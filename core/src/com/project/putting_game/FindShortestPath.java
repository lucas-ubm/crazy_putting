package com.project.putting_game;

// A Java program for Dijkstra's single source shortest path algorithm.
// The program is for adjacency matrix representation of the graph
import com.badlogic.gdx.Gdx;

import java.util.*;
import java.lang.*;

class FindShortestPath {

    static final int SCALEDOWN = 14;
    static final int V = (int) (Gdx.graphics.getWidth() / SCALEDOWN) * (int) (Gdx.graphics.getHeight() / SCALEDOWN);

    void dijkstra(int graph[][], int src, int target) {

        ArrayList<Integer> parent = new ArrayList<Integer>();

        int dist[] = new int[V];

        Boolean included[] = new Boolean[V];

        for (int i = 0; i < V; i++) {
            dist[i] = Integer.MAX_VALUE;
            included[i] = false;
        }

        dist[src] = 0;

            int u = minDistance(dist, included, target, parent);

            included[u] = true;

            printSolution(parent);
        }


    int minDistance(int dist[], Boolean included[], int target, ArrayList<Integer> parent) {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < V; v++) {
            if (included[v] == false && dist[v] <= min) {
                min = dist[v];
                min_index = v;
                System.out.println("index is " + min_index);
                parent.add(v);
                System.out.println("added");
                if (v == target) break;
            }
        }

        return min_index;
    }

    void printSolution(ArrayList<Integer> parent) {
        System.out.println("The Path");
        for (int i = 0; i < parent.size(); i++) {
            System.out.println(parent.get(i));
        }
    }

    }
