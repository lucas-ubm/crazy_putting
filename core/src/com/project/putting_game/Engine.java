package com.project.putting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import jdk.nashorn.internal.objects.annotations.Function;

public class Engine {

    public static final double g = 9.81;
    public static double CurrentFriction;
    public static double currentHeight;
    public static final double h = 0.005;
    public static double xh;
    public static double yh;
    public static double vx_h;
    public static double vy_h;
    public static double vx;
    public static double vy;

    public static void calculate(Ball ball, Field fields) {
        //Getting the current location and velocity of the ball
        double x = ball.position.x;
        double y = ball.position.y;
        vx = ball.velocity.x;
        vy = ball.velocity.y;

        //Calculating the next location and velocity of the ball per timestep
        xh = x + h * vx;
        yh = y + h * vy;
        vx_h = vx + h * forceX(ball,fields);
        vy_h = vy + h * forceY(ball,fields);

        //Storing the newly obtained velocities and locations in the Ball object
        ball.position.x = (float) xh;
        ball.shape.x = (float) xh;
        ball.position.y = (float) yh;
        ball.shape.y = (float) yh;
        ball.velocity.x = (float) vx_h;
        ball.velocity.y = (float) vy_h;

        //Get the friction of the surface at current location ball
        CurrentFriction = fields.getMatrix()[(int) ball.position.y][(int) ball.position.x].friction;

        //Get the height of the field at current location ball
        currentHeight = fields.getMatrix()[(int) ball.position.y][(int) ball.position.x].height;

        //Checks whether the ball has touched the walls or touched the water. If it did, return to the previous position and set velocity to 0.
        int border = 0;
        int ballSide =(int) ball.shape.height/2;
        int side = border + ballSide;
        if(ball.position.x < 5){
            ball.position = ball.prevPosition;
            ball.velocity.scl(0);

        }
        if(ball.position.x > 800- 16 ) {
            ball.position = ball.prevPosition;
            ball.velocity.scl(0);

        }
        if(ball.position.y < 5) {
            ball.position = ball.prevPosition;
            ball.velocity.scl(0);

        }
        if(ball.position.y > 480 - 16) {
            ball.position = ball.prevPosition;
            ball.velocity.scl(0);

        }
        if (ball.position.x <= border || ball.position.y <= border || ball.position.x >= Gdx.graphics.getWidth() - side ||
                ball.position.y >= Gdx.graphics.getHeight() - side || water(ball, fields)) {
//             System.out.println("Previous " + ball.prevPosition);
            ball.position = ball.prevPosition;
            ball.velocity = new Vector3(0, 0, 0);
        }
    }

    /**Method to calculate the force on the ball at the x-axis. This method is used when calculating the new velocity*/
    public static double forceX(Ball ball,Field field) {
        double Fx = ((-g) * FunctionAnalyser.derivative(field,(int)ball.position.x,(int)ball.position.y,"x")) - (CurrentFriction * g * vx);
        return Fx;



    }

    /**Method to calculate the force on the ball at the y-axis. This method is used when calculating the new velocity*/
    public static double forceY(Ball ball,Field field) {
        double Fy = ((-g) * FunctionAnalyser.derivative(field,(int)ball.position.x,(int)ball.position.y,"y")) - (CurrentFriction * g * vy);
        return Fy;
    }

//Right now, input is the ball object containing vectors. The output is only one position.
//Now we get 2 positions, current pos + position we want to get too. Now we want to know what force we have to use to get to that point.
//So we simulate the movement of the ball to place X.

    public static boolean water(Ball ball, Field field) {
        Vector2 bottomLeft = new Vector2(ball.position.x - ball.shape.width / 2, ball.position.y - ball.shape.height / 2);
        for (int i = (int)bottomLeft.x; i < bottomLeft.x + ball.shape.width; i++) {
            for (int j = (int)bottomLeft.y; j < bottomLeft.y + ball.shape.height; j++) {
                if (ball.shape.contains(new Vector2(i, j))) {
                    //System.out.println(i+" "+j);
                    if(field.getMatrix()[field.getMatrix().length-1-j][i].height < 0){
                        //System.out.println(field.getMatrix()[j][i].height);
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
