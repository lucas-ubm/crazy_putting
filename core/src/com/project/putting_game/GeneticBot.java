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


    public GeneticBot(Field course, Ball ball, Hole hole, int populationSize) {
        this.populationSize = populationSize;
        this.population = new ArrayList<Shot>();
        this.course = course;
        this.ball = ball;
        this.hole = hole;
        bestShot = new Shot(0,0,ball,course, hole);
    }

    public Vector3 startProcess() {
        populate();
        while(bestShot.getScore() > 15){
            geneticAlgorithm();
        }
        return new Vector3(bestShot.getX(), bestShot.getY(), 0);

    }

    public void geneticAlgorithm() {
        ArrayList<Shot> newPopulation = new ArrayList<Shot>();
        while(newPopulation.size() < population.size()) {
            Shot s1 = rouletteWheelSelection();
            Shot s2 = rouletteWheelSelection();
            Shot son = arithmeticCrossover(s1, s2);
            mutationXY(son);
            newPopulation.add(son);
            if(son.getScore() > bestShot.getScore()) {
                bestShot = son;
            }

        }
        this.population = newPopulation;
    }

    public void populate() {
        //System.out.println("Taking shots");
        Random random = new Random();

        for(int i=0; i < populationSize; i++) {
            float x = random.nextFloat() * Gdx.graphics.getHeight();
            float y = random.nextFloat() * Gdx.graphics.getWidth();
            population.add(new Shot(x,y, ball, course, hole));
        }
    }

    public Shot rouletteWheelSelection() {
        double totalFitness = 0;
        for(Shot s: population) {
            totalFitness =+ s.getScore();
        }
        double threshold = Math.random() * totalFitness;
        int i = 0;
        Shot shot = new Shot(0,0,ball,course,hole);
        while(shot.getScore() < threshold){
            shot = population.get(i);
            i++;
        }

        return shot;
    }


    public Shot arithmeticCrossover(Shot s1, Shot s2) {
        return new Shot((s1.getX()+s2.getX())/2, (s1.getY()+s2.getY())/2, ball, course, hole);
    }

    public void mutationXY(Shot s) {
        double p = Math.random();
        if(p < 0.01) {
            s.setX((float)(s.getX()*Math.random()*0.1));
            s.setY((float)(s.getY()*Math.random()*0.1));
        }
        else if(p>0.01 && p<0.02) {
            s.setY((float)(s.getY()*Math.random()*0.2));
        }
        else if(p>0.02 && p<0.03) {
            s.setX((float)(s.getX()*Math.random()*0.2));
        }
    }

}
