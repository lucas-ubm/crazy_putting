package com.project.putting_game;

import com.badlogic.gdx.math.Vector3;

public class Field {
    public Properties[][] matrix;
    public Vector3 hole;
    public int holeRadius;

    public Field (int x, int y, int holeRadius, String course) {
        matrix = new Properties[y][x];

        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = new Properties();
            }
        }


        if(course.equals("sinx+siny")) {
            for(int i = 0; i < matrix.length; i++) {
                for(int j = 0; j < matrix[0].length; j++) {
                    matrix[i][j].height = Math.sin((double)(j)/(400/5.1))+Math.sin((double)(i)/(240/5.1));
                }
            }
        }
        else if(course.equals("flat")) {
            for(int i = 0; i < matrix.length; i++) {
                for(int j = 0; j < matrix[0].length; j++) {
                    matrix[i][j].height = 1;
                }
            }
        }
        else if(course.equals("slope")) {
            for(int i = 0; i < matrix.length; i++) {
                for(int j = 0; j < matrix[0].length; j++) {
                    matrix[i][j].height = i+j;
                }
            }
        }
        else if(course.equals("parabola")) {
            for(int i = 0; i < matrix.length; i++) {
                for(int j = 0; j < matrix[0].length; j++) {
                    matrix[i][j].height = i+j;
                }
            }
        }

        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                matrix[i][j].friction = 0.4;
            }
        }

        this.hole = new Vector3(0,0,0);
        this.holeRadius = holeRadius;

    }
}
