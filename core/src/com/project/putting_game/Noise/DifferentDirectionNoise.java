package com.project.putting_game.Noise;

import java.util.Random;

public class DifferentDirectionNoise implements NoiseMachine{

    double speedLimit = 5;
    double[] dRL = {0, 5};
    Random r = new Random();

    public DifferentDirectionNoise() {
        speedLimit = speedLimit * r.nextDouble();
    }
    
    public DifferentDirectionNoise(double sl, double[] dRLIN){
        speedLimit = sl * r.nextDouble();
        dRL = dRLIN;
    }

    public double[] generateNoise(){
        double[] noise = new double[2];
        noise[0] = speedLimit;
        noise[1] = dRL[0] + (dRL[1] - dRL[0]) * r.nextDouble();
        return noise;
        
    }

}