package com.project.putting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class Engine {

    public static final double g = 9.81;
    public static double CurrentFriction;
    public static double currentHeight;
    public static final float h = 0.1f;
    public static Vector3 k1;
    public static Vector3 k2;
    public static Vector3 k3;
    public static Vector3 k4;


    public static void calculate(Ball ball, Field fields, ArrayList<String> formula) {

        k1 = velocity(ball.position, ball.velocity, formula).scl(h);
        k2 = velocity(ball.position.add(1f/3f*h), ball.velocity.add(k1.scl(1f/3f)), formula).scl(h);
        k3 = velocity(ball.position.add(2f/3f*h), ball.velocity.sub(k1.scl(1f/3f).add(k2)), formula).scl(h);
        k4 = velocity(ball.position.add(h), ball.velocity.add(k1).sub(k2).add(k3), formula).scl(h);

        ball.position.add((k1.add(k2.scl(2)).add(k3.scl(2)).add(k4)).scl(h/6f));
        ball.shape.x = ball.position.x;
        ball.shape.y = ball.position.y;

        //Get the friction of the surface at current location ball
        CurrentFriction = fields.getMatrix()[(int) ball.position.y][(int) ball.position.x].friction;

        //Get the height of the field at current location ball
        currentHeight = fields.getMatrix()[(int) ball.position.y][(int) ball.position.x].height;

        //Checks whether the ball has touched the walls or touched the water. If it did, return to the previous position and set velocity to 0.
        int border = 0;
        int ballSide =(int) ball.shape.height;
        int side = border + ballSide;
        if (ball.position.x <= ball.shape.width/2 || ball.position.y <= ball.shape.height/2 || ball.position.x >= Gdx.graphics.getWidth() - side ||
                ball.position.y >= Gdx.graphics.getHeight() - side || water(ball, fields)) {
//             System.out.println("Previous " + ball.prevPosition);
            ball.position = ball.prevPosition;
            ball.velocity.scl(0);
        }

        if(ball.velocity.len() <= 50) {
            ball.velocity.scl(0);
        }

        if(ball.position.x <= ball.shape.width/2){
            ball.position = ball.prevPosition;
            ball.velocity.scl(0);
        }
        if(ball.position.x >= Gdx.graphics.getWidth() - ball.shape.width/2) {
            ball.position = ball.prevPosition;
            ball.velocity.scl(0);
        }
        if(ball.position.y <= ball.shape.height/2) {
            ball.position = ball.prevPosition;
            ball.velocity.scl(0);
        }
        if(ball.position.y >= Gdx.graphics.getHeight() - ball.shape.height/2) {
            ball.position = ball.prevPosition;
            ball.velocity.scl(0);
        }

    }

    /**
     *
     * @param position
     * @param velocity
     * @param formula
     * @return
     */
    public static Vector3 velocity(Vector3 position, Vector3 velocity, ArrayList<String> formula){
        Vector3 acceleration = new Vector3();
        acceleration.x =(float) (((-g) * FunctionAnalyser.derivative(formula, position.x, position.y, "x")) - (CurrentFriction * g * velocity.x));
        acceleration.y =(float) (((-g) * FunctionAnalyser.derivative(formula, position.x, position.y, "y")) - (CurrentFriction * g * velocity.y));
        Vector3 newVelocity = velocity.cpy().add(acceleration.scl(h));

        return newVelocity;
    }

    //Right now, input is the ball object containing vectors. The output is only one position.
    //Now we get 2 positions, current pos + position we want to get too. Now we want to know what force we have to use to get to that point.
    //So we simulate the movement of the ball to place X.

    public static boolean water(Ball ball, Field field) {
        //System.out.println("Position:"+ball.position.x+" "+ball.position.y);
        Vector2 center = new Vector2(ball.position.x, ball.position.y);
        for (int i = (int)(center.x-ball.shape.width); i < center.x + ball.shape.width; i++) {
            for (int j = (int)(center.y-ball.shape.height); j < center.y + ball.shape.height; j++) {
                if (ball.shape.contains(new Vector2(i, j))) {
                    if(field.getMatrix()[field.getMatrix().length-1-j][i].height < 0){
                        return true;
                    }
                }
            }
        }
        return false;
    }



}
