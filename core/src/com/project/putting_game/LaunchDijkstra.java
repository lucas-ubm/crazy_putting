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
        System.out.println("it is constructing");
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

        System.out.println("it is at heights");

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
        System.out.println("it is at the translation");

        nodesNumbers = new int[height][width];
        int current = 1;
        for(int i=0; i < height ; i++){
            for(int m=0; m < width ; m++){
                nodesNumbers[i][m] = current;
                current++;
            }
        }

        System.out.println("it is done with numbering");


            ArrayList<Integer> linkedTo = new ArrayList<Integer>();

        System.out.println("before");
        int[][] adjList = new int[height*width][height*width];
        System.out.println("it made the adjecency list");

        int resident = 0;
        int linkingTo = 0;

        for(int i=0; i < height ; i++){
            for(int m=0; m < width ; m++){
                resident = nodesNumbers[i][m];

                if((i-1) > 0 && (i-1) < height && (m-1)>0 && (m-1)<width && in[i-1][m-1]>0) linkedTo.add(nodesNumbers[i-1][m-1]);    //top-left
                if((i-1) > 0 && (i-1) < height && (m)>0 && (m)<width && in[i-1][m]>0) linkedTo.add(nodesNumbers[i-1][m]);            //top
                if((i-1) > 0 && (i-1) < height && (m+1)>0 && (m+1)<width && in[i-1][m+1]>0) linkedTo.add(nodesNumbers[i-1][m+1]);    //top-right
                if((i) > 0 && (i) < height && (m-1)>0 && (m-1)<width && in[i][m-1]>0) linkedTo.add(nodesNumbers[i][m-1]);            //left
                if((i) > 0 && (i) < height && (m+1)>0 && (m+1)<width && in[i][m+1]>0) linkedTo.add(nodesNumbers[i][m+1]);            //right
                if((i+1) > 0 && (i+1) < height && (m-1)>0 && (m-1)<width && in[i+1][m-1]>0) linkedTo.add(nodesNumbers[i+1][m-1]);    //bottom-left
                if((i+1) > 0 && (i+1) < height && (m)>0 && (m)<width && in[i+1][m]>0) linkedTo.add(nodesNumbers[i+1][m]);            //bottom
                if((i+1) > 0 && (i+1) < height && (m+1)>0 && (m+1)<width && in[i+1][m+1]>0) linkedTo.add(nodesNumbers[i+1][m+1]);    //bottom-right

                for(int j=0 ; j<linkedTo.size() ; j++){
                    adjList[resident][linkedTo.get(i)] = 1;
                }

            }
        }
        return adjList;
    }

    }