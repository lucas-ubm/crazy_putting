package com.project.putting_game;

import com.badlogic.gdx.math.Vector3;
import java.util.ArrayList;
public class Shot
{
    private Ball ball;
    private Field course;
    private Vector3 direction;
    private Vector3 originalPosition = new Vector3(80,80,0);
    private Hole hole;
    private float x;
    private float y;
    public float finalX;
    public float finalY;
    private float randX;
    private float randY;
    private float score;
    private ArrayList<String> fieldFormula;

    Shot(float x, float y, Ball ball, Field course, Hole hole, ArrayList<String> fieldFormula)
    {
        this.ball = ball;
        this.course = course;
        this.fieldFormula = fieldFormula;
        //originalPosition = ball.position;
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
        //ball.setUserVelocity(new Vector3(0,0,0));
        x = ball.position.x;//ball.shape.width/2; hole.holeShape.width/2
        y = ball.position.y;//ball.shape.height/2; hole.holeShape.height/2
        finalX = x;
        finalY = y;

        float goalX = hole.position.x+9;//(hole.holeShape.width/2)-(ball.shape.width/2);
        float goalY = hole.position.y+9;//(hole.holeShape.width/2)-(ball.shape.width/2);
        float ax = goalX - ball.position.x;
        float ay = goalY - ball.position.y;
        float x2 = ax*ax;
        float y2 = ay*ay;
        float a = x2+y2;
        score = (float) Math.sqrt(a);
        //System.out.println("x: " + x);
        //System.out.println("y: " + y);
        /*float ax = (hole.position.x+25) - (ball.position.x - 16);
        float ay = (hole.position.y+25) - (ball.position.y - 16);
        float x2 = ax*ax;
        float y2 = ay*ay;
        float a = x2+y2;
        score = (float) Math.sqrt(a);*/
        //System.out.println(score);
        ball.position = originalPosition;
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

