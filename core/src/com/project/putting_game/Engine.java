package com.project.putting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class Engine {

    public static final double g = 9.81;
    public static double CurrentFriction;
    public static double currentHeight;
    public static final float h = 0.005f;
    public static Vector3 k1;
    public static Vector3 k2;
    public static Vector3 k3;
    public static Vector3 k4;

    public static boolean calculate(Ball ball, Field fields, ArrayList<String> formula) {
        //System.out.println(ball.velocity.x+" "+ball.velocity.y+" "+ball.position.y+" "+ball.position.x);
        //Get the friction of the surface at current location ball
        CurrentFriction = fields.getMatrix()[(int) ball.position.y][(int) ball.position.x].friction;
        //Get the height of the field at current location ball
        currentHeight = fields.getMatrix()[(int) ball.position.y][(int) ball.position.x].height;

        if (formula.get(0).equalsIgnoreCase("spline")) {
            k1 = acceleration(ball.position.cpy(), ball.velocity.cpy(), fields).scl(h);
            k2 = acceleration(ball.position.cpy().add(1f / 3f * h), ball.velocity.cpy().add(k1.cpy().scl(1f / 3f)), fields).scl(h);
            k3 = acceleration(ball.position.cpy().add(2f / 3f * h), ball.velocity.cpy().sub(k1.cpy().scl(1f / 3f).add(k2.cpy())), fields).scl(h);
            k4 = acceleration(ball.position.cpy().add(h), ball.velocity.cpy().add(k1.cpy()).sub(k2.cpy()).add(k3.cpy()), fields).scl(h);
        } else {
            k1 = acceleration(ball.position.cpy(), ball.velocity.cpy(), formula).scl(h);
            k2 = acceleration(ball.position.cpy().add(1f / 3f * h), ball.velocity.cpy().add(k1.cpy().scl(1f / 3f)), formula).scl(h);
            k3 = acceleration(ball.position.cpy().add(2f / 3f * h), ball.velocity.cpy().sub(k1.cpy().scl(1f / 3f).add(k2.cpy())), formula).scl(h);
            k4 = acceleration(ball.position.cpy().add(h), ball.velocity.cpy().add(k1.cpy()).sub(k2.cpy()).add(k3.cpy()), formula).scl(h);
        }

        ball.velocity.add((k1.add(k2.scl(3)).add(k3.scl(3)).add(k4)).scl(1f / 6f));
        ball.setPosition(ball.position.add(ball.velocity.cpy().scl(h)));
        //System.out.println(ball.velocity.x+" "+ball.velocity.y+" "+ball.position.y+" "+ball.position.x);

        //Checks whether the ball has touched the walls or touched the water. If it did, return to the previous position and set acceleration to 0.
        int border = 0;
        double ballSide = ball.shape.height;
        double side = border + ballSide;

        if (ball.position.x <= ball.shape.width / 2 || ball.position.y <= ball.shape.height / 2 || ball.position.x >= Gdx.graphics.getWidth() - side ||
                ball.position.y >= Gdx.graphics.getHeight() - side || water(ball, fields)) {
            ball.setPosition(ball.prevPosition);
            ball.velocity.scl(0);
        }

        if (ball.velocity.len() <= 50) {
            ball.velocity.scl(0);
        }
        return water(ball, fields);
    }

    /**
     *
     * @param position position of the ball
     * @param velocity velocity of the ball
     * @param formula formula of the field
     * @return acceleration
     */
    public static Vector3 acceleration(Vector3 position, Vector3 velocity,ArrayList<String> formula){
        Vector3 acceleration = new Vector3();
        acceleration.x =(float) (((-g) * FunctionAnalyser.derivative(formula, position.x, position.y, "x")) - (CurrentFriction * g * velocity.x));

        acceleration.y =(float) (((-g) * FunctionAnalyser.derivative(formula, position.x, position.y, "y")) - (CurrentFriction * g * velocity.y));

        return acceleration;
    }

    /**
     *
     * @param position position of the ball
     * @param velocity velocity of the ball
     * @param field field containing spline
     * @return acceleration
     */
    public static Vector3 acceleration(Vector3 position, Vector3 velocity, Field field){
        Vector3 acceleration = new Vector3();
        acceleration.x = (float) (((-g) * FunctionAnalyser.derivative(field, (int) position.x, (int) position.y, "x")) - (CurrentFriction * g * velocity.x));

        acceleration.y = (float) (((-g) * FunctionAnalyser.derivative(field, (int) position.x, (int) position.y, "y")) - (CurrentFriction * g * velocity.y));

        return acceleration;
    }


    //Right now, input is the ball object containing vectors. The output is only one position.
    //Now we get 2 positions, current pos + position we want to get too. Now we want to know what force we have to use to get to that point.
    //So we simulate the movement of the ball to place X.

    public static boolean water(Ball ball, Field field) {
        //System.out.println("Position:"+ball.position+"Height:"+field.getMatrix()[field.getMatrix().length-1- (int)ball.position.y][(int)ball.position.x].height);
        boolean inside=false;
        Vector2 center = new Vector2(ball.position.x, ball.position.y);
        for (int i = (int)(center.x-ball.shape.width); i < center.x + ball.shape.width; i++) {
            for (int j = (int)(center.y-ball.shape.height); j < center.y + ball.shape.height; j++) {
                if (ball.shape.contains(new Vector2(i, j))) {
                    inside=true;
                    if(field.getMatrix()[field.getMatrix().length-1-j][i].height < 0){
                        return true;
                    }
                }
            }
        }
        //System.out.println(inside);
        return false;
    }


}