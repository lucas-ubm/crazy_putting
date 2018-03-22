package com.project.putting_game;

import com.badlogic.gdx.math.Vector3;

public class Field {
    public Properties[][] matrix;
    public Vector3 hole;
    public int holeRadius;

    public Field (int x, int y, Vector3 hole, int holeRadius) {
        matrix = new Properties[y][x];

        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = new Properties();
            }
        }



        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                matrix[i][j].height = Math.sin(i) + Math.pow(j,2);
            }
        }

        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                matrix[i][j].friction = Math.sin(i) + Math.cos(Math.sin(Math.pow(j,2)));
            }
        }

        this.hole = hole;
        this.holeRadius = holeRadius;

    }
}
