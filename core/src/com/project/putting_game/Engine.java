package com.project.putting_game;

import com.badlogic.gdx.math.Vector3;

public class Engine
{

    public static final double g = 9.81;
    public static double Currentfriction;
    public static double currentHeight;
    public static final double h = 0.01;
    public static double xh;
    public static double yh;
    public static double vx_h;
    public static double vy_h;
    public static double vx;
    public static double vy;


    public static void calculate(Ball ball, Field fields, String course)
    {
         double x =  ball.position.x;
         double y =  ball.position.y;

         vx = ball.velocity.x;
         vy = ball.velocity.y;

         xh = x + h * vx;
         yh = y + h * vy;

         vx_h = vx + h * forceX(course);
         vy_h = vy + h * forceY(course);

         ball.velocity.x = (float)vx_h;
         ball.velocity.y = (float)vy_h;

         ball.position.x = (float)xh;
         ball.position.y = (float)yh;

        Currentfriction = fields.matrix[(int)ball.position.y][(int)ball.position.x].friction;
        currentHeight = fields.matrix[(int)ball.position.y][(int)ball.position.x].height;

        if(ball.position.x <= 60 || ball.position.y <= 60 || ball.position.x >= 800-92 || ball.position.y >=480-92|| currentHeight < 0) {
            System.out.println("Previous " + ball.prevPosition);
            ball.position = ball.prevPosition;
            ball.velocity = new Vector3(0,0,0);
        }

    }

    public static double forceX(String course)
    {
        double Fx =0;
        if(course.equals("flat")) {
            Fx = ((-g) * (0)) - (Currentfriction * g * vx) ;
        }
        else if(course.equals("sinx+siny")) {
            Fx = ((-g) * (Math.cos(xh))) - (Currentfriction * g * vx);
        }
        else if(course.equals("slope")) {
            Fx = ((-g) * (xh)) - (Currentfriction * g * vx);
        }
        else if(course.equals("parabola")){
            Fx = ((-g) * (0.1+0.06*xh)) - (Currentfriction * g * vx);
        }

        return Fx;
    }
    public static double forceY(String course)
    {
        double Fy = 0;
        if(course.equals("flat")) {
            Fy = ((-g) * (0)) - (Currentfriction * g * vy);
        }
        else if(course.equals("sinx+siny")) {
            Fy = ((-g) * (Math.cos(yh))) - (Currentfriction * g * vy);
        }
        else if(course.equals("slope")) {
            Fy = ((-g) * (yh)) - (Currentfriction * g * vy);
        }
        else if(course.equals("parabola")){
            Fy = ((-g) * (0.2)) - (Currentfriction * g * vx);
        }

        return Fy;
    }


}
