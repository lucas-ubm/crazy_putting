package com.project.putting_game;

import com.badlogic.gdx.math.Vector3;
import java.util.ArrayList;

public class BinaryBot
{
    private Field course;
    private Ball ball;
    private int boundX;
    private int boundY;
    private ArrayList<String> fieldFormula;
    private Hole hole;
    private final Vector3 startingPosition;

    BinaryBot(Field course, Ball ball, Hole hole, ArrayList<String> fieldFormula)
    {
        this.course = course;
        this.ball = ball;
        //System.out.println("ball pos " + ball.position);
        this.fieldFormula = fieldFormula;
        startingPosition = ball.position.cpy();
        //System.out.println("Starting position: " + startingPosition);
        this.hole = hole;
        //System.out.println("hole pos " + hole.position);
    }

    public Vector3 startProcess()
    {
        int realX;
        int realY;
        if(ball.position.x <= hole.position.x)
        {
            boundX = 4000;
            //System.out.println("Starting positive binary search");
            realX = binarySearchX(0, boundX, 0);
        }
        else
        {
            boundX = -4000;
            System.out.println();
            //System.out.println("Starting negative binary search");
            realX = binarySearchX(boundX, 0, 1);
        }

        if(ball.position.y <= hole.position.y)
        {
            boundY = 4000;
            //System.out.println("Starting positive binary search");
            realY = binarySearchY(0, boundY, 0);
        }
        else
        {
            boundY = -4000;
            //System.out.println("Starting negative binary search");
            realY = binarySearchY(boundY, 0, 1);
        }

        //System.out.println("x: " + realX);
        //System.out.println("y: " + realY);

        Vector3 shot = new Vector3(realX, realY, 0);
        ball.position = startingPosition.cpy();

        return shot;
    }

    public int binarySearchX(int min , int max, int minOrPlus)
    {
        ball.position = startingPosition.cpy();

        int awnser = 0;

        //System.out.println("binary X");
        //System.out.println("min: " + min + " max: " + max);

        int point = (max+min)/2;
        //System.out.println("point: " + point);

        Vector3 velocity;
        velocity = new Vector3(point,0,0);
        ball.setUserVelocity(velocity);

        while(ball.velocity.len() >= 0.02)
        {
            Engine.calculate(ball, course, fieldFormula);
        }

        //System.out.println("ball pos: " + ball.position);
        //System.out.println("hole pos: " + hole.position);

        if(Math.abs((ball.position.x - hole.position.x))<13)
            return point;

        if(minOrPlus == 0)
        {
            if(ball.position.x > hole.position.x || (point > 40 && (int) ball.position.x == startingPosition.x))
            {
                //System.out.println("too far");
                awnser = binarySearchX(min, point, 0);
            }
            else if(ball.position.x <= hole.position.x)
            {
                //System.out.println("came up short");
                awnser = binarySearchX(point, max, 0);
            }
        }
        else
        {
            if(ball.position.x <= hole.position.x || ((int) ball.position.x == startingPosition.x && point <-40))
            {
                //System.out.println("too far");
                awnser = binarySearchX(point, max, 1);
            }
            else
            {
                //System.out.println("came up short");
                awnser = binarySearchX(min, point, 1);
            }
        }

        //System.out.println("something went wrong");
        return awnser;
    }

    public int binarySearchY(int min , int max, int minOrPlus)
    {
        ball.position = startingPosition.cpy();

        int awnser = 0;

        //System.out.println("binary Y");
        //System.out.println("min: " + min + " max: " + max);

        int point = (max+min)/2;
        //System.out.println("point: " + point);

        Vector3 velocity;
        velocity = new Vector3(0,point,0);
        ball.setUserVelocity(velocity);

        while(ball.velocity.len() >= 0.02)
        {
            Engine.calculate(ball, course, fieldFormula);
        }

        //System.out.println("ball pos: " + ball.position);
        //System.out.println("hole pos: " + hole.position);

        if(Math.abs((ball.position.y - hole.position.y))<13)
            return point;

        if(minOrPlus == 0)
        {
            if(ball.position.y > hole.position.y || (point > 40 && (int) ball.position.y == startingPosition.y))
            {
                //System.out.println("too far");
                awnser = binarySearchY(min, point, 0);
            }
            else if(ball.position.y <= hole.position.y)
            {
                //System.out.println("came up short");
                awnser = binarySearchY(point, max, 0);
            }
        }
        else
        {
            if(ball.position.y <= hole.position.y || ((int) ball.position.y == startingPosition.y && point <-40))
            {
                //System.out.println("too far");
                awnser = binarySearchY(point, max, 1);
            }
            else
            {
                //System.out.println("came up short");
                awnser = binarySearchY(min, point, 1);
            }
        }

        //System.out.println("something went wrong");
        return awnser;
    }

}