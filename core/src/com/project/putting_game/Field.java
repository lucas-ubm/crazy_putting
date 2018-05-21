package com.project.putting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
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
        if(course.equals("spline")) {
        	//initialise test case
        	int gridW = 8;
        	int gridH = 4;
        	Vector3[][] heights = new Vector3[gridH][gridW];
        	for(int m=0; m<gridW; m++)
        		for(int n=0; n<gridH; n++)
        	        heights[n][m]= new Vector3((Gdx.graphics.getWidth()/(gridW-1)*m),Gdx.graphics.getHeight()/(gridH-1)*n,0);
        	//user input test case
	        /*
	        10 7 7 7 5 5 5 5
	        10 7 7 7 5 4 4 4
	        10 7 4 6 5 4 2 2
	        10 7 4 6 5 4 2 1
	         */
	        heights[0][0].z = 10;
	        heights[1][0].z = 10;
	        heights[2][0].z = 10;
	        heights[3][0].z = 10;
	        heights[0][1].z = 7;
	        heights[1][1].z = 7;
	        heights[2][1].z = 7;
	        heights[3][1].z = 7;
	        heights[0][2].z = 7;
	        heights[1][2].z = 7;
	        heights[2][2].z = 4;
	        heights[3][2].z = 4;
	        heights[0][3].z = 7;
	        heights[1][3].z = 7;
	        heights[2][3].z = 6;
	        heights[3][3].z = 6;
	        heights[0][4].z = 5;
	        heights[1][4].z = 5;
	        heights[2][4].z = 5;
	        heights[3][4].z = 5;
	        heights[0][5].z = 5;
	        heights[1][5].z = 4;
	        heights[2][5].z = 4;
	        heights[3][5].z = 4;
	        heights[0][6].z = 5;
	        heights[1][6].z = 4;
	        heights[2][6].z = 2;
	        heights[3][6].z = 2;
	        heights[0][7].z = 5;
	        heights[1][7].z = 4;
	        heights[2][7].z = 2;
	        heights[3][7].z = 1;
        	//compute field
        	double[][] ds;
	        max= heights[0][0].z;
	        min=max;
	        for(int p=0; p<heights.length-2; p++)
		        for(int q=heights[p].length-2; q>=0; q--) {
			        ds = SplineInterpolator.findCoefs(heights, p, q);
			        for (int i = (int)heights[p][q].x; i < heights[p + 1][q + 1].x; i++)
				        for (int j = (int)heights[p][q].y; j < heights[p + 1][q + 1].y; j++) {
					        matrix[i][j] = new Properties();
					        matrix[i][j].height = SplineInterpolator.getHeight(ds, i, j);
					        if(matrix[i][j].height>max)
						        max= matrix[i][j].height;
					        if(matrix[i][j].height<min)
						        min=matrix[i][j].height;
					        matrix[i][j].friction = 0.4;
				        }
		        }
        }else{
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
