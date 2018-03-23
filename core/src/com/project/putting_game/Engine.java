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


    public static void calculate(Ball ball, Field fields)
    {
         double x =  ball.position.x;
         double y =  ball.position.y;

         vx = ball.velocity.x;
         vy = ball.velocity.y;

         xh = x + h * vx;
         yh = y + h * vy;

         vx_h = vx + h * forceX(ball);
         vy_h = vy + h * forceY();

         ball.velocity.x = (float)vx_h;
         System.out.println(ball.velocity.x + "XXXXXX");

         ball.velocity.y = (float)vy_h;
         System.out.println(ball.velocity.y + "YYYYYY");

         ball.position.x = (float)xh;
         ball.position.y = (float)yh;

        Currentfriction = fields.matrix[(int)ball.position.y][(int)ball.position.x].friction;
        currentHeight = fields.matrix[(int)ball.position.y][(int)ball.position.x].height;

         if(ball.position.x <= 60 || ball.position.y <= 60 || ball.position.x >= 800-92 || ball.position.y >= 480-92 || touchedWater()) {
             System.out.println("Previous " + ball.prevPosition);
             ball.position = ball.prevPosition;
             ball.velocity = new Vector3(0,0,0);
         }

    }

    public static double forceX(Ball ball)
    {
        double Fx = ((-g) * (0)) - (Currentfriction * g * vx) ;
        return Fx;
    }
    public static double forceY()
    {
        double Fy = ((-g) * (0)) - (Currentfriction * g * vy) ;
        return Fy;
    }

    public static boolean touchedWater() {
        boolean result = false;
        for()
    }
}
