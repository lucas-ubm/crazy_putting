package com.project.putting_game;
import com.badlogic.gdx.math.Vector3;

public class Shot implements Comparable {
    private Ball ball;
    private Field course;
    private Vector3 direction;
    private Vector3 originalPosition;
    private Hole hole;
    private float x;
    private float y;
    private float score;

    Shot(Vector3 direction, Ball ball, Field course, Hole hole)
    {
        this.ball = ball;
        this.course = course;

        float intensity = 1f;
        float a = (float)Math.random()*intensity;
        float b = (float)Math.random()*intensity;

        if(Math.random() < 0.5) {
            a*=-1;
        }
        if(Math.random() < 0.5) {
            b*=-1;
        }

        Vector3 fakePosition = new Vector3(ball.position.cpy().x *a, ball.position.cpy().y * b, 0);


        originalPosition = fakePosition;
        this.direction = direction;
        //System.out.println(direction);

        this.hole = hole;
        takeShot();
    }

    public void takeShot() {
        ball.setUserVelocity(direction.cpy());
        ball.prevPosition = ball.position.cpy();
        while(ball.velocity.len() != 0) {
            Engine.calculate(ball, course, course.getFormula());
        }
//        //ball.setUserVelocity(new Vector3(0,0,0));
//        x = ball.position.x - ball.shape.width/2;
//        y = ball.position.y - ball.shape.height/2;
//        //System.out.println("x: " + x);
//        //System.out.println("y: " + y);
//        float ax = (hole.position.x + hole.holeShape.width/2) - (ball.position.x - ball.shape.width/2);
//        float ay = (hole.position.y + hole.holeShape.height/2) - (ball.position.y - ball.shape.height/2);
//        float x2 = ax*ax;
//        float y2 = ay*ay;
//        float a = x2+y2;
//        float distance = (float) Math.sqrt(a);
        float distance = ball.position.dst(hole.position);

        score = 1/distance;

//        if(water()){
//            score*=0.5;
//            System.out.println("anacoluto");
//        }
//        ball.position = originalPosition;
    }

//    private boolean water(){
//        Ball waterBall = ball.copy();
//        waterBall.setUserVelocity(waterBall.position.sub(hole.position.cpy()).scl(-1));
//        while(waterBall.velocity.len()!=0) {
//            if(Engine.calculate(waterBall, course, course.getFormula())){
//                return true;
//            }
//        }
//        return false;
//    }
    public float getRandX()
    {
        return direction.x;
    }

    public float getRandY()
    {
        return direction.y;
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
    public void setDirection(Vector3 direction) {this.direction = direction;}

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
