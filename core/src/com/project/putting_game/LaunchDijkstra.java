package com.project.putting_game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

public class LaunchDijkstra {

    Field course;
    Vector3 start;
    Vector3 target;
    double[][] heights;
    int[][] adjecencyList;
    ArrayList miniTargets = new ArrayList();

    public LaunchDijkstra (Field c, Ball ball, Hole hole){
        course = c;
        start = ball.getPosition();
        target = hole.getPosition();
        heights = translateToHeights(c.getMatrix());
    }

    public double[][] translateToHeights(Properties[][] matrix) {

        double[][] heights = new double[Gdx.graphics.getHeight()][Gdx.graphics.getWidth()];

        for(int i; i<Gdx.graphics.getHeight() ; i++){
            for(int m ; m<Gdx.graphics.getWidth() ; m++){
                heights[i][m] = matrix[i][m].getHeight();   }    }
        return heights;
    }

    public int[][] translateToAdecencyList(double[][]) {
        //int[][] adjList = new int[Gdx.graphics.getHeight()][Gdx.graphics.getWidth()];
        int[][] adjList = new int[10][10];

        for(int i; i<Gdx.graphics.getHeight() ; i++){
            for(int m ; m<Gdx.graphics.getWidth() ; m++){
                adjList[i][m] = if(matrix[i][m].getHeight() > 0){ 1};   
            }
        }

                return adjList;
    }


}