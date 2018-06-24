package com.project.putting_game.Noise;

public class main {

    public static void main(String args[]) {
        DifferentBothNoise machine = new DifferentBothNoise();
        for(int i=0 ; i<11 ; i++) {
            double[] result = machine.generateNoise();
            System.out.println(result[0]);
            System.out.println(result[1]);
        }

    }
}