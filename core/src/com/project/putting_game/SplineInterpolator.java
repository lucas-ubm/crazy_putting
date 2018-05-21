package com.project.putting_game;

import com.badlogic.gdx.math.Vector3;

public class SplineInterpolator {

	public static double getHeight(double[][] ds, double x, double y){
		double[][] xs= {{1,x,Math.pow(x,2),Math.pow(x,3)}};
		double[][] ys={{1},{y},{Math.pow(y,2)},{Math.pow(y,3)}};
		return Multiplication(Multiplication(xs,ds),ys)[0][0];//has only 1 entry
	};
	public static double[][] findCoefs(Vector3[][] heights, int i, int j) {
		double[][] c1 = {{1, 0, 0, 0}, {0, 0, 1, 0}, {-3, 3, -2, -1}, {2, -2, 1, 1}};
		double[][] c2 = {{1, 0, -3, 2}, {0, 0, 3, -2}, {0, 1, -2, 1}, {0, 0, -1, 1}};
		double[][] ds = {{heights[i][j].z,heights[i][j+1].z,fy(heights,i,j),fy(heights,i,j+1)},{heights[i+1][j].z,heights[i+1][j+1].z,fy(heights,i+1,j),fy(heights,i+1,j+1)},{fx(heights,i,j),fx(heights,i,j+1),fxy(heights,i,j),fxy(heights,i,j+1)},{fx(heights,i+1,j),fx(heights,i+1,j+1),fxy(heights,i+1,j),fxy(heights,i+1,j+1)}};
		return Multiplication(Multiplication(c1,ds),c2);
	}
	public static double fx(Vector3[][] heights, int i, int j){
		if(i==0)
			return (heights[i+1][j].z-heights[i][j].z)/(2*(heights[i+1][j].x - heights[i][j].x));
		else if(i==heights.length-1)
			return (heights[i][j].z-heights[i-1][j].z)/(2*(heights[i][j].x - heights[i-1][j].x));
		else
			return (heights[i+1][j].z-heights[i-1][j].z)/(2*(heights[i+1][j].x - heights[i-1][j].x));
	}
	public static double fy(Vector3[][] heights, int i, int j){
		if(j==0)
			return (heights[i][j+1].z-heights[i][j].z)/(2*(heights[i][j+1].y - heights[i][j].y));
		else if(j==heights[i].length-1)
			return (heights[i][j].z-heights[i][j-1].z)/(2*(heights[i][j].y - heights[i][j-1].y));
		else
			return (heights[i][j+1].z-heights[i][j-1].z)/(2*(heights[i][j+1].y - heights[i][j-1].y));
	}
	public static double fxy(Vector3[][] heights, int i, int j){
		if(i==0 && j==0)//corners
			return (heights[i+1][j+1].z-heights[i+1][j].z-heights[i][j+1].z+heights[i][j].z)/(4*(heights[i][j+1].x - heights[i+1][j+1].x)*(heights[i][j+1].y-heights[i][j].y));
		else if(i==heights.length-1&& j==0)
			return (heights[i][j+1].z-heights[i][j].z-heights[i-1][j+1].z+heights[i-1][j].z)/(4*(heights[i-1][j+1].x - heights[i][j+1].x)*(heights[i-1][j+1].y-heights[i-1][j].y));
		else if(i==0 &&j==heights[i].length-1)
			return (heights[i+1][j].z-heights[i+1][j-1].z-heights[i][j].z+heights[i][j-1].z)/(4*(heights[i][j].x - heights[i+1][j].x)*(heights[i][j].y-heights[i][j-1].y));
		else if(i==heights.length-1&&j==heights[i].length-1)
			return (heights[i][j].z-heights[i][j-1].z-heights[i-1][j].z+heights[i-1][j-1].z)/(4*(heights[i-1][j].x - heights[i][j].x)*(heights[i-1][j].y-heights[i-1][j-1].y));
		else if(i==0)//borders
			return (heights[i+1][j+1].z-heights[i+1][j-1].z-heights[i][j+1].z+heights[i][j-1].z)/(4*(heights[i][j+1].x - heights[i+1][j+1].x)*(heights[i][j+1].y-heights[i][j-1].y));
		else if(i==heights.length-1)
			return (heights[i][j+1].z-heights[i][j-1].z-heights[i-1][j+1].z+heights[i-1][j-1].z)/(4*(heights[i-1][j+1].x - heights[i][j+1].x)*(heights[i-1][j+1].y-heights[i-1][j-1].y));
		else if(j==0)
			return (heights[i+1][j+1].z-heights[i+1][j].z-heights[i-1][j+1].z+heights[i-1][j].z)/(4*(heights[i-1][j+1].x - heights[i+1][j+1].x)*(heights[i-1][j+1].y-heights[i-1][j].y));
		else if(j==heights[i].length-1)
			return (heights[i+1][j].z-heights[i+1][j-1].z-heights[i-1][j].z+heights[i-1][j-1].z)/(4*(heights[i-1][j].x - heights[i+1][j].x)*(heights[i-1][j].y-heights[i-1][j-1].y));
		else//middle
			return (heights[i+1][j+1].z-heights[i+1][j-1].z-heights[i-1][j+1].z+heights[i-1][j-1].z)/(4*(heights[i-1][j+1].x - heights[i+1][j+1].x)*(heights[i-1][j+1].y-heights[i-1][j-1].y));
	}
	public static double[][] Multiplication(double[][] m1, double[][] m2) {
		double[][] result = new double[m1.length][m2[0].length];
		for (int i=0; i<m1.length; i++) {
			for (int j=0; j<m2[0].length; j++) {
				for (int k=0; k<m1[0].length; k++)
					result[i][j] = result[i][j] + m1[i][k]*m2[k][j];
			}
		}
		return result;
	}
}
