package com.project.putting_game;
import com.badlogic.gdx.math.Vector3;

public class Engine
{

    public static final double g = 9.81;
    public static double CurrentFriction;
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
        //Getting the current location and velocity of the ball
         double x =  ball.position.x;
         double y =  ball.position.y;
         vx = ball.velocity.x;
         vy = ball.velocity.y;

        //Calculating the next location and velocity of the ball per timestep
         xh = x + h * vx;
         yh = y + h * vy;
         vx_h = vx + h * forceX(ball);
         vy_h = vy + h * forceY();

        //Storing the newly obtained velocities and locations in the Ball object
        ball.position.x = (float)xh;
        ball.position.y = (float)yh;
        ball.velocity.x = (float)vx_h;
        ball.velocity.y = (float)vy_h;

        //Get the friction of the surface at current location ball
        CurrentFriction = fields.matrix[(int)ball.position.y][(int)ball.position.x].friction;

        //Get the height of the field at current location ball
        currentHeight = fields.matrix[(int)ball.position.y][(int)ball.position.x].height;


        //Checks whether the ball has touched the walls or touched the water. If it did, return to the previous position and set velocity to 0.
         if(ball.position.x <= 60 || ball.position.y <= 60 || ball.position.x >= 800-92 || ball.position.y >= 480-92 || currentHeight < 0)
         {
//             System.out.println("Previous " + ball.prevPosition);
             ball.position = ball.prevPosition;
             ball.velocity = new Vector3(0,0,0);
         }

    }


    /*Method to calculate the force on the ball at the x-axis. This method is used when calculating the new velocity*/
    public static double forceX(Ball ball)
    {
        double Fx =  -(CurrentFriction * g * vx) ;
        return Fx;
    }

    //*Method to calculate the force on the ball at the y-axis. This method is used when calculating the new velocity*//
    public static double forceY()
    {
        double Fy =  -(CurrentFriction * g * vy) ;
        return Fy;
    }

}

//Right now, input is the ball object containing vectors. The output is only one position.
//Now we get 2 positions, current pos + position we want to get too. Now we want to know what force we have to use to get to that point.
//So we simulate the movement of the ball to place X.