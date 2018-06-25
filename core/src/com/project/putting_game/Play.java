package com.project.putting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Random;

public class Play {
    public ArrayList<Shot> moves;
    private double score;

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
        return score = moves.get(moves.size()-1).getScore();
    }

    public Shot createShot(Ball ball, Field course, Hole hole){
        Random random = new Random();
        float x = random.nextFloat() * Gdx.graphics.getWidth();
        float y = random.nextFloat() * Gdx.graphics.getHeight();

        Vector3 direction = new Vector3(x,y,0);
        Shot s = new Shot(direction, ball, course, hole);
        moves.add(s);
        return s;
    }


    public void print(){
        for(int i=0; i<moves.size(); i++){
            System.out.println("Shot "+i+" is "+moves.get(i).getDirection());
        }
    }
}
