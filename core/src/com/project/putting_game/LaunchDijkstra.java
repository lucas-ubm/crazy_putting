package com.project.putting_game;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

public class LaunchDijkstra {

    Field course;
    Vector3 ballPos;
    Vector3 holePos;
    int source;
    int target;
    double[][] heights;
    int[][] adjacencyList;
    ArrayList<Hole> miniTargets = new ArrayList<Hole>();
    int[][] nodesNumbers;

    int height = (int)Gdx.graphics.getHeight();
    int width = (int)Gdx.graphics.getWidth();

    public LaunchDijkstra (Field c, Ball ball, Hole hole){
        course = c;
        ballPos = ball.getPosition();
        holePos = hole.getPosition();
        heights = translateToHeights(c.getMatrix());
        adjacencyList = translateToAdjacencyList(heights);
        setPositions();
    }

    public void run () {
        ShortestPath t = new ShortestPath();
        t.dijkstra(adjacencyList, source, target);
    }

    public void setPositions() {
        source = nodesNumbers[(int)ballPos.x][(int)ballPos.y];
        target = nodesNumbers[(int)holePos.x][(int)holePos.y];
    }

    public double[][] translateToHeights(Properties[][] matrix) {

        double[][] heights = new double[height][width];

        for(int i=0; i<height ; i++){
            for(int m=0; m<width ; m++){
                heights[i][m] = matrix[i][m].getHeight();   }    }

                return heights;
    }

    /*
    public int[][] translateToAdjacencyList(double[][] in) {

        int[][] adjList = new int[height][width];

        for(int i=0; i < Gdx.graphics.getHeight() ; i++){
            for(int m=0; m < Gdx.graphics.getWidth() ; m++){
                if(in[i][m] > 0) adjList[i][m] = 1;   
            }
        }

                return adjList;
    }
    */

    public int[][] translateToAdjacencyList(double[][] in) {

        nodesNumbers = new int[height][width];
        int current = 1;
        for(int i=0; i < height ; i++){
            for(int m=0; m < width ; m++){
                nodesNumbers[i][m] = current;
                current++;
            }
        }

        ArrayList<Integer> linkedTo = new ArrayList<Integer>();

        int[][] adjList = new int[height*width][height*width];
        for(int i=0; i < height ; i++){
            for(int m=0; m < width ; m++){
                int resident = nodesNumbers[i][m];

                if((i-1) > 0 && (i-1) < height && (m-1)>0 && (m-1)<width && in[i-1][m-1]>0) linkedTo.add(nodesNumbers[i-1][m-1]);    //top-left
                if((i-1) > 0 && (i-1) < height && (m)>0 && (m)<width && in[i-1][m]>0) linkedTo.add(nodesNumbers[i-1][m]);            //top
                if((i-1) > 0 && (i-1) < height && (m+1)>0 && (m+1)<width && in[i-1][m+1]>0) linkedTo.add(nodesNumbers[i-1][m+1]);    //top-right
                if((i) > 0 && (i) < height && (m-1)>0 && (m-1)<width && in[i][m-1]>0) linkedTo.add(nodesNumbers[i][m-1]);            //left
                if((i) > 0 && (i) < height && (m+1)>0 && (m+1)<width && in[i][m+1]>0) linkedTo.add(nodesNumbers[i][m+1]);            //right
                if((i+1) > 0 && (i+1) < height && (m-1)>0 && (m-1)<width && in[i+1][m-1]>0) linkedTo.add(nodesNumbers[i+1][m-1]);    //bottom-left
                if((i+1) > 0 && (i+1) < height && (m)>0 && (m)<width && in[i+1][m]>0) linkedTo.add(nodesNumbers[i+1][m]);            //bottom
                if((i+1) > 0 && (i+1) < height && (m+1)>0 && (m+1)<width && in[i+1][m+1]>0) linkedTo.add(nodesNumbers[i+1][m+1]);    //bottom-right
            }
        }

        int[][] list = new int[height*width][height*width];
        int linkingTo;
            for(int i=0 ; i<linkedTo.size() ; i++){
                linkingTo = linkedTo.get(i);
                adjList[current][linkingTo] = 1;
            }

        return list;
    }

    }