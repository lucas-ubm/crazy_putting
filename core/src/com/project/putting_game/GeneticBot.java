package com.project.putting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class GeneticBot {
    private Field course;
    private Ball ball;
    private int populationSize;
    private ArrayList<Play> population;
    private Play bestPlay;
    private Hole hole;
    private int nMoves;
    public int generation;
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
//        System.out.println("Hey");
        populate();
        //1/(hole.holeShape.height-ball.shape.height)
        while(bestPlay.getScore() < 1/(hole.holeShape.height/4)){
            geneticAlgorithm();
//            System.out.println("Here");
//            bestPlay.print();
//            if(gener
// ation >= 5){
//                return bestPlay;
//            }
            if(population.get(0).moves.get(0).getDirection().len() > new Vector3(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),0).len()){
                return null;
            }
        }
        ball.position = ball.oriPosition;
        return bestPlay;

    }

    public void geneticAlgorithm() {
        ArrayList<Play> newPopulation = new ArrayList<Play>();
        totalFitness = 0;
        for(Play p: population) {
            totalFitness += p.getScore();
        }

        while(newPopulation.size() < population.size()) {
            Play p1 = rouletteWheelSelection();
//            System.out.print("p1 ");
//            p1.print();
            Play p2 = rouletteWheelSelection();
//            System.out.print("p2 ");
//            p2.print();
            Play son = arithmeticCrossover(p1, p2);
            mutationXY(son);
            newPopulation.add(son);
            if(son.getScore() > bestPlay.getScore()) {
                bestPlay = son;
            }
        }
        generation++;
//        System.out.println("Fitness: "+totalFitness + " for generation "+generation);
        this.population = newPopulation;
    }

    public void populate() {
        //System.out.println("Taking shots");

        for(int i=0; i < populationSize; i++) {
            Play p = new Play();
            for(int j = 0; j < nMoves; j++){
                p.createShot(ball, course, hole);
//            System.out.println(population.get(i).getScore());
            }
            p.setLastPos(ball.position.cpy());
            ball.position = ball.oriPosition.cpy();

            ball.moveHistory = new Queue<Vector3>();
            population.add(p);
//            p.print();
//            System.out.println("Score = "+p.getScore());
            if(p.getScore() > bestPlay.getScore()) {
                bestPlay = p;
            }
        }

    }

    public Play rouletteWheelSelection() {

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
            float x1 = p1.moves.get(i).getDirection().x;
            float x2 = p2.moves.get(i).getDirection().x;

            float y1 = p1.moves.get(i).getDirection().y;
            float y2 = p2.moves.get(i).getDirection().y;
            Vector3 direction = new Vector3(x1+x2/2f, y1+y2/2f, 0);

//            if(Math.random() < 0.5) {
//                direction.x = direction.x*-1;
//            }
//
//            if(Math.random() < 0.5) {
//                direction.y = direction.y*-1;
//            }

            p.moves.add(new Shot(direction.cpy(), ball, course, hole));
        }
        p.setLastPos(ball.position.cpy());
        ball.position = ball.oriPosition.cpy();

        ball.moveHistory = new Queue<Vector3>();
        return p;
    }

    public void mutationXY(Play p) {
        double a = Math.random();
        if(a < 0.05) {
            Vector3 direction = p.moves.get((int)(a*nMoves-1)).getDirection();
            p.moves.get((int)(a*nMoves-1)).setDirection(new Vector3(direction.y, direction.x, 0));
        }

    }

}
