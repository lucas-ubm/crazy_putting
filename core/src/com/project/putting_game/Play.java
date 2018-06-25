package com.project.putting_game;

import java.util.ArrayList;

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

}
