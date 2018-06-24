package com.project.putting_game;

import java.util.ArrayList;

public class Play {
    public ArrayList<Shot> moves;
    public Play() {
        this.moves = new ArrayList<Shot>();
    }
    public Play(ArrayList<Shot> moves){
        this.moves = moves;
    }
}
