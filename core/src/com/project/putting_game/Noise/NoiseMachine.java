package com.project.putting_game.Noise;

public interface NoiseMachine{
    
    double speedLimit = 5;
    double[] dRL = {0, 5};

    //contructor: two arguments, double represents the speed limit, double[2] represents the direction degree limits
    //if constructed without arguments: default are 5, and 0-360.
    //return: [0] represents the speed, [1] represents the direction of noise
    public double[] generateNoise();

}