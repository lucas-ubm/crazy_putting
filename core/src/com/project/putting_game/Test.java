package com.project.putting_game;

public class Test {
    public static void main(String[] args){
        System.out.println(FunctionAnalyser.runge_kutta(FunctionAnalyser.ShuntingYard("sinx+y-y^3"), 0, 10, 0.25, 2));
        System.out.println(FunctionAnalyser.derivative(FunctionAnalyser.ShuntingYard("3*x^2-y"), 7,"x"));
    }
}
