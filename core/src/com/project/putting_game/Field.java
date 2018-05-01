package com.project.putting_game;

import com.badlogic.gdx.math.Vector3;

public class Field {
    public Properties[][] matrix;
    public Vector3 hole;
    public int holeRadius;

    public Field (int x, int y, Vector3 hole, int holeRadius, String course) {
        matrix = new Properties[x][y];

        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = new Properties();
                if(course.equals("flat")){
                    matrix[i][j].height = 1;
                }
                else if(course.equals("slope")||course.equals("parabola")){
                    matrix[i][j].height = i + j;
                }
                else if(course.equals("sinx+siny")){
                    matrix[i][j].height = Math.sin((double)(j)/(400/5.1))+Math.sin((double)(i)/(240/5.1));
                }
                matrix[i][j].friction = 0.4;
            }
        }

        this.hole = hole;
        this.holeRadius = holeRadius;
    }
}
