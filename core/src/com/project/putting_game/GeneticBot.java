package com.project.putting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Random;

public class GeneticBot {
    private Field course;
    private Ball ball;
    private int populationSize;
    private ArrayList<Play> population;
    private Play bestPlay;
    private Hole hole;
    private int nMoves;
    private double totalFitness;


    public GeneticBot(Field course, Ball ball, Hole hole, int populationSize, int nMoves) {
        this.nMoves = nMoves;
        this.populationSize = populationSize;
        this.population = new ArrayList<Play>();
        this.course = course;
        this.ball = ball;
        this.hole = hole;
        bestPlay = new Play();
    }

    public Play startProcess() {
        populate();
        while(bestPlay.getScore() < 0.06){
            geneticAlgorithm();
        }
        return bestPlay;

    }

    public void geneticAlgorithm() {
        ArrayList<Play> newPopulation = new ArrayList<Play>();
        while(newPopulation.size() < population.size()) {
            Play p1 = rouletteWheelSelection();
//            System.out.println(s1.getDirection());
            Play p2 = rouletteWheelSelection();
//            System.out.println(s2.getDirection());
            Play son = arithmeticCrossover(p1, p2);
            mutationXY(son);
            newPopulation.add(son);
            if(son.getScore() > bestPlay.getScore()) {
                bestPlay = son;
            }
//            System.out.println(son.getDirection());
        }
        this.population = newPopulation;
    }

    public void populate() {
        //System.out.println("Taking shots");
        Random random = new Random();

        for(int i=0; i < populationSize; i++) {
            Play p = new Play();
            for(int i=0; i < nMoves; i++){
                float x = random.nextFloat() * Gdx.graphics.getWidth();
                float y = random.nextFloat() * Gdx.graphics.getHeight();

                Vector3 direction = new Vector3(x,y,0);
                p.moves.add(new Shot(direction, ball, course, hole));
//            System.out.println(population.get(i).getScore());
            }
            population.add(p);
        }

    }

    public Play rouletteWheelSelection() {
        totalFitness = 0;
        for(Play p: population) {
            totalFitness += p.getScore();
        }
//        System.out.println("Jelly bean: "+totalFitness);
//        System.out.println("Fitness: "+totalFitness);
        double threshold = Math.random() * totalFitness;
//        System.out.println("Threshold: "+threshold);
        for(int i = 0; i < population.size(); i++) {
//            System.out.println("Direction: "+population.get(i).getDirection());
//            System.out.println("Score: "+population.get(i).getScore());
            threshold -= population.get(i).getScore();
//            System.out.println("New threshold = "+threshold);
            if(threshold < 0) {
//                System.out.println("Hey");
                return population.get(i);
            }
        }
//        System.out.println(population.get(population.size()-1).getDirection());
        return population.get(population.size()-1);
    }


    public Play arithmeticCrossover(Play p1, Play p2) {
        Play p = new Play();
        for(int i = 0; i < nMoves; i++){
            Vector3 direction = new Vector3((p1.moves.get(i).getRandX()+p2.moves.get(i).getRandX())/2, (p1.moves.get(i).getRandY()+p2.moves.get(i).getRandY())/2, 0);
            p.moves.add(new Shot(direction.cpy(), ball, course, hole));
        }
        return p;
    }

    public void mutationXY(Play p) {
        double a = Math.random();
        if(a < 0.01) {
            p.moves.remove(nMoves);
            p.moves.add(new Shot(new Vector3((float)(Math.random()*Gdx.graphics.getWidth()), (float)(Math.random()*Gdx.graphics.getHeight()), 0), ball, course, hole)ñ
        }

    }

}
