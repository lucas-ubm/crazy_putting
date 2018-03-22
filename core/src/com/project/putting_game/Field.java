package com.project.putting_game;

import com.badlogic.gdx.math.Vector3;

public class Field {
    public Properties[][] matrix;
    public Vector3 hole;
    public int holeRadius;

    public Field (int x, int y, Vector3 hole, int holeRadius, String course) {
        matrix = new Properties[y][x];

        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = new Properties();
            }
        }


        if(course.equals("sinx+siny")) {
            for(int i = 0; i < matrix.length; i++) {
                for(int j = 0; j < matrix[0].length; j++) {
                    matrix[i][j].height = Math.sin(j/(400/5.1))+Math.sin(i/(240/5.1));
                }
            }
        }

        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                matrix[i][j].friction = 0.08;
            }
        }

        this.hole = hole;
        this.holeRadius = holeRadius;

    }
}
