package com.project.putting_game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Random;

public class GeneticBot {
    private Field course;
    private Ball ball;
    private int populationSize;
    private ArrayList<Shot> population;
    private Shot bestShot;
    private Hole hole;
    private int nMoves;
    private double totalFitness;


    public GeneticBot(Field course, Ball ball, Hole hole, int populationSize, int nMoves) {
        this.nMoves = nMoves;
        this.populationSize = populationSize;
        this.population = new ArrayList<Shot>();
        this.course = course;
        this.ball = ball;
        this.hole = hole;
        bestShot = new Shot(new Vector3(0,0,0),ball,course, hole);
    }

    public Vector3 startProcess() {
        populate();
        while(bestShot.getScore() < 0.06){
            geneticAlgorithm();
            System.out.println("Best: "+bestShot.getDirection());
        }
        return new Vector3(bestShot.getX(), bestShot.getY(), 0);

    }

    public void geneticAlgorithm() {
        ArrayList<Shot> newPopulation = new ArrayList<Shot>();
        while(newPopulation.size() < population.size()) {
            Shot s1 = rouletteWheelSelection();
//            System.out.println(s1.getDirection());
            Shot s2 = rouletteWheelSelection();
//            System.out.println(s2.getDirection());
            Shot son = arithmeticCrossover(s1, s2);
            mutationXY(son);
            newPopulation.add(son);
            if(son.getScore() > bestShot.getScore()) {
                bestShot = son;
            }
//            System.out.println(son.getDirection());
        }
        this.population = newPopulation;
    }

    public void populate() {
        //System.out.println("Taking shots");
        Random random = new Random();

        for(int i=0; i < populationSize; i++) {
            for(int i=0; i < nMoves; i++){

            }
            
        }

    }

    public Shot rouletteWheelSelection() {
        totalFitness = 0;
        for(Shot s: population) {
            totalFitness += s.getScore();
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


    public Shot arithmeticCrossover(Shot s1, Shot s2) {
        Vector3 direction = new Vector3((s1.getRandX()+s2.getRandX())/2, (s1.getRandY()+s2.getRandY())/2, 0);
        return new Shot(direction.cpy(), ball, course, hole);
    }

    public void mutationXY(Shot s) {
        double p = Math.random();
        if(p < 0.01) {
            s.setX((float)(s.getX()*Math.random()));
            s.setY((float)(s.getY()*Math.random()));
        }
        else if(p>0.01 && p<0.02) {
            s.setY((float)(s.getY()*Math.random()));
        }
        else if(p>0.02 && p<0.03) {
            s.setX((float)(s.getX()*Math.random()));
        }
    }

}
