package com.project.putting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Random;

public class Play {
    public ArrayList<Shot> moves;
    private double score;
    private Vector3 lastPos;

    public Play() {
        this.moves = new ArrayList<Shot>();
    }

    public Play(ArrayList<Shot> moves){
        this.moves = moves;
    }

    public double getScore() {
        double score = 0;
        if(moves.size() == 0){
            return -1;
        }
//        moves.get(moves.size()-1).water();
        return score = moves.get(moves.size()-1).getScore();
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Shot createShot(Ball ball, Field course, Hole hole){
        Random random = new Random();
        float x = random.nextFloat() * Gdx.graphics.getWidth();
        float y = random.nextFloat() * Gdx.graphics.getHeight();
        if(Math.random() < 0.5){
            x*=-1;
        }
        if(Math.random() < 0.5){
            y*=-1;
        }
        Vector3 direction = new Vector3(x,y,0);
        Shot s = new Shot(direction, ball, course, hole);
        moves.add(s);
        return s;
    }

    public void setLastPos(Vector3 lastPos){
        this.lastPos = lastPos;
    }


    public void print(){
        for(int i=0; i<moves.size(); i++){
            System.out.println("Shot "+i+" is "+moves.get(i).getDirection());
        }
        System.out.println("Last pos was " +lastPos);
    }
}
