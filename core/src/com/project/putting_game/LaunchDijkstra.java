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
    ArrayList<Integer> miniTargets = new ArrayList<Integer>();

    public LaunchDijkstra (Field c, Ball ball, Hole hole){
        course = c;
        start = ball.getPosition();
        target = hole.getPosition();
        heights = translateToHeights(c.getMatrix());
    }

    public double[][] translateToHeights(Properties[][] matrix) {

        double[][] heights = new double[Gdx.graphics.getHeight()][Gdx.graphics.getWidth()];

        int height = (int)Gdx.graphics.getHeight();
        int width = (int)Gdx.graphics.getWidth();

        for(int i=0; i<height ; i++){
            for(int m=0; m<width ; m++){
                heights[i][m] = matrix[i][m].getHeight();   }    }
        return heights;
    }

    public int[][] translateToAdecencyList(double[][] in) {
        //int[][] adjList = new int[Gdx.graphics.getHeight()][Gdx.graphics.getWidth()];
        int[][] adjList = new int[10][10];

        for(int i=0; i<Gdx.graphics.getHeight() ; i++){
            for(int m=0; m<Gdx.graphics.getWidth() ; m++){
                if(in[i][m] > 0) adjList[i][m] = 1;   
            }
        }

                return adjList;
    }


}