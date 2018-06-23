package com.project.putting_game;
import com.badlogic.gdx.math.Vector3;

public class Shot implements Comparable {
    private Ball ball;
    private Field course;
    private Vector3 direction;
    private Vector3 originalPosition = new Vector3(500,100,0);
    private Hole hole;
    private float x;
    private float y;
    private float randX;
    private float randY;
    private float score;

    Shot(float x, float y, Ball ball, Field course, Hole hole)
    {
        this.ball = ball;
        this.course = course;
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
            Engine.calculate(ball, course, course.getFormula());
        }
        //ball.setUserVelocity(new Vector3(0,0,0));
        x = ball.position.x - ball.shape.width/2;
        y = ball.position.y - ball.shape.height/2;
        //System.out.println("x: " + x);
        //System.out.println("y: " + y);
        float ax = (hole.position.x + hole.holeShape.width/2) - (ball.position.x - ball.shape.width/2);
        float ay = (hole.position.y + hole.holeShape.height/2) - (ball.position.y - ball.shape.height/2);
        float x2 = ax*ax;
        float y2 = ay*ay;
        float a = x2+y2;
        score = (float) Math.sqrt(a);
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

    public void setX(float x) {this.x = x;}

    public void setY(float y) {this.x = y;}

    public float getScore()
    {
        return score;
    }

    public Vector3 getDirection()
    {
        return direction;
    }

    @Override
    public int compareTo(Object o) {
        Shot shot = (Shot)o;
        if(this.score > shot.score) {
            return 1;
        }
        if(this.score < shot.score) {
            return -1;
        }

        return 0;
    }
}
