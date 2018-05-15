package com.project.putting_game;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

public class Field {
    private Properties[][] matrix;
    private double max;

	public Properties[][] getMatrix() {
		return matrix;
	}

	public double getMax() {
		return max;
	}

	public double getMin() {
		return min;
	}

	private double min;

    public Field (String course) {
        matrix = new Properties[Gdx.graphics.getWidth()][Gdx.graphics.getHeight()];
        ArrayList<String> function = FunctionAnalyser.ShuntingYard(course);
		max= FunctionAnalyser.reversePolish(function,0,0);
		min=max;
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = new Properties();
                matrix[i][j].height = FunctionAnalyser.reversePolish(function,i,j);
                if(matrix[i][j].height>max)
                    max= matrix[i][j].height;
                if(matrix[i][j].height<min)
                    min=matrix[i][j].height;
                matrix[i][j].friction = 0.4;
            }
        }
    }
}
