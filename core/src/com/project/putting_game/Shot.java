package com.project.putting_game;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class Shot
{
    private Ball ball;
    private Field course;
    private Vector3 direction;
    private Vector3 originalPosition;
    private Hole hole;
    private float x;
    private float y;
    public float finalX;
    public float finalY;
    private float randX;
    private float randY;
    private float score;
    private ArrayList<String> fieldFormula;;

    Shot(float x, float y, Ball ball, Field course, Hole hole, ArrayList<String> fieldFormula)
    {
        this.ball = ball;
        this.course = course;
        this.fieldFormula = fieldFormula;
        originalPosition = ball.position.cpy();
        direction = new Vector3(x,y,0);
        //System.out.println(direction);
        randX = x;
        randY = y;
        this.hole = hole;
        takeShot();
    }

    public void takeShot()
    {
        ball.setUserVelocity(new Vector3(randX, randY, 0));
        while(ball.velocity.len() >= 0.02)
        {
            Engine.calculate(ball, course, fieldFormula);
        }
        x = ball.position.x;//ball.shape.width/2; hole.holeShape.width/2
        y = ball.position.y;//ball.shape.height/2; hole.holeShape.height/2
        finalX = x;
        finalY = y;

        float ax = hole.position.x - ball.position.x;
        float ay = hole.position.y - ball.position.y;
        float x2 = ax*ax;
        float y2 = ay*ay;
        float a = x2+ y2;
        score = (float) Math.sqrt(a);

        ball.position = originalPosition.cpy();
    }

    public float getRandX()
    {
        return randX;
    }

    public float getRandY()
    {
        return randY;
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public float getScore()
    {
        return score;
    }

    public Vector3 getDirection()
    {
        return direction;
    }
}
