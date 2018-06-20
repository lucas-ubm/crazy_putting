package com.project.putting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class Engine {

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


    public static void calculate(Ball ball, Field fields, ArrayList<String> formula) {
        //Getting the current location and velocity of the ball
        double x = ball.position.x;
        double y = ball.position.y;
        vx = ball.velocity.x;
        vy = ball.velocity.y;

        //Calculating the next location and velocity of the ball per timestep
        xh = x + h * vx;
        yh = y + h * vy;
        vx_h = vx + h * forceX(ball, formula);
        vy_h = vy + h * forceY(ball, formula);

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
        int border = 60;
        int ballSide =(int) ball.shape.height;
        int side = border + ballSide;
        if (ball.position.x <= border || ball.position.y <= border || ball.position.x >= Gdx.graphics.getWidth() - side ||
                ball.position.y >= Gdx.graphics.getHeight() - side) {
//             System.out.println("Previous " + ball.prevPosition);
            ball.position = ball.prevPosition;
            ball.velocity.scl(0);
        }

        if(ball.position.x <= ball.shape.width/2){
            ball.position = ball.prevPosition;
            ball.velocity.scl(0);
        }
        if(ball.position.x >= fieldShape.width - ball.shape.width/2) {
            ball.position = ball.prevPosition;
            ball.velocity.scl(0);
        }
        if(ball.position.y <= ball.shape.height/2) {
            ball.position = ball.prevPosition;
            ball.velocity.scl(0);
        }
        if(ball.position.y >= fieldShape.height - ball.shape.height/2) {
            ball.position = ball.prevPosition;
            ball.velocity.scl(0);
        }

    }


    /**Method to calculate the force on the ball at the x-axis. This method is used when calculating the new velocity*/
    public static double forceX(Ball ball, ArrayList<String> formula) {
        double Fx = ((-g) * FunctionAnalyser.derivative(formula, ball.position.x, ball.position.y, "x")) - (CurrentFriction * g * vx);
        return Fx;
    }

    /**Method to calculate the force on the ball at the y-axis. This method is used when calculating the new velocity*/
    public static double forceY(Ball ball, ArrayList<String> formula) {
        double Fy = ((-g) * FunctionAnalyser.derivative(formula, ball.position.x, ball.position.y, "y")) - (CurrentFriction * g * vy);
        return Fy;
    }



}
