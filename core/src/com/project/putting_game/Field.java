package com.project.putting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class Field {
	private Properties[][] matrix;
	private double max;
	private double min;
	private ArrayList<String> formula;

	public Properties[][] getMatrix() {
		return matrix;
	}

	public double getMax() {
		return max;
	}

	public double getMin() {
		return min;
	}


	public Field (String course) {
		matrix = new Properties[Gdx.graphics.getHeight()][Gdx.graphics.getWidth()];
		if(course.equals("spline")) {
			//initialise test case
			int gridW = 7;
			int gridH = 4;
			Vector3[][] heights = new Vector3[gridH][gridW];
			Gdx.graphics.setWindowedMode((gridW-1)*(int)(Gdx.graphics.getWidth()/(gridW-1)), (gridH-1)*(int)(Gdx.graphics.getHeight()/(gridH-1)));
			for(int m=0; m<gridW; m++)
				for(int n=0; n<gridH; n++){
					heights[n][m]= new Vector3((Gdx.graphics.getWidth()/(gridW-1)*m),Gdx.graphics.getHeight()/(gridH-1)*n,0);
					//System.out.println(n+" "+m+" "+heights[n][m].y+" "+heights[n][m].x);
				}
			//user input test case
	        /*
	        70 70 70 50 50 50 50
	        70 70 70 50 40 40 40
	        70 40 60 50 40 20 20
	        70 40 60 50 40 20 10
	         */
	        heights[0][0].z = 70;
	        heights[1][0].z = 70;
	        heights[2][0].z = 70;
	        heights[3][0].z = 70;
	        heights[0][1].z = 70;
	        heights[1][1].z = 70;
	        heights[2][1].z = 40;
	        heights[3][1].z = 40;
	        heights[0][2].z = 70;
	        heights[1][2].z = 70;
	        heights[2][2].z = 60;
	        heights[3][2].z = 60;
	        heights[0][3].z = 50;
	        heights[1][3].z = 50;
	        heights[2][3].z = 50;
	        heights[3][3].z = 50;
	        heights[0][4].z = 50;
	        heights[1][4].z = 40;
	        heights[2][4].z = 40;
	        heights[3][4].z = 40;
	        heights[0][5].z = 50;
	        heights[1][5].z = 40;
	        heights[2][5].z = 20;
	        heights[3][5].z = 20;
	        heights[0][6].z = 50;
	        heights[1][6].z = 40;
	        heights[2][6].z = 20;
	        heights[3][6].z = 10;
        	//compute field
        	double[][] as;
	        max= heights[0][0].z;
	        min=max;
	        for(int p=0; p<heights.length-1; p++)
		        for(int q=0; q<heights[p].length-1; q++) {
			        as = SplineInterpolator.findCoefs(heights, p, q);//compute coefficients
			        //SplineInterpolator.MatrixToString(as);
			        //System.out.println(p+" "+q+" i "+heights[p][q].y+" to "+heights[p + 1][q + 1].y+" j "+heights[p][q].x+" to "+heights[p+1][q+1].x);
			        for (int i = (int)heights[p][q].y; i < heights[p + 1][q + 1].y; i++)
				        for (int j = (int)heights[p][q].x; j < heights[p + 1][q + 1].x; j++) {
					        matrix[i][j] = new Properties();
					        matrix[i][j].height = SplineInterpolator.getHeight(as, j, i);//compute height
					        if(matrix[i][j].height>max) //update max and min
						        max= matrix[i][j].height;
					        if(matrix[i][j].height<min)
						        min=matrix[i][j].height;
					        matrix[i][j].friction = 0.4;
					        //if(p==0&&q==0&&i>0&&i<7&&j>0&&j<7){ System.out.println(matrix[i][j].height);}
				        }
		        }
		        //System.out.println("max: "+max+" "+min);
        }else{
	        ArrayList<String> function = FunctionAnalyser.ShuntingYard(course);
	        this.formula = function;
			max= FunctionAnalyser.reversePolish(function,0,0);//initialise
			min=max;//initialise
	        for(int i = 0; i < matrix.length; i++) {
	            for(int j = 0; j < matrix[0].length; j++) {
	                matrix[i][j] = new Properties();
	                matrix[i][j].height = FunctionAnalyser.reversePolish(function,j,i);
	                if(matrix[i][j].height>max)
	                    max= matrix[i][j].height;
	                if(matrix[i][j].height<min)
	                    min=matrix[i][j].height;
	                matrix[i][j].friction = 0.4;
	            }
	        }
       }
    }

    public ArrayList<String> getFormula() {
        return formula;
    }

}
