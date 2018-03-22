package com.project.putting_game;

public class Engine
{

    public static final double g = 9.81;
    public static double Currentfriction;
    public static final double h = 0.1;
    public static double xh;
    public static double yh;
    public static double vx_h;
    public static double vy_h;
    public static double vx;
    public static double vy;


    public static void Calculate(Ball ball, Field fields)
    {
         double x =  ball.position.x;
         double y =  ball.position.y;

         double vx = ball.velocity.x;
         double vy = ball.velocity.y;

         xh = x + h * vx;
         yh = y + h * vy;

         vx_h = vx + h * ForceX();
         vy_h = vy + h * ForceY();

         ball.position.x = (float)xh;
         ball.position.y = (float)yh;

         Currentfriction = fields.matrix[(int)ball.position.x][(int)ball.position.y].friction;
    }

    public static double ForceX()
    {
        double Fx = ((-g) * (xh)) - (Currentfriction * g * vx) / Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2));
        return Fx;
    }
    public static double ForceY()
    {
        double Fy = ((-g) * (yh)) - (Currentfriction * g * vy) / Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2));;
        return Fy;
    }
}
